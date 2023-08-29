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


public class MainModel
{
	private MainViewGUIGenerated mainView;
	private MainController mainController;
	
	private EmotionalSongsInterface stub;
	private DefaultListModel<Canzone> canzoneJlist;
	private String userId;
	private boolean isLogged = false;
	
	public MainModel()
	{
		new ServerInternetProtocolAddressDialog(this);
	}
	
	public void setStub(String serverIP) throws RemoteException
	{
		Registry registry = LocateRegistry.getRegistry(serverIP);
		try
		{
			stub = (EmotionalSongsInterface) registry.lookup("EmotionalSong");
		}
		catch (NotBoundException e)
		{} //Non pùò essere il nome è HardCoded nelle classi
	}
	
	public void setMainView(MainViewGUIGenerated mainView)
	{
		this.mainView = mainView;
	}
	
	public void setMainController(MainController mainController)
	{
		this.mainController = mainController;
	}
	
	/**
	 * Metodo che controlla la logica di ricerca lato client
	 * @param titolo il titolo del brano da ricercare nel server
	 * @throws IllegalArgumentException se il titolo è nullo o vuoto
	 * @throws RemoteException se non è possibile connettersi al server
	 */
	public void cercaBranoMusicale(String titolo) throws IllegalArgumentException, RemoteException
	{
		canzoneJlist = new DefaultListModel<Canzone>();
		List<Canzone> lista = stub.cercaBranoMusicale(titolo);
		lista.forEach(canzoneJlist::addElement);
	}
	
	/**
	 * Metodo che controlla la logica di ricerca lato client
	 * @param nomeArtista il nome dell'artista che ha creato il brano
	 * @param anno l'anno in cui il brano è stato creato
	 * @throws IllegalArgumentException se il nome dell'artista o l'anno sono vuoti o nulli
	 * @throws RemoteException se non è possibile connettersi al server
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
		return false;
	}
	
	public boolean accedi(String userId, String password) throws RemoteException
	{
		if(stub.accedi(userId,password))
		{
			mainView.setLoggedIn();
			return true;
		}
		return false;
	}
	
	public void logOut(String idUtente) throws RemoteException
	{
		stub.logOut(idUtente);
		mainView.setLoggedOut();
	}
	
	public boolean RegistraPlaylist(Playlist newPlaylist) throws IOException, RemoteException
	{
		return false;
	}
	
	public boolean aggiungiCanzone(String idCanzone, String idPlaylist) throws IOException, RemoteException
	{
		return false;
	}
	
	public boolean inserisciEmozioni(Percezione newPercezione) throws IOException, RemoteException
	{
		return false;
	}
	
	public boolean aggiungiCompilation(List<Canzone> listaCanzoni, String idPlaylist) throws IOException, RemoteException
	{
		return false;
	}
	
	public List<Playlist> cercaPlaylistPerUtente(String idUtente) throws RemoteException
	{
		return null;
	}
	
	public boolean controlloUserid(String UserID) throws RemoteException
	{
		return false;
	}
	
	public DefaultListModel<Canzone> getCanzoneJlist()
	{
		return canzoneJlist;
	}
	
	public String getUserId()
	{
		return userId;
	}
}
