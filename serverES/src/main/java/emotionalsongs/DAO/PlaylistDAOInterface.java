package emotionalsongs.DAO;

import common.Playlist;

import java.io.IOException;

/**
 * Questa interfaccia estende l'interfaccia generica Dao e definisce operazioni specifiche per l'accesso ai dati delle playlist.
 * L'obiettivo è fornire un'astrazione per l'accesso alle playlist,
 * consentendo la gestione separata della logica di accesso ai dati delle playlist dalla logica di business dell'applicazione.
 */
public interface PlaylistDAOInterface extends Dao<Playlist>
{
	/**
	 * Aggiunge una canzone a una specifica playlist nella sorgente dati.
	 *
	 * @param idPlaylist L'ID della playlist a cui aggiungere la canzone.
	 * @param idCanzone  L'ID della canzone da aggiungere alla playlist.
	 * @return True se l'operazione di aggiunta della canzone è riuscita, altrimenti false.
	 * @throws IOException se si verifica un errore di Input/Output relativo al database.
	 */
	public boolean addSong(String idPlaylist, String idCanzone) throws IOException;
}
