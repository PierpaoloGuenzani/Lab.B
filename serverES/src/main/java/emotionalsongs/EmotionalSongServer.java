package emotionalsongs;

import common.EmotionalSongsInterface;
import emotionalsongs.logic.EmotionalSongsService;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

/**
 * Questa classe rappresenta il punto d'ingresso principale dell'applicazione "EmotionalSong".
 * L'applicazione utilizza la tecnologia RMI (Remote Method Invocation) per fornire un'interfaccia
 * remota denominata "EmotionalSongsInterface" agli utenti.
 *
 * Nel metodo principale, viene creato un oggetto "EmotionalSongsService" che fornisce l'implementazione
 * dei servizi definiti nell'interfaccia remota. Successivamente, questo oggetto viene esportato come
 * oggetto remoto utilizzando "UnicastRemoteObject.exportObject". Viene quindi creato un registro RMI
 * utilizzando "LocateRegistry.createRegistry" sulla porta di registro predefinita e il servizio viene
 * registrato con un nome specifico ("EmotionalSong").
 *
 * @throws IOException Se si verifica un errore durante l'export dell'oggetto remoto.
 * @throws AlreadyBoundException Se il nome "EmotionalSong" è già registrato nel registro RMI.
 * @throws RemoteException Se si verifica un errore durante la gestione delle chiamate remote.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class EmotionalSongServer
{
	/**
	 * Metodo principale che avvia il server RMI per l'applicazione "EmotionalSong".
	 *
	 * @param args Argomenti della riga di comando (non utilizzati in questo caso).
	 * @throws IOException Se si verifica un errore durante l'export dell'oggetto remoto.
	 * @throws AlreadyBoundException Se il nome "EmotionalSong" è già registrato nel registro RMI.
	 * @throws RemoteException Se si verifica un errore durante la gestione delle chiamate remote.
	 */
	public static void main(String[] args) throws IOException, AlreadyBoundException, SQLException
	{
		new ServerGUI();
		// Crea un oggetto EmotionalSongsService
		EmotionalSongsService object = EmotionalSongsService.getInstance();
		// Esporta l'oggetto come oggetto remoto
		EmotionalSongsInterface stab = (EmotionalSongsInterface) UnicastRemoteObject.exportObject(object, 0);
		// Crea un registro RMI sulla porta predefinita e registra il servizio con il nome "EmotionalSong"
		Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		registry.bind("EmotionalSong", stab);
	}
}
