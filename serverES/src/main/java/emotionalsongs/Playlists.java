package emotionalsongs;

import common.Canzone;
import common.Playlist;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe per la gestione e il salvataggio delle playlist.
 * Questa classe offre funzionalità per manipolare le playlist, inclusa la ricerca, l'aggiunta e il controllo delle canzoni.
 * @throws IOException nel caso di errori di lettura dal database.
 */
public class Playlists
{
	//private static final String dbFile = "data/Playlist.dati.txt";
	private PlaylistDAOInterface db;
	private ConcurrentHashMap<String ,Playlist> mappa;
	private static Playlists instance;

	/**
	 * Costruttore privato per garantire un'unica istanza della classe Playlists (Singleton).
	 * Il costruttore è vuoto poiché tutte le inizializzazioni sono gestite nel blocco statico o dai metodi della classe.
	 * È chiamato solo una volta dal metodo getInstance().
	 * @throws IOException nel caso di errori di lettura dal database.
	 */
	private Playlists() throws IOException
	{}

	/**
	 * Restituisce l'istanza singleton della classe Playlists.
	 * Se l'istanza non è ancora creata, ne crea una nuova.
	 * @return un'istanza di Playlists.
	 * @throws IOException nel caso di errori di lettura dal database.
	 */
	public static Playlists getInstance() throws IOException
	{
		if(instance == null)
			instance = new Playlists();
		return instance;
	}

	/**
	 * Restituisce il database che contiene tutte le playlist.
	 * @return un'interfaccia del database.
	 */
	public PlaylistDAOInterface getDB()
	{
		return db;
	}

	/**
	 * Imposta il database delle playlist.
	 * @param db l'implementazione dell'interfaccia DB da assegnare
	 */
	public void setDB(PlaylistDAOInterface db)
	{
		this.db = db;
		update();
	}

	/**
	 * Verifica che tra le playlist create da un utente sia presente un determinato brano.
	 * @param idPersona l'ID dell'utente che ha creato la playlist.
	 * @param idCanzone l'ID della canzone da cercare.
	 * @return true se la canzone è presente in almeno una delle playlist dell'utente, false altrimenti.
	 */
	public boolean controllaCanzonePersona(String idPersona, String idCanzone)
	{
		for (Playlist collezione : mappa.values())
		{
			if (collezione.getIdPersona().equals(idPersona)){
				if (collezione.canzonePresente(idCanzone)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Aggiunge una canzone alla playlist specificata.
	 * @param idCanzone la stringa che identifica la canzone da aggiungere
	 * @param idPlaylist l'ID della playlist a cui aggiungere
	 * @return true se la canzone e' stata aggiunta alla playlist, false altrimenti
	 * @throws IOException se si verifica un errore di Input/Output relativo al database
	 */
	public boolean aggiungiCanzone(String idCanzone, String idPlaylist) throws IOException
	{
		if(mappa.containsKey(idPlaylist)) {
			if (mappa.get(idPlaylist).aggiungiCanzone(idCanzone)) {
				db.addSong(idCanzone, idPlaylist);
				return true;
			}
		}
		return false;
	}

	/**
	 * Aggiunge una compilation alla playlist.
	 * @param elenco la lista di canzoni da aggiungere
	 * @param idPlaylist l'ID della playlist a cui aggiungere
	 * @return true se le canzoni sono state aggiunte alla playlist, false altrimenti
	 * @throws IOException se si verifica un errore di Input/Output relativo al database
	 */
	public boolean aggiungiCompilation(List<Canzone> elenco, String idPlaylist) throws IOException
	{
		for (Canzone c : elenco)
		{
			aggiungiCanzone(c.getId(), idPlaylist);
		}
		return true;
	}


	/**
	 * Cerca una playlist mediante il suo nome.
	 * @param nomePlaylist il nome della playlist
	 * @return una lista di playlist i cui nomi contengono la stringa passata come argomento o una lista vuota nel caso in cui nessuna playlist corrisponda a quella cercata
	 */
	public List<Playlist> cercaPlaylistPerNome(String nomePlaylist) {
		LinkedList<Playlist> listaPlaylist= new LinkedList<Playlist>();
		for (Playlist c: mappa.values()) {
			if (nomePlaylist.contains(c.getTitolo() ))
				listaPlaylist.add(c);
		}
		return listaPlaylist;
	}

	/**
	 * Cerca una playlist mediante il suo ID.
	 * @param idPlaylist l'ID della playlist da cercare
	 * @return la playlist corrispondente, null altrimenti
	 */
	public Playlist cercaPlaylistPerId(String idPlaylist) {
		return mappa.get(idPlaylist);
	}

	/**
	 * Permette di visualizzare l'elenco delle playlist associate a una persona.
	 * @param IdPersona l'ID della persona che ha creato le playlist
	 * @return la lista delle playlist corrispondenti o la lista vuota nel caso in cui non gli sia associata nessuna
	 */
	public List<Playlist> cercaPlaylistPerIdPersona(String IdPersona) {
		LinkedList<Playlist> listaPlaylist= new LinkedList<Playlist>();
		for (Playlist c: mappa.values()) {
			if (IdPersona.equals(c.getIdPersona() ))
				listaPlaylist.add(c);
		}
		return listaPlaylist;
	}

	/**
	 * Aggiunge la playlist passata come argomento alla HashMap.
	 * @param playlist la playlist da aggiungere
	 * @throws IOException se si verifica un errore di Input/Output relativo al database
	 */
	public boolean aggiungiPlaylist(Playlist playlist) throws IOException{
		if(mappa.containsKey(playlist.getIdPlaylist()))
		{
			Playlist.setCount(mappa.size());
			Playlist newplaylist = new Playlist(playlist.getTitolo(), playlist.getIdPersona());
			newplaylist.setListaCanzoni(playlist.getAlberoCanzoni());
			playlist = newplaylist;
		}
		mappa.put(playlist.getIdPlaylist(),playlist);
		db.save(playlist);
		return true;
	}

	/**
	 * Aggiorna la mappa delle playlist con i dati dal database.
	 * @return True se l'operazione di aggiornamento è riuscita, altrimenti False.
	 */
	public boolean update()
	{
		if(db == null)
			return false;
		mappa = db.getAll();
		Playlist.setCount(mappa.size());
		return true;
	}
}
