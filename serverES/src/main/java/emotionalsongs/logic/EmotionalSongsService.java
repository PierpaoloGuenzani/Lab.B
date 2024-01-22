package emotionalsongs.logic;

import common.*;
import emotionalsongs.DAO.PerceptionSQLDB;
import emotionalsongs.DAO.PlaylistSQLDB;
import emotionalsongs.DAO.SongSQLDB;
import emotionalsongs.DAO.UserSQLDB;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


/**
 * Questa classe rappresenta il cuore dell'applicazione "EmotionalSong", orchestrando le funzionalità
 * principali attraverso un approccio singleton, garantendo un'unica istanza dell'applicazione e semplificando
 * la gestione centralizzata dei dati.
 *
 * Con un sistema di autenticazione basato su login/logout, l'applicazione concede l'accesso esclusivamente
 * agli utenti registrati.
 *
 * Oltre alle operazioni di base, come la ricerca di brani e artisti, la classe si occupa della creazione
 * e gestione di playlist, dell'inserimento di emozioni associate ai brani, e altro ancora.
 *
 * La classe implementa il pattern Singleton attraverso il metodo statico "getInstance()" per garantire
 * la presenza di un'unica istanza dell'applicazione.
 */
public class EmotionalSongsService implements EmotionalSongsInterface
{
    private SortedSet<String> userLoggedIn;
    private Canzoni canzoni;
    private Persone persone;
    private Percezioni percezioni;
    private Playlists playlists;
    private static EmotionalSongsService instance;

    /**
     * Costruttore privato della classe EmotionalSongsService. Questo costruttore è chiamato solo
     * internamente per garantire l'implementazione del pattern singleton e la gestione centralizzata
     * dei dati dell'applicazione.
     *
     * Durante la creazione dell'istanza, vengono inizializzati i principali componenti dell'applicazione,
     * inclusi i set di utenti loggati, le istanze di gestione per canzoni, persone, percezioni e playlist.
     *
     *
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     */
    private EmotionalSongsService() throws IOException
    {
        // Inizializzazione del set di utenti loggati in modo sicuro per thread
        userLoggedIn = Collections.synchronizedSortedSet(new TreeSet<String>());
        // Inizializzazione delle istanze singleton per gestione dati
        canzoni = Canzoni.getInstance();
        persone = Persone.getInstance();
        percezioni = Percezioni.getInstance();
        playlists = Playlists.getInstance();
    }
    
    /**
     * Metodo per stabilire la connessione al database PostgreSQL utilizzando le classi JDBC.
     *
     * @param url - l'url del database, se nulla utilizzo le impostazioni di default (jdbc:postgresql:dbES)
     * @param user - l'user per stabilire la connessione al db, se nulla utilizzo le impostazioni di default (postgres)
     * @param password - la password per connettersi al db, se nulla utilizzo le impostazioni di default (admin)
     * @throws SQLException - in caso di errore col db
     */
    public void setDBs(String url, String user, String password) throws SQLException
    {
        Connection c;
        if(url == null && user == null && password == null)
            c = DriverManager.getConnection("jdbc:postgresql:dbES","postgres","admin");
        else
            c = DriverManager.getConnection(url, user, password);
        canzoni.setDb(new SongSQLDB(c));
        persone.setDB(new UserSQLDB(c));
        percezioni.setDb(new PerceptionSQLDB(c));
        playlists.setDB(new PlaylistSQLDB(c));
    }

    /**
     * Ottiene o crea l'istanza singleton della classe EmotionalSongsService, seguendo l'approccio singleton.
     * Questo metodo garantisce un'unica istanza dell'applicazione e gestisce eventuali errori di Input/Output
     * durante la creazione dell'istanza.
     *
     * @return L'istanza singleton di EmotionalSongsService.
     * @throws IOException In caso di errore di Input/Output relativo al database durante la creazione dell'istanza.
     */
    public static EmotionalSongsService getInstance() throws IOException
    {
        if(instance == null)
            instance = new EmotionalSongsService();
        return instance;
    }

    /**
     * Effettua una ricerca di brani basata sul titolo fornito.
     *
     * @param titolo Il titolo della canzone o una parola contenuta nel titolo da cercare.
     * @return Una lista di brani il cui titolo contiene la stringa fornita come argomento.
     *         Se nessun brano corrisponde alla ricerca, viene restituita una lista vuota.
     * @see Canzoni#cercaBranoMusicale(String)
     */
    @Override
    public List<Canzone> cercaBranoMusicale(String titolo)
    {
        return canzoni.cercaBranoMusicale(titolo);
    }

    /**
     * Ricerca i brani creati da un determinato artista in un dato anno.
     *
     * @param nomeArtista Il nome dell'artista da cercare.
     * @param anno        L'anno di rilascio dei brani da cercare.
     * @return Una lista di brani creati dall'artista nel dato anno.
     *         Se nessun brano corrisponde alla ricerca, viene restituita una lista vuota.
     * @see Canzoni#cercaBranoMusicale(String, int)
     */
    @Override
    public List<Canzone> cercaBranoMusicale(String nomeArtista, int anno)
    {
        return canzoni.cercaBranoMusicale(nomeArtista, anno);
    }

    /**
     * Effettua la registrazione di un nuovo utente all'applicazione.
     *
     * @param newUtenteRegistrato l'utente da registrare
     * @return true se la registrazione è andata a buon fine, false altrimenti
     * @throws IOException in caso di errore di Input/Output relativo al database
     * @see Persone#Registrazione(UtenteRegistrato)
     */
    @Override
    public boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException
    {
        return persone.Registrazione(newUtenteRegistrato);
    }

    /**
     * Permette ad un utente di accedere all'applicazione con le credenziali inserite in fase di registrazione.
     *
     * @param userId   l'userId scelto in fase di registrazione
     * @param password la password scelta in fase di registrazione
     * @return true se l'accesso è andato a buon fine, false altrimenti
     * @see Persone#accedi(String, String)
     */
    @Override
    public boolean accedi(String userId, String password)
    {
        boolean accessoRiuscito = persone.accedi(userId, password);
        if(accessoRiuscito)
        {
            // Verifica se l'utente è già loggato se si restituisco false
            if(userLoggedIn.contains(userId))
            {
                return false;
            }
            userLoggedIn.add(userId);
        }
        return accessoRiuscito;
    }

    /**
     * Effettua il logout dell'utente dall'applicazione.
     *
     * @param idUtente l'ID dell'utente da disconnettere
     */
    public void logOut(String idUtente)
    {
        userLoggedIn.remove(idUtente);
    }

    /**
     * Restituisce la lista delle canzoni associate a una playlist specifica.
     *
     * @param idPlaylist l'ID della playlist da cui ottenere le canzoni
     * @return una lista di canzoni associate alla playlist o una lista vuota se la playlist non contiene canzoni
     */
    public List<Canzone> canzoniDaIdPlaylist(String idPlaylist)
    {
        Playlist playlist = playlists.cercaPlaylistPerId(idPlaylist);
        return canzoni.getCanzoni(playlist.getListaCanzoni());
    }

    /**
     * Registra una nuova playlist per l'utente corrente.
     *
     * @param nomePlaylist il nome della nuova playlist
     * @param userId l'ID dell'utente che registra la playlist
     * @return true se la registrazione della playlist è avvenuta con successo, false altrimenti
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     */
    public boolean RegistraPlaylist(String nomePlaylist, String userId) throws IOException
    {
        // Verifica se l'utente è autenticato
        if(nomePlaylist == null || nomePlaylist.isBlank()) return false;
        if(!userLoggedIn.contains(userId)) return false;
        Playlist newPlaylist = new Playlist(nomePlaylist, userId);
        // Aggiunge la nuova playlist al sistema di gestione delle playlist
        playlists.aggiungiPlaylist(newPlaylist);
        return true;
    }

    /**
     * Aggiunge una canzone alla playlist specificata.
     *
     * @param idCanzone la stringa che identifica la canzone da aggiungere
     * @param idPlaylist l'ID della playlist a cui aggiungere la canzone
     * @param userId l'ID dell'utente che sta aggiungendo la canzone
     * @return true se la canzone è stata aggiunta alla playlist con successo, false altrimenti
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     * @see Playlists#aggiungiCanzone(String, String)
     */
    @Override
    public boolean aggiungiCanzone(String idCanzone, String idPlaylist, String userId) throws IOException
    {
        // Verifica se l'utente è autenticato
        if(!userLoggedIn.contains(userId)) return false;
        return playlists.aggiungiCanzone(idCanzone, idPlaylist);
    }

    /**
     * Aggiunge e salva una nuova percezione associata a una canzone.
     *
     * @param newPercezione la percezione da aggiungere
     * @param userId l'ID dell'utente che sta inserendo la percezione
     * @return true se la percezione è stata aggiunta con successo, false altrimenti
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     * @see Percezioni#add(Percezione)
     */
    @Override
    public boolean inserisciEmozioni(Percezione newPercezione, String userId) throws IOException
    {
        // Verifica se l'utente è autenticato
        if(userLoggedIn.contains(userId))
        {
            // Se l'utente è autenticato, aggiunge la percezione chiamando il metodo della classe Percezioni
            return percezioni.add(newPercezione);
        }
        return false;
    }

    /**
     * Ricerca una canzone mediante l'ID fornito come argomento.
     *
     * @param idCanzone l'ID della canzone da restituire
     * @return la Canzone corrispondente all'ID, o null se non trovata
     * @see Canzoni#getCanzone(String)
     */
    public Canzone cercaCanzone(String idCanzone)
    {
        return canzoni.getCanzone(idCanzone);
    }

    /**
     * Ricerca i brani creati da un artista.
     * @param autore il nome dell'artista 
     * @return la lista dei brani dell'artista specificato o una lista vuota nel caso in cui non sia presente nel database
     * @see Canzoni#cercaBraniPerAutore(String) 
     */
    @Override
    public List<Canzone> cercaBraniPerAutore(String autore)
    {
        return canzoni.cercaBraniPerAutore(autore);
    }

    /**
     * Visualizza un prospetto riassuntivo delle emozioni associate a una canzone.
     *
     * @param idCanzone l'ID della canzone per cui visualizzare le emozioni
     * @return il prospetto riassuntivo delle emozioni
     * @throws RemoteException se si verifica un errore durante l'esecuzione remota
     */@Override
    public ProspettoRiassuntivo visualizzaEmozioni(String idCanzone) throws RemoteException
    {
        // Cerca le emozioni associate alla canzone con l'ID specificato
        List<Percezione>  possibiliEmozioni = percezioni.cercaEmozioni(idCanzone);
        // Array delle emozioni disponibili
        Emozione[] emozione = Emozione.values();
        int n_enum = emozione.length;
        // Inizializza i contatori e accumulatori per calcolare le medie
        int[] countEmozione = new int[n_enum];
        long[] totaleEmozione = new long[n_enum];
        float[] mediaEmozione = new float[n_enum];
        // Se non ci sono emozioni, crea un prospetto vuoto
        if (possibiliEmozioni.isEmpty())
        {
            ProspettoRiassuntivo p = new ProspettoRiassuntivo();
            p.setMedieEmozioni(mediaEmozione);
        }
        // Calcola le medie delle emozioni
        for (Percezione e : possibiliEmozioni) {
            int n = e.getEmozione().ordinal();
            countEmozione[n]++;
            totaleEmozione[n] += e.getScore();
        }
        // Calcola le medie effettive
        for (int i = 0; i < n_enum; i++) {
            if (countEmozione[i] != 0) {
                mediaEmozione[i] = (float) totaleEmozione[i] / countEmozione[i];
            }
        }
        // Crea un nuovo prospetto e imposta le medie delle emozioni
        ProspettoRiassuntivo prospetto = new ProspettoRiassuntivo();
        prospetto.setMedieEmozioni(mediaEmozione);
        // Aggiunge i commenti al prospetto
        for (Percezione p : possibiliEmozioni)
        {
            String commento = p.getNote();
            if(commento != null && !commento.isBlank()) prospetto.addCommento(commento);
        }
        return prospetto;
    }
    
    /**
     * Restituisce la lista delle playlist create da un utente
     *
     * @param idUtente L'ID dell'utente di cui cercare le playlist.
     * @return List<Playlist> - la lista delle playlist
     * @throws RemoteException se si verifica un errore durante l'esecuzione remota
     */
    @Override
    public List<Playlist> cercaPlaylistPerUtente(String idUtente) throws RemoteException
    {
        return playlists.cercaPlaylistPerIdPersona(idUtente);
    }
    
    
    /**
     * Verifica che tra le playlist create da un utente sia presente un determinato brano.
     *
     * @param idUtente l'ID dell'utente
     * @param idCanzone l'ID della canzone da cercare
     * @return true se la canzone è presente in almeno una delle playlist dell'utente, false altrimenti
     * @see Playlists#controllaCanzonePersona(String, String)
     */
    public boolean controllaCanzoneUtente(String idUtente, String idCanzone)
    {
        return playlists.controllaCanzonePersona(idUtente, idCanzone);
    }

    /**
     * Controlla se un userID è già in uso.
     * @param UserID l'ID dell'utente da controllare
     * @return true se l'ID è già presente, false altrimenti
     */
    @Override
    public boolean controlloUserid(String UserID)
    {
        return persone.contains(UserID);
    }

    /**
     * Converte una stringa in un intero.
     *
     * @param s la stringa da convertire
     * @return l'intero convertito, o Integer.MAX_VALUE in caso di errore di conversione
     */
    private static int convertitore(String s)
    {
        int n;
        try
        {
            n = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            // In caso di errore di conversione, imposta n a Integer.MAX_VALUE
            n = Integer.MAX_VALUE; }
        return n;
    }
}
