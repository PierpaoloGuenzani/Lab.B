package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Questa classe rappresenta una finestra di dialogo per l'accesso utente.
 * Gli utenti devono inserire un nome utente e una password per accedere.
 * La finestra gestisce l'interfaccia grafica e le azioni associate ai pulsanti.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class AccediDialog
{
	/**
	 * Lunghezza predefinita per i campi di testo.
	 */
	public static final int DEFAULT_FIELD_LENGTH = 15;

	private JDialog finestra;        // La finestra di dialogo
	private JPanel mainPanel, fieldPanel, buttonPanel, userPanel, passwordPanel;
	JTextField userField;           // Campo di testo per l'inserimento dell'username
	JPasswordField passwordField;   // Campo di testo per l'inserimento della password
	JButton accediButton;           // Pulsante "Accedi" per avviare l'autenticazione
	private JButton annullaButton;   // Pulsante "Annulla" per chiudere la finestra senza accedere
	private JLabel userLabel, passwordLabel;  // Etichette per i campi di testo

	/**
	 * Costruttore della classe AccediDialog.
	 * Inizializza la finestra di dialogo, i campi di testo e i pulsanti associati.
	 */
	public AccediDialog()
	{
		finestra = new JDialog(MainView.finestra);
		finestra.setTitle("ACCEDI");
		finestra.setModal(true);
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		finestra.setSize(250, 150);

		mainPanel = new JPanel(new BorderLayout());
		initializeField();
		initializeButtons();

		finestra.add(mainPanel);
	}

	/**
	 * Costruttore della classe AccediDialog che accetta il modello principale e la vista principale.
	 * Imposta l'azione del pulsante "Accedi" per verificare le credenziali dell'utente.
	 *
	 * @param mainView La vista principale dell'applicazione.
	 * @param mainModel Il modello principale dell'applicazione.
	 */
	public AccediDialog(MainView mainView,MainModel mainModel)
	{
		this();  // Chiama il costruttore di default per inizializzare la finestra e i componenti UI.
		// Aggiungi un listener al pulsante "Accedi" per verificare le credenziali e gestire l'accesso.
		MyListener myListener = new MyListener(mainModel, mainView);
		accediButton.addActionListener(myListener/*new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Ottieni l'username inserito dall'utente.
				String user = userField.getText();
				if(user.equals(""))
				{
					// Mostra un messaggio di errore se l'username è vuoto.
					JOptionPane.showMessageDialog(mainPanel, "Username non può essere vuoto", "ERRORE", JOptionPane.ERROR_MESSAGE);
					return;  // Termina la funzione in caso di errore.
				}
				// Ottieni la password inserita dall'utente.
				String password = String.valueOf(passwordField.getPassword());
				if(password.equals(""))
				{
					// Mostra un messaggio di errore se la password è vuota.
					JOptionPane.showMessageDialog(mainPanel, "Password non può essere vuota", "ERRORE", JOptionPane.ERROR_MESSAGE);
					return;  // Termina la funzione in caso di errore.
				}
				try
				{
					// Verifica le credenziali con il modello principale.
					if(mainModel.accedi(user, password))
					{
						// Se le credenziali sono corrette, mostra un messaggio di successo.
						JOptionPane.showMessageDialog(mainPanel, "Login effettuato", "AUTENTICAZIONE ACCETTATA", JOptionPane.INFORMATION_MESSAGE);
						// Imposta la vista come autenticata e chiudi la finestra di dialogo.
						mainView.setLoggedIn();
						finestra.dispose();
					}
					else
					{
						// Se le credenziali sono errate, mostra un messaggio di errore.
						JOptionPane.showMessageDialog(mainPanel, "Username o password errati", "ERRORE", JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException ex)
				{
					// Se c'è un errore di connessione al server, mostra un messaggio di errore.
					JOptionPane.showMessageDialog(mainPanel, "Connessione con il server non disponibile", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}*/);
	}

	/**
	 * Inizializza il pannello dei campi di testo per l'username e la password.
	 */
	private void initializeField()
	{
		fieldPanel = new JPanel();
		
		userPanel = new JPanel();
		userLabel = new JLabel("Username:");
		userPanel.add(userLabel);
		userField = new JTextField(DEFAULT_FIELD_LENGTH);
		userPanel.add(userField);
		fieldPanel.add(userPanel);
		
		passwordPanel = new JPanel();
		passwordLabel = new JLabel("Password:");
		passwordPanel.add(passwordLabel);
		passwordField = new JPasswordField(DEFAULT_FIELD_LENGTH);
		passwordPanel.add(passwordField);
		fieldPanel.add(passwordPanel);
		
		mainPanel.add(fieldPanel, BorderLayout.CENTER);
	}

	/**
	 * Inizializza i pulsanti "Accedi" e "Annulla" e associa loro azioni.
	 */
	private void initializeButtons()
	{
		buttonPanel = new JPanel();
		
		accediButton = new JButton("Accedi");
		buttonPanel.add(accediButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				finestra.dispose();
			}
		});
		buttonPanel.add(annullaButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Mostra la finestra di dialogo al centro della finestra principale.
	 */
	public void draw()
	{
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
	
	private class MyListener implements ActionListener
	{
		private MainModel mainModel;
		private MainView mainView;
		
		public MyListener(MainModel mainModel, MainView mainView)
		{
			this.mainModel = mainModel;
			this.mainView = mainView;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Ottieni l'username inserito dall'utente.
			String user = userField.getText();
			if(user.equals(""))
			{
				// Mostra un messaggio di errore se l'username è vuoto.
				JOptionPane.showMessageDialog(mainPanel, "Username non può essere vuoto", "ERRORE", JOptionPane.ERROR_MESSAGE);
				return;  // Termina la funzione in caso di errore.
			}
			// Ottieni la password inserita dall'utente.
			String password = String.valueOf(passwordField.getPassword());
			if(password.equals(""))
			{
				// Mostra un messaggio di errore se la password è vuota.
				JOptionPane.showMessageDialog(mainPanel, "Password non può essere vuota", "ERRORE", JOptionPane.ERROR_MESSAGE);
				return;  // Termina la funzione in caso di errore.
			}
			try
			{
				// Verifica le credenziali con il modello principale.
				if(mainModel.accedi(user, password))
				{
					// Se le credenziali sono corrette, mostra un messaggio di successo.
					JOptionPane.showMessageDialog(mainPanel, "Login effettuato", "AUTENTICAZIONE ACCETTATA", JOptionPane.INFORMATION_MESSAGE);
					// Imposta la vista come autenticata e chiudi la finestra di dialogo.
					mainView.setLoggedIn();
					finestra.dispose();
				}
				else
				{
					// Se le credenziali sono errate, mostra un messaggio di errore.
					JOptionPane.showMessageDialog(mainPanel, "Username o password errati", "ERRORE", JOptionPane.ERROR_MESSAGE);
				}
			} catch (RemoteException ex)
			{
				// Se c'è un errore di connessione al server, mostra un messaggio di errore.
				JOptionPane.showMessageDialog(mainPanel, "Connessione con il server non disponibile", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
