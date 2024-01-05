package emotionalsongs;

import common.Playlist;

import java.sql.*;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class PlaylistSQLDB implements PlaylistDAOInterface
{
	
	private Connection serverSQL;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement selectAllSongIdFromPlaylist;
	private PreparedStatement insert;
	private PreparedStatement insertSongInPlaylist;
	private PreparedStatement update;
	private PreparedStatement delete;
	
	public PlaylistSQLDB(Connection serverSQL) throws SQLException
	{
		this.serverSQL = serverSQL;
		synchronized (serverSQL)
		{
			select = serverSQL.prepareStatement("SELECT * FROM playlist WHERE idPlaylist = ?");
			selectAllSongIdFromPlaylist = serverSQL.prepareStatement("SELECT idCanzone FROM Playlist_Canzoni" +
					"WHERE idPlaylist = ?");
			selectAll = serverSQL.prepareStatement("SELECT * FROM playlists");
			
			insert = serverSQL.prepareStatement("INSERT INTO Playlists(idPlaylist, idUtente, titolo) " +
					"VALUES (?, ?, ?)");
			insertSongInPlaylist = serverSQL.prepareStatement("INSERT INTO Playlist_Canzoni(idPlaylist, idCanzone) " +
					"VALUES (?, ?)");
			delete = serverSQL.prepareStatement("DELETE FROM Playlists WHERE idPlaylist = ?");
		}
	}
	
	@Override
	public Optional<Playlist> get(String id)
	{
		if(id == null | id.isEmpty())
			return Optional.empty();
		try
		{
			ResultSet resultSet;
			synchronized (serverSQL)
			{
				//TODO: controllare e pensare bene
				select.setString(1, id);
				resultSet = select.executeQuery();
			}
			if (resultSet.next())
			{
				Playlist p = new Playlist(
						resultSet.getString("titolo"),
						resultSet.getString("idUtente")
				);
				selectAllSongIdFromPlaylist.setString(1,p.getIdPlaylist());
				ResultSet idCanzoni =  selectAllSongIdFromPlaylist.executeQuery();
				while (idCanzoni.next())
				{
					p.aggiungiCanzone(idCanzoni.getString("idCazone"));
				}
				return Optional.of(p);
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
		} //LOG?
		return Optional.empty();
	}
	
	@Override
	public ConcurrentHashMap<String, Playlist> getAll()
	{
		ConcurrentHashMap<String, Playlist> albero = new ConcurrentHashMap<>();
		try
		{
			
			ResultSet resultSet = selectAll.executeQuery();
			while(resultSet.next())
			{
				Playlist p = new Playlist(
						resultSet.getString("titolo"),
						resultSet.getString("idUtente"),
						resultSet.getString("idPlaylist")
				);
				albero.put(p.getIdPlaylist(), p);
				selectAllSongIdFromPlaylist.setString(1,p.getIdPlaylist());
				ResultSet canzoni =  selectAllSongIdFromPlaylist.executeQuery();
				while (canzoni.next())
				{
					p.aggiungiCanzone(canzoni.getString("idCanzone"));
				}
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
		} //LOG?
		return albero;
	}
	
	@Override
	public boolean save(Playlist playlist)
	{
		if(playlist == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				insert.setString(1, playlist.getIdPlaylist());
				insert.setString(2, playlist.getIdPersona());
				insert.setString(3, playlist.getTitolo());
				insert.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean update(Playlist playlist, Object[] params)
	{
		//TODO
		return false;
	}
	
	public boolean addSong(String idCanzone, String idPlaylist)
	{
		if(idPlaylist == null | idPlaylist.length() < 16)
			return false;
		if(idCanzone == null | idCanzone.isBlank())
			return false;
		PreparedStatement insert = null;
		try
		{
			synchronized (serverSQL)
			{
				insertSongInPlaylist.setString(1, idPlaylist);
				insertSongInPlaylist.setString(2, idCanzone);
				insertSongInPlaylist.executeUpdate();
			}
		} catch (SQLException e)
		{
			System.err.println(e.toString());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean delete(Playlist playlist)
	{
		if(playlist == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				delete.setString(1, playlist.getIdPlaylist());
				delete.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}
