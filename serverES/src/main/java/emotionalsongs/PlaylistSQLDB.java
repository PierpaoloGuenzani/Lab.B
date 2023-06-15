package emotionalsongs;

import common.Playlist;

import java.sql.*;
import java.util.Optional;
import java.util.TreeMap;

public class PlaylistSQLDB implements Dao<Playlist>
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
		} catch (SQLException e) {} //LOG?
		return Optional.empty();
	}
	
	@Override
	public TreeMap<String, Playlist> getAll()
	{
		TreeMap<String, Playlist> albero = new TreeMap<>();
		try
		{
			PreparedStatement select = serverSQL.prepareStatement(
					"SELECT * FROM playlist");
			ResultSet resultSet = select.executeQuery();
			while(resultSet.next())
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
			}
		} catch (SQLException e) {} //LOG?
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
					"INSERT INTO emozioni(" +
						"idPlaylist, idUtente, titolo)" +
						"VALUES (?, ?, ?)");
			insert.setString(1, playlist.getIdPlaylist());
			insert.setString(2, playlist.getIdPersona());
			insert.setString(3, playlist.getTitolo());
			ResultSet resultSet = insert.executeQuery();
			PreparedStatement insertSong = serverSQL.prepareStatement(
					"INSERT INTO Playlist_Canzoni(" +
						"idPlaylist, idCanzone" +
						"VALUES (?, ?)");
			String id = playlist.getIdPlaylist();
			for(String canzone : playlist.getListaCanzoni())
			{
				insertSong.setString(1, id);
				insertSong.setString(2, canzone);
				insertSong.executeQuery();
			}
		} catch (SQLException e) {
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
	
	public boolean addSong(String idPlaylist, String idCanzone)
	{
		if(idPlaylist == null | idPlaylist.length() < 18)
			return false;
		if(idCanzone == null | idCanzone.isBlank())
			return false;
		PreparedStatement insert = null;
		try
		{
			insert = serverSQL.prepareStatement(
					"INSERT INTO Playlist_Canzoni" +
						"VALUES (?, ?)");
			insert.setString(1,idPlaylist);
			insert.setString(2,idCanzone);
			ResultSet resultSet = insert.executeQuery();
		} catch (SQLException e)
		{
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
			ResultSet resultSet = delete.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}
