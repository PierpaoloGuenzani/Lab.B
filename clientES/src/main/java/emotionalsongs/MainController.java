package emotionalsongs;

import common.Canzone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

/**
 * La classe MainController è responsabile di gestire le interazioni dell'utente nell'applicazione "EmotionalSongs".
 * Questo controller collega la Vista (MainView) al Modello (MainModel) e coordina le azioni tra di essi.
 *
 * Implementa l'interfaccia ActionListener per reagire agli eventi generati dagli elementi grafici dell'interfaccia utente.
 * Le azioni gestite includono la creazione di nuovi account, la creazione di playlist, la visualizzazione delle playlist, la ricerca di brani musicali
 * e altre interazioni chiave nell'applicazione.
 */
public class MainController extends WindowAdapter implements ActionListener
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
				// Apre una finestra di dialogo per la creazione di un nuovo account
				NuovoUtenteDialog nuovoUtenteDialog = new NuovoUtenteDialog();
				if(mainModel != null) nuovoUtenteDialog.setMainModel(mainModel);
				nuovoUtenteDialog.draw();
			}
			if(source.equals(mainView.nuovaPlaylistItem))
			{
				// Apre una finestra di dialogo per la creazione di una nuova playlist
				NuovaPlaylistDialog nuovaPlaylistDialog = new NuovaPlaylistDialog();
				if(mainModel != null) nuovaPlaylistDialog.setMainModel(mainModel);
				nuovaPlaylistDialog.draw();
			}
			if(source.equals(mainView.visualizzaPlaylistItem))
			{
				SelectPlaylistDialog selectPlaylistDialog = new SelectPlaylistDialog(SelectPlaylistDialog.VISUALIZZA_PLAYLIST);
				if(mainModel != null) selectPlaylistDialog.setMainModel(mainModel);
				selectPlaylistDialog.draw();
			}
			if (source.equals(mainView.autoreEAnnoRadioButton))
			{
				// Imposta la barra di ricerca in modalità "Ricerca per Autore e Anno"
				mainView.setDoubleBar();
			}
			if (source.equals(mainView.autoreRadioButton))
			{
				// Imposta la barra di ricerca in modalità "Ricerca per Autore"
				mainView.setMonoBar();
			}
			if (source.equals(mainView.titoloRadioButton))
			{
				// Imposta la barra di ricerca in modalità "Ricerca per Titolo"
				mainView.setMonoBar();
			}
			//RICERCA
			if (source.equals(mainView.ricercaButton))
			{
				// Gestisce l'azione del pulsante di ricerca
				if (mainView.titoloRadioButton.isSelected())
				{
					// Ricerca per titolo
					if(mainView.searchField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito nessun titolo!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel != null) mainModel.cercaBranoMusicale(mainView.searchField.getText());
				}
				if (mainView.autoreRadioButton.isSelected())
				{
					// Ricerca per autore
					if(mainView.searchField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito nessun autore!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel != null) mainModel.cercaBraniPerAutore(mainView.searchField.getText());
				}
				if (mainView.autoreEAnnoRadioButton.isSelected())
				{
					// Ricerca per autore e anno
					if(mainView.searchField.getText().equals("") || mainView.annoField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(mainView.finestra, "Non hai inserito l'autore e/o l'anno!", "ERRORE", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(mainModel != null) mainModel.cercaBranoMusicale(mainView.searchField.getText(), Integer.parseInt(mainView.annoField.getText()));
				}
				// Aggiorna la lista delle canzoni nella Vista
				mainView.setJListModel(mainModel.getCanzoneJlist());
			}
			// TASTI
			// Accedi
			if (source.equals(mainView.accediButton))
			{
				// Apre una finestra di dialogo per l'accesso
				AccediDialog accediDialog = new AccediDialog(mainView, mainModel);
				//TODO mettere setter
				accediDialog.draw();
			}
			// Logout
			if (source.equals(mainView.logOutButton))
			{
				// Gestisce l'azione del pulsante di logout
				mainView.setLoggedOut();
				if(mainModel != null) mainModel.logOut();
			}
			// Visualizza emozioni
			if(source.equals(mainView.visualizzaEmozioniButton))
			{
				// Ottiene la canzone selezionata e visualizza le emozioni associate
				Canzone canzone = (Canzone) mainView.canzoneJList.getSelectedValue();
				if(canzone == null)
				{
					JOptionPane.showMessageDialog(mainView.finestra, "Nessun canzone selezionata", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(mainModel != null) mainModel.visualizzaEmozioni(canzone.getId());
			}
			// Inserisci emozioni
			if(source.equals(mainView.inserisciEmozioneButton))
			{
				// Ottiene la canzone selezionata e apre una finestra di dialogo per l'inserimento di emozioni
				Canzone canzone = (Canzone) mainView.canzoneJList.getSelectedValue();
				if(canzone == null)
				{
					JOptionPane.showMessageDialog(mainView.finestra, "Nessun canzone selezionata", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				// Verifica se l'utente ha creato almeno una playlist
				if(mainModel.cercaPlaylistUtente().size() == 0)
				{
					JOptionPane.showMessageDialog(mainView.finestra, "Nessuna Playlist creata, creala e poi riprova!", "ERRORE", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(false)
				{
					// TODO: Verificare che la canzone sia in una playlist, altrimenti selezionare quale tra le playlist
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
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		super.windowClosing(e);
		try
		{
			mainModel.logOut();
		} catch (RemoteException ex) {}
	}
}
