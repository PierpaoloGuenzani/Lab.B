package common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ProspettoRiassuntivo implements Serializable
{
	float[] medieEmozioni;
	List<String> note;
	
	public ProspettoRiassuntivo(float[] medieEmozioni)
	{
		this.medieEmozioni = medieEmozioni;
	}
	
	public void addCommento(String newCommento)
	{
		if(note == null)
			note = new LinkedList<>();
		note.add(newCommento);
	}
}
