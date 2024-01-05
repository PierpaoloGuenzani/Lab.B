package emotionalsongs;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Questa interfaccia definisce un set di operazioni generiche per l'accesso ai dati.
 * L'obiettivo di un'interfaccia Dao (Data Access Object) è fornire un'astrazione per l'accesso ai dati,
 * permettendo la separazione della logica di accesso ai dati dalla logica di business dell'applicazione.
 *
 * @param <T> Il tipo generico che rappresenta l'entità dei dati trattati da questo Dao.
 */
public interface Dao<T>
{
	/**
	 * Recupera un elemento specifico dalla sorgente dati in base all'ID fornito.
	 *
	 * @param id L'ID dell'elemento da recuperare.
	 * @return Un oggetto Optional contenente l'elemento se presente, altrimenti vuoto.
	 */
	Optional<T> get(String id);

	/**
	 * Ottiene tutti gli elementi dalla sorgente dati e li restituisce come una mappa,
	 * dove le chiavi sono stringhe (presumibilmente ID) e i valori sono oggetti di tipo T.
	 *
	 * @return Una mappa che associa gli ID agli oggetti T.
	 */
	ConcurrentHashMap<String, T> getAll();

	/**
	 * Salva un nuovo oggetto T nella sorgente dati.
	 *
	 * @param t L'oggetto T da salvare.
	 * @return True se l'operazione di salvataggio è riuscita, altrimenti false.
	 */
	boolean save(T t);

	/**
	 * Aggiorna un oggetto T esistente nella sorgente dati con i parametri forniti.
	 *
	 * @param t L'oggetto T da aggiornare.
	 * @param params Un array di parametri per l'aggiornamento.
	 * @return True se l'operazione di aggiornamento è riuscita, altrimenti false.
	 */
	boolean update(T t, Object[] params);

	/**
	 * Elimina un oggetto T dalla sorgente dati.
	 *
	 * @param t L'oggetto T da eliminare.
	 * @return True se l'operazione di eliminazione è riuscita, altrimenti false.
	 */
	boolean delete(T t);
}
