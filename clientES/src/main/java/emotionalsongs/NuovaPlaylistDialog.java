package emotionalsongs;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Questa classe rappresenta una finestra di dialogo per la creazione di una nuova playlist.
 * La finestra contiene un campo per inserire il nome della playlist desiderata e due pulsanti
 * per confermare o annullare l'operazione.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class NuovaPlaylistDialog implements MyDialog
{
	
	private JDialog finestra;
	private JPanel mainPanel, buttonPanel, fieldPanel;
	private JButton confermaButton, annullaButton;
	private JLabel nomeLabel;
	private JTextField nomeField;

	/**
	 * Costruisce una nuova finestra di dialogo per la creazione di una playlist.
	 */
	public NuovaPlaylistDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("NUOVA PLAYLIST");
		finestra.setModal(true);
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeButton();
		initializeField();
		
		finestra.add(mainPanel);
	}

	/**
	 * Inizializza il pannello dei campi (campo di testo per il nome della playlist).
	 */
	private void initializeField()
	{
		fieldPanel = new JPanel();
		
		nomeLabel = new JLabel("Nome della playlist:");
		fieldPanel.add(nomeLabel);
		
		nomeField = new JTextField(25);
		fieldPanel.add(nomeField);
		
		mainPanel.add(fieldPanel, BorderLayout.CENTER);
	}

	/**
	 * Inizializza il pannello dei pulsanti (Conferma e Annulla) e gestisce l'azione del pulsante Annulla per chiudere la finestra di dialogo.
	 */
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		buttonPanel.setVisible(true);
		
		confermaButton = new JButton("Conferma");
		buttonPanel.add(confermaButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) { finestra.dispose(); }
		});
		buttonPanel.add(annullaButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Mostra la finestra di dialogo.
	 */
	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}

	/**
	 * Imposta il modello principale per la finestra di dialogo e gestisce l'azione del pulsante di conferma.
	 *
	 * @param mainModel Il modello principale dell'applicazione.
	 */
	public void setMainModel(MainModel mainModel)
	{
		// Aggiunge un listener al pulsante "Conferma" per gestire l'azione dell'utente.
		MyListener myListener = new MyListener(mainModel);
		confermaButton.addActionListener(myListener);
	}
	
	private class MyListener implements ActionListener
	{
		private MainModel mainModel;
		
		public MyListener(MainModel mainModel)
		{
			this.mainModel = mainModel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				// Verifica se il campo del nome è vuoto.
				if(nomeField.getText().isBlank()){
					JOptionPane.showMessageDialog(MainView.finestra, "Il nome non può essere vuoto", "TITOLO INVALIDO", JOptionPane.ERROR_MESSAGE);
				}
				
				// Prova a registrare la nuova playlist attraverso il mainModel.
				if(mainModel.RegistraPlaylist(nomeField.getText()))
				{
					// Se la registrazione è avvenuta con successo, mostra un messaggio informativo.
					JOptionPane.showMessageDialog(MainView.finestra, "Playlist creata con successo", "CONFERMA", JOptionPane.INFORMATION_MESSAGE);
					// Chiude la finestra di dialogo.
					finestra.dispose();
				}
				else{
					// Se il nome non può essere utilizzato, mostra un messaggio di errore.
					JOptionPane.showMessageDialog(MainView.finestra, "Il nome non può essere usato", "TITOLO INVALIDO", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (IOException ex)
			{
				// Gestisce l'eccezione in caso di errore di comunicazione con il server.
				String titolo = "CONNECTION ERROR";
				String message = "Errore di comunicazione con il server!";
				JOptionPane.showMessageDialog(MainView.finestra, message, titolo, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
