package emotionalsongs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe SQLDB gestisce la connessione al database PostgreSQL utilizzato nell'applicazione.
 * Offre la possibilità di avere una connessione condivisa tra diverse istanze della classe
 * per ottimizzare l'efficienza e risparmiare risorse di sistema.
 *
 * Una connessione condivisa è utile per evitare l'apertura e la chiusura ripetuta di connessioni
 * al database, garantendo che più istanze di SQLDB possano condividere la stessa connessione.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class SQLDB
{
	private static final Logger LOGGER = Logger.getLogger(SQLDB.class.getName());
	private Connection serverSQL;
	private static Connection sharedSQLserver;

	/**
	 * Costruttore che inizializza una nuova connessione al database.
	 *
	 * Il parametro "sharedServer" nel costruttore controlla se la connessione deve essere condivisa o meno.
	 * Se è condivisa, la connessione viene assegnata alla variabile statica "sharedSQLserver".
	 * Se non è condivisa, ogni istanza di SQLDB avrà la sua connessione separata assegnata alla variabile non statica "serverSQL".
	 *
	 * @param url       URL del database
	 * @param user      Nome utente per l'accesso al database
	 * @param password  Password per l'accesso al database
	 * @param sharedServer  True se si desidera condividere la connessione condivisa
	 *
	 * @throws SQLException se si verifica un errore durante l'apertura della connessione al database
	 */
	public SQLDB(String url, String user, String password, boolean sharedServer)
	{
		Connection c = null;
		try
		{
			// Crea una nuova connessione al database con i parametri specificati.
			c = DriverManager.getConnection(url, user, password);
		} catch (SQLException e)
		{
			LOGGER.warning("Errore nell'apertura del db in fase di creazione oggetto SQLDB.");
		}

		// Se sharedServer è true, assegna la connessione alla variabile condivisa sharedSQLserver,
		// altrimenti assegna la connessione alla variabile di istanza this.serverSQL.
		if(sharedServer)
		{
			sharedSQLserver = c;
		}
		else
		{
			this.serverSQL = c;
		}
	}

	/**
	 * Costruttore che crea un oggetto SQLDB con una connessione condivisa al database.
	 *
	 * @throws SQLException se si verifica un errore durante l'apertura della connessione al database
	 */
	// Questo costruttore dovrebbe essere chiamato solo dopo aver utilizzato l'altro costruttore con sharedServer=true.
	public SQLDB()
	{
		// Se esiste una connessione condivisa (sharedSQLserver), cerca di crearne una nuova con valori predefiniti.
		if(sharedSQLserver != null)
		{
			try
			{
				sharedSQLserver = DriverManager.getConnection("localhost", "postgres", "admin");
			} catch (SQLException e)
			{
				LOGGER.warning("Errore nell'apertura del db in fase di creazione oggetto SQLDB.");
			}
		}
	}

	/**
	 * Restituisce la connessione al server SQL associata a questa istanza.
	 *
	 * @return Connessione al server SQL
	 */
	public Connection getServerSQL()
	{
		return serverSQL;
	}

	/**
	 * Imposta la connessione al server SQL per questa istanza.
	 *
	 * @param serverSQL Nuova connessione al server SQL
	 */
	public void setServerSQL(Connection serverSQL)
	{
		this.serverSQL = serverSQL;
	}

	/**
	 * Restituisce la connessione condivisa al server SQL utilizzata da tutte le istanze.
	 *
	 * @return Connessione condivisa al server SQL
	 */
	public static Connection getSharedSQLserver()
	{
		return sharedSQLserver;
	}

	/**
	 * Imposta la connessione condivisa al server SQL utilizzata da tutte le istanze.
	 *
	 * @param sharedSQLserver Nuova connessione condivisa al server SQL
	 */
	public static void setSharedSQLserver(Connection sharedSQLserver)
	{
		SQLDB.sharedSQLserver = sharedSQLserver;
	}
}
