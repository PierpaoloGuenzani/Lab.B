package emotionalsongs;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Fornisce un modo centralizzato per ottenere connessioni al database.
 * Utilizza un pattern Factory per creare e restituire oggetti di tipo Connection.
 * La configurazione della connessione è gestita da un file di properties esterno.
 */
public class ConnectionFactory {
    // URL di connessione al database
    private static String CONNECTION_URL;
    // Nome utente per l'autenticazione al database
    private static String USER;
    // Password per l'autenticazione al database
    private static String PASS;

    // Blocco di inizializzazione statica per caricare le informazioni di connessione da un file di properties
    static {
        try (InputStream input = new FileInputStream("src//resources/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            CONNECTION_URL = properties.getProperty("CONNECTION_URL");
            USER = properties.getProperty("USER");
            PASS = properties.getProperty("PASS");
        } catch (IOException e) {
            // In caso di errore nel caricamento delle proprietà, l'eccezione viene stampata ma non blocca l'applicazione.
            e.printStackTrace();
        }
    }

    /**
     * Restituisce una nuova connessione al database.
     * @return Oggetto di tipo Connection che rappresenta la connessione al database.
     * @throws SQLException se si verifica un errore durante la connessione al database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USER, PASS);
    }
}
