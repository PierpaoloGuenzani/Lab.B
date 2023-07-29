package emotionalsongs;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EmotionalSong
{
	public static void main(String[] args) throws RemoteException
	{
		Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		//registry.bind("EmotionalSong",);
	}
}
