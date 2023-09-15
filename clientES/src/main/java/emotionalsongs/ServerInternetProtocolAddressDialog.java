package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * La classe ServerInternetProtocolAddressDialog rappresenta una finestra di dialogo per inserire l'indirizzo IP del server.
 * Questa finestra viene utilizzata per stabilire una connessione al server dell'applicazione "EmotionalSongs".
 */
public class ServerInternetProtocolAddressDialog implements MyDialog
{
	private JDialog finestra;		// La finestra di dialogo
	private JLabel serverLabel;		 // Etichetta per l'indicazione del campo dell'indirizzo IP del server
	private JTextField serverIPField;	// Campo di testo per inserire l'indirizzo IP del server
	private JButton connettiButton, annullaButton;		// Pulsanti "Connettiti" per avviare la connessione e "Annulla" per annullare l'operazione
	private JPanel buttonPanel, fieldPanel, mainPanel;		// Pannelli contenenti i pulsanti o il campo di testo e il pannello principale della finestra di dialogo

	/**
	 * Il costruttore della classe ServerInternetProtocolAddressDialog inizializza la finestra di dialogo.
	 * Imposta titolo, modalità modale, dimensioni minime, campi di testo e pulsanti.
	 */
	public ServerInternetProtocolAddressDialog()
	{
		finestra = new JDialog();
		finestra.setModal(true);
		finestra.setTitle("SERVER IP");
		finestra.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		mainPanel = new JPanel(new BorderLayout());
		finestra.setMinimumSize(new Dimension(250, 150));
		
		initializeField();		// Inizializza il campo di testo per l'indirizzo IP
		initializeButton();		// Inizializza i pulsanti
		
		finestra.add(mainPanel);		// Aggiunge il pannello principale alla finestra di dialogo
//		finestra.setLocationRelativeTo(MainView.finestra);
//		finestra.setVisible(true);
	}

	/**
	 * Inizializza il campo di testo per l'indirizzo IP e l'etichetta corrispondente.
	 */
	private void initializeField()
	{
		fieldPanel = new JPanel();
		serverLabel = new JLabel("IP server:");
		fieldPanel.add(serverLabel);
		serverIPField = new JTextField(10);
		fieldPanel.add(serverIPField);
		
		mainPanel.add(fieldPanel, BorderLayout.CENTER);
	}

	/**
	 * Inizializza i pulsanti "Connettiti" e "Annulla" e associa loro azioni.
	 */
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		connettiButton = new JButton("Connettiti");
		connettiButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (serverIPField.getText().equals(""))
				{
					EmotionalSongs.IPAddress = null;
					JOptionPane.showMessageDialog(mainPanel, "Connessione a localhost", "Connessione", JOptionPane.INFORMATION_MESSAGE);
				}
				EmotionalSongs.IPAddress = serverIPField.getText();
				finestra.dispose();
			}
		});
		buttonPanel.add(connettiButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				finestra.dispose();
				MainView.finestra.dispose();
			}
		});
		buttonPanel.add(annullaButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Il metodo "draw()" mostra la finestra di dialogo al centro della finestra principale dell'applicazione "EmotionalSongs".
	 */
	@Override
	public void draw()
	{
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
