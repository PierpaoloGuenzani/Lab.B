package emotionalsongs;

import common.Canzone;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe per la gestione dei brani musicali.
 * @author Cuvato Paolo 748691 VA
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 * @author Paradiso Fabiola 749727 VA
 */
public class Canzoni {
    private static final String dbFile = "data/Canzoni.dati.txt";
    private Dao<Canzone> db;
    private ConcurrentHashMap<String, Canzone> mappa;
    
    /**
     * Crea un oggetto che raccoglie le canzoni presenti e ne permette la gestione.
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     */
    Canzoni() throws IOException
    {}

    /**
     * Restituisce il database che contiene tutte le canzoni.
     * @return un'interfaccia del database
     */
    public Dao<Canzone> getDb() {
        return db;
    }

    /**
     * Assegna un database.
     * @param db l'implementazione dell'interfaccia DB da assegnare
     */
    public void setDb(Dao<Canzone> db) {
        if (db == null) throw new NullPointerException("File db non può essere un valore nullo");
        this.db = db;
        update();
    }

    /**
     * Ricerca i brani nel cui titolo è presente una determinata parola.
     * @param parola la stringa da cercare
     * @return una lista di brani i cui titoli contengono la stringa passata come argomento o una lista vuota nel caso in cui nessun brano corrisponda a quello cercato
     */
    public List<Canzone> cercaBranoMusicale(String parola) {
        LinkedList<Canzone> listaBrani= new LinkedList<Canzone>();
        String nomeBranoMaius = parola.toUpperCase();
        for (Canzone c: mappa.values()) {
            if (c.getTitolo().toUpperCase().contains(nomeBranoMaius))
                listaBrani.add(c);
        }
        return listaBrani;
    }

    /**
     * Ricerca i brani creati da un artista in un determinato anno.
     * @param artista il nome dell'artista
     * @param anno l'anno di rilascio della canzone
     * @return la lista dei brani rilasciati da tale artista nell'anno specificato o una lista vuota nel caso in cui non sia presente nel database
     */
    public List<Canzone> cercaBranoMusicale(String artista, int anno) {
        LinkedList<Canzone> listaBrani= new LinkedList<Canzone>();
        String nomeArtistaMaius = artista.toUpperCase();
        for (Canzone c : mappa.values()) {
            if( c.getArtista().toUpperCase().equals(nomeArtistaMaius) && anno == c.getAnno() ){
                listaBrani.add(c);
            }
        }
        return listaBrani;
    }

    /**
     * Ricerca i brani creati da un artista.
     * @param artista il nome dell'artista
     * @return la lista dei brani dell'artista specificato o una lista vuota nel caso in cui non sia presente nel database
     */
    public List<Canzone> cercaBraniPerAutore(String artista) {
        LinkedList<Canzone> listaBrani= new LinkedList<Canzone>();
        String nomeArtistaMaius = artista.toUpperCase();
        for (Canzone c : mappa.values()) {
            if( c.getArtista().toUpperCase().equals(nomeArtistaMaius) ){
                listaBrani.add(c);
            }
        }
        return listaBrani;
    }

    /**
     * Restituisce una canzone in base all'ID passato.
     * @param idCanzone l'ID della canzone da restituire
     * @return la canzone associata all'ID o null in caso non esista
     */
    public Canzone getCanzone(String idCanzone) {
        return mappa.get(idCanzone);
    }
    
    /**
     * Restituisce una lista di canzoni in base agli ID passati.
     * @param listIdCanzoni lista contenete tutti gli ID delle canzoni
     * @return lista di canzoni
     */
    public List<Canzone> getCanzoni(List<String> listIdCanzoni)
    {
        LinkedList<Canzone> l = new LinkedList<>();
        listIdCanzoni.forEach(id -> l.add(mappa.get(id)));
        return l;
    }
    
    public boolean update()
    {
        if(db == null)
            return false;
        mappa = db.getAll();
        return true;
    }
}