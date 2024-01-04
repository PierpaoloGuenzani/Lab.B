package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Questa classe rappresenta una finestra di dialogo con il campo per l'inserimento dell'indirizzo IP del server.
 * L'utente può confermare o annullare l'operazione.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 */
public class ServerInternetProtocolAddressDialog implements MyDialog
{
	private JDialog finestra;		// La finestra di dialogo
	private JLabel serverLabel;		 // Etichetta per l'indicazione del campo dell'indirizzo IP del server
	private JTextField serverIPField;	// Campo di testo per inserire l'indirizzo IP del server
	private JButton connettiButton, annullaButton;		// Pulsanti "Connettiti" per avviare la connessione e "Annulla" per annullare l'operazione
	private JPanel buttonPanel, fieldPanel, mainPanel;		// Pannelli contenenti i pulsanti o il campo di testo e il pannello principale della finestra di dialogo

	/**
	 * Costruisce una nuova finestra di dialogo per l'inserimento dell'indirizzo IP del server.
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
	 * Inizializza l'etichetta e il campo di testo per l'inserimento dell'indirizzo IP.
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
	 * Inizializza e gestisce i pulsanti per la connessione e l'annullamento dell'operazione.
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
					EmotionalSongClient.IPAddress = null;
					JOptionPane.showMessageDialog(mainPanel, "Connessione a localhost", "Connessione", JOptionPane.INFORMATION_MESSAGE);
				}
				EmotionalSongClient.IPAddress = serverIPField.getText();
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
	 * Mostra la finestra di dialogo al centro della finestra principale.
	 */
	@Override
	public void draw()
	{
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
