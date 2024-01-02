package common;

import java.io.IOException;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EmotionalSongsInterface extends Remote
{
	List<Canzone> cercaBranoMusicale(String titolo) throws RemoteException;
	
	List<Canzone> cercaBranoMusicale(String nomeArtista, int anno) throws RemoteException;
	
	List<Canzone> cercaBraniPerAutore(String autore) throws RemoteException;
	
	ProspettoRiassuntivo visualizzaEmozioni(String idCanzone) throws RemoteException;
	
	boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException, RemoteException;
	
	boolean accedi(String userId, String password)throws RemoteException;
	
	void logOut(String idUtente) throws RemoteException;
	
	boolean RegistraPlaylist(String nomePlaylist, String userId) throws IOException, RemoteException;
	
	boolean aggiungiCanzone(String idCanzone, String idPlaylist, String userId) throws IOException, RemoteException;
	
	boolean inserisciEmozioni(Percezione newPercezione, String userId) throws IOException, RemoteException;
	
	boolean aggiungiCompilation(List<Canzone> listaCanzoni, String idPlaylist) throws IOException, RemoteException;
	
	List<Playlist> cercaPlaylistPerUtente(String idUtente) throws RemoteException;
	
	boolean controlloUserid(String UserID) throws RemoteException;
	
	List<Canzone> canzoniDaIdPlaylist(String idPlaylist) throws RemoteException;
	
	boolean controllaCanzoneUtente(String idUtente, String idCanzone) throws RemoteException;
}
