package common;

import java.io.Serializable;

/**
 * Le istanze della classe {@code Percezione} rappresentano tutto ciò che è associato all'emozione selezionata.
 * Ogni percezione contiene informazioni sull'emozione, il punteggio, l'ID della canzone associata e l'ID dell'utente che
 * ha creato la percezione.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class Percezione implements Serializable {
    
    private final String songId;
    private final String userId;
    private final Emozione emozione;
    private int score;
    private String note;

    /**
     * Costruisce un oggetto {@code Percezione} che contiene i dati associati all'emozione.
     *
     * @param emozione l'emozione selezionata
     * @param score    il punteggio che rappresenta il grado d'intensità
     * @param songId   l'ID della canzone associata all'emozione
     * @param userId   l'ID dell'utente a cui è associata l'emozione
     * @throws NullPointerException se uno degli argomenti è nullo
     */
    public Percezione(Emozione emozione, int score, String songId, String userId) {
        if (emozione == null) throw new NullPointerException("L'emozione non può essere un valore nullo.");
        if (score == 0) throw new NullPointerException("Lo score non può essere un valore nullo.");
        if (songId == null) throw new NullPointerException("L'id della canzone non può essere un valore nullo.");
        if (userId == null) throw new NullPointerException("L'id dell'utente non può essere un valore nullo.");

        this.emozione = emozione;
        this.score = score;
        this.songId = songId;
        this.userId = userId;
    }

    /**
     * Restituisce l'ID della canzone associata alla Percezione.
     *
     * @return l'ID della canzone
     */
    public String getSongId() {
        return songId;
    }

    /**
     * Restituisce l'ID dell'utente che ha creato la Percezione.
     *
     * @return l'ID dell'utente
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Restituisce l'emozione corrispondente alla Percezione.
     *
     * @return l'emozione corrispondente
     * @see Emozione
     */
    public Emozione getEmozione() {
        return emozione;
    }

    /**
     * Restituisce l'intensità dell'emozione corrispondente alla Percezione.
     *
     * @return l'intensità dell'emozione
     */
    public int getScore() {
        return score;
    }

    /**
     * Aggiunge uno score alla Percezione.
     *
     * @param score numero da 1 a 5 che definisce l'intensità dell'emozione
     */
    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * Restituisce il commento inserito dall'utente nella Percezione.
     *
     * @return il commento relativo
     */
    public String getNote() {
        return note;
    }

    /**
     * Aggiunge un commento alla Percezione.
     *
     * @param note il commento da aggiungere
     */
    public void aggiungiNote(String note){
        this.note = note;
    }
}

