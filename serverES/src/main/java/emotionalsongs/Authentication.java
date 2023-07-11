package emotionalsongs;

import common.UtenteRegistrato;
/**
 * Classe che permette di controllare l'accesso all'applicazione.
 * @author Guenzani Pierpaolo 738675 VA
 * @author Tropeano Martina 749890 VA
 * @author Paradiso Fabiola 749727 VA
 * @author Cuvato Paolo 748691 VA
 */
public class Authentication {

    private static UtenteRegistrato loggedAs;
    private static Authentication authentication= new Authentication();

    private Authentication() {}
    
    /**
     * Ritorna un oggetto globale di tipo Authentication se una UtenteRegistrato ha eseguito il login
     * @return Authentication che riferisce a se stessi
     */
    public static Authentication getAuthentication()
    {
        if(loggedAs == null)
            return null;
        else
            return authentication;
    }
    
    /**
     * Setta la UtenteRegistrato che ha effettuato l'accesso
     * @param UtenteRegistrato che ha passato il test dell login
     */
    public static void setAuthentication(UtenteRegistrato UtenteRegistrato)
    {
        loggedAs = UtenteRegistrato;
    }
    
    /**
     * Restituisce la UtenteRegistrato che ha effettuato l'accesso
     * @return {@link UtenteRegistrato} se la UtenteRegistrato ha effettuato l'accesso, {@code null} altrimenti
     */
    public static UtenteRegistrato getLoggedAs()
    {
        if(loggedAs == null)
            return null;
        else
            return loggedAs;
    }

    /**
     * Permette di effettuare il logout dall'applicazione.
     */
    public static void logOut()
    {
        loggedAs = null;
    }

    /**
     * Permette di controllare se qualcuno ha già effettuato l'accesso all'applicazione.
     * @return True se qualcuno ha già effettuato l'accesso. False se nessuno è connesso.
     */
    public static boolean isLogged()
    {
        return loggedAs != null;
    }
}
