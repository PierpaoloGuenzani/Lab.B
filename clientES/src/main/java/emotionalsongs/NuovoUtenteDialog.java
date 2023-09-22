package emotionalsongs;

import common.UtenteRegistrato;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Questa classe rappresenta una finestra di dialogo per la creazione di un nuovo utente.
 * La finestra contiene campi per inserire i dettagli dell'utente, come nome, cognome, codice fiscale, indirizzo,
 * e-mail, nome utente e password. L'utente può confermare o annullare l'operazione di registrazione.
 */
public class NuovoUtenteDialog implements MyDialog
{
	public static final int DEFAULT_FIELD_LENGTH = 15;
	public static final int DEFAULT_PANEL_WIDTH = 300;
	public static final int DEFAULT_PANEL_HEIGHT = 30;
	private JDialog finestra;
	private JTextField nomeField, cognomeField, codiceFiscaleField, indirizzoField, emailField, userIdField, passwordField;
	private JLabel nomeLabel, cognomeLabel, codiceFiscaleLabel, indirizzoLabel, emailLabel, userIdLabel, passwordLabel;
	private JLabel verifiedIcon, invalidatedIcon; //TODO metterne 2 per ogni field
	private JButton confermaButton, annullaButton;
	private JPanel mainPanel, buttonPanel, fieldPanel, labelPanel, iconPanel;
	
	private MainModel mainModel;
	private boolean codiceFiscaleFlag, emailFlag, userIdFlag;

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
		confermaButton = new JButton("Connettiti");
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
		setVerifier();
		confermaButton.addActionListener(new ActionListener()
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
		});
	}

	/**
	 * Imposta i verificatori per i campi di input, abilita o disabilita il pulsante di conferma in base alla validità dei dati.
	 */
	public void setVerifier()
	{
		confermaButton.setEnabled(false);
		buttonPanel.validate();
		buttonPanel.repaint();

		// Per il campo Nome e il campo Cognome, ci sono listener associati all'evento focusLost, che viene attivato quando il campo perde il focus
		// ovvero quando l'utente passa a un altro campo o clicca altrove nella finestra.
		// Se uno dei campi Nome o Cognome è vuoto (blank), viene mostrato un messaggio di avviso tramite JOptionPane.
		nomeField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				if(nomeField.getText().isBlank())
				{
					JOptionPane.showMessageDialog(mainPanel, "Il nome non può essere vuoto!", "Nome invalido", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		cognomeField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				if(nomeField.getText().isBlank())
				{
					JOptionPane.showMessageDialog(mainPanel, "Il cognome non può essere vuoto!", "Cognome invalido", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		// Per il campo Codice Fiscale, c'è un listener associato all'evento focusLost che verifica la lunghezza del testo inserito.
		// Se la lunghezza del Codice Fiscale non è esattamente di 16 caratteri, viene mostrato un messaggio di avviso tramite JOptionPane.
		codiceFiscaleField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				if(codiceFiscaleField.getText().length() != 16)
				{
					JOptionPane.showMessageDialog(mainPanel,
							"Il codice fiscale non è corretto (la lunghezza deve essere di 16 caratteri)",
							"Codice Fiscale invalido", JOptionPane.WARNING_MESSAGE);
					codiceFiscaleFlag = false;
				}
				else
				{
					codiceFiscaleFlag = true;
				}
			}
		});
		
		indirizzoLabel.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				if(indirizzoField.getText().isBlank())
				{
					JOptionPane.showMessageDialog(mainPanel, "L'indirizzo non può essere vuoto!", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		// Utilizza un'espressione regolare (regularSyntax) per verificare se il testo inserito corrisponde a un formato di indirizzo e-mail valido
		emailField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				final String regularSyntax = "[A-Za-z0-9]+[._-]?[A-Za-z0-9]+@[[A-Za-z0-9]+.[A-Za-z0-9]+]+";
				if(emailField.getText().matches(regularSyntax))
				{
					emailFlag = true;
				}
				else
				{
					JOptionPane.showMessageDialog(mainPanel, "L'e-mail non è valida", "E-Mail invalido", JOptionPane.WARNING_MESSAGE);
					emailFlag = false;
				}
			}
		});

		// Verifica se l'username è già in uso chiamando un metodo controlloUserid del mainModel
		userIdField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				try
				{
					if(mainModel.controlloUserid(userIdField.getText()))
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

		// Verifica la lunghezza della password
		passwordField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				if(codiceFiscaleFlag && emailFlag && userIdFlag)
				{
					confermaButton.setEnabled(true);
				}
				if(passwordField.getText().length()<6)
				{
					JOptionPane.showMessageDialog(mainPanel, "Password troppo corta!", "Password invalida", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
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
