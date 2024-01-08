package emotionalsongs;

import common.UtenteRegistrato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Implementa l'interfaccia Dao<UtenteRegistrato>, utilizza un approccio di accesso ai dati tramite JDBC e si connette a un database SQL.
 * Utilizza PreparedStatement per eseguire query parametriche in modo sicuro, garantendo la corretta separazione tra i comandi SQL e
 * i dati di input degli utenti e contribuendo a prevenire attacchi SQL injection.
 *
 * Fornisce metodi per recuperare, salvare, aggiornare ed eliminare utenti registrati dal database.
 * L'obiettivo è separare la logica di accesso ai dati dalla logica di business dell'applicazione,
 * garantendo un'astrazione per l'accesso agli utenti registrati.
 *
 * Le eventuali eccezioni di tipo SQLException sono gestite internamente e registrate nei log. I log, o file di registro, costituiscono
 * uno strumento per tracciare e analizzare le attività del programma. In presenza di eccezioni, la classe UserSQLDB attua procedure
 * specifiche di gestione degli errori e annota informazioni insieme allo stack trace nei log.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 *
 */
public class UserSQLDB implements Dao<UtenteRegistrato>
{
	private static final Logger LOGGER = Logger.getLogger(UserSQLDB.class.getName());
	private Connection serverSQL;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;

	/**
	 * Costruisce un oggetto UserSQLDB utilizzando una connessione SQL fornita.
	 *
	 * @param serverSQL Connessione al database SQL.
	 * @throws SQLException se si verifica un errore durante l'inizializzazione delle PreparedStatement.
	 */
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

	/**
	 * Recupera un singolo utente registrato dal database in base all'ID fornito.
	 *
	 * @param id ID dell'utente registrato da recuperare.
	 * @return Un Optional contenente l'utente registrato se presente, altrimenti vuoto.
	 */
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
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di un utente.");
		}
		return Optional.empty();
	}

	/**
	 * Recupera tutti gli utenti registrati dal database.
	 *
	 * @return Una ConcurrentHashMap contenente gli utenti registrati, dove le chiavi sono gli ID degli utenti.
	 */
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
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di tutti gli utenti.");
		}
		return albero;
	}

	/**
	 * Salva un nuovo utente registrato nel database.
	 *
	 * @param utenteRegistrato Utente registrato da salvare.
	 * @return True se l'operazione di salvataggio è riuscita, altrimenti false.
	 */
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
			LOGGER.warning("Errore nell'apertura del db in fase di salvataggio di un nuovo utente.");
			return false;
		}
		return true;
	}

	/**
	 * Aggiorna un utente registrato esistente nel database.
	 *
	 * @param utenteRegistrato Utente registrato da aggiornare.
	 * @param params  Parametri per l'aggiornamento.
	 * @return True se l'operazione di aggiornamento è riuscita, altrimenti false.
	 */
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
			LOGGER.warning("Errore nell'apertura del db in fase di aggiornamento dati di un utente");
			return false;
		}
		return true;
	}

	/**
	 * Elimina un utente registrato dal database.
	 *
	 * @param utenteRegistrato Utente registrato da eliminare.
	 * @return True se l'operazione di eliminazione è riuscita, altrimenti false.
	 */
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
			LOGGER.warning("Errore nell'apertura del db in fase di eliminazione di un utente.");
			return false;
		}
		return true;
	}
}
