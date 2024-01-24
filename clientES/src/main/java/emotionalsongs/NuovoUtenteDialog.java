package emotionalsongs;

import common.UtenteRegistrato;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

/**
 * Questa classe rappresenta una finestra di dialogo per la creazione di un nuovo utente.
 * La finestra contiene campi per inserire i dettagli dell'utente, come nome, cognome, codice fiscale, indirizzo,
 * e-mail, nome utente e password. L'utente può confermare o annullare l'operazione di registrazione.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class NuovoUtenteDialog implements MyDialog
{
	/**
	 * Lunghezza predefinita dei campi di input.
	 */
	public static final int DEFAULT_FIELD_LENGTH = 15;
	/**
	 * Larghezza predefinita del pannello.
	 */
	public static final int DEFAULT_PANEL_WIDTH = 300;
	/**
	 * Altezza predefinita del pannello.
	 */
	public static final int DEFAULT_PANEL_HEIGHT = 30;
	private JDialog finestra;
	private JTextField nomeField, cognomeField, codiceFiscaleField, indirizzoField, emailField, userIdField, passwordField;
	private JLabel nomeLabel, cognomeLabel, codiceFiscaleLabel, indirizzoLabel, emailLabel, userIdLabel, passwordLabel;
	//private JLabel verifiedIcon, invalidatedIcon; //TODO metterne 2 per ogni field o usa colori diversi nei bordi
	private JButton confermaButton, annullaButton;
	private JPanel mainPanel, buttonPanel, fieldPanel, labelPanel, iconPanel;

	private MainModel mainModel;

	/**
	 * Costruisce una nuova finestra di dialogo per la creazione di un nuovo utente.
	 */
	public NuovoUtenteDialog()
	{
		finestra = new JDialog();
		finestra.setModal(true);
		finestra.setTitle("Nuovo utente");
		//finestra.setMinimumSize(new Dimension(500, 400));
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		mainPanel = new JPanel(new BorderLayout());

		initializeField();
		initializeButton();

		finestra.add(mainPanel);
		/*finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);*/
	}

	/**
	 * Inizializza i campi e le etichette per inserire i dettagli dell'utente.
	 */
	private void initializeField()
	{
		labelPanel = new JPanel(new GridLayout(0,1));
		fieldPanel = new JPanel(new GridLayout(0,1));
		Dimension minDimension = new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);

		nomeLabel = new JLabel("Nome:");
		nomeLabel.setLabelFor(nomeField);
		labelPanel.add(nomeLabel);
		nomeField = new JTextField(DEFAULT_FIELD_LENGTH);
		nomeField.setMaximumSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));
		fieldPanel.add(nomeField);
		//icon
		//icon

		cognomeLabel = new JLabel("Cognome:");
		cognomeLabel.setLabelFor(cognomeField);
		labelPanel.add(cognomeLabel);
		cognomeField = new JTextField(DEFAULT_FIELD_LENGTH);
		fieldPanel.add(cognomeField);
		//icon
		//icon

		codiceFiscaleLabel = new JLabel("Codice Fiscale:");
		codiceFiscaleLabel.setLabelFor(codiceFiscaleField);
		labelPanel.add(codiceFiscaleLabel);
		codiceFiscaleField = new JTextField(DEFAULT_FIELD_LENGTH);
		fieldPanel.add(codiceFiscaleField);
		//icon
		//icon

		indirizzoLabel = new JLabel("Indirizzo:");
		indirizzoLabel.setLabelFor(indirizzoField);
		labelPanel.add(indirizzoLabel);
		indirizzoField = new JTextField(DEFAULT_FIELD_LENGTH);
		fieldPanel.add(indirizzoField);
		//icon
		//icon

		emailLabel = new JLabel("E-mail:");
		emailLabel.setLabelFor(emailField);
		labelPanel.add(emailLabel);
		emailField = new JTextField(DEFAULT_FIELD_LENGTH);
		fieldPanel.add(emailField);
		//icon
		//icon

		userIdLabel = new JLabel("Nome utente:");
		userIdLabel.setLabelFor(userIdField);
		labelPanel.add(userIdLabel);
		userIdField = new JTextField(DEFAULT_FIELD_LENGTH);
		fieldPanel.add(userIdField);
		//icon
		//icon

		passwordLabel = new JLabel("Password:");
		passwordLabel.setLabelFor(passwordField);
		labelPanel.add(passwordLabel);
		passwordField = new JTextField(DEFAULT_FIELD_LENGTH);
		fieldPanel.add(passwordField);
		//icon
		//icon

		mainPanel.add(labelPanel, BorderLayout.LINE_START);
		mainPanel.add(fieldPanel, BorderLayout.CENTER);
	}

	/**
	 * Inizializza i pulsanti per confermare o annullare l'operazione di registrazione dell'utente.
	 */
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		confermaButton = new JButton("Conferma");
		buttonPanel.add(confermaButton);

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
	 * Imposta il modello principale per la finestra di dialogo e gestisce l'azione del pulsante di conferma.
	 * @param mainModel Il modello principale dell'applicazione.
	 */
	public void setMainModel(MainModel mainModel)
	{
		this.mainModel = mainModel;
		//setVerifier();
		confermaButton.addActionListener(/*new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(mainModel == null) return;
				UtenteRegistrato utenteRegistrato = new UtenteRegistrato(
						nomeField.getText(),
						cognomeField.getText(),
						codiceFiscaleField.getText(),
						indirizzoField.getText(),
						emailField.getText(),
						passwordField.getText(),
						userIdField.getText()
				);
				try
				{
					if(mainModel.Registrazione(utenteRegistrato))
					{
						JOptionPane.showMessageDialog(mainPanel, "Nuovo account creato", "CONFERMA", JOptionPane.INFORMATION_MESSAGE);
						finestra.dispose();
					}
				} catch (IOException ex)
				{
					JOptionPane.showMessageDialog(mainPanel, "Errore di connessione con il server!", "ERRORE", JOptionPane.ERROR_MESSAGE);
				}
			}
		}*/
		new NuovoUtenteActionListener());
	}

	/**
	 * Imposta i verificatori per i campi di input, abilita o disabilita il pulsante di conferma in base alla validità dei dati.
	 */
	/*
	public void setVerifier()
	{
		confermaButton.setEnabled(false);
		buttonPanel.validate();
		buttonPanel.repaint();

		// Per il campo Nome e il campo Cognome, ci sono listener associati all'evento focusLost, che viene attivato quando il campo perde il focus
		// ovvero quando l'utente passa a un altro campo o clicca altrove nella finestra.
		// Se uno dei campi Nome o Cognome è vuoto (blank) o non è in formato valido (almeno 3 caratteri alfabetici e nessun numero), viene mostrato un messaggio di avviso tramite JOptionPane.
		nomeField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				String nome = nomeField.getText().trim();
				if (nome.isBlank()) {
					JOptionPane.showMessageDialog(mainPanel, "Il nome non può essere vuoto!", "Nome invalido", JOptionPane.WARNING_MESSAGE);
				} else if (!nome.matches("^[a-zA-Z]{3,}$")) {
					JOptionPane.showMessageDialog(mainPanel, "Il nome deve contenere almeno tre caratteri alfabetici e non deve includere numeri.", "Nome invalido", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		cognomeField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				String cognome = cognomeField.getText().trim();
				if(cognome.isBlank()){
					JOptionPane.showMessageDialog(mainPanel, "Il cognome non può essere vuoto!", "Cognome invalido", JOptionPane.WARNING_MESSAGE);
				} else if (!cognome.matches("^[a-zA-Z]{3,}$")) {
					JOptionPane.showMessageDialog(mainPanel, "Il cognome deve contenere almeno tre caratteri alfabetici e non deve includere numeri.", "Cognome invalido", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		// Per il campo Codice Fiscale, c'è un listener associato all'evento focusLost che verifica la lunghezza del testo inserito.
		// Se la lunghezza del Codice Fiscale non è esattamente di 16 caratteri, viene mostrato un messaggio di avviso tramite JOptionPanel
		// Si controlla se rispetta l'espressione regolare
		codiceFiscaleField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				String codiceFiscale = codiceFiscaleField.getText().toUpperCase(); // Converti tutto in maiuscolo
				if(codiceFiscale.length() != 16)
				{
					JOptionPane.showMessageDialog(mainPanel,
							"Il codice fiscale deve essere lungo 16 caratteri.",
							"Lunghezza Codice Fiscale", JOptionPane.WARNING_MESSAGE);
					codiceFiscaleFlag = false;
					codiceFiscaleField.requestFocus(); // Imposta il focus sul campo per favorire il reinserimento
					return; // Esci dal metodo se la lunghezza non è corretta
				}
				// Definisci l'espressione regolare per il codice fiscale
				String regex = "^[A-Za-z]{6}\\d{2}[A-Za-z]\\d{2}[0-3][0-9][A-Za-z]\\d{3}[A-Za-z]$";
				Pattern pattern = Pattern.compile(regex);
				// Applica l'espressione regolare
				if (!pattern.matcher(codiceFiscale).matches()) {
					JOptionPane.showMessageDialog(mainPanel,
							"Il codice fiscale non è corretto. Assicurati di seguire il formato specificato. Formato: ABCDEF12G34H567I",
							"Codice Fiscale invalido", JOptionPane.WARNING_MESSAGE);
					codiceFiscaleFlag = false;
					codiceFiscaleField.requestFocus(); // Imposta il focus sul campo per favorire il reinserimento
				} else {
					codiceFiscaleFlag = true;
				}
			}
		});

		indirizzoField.addFocusListener( new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				String indirizzo = indirizzoField.getText().trim().toLowerCase();
				if (indirizzo.isEmpty())
				{
					JOptionPane.showMessageDialog(mainPanel, "L'indirizzo non può essere vuoto!", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
				} else if (!indirizzo.matches("^[a-zA-Z0-9\\s]+$")) {
					//  l'indirizzo contiene solo caratteri alfabetici, numerici e spazi
					JOptionPane.showMessageDialog(mainPanel, "L'indirizzo può contenere solo caratteri alfabetici, numerici e spazi.", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
				} else {
					// l'inizio dell'indirizzo
					String regex = "^(via|piazza|corso|viale)\\s.+";
					if (indirizzo.matches(regex)) {
						// L'indirizzo è valido
					} else {
						JOptionPane.showMessageDialog(mainPanel, "L'indirizzo deve iniziare con Via, Piazza, Corso o Viale seguito da qualche parola.", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		// Utilizza un'espressione regolare (regularSyntax) per verificare se il testo inserito corrisponde a un formato di indirizzo e-mail valido
		emailField.addFocusListener( new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				String email = emailField.getText().trim();
				// Utilizza un'espressione regolare per verificare se l'indirizzo email è valido
				final String regularSyntax = "^[A-Za-z0-9._%+-]+@(studenti\\\\.)?uninsubria\\\\.it$|^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Z|a-z]{2,}$+";
				if(email.matches(regularSyntax))
				{
					emailFlag = true;
				}
				else
				{
					JOptionPane.showMessageDialog(mainPanel, "L'indirizzo email non è valido", "Email invalido", JOptionPane.WARNING_MESSAGE);
					emailFlag = false;
				}
			}
		});

		// Verifica se l'username è valido (lungo almeno 5, solo caratteri alfanumerici) e se sia già in uso chiamando un metodo controlloUserid del mainModel
		// Eseguo i controlli di lunghezza e caratteri prima del blocco try-catch perchè così facendo riesco a
		// gestire gli errori locali senza dover necessariamente gestire un'eccezione remota.
		userIdField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				String username = userIdField.getText().trim();
				// Controlli locali
				// Controlla la lunghezza dell'username
				if (username.length() < 5) {
					userIdFlag = false;
					JOptionPane.showMessageDialog(mainPanel, "L'username deve essere lungo almeno 5 caratteri.", "Username invalido", JOptionPane.WARNING_MESSAGE);
					return;
				} else if(!username.matches("^[a-zA-Z0-9]+$")) {
					userIdFlag = false;
					JOptionPane.showMessageDialog(mainPanel, "L'username può contenere solo lettere e numeri.", "Username invalido", JOptionPane.WARNING_MESSAGE);
					return;
				}

				// Controlli remoti
				try{
					// Verifica se l'username è già in uso
					if(mainModel.controlloUserid(username))
					{
						userIdFlag = false;
						JOptionPane.showMessageDialog(mainPanel, "Username già in uso, riprova", "Username invalido", JOptionPane.WARNING_MESSAGE);
					}
					else
					{
						userIdFlag = true;
					}
				} catch (RemoteException ex)
				{
					JOptionPane.showMessageDialog(mainPanel, "Errore di connessione con il server!", "ERRORE", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Verifica la lunghezza della password (minimo 6), che contenga almeno un carattere maiuscolo, un minuscolo e un numero e nessuno spazio
		// Se la password non supera uno qualsiasi dei controlli, il pulsante rimane disabilitato.
		// Se la password supera tutti i controlli, il pulsante viene abilitato (infatti lo trovo nell'else dell'ultimo controllo)
		passwordField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				String password = passwordField.getText();

				if(codiceFiscaleFlag && emailFlag && userIdFlag) {
					// Verifica la lunghezza della password
					if (password.length() < 6) {
						JOptionPane.showMessageDialog(mainPanel, "La password deve essere lunga almeno 6 caratteri", "Password invalida", JOptionPane.WARNING_MESSAGE);
						confermaButton.setEnabled(false); // Disabilita il pulsante in caso di password troppo corta
						return;
					}
					// Verifica che la password contenga almeno una lettera maiuscola, una lettera minuscola e un numero
					if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
						JOptionPane.showMessageDialog(mainPanel, "La password deve contenere almeno una lettera maiuscola, una lettera minuscola e un numero", "Password invalida", JOptionPane.WARNING_MESSAGE);
						confermaButton.setEnabled(false); // Disabilita il pulsante se la password non soddisfa i criteri
						return;
					}
					// Verifica che la password non contenga spazi
					if (password.contains(" ")) {
						JOptionPane.showMessageDialog(mainPanel, "La password non può contenere spazi", "Password invalida", JOptionPane.WARNING_MESSAGE);
						confermaButton.setEnabled(false); // Disabilita il pulsante se la password contiene spazi non consentiti
					} else {
						confermaButton.setEnabled(true); // Abilita il pulsante se la password è valida
					}
				}
			}
		});
	}
	 */
	
	private class NuovoUtenteActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(mainModel == null)
				return;
			
			String nome = nomeField.getText().trim();
			if (nome.isBlank())
			{
				JOptionPane.showMessageDialog(mainPanel, "Il nome non può essere vuoto!", "Nome invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!nome.matches("^[a-zA-Z]{3,}$"))
			{
				JOptionPane.showMessageDialog(mainPanel, "Il nome deve contenere almeno tre caratteri alfabetici e non deve includere numeri.", "Nome invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String cognome = cognomeField.getText().trim();
			if(cognome.isBlank())
			{
				JOptionPane.showMessageDialog(mainPanel, "Il cognome non può essere vuoto!", "Cognome invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!cognome.matches("^[a-zA-Z]{3,}$"))
			{
				JOptionPane.showMessageDialog(mainPanel, "Il cognome deve contenere almeno tre caratteri alfabetici e non deve includere numeri.", "Cognome invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String codiceFiscale = codiceFiscaleField.getText().toUpperCase(); // Converti tutto in maiuscolo
			if(codiceFiscale.length() != 16)
			{
				JOptionPane.showMessageDialog(mainPanel,
						"Il codice fiscale deve essere lungo 16 caratteri.",
						"Lunghezza Codice Fiscale", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// Definisci l'espressione regolare per il codice fiscale
			String regex = "^[A-Za-z]{6}\\d{2}[A-Za-z]\\d{2}[A-Za-z]\\d{3}[A-Za-z]$";
			// Applica l'espressione regolare
			if (!codiceFiscale.matches(regex))
			{
				JOptionPane.showMessageDialog(mainPanel,
						"Il codice fiscale non è corretto. Assicurati di seguire il formato specificato. Formato: ABCDEF12G34H567I",
						"Codice Fiscale invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String indirizzo = indirizzoField.getText().trim().toLowerCase();
			if (indirizzo.isEmpty())
			{
				JOptionPane.showMessageDialog(mainPanel, "L'indirizzo non può essere vuoto!", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!indirizzo.matches("^(via|piazza|corso|viale)\\s+[a-zA-Z]+(?:\\s+[a-zA-Z]+){0,4}+\\s+\\d{1,5}$"))
			{
				//  l'indirizzo contiene solo caratteri alfabetici, numerici e spazi
				//JOptionPane.showMessageDialog(mainPanel, "L'indirizzo può contenere solo caratteri alfabetici, numerici e spazi.", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog(mainPanel, "L'indirizzo deve iniziare con Via, Piazza, Corso o Viale e terminare con il numero civico.", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String email = emailField.getText().trim();
			// Utilizza un'espressione regolare per verificare se l'indirizzo email è valido
			//final String regularSyntax = "^(?i)[A-Za-z0-9._-]+@(studenti\\\\.)?uninsubria\\\\.it$|^[A-Za-z0-9._-]+@[A-Za-z0-9-]+\\\\.(it|us|biz|org|info|com|net|eu)$";
			final String regularSyntax = "^(?i)[A-Za-z0-9._-]+@(studenti\\.)?[A-Za-z0-9-]+\\.(it|us|biz|org|info|com|net|eu)$";
			if(!email.matches(regularSyntax))
			{
				JOptionPane.showMessageDialog(mainPanel, "L'indirizzo email non è valido", "Email invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String username = userIdField.getText().trim();
			// Controlli locali
			// Controlla la lunghezza dell'username
			if (username.length() < 5)
			{
				JOptionPane.showMessageDialog(mainPanel, "L'username deve essere lungo almeno 5 caratteri.", "Username invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!username.matches("^[a-zA-Z0-9]+$"))
			{
				JOptionPane.showMessageDialog(mainPanel, "L'username può contenere solo lettere e numeri.", "Username invalido", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// Controlli remoti
			try{
				// Verifica se l'username è già in uso
				if(mainModel.controlloUserid(username))
				{
					JOptionPane.showMessageDialog(mainPanel, "Username già in uso, riprova", "Username invalido", JOptionPane.WARNING_MESSAGE);
					return;
				}
			} catch (RemoteException ex)
			{
				JOptionPane.showMessageDialog(mainPanel, "Errore di connessione con il server!", "ERRORE", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String password = passwordField.getText();
			// Verifica la lunghezza della password
			if (password.length() < 6)
			{
				JOptionPane.showMessageDialog(mainPanel, "La password deve essere lunga almeno 6 caratteri", "Password invalida", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// Verifica che la password contenga almeno una lettera maiuscola, una lettera minuscola e un numero
			if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*"))
			{
				JOptionPane.showMessageDialog(mainPanel, "La password deve contenere almeno una lettera maiuscola, una lettera minuscola e un numero", "Password invalida", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (password.contains(" "))
			{
				JOptionPane.showMessageDialog(mainPanel, "La password non può contenere spazi", "Password invalida", JOptionPane.WARNING_MESSAGE);
				return;
			}

			
			UtenteRegistrato utenteRegistrato = new UtenteRegistrato(
					nomeField.getText(),
					cognomeField.getText(),
					codiceFiscaleField.getText(),
					indirizzoField.getText(),
					emailField.getText(),
					passwordField.getText(),
					userIdField.getText()
			);
			try
			{
				if(mainModel.Registrazione(utenteRegistrato))
				{
					JOptionPane.showMessageDialog(mainPanel, "Nuovo account creato", "CONFERMA", JOptionPane.INFORMATION_MESSAGE);
					finestra.dispose();
				}
			} catch (IOException ex)
			{
				JOptionPane.showMessageDialog(mainPanel, "Errore di connessione con il server!", "ERRORE", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Mostra la finestra di dialogo per la registrazione di un nuovo utente.
	 */
	@Override
	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
