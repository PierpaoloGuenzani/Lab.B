package emotionalsongs;

import common.UtenteRegistrato;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gestisce la registrazione e l'accesso degli utenti all'applicazione.
 */
public class Persone
{
	//private static final String dbFile = "data/UtentiRegistrati.dati.txt";
	private Dao<UtenteRegistrato> db;
	private ConcurrentHashMap<String,UtenteRegistrato> mappa;
	private static Persone instance;

	/**
	 * Crea un oggetto per la gestione degli utenti registrati nel database.
	 *
	 * @throws IOException se si verifica un errore di Input/Output relativo al database
	 */
	private Persone() throws IOException {}

	/**
	 * Ottiene l'istanza singleton della classe `Persone`.
	 *
	 * @return un'istanza di `Persone`
	 * @throws IOException se si verifica un errore di Input/Output relativo al database
	 */
	public static Persone getInstance() throws IOException
	{
		if(instance == null)
			instance = new Persone();
		return instance;
	}

	/**
	 * Restituisce il database contenente tutti gli utenti registrati.
	 *
	 * @return un'interfaccia del database
	 */
	public Dao<UtenteRegistrato> getDB()
	{
		return db;
	}


	/**
	 * Imposta un database per la gestione degli utenti registrati.
	 *
	 * @param DB l'implementazione dell'interfaccia DB da assegnare
	 */
	public void setDB(Dao<UtenteRegistrato> DB)
	{
		this.db = DB;
		update();
	}

	/**
	 * Effettua la registrazione di un nuovo utente all'applicazione.
	 *
	 * @param newUtenteRegistrato l'utente da registrare
	 * @return true se la registrazione è andata a buon fine, false altrimenti
	 * @throws IOException se si verifica un errore di Input/Output relativo al database
	 */
	public boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException
	{
		if(mappa.containsKey(newUtenteRegistrato.getUserId()))
		{
			return false;
		}
		mappa.put(newUtenteRegistrato.getUserId(), newUtenteRegistrato);
		db.save(newUtenteRegistrato);
		return true;
	}

	/**
	 * Permette a un utente di accedere all'applicazione con le credenziali inserite in fase di registrazione.
	 *
	 * @param userId   l'userId scelto in fase di registrazione
	 * @param password la password scelta in fase di registrazione
	 * @return true se l'accesso è andato a buon fine, false altrimenti
	 */
	public boolean accedi(String userId, String password)
	{
		if(mappa.containsKey(userId))
		{
			if(mappa.get(userId).getPassword().equals(password))
			{
				Authentication.setAuthentication(mappa.get(userId));
				return true;
			}
		}
		return false;  //accesso non riuscito
	}

	/**
	 * Controlla se un userId è già in uso.
	 *
	 * @param userId l'ID dell'utente
	 * @return true se è già presente, false altrimenti
	 */
	public boolean contains(String userId)
	{
		return mappa.containsKey(userId);
	}

	/**
	 * Aggiorna la mappa degli utenti registrati con i dati dal database.
	 *
	 * @return true se l'aggiornamento è avvenuto con successo, false altrimenti
	 */
	public boolean update()
	{
		if(db == null)
			return false;
		mappa = db.getAll();
		return true;
	}
}
