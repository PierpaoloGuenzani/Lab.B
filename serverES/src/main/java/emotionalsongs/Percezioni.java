package emotionalsongs;

import common.Percezione;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe per la gestione delle percezioni.
 * @author Paradiso Fabiola 749727 VA
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 * @author Cuvato Paolo 748691 VA
 */
public class Percezioni {
    private static final String dbFile = "data/Emozioni.dati.txt";
    private PerceptionDAOInterface db;
    private ConcurrentHashMap<String, List<Percezione>> mappa;
    private static Percezioni instance;

    /**
     * Crea un oggetto che raccoglie in una lista le percezioni presenti nel database e ne permette la gestione.
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     */
    private Percezioni() throws IOException {}
    
    public static Percezioni getInstance() throws IOException
    {
        if(instance == null)
            instance = new Percezioni();
        return instance;
    }

    /**
     * Restituisce il database che contiene tutte le percezioni.
     * @return un'interfaccia del database
     */
    public PerceptionDAOInterface getDb() {
        return db;
    }

    /**
     * Assegna un database.
     * @param db l'implementazione dell'interfaccia DB da assegnare
     */
    public void setDb(PerceptionDAOInterface db) {
        this.db = db;
        update();
    }

    /**
     * Ricerca nel database le emozioni associate al brano.
     * @param idCanzone la stringa che identifica la canzone
     * @return la lista di emozioni associate alla canzone
     */
    public List<Percezione> cercaEmozioni(String idCanzone) {
        if(mappa.containsKey(idCanzone))
            return mappa.get(idCanzone);
        return new LinkedList<Percezione>();
    }

    /**
     * Aggiunge e salva una nuova percezione.
     * @param newPercezione la Percezione da aggiungere
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     */
    public void add(Percezione newPercezione) throws IOException {
        if(mappa.containsKey(newPercezione.getSongId()))
        {
            List<Percezione> l = mappa.get(newPercezione.getSongId());
            l.add(newPercezione);
        }
        else
        {
            LinkedList<Percezione> l = new LinkedList<>();
            l.add(newPercezione);
            mappa.put(newPercezione.getSongId(),l);
        }
        db.save(newPercezione);
    }

    /**
     * Controlla che un utente non abbia gia' inserito le proprie emozioni per una canzone.
     * @param idPersona l'ID dell'utente
     * @param idCanzone l'ID della canzone
     * @return true se un utente ha gia' inserito emozioni, false altrimenti
     */
    public boolean controllaEmozioniPersona(String idPersona, String idCanzone){
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
    
    public boolean update()
    {
        if(db == null)
            return false;
        mappa = db.getAll();
        return true;
    }
    
}
