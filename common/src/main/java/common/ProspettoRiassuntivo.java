package common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ProspettoRiassuntivo implements Serializable
{
	private float[] medieEmozioni;
	//TODO: inviare più dati
	private List<String> note;
	
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
	
	public void addCommento(String newCommento)
	{
		/*if(note == null)
			note = new LinkedList<>();*/
		note.add(newCommento);
	}
	
	public void setMedieEmozioni(float[] medieEmozioni)
	{
		this.medieEmozioni = medieEmozioni;
	}
	
	public float[] getMedieEmozioni()
	{
		return medieEmozioni;
	}
	
	public List<String> getNote()
	{
		return note;
	}
	
}
