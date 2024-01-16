package common;

import java.io.IOException;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * L'interfaccia {@code EmotionalSongsInterface} definisce i servizi offerti dalla piattaforma "Emotional Songs" attraverso
 * l'architettura RMI (Remote Method Invocation).
 * Fornisce la segnatura un insieme di metodi pubblici per la gestione di brani musicali, emozioni, utenti registrati e playlist.
 * Le classi che implementano questa interfaccia devono fornire un'implementazione completa per tutti i metodi dichiarati.
 *
 * Tutti i metodi dichiarati possono lanciare RemoteException, eccezione standard RMI per problemi di comunicazione.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public interface EmotionalSongsInterface extends Remote
{
	/**
	 * Cerca brani musicali per titolo.
	 * @param titolo Il titolo del brano da cercare.
	 * @return Una lista di brani musicali che corrispondono al titolo.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	List<Canzone> cercaBranoMusicale(String titolo) throws RemoteException;

	/**
	 * Cerca brani musicali per nome dell'artista e anno di rilascio.
	 * @param nomeArtista Il nome dell'artista.
	 * @param anno L'anno di rilascio del brano.
	 * @return Una lista di brani musicali che corrispondono ai criteri di ricerca.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	List<Canzone> cercaBranoMusicale(String nomeArtista, int anno) throws RemoteException;

	/**
	 * Cerca brani musicali per autore.
	 * @param autore Il nome dell'artista o autore.
	 * @return Una lista di brani musicali dell'autore specificato.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	List<Canzone> cercaBraniPerAutore(String autore) throws RemoteException;

	/**
	 * Visualizza un prospetto riassuntivo delle emozioni associate a una canzone.
	 * @param idCanzone L'ID della canzone.
	 * @return Un oggetto ProspettoRiassuntivo che rappresenta le emozioni associate alla canzone.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	ProspettoRiassuntivo visualizzaEmozioni(String idCanzone) throws RemoteException;

	/**
	 * Registra un nuovo utente.
	 * @param newUtenteRegistrato Un oggetto UtenteRegistrato contenente le informazioni del nuovo utente.
	 * @return True se la registrazione è avvenuta con successo, False altrimenti.
	 * @throws IOException se si verifica un errore di Input/Output durante la registrazione.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException, RemoteException;

	/**
	 * Effettua l'accesso di un utente.
	 * @param userId L'ID dell'utente.
	 * @param password La password dell'utente.
	 * @return True se l'accesso è avvenuto con successo, False altrimenti.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	boolean accedi(String userId, String password)throws RemoteException;

	/**
	 * Esegue il logout di un utente.
	 * @param idUtente L'ID dell'utente.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	void logOut(String idUtente) throws RemoteException;

	/**
	 * Registra una nuova playlist per un utente.
	 * @param nomePlaylist Il nome della nuova playlist.
	 * @param userId L'ID dell'utente proprietario della playlist.
	 * @return True se la registrazione della playlist è avvenuta con successo, False altrimenti.
	 * @throws IOException se si verifica un errore di Input/Output durante la registrazione della playlist.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	boolean RegistraPlaylist(String nomePlaylist, String userId) throws IOException, RemoteException;

	/**
	 * Aggiunge una canzone a una playlist.
	 * @param idCanzone L'ID della canzone da aggiungere.
	 * @param idPlaylist L'ID della playlist a cui aggiungere la canzone.
	 * @param userId L'ID dell'utente che sta aggiungendo la canzone alla playlist.
	 * @return True se l'aggiunta della canzone è avvenuta con successo, False altrimenti.
	 * @throws IOException se si verifica un errore di Input/Output durante l'aggiunta della canzone.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	boolean aggiungiCanzone(String idCanzone, String idPlaylist, String userId) throws IOException, RemoteException;

	/**
	 * Inserisce le emozioni associate a una canzone da parte di un utente.
	 * @param newPercezione Un oggetto Percezione contenente le emozioni associate alla canzone.
	 * @param userId L'ID dell'utente che sta inserendo le emozioni.
	 * @return True se l'inserimento delle emozioni è avvenuto con successo, False altrimenti.
	 * @throws IOException se si verifica un errore di Input/Output durante l'inserimento delle emozioni.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	boolean inserisciEmozioni(Percezione newPercezione, String userId) throws IOException, RemoteException;
	

	/**
	 * Cerca le playlist associate a un utente.
	 * @param idUtente L'ID dell'utente di cui cercare le playlist.
	 * @return Una lista di playlist associate all'utente.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	List<Playlist> cercaPlaylistPerUtente(String idUtente) throws RemoteException;

	/**
	 * Controlla se esiste già un utente con un determinato UserID.
	 * @param UserID L'ID dell'utente da controllare.
	 * @return True se l'utente con il dato UserID esiste, False altrimenti.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	boolean controlloUserid(String UserID) throws RemoteException;

	/**
	 * Ottiene la lista di canzoni associate a una playlist.
	 * @param idPlaylist L'ID della playlist.
	 * @return Una lista di canzoni associate alla playlist.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	List<Canzone> canzoniDaIdPlaylist(String idPlaylist) throws RemoteException;

	/**
	 * Controlla se una determinata canzone è presente nella playlist di un utente.
	 * @param idUtente L'ID dell'utente proprietario della playlist.
	 * @param idCanzone L'ID della canzone da controllare.
	 * @return True se la canzone è presente nella playlist dell'utente, False altrimenti.
	 * @throws RemoteException se si verifica un errore durante la chiamata remota.
	 */
	boolean controllaCanzoneUtente(String idUtente, String idCanzone) throws RemoteException;
}
