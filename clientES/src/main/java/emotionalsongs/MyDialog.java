package emotionalsongs;
/**
 * L'interfaccia MyDialog definisce un contratto per le classi responsabili della gestione di dialoghi personalizzati
 * all'interno dell'applicazione. Un dialogo rappresenta una finestra interattiva utilizzata per ottenere input,
 * visualizzare messaggi informativi, chiedere conferme o eseguire altre interazioni specifiche con l'utente.
 * Le classi che implementano MyDialog devono fornire un metodo "draw()" per disegnare il dialogo personalizzato nell'interfaccia utente.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public interface MyDialog {
    /**
     * Disegna il dialogo personalizzato nell'interfaccia utente.
     * Questo metodo deve essere implementato nelle classi concrete che rappresentano dialoghi specifici.
     * Viene chiamato quando è necessario visualizzare il dialogo nell'applicazione.
     */
    public void draw();
}
