package common;

import java.io.IOException;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EmotionalSongs extends Remote
{
	List<Canzone> cercaBranoMusicale(String titolo) throws RemoteException;
	
	List<Canzone> cercaBranoMusicale(String nomeArtista, int anno) throws RemoteException;
	
	void visualizzaEmozioniBrano(String idCanzone) throws RemoteException;
	
	boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException, RemoteException;
	
	boolean accedi(String userId, String password)throws RemoteException;
	
	Playlist RegistraPlaylist(String titolo) throws IOException, RemoteException;
	
	boolean mostraPlaylist()throws RemoteException;
	
	boolean aggiungiCanzone(String idCanzone, String idPlaylist) throws IOException, RemoteException;
	
	void inserisciEmozioni(Percezione newPercezione) throws IOException, RemoteException;
	
	Canzone cercaCanzone(String idCanzone) throws RemoteException;
	
	List<Canzone> cercaBraniPerAutore(String autore) throws RemoteException;
	
	boolean aggiungiCompilation(List<Canzone> listaCanzoni, String idPlaylist) throws IOException, RemoteException;
	
	boolean controllaCanzoneUtente(String idCanzone) throws RemoteException;
	
	boolean controllaEmozioniUtente(String idCanzone) throws RemoteException;
	
	List<Playlist> cercaPlaylistPerUtente() throws RemoteException;
	
	boolean controlloUserid(String UserID) throws RemoteException;
}
