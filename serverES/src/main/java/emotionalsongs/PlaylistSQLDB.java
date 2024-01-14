package emotionalsongs;

import common.Playlist;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Implementa l'interfaccia PlaylistDAOInterface, utilizza un approccio di accesso ai dati tramite JDBC e si connette a un database SQL.
 * Utilizza PreparedStatement per eseguire query parametriche in modo sicuro, garantendo la corretta separazione tra i comandi SQL e
 * i dati di input degli utenti e contribuendo a prevenire attacchi SQL injection.
 *
 * I metodi di questa classe consentono di recuperare, salvare,aggiornare ed eliminare playlist dal database.
 * L'obiettivo è fornire un'astrazione per l'accesso alle playlist,
 * separando la logica di accesso ai dati dalla logica di business dell'applicazione.
 *
 * Le eventuali eccezioni di tipo SQLException sono gestite internamente e registrate nei log. I log, o file di registro, costituiscono
 * uno strumento per tracciare e analizzare le attività del programma. In presenza di eccezioni, la classe PlaylistSQLDB attua procedure
 * specifiche di gestione degli errori e annota informazioni insieme allo stack trace nei log.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 *
 * @see PlaylistDAOInterface
 */
public class PlaylistSQLDB implements PlaylistDAOInterface
{
	private static final Logger LOGGER = Logger.getLogger(PlaylistSQLDB.class.getName());
	private Connection serverSQL;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement selectAllSongIdFromPlaylist;
	private PreparedStatement insert;
	private PreparedStatement insertSongInPlaylist;
	private PreparedStatement update;
	private PreparedStatement delete;

	/**
	 * Costruisce un oggetto PlaylistSQLDB utilizzando una connessione SQL fornita.
	 *
	 * @param serverSQL La connessione SQL da utilizzare.
	 * @throws SQLException se si verifica un errore durante l'inizializzazione delle query preparate.
	 */
	public PlaylistSQLDB(Connection serverSQL) throws SQLException
	{
		this.serverSQL = serverSQL;
		synchronized (serverSQL)
		{
			select = serverSQL.prepareStatement("SELECT * FROM playlist WHERE idPlaylist = ?");
			selectAllSongIdFromPlaylist = serverSQL.prepareStatement("SELECT idCanzone FROM Playlist_Canzoni " +
					"WHERE idPlaylist = ?");
			selectAll = serverSQL.prepareStatement("SELECT * FROM playlists");
			
			insert = serverSQL.prepareStatement("INSERT INTO Playlists(idPlaylist, idUtente, titolo) " +
					"VALUES (?, ?, ?)");
			insertSongInPlaylist = serverSQL.prepareStatement("INSERT INTO Playlist_Canzoni(idPlaylist, idCanzone) " +
					"VALUES (?, ?)");
			delete = serverSQL.prepareStatement("DELETE FROM Playlists WHERE idPlaylist = ?");
		}
	}

	/**
	 * Recupera una playlist specifica in base all'ID fornito.
	 *
	 * @param id L'ID della playlist da recuperare.
	 * @return Un oggetto Optional contenente la playlist se presente, altrimenti vuoto.
	 */
	@Override
	public Optional<Playlist> get(String id)
	{
		if(id == null | id.isEmpty())
			return Optional.empty();
		try
		{
			synchronized (serverSQL)
			{
				select.setString(1, id);
				ResultSet resultSet = select.executeQuery();
				if (resultSet.next())
				{
					Playlist p = new Playlist(resultSet.getString("titolo"), resultSet.getString("idUtente"), resultSet.getString("idPlaylist"));
					getAllSongIdFromPlaylistQuery(p.getIdPlaylist()).forEach(p::aggiungiCanzone);
					return Optional.of(p);
				}
			}
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di una playlist.");
			System.err.println(e.toString());
		}
		return Optional.empty();
	}

	/**
	 * Recupera tutte le playlist dalla sorgente dati e le restituisce come una mappa.
	 *
	 * @return Una mappa che associa gli ID alle playlist.
	 */
	@Override
	public ConcurrentHashMap<String, Playlist> getAll()
	{
		ConcurrentHashMap<String, Playlist> map = new ConcurrentHashMap<>();
		ResultSet resultSet;
		try
		{
			synchronized (serverSQL)
			{
				resultSet = selectAll.executeQuery();
				while (resultSet.next())
				{
					Playlist p = new Playlist(resultSet.getString("titolo"), resultSet.getString("idUtente"), resultSet.getString("idPlaylist"));
					map.put(p.getIdPlaylist(), p);
					getAllSongIdFromPlaylistQuery(p.getIdPlaylist()).forEach(p::aggiungiCanzone);
				}
			}
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di recupero di tutte le playlist.");
		}
		return map;
	}

	/**
	 * Recupera gli ID delle canzoni associate a una specifica playlist dalla sorgente dati.
	 *
	 * @param idPlaylist L'ID della playlist di cui recuperare gli ID delle canzoni associate.
	 * @return Una lista contenente gli ID delle canzoni associate alla playlist specificata.
	 * @throws SQLException Se si verifica un errore durante l'esecuzione della query SQL.
	 */
	//ATTENZIONE DEVE ESSERE SEMPRE LANCIATO ALL' NTERNO DI UN BLOCCO SYNCHRONIZED
	private List<String> getAllSongIdFromPlaylistQuery(String idPlaylist) throws SQLException
	{
		List<String> list = new LinkedList<>();
		selectAllSongIdFromPlaylist.setString(1, idPlaylist);
		ResultSet idCanzoni = selectAllSongIdFromPlaylist.executeQuery();
		while (idCanzoni.next())
		{
			try
			{
				list.add(idCanzoni.getString("idCanzone"));
			} catch (SQLException e) {				//non aggiungo l'id alla playlist
				// Ignora e continua con le iterazioni successive in caso di eccezione
				LOGGER.warning("Errore nell'apertura del db in fase di recupero degli ID delle canzoni da una playlist.");
			}
		}
		return list;
	}

	/**
	 * Salva una nuova playlist nella sorgente dati.
	 *
	 * @param playlist La playlist da salvare.
	 * @return True se l'operazione di salvataggio è riuscita, altrimenti false.
	 */
	@Override
	public boolean save(Playlist playlist)
	{
		if(playlist == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				insert.setString(1, playlist.getIdPlaylist());
				insert.setString(2, playlist.getIdPersona());
				insert.setString(3, playlist.getTitolo());
				insert.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
			LOGGER.warning("Errore nell'apertura del db in fase di salvataggio di una playlist.");
			return false;
		} //LOG?
		return true;
	}

	/**
	 * Aggiorna una playlist esistente nella sorgente dati con i parametri forniti.
	 *
	 * @param playlist La playlist da aggiornare.
	 * @param params   Un array di parametri per l'aggiornamento.
	 * @return True se l'operazione di aggiornamento è riuscita, altrimenti false.
	 */
	@Override
	public boolean update(Playlist playlist, Object[] params)
	{
		//TODO
		return false;
	}

	/**
	 * Aggiunge una canzone a una specifica playlist nella sorgente dati.
	 *
	 * @param idCanzone  L'ID della canzone da aggiungere alla playlist.
	 * @param idPlaylist L'ID della playlist a cui aggiungere la canzone.
	 * @return True se l'operazione di aggiunta della canzone è riuscita, altrimenti false.
	 */
	public boolean addSong(String idCanzone, String idPlaylist)
	{
		if(idPlaylist == null | idPlaylist.length() < 16)
			return false;
		if(idCanzone == null | idCanzone.isBlank())
			return false;
		PreparedStatement insert = null;
		try
		{
			synchronized (serverSQL)
			{
				insertSongInPlaylist.setString(1, idPlaylist);
				insertSongInPlaylist.setString(2, idCanzone);
				insertSongInPlaylist.executeUpdate();
			}
		} catch (SQLException e)
		{
			System.err.println(e.toString());
			LOGGER.warning("Errore nell'apertura del db in fase di aggiunta di una canzone alla playlist.");
			return false;
		}
		return true;
	}

	/**
	 * Elimina una playlist dalla sorgente dati.
	 *
	 * @param playlist La playlist da eliminare.
	 * @return True se l'operazione di eliminazione è riuscita, altrimenti false.
	 */
	@Override
	public boolean delete(Playlist playlist)
	{
		if(playlist == null)
			return false;
		try
		{
			synchronized (serverSQL)
			{
				delete.setString(1, playlist.getIdPlaylist());
				delete.executeUpdate();
			}
		} catch (SQLException e) {
			LOGGER.warning("Errore nell'apertura del db in fase di eliminazione di una playlist.");
			return false;
		}
		return true;
	}
}
