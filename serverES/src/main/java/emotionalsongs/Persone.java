package emotionalsongs;

import common.UtenteRegistrato;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Permette di controllare la situazione degli utenti gia' registrati.
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 * @author Paradiso Fabiola 749727 VA
 * @author Cuvato Paolo 748691 VA
 */
public class Persone
{
	private static final String dbFile = "data/UtentiRegistrati.dati.txt";
	private Dao<UtenteRegistrato> db;
	private TreeMap<String,UtenteRegistrato> mappa;

	//costruttore
	/**
	 * Crea un oggetto che raccoglie i dati degli utenti presenti nel database e ne permette la gestione.
	 * @throws IOException se si verifica un errore di Input/Output relativo al database
	 */
	public Persone() throws IOException
	{}
	
	/**
	 * Restituisce il database che contiene tutti gli utenti registrati.
	 * @return un'interfaccia del database
	 */
	public Dao<UtenteRegistrato> getDB()
	{
		return db;
	}
	
	
	/**
	 * Assegna un database.
	 * @param DB l'implementazione dell'interfaccia DB da assegnare
	 */
	public void setDB(Dao<UtenteRegistrato> DB)
	{
		this.db = DB;
		update();
	}
	
	//metodi
	/**
	 * Effettua la registrazione di un nuovo utente all'applicazione.
	 * @param newUtenteRegistrato la UtenteRegistrato da registrare
	 * @return true se la registrazione e' andata a buon fine, false altrimenti
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
	 * Permette ad un utente di accedere all'applicazione con i dati inseriti in fase di registrazione.
	 * @param userId l'userId scelto in fase di registrazione
	 * @param password la password scelta in fase di registrazione
	 * @return true se l'accesso e' andato a buon fine, false altrimenti
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
	 * Controlla che un userId non sia già in uso.
	 * @param userId l'ID dell'utente
	 * @return true se e' gia' presente, false altrimenti
	 */
	public boolean contains(String userId)
	{
		return mappa.containsKey(userId);
	}
	
	public boolean update()
	{
		if(db == null)
			return false;
		mappa = db.getAll();
		return true;
	}
}
