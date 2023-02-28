package emotionalsongs.common;

import java.io.Serializable;

/**
 * Ogni sua istanza rappresenta una canzone e raccoglie i suoi dettagli.
 * @author Cuvato Paolo 748691 VA
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 * @author Paradiso Fabiola 749727 VA
 */
public class Canzone implements Serializable, Comparable<Canzone>
{
	private final String id;
	private String titolo;
	private String artista;
	private int anno;

	/**
	 * Permette di creare una nuova istanza della classe Canzone.
	 * @param id la stringa che identifica univocamente un'istanza
	 * @param titolo il titolo dell'istanza
	 * @param artista l'artista dell'istanza
	 * @param anno l'anno di pubblicazione dell'istanza
	 */
	public Canzone(String id, String titolo, String artista, int anno)
	{
		if (id.equals("")) throw new NullPointerException("Id non può essere un valore nullo.");
		if (titolo.equals("")) throw new NullPointerException("Titolo non può essere un valore nullo.");
		if (artista.equals("")) throw new NullPointerException("Artista non può essere un valore nullo.");
		if (anno == 0) throw new NullPointerException("Anno non può essere 0.");

		this.id = id;
		this.titolo = titolo;
		this.artista = artista;
		this.anno = anno;
	}

	/**
	 * Restituisce l'ID dell'istanza che esegue il metodo.
	 * @return la stringa contenente l'ID della canzone
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Restituisce il titolo dell'istanza che esegue il metodo.
	 * @return la stringa contenente il titolo della canzone
	 */
	public String getTitolo()
	{
		return titolo;
	}

	/**
	 * Associa il titolo passato come parametro al campo titolo.
	 * @param titolo il titolo della canzone
	 */
	public void setTitolo(String titolo)
	{
		if (titolo.equals("")) throw new NullPointerException("Il titolo non può essere un valore nullo.");
		this.titolo = titolo;
	}

	/**
	 * Restituisce la stringa contenente il nome dell'artista.
	 * @return la stringa contenente l'artista della canzone
	 */
	public String getArtista()
	{
		return artista;
	}

	/**
	 * Associa il nome dell'artista passato come parametro al campo artista.
	 * @param artista il nome dell'artista
	 */
	public void setArtista(String artista)
	{
		if (artista.equals("")) throw new NullPointerException("L'artista non può essere un valore nullo.");
		this.artista = artista;
	}

	/**
	 * Restituisce il valore del campo anno.
	 * @return l'intero corrispondente all'anno di rilascio della canzone
	 */
	public int getAnno() {
		return anno;
	}

	/**
	 * Associa l'anno fornito come parametro al campo anno dell'istanza che esegue il metodo.
	 * @param anno l'anno di rilascio della canzone
	 */
	public void setAnno(int anno) {
		if (anno == 0) throw new NullPointerException("L'anno non può essere 0.");
		this.anno = anno;
	}

	/**
	 * Confronta la canzone fornita come argomento con quella che esegue il metodo.
	 * @see String#compareTo(String)
	 * @param canzone la canzone da confrontare
	 * @return ritorna un intero negativo, positivo o pari a zero se rispettivamente l'oggetto che esegue il metodo e' minore, maggiore o uguale all'oggetto fornito come argomento
	 * @throws NullPointerException se l'oggetto fornito come argomento e' null
	 * @throws ClassCastException se il tipo dell'oggetto fornito come argomento non gli permette di essere confrontato con quello che esegue il metodo
	 */
	@Override
	public int compareTo(Canzone canzone) {
		if(canzone == null) throw new NullPointerException("Comparazione fallita.");
		return id.compareTo(canzone.id);
	}

	/**
	 * Ritorna una stringa contenente tutti i dettagli dell'istanza che esegue il metodo.
	 * @return una stringa contenente i dettagli della canzone
	 */
	@Override
	public String toString()
	{
		return "ID: " + id + " , Titolo: " + titolo + " , Artista: " + artista + " , Anno: " + anno + "\n";
	}

}