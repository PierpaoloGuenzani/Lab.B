package common;

import java.io.Serializable;

/**
 * Le sue istanze rappresentano tutto cio' che viene associato all'emozione selezionata.
 * @author Paradiso Fabiola 749727 VA
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 * @author Cuvato Paolo 748691 VA
 */
public class Percezione implements Serializable {
    
    private final Emozione emozione;
    private final int score;
    private String note;
    private final String songId;
    private final String userId;

    /**
     * Costruisce un oggetto che contiene i dati associati all'emozione.
     * @param emozione l'emozione selezionata
     * @param score il punteggio che rappresenta il grado d'intensita'
     * @param songId l'ID della canzone associata all'emozione
     * @param userId l'ID dell'utente a cui e' associata l'emozione
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
     * Restituisce l'emozione corrispondente alla Percezione.
     * @return l'emozione corrispondente
     * @see Emozione
     */
    public Emozione getEmozione() {
        return emozione;
    }

    /**
     * Restituisce l'intensita' dell'emozione corrispondente alla Percezione.
     * @return l'intensita' dell'emozione
     */
    public int getScore() {
        return score;
    }

    /**
     * Restituisce il commento inserito dall'utente nella Percezione.
     * @return il commento relativo
     */
    public String getNote() {
        return note;
    }

    /**
     * Restituisce l'ID della canzone associata alla Percezione.
     * @return l'ID della canzone
     */
    public String getSongId() {
        return songId;
    }

    /**
     * Restituisce l'ID dell'utente che ha creato la Percezione.
     * @return l'ID dell'utente
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Aggiunge un commento alla Percezione.
     * @param note il commento da aggiungere
     */
    public void aggiungiNote(String note){
        this.note = note;
    }
}

