package emotionalsongs;

import common.EmotionalSongsInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class MainController implements ActionListener
{
	private MainView mainView;
	private MainModel mainModel;
	
	public MainController(MainView mainView)
	{
		this.mainView = mainView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		try
		{
			if (source.equals(mainView.accediButton))
			{
				mainView.setLoggedIn();
			}
			if (source.equals(mainView.logOutButton))
			{
				mainView.setLoggedOut();
			}
			if (source.equals(mainView.autoreEAnnoRadioButton))
			{
				mainView.setDoubleBar();
			}
			if (source.equals(mainView.autoreRadioButton))
			{
				mainView.setMonoBar();
			}
			if (source.equals(mainView.titoloRadioButton))
			{
				mainView.setMonoBar();
			}
			if (source.equals(mainView.ricercaButton))
			{
				if (mainView.titoloRadioButton.isSelected())
				{
					mainModel.cercaBranoMusicale(mainView.SearchField.getText());
					
				}
				if (mainView.autoreRadioButton.isSelected())
				{
					mainModel.cercaBraniPerAutore(mainView.SearchField.getText());
				}
				if (mainView.autoreEAnnoRadioButton.isSelected())
				{
					mainModel.cercaBranoMusicale(mainView.SearchField.getText(), ((Number)mainView.annoField.getValue()).intValue());
				}
			}
		}
		catch (RemoteException remoteException)
		{
			JOptionPane.showMessageDialog(mainView, "Connessione con il server non disponibile", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
