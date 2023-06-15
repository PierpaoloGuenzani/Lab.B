package emotionalsongs;

import common.Emozione;
import common.Percezione;

import java.sql.*;
import java.util.Optional;
import java.util.TreeMap;

public class PerceptionSQLDB implements Dao<Percezione>
{
	private final static String SEPARATORE = "<>";
	private Connection serverSQL;
	
	public PerceptionSQLDB(Connection serverSQL)
	{
		this.serverSQL = serverSQL;
	}
	
	//id composto quindi separo stringa in più stringhe
	@Override
	public Optional<Percezione> get(String id)
	{
		if(id == null | id.isEmpty())
			return Optional.empty();
		if(!id.contains(SEPARATORE))
			return Optional.empty();
		String[] ids = id.split("<>");
		return get(ids[0], ids[1], ids[2]);
	}

	public Optional<Percezione> get(String idCanzone, String idUtente, String emozione)
	{
		if(idCanzone == null || idUtente == null || emozione == null)
			return Optional.empty();
		if(idCanzone.length() < 18)
			return Optional.empty();
		try
		{
			PreparedStatement select = serverSQL.prepareStatement(
					"SELECT * FROM emozioni" +
							"WHERE idCanzone = ?" +
							"AND idUtente = ? AND idEmozione = ?");
			select.setString(1, idCanzone);
			select.setString(2, idUtente);
			select.setShort(3, Short.parseShort(emozione));
			ResultSet resultSet = select.executeQuery();
			if (resultSet.next())
			{
				return Optional.of(
						new Percezione(
						Emozione.values()[resultSet.getShort("idEmozione")],
						resultSet.getInt("score"),
						resultSet.getString("idCanzone"),
						resultSet.getString("idUtente")
						));
			}
		} catch (SQLException e) {} //LOG?
		return Optional.empty();
	}

	@Override
	public TreeMap<String, Percezione> getAll()
	{
		//TODO IOException a TUTTI i metodi delle classi SQL
		TreeMap<String, Percezione> albero = new TreeMap<>();
		try
		{
			PreparedStatement selectAll = serverSQL.prepareStatement(
					"SELECT * FROM emozioni");
			ResultSet resultSet = selectAll.executeQuery();
			while(resultSet.next())
			{
				short index = resultSet.getShort("idEmozione");
				Percezione p = new Percezione(
						Emozione.values()[index],
						resultSet.getInt("score"),
						resultSet.getString("idCanzone"),
						resultSet.getString("idUtente")
				);
				String note = resultSet.getString("note");
				if(note != null)
					p.aggiungiNote(note);
				String key = p.getSongId() + SEPARATORE + p.getUserId()
						   + SEPARATORE + p.getEmozione().ordinal();
				albero.put(key, p);
			}
		} catch (SQLException e) {} //LOG?
		return albero;
	}
	
	@Override
	public boolean save(Percezione percezione)
	{
		if(percezione == null)
			return false;
		try
		{
			PreparedStatement insert = serverSQL.prepareStatement(
					"INSERT INTO emozioni(" +
						"idCanzone, idUtente, idEmozione, score, note)" +
						"VALUES (?, ?, ?, ?, ?)");
			insert.setString(1, percezione.getSongId());
			insert.setString(2, percezione.getUserId());
			insert.setShort(3, (short)percezione.getEmozione().ordinal());
			insert.setInt(4, percezione.getScore());
			String note = percezione.getNote();
			if(note == null)
				insert.setNull(5, Types.VARCHAR);
			else
				insert.setString(5, note);
			ResultSet resultSet = insert.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean update(Percezione percezione, Object[] params)
	{
		if(percezione == null || params == null)
			return false;
		if(params.length != percezione.getClass().getDeclaredFields().length)
			return false;
		if(!percezione.getUserId().equals((String)params[0]))
			return false;
		try
		{
			PreparedStatement update = serverSQL.prepareStatement(
					"UPDATE emozioni SET score = ?, note = ? " +
						"WHERE idCanzone = ? AND idUtente = ? AND idEmozione = ?");
			update.setInt(1, percezione.getScore());
			String note = percezione.getNote();
			if(note == null)
				update.setNull(2, Types.VARCHAR);
			else
				update.setString(2, note);
			update.setString(3, percezione.getSongId());
			update.setString(4, percezione.getUserId());
			update.setShort(5, (short)percezione.getEmozione().ordinal());
			ResultSet resultSet = update.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
	
	@Override
	public boolean delete(Percezione percezione)
	{
		if(percezione == null)
			return false;
		try
		{
			PreparedStatement delete = serverSQL.prepareStatement(
					"DELETE FROM utentiRegistrati WHERE idUtente = ?" +
						"AND idCanzone = ? AND idEmozione = ?");
			delete.setString(1, percezione.getUserId());
			delete.setString(2, percezione.getSongId());
			delete.setShort(3, (short)percezione.getEmozione().ordinal());
			ResultSet resultSet = delete.executeQuery();
		} catch (SQLException e) {
			return false;
		} //LOG?
		return true;
	}
}