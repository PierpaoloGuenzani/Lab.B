package emotionalsongs;

import common.Canzone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class MainController implements ActionListener
{
	private MainView mainView;
	private MainModel mainModel;
	
	public MainController()
	{}
	
	public void setMainView(MainView mainView)
	{
		this.mainView = mainView;
	}
	
	public void setMainModel(MainModel mainModel)
	{
		this.mainModel = mainModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		try
		{
			if (source.equals(mainView.accediButton))
			{
				if(mainModel == null || mainView == null) return;
				new AccediDialog(mainView, mainModel).draw();
			}
			if (source.equals(mainView.logOutButton))
			{
				mainView.setLoggedOut();
				if(mainModel == null) return;
				mainModel.logOut();
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
			//RICERCA
			if (source.equals(mainView.ricercaButton))
			{
				if (mainView.titoloRadioButton.isSelected())
				{
					if(mainView.searchField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito nessun titolo!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel == null) return;
					mainModel.cercaBranoMusicale(mainView.searchField.getText());
				}
				if (mainView.autoreRadioButton.isSelected())
				{
					if(mainView.searchField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito nessun autore!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel == null) return;
					mainModel.cercaBraniPerAutore(mainView.searchField.getText());
				}
				if (mainView.autoreEAnnoRadioButton.isSelected())
				{
					if(mainView.searchField.getText().equals("") || mainView.annoField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito l'autore e/o l'anno!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel == null) return;
					mainModel.cercaBranoMusicale(mainView.searchField.getText(), Integer.parseInt(mainView.annoField.getText()));
				}
				mainView.setJListModel(mainModel.getCanzoneJlist());
			}
			//VISUALIZZA EMOZIONE
			if(source.equals(mainView.visualizzaEmozioniButton))
			{
				Canzone canzone = (Canzone) mainView.canzoneJList.getSelectedValue();
				if(canzone == null)
				{
					JOptionPane.showMessageDialog(mainView.finestra, "Nessun canzone selezionata", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
					//TODO rimuove solo debug
					new ProspettoRiassuntivoDialog();
					return;
				}
				if(mainModel == null) return;
				mainModel.visualizzaEmozioni(canzone.getId());
			}
			if(source.equals(mainView.nuovoAccountItem))
			{
				new NuovoUtenteDialog();
			}
		}
		catch (RemoteException remoteException)
		{
			JOptionPane.showMessageDialog(mainView.finestra, "Connessione con il server non disponibile", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
