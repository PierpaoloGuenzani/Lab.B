package emotionalsongs.logic;

import common.Canzone;
import emotionalsongs.DAO.Dao;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe per la gestione dei brani musicali.
 * Questa classe rappresenta un gestore per le operazioni relative ai brani musicali,
 * offrendo funzionalità di ricerca, aggiornamento e accesso ai dati.
 * @throws IOException se si verifica un errore di Input/Output relativo al database.
 */
public class Canzoni {
    //private static final String dbFile = "data/Canzoni.dati.txt";
    private Dao<Canzone> db;
    private ConcurrentHashMap<String, Canzone> mappa;
    private static Canzoni instance;

    /**
     * Costruttore privato per garantire un'unica istanza della classe.
     * Il costruttore è vuoto poiché tutte le inizializzazioni sono gestite nel blocco statico o dai metodi della classe.
     * È chiamato solo una volta dal metodo getInstance().
     * @throws IOException se si verifica un errore di Input/Output relativo al database.
     */
    private Canzoni() throws IOException
    {}

    /**
     * Restituisce l'istanza singleton della classe Canzoni.
     * Se l'istanza non è ancora creata, ne crea una nuova.
     * @return un'istanza di Canzoni.
     * @throws IOException se si verifica un errore di Input/Output relativo al database.
     */
    public static Canzoni getInstance() throws IOException
    {
        if(instance == null)
            instance = new Canzoni();
        return instance;
    }

    /**
     * Restituisce il database contenente tutte le canzoni.
     * @return un'interfaccia del database.
     */
    public synchronized Dao<Canzone> getDb() {
        return db;
    }

    /**
     * Imposta il database delle canzoni.
     * @param db l'implementazione dell'interfaccia DB da assegnare.
     */
    public synchronized void setDb(Dao<Canzone> db) {
        if (db == null) throw new NullPointerException("File db non può essere un valore nullo");
        this.db = db;
        update();
    }

    /**
     * Ricerca i brani il cui titolo contiene una specifica parola.
     * @param parola la stringa da cercare.
     * @return una lista di brani il cui titolo contiene la stringa passata come argomento o una lista vuota se nessun brano corrisponde.
     */
    public synchronized List<Canzone> cercaBranoMusicale(String parola) {
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
     * @param artista il nome dell'artista.
     * @param anno l'anno di rilascio della canzone.
     * @return la lista dei brani rilasciati da tale artista nell'anno specificato o una lista vuota se non è presente nel database.
     */
    public synchronized List<Canzone> cercaBranoMusicale(String artista, int anno) {
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
     * @param artista il nome dell'artista.
     * @return la lista dei brani dell'artista specificato o una lista vuota se non è presente nel database.
     */
    public synchronized List<Canzone> cercaBraniPerAutore(String artista) {
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
     * @param idCanzone l'ID della canzone da restituire.
     * @return la canzone associata all'ID o null se non esiste.
     */
    public synchronized Canzone getCanzone(String idCanzone) {
        return mappa.get(idCanzone);
    }

    /**
     * Restituisce una lista di canzoni in base agli ID passati.
     * @param listIdCanzoni lista contenente tutti gli ID delle canzoni.
     * @return lista di canzoni.
     */
    public synchronized List<Canzone> getCanzoni(List<String> listIdCanzoni)
    {
        LinkedList<Canzone> l = new LinkedList<>();
        listIdCanzoni.forEach(id -> l.add(mappa.get(id)));
        return l;
    }

    /**
     * Aggiorna la mappa delle canzoni con i dati dal database.
     * @return True se l'operazione di aggiornamento è riuscita, altrimenti False.
     */
    public synchronized boolean update()
    {
        if(db == null)
            return false;
        mappa = db.getAll();
        return true;
    }
}