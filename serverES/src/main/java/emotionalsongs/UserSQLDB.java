package emotionalsongs;

import common.UtenteRegistrato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.TreeMap;

public class UserSQLDB implements Dao<UtenteRegistrato>
{
	private Connection serverSQL;
	
	public UserSQLDB(Connection serverSQL)
	{
		this.serverSQL = serverSQL;
	}
	
	@Override
	public Optional<UtenteRegistrato> get(String id)
	{
		try
		{
			PreparedStatement select = serverSQL.prepareStatement(
					"SELECT * FROM utentiRegistrati, persone" +
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
	public TreeMap<String, UtenteRegistrato> getAll()
	{
		TreeMap<String , UtenteRegistrato> albero = new TreeMap<>();
		try
		{
			PreparedStatement selectAll = serverSQL.prepareStatement(
					"SELECT * FROM utentiRegistrati");
			ResultSet resultSet = selectAll.executeQuery();
			while(resultSet.next())
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
				albero.put(u.getUserId(), u);
			}
		} catch (SQLException e) {} //LOG?
		return albero;
	}
	
	@Override
	public boolean save(UtenteRegistrato utenteRegistrato)
	{
		try
		{
			PreparedStatement insert = serverSQL.prepareStatement(
					"INSERT INTO utentiRegistrati(" +
						"userid, password, email, codiceFiscale, nome, cognome, indirizzo)" +
						"VALUES (?, ?, ?, ?, ?, ?, ?)");
			insert.setString(1, utenteRegistrato.getUserId());
			insert.setString(2, utenteRegistrato.getPassword());
			insert.setString(3, utenteRegistrato.getEmail());
			insert.setString(4, utenteRegistrato.getCodiceFiscale());
			insert.setString(5, utenteRegistrato.getNome());
			insert.setString(6, utenteRegistrato.getCognome());
			insert.setString(7, utenteRegistrato.getIndirizzoFisico());
			ResultSet resultSet = insert.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean update(UtenteRegistrato utenteRegistrato, Object[] params)
	{
		if(params.length != utenteRegistrato.getClass().getDeclaredFields().length)
			return false;
		if(!utenteRegistrato.getUserId().equals((String)params[0]))
			return false;
		try
		{
			PreparedStatement update = serverSQL.prepareStatement("UPDATE utentiRegistrati SET " +
					"password = ?, email = ?, codiceFiscale = ?, nome = ?, cognome = ?, indirizzo = ?" +
					"WHERE userid = ?");
			update.setString(1, utenteRegistrato.getPassword());
			update.setString(2, utenteRegistrato.getEmail());
			update.setString(3, utenteRegistrato.getCodiceFiscale());
			update.setString(4, utenteRegistrato.getNome());
			update.setString(5, utenteRegistrato.getCognome());
			update.setString(6, utenteRegistrato.getIndirizzoFisico());
			update.setString(7, utenteRegistrato.getUserId());
			ResultSet resultSet = update.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean delete(UtenteRegistrato utenteRegistrato)
	{
		try
		{
			PreparedStatement delete = serverSQL.prepareStatement(
					"DELETE FROM utentiRegistrati WHERE userid = ?");
			delete.setString(1, utenteRegistrato.getUserId());
			ResultSet resultSet = delete.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}
