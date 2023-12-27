package emotionalsongs;

import common.Canzone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * La classe MainController è responsabile di gestire le interazioni dell'utente nell'applicazione "EmotionalSongs".
 * Implementa l'interfaccia ActionListener per catturare gli eventi generati dagli elementi dell'interfaccia utente.
 * Questo controller collega la Vista (MainView) al Modello (MainModel) e coordina le azioni tra di essi.
 */
public class MainController implements ActionListener
{
	private MainView mainView;
	private MainModel mainModel;

	/**
	 * Costruttore di MainController.
	 */
	public MainController()
	{}

	/**
	 * Imposta la Vista principale per il controller.
	 *
	 * @param mainView La Vista principale da associare a questo controller.
	 */
	public void setMainView(MainView mainView)
	{
		this.mainView = mainView;
	}

	/**
	 * Imposta il Modello principale per il controller.
	 *
	 * @param mainModel Il Modello principale da associare a questo controller.
	 */
	public void setMainModel(MainModel mainModel)
	{
		this.mainModel = mainModel;
	}

	/**
	 * Gestisce gli eventi generati dall'interfaccia utente.
	 *
	 * @param e L'evento generato.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(mainView == null) return;
		Object source = e.getSource();
		try
		{
			//MENU ITEM
			if(source.equals(mainView.nuovoAccountItem))
			{
				NuovoUtenteDialog nuovoUtenteDialog = new NuovoUtenteDialog();
				if(mainModel != null) nuovoUtenteDialog.setMainModel(mainModel);
				nuovoUtenteDialog.draw();
			}
			if(source.equals(mainView.nuovaPlaylistItem))
			{
				NuovaPlaylistDialog nuovaPlaylistDialog = new NuovaPlaylistDialog();
				if(mainModel != null) nuovaPlaylistDialog.setMainModel(mainModel);
				nuovaPlaylistDialog.draw();
			}
			if(source.equals(mainView.visualizzaPlaylistItem))
			{
				//TODO: eliminare aggiungi canzone dialog
				SelectPlaylistDialog selectPlaylistDialog = new SelectPlaylistDialog(SelectPlaylistDialog.VISUALIZZA_PLAYLIST);
				if(mainModel != null) selectPlaylistDialog.setMainModel(mainModel);
				selectPlaylistDialog.draw();
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
					if(mainModel != null) mainModel.cercaBranoMusicale(mainView.searchField.getText());
				}
				if (mainView.autoreRadioButton.isSelected())
				{
					if(mainView.searchField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito nessun autore!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel != null) mainModel.cercaBraniPerAutore(mainView.searchField.getText());
				}
				if (mainView.autoreEAnnoRadioButton.isSelected())
				{
					if(mainView.searchField.getText().equals("") || mainView.annoField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito l'autore e/o l'anno!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel != null) mainModel.cercaBranoMusicale(mainView.searchField.getText(), Integer.parseInt(mainView.annoField.getText()));
				}
				mainView.setJListModel(mainModel.getCanzoneJlist());
			}
			//TASTI
			//accedi
			if (source.equals(mainView.accediButton))
			{
				AccediDialog accediDialog = new AccediDialog(mainView, mainModel);
				//TODO mettere setter
				accediDialog.draw();
			}
			//logout
			if (source.equals(mainView.logOutButton))
			{
				mainView.setLoggedOut();
				if(mainModel != null) mainModel.logOut();
			}
			//visualizza emozioni
			if(source.equals(mainView.visualizzaEmozioniButton))
			{
				Canzone canzone = (Canzone) mainView.canzoneJList.getSelectedValue();
				if(canzone == null)
				{
					JOptionPane.showMessageDialog(mainView.finestra, "Nessun canzone selezionata", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(mainModel != null) mainModel.visualizzaEmozioni(canzone.getId());
			}
			//inserisci emozioni
			if(source.equals(mainView.inserisciEmozioneButton))
			{
				Canzone canzone = (Canzone) mainView.canzoneJList.getSelectedValue();
				if(canzone == null)
				{
					JOptionPane.showMessageDialog(mainView.finestra, "Nessun canzone selezionata", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(mainModel.cercaPlaylistUtente().size() == 0)
				{
					JOptionPane.showMessageDialog(mainView.finestra, "Nessuna Playlist creata, creala e poi riprova!", "ERRORE", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(false)
				{
					//TODO controllare controllare che canzone sia in una playlist, se no selezionare quale tra le playlist
				}
				AggiungiEmozioneDialog aggiungiEmozioneDialog = new AggiungiEmozioneDialog();
				aggiungiEmozioneDialog.setSongId(canzone.getId());
				aggiungiEmozioneDialog.setMainModel(mainModel);
				aggiungiEmozioneDialog.draw();
			}
		}
		catch (RemoteException remoteException)
		{
			// Gestione dell'eccezione in caso di errore di connessione
			JOptionPane.showMessageDialog(mainView.finestra, "Connessione con il server non disponibile", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
