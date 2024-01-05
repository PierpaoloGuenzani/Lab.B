package emotionalsongs;

import common.UtenteRegistrato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class UserSQLDB implements Dao<UtenteRegistrato>
{
	private Connection serverSQL;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	
	
	public UserSQLDB(Connection serverSQL) throws SQLException
	{
		this.serverSQL = serverSQL;
		synchronized (this.serverSQL)
		{
			select = serverSQL.prepareStatement("SELECT * FROM utentiRegistrati WHERE userid = ?");
			selectAll = serverSQL.prepareStatement("SELECT * FROM utentiRegistrati");
			insert = serverSQL.prepareStatement("INSERT INTO utentiRegistrati(" +
					"userid, password, email, codiceFiscale, nome, cognome, indirizzo)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?)");
			update = serverSQL.prepareStatement("UPDATE utentiRegistrati SET " +
					"password = ?, email = ?, codiceFiscale = ?, nome = ?, cognome = ?, indirizzo = ?" +
					"WHERE userid = ?");
			delete = serverSQL.prepareStatement("DELETE FROM utentiRegistrati WHERE userid = ?");
		}
	}
	
	@Override
	public Optional<UtenteRegistrato> get(String id)
	{
		if(id == null | id.isEmpty())
			return Optional.empty();
		try
		{
			ResultSet resultSet;
			synchronized (serverSQL)
			{
				select.setString(1, id);
				resultSet = select.executeQuery();
			}
			if (resultSet.next())
			{
				UtenteRegistrato u = new UtenteRegistrato(
						resultSet.getString("nome"),
						resultSet.getString("cognome"),
						resultSet.getString("codicefiscale"),
						resultSet.getString("città") +
						resultSet.getString("via") +
						resultSet.getInt("numerocivico"),
						resultSet.getString("email"),
						resultSet.getString("password"),
						resultSet.getString("userid")
				);
				return Optional.of(u);
			}
		} catch (SQLException e) {} //LOG?
		return Optional.empty();
	}
	
	@Override
	public ConcurrentHashMap<String, UtenteRegistrato> getAll()
	{
		ConcurrentHashMap<String , UtenteRegistrato> albero = new ConcurrentHashMap<String , UtenteRegistrato>();
		try
		{
			ResultSet resultSet;
			synchronized (serverSQL)
			{
				resultSet = selectAll.executeQuery();
			}
			while(resultSet.next())
			{
				UtenteRegistrato u = new UtenteRegistrato(
						resultSet.getString("nome"),
						resultSet.getString("cognome"),
						resultSet.getString("codicefiscale"),
						resultSet.getString("indirizzo"),
						resultSet.getString("email"),
						resultSet.getString("password"),
						resultSet.getString("userid")
				);
				albero.put(u.getUserId(), u);
			}
		} catch (SQLException e) {} //LOG?
		return albero;
	}
	
	@Override
	public boolean save(UtenteRegistrato utenteRegistrato)
	{
		if(utenteRegistrato == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				insert.setString(1, utenteRegistrato.getUserId());
				insert.setString(2, utenteRegistrato.getPassword());
				insert.setString(3, utenteRegistrato.getEmail());
				insert.setString(4, utenteRegistrato.getCodiceFiscale());
				insert.setString(5, utenteRegistrato.getNome());
				insert.setString(6, utenteRegistrato.getCognome());
				insert.setString(7, utenteRegistrato.getIndirizzoFisico());
				insert.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean update(UtenteRegistrato utenteRegistrato, Object[] params)
	{
		if(utenteRegistrato == null || params == null)
			return false;
		if(params.length != utenteRegistrato.getClass().getDeclaredFields().length)
			return false;
		if(!utenteRegistrato.getUserId().equals((String)params[0]))
			return false;
		try
		{
			synchronized (serverSQL)
			{
				update.setString(1, utenteRegistrato.getPassword());
				update.setString(2, utenteRegistrato.getEmail());
				update.setString(3, utenteRegistrato.getCodiceFiscale());
				update.setString(4, utenteRegistrato.getNome());
				update.setString(5, utenteRegistrato.getCognome());
				update.setString(6, utenteRegistrato.getIndirizzoFisico());
				update.setString(7, utenteRegistrato.getUserId());
				update.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean delete(UtenteRegistrato utenteRegistrato)
	{
		if(utenteRegistrato == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				delete.setString(1, utenteRegistrato.getUserId());
				delete.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}
