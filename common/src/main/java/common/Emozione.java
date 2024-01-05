package common;

import java.io.Serializable;

/**
 * L'enum {@code Emozione} rappresenta le emozioni che possono essere selezionate dall'utente.
 * Ogni costante al suo interno rappresenta una specifica emozione.
 *
 * Le emozioni disponibili sono:
 * <ul>
 *     <li>{@code AMAZEMENT}</li>
 *     <li>{@code SOLEMNITY}</li>
 *     <li>{@code TENDERNESS}</li>
 *     <li>{@code NOSTALGIA}</li>
 *     <li>{@code CALMNESS}</li>
 *     <li>{@code POWER}</li>
 *     <li>{@code JOY}</li>
 *     <li>{@code TENSION}</li>
 *     <li>{@code SADNESS}</li>
 * </ul>
 *
 * Implementa l'interfaccia {@code Serializable} per consentire la serializzazione degli oggetti di questo tipo.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
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

    /**
     * Restituisce un array di stringhe contenente i nomi delle emozioni disponibili.
     * @return Un array di stringhe con i nomi delle emozioni.
     */
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
