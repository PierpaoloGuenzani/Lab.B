package emotionalsongs;

import common.Emozione;
import common.Percezione;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PerceptionSQLDB implements PerceptionDAOInterface
{
	private final static String SEPARATORE = "<>";
	private Connection serverSQL;
	
	public PerceptionSQLDB(Connection serverSQL)
	{
		this.serverSQL = serverSQL;
	}

	public List<Percezione> get(String idCanzone, String idUtente)
	{
		LinkedList<Percezione> lista = new LinkedList<>();
		if(idCanzone == null || idUtente == null)
			return lista;
		try
		{
			PreparedStatement select = serverSQL.prepareStatement(
					"SELECT * FROM emozioni" +
							"WHERE idCanzone = ?" +
							"AND idUtente = ?");
			select.setString(1, idCanzone);
			select.setString(2, idUtente);
			ResultSet resultSet = select.executeQuery();
			while(resultSet.next())
			{
				Percezione p = new Percezione(
						Emozione.values()[resultSet.getShort("idEmozione")],
						resultSet.getInt("score"),
						resultSet.getString("idCanzone"),
						resultSet.getString("idUtente"));
				lista.add(p);
			}
		} catch (SQLException e) {} //LOG?
		return lista;
	}

	@Override
	public ConcurrentHashMap<String, List<Percezione>> getAll()
	{
		ConcurrentHashMap<String, List<Percezione>> albero = new ConcurrentHashMap<>();
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
				List<Percezione> lista;
				//se albero contiene già lista allora aggiungo altrimenti creo la lista
				if(albero.containsKey(p.getSongId()))
				{
					lista = albero.get(p.getSongId());
				}
				else
				{
					lista = new LinkedList<>();
					albero.put(p.getSongId(), lista);
				}
				lista.add(p);
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