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
			PreparedStatement select = serverSLQ.prepareStatement("SELECT * FROM canzoni WHERE idCanzone = ?");
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
			PreparedStatement select = serverSLQ.prepareStatement("SELECT * FROM canzoni");
			ResultSet resultSet = select.executeQuery();
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
	public void save(Canzone canzone)
	{
	
	}
	
	@Override
	public void update(Canzone canzone, Object[] params)
	{
	
	}
	
	@Override
	public void delete(Canzone canzone)
	{
	}
}
