package emotionalsongs;

import javax.swing.*;
import java.rmi.RemoteException;

public class EmotionalSongs
{
	static String IPAddress;
	
	public EmotionalSongs()
	{
		MainView mainView = new MainView();
		MainController mainController = new MainController();
		
		mainController.setMainView(mainView);
		mainView.setMainController(mainController);
		
		new ServerInternetProtocolAddressDialog();
		try
		{
			MainModel mainModel = new MainModel();
			mainModel.setStub(IPAddress);
			
			mainController.setMainModel(mainModel);
			
		} catch (RemoteException e)
		{
			JOptionPane.showMessageDialog(MainView.finestra, "Impossibile connettersi al server", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void main(String[] args)
	{
		new EmotionalSongs();
	}
}
