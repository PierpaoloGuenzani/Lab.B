package emotionalsongs.DAO;

import common.Canzone;

import java.sql.*;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Implementa l'interfaccia Dao<Canzone>, utilizza un approccio di accesso ai dati tramite JDBC e si connette a un database SQL.
 * Utilizza PreparedStatement per eseguire query parametriche in modo sicuro, garantendo la corretta separazione tra i comandi SQL e
 * i dati di input degli utenti e contribuendo a prevenire attacchi SQL injection.
 *
 * Fornisce metodi per recuperare, salvare, aggiornare ed eliminare canzoni dal database.
 * L'obiettivo è separare la logica di accesso ai dati dalla logica di business dell'applicazione,
 * garantendo un'astrazione per l'accesso alle canzoni.
 *
 * Le eventuali eccezioni di tipo SQLException sono gestite internamente e registrate nei log. I log, o file di registro, costituiscono
 * uno strumento per tracciare e analizzare le attività del programma. In presenza di eccezioni, la classe SongSQLDB attua procedure
 * specifiche di gestione degli errori e annota informazioni insieme allo stack trace nei log.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 *
 */
public class SongSQLDB implements Dao<Canzone>
{
	private static final Logger LOGGER = Logger.getLogger(SongSQLDB.class.getName());
	private Connection serverSLQ;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;


	/**
	 *  Costruisce un oggetto SongSQLDB utilizzando una connessione SQL fornita.
	 *
	 * @param serverSLQ Connessione al database SQL.
	 * @throws SQLException se si verifica un errore durante l'inizializzazione delle PreparedStatement.
	 */
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

	/**
	 * Recupera una singola canzone dal database in base all'ID fornito.
	 *
	 * @param id ID della canzone da recuperare.
	 * @return Un Optional contenente la canzone se presente, altrimenti vuoto.
	 */
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
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di una canzone.");
		}
		return Optional.empty();
	}

	/**
	 * Recupera tutte le canzoni dal database.
	 *
	 * @return Una ConcurrentHashMap contenente le canzoni, dove le chiavi sono gli ID delle canzoni.
	 */
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
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di tutte le canzoni.");
		}
		return albero;
	}

	/**
	 * Salva una nuova canzone nel database.
	 *
	 * @param canzone Canzone da salvare.
	 * @return True se l'operazione di salvataggio è riuscita, altrimenti false.
	 */
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
			LOGGER.warning("Errore nell'apertura del db in fase di salvataggio di una nuova canzone.");
			return false;
		}
		return true;
	}

	/**
	 * Aggiorna una canzone esistente nel database.
	 *
	 * @param canzone Canzone da aggiornare.
	 * @param params  Parametri per l'aggiornamento.
	 * @return True se l'operazione di aggiornamento è riuscita, altrimenti false.
	 */
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
			LOGGER.warning("Errore nell'apertura del db in fase di aggiornamento campi di una canzone.");
			return false;
		}
		return true;
	}

	/**
	 * Elimina una canzone dal database.
	 *
	 * @param canzone Canzone da eliminare.
	 * @return True se l'operazione di eliminazione è riuscita, altrimenti false.
	 */
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
			LOGGER.warning("Errore nell'apertura del db in fase di eliminazione di una canzone.");
			return false;
		}
		return true;
	}
}
