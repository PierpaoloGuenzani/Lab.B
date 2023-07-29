package emotionalsongs;

import common.Playlist;

import java.sql.*;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class PlaylistSQLDB implements PlaylistDAOInterface
{
	
	private Connection serverSQL;
	
	public PlaylistSQLDB(Connection serverSQL)
	{
		this.serverSQL = serverSQL;
	}
	
	@Override
	public Optional<Playlist> get(String id)
	{
		if(id == null | id.isEmpty())
			return Optional.empty();
		try
		{
			PreparedStatement select = serverSQL.prepareStatement(
					"SELECT * FROM playlist" +
							"WHERE idPlaylist = ?");
			select.setString(1, id);
			ResultSet resultSet = select.executeQuery();
			if (resultSet.next())
			{
				Playlist p = new Playlist(
						resultSet.getString("titolo"),
						resultSet.getString("idUtente")
				);
				PreparedStatement selectSong = serverSQL.prepareStatement(
						"SELECT idCanzone FROM Playlist_Canzoni" +
							"WHERE idPlaylist = ?");
				selectSong.setString(1,p.getIdPlaylist());
				ResultSet canzoni =  selectSong.executeQuery();
				while (canzoni.next())
				{
					p.aggiungiCanzone(canzoni.getString("idCazone"));
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
			PreparedStatement select = serverSQL.prepareStatement(
					"SELECT * FROM playlists");
			ResultSet resultSet = select.executeQuery();
			while(resultSet.next())
			{
				Playlist p = new Playlist(
						resultSet.getString("titolo"),
						resultSet.getString("idUtente"),
						resultSet.getString("idPlaylist")
				);
				albero.put(p.getIdPlaylist(), p);
				PreparedStatement selectSong = serverSQL.prepareStatement(
						"SELECT * FROM Playlist_Canzoni " +
								"WHERE idPlaylist = ?");
				selectSong.setString(1,p.getIdPlaylist());
				ResultSet canzoni =  selectSong.executeQuery();
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
			PreparedStatement insert = serverSQL.prepareStatement(
					"INSERT INTO Playlists(" +
						"idPlaylist, idUtente, titolo)" +
						"VALUES (?, ?, ?)");
			insert.setString(1, playlist.getIdPlaylist());
			insert.setString(2, playlist.getIdPersona());
			insert.setString(3, playlist.getTitolo());
			insert.executeUpdate();/*
			PreparedStatement insertSong = serverSQL.prepareStatement(
					"INSERT INTO Playlist_Canzoni(" +
						"idPlaylist, idCanzone" +
						"VALUES (?, ?)");
			String id = playlist.getIdPlaylist();
			for(String idCanzone : playlist.getListaCanzoni())
			{
				insertSong.setString(1, playlist.getIdPlaylist());
				insertSong.setString(2, idCanzone);
				insertSong.executeQuery();
			}*/
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
			insert = serverSQL.prepareStatement(
					"INSERT INTO Playlist_Canzoni(idPlaylist, idCanzone) " +
						"VALUES (?, ?)");
			insert.setString(1,idPlaylist);
			insert.setString(2,idCanzone);
			insert.executeUpdate();
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
			PreparedStatement delete = serverSQL.prepareStatement(
					"DELETE FROM Playlists WHERE idPlaylist = ?");
			delete.setString(1, playlist.getIdPlaylist());
			delete.executeUpdate();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}
