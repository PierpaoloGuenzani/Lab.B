package emotionalsongs.logic;

import common.Percezione;
import emotionalsongs.DAO.PerceptionDAOInterface;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe per la gestione delle percezioni.
 * Questa classe gestisce le operazioni relative alle percezioni musicali, fornendo funzionalità di ricerca,
 * aggiornamento e accesso ai dati associati alle emozioni degli utenti.
 * @throws IOException se si verifica un errore di Input/Output relativo al database.
 */
public class Percezioni {
    //private static final String dbFile = "data/Emozioni.dati.txt";
    private PerceptionDAOInterface db;
    private ConcurrentHashMap<String, List<Percezione>> mappa;
    private static Percezioni instance;

    /**
     * Costruttore privato per garantire un'unica istanza della classe.
     * Il costruttore è vuoto poiché tutte le inizializzazioni sono gestite nel blocco statico o dai metodi della classe.
     * È chiamato solo una volta dal metodo getInstance().
     * @throws IOException se si verifica un errore di Input/Output relativo al database.
     */
    private Percezioni() throws IOException {}

    /**
     * Restituisce l'istanza singleton della classe Percezioni.
     * Se l'istanza non è ancora creata, ne crea una nuova.
     * @return un'istanza di Percezioni.
     * @throws IOException se si verifica un errore di Input/Output relativo al database.
     */
    // Il costruttore Percezioni() è coinvolto nell'inizializzazione dell'oggetto,
    // ma la creazione effettiva dell'istanza è controllata e regolata dal metodo getInstance()
    public static Percezioni getInstance() throws IOException
    {
        if(instance == null)
            instance = new Percezioni();
        return instance;
    }

    /**
     * Restituisce il database che contiene tutte le percezioni.
     * @return un'interfaccia del database.
     */
    public synchronized PerceptionDAOInterface getDb() {
        return db;
    }

    /**
     * Imposta il database delle percezioni.
     * @param db l'implementazione dell'interfaccia DB da assegnare.
     */
    public synchronized void setDb(PerceptionDAOInterface db) {
        this.db = db;
        update();
    }

    /**
     * Ricerca nel database le percezioni associate a una canzone.
     * @param idCanzone l'ID della canzone.
     * @return la lista di percezioni associate alla canzone o una lista vuota se non sono presenti.
     */
    public synchronized List<Percezione> cercaEmozioni(String idCanzone) {
        if(mappa.containsKey(idCanzone))
            return mappa.get(idCanzone);
        return new LinkedList<Percezione>();
    }

    /**
     * Aggiunge e salva una nuova percezione nel database.
     * @param newPercezione la percezione da aggiungere.
     * @throws IOException se si verifica un errore di Input/Output relativo al database.
     */
    public synchronized boolean add(Percezione newPercezione) throws IOException {
        if(mappa.containsKey(newPercezione.getSongId()))
        {
            List<Percezione> l = mappa.get(newPercezione.getSongId());
            for (Percezione percezione : l)
            {
                if(percezione.getUserId().equals(newPercezione.getUserId())
                        && percezione.getEmozione().equals(newPercezione.getEmozione()))
                    return false;
            }
            l.add(newPercezione);
        }
        else
        {
            LinkedList<Percezione> l = new LinkedList<>();
            l.add(newPercezione);
            mappa.put(newPercezione.getSongId(),l);
        }
        db.save(newPercezione);
        return true;
    }

    /**
     * Controlla se un utente ha già inserito le proprie emozioni per una canzone.
     * @param idPersona l'ID dell'utente.
     * @param idCanzone l'ID della canzone.
     * @return true se l'utente ha già inserito emozioni, false altrimenti.
     */
    public synchronized boolean controllaEmozioniPersona(String idPersona, String idCanzone){
        List<Percezione> listaPercezioni = mappa.get(idCanzone);
        if(listaPercezioni == null)
            return false;
        for (Percezione percezione : listaPercezioni)
        {
            if(percezione.getUserId().equals(idPersona))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Aggiorna la mappa delle percezioni con i dati dal database.
     * @return True se l'operazione di aggiornamento è riuscita, altrimenti False.
     */
    public boolean update()
    {
        if(db == null)
            return false;
        mappa = db.getAll();
        return true;
    }
    
}
