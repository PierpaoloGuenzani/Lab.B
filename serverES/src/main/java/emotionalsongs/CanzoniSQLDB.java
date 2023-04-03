package emotionalsongs;

import common.Canzone;

import java.sql.*;
import java.util.Optional;
import java.util.TreeMap;

public class CanzoniSQLDB implements Dao<Canzone>
{
	private Connection serverSLQ;
	
	public CanzoniSQLDB(Connection serverSLQ)
	{
		this.serverSLQ = serverSLQ;
	}
	
	@Override
	public Optional<Canzone> get(String id)
	{
		try
		{
			PreparedStatement select = serverSLQ.prepareStatement(
					"SELECT * FROM canzoni WHERE idCanzone = ?");
			select.setString(1, id);
			ResultSet resultSet = select.executeQuery();
			if (resultSet.next())
			{
				//return Optional.of(); //Prova
			}
		} catch (SQLException e) {} //LOG?
		return Optional.empty();
	}
	
	@Override
	public TreeMap<String, Canzone> getAll()
	{
		TreeMap<String , Canzone> albero = new TreeMap<>();
		try
		{
			PreparedStatement selectAll = serverSLQ.prepareStatement("SELECT * FROM canzoni");
			ResultSet resultSet = selectAll.executeQuery();
			while(resultSet.next())
			{
				Canzone c = new Canzone(
						resultSet.getString("idCanzone"),
						resultSet.getString("titolo"),
						resultSet.getString("produttore"),
						resultSet.getInt("anno")
				);
				albero.put(c.getId(),c);
			}
		} catch (SQLException e) {} //LOG?
		return albero;
	}
	
	@Override
	public boolean save(Canzone canzone)
	{
		try
		{
			PreparedStatement insert = serverSLQ.prepareStatement(
					"INSERT INTO canzoni(idCanzone, titolo, produttore, anno)" +
						"VALUES (?, ?, ?, ?)");
			insert.setString(1, canzone.getId());
			insert.setString(2, canzone.getTitolo());
			insert.setString(3, canzone.getArtista());
			insert.setInt(4, canzone.getAnno());
			ResultSet resultSet = insert.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean update(Canzone canzone, Object[] params)
	{
		if(params.length != canzone.getClass().getDeclaredFields().length)
			return false;
		if(!canzone.getId().equals((String)params[0]))
			return false;
		try
		{
			PreparedStatement update = serverSLQ.prepareStatement(
					"UPDATE canzoni SET titolo = ?, produttore = ?, anno = ?" +
						"WHERE idCanzone = ?");
			update.setString(1, (String)params[2]);
			update.setString(2, (String)params[3]);
			update.setInt(3, (Integer)params[4]);
			update.setString(1, canzone.getId());
			ResultSet resultSet = update.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean delete(Canzone canzone)
	{
		try
		{
			PreparedStatement delete = serverSLQ.prepareStatement(
					"DELETE FROM canzoni WHERE idCanzone = ?");
			delete.setString(1, canzone.getId());
			ResultSet resultSet = delete.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}
