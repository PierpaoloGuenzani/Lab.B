package common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * La classe {@code Playlist} gestisce i brani presenti nelle diverse playlist.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class Playlist implements Serializable, Comparable<Playlist>
{
	private static long COUNT = 0; //va fatto settare ogni volta che riapro la applicazione
	private final String idPlaylist;
	private final String idPersona;
	private String titolo;
	private TreeSet<String> listaCanzoni;

	/**
	 * Costruisce un oggetto {@code Playlist} contenente i dati della playlist.
	 *
	 * @param titolo     il titolo da assegnare alla playlist
	 * @param idPersona  l'ID della persona che ha creato la playlist
	 * @throws NullPointerException se il nome della playlist o l'ID della persona sono null
	 */
	public Playlist(String titolo, String idPersona)
	{
		if(titolo == null) throw new NullPointerException("Il nome della playlist non puo' essere null.");
		if(idPersona == null) throw new NullPointerException("L' id della persona che ha creato la playlist non puo' essere minore di 0.");
		this.idPersona = idPersona;
		this.titolo = titolo;
		idPlaylist = String.format("%1$016x",COUNT); //Long.toHexString(COUNT++);
		listaCanzoni = new TreeSet<>();
	}

	/**
	 * Costruisce un oggetto {@code Playlist} contenente i dati della playlist.
	 *
	 * @param titolo     il titolo della playlist
	 * @param idPersona  l'ID della persona che ha creato la playlist
	 * @param idPlaylist l'ID specifico della playlist
	 * @throws NullPointerException se il nome della playlist, l'ID della persona o l'ID della playlist sono null
	 */
	public Playlist(String titolo, String idPersona, String idPlaylist)
	{
		if(titolo == null) throw new NullPointerException("Il nome della playlist non puo' essere null.");
		if(idPersona == null) throw new NullPointerException("L' id della persona che ha creato la playlist non puo' essere minore di 0.");
		this.idPersona = idPersona;
		this.titolo = titolo;
		this.idPlaylist = idPlaylist;
		listaCanzoni = new TreeSet<>();
	}

	/**
	 * Costruisce un oggetto {@code Playlist} contenente i dati della playlist.
	 *
	 * @param titolo il titolo della playlist
	 * @param p      la persona che vuole creare la playlist
	 * @throws NullPointerException se il nome della playlist o l'oggetto UtenteRegistrato sono null
	 */
	public Playlist(String titolo, UtenteRegistrato p)
	{
		this(titolo, p.getUserId());
	}

	/**
	 * Restituisce l'ID della playlist.
	 *
	 * @return l'ID della playlist
	 */
	public String getIdPlaylist() {
		return idPlaylist;
	}

	/**
	 * Restituisce l'ID della persona che ha creato la playlist.
	 *
	 * @return l'ID della persona
	 */
	public String getIdPersona()
	{
		return idPersona;
	}

	/**
	 * Restituisce il titolo della playlist.
	 *
	 * @return il nome della playlist
	 */
	public String getTitolo()
	{
		return titolo;
	}

	/**
	 * Restituisce la lista delle canzoni presenti nella playlist.
	 *
	 * @return la lista degli ID delle canzoni della playlist
	 */
	public List<String> getListaCanzoni()
	{
		return new LinkedList<>(listaCanzoni);
	}

	/**
	 * Restituiscel'albero ordinato contenente gli ID delle canzoni presenti nella playlist.
	 *
	 * @return un oggetto di tipo TreeSet contenente gli ID delle canzoni della playlist
	 */
	public TreeSet<String> getAlberoCanzoni() {return listaCanzoni;}

	/**
	 * Assegna la lista delle canzoni alla playlist.
	 *
	 * @param listaCanzoni la lista degli ID delle canzoni da assegnare alla playlist
	 */
	public void setListaCanzoni(List<String> listaCanzoni)
	{
		if(listaCanzoni == null) throw new NullPointerException("La lista che deve essere assegnata non puo' avere riferimento null.");
		this.listaCanzoni = new TreeSet<String>(listaCanzoni);
	}
	
	/**
	 * Assegna la lista delle canzoni alla playlist.
	 *
	 * @param alberoCanzoni un albero ordinato contenente tutti gli ID delle canzoni
	 */
	public void setListaCanzoni(TreeSet<String> alberoCanzoni)
	{
		if(alberoCanzoni == null) throw new NullPointerException("La lista che deve essere assegnata non puo' avere riferimento null.");
		this.listaCanzoni = listaCanzoni;
	}

	/**
	 * Aggiunge alla playlist la canzone il cui ID è passato come argomento.
	 *
	 * @param idCanzone la stringa che identifica la canzone da aggiungere
	 * @return true se la canzone è stata aggiunta con successo, false altrimenti
	 * @throws NullPointerException se l'ID della canzone è null
	 */
	public boolean aggiungiCanzone(String idCanzone)
	{
		if(idCanzone == null) throw new NullPointerException("L'id della canzone deve essere maggiore o uguale a 0.");
		if (!canzonePresente(idCanzone))
		{
			return listaCanzoni.add(idCanzone);
		}
		return false;
	}

	/**
	 * Aggiunge alla playlist la canzone passata come argomento.
	 *
	 * @param c l'oggetto della classe Canzone da aggiungere
	 * @return true se la canzone è stata aggiunta con successo, false se la canzone era già presente nella playlist
	 * @throws NullPointerException se l'oggetto della classe Canzone fornito come argomento è null
	 */
	public boolean aggiungiCanzone(Canzone c)
	{
		if(c == null) throw new NullPointerException("Non si puo' aggiungere un'istanza null.");
		return aggiungiCanzone(c.getId());
	}

	/**
	 * Rimuove la canzone identificata dall'ID specificato dalla playlist.
	 *
	 * @param idCanzone l'ID della canzone da rimuovere
	 * @throws NullPointerException se l'ID della canzone è null
	 */
	public void rimuoviCanzone(String idCanzone)
	{
		if(idCanzone == null) throw new NullPointerException("L'id della canzone deve essere maggiore o uguale a 0.");
		listaCanzoni.remove(idCanzone);
	}

	/**
	 * Rimuove la canzone passata come argomento dalla playlist che esegue il metodo.
	 *
	 * @param c l'oggetto della classe Canzone da rimuovere
	 */
	public void rimuoviCanzone(Canzone c)
	{
		if(c == null) throw new NullPointerException("Non si puo' rimuovere un'istanza null.");
		rimuoviCanzone(c.getId());
	}

	/**
	 * Verifica che nella playlist sia presente la canzone identificata dall'ID specificato.
	 *
	 * @param idCanzone la stringa che identifica la canzone
	 * @return true se la canzone e' presente nella playlist, false altrimenti
	 */
	public boolean canzonePresente(String idCanzone)
	{
		return listaCanzoni.contains(idCanzone);
	}

	/**
	 * Verifica che nella playlist sia presente la canzone passata come argomento.
	 *
	 * @param c l'oggetto della classe canzone
	 * @return true se la canzone e' presente nella playlist, false altrimenti
	 */
	public boolean canzonePresente(Canzone c)
	{
		return canzonePresente(c.getId());
	}

	/**
	 * Restituisce una rappresentazione testuale della playlist, mostrando l'ID, il nome e l'ID della persona.
	 *
	 * @return una stringa contenente i dettagli della playlist
	 */
	@Override
	public String toString() {
		return "ID: " + idPlaylist + " , Nome: " + titolo + " , IDPersona: " + idPersona + "\n" ;
	}

	/**
	 * Imposta il contatore statico delle playlist creato dall'ultimo avvio dell'applicazione.
	 *
	 * @param id l'intero da assegnare al contatore
	 */
	public static void setCount(long id)
	{
		COUNT = id;
	}

	/**
	 * Imposta il contatore statico delle playlist a partire da una rappresentazione stringa esadecimale.
	 *
	 * @param id la stringa esadecimale da convertire e assegnare al contatore
	 */
	public static void setCount(String id) {
		COUNT = Long.decode("#"+id);
	}

	/**
	 * Confronta questa playlist con un'altra in base all'ID della playlist.
	 *
	 * @param playlist la playlist da confrontare
	 * @return un intero negativo, positivo o pari a zero se questa playlist è rispettivamente minore, maggiore o uguale alla playlist fornita
	 */
	@Override
	public int compareTo(Playlist playlist)
	{
		return this.idPlaylist.compareTo(playlist.getIdPlaylist());
	}

	/**
	 * Converte un ID numerico in una rappresentazione esadecimale a 16 cifre.
	 *
	 * @param id l'ID numerico da convertire
	 * @return una stringa esadecimale a 16 cifre rappresentante l'ID
	 */
	public static String convertitoreIdString(long id) {
		return String.format("%1$016x", COUNT);
	}
}
