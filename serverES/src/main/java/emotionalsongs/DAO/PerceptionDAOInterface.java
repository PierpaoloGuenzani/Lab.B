package emotionalsongs.DAO;

import common.Percezione;
import common.Playlist;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Questa interfaccia definisce un set di operazioni specifiche per l'accesso ai dati delle percezioni.
 * L'obiettivo di questa interfaccia è fornire un'astrazione per l'accesso alle percezioni,
 * consentendo la gestione separata della logica di accesso ai dati delle percezioni dalla logica di business dell'applicazione.
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public interface PerceptionDAOInterface
{
	/**
	 * Ottiene tutte le percezioni dalla sorgente dati e le restituisce come una mappa,
	 * dove le chiavi sono stringhe (presumibilmente ID delle canzoni) e i valori sono liste di percezioni.
	 *
	 * @return Una mappa che associa gli ID delle canzoni alle liste di percezioni.
	 */
	ConcurrentHashMap<String, List<Percezione>> getAll();

	/**
	 * Recupera le percezioni relative a una specifica canzone e utente dalla sorgente dati.
	 *
	 * @param idCanzone L'ID della canzone per cui recuperare le percezioni.
	 * @param idUtente   L'ID dell'utente per cui recuperare le percezioni.
	 * @return Una lista di percezioni se presenti, altrimenti una lista vuota.
	 */
	List<Percezione> get(String idCanzone, String idUtente);

	/**
	 * Salva una nuova percezione nella sorgente dati.
	 *
	 * @param t La percezione da salvare.
	 * @return True se l'operazione di salvataggio è riuscita, altrimenti false.
	 */
	boolean save(Percezione t);

	/**
	 * Aggiorna una percezione esistente nella sorgente dati con i parametri forniti.
	 *
	 * @param t      La percezione da aggiornare.
	 * @param params Un array di parametri per l'aggiornamento.
	 * @return True se l'operazione di aggiornamento è riuscita, altrimenti false.
	 */
	boolean update(Percezione t, Object[] params);

	/**
	 * Elimina una percezione dalla sorgente dati.
	 *
	 * @param t La percezione da eliminare.
	 * @return True se l'operazione di eliminazione è riuscita, altrimenti false.
	 */
	boolean delete(Percezione t);
}
