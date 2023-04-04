package emotionalsongs;

import common.Percezione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.TreeMap;

public class PerceptionSQLDB implements Dao<Percezione>
{
	private Connection serverSQL;
	
	public PerceptionSQLDB(Connection serverSQL)
	{
		this.serverSQL = serverSQL;
	}
	
	//id composto quindi separo stringa in più
	@Override
	public Optional<Percezione> get(String id)
	{
		if(!id.contains("<>"))
			return Optional.empty();
		
		try
		{
			PreparedStatement select = serverSQL.prepareStatement(
					"SELECT * FROM emozioni" +
						"WHERE idpersona = ?" +
						"AND idpersona = codicefiscale");
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
	public TreeMap<String, Percezione> getAll()
	{
		return null;
	}
	
	@Override
	public boolean save(Percezione percezione)
	{
		return false;
	}
	
	@Override
	public boolean update(Percezione percezione, Object[] params)
	{
		return false;
	}
	
	@Override
	public boolean delete(Percezione percezione)
	{
		return false;
	}
}
