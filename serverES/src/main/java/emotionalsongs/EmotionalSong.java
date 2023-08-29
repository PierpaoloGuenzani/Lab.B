package emotionalsongs;

import common.EmotionalSongsInterface;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EmotionalSong
{
	public static void main(String[] args) throws IOException, AlreadyBoundException
	{
		EmotionalSongsService object = new EmotionalSongsService();
		EmotionalSongsInterface stab = (EmotionalSongsInterface) UnicastRemoteObject.exportObject(object, 0);
		Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		registry.bind("EmotionalSong", stab);
	}
}
