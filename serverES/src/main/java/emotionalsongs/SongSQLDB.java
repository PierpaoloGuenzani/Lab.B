package emotionalsongs;

import common.Canzone;

import java.sql.*;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class SongSQLDB implements Dao<Canzone>
{
	private Connection serverSLQ;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	
	public SongSQLDB(Connection serverSLQ) throws SQLException
	{
		this.serverSLQ = serverSLQ;
		synchronized (serverSLQ)
		{
			select = serverSLQ.prepareStatement("SELECT * FROM canzoni WHERE idCanzone = ?");
			selectAll = serverSLQ.prepareStatement("SELECT * FROM canzoni");
			insert = serverSLQ.prepareStatement(
					"INSERT INTO canzoni(idCanzone, titolo, produttore, anno)" +
						"VALUES (?, ?, ?, ?)");
			update = serverSLQ.prepareStatement("UPDATE canzoni SET titolo = ?, produttore = ?, anno = ?" +
							"WHERE idCanzone = ?");
			delete = serverSLQ.prepareStatement("DELETE FROM canzoni WHERE idCanzone = ?");
		}
	}
	
	@Override
	public Optional<Canzone> get(String id)
	{
		if(id == null | id.length() < 18)
			return Optional.empty();
		try
		{
			ResultSet resultSet;
			synchronized (serverSLQ)
			{
				select.setString(1, id);
				resultSet = select.executeQuery();
			}
			if (resultSet.next())
			{
				Canzone c = new Canzone(
						resultSet.getString("idCanzone"),
						resultSet.getString("titolo"),
						resultSet.getString("produttore"),
						resultSet.getInt("anno")
				);
				return Optional.of(c);
			}
		} catch (SQLException e) {} //LOG?
		return Optional.empty();
	}
	
	@Override
	public ConcurrentHashMap<String, Canzone> getAll()
	{
		ConcurrentHashMap<String , Canzone> albero = new ConcurrentHashMap<String , Canzone>(524288); //pari a 2^19 dato che log2(n.canzoni) = 19
		try
		{
			ResultSet resultSet;
			synchronized (serverSLQ)
			{
				resultSet = selectAll.executeQuery();
			}
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
		if(canzone == null)
			return false;
		try
		{
			synchronized (serverSLQ)
			{
				insert.setString(1, canzone.getId());
				insert.setString(2, canzone.getTitolo());
				insert.setString(3, canzone.getArtista());
				insert.setInt(4, canzone.getAnno());
				insert.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean update(Canzone canzone, Object[] params)
	{
		if(canzone == null || params == null)
			return false;
		if(params.length != canzone.getClass().getDeclaredFields().length)
			return false;
		if(!canzone.getId().equals((String)params[0]))
			return false;
		try
		{
			synchronized (serverSLQ)
			{
				update.setString(1, (String) params[2]);
				update.setString(2, (String) params[3]);
				update.setInt(3, (Integer) params[4]);
				update.setString(4, canzone.getId());
				update.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean delete(Canzone canzone)
	{
		if(canzone == null)
			return false;
		try
		{
			synchronized (serverSLQ)
			{
				delete.setString(1, canzone.getId());
				delete.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}
