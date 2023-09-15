package emotionalsongs;

/**
 * L'interfaccia MyDialog rappresenta un contratto per le classi che definiscono dialoghi personalizzati nell'applicazione.
 * Con "dialoghi" ci si riferisce a finestre di dialogo che vengono mostrate all'utente di un'applicazione software per interagire con esso.
 * Sono utilizzate per ottenere input dall'utente, visualizzare messaggi informativi, chiedere conferme o eseguire altre interazioni specifiche.
 * Le classi che implementano questa interfaccia devono fornire un metodo "draw()" per disegnare il dialogo personalizzato.
 */
public interface MyDialog {
    /**
     * E' responsabile di disegnare il dialogo personalizzato nell'interfaccia utente.
     * Questo metodo viene implementato nelle classi concrete che rappresentano dialoghi specifici e viene chiamato quando è necessario
     * visualizzare il dialogo nell'applicazione.
     */
    public void draw();
}
