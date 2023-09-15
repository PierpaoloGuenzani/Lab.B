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
	private DefaultListModel<Canzone> canzoneJlist;
	private String userId;
	private boolean isLogged = false;

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
	 * Metodo che controlla la logica di ricerca lato client
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
	
	public void visualizzaEmozioni(String idCanzone) throws RemoteException
	{
		ProspettoRiassuntivo prospettoRiassuntivo = stub.visualizzaEmozioni(idCanzone);
		new ProspettoRiassuntivoDialog(prospettoRiassuntivo);
	}
	
	public boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException, RemoteException
	{
		return stub.Registrazione(newUtenteRegistrato);
	}
	
	public boolean accedi(String userId, String password) throws RemoteException
	{
		if(stub.accedi(userId,password))
		{
			this.userId = userId;
			return true;
		}
		return false;
	}
	
	public void logOut() throws RemoteException
	{
		if(userId == null) return;
		stub.logOut(userId);
		userId = null;
	}
	
	public boolean RegistraPlaylist(String nomePlaylist) throws IOException, RemoteException
	{
		//TODO
		return true;
	}
	
	public boolean aggiungiCanzone(String idCanzone, String idPlaylist) throws IOException, RemoteException
	{
		return false;
	}
	
	public boolean inserisciEmozioni(Percezione newPercezione) throws IOException, RemoteException
	{
		return stub.inserisciEmozioni(newPercezione);
	}
	
	public boolean aggiungiCompilation(List<Canzone> listaCanzoni, String idPlaylist) throws IOException, RemoteException
	{
		return false;
	}
	
	public List<Playlist> cercaPlaylistPerUtente() throws RemoteException
	{
		return stub.cercaPlaylistPerUtente(userId);
	}
	
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
}
