package common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Classe per gestire i brani presenti nelle diverse playlist.
 * @author Guenzani Pierpaolo 738675 VA
 * @author Tropeano Martina 749890 VA
 * @author Paradiso Fabiola 749727 VA
 * @author Cuvato Paolo 748691 VA
 */
public class Playlist implements Serializable
{
	private static int COUNT = 0; //va fatto settare ogni volta che riapro la applicazione
	private final String idPlaylist;
	private final String titolo;
	private final String idPersona;
	private TreeSet<String> albero;

	/**
	 * Costruisce un oggetto che contiene i dati della playlist.
	 * @param titolo il titolo da assegnare alla playlist
	 * @param idPersona l'ID della persona che ha creato la playlist
	 */
	public Playlist(String titolo, String idPersona)
	{
		if(titolo == null) throw new NullPointerException("Il nome della playlist non puo' essere null.");
		if(idPersona == null) throw new NullPointerException("L' id della persona che ha creato la playlist non puo' essere minore di 0.");
		this.idPersona = idPersona;
		this.titolo = titolo;
		idPlaylist = Integer.toHexString(COUNT++);
		albero = new TreeSet<>();
	}
	
	/**
	 * Costruisce un oggetto che contiene i dati della playlist.
	 * @param titolo il titolo della playlist
	 * @param p la persona che vuole creare la playlist
	 */
	public Playlist(String titolo, Persona p)
	{
		this(titolo, p.getUserId());
	}

	/**
	 * Restituisce l'ID della playlist.
	 * @return l'ID della playlist
	 */
	public String getIdPlaylist() {
		return idPlaylist;
	}

	/**
	 * Restituisce l'ID della persona che ha creato la playlist.
	 * @return l'ID della persona
	 */
	public String getIdPersona()
	{
		return idPersona;
	}

	/**
	 * Restituisce il titolo della playlist.
	 * @return il nome della playlist
	 */
	public String getTitolo()
	{
		return titolo;
	}

	/**
	 * Restituisce la lista delle canzoni presenti nella playlist.
	 * @return la lista degli ID delle canzoni della playlist
	 */
	public List<String> getListaCanzoni()
	{
		return new LinkedList<>(albero);
	}

	/**
	 * Assegna la lista delle canzoni alla playlist.
	 * @param listaCanzoni la lista degli ID delle canzoni da assegnare alla playlist
	 */
	public void setListaCanzoni(List<String> listaCanzoni)
	{
		if(listaCanzoni == null) throw new NullPointerException("La lista che deve essere assegnata non puo' avere riferimento null.");
		albero = new TreeSet<>(listaCanzoni);
	}

	/**
	 * Aggiunge alla playlist la canzone il cui ID e' passato come argomento.
	 * @param idCanzone la stringa che identifica la canzone da aggiungere
	 */
	public boolean aggiungiCanzone(String idCanzone)
	{
		if(idCanzone == null) throw new NullPointerException("L'id della canzone deve essere maggiore o uguale a 0.");
		if (!canzonePresente(idCanzone))
		{
			return albero.add(idCanzone);
		}
		return false;
	}

	/**
	 * Aggiunge alla playlist la canzone passata come argomento.
	 * @param c l'oggetto della classe Canzone da aggiungere
	 */
	public void aggiungiCanzone(Canzone c)
	{
		if(c == null) throw new NullPointerException("Non si puo' aggiungere un'istanza null.");
		aggiungiCanzone(c.getId());
	}

	/**
	 * Rimuove la canzone il cui ID e' passato come argomento dalla playlist che esegue il metodo.
	 * @param idCanzone la stringa che identifica la canzone da rimuovere
	 */
	public void rimuoviCanzone(String idCanzone)
	{
		if(idCanzone == null) throw new NullPointerException("L'id della canzone deve essere maggiore o uguale a 0.");
		albero.remove(idCanzone);
	}

	/**
	 * Rimuove la canzone passata come argomento dalla playlist che esegue il metodo.
	 * @param c l'oggetto della classe Canzone da rimuovere
	 */
	public void rimuoviCanzone(Canzone c)
	{
		if(c == null) throw new NullPointerException("Non si puo' rimuovere un'istanza null.");
		rimuoviCanzone(c.getId());
	}

	/**
	 * Verifica che nella playlist sia presente la canzone il cui ID e' passato come argomento.
	 * @param idCanzone la stringa che identifica la canzone
	 * @return true se la canzone e' presente nella playlist, false altrimenti
	 */
	public boolean canzonePresente(String idCanzone)
	{
		return albero.contains(idCanzone);
	}

	/**
	 * Verifica che nella playlist sia presente la canzone passata come argomento.
	 * @param c l'oggetto della classe canzone
	 * @return true se la canzone e' presente nella playlist, false altrimenti
	 */
	public boolean canzonePresente(Canzone c)
	{
		return canzonePresente(c.getId());
	}

	/**
	 * Permette di visualizzare i dettagli della playlist che esegue il metodo.
	 * @return una stringa contenente i dettagli della playlist
	 */
	@Override
	public String toString() {
		return "ID: " + idPlaylist + " , Nome: " + titolo + " , IDPersona: " + idPersona + "\n" ;
	}

	/**
	 * Assegna l'intero passato come argomento al contatore della classe. Tiene traccia del numero di playlist create dall'ultimo avvio dell'applicazione.
	 * @param id l'intero da assegnare
	 */
	public static void setCount(int id)
	{
		COUNT = id;
	}
}
