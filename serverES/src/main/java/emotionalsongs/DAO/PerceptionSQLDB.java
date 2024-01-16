package emotionalsongs.DAO;

import common.Emozione;
import common.Percezione;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Implementa l'interfaccia PerceptionDAOInterface, utilizza un approccio di accesso ai dati tramite JDBC e si connette a un database SQL.
 * Utilizza PreparedStatement per eseguire query parametriche in modo sicuro, garantendo la corretta separazione tra i comandi SQL e
 * i dati di input degli utenti e contribuendo a prevenire attacchi SQL injection.
 *
 * Fornisce metodi per recuperare, salvare, aggiornare ed eliminare le percezioni delle emozioni delle canzoni associate agli utenti dal database.
 * L'obiettivo è separare la logica di accesso ai dati dalla logica di business dell'applicazione,
 * garantendo un'astrazione per l'accesso alle percezioni.
 *
 * Le eventuali eccezioni di tipo SQLException sono gestite internamente e registrate nei log. I log, o file di registro, costituiscono
 * uno strumento per tracciare e analizzare le attività del programma. In presenza di eccezioni, la classe PerceptionSQLDB attua procedure
 * specifiche di gestione degli errori e annota informazioni insieme allo stack trace nei log.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 *
 */
public class PerceptionSQLDB implements PerceptionDAOInterface
{
	private final static String SEPARATORE = "<>";
	private static final Logger LOGGER = Logger.getLogger(PerceptionSQLDB.class.getName());
	private Connection serverSQL;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;

	/**
	 * Costruisce un oggetto PerceptionSQLDB utilizzando una connessione SQL fornita.
	 *
	 * @param serverSQL Connessione al database SQL.
	 * @throws SQLException se si verifica un errore durante l'inizializzazione delle PreparedStatement.
	 */
	public PerceptionSQLDB(Connection serverSQL) throws SQLException
	{
		this.serverSQL = serverSQL;
		synchronized (serverSQL)
		{
			select = serverSQL.prepareStatement("SELECT * FROM emozioni" +
					"WHERE idCanzone = ? AND idUtente = ?");
			selectAll = serverSQL.prepareStatement("SELECT * FROM emozioni");
			insert = serverSQL.prepareStatement("INSERT INTO emozioni(idCanzone, idUtente, idEmozione, score, note)" +
					"VALUES (?, ?, ?, ?, ?)");
			update = serverSQL.prepareStatement("UPDATE emozioni SET score = ?, note = ? " +
					"WHERE idCanzone = ? AND idUtente = ? AND idEmozione = ?");
			delete = serverSQL.prepareStatement("DELETE FROM utentiRegistrati WHERE idUtente = ?" +
					"AND idCanzone = ? AND idEmozione = ?");
		}
	}

	/**
	 * Recupera le percezioni delle emozioni associate a una canzone e un utente specifici dal database.
	 *
	 * @param idCanzone ID della canzone.
	 * @param idUtente ID dell'utente.
	 * @return Una lista di percezioni delle emozioni associate alla canzone e all'utente specificati.
	 */
	public List<Percezione> get(String idCanzone, String idUtente)
	{
		LinkedList<Percezione> lista = new LinkedList<>();
		if(idCanzone == null || idUtente == null)
			return lista;
		try
		{
			ResultSet resultSet;
			synchronized (serverSQL)
			{
				select.setString(1, idCanzone);
				select.setString(2, idUtente);
				resultSet = select.executeQuery();
			}
			while(resultSet.next())
			{
				Percezione p = new Percezione(
						Emozione.values()[resultSet.getShort("idEmozione")],
						resultSet.getInt("score"),
						resultSet.getString("idCanzone"),
						resultSet.getString("idUtente"));
				lista.add(p);
			}
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di una lista di percezioni.");
		}
		return lista;
	}

	/**
	 * Recupera tutte le percezioni delle emozioni dal database.
	 *
	 * @return Una ConcurrentHashMap contenente le percezioni delle emozioni, dove le chiavi sono gli ID delle canzoni.
	 */
	@Override
	public ConcurrentHashMap<String, List<Percezione>> getAll()
	{
		ConcurrentHashMap<String, List<Percezione>> albero = new ConcurrentHashMap<>();
		try
		{
			ResultSet resultSet;
			synchronized (serverSQL)
			{
				resultSet = selectAll.executeQuery();
			}
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
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di tutte le percezioni.");
		}
		return albero;
	}

	/**
	 * Salva una nuova percezione delle emozioni nel database.
	 *
	 * @param percezione Percezione delle emozioni da salvare.
	 * @return True se l'operazione di salvataggio è riuscita, altrimenti false.
	 */
	@Override
	public boolean save(Percezione percezione)
	{
		if(percezione == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				insert.setString(1, percezione.getSongId());
				insert.setString(2, percezione.getUserId());
				insert.setShort(3, (short) percezione.getEmozione().ordinal());
				insert.setInt(4, percezione.getScore());
				String note = percezione.getNote();
				if (note == null) insert.setNull(5, Types.VARCHAR);
				else insert.setString(5, note);
				insert.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di salvataggio di una nuova percezione.");
			return false;
		}
		return true;
	}

	/**
	 * Aggiorna una percezione delle emozioni esistente nel database.
	 *
	 * @param percezione Percezione delle emozioni da aggiornare.
	 * @param params  Parametri per l'aggiornamento.
	 * @return True se l'operazione di aggiornamento è riuscita, altrimenti false.
	 */
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
			synchronized (serverSQL)
			{
				update.setInt(1, percezione.getScore());
				String note = percezione.getNote();
				if (note == null) update.setNull(2, Types.VARCHAR);
				else update.setString(2, note);
				update.setString(3, percezione.getSongId());
				update.setString(4, percezione.getUserId());
				update.setShort(5, (short) percezione.getEmozione().ordinal());
				update.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di aggiornamento di una percezione.");
			return false;
		}
		return true;
	}

	/**
	 * Elimina una percezione delle emozioni dal database.
	 *
	 * @param percezione Percezione delle emozioni da eliminare.
	 * @return True se l'operazione di eliminazione è riuscita, altrimenti false.
	 */
	@Override
	public boolean delete(Percezione percezione)
	{
		if(percezione == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				delete.setString(1, percezione.getUserId());
				delete.setString(2, percezione.getSongId());
				delete.setShort(3, (short) percezione.getEmozione().ordinal());
				delete.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di eliminazione di una percezione.");
			return false;
		}
		return true;
	}
}