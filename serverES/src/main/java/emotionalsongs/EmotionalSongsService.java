package emotionalsongs;

import common.*;
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
     * Viene inoltre stabilita la connessione al database PostgreSQL utilizzando le classi JDBC e viene
     * configurato l'accesso alle tabelle del database per ciascun componente.
     *
     * In caso di errore durante la connessione al database, viene stampata la traccia dell'eccezione.
     *
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     */
    private EmotionalSongsService() throws IOException {
        //new ServerGUI();
        // Inizializzazione del set di utenti loggati in modo sicuro per thread
        userLoggedIn = Collections.synchronizedSortedSet(new TreeSet<String>());
        // Inizializzazione delle istanze singleton per gestione dati
        canzoni = Canzoni.getInstance();
        persone = Persone.getInstance();
        percezioni = Percezioni.getInstance();
        playlists = Playlists.getInstance();
    
        try
        {
            // Connessione al database PostgreSQL
            Connection c = DriverManager.getConnection("jdbc:postgresql:dbES","postgres","admin");
            // Configurazione delle connessioni dei componenti al database
            canzoni.setDb(new SongSQLDB(c));
            persone.setDB(new UserSQLDB(c));
            percezioni.setDb(new PerceptionSQLDB(c));
            playlists.setDB(new PlaylistSQLDB(c));
        } catch (SQLException e)
        {
            // Stampa della traccia dell'eccezione in caso di errore di connessione al database
            e.printStackTrace();
        }
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
            // Se l'istanza non esiste, crea una nuova istanza
            instance = new EmotionalSongsService();
        // Restituisce l'istanza esistente o appena creata
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
    public List<Canzone> cercaBranoMusicale(String titolo) {
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
    public List<Canzone> cercaBranoMusicale(String nomeArtista, int anno) {
        return canzoni.cercaBranoMusicale(nomeArtista, anno);
    }

    /**
     * Visualizza le emozioni associate a un brano identificato dall'ID specificato.
     *
     * @param idCanzone L'ID del brano di cui visualizzare le emozioni.
     * @throws RemoteException Se si verifica un errore durante la gestione delle chiamate remote.
     * @see Percezioni#cercaEmozioni(String)
     */
    public void visualizzaEmozioniBrano(String idCanzone) {
        // Creazione di uno scanner per l'input utente
        Scanner sc = new Scanner(System.in);
        // Ricerca delle percezioni associate alla canzone
        List<Percezione>  possibiliEmozioni = percezioni.cercaEmozioni(idCanzone);
        // Costanti per la formattazione del testo colorato sulla console
        String R = "\u001B[31m", V = "\u001B[32m", B = "\u001B[0m";

        // Se non ci sono percezioni, stampa un messaggio e termina il metodo
        if (possibiliEmozioni.isEmpty()) {
            System.out.println(R + "Il brano ricercato non contiene emozioni inserite dagli utenti. " + B);
            return;
        }
        // Array di enum per le emozioni
        Emozione[] emozione = Emozione.values();
        int n_enum = emozione.length;
        // Array per conteggio, somma totale e media delle emozioni
        int[] countEmozione = new int[n_enum];
        long[] totaleEmozione = new long[n_enum];
        float[] mediaEmozione = new float[n_enum];
        // Iterazione sulle percezioni per popolare gli array
        for (Percezione e : possibiliEmozioni) {
            int n = e.getEmozione().ordinal();
            countEmozione[n]++;
            totaleEmozione[n] += e.getScore();
        }
        // Stampa delle informazioni sulle emozioni
        System.out.println("Per il brano selezionato sono state inserite le seguenti informazioni: ");
        System.out.print("(il primo intero corrisponde al numero di utenti che hanno inserito tale emozione e il secondo alla media dei punteggi)");
        for (int i = 0; i < n_enum; i++) {
            System.out.printf("\n%-12s", emozione[i].toString());
            System.out.printf("\t%-5s", countEmozione[i]);
            // Calcolo e stampa della media solo se ci sono dati per quella emozione
            if (countEmozione[i] != 0) {
                mediaEmozione[i] = (float) totaleEmozione[i] / countEmozione[i];
                System.out.printf("\t%-5s", mediaEmozione[i]);
            }
        }
        // Richiesta all'utente di mostrare i commenti
        System.out.print("\nMostrare i commenti? (S/N): ");
        char checkNote = sc.nextLine().toUpperCase().charAt(0);
        // Validazione dell'input utente
        while (checkNote != 'S' && checkNote != 'N') {
            System.out.print(R + "Il valore inserito non è valido. Prego reinserire S o N: " + B);
            checkNote = sc.next().toUpperCase().charAt(0);
        }
        // Se l'utente vuole mostrare i commenti, vengono stampati
        if (checkNote == 'S') {
            System.out.println("I commenti inseriti sono: ");
            for (Percezione p : possibiliEmozioni) {
                String note = p.getNote();
                if (!note.equals(""))
                    System.out.println(note);
            }
        }
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
    public boolean Registrazione(UtenteRegistrato newUtenteRegistrato) throws IOException {
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
        // Chiamata al metodo di accesso nella classe Persone
        boolean accessoRiuscito = persone.accedi(userId, password);
        if(accessoRiuscito )
        {
            // Verifica se l'utente è già loggato
            if(userLoggedIn.contains(userId))
            {
                return true;
            }
            // Aggiunge l'utente alla lista degli utenti loggati
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
        // Rimuove l'utente dalla lista degli utenti loggati
        userLoggedIn.remove(idUtente);
    }

    /**
     * Restituisce la lista delle canzoni associate a una playlist specifica.
     *
     * @param idPlaylist l'ID della playlist da cui ottenere le canzoni
     * @return una lista di canzoni associate alla playlist o una lista vuota se la playlist non contiene canzoni
     */public List<Canzone> canzoniDaIdPlaylist(String idPlaylist)
    {
        // Ottiene la playlist corrispondente all'ID specificato
        Playlist playlist = playlists.cercaPlaylistPerId(idPlaylist);
        // Restituisce la lista delle canzoni associate alla playlist
        return canzoni.getCanzoni(playlist.getListaCanzoni());    }

    /**
     * Registra una nuova playlist per l'utente corrente.
     *
     * @param nomePlaylist il nome della nuova playlist
     * @param userId l'ID dell'utente che registra la playlist
     * @return true se la registrazione della playlist è avvenuta con successo, false altrimenti
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     */public boolean RegistraPlaylist(String nomePlaylist, String userId) throws IOException
    {
        // Verifica se l'utente è autenticato
        if(!userLoggedIn.contains(userId)) return false;
        // Richiama il metodo di registrazione della playlist
        return RegistraPlaylist(nomePlaylist);
    }

    /**
     * Esegue il logout dell'utente corrente dall'applicazione.
     */
    private void logOut()
    {
        // Chiama il metodo di logout del sistema di autenticazione
        Authentication.logOut();
    }

    /**
     * Crea e registra una nuova playlist con il titolo specificato.
     *
     * @param titolo il titolo della playlist da creare
     * @return true se la playlist è stata creata e registrata con successo, false altrimenti
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     * @see Playlists#aggiungiPlaylist(Playlist)
     */
    public boolean RegistraPlaylist(String titolo) throws IOException {
        // Crea una nuova playlist con il titolo fornito e l'utente corrente
        Playlist newPlaylist = new Playlist(titolo, Authentication.getLoggedAs());
        // Aggiunge la nuova playlist al sistema di gestione delle playlist
        playlists.aggiungiPlaylist(newPlaylist);
        // Restituisce true per indicare che la creazione e la registrazione sono avvenute con successo
        return true;
    }

    /**
     * Mostra l'elenco delle playlist create dall'utente autenticato e i brani in esse contenuti.
     *
     * @return true se la visualizzazione delle playlist è avvenuta con successo, false altrimenti
     * @see Playlists#cercaPlaylistPerIdPersona(String)
     */
    public boolean mostraPlaylist() {
        // Verifica se l'utente è autenticato
        if(!Authentication.isLogged())
            return false;
        // Stampa l'elenco delle playlist create dall'utente
        System.out.println("Hai creato le seguenti playlist: ");
        // Ottiene l'elenco delle playlist create dall'utente autenticato
        List<Playlist> userPlaylists = playlists.cercaPlaylistPerIdPersona(Authentication.getLoggedAs().getUserId());
        // Itera su ciascuna playlist
        for(Playlist playlist : userPlaylists){
            // Stampa le informazioni sulla playlist
            System.out.print(playlist.toString());
            // Ottiene la lista delle canzoni nella playlist
            List<Canzone> lista = canzoni.getCanzoni(playlist.getListaCanzoni());
            if(lista.size() == 0){
                System.out.println("\tLa playlist è vuota.");
            }else{
                // Stampa ciascuna canzone nella playlist
                lista.forEach(canzone -> System.out.print("\t"+canzone));
            }
        }
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
    public boolean aggiungiCanzone(String idCanzone, String idPlaylist, String userId) throws IOException {
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
    public boolean inserisciEmozioni(Percezione newPercezione, String userId) throws IOException {
        // Verifica se l'utente è autenticato
        if(userLoggedIn.contains(userId))
        {
            // Se l'utente è autenticato, aggiunge la percezione chiamando il metodo della classe Percezioni
            percezioni.add(newPercezione);
            return true;
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
    public Canzone cercaCanzone(String idCanzone) {
        return canzoni.getCanzone(idCanzone);
    }

    /**
     * Ricerca i brani creati da un artista.
     * @param autore il nome dell'artista 
     * @return la lista dei brani dell'artista specificato o una lista vuota nel caso in cui non sia presente nel database
     * @see Canzoni#cercaBraniPerAutore(String) 
     */
    @Override
    public List<Canzone> cercaBraniPerAutore(String autore) {
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
     * Aggiunge una compilation di canzoni alla playlist specificata.
     *
     * @param listaCanzoni la lista di canzoni da aggiungere alla playlist
     * @param idPlaylist l'ID della playlist a cui aggiungere le canzoni
     * @return true se le canzoni sono state aggiunte alla playlist con successo, false altrimenti
     * @throws IOException se si verifica un errore di Input/Output relativo al database
     * @see Playlists#aggiungiCompilation(List, String)
     */
    @Override
    public boolean aggiungiCompilation(List<Canzone> listaCanzoni, String idPlaylist) throws IOException{
        // Chiamata al metodo della classe Playlists per aggiungere la compilation alla playlist
        return playlists.aggiungiCompilation(listaCanzoni, idPlaylist);
    }

    /**
     * Verifica che tra le playlist create dall'utente che ha effettuato il login sia presente un determinato brano.
     *
     * @param idCanzone l'ID della canzone da cercare
     * @return true se la canzone è presente in almeno una delle playlist dell'utente, false altrimenti
     * @see Playlists#controllaCanzonePersona(String, String)
     */
    public boolean controllaCanzoneUtente(String idCanzone) {
        return playlists.controllaCanzonePersona(Authentication.getLoggedAs().getUserId(),idCanzone);
    }

    /**
     * Verifica che tra le playlist create da un utente sia presente un determinato brano.
     *
     * @param idUtente l'ID dell'utente
     * @param idCanzone l'ID della canzone da cercare
     * @return true se la canzone è presente in almeno una delle playlist dell'utente, false altrimenti
     * @see Playlists#controllaCanzonePersona(String, String)
     */public boolean controllaCanzoneUtente(String idUtente, String idCanzone) {
        return playlists.controllaCanzonePersona(idUtente, idCanzone);
    }

    /**
     * Controlla se l'utente che ha effettuato l'accesso ha già inserito emozioni per una determinata canzone.
     *
     * @param idCanzone l'ID della canzone
     * @return true se l'utente ha già inserito emozioni per la canzone, false altrimenti
     * @see Percezioni#controllaEmozioniPersona(String, String)
     */
    public boolean controllaEmozioniUtente(String idCanzone) {
        return percezioni.controllaEmozioniPersona(Authentication.getLoggedAs().getUserId(),idCanzone);
    }

    /**
     * Permette di visualizzare l'elenco delle playlist associate all'utente che ha effettuato l'accesso.
     *
     * @return la lista delle playlist corrispondenti o una lista vuota nel caso in cui non siano presenti nel database
     * @see Playlists#cercaPlaylistPerIdPersona(String)
     */
    @Override
    public List<Playlist> cercaPlaylistPerUtente(String idUtente){
        return playlists.cercaPlaylistPerIdPersona(Authentication.getLoggedAs().getUserId());
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
     */private static int convertitore(String s)
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

    //**
    //* Funzione da cui inizia l'esecuzione dell'intera applicazione.
    //* @param args l'elenco di argomenti forniti dalla riga di comando
    // */
    /*public static void main(String[] args){
        try {
            
            int r;
            String R = "\u001B[31m", V = "\u001B[32m", B = "\u001B[0m";
            Canzone canzoneSelezionata;
            Scanner sc = new Scanner(System.in);
            EmotionalSongsService emotionalSongsService = new EmotionalSongsService();
            
            do {
                //stampiamo il menù iniziale
                System.out.println("\n" +
                        " *** Fai la tua scelta *** \n " +
                        "\t 1. Ricerca di un brano per titolo \n" +
                        "\t 2. Ricerca di un brano per autore e anno \n" +
                        "\t 3. Visualizza le emozioni \n" +
                        "\t 4. Registrazione \n" +
                        "\t 5. Accedi \n" +
                        "\t 6. Logout \n" +
                        "\t 7. Crea una playlist \n" +
                        "\t 8. Mostra le playlist \n" +
                        "\t 9. Inserisci canzoni nella playlist \n" +
                        "\t 10. Inserisci le emozioni \n" +
                        "\t 0. Esci dal programma \n");
                System.out.print("Inserisci l'intero corrispondente alla tua scelta: ");
                String numero = sc.nextLine();
                r = convertitore(numero);
                
                
                //Ora inizio con le operazioni nei diversi casi
                switch (r) {
                    case 1:
                        System.out.print("Inserisci il titolo da cercare: ");
                        String titolo = sc.nextLine();
                        if (titolo.equals("")) {
                            break;
                        }
                        List<Canzone> possibiliTitoli = emotionalSongsService.cercaBranoMusicale(titolo);
                        if (possibiliTitoli.isEmpty()) {
                            System.out.println(R + "Nessun brano trovato." + B);
                            break;   //mi fa uscire dallo switch
                        }
                        possibiliTitoli.forEach(System.out::print);
                        break;
                    
                    case 2:
                        System.out.print("Inserisci l'autore da cercare: ");
                        String autore = sc.nextLine();
                        if (autore.equals("")) {
                            break;
                        }
                        System.out.print("Inserisci l'anno da cercare: ");
                        int anno = convertitore(sc.nextLine());
                        List<Canzone> possibiliBrani = emotionalSongsService.cercaBranoMusicale(autore, anno);
                        if (possibiliBrani.isEmpty()) {
                            System.out.println(R + "Nessun brano trovato. " + B);
                            break;
                        }
                        possibiliBrani.forEach(System.out::print);
                        //System.out.println(possibiliBrani.toString());
                        break;
                    
                    case 3:
                        System.out.print("Inserisci l'ID del brano che vuoi selezionare: ");
                        String IdBrano = sc.nextLine();
                        if (IdBrano.equals("")) {
                            break;
                        }
                        canzoneSelezionata = emotionalSongsService.cercaCanzone(IdBrano);
                        if (canzoneSelezionata == null) {
                            System.out.println(R + "Nessun brano trovato. " + B);
                            break;
                        }
                        System.out.print("Hai selezionato la seguente canzone: ");
                        System.out.print(V + canzoneSelezionata.toString() + B);
                        emotionalSongsService.visualizzaEmozioniBrano(IdBrano);

                        break;
                    
                    
                    case 4:
                        UtenteRegistrato newUtenteRegistrato;
                        System.out.println("Inserisci i tuoi dati per registrarti all'applicazione.");
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        if (nome.equals("")) {
                            break;
                        }
                        System.out.print("Cognome: ");
                        String cognome = sc.nextLine();
                        System.out.print("Codice Fiscale: ");
                        String codiceFiscale = sc.nextLine();
                        System.out.print("Indirizzo fisico: ");
                        String indirizzoFisico = sc.nextLine();
                        System.out.print("E-mail: ");
                        String email = sc.nextLine();
                        System.out.print("UserID: ");
                        String userId = sc.nextLine();
                        System.out.print("Password: ");
                        String password = sc.nextLine();
                        if(!email.contains("@"))
                        {
                            System.out.println(R+"E-mail non valida - è necessaria @. "+B);
                            break;
                        }
                        if(!email.substring(email.indexOf("@")).contains("."))
                        {
                            System.out.println(R+"E-mail non valida - è necessario un dominio (.com, .eu, .it)."+B);
                            break;
                        }
                        if(emotionalSongsService.controlloUserid(userId))
                        {
                            System.out.println(R+"UserId già in uso."+B);
                            break;
                        }
                        if(password.length()<6)
                        {
                            System.out.println(R+"Password troppo corta!"+B);
                            break;
                        }
                        try {
                            newUtenteRegistrato = new UtenteRegistrato(nome, cognome, codiceFiscale, indirizzoFisico, email, password, userId);
                        } catch (NullPointerException exception) {
                            System.out.println(R + "Nessun campo può essere vuoto. Ripeti la registrazione." + B);
                            break;
                        }
                        if (emotionalSongsService.Registrazione(newUtenteRegistrato)) {
                            System.out.println(V + "La registrazione è andata a buon fine. Ora puoi eseguire l'accesso." + B);
                            break;
                        } else {
                            System.out.println("Hai già effettuato la registrazione. Puoi eseguire l'accesso.");
                        }
                        break;
                    
                    case 5:
                        if (Authentication.isLogged()) {
                            String userIdConnesso = (Authentication.getLoggedAs()).getUserId();
                            System.out.println(R + "Impossibile effettuare l'accesso: utente " + userIdConnesso + " già connesso." + B);
                            System.out.print("Effettuare il logout? (S/N) ");
                            char check = sc.next().toUpperCase().charAt(0);
                            sc.nextLine();
                            while (check != 'S' && check != 'N') {
                                System.out.print(R + "Il valore inserito non è valido. Prego reinserire S o N. " + B);
                                check = sc.next().toUpperCase().charAt(0);
                                sc.nextLine();
                            }
                            if (check == 'S') {
                                Authentication.logOut();
                                System.out.println(V + "Disconnessione avvenuta con successo. Ora puoi effettuare l'accesso." + B);
                            } else {
                                System.out.println(V + "Continua come utente " + userIdConnesso + B);
                                break;
                            }
                        }
                        System.out.println("Inserisci UserId e password scelti in fase di registrazione.");
                        System.out.print("UserID: ");
                        userId = sc.nextLine();
                        if (userId.equals("")) {
                            break;
                        }
                        System.out.print("Password: ");
                        password = sc.nextLine();
                        if (emotionalSongsService.accedi(userId, password)) {
                            System.out.println(V + "Accesso riuscito. Ora puoi creare playlist e inserire le tue emozioni." + B);
                        } else {
                            System.out.println(R + "Accesso non riuscito." + B);
                        }
                        break;
                    
                    case 6:
                        emotionalSongsService.logOut();
                        System.out.println(V + "Disconnessione avvenuta con successo." + B);
                        break;
                    
                    case 7:
                        if (!Authentication.isLogged()) {
                            System.out.println(R + "Per creare una playlist, devi prima effettuare l'accesso." + B);
                            break;
                        }
                        System.out.print("Dai un nome alla tua playlist: ");
                        String titoloPlaylist = sc.nextLine();
                        if (titoloPlaylist.equals("")) {
                            break;
                        }
                        Playlist newPlaylist = new Playlist(titoloPlaylist, Authentication.getLoggedAs().getUserId());
                        emotionalSongsService.RegistraPlaylist(newPlaylist);
                        System.out.print(V + "Hai creato la playlist " + newPlaylist.toString() + B);
                        break;
                    
                    case 8:
                        if (!Authentication.isLogged()) {
                            System.out.println(R + "Per visualizzare le playlist che hai creato, devi prima effettuare l'accesso." + B);
                            break;
                        }
                        if ((emotionalSongsService.cercaPlaylistPerUtente(Authentication.getLoggedAs().getUserId()).size() == 0)) {
                            System.out.println("Non hai creato alcuna playlist.");
                            break;
                        }
                        emotionalSongsService.mostraPlaylist();
                        break;
                    
                    case 9:
                        if (!Authentication.isLogged()) {
                            System.out.println(R + "Devi prima effettuare l'accesso!" + B);
                            break;
                        }
                        List<Playlist> possibiliPlaylist = emotionalSongsService.cercaPlaylistPerUtente(Authentication.getLoggedAs().getUserId());
                        if (possibiliPlaylist.isEmpty()) {
                            System.out.println(R + "Non hai ancora creato una playlist. " + B);
                            break;
                        }
                        String idPlaylist;
                        boolean flag, flag2 = false;
                        if (possibiliPlaylist.size() == 1) {
                            idPlaylist = possibiliPlaylist.get(0).getIdPlaylist();
                        } else {
                            System.out.println("In quale playlist vuoi inserirla? ");
                            possibiliPlaylist.forEach(System.out::print);
                            System.out.println("Copia l'id della playlist. ");
                            do{
                                idPlaylist = sc.nextLine();
                                for (Playlist playlist : possibiliPlaylist){
                                    if( idPlaylist.equals(playlist.getIdPlaylist()) ){
                                        flag2 = true;
                                    }
                                }
                                if( !flag2 ){
                                    System.out.print(R + "Il valore inserito non è valido. Prego reinserire: " + B);
                                }
                            }while( !flag2);

                        }
                        System.out.print("Vuoi inserire un singolo brano o la compilation di un artista? Inserisci S (singolo) o C (compilation). ");
                        char check = sc.next().toUpperCase().charAt(0);
                        sc.nextLine();
                        while (check != 'S' && check != 'C') {
                            System.out.println(R + "Il valore inserito non è valido. Prego reinserire S o C." + B);
                            check = sc.next().toUpperCase().charAt(0);
                            sc.nextLine();
                        }
                        if (check == 'S') {
                            System.out.print("Inserisci idCanzone: ");
                            String idCanzone = sc.nextLine();
                            if (idCanzone.equals("")) {
                                break;
                            }
                            if (emotionalSongsService.cercaCanzone(idCanzone) == null) {
                                System.out.println(R + "Non ho trovato nessuna canzone." + B);
                                break;
                            }
                            flag = emotionalSongsService.aggiungiCanzone(idCanzone, idPlaylist);
                        } else {
                            System.out.print("Inserisci il nome completo dell'artista: ");
                            String artista = sc.nextLine();
                            List<Canzone> listaDaAggiungere = emotionalSongsService.cercaBraniPerAutore(artista);
                            if (listaDaAggiungere.isEmpty()) {
                                System.out.println("Non ho trovato nessun artista. Nessuna compilation aggiunta.");
                                break;
                            }
                            flag = emotionalSongsService.aggiungiCompilation(listaDaAggiungere, idPlaylist);
                        }
                        if (flag)
                            System.out.println(V + "L'aggiunta è andata a buon fine." + B);
                        else
                            System.out.println(R + "L'aggiunta non è andata a buon fine. Riprova. " + B);
                        break;
                    
                    case 10:
                        if (!Authentication.isLogged()) {
                            System.out.println(R + "Devi prima effettuare l'accesso." + B);
                            break;
                        }
                        System.out.print("Inserisci idCanzone: ");
                        String idCanzone = sc.nextLine();
                        if (idCanzone.equals("")) {
                            break;
                        }
                        if (!emotionalSongsService.controllaCanzoneUtente(idCanzone)) {
                            System.out.println(R + "Devi prima inserire la canzone in una playlist. " + B);
                            break;
                        }
                        if (emotionalSongsService.controllaEmozioniUtente(idCanzone)) {
                            System.out.println(R + "Hai già inserito delle emozioni per questa canzone. " + B);
                            break;
                        }
                        System.out.println("Per ogni emozione mostrata, inserisci un punteggio da 1 (meno intenso) a 5 (molto intenso) per valutarla (sono validi solo numeri interi)." +
                                "\nInserisci 0 se non vuoi inserire tale emozione." +
                                "\nUna volta valutata, puoi inserire anche un commento (fino a 256 caratteri) per ogni emozione.");
                        
                        for (Emozione e : Emozione.values()) {
                            int score = -1;
                            System.out.print(e);
                            System.out.print(":\n\t-Inserisci lo score: ");
                            score = convertitore(sc.nextLine());
                            while (score < 0 || score > 5) {
                                System.out.print(R + "\tIntero non valido. Inserisci nuovamente lo score: " + B);
                                score = convertitore(sc.nextLine());
                            }
                            if (score != 0) {
                                Percezione newPercezione = new Percezione(e, score, idCanzone, Authentication.getLoggedAs().getUserId());
                                System.out.print("\t-Nota (premi Invio per non inserire nulla): ");
                                String note = sc.nextLine();
                                if (note.length() > 256) {
                                    System.out.println(R + "Errore: hai superato il limite consentito di caratteri --> nota non salvata" + B);
                                }
                                if (!note.equals("\n")) {
                                    newPercezione.aggiungiNote(note);
                                }
                                emotionalSongsService.inserisciEmozioni(newPercezione);
                            }
                        }
                        break;
                    
                    case 0:
                        emotionalSongsService.logOut();
                        System.out.println("Ciao.");
                        break;
                    
                    default:
                        System.out.println(R + "Il valore inserito non e' corretto. Ritenta." + B);
                }
            } while (r != 0);
            sc.close();
        }catch(IOException e){
            System.err.println("Si è verificato un errore di Input/Output sui database. Il programma si arresterà a breve. ");
        }
    }*/
    

}
