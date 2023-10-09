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
	 * @param mainModel Il modello principale dell'applicazione.
	 */
	public void setMainModel(MainModel mainModel)
	{
		confermaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(nomeField.getText().isBlank()) JOptionPane.showMessageDialog(MainView.finestra, "Il nome non può essere vuoto", "TITOLO INVALIDO", JOptionPane.ERROR_MESSAGE);
					if(mainModel.RegistraPlaylist(nomeField.getText()))
					{
						JOptionPane.showMessageDialog(MainView.finestra, "Playlist creata con successo", "CONFERMA", JOptionPane.INFORMATION_MESSAGE);
						finestra.dispose();
					}
					else JOptionPane.showMessageDialog(MainView.finestra, "Il nome non può essere usato", "TITOLO INVALIDO", JOptionPane.ERROR_MESSAGE);
				}
				catch (IOException ex)
				{
					String titolo = "CONNECTION ERROR";
					String message = "Errore di comunicazione con il server!";
					JOptionPane.showMessageDialog(MainView.finestra, message, titolo, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
