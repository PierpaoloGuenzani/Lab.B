package common;

import java.io.Serializable;

/**
 * Contiene le emozioni che possono essere selezionate dall'utente.
 * @author Paradiso Fabiola 749727 VA
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 * @author Cuvato Paolo 748691 VA
*/
public enum Emozione implements Serializable
{
    AMAZEMENT,
    SOLEMNITY,
    TENDERNESS,
    NOSTALGIA,
    CALMNESS,
    POWER,
    JOY,
    TENSION,
    SADNESS;
    
    public String[] names()
    {
        int n = values().length;
        String[] names = new String[n];
        for (int i = 0; i < n; i++)
        {
            names[i] = values()[i].name();
        }
        return names;
    }
}
