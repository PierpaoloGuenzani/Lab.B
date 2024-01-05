package emotionalsongs;

import common.UtenteRegistrato;
/**
 * Gestisce l'accesso degli utenti all'applicazione "EmotionalSong".
 * La classe mantiene traccia dello stato di accesso degli utenti, garantendo che ci sia al massimo un utente autenticato
 * alla volta. Utilizza un approccio singleton, permettendo l'accesso a un'istanza globale di tipo Authentication
 * solo se un utente ha effettuato il login.
 * Oltre a gestire l'accesso, fornisce metodi per ottenere informazioni sull'utente autenticato, effettuare il logout
 * e verificare se c'è attualmente un utente autenticato.
 */
public class Authentication {

    private static UtenteRegistrato loggedAs;
    private static Authentication authentication= new Authentication();

    /**
     * Costruisce un'istanza di Authentication.
     * È dichiarato privato per garantire che l'istanza di Authentication possa essere creata solo all'interno
     * della stessa classe. Questo contribuisce a mantenere un approccio singleton, dove esiste una sola istanza
     * di Authentication nell'applicazione.
     */
    private Authentication() {}

    /**
     * Restituisce un'istanza globale di tipo Authentication se un UtenteRegistrato ha effettuato il login.
     *
     * @return un'istanza di Authentication che riferisce a se stessa o null se nessun utente è autenticato
     */
    public static Authentication getAuthentication()
    {
        if(loggedAs == null)
            return null;
        else
            return authentication;
    }

    /**
     * Imposta l'UtenteRegistrato che ha effettuato l'accesso.
     *
     * @param UtenteRegistrato l'utente che ha superato l'autenticazione
     */
    public static void setAuthentication(UtenteRegistrato UtenteRegistrato)
    {
        loggedAs = UtenteRegistrato;
    }

    /**
     * Restituisce l'UtenteRegistrato che ha effettuato l'accesso.
     *
     * @return l'UtenteRegistrato che ha effettuato l'accesso, null altrimenti
     */
    public static UtenteRegistrato getLoggedAs()
    {
        if(loggedAs == null)
            return null;
        else
            return loggedAs;
    }

    /**
     * Effettua il logout dall'applicazione.
     */
    public static void logOut()
    {
        loggedAs = null;
    }

    /**
     * Verifica se un utente ha effettuato l'accesso all'applicazione.
     *
     * @return true se un utente ha effettuato l'accesso, false altrimenti
     */
    public static boolean isLogged()
    {
        return loggedAs != null;
    }
}
