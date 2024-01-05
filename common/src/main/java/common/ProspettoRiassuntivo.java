package common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Rappresenta un prospetto riassuntivo contenente medie di emozioni e note associate.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class ProspettoRiassuntivo implements Serializable
{
	private float[] medieEmozioni;
	//TODO: inviare più dati
	private List<String> note;

	/**
	 * Costruisce un nuovo oggetto ProspettoRiassuntivo inizializzando la lista delle note.
	 */
	public ProspettoRiassuntivo()
	{
		note = new LinkedList<>();
	}
	
	/*public ProspettoRiassuntivo(float[] medieEmozioni)
	{
		int n =medieEmozioni.length;
		this.medieEmozioni = new Float[n];
		for(int i = 0; i < n; n++ )
		{
			this.medieEmozioni[i] = medieEmozioni[i];
		}
	}*/

	/**
	 * Aggiunge un commento alla lista delle note.
	 *
	 * @param newCommento il commento da aggiungere
	 */
	public void addCommento(String newCommento)
	{
		/*if(note == null)
			note = new LinkedList<>();*/
		note.add(newCommento);
	}

	/**
	 * Imposta le medie delle emozioni nel prospetto riassuntivo.
	 *
	 * @param medieEmozioni un array di float rappresentante le medie delle emozioni
	 */
	public void setMedieEmozioni(float[] medieEmozioni)
	{
		this.medieEmozioni = medieEmozioni;
	}

	/**
	 * Restituisce le medie delle emozioni presenti nel prospetto riassuntivo.
	 *
	 * @return un array di float rappresentante le medie delle emozioni
	 */
	public float[] getMedieEmozioni()
	{
		return medieEmozioni;
	}

	/**
	 * Restituisce la lista di note associate al prospetto riassuntivo.
	 *
	 * @return la lista di note
	 */
	public List<String> getNote()
	{
		return note;
	}
	
}
