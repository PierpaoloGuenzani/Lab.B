package emotionalsongs;

import common.*;

import javax.swing.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Year;
import java.util.List;

/**
 * La classe MainModel rappresenta il Modello dell'applicazione "EmotionalSongs" e gestisce le operazioni relative ai dati e alla logica di business.
 * Questa classe è parte del pattern Model-View-Controller (MVC) in cui il Modello è responsabile della manipolazione dei dati e delle interazioni con il server.
 * Il Modello si connette al server utilizzando l'indirizzo IP fornito da "EmotionalSongs" e svolge diverse operazioni come la ricerca di brani musicali, la gestione delle emozioni e altro ancora.
 * Il Modello collabora con il Controller (MainController) per coordinare le azioni dell'utente.
 */
public class MainModel
{
	
	private EmotionalSongsInterface stub;
	//cash dei risultati
	private DefaultListModel<Canzone> canzoneJlist;
	private String userId;
	private boolean isLogged;

	/**
	 * Costruttore di MainModel.
	 */
	public MainModel()
	{}

	/**
	 * Imposta lo stub del server RMI utilizzando l'indirizzo IP specificato.
	 * @param serverIP L'indirizzo IP del server RMI.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public void setStub(String serverIP) throws RemoteException
	{
		Registry registry = LocateRegistry.getRegistry(serverIP);
		try
		{
			stub = (EmotionalSongsInterface) registry.lookup("EmotionalSong");
		}
		catch (NotBoundException e)
		{} //Non può essere il nome è HardCoded nelle classi
	}
	
	/**
	 * Metodo per cercare brani musicali per titolo sul server.
	 * @param titolo Il titolo del brano da cercare.
	 * @throws IllegalArgumentException se il titolo è nullo o vuoto.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public void cercaBranoMusicale(String titolo) throws IllegalArgumentException, RemoteException
	{
		canzoneJlist = new DefaultListModel<Canzone>();
		List<Canzone> lista = stub.cercaBranoMusicale(titolo);
		lista.forEach(canzoneJlist::addElement);
	}

	/**
	 * Metodo per cercare brani musicali per nome dell'artista e anno sul server.
	 * @param nomeArtista Il nome dell'artista.
	 * @param anno L'anno in cui il brano è stato creato.
	 * @throws IllegalArgumentException se il nome dell'artista o l'anno sono vuoti o nulli.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public void cercaBranoMusicale(String nomeArtista, int anno) throws IllegalArgumentException, RemoteException
	{
		if(nomeArtista == null | nomeArtista.equals("")) throw new IllegalArgumentException("Il nome dell'artista è vuoto!");
		if(anno <= 0 | anno > Year.now().getValue())
			throw new IllegalArgumentException("L'anno del singolo non può essere vuoto;\nConsiglio: usare ricerca per autore");
		canzoneJlist = new DefaultListModel<Canzone>();
		List<Canzone> lista = stub.cercaBranoMusicale(nomeArtista, anno);
		lista.forEach(canzoneJlist::addElement);
	}
	
	/**
	 * Metodo che controlla la logica di ricerca lato client.
	 * Ricerca brani musicali per autore sul server.
	 * @param autore il nome dell'artista che ha creato il brano
	 * @throws IllegalArgumentException se il titolo è nullo o vuoto
	 * @throws RemoteException se non è possibile connettersi al server
	 */
	public void cercaBraniPerAutore(String autore) throws IllegalArgumentException, RemoteException
	{
		if(autore == null | autore.equals("")) throw new IllegalArgumentException("Il nome dell'artista è vuoto!");
		canzoneJlist = new DefaultListModel<Canzone>();
		List<Canzone> lista = stub.cercaBraniPerAutore(autore);
		lista.forEach(canzoneJlist::addElement);
	}

	/**
	 * Visualizza le emozioni associate a una canzone.
	 *
	 * @param idCanzone L'ID della canzone di cui visualizzare le emozioni.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public void visualizzaEmozioni(String idCanzone) throws RemoteException
	{
		//TODO: fare meglio
		ProspettoRiassuntivo prospettoRiassuntivo = stub.visualizzaEmozioni(idCanzone);
		new ProspettoRiassuntivoDialog(prospettoRiassuntivo).draw();
	}

	/**
	 * Registra un nuovo utente.
	 *
	 * @param newUtenteRegistrato Il nuovo utente da registrare.
	 * @return true se la registrazione ha successo, false altrimenti.
	 * @throws IOException se si verifica un errore di I/O.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException, RemoteException
	{
		return stub.Registrazione(newUtenteRegistrato);
	}

	/**
	 * Effettua l'accesso all'applicazione.
	 *
	 * @param userId   L'ID dell'utente.
	 * @param password La password dell'utente.
	 * @return true se l'accesso ha successo, false altrimenti.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public boolean accedi(String userId, String password) throws RemoteException
	{
		if(stub.accedi(userId,password))
		{
			this.userId = userId;
			isLogged = true;
			return true;
		}
		return false;
	}

	/**
	 * Effettua il logout dall'applicazione.
	 *
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public void logOut() throws RemoteException
	{
		if(userId == null) return;
		stub.logOut(userId);
		userId = null;
	}
	
	/**
	 * Registra una nuova playlist per l'utente corrente.
	 *
	 * @param nomePlaylist Il nome della nuova playlist.
	 * @return true se la registrazione ha successo, false altrimenti.
	 * @throws IOException se si verifica un errore di I/O.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public boolean RegistraPlaylist(String nomePlaylist) throws IOException, RemoteException
	{
		if(!isLogged) return false;
		return stub.RegistraPlaylist(nomePlaylist, userId);
	}

	/**
	 * Aggiunge una canzone a una playlist.
	 *
	 * @param idCanzone  L'ID della canzone da aggiungere.
	 * @param idPlaylist L'ID della playlist alla quale aggiungere la canzone.
	 * @return true se l'aggiunta ha successo, false altrimenti.
	 * @throws IOException se si verifica un errore di I/O.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public boolean aggiungiCanzone(String idCanzone, String idPlaylist) throws IOException, RemoteException
	{
		if(!isLogged) return false;
		return stub.aggiungiCanzone(idCanzone, idPlaylist, userId);
	}

	/**
	 * Inserisce le emozioni associate a una canzone.
	 *
	 * @param newPercezione Le percezioni dell'utente sulla canzone.
	 * @return true se l'inserimento ha successo, false altrimenti.
	 * @throws IOException se si verifica un errore di I/O.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public boolean inserisciEmozioni(Percezione newPercezione) throws IOException, RemoteException
	{
		if(!isLogged) return false;
		return stub.inserisciEmozioni(newPercezione, userId);
	}

	/**
	 * Aggiunge una compilation di canzoni a una playlist.
	 *
	 * @param listaCanzoni La lista delle canzoni da aggiungere.
	 * @param idPlaylist   L'ID della playlist alla quale aggiungere le canzoni.
	 * @return true se l'aggiunta ha successo, false altrimenti.
	 * @throws IOException se si verifica un errore di I/O.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public boolean aggiungiCompilation(List<Canzone> listaCanzoni, String idPlaylist) throws IOException, RemoteException
	{
		//TODO: implementazione o rimuovere
		return false;
	}

	/**
	 * Ottiene la lista di canzoni associate a una playlist.
	 *
	 * @param idPlaylist L'ID della playlist.
	 * @return Il modello di lista di default delle canzoni.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public DefaultListModel<Canzone> canzoniDaIdPlaylist(String idPlaylist) throws RemoteException
	{
		DefaultListModel<Canzone> canzoniJList= new DefaultListModel<>();
		stub.canzoniDaIdPlaylist(idPlaylist).forEach(canzone -> canzoniJList.addElement(canzone));
		return canzoniJList;
	}

	/**
	 * Cerca le playlist associate all'utente corrente.
	 *
	 * @return Il modello di lista di default delle playlist.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public DefaultListModel<Playlist> cercaPlaylistUtente() throws RemoteException
	{
		DefaultListModel<Playlist> playlistJList = new DefaultListModel<>();
		stub.cercaPlaylistPerUtente(userId).forEach(playlist -> playlistJList.addElement(playlist));
		return playlistJList;
	}

	/**
	 * Controlla l'esistenza di un determinato UserID.
	 *
	 * @param UserID L'ID dell'utente da controllare.
	 * @return true se l'ID esiste, false altrimenti.
	 * @throws RemoteException se non è possibile connettersi al server.
	 */
	public boolean controlloUserid(String UserID) throws RemoteException
	{
		return stub.controlloUserid(UserID);
	}

	/**
	 * Ottiene il modello di lista di default delle canzoni.
	 * @return Il modello di lista di default delle canzoni.
	 */
	public DefaultListModel<Canzone> getCanzoneJlist()
	{
		return canzoneJlist;
	}

	/**
	 * Ottiene l'ID dell'utente corrente.
	 * @return L'ID dell'utente corrente.
	 */
	public String getUserId()
	{
		return userId;
	}
	
	public boolean controllaCanzonePersona(String songId) throws RemoteException
	{
		return stub.controllaCanzoneUtente(userId, songId);
	}
}
