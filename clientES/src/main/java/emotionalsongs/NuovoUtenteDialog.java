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
	
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		confermaButton = new JButton("Connettiti");
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
					mainModel.Registrazione(utenteRegistrato);
				} catch (IOException ex)
				{
					JOptionPane.showMessageDialog(mainPanel, "Errore di connessione con il server!", "ERRORE", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
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
	
	public void setMainModel(MainModel mainModel)
	{
		this.mainModel = mainModel;
		setVerifier();
	}
	
	public void setVerifier()
	{
		confermaButton.setEnabled(false);
		buttonPanel.validate();
		buttonPanel.repaint();
		
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
		
		codiceFiscaleField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				if(codiceFiscaleField.getText().length()<15)
				{
					JOptionPane.showMessageDialog(mainPanel, "Il codice fiscale non è corretto", "Codice Fiscale invalido", JOptionPane.WARNING_MESSAGE);
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
				if(nomeField.getText().isBlank())
				{
					JOptionPane.showMessageDialog(mainPanel, "Il cognome non può essere vuoto!", "Indirizzo invalido", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
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
		
		passwordField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				super.focusLost(e);
				if(passwordField.getText().length()<6)
				{
					JOptionPane.showMessageDialog(mainPanel, "Password troppo corta!", "Password invalida", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					if(codiceFiscaleFlag && emailFlag && userIdFlag)
					{
						confermaButton.setEnabled(true);
					}
				}
			}
		});
	}

	@Override
	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
