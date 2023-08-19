package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuovoUtenteDialog
{
	public static final int DEFAULT_FIELD_LENGTH = 15;
	public static final int DEFAULT_PANEL_WIDTH = 300;
	public static final int DEFAULT_PANEL_HEIGHT = 30;
	private JDialog finestra;
	private JTextField nomeField, cognomeField, codiceFiscaleField, indirizzoField, emailField, userIdField, passwordField, checkPasswordField;
	private JLabel nomeLabel, cognomeLabel, codiceFiscaleLabel, indirizzoLabel, emailLabel, userIdLabel, passwordLabel, checkPasswordLabel;
	private JLabel verifiedIcon, invalidatedIcon; //TODO metterne 2 per ogni field
	private JButton confermaButton, annullaButton;
	private JPanel mainPanel, buttonPanel, fieldPanel, labelPanel, iconPanel;
	
	public NuovoUtenteDialog()
	{
		finestra = new JDialog();
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		finestra.setTitle("Nuovo utente");
		finestra.setMinimumSize(new Dimension(500, 400));
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeField();
		initializeButton();
		
		finestra.add(mainPanel);
		finestra.setVisible(true);
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
		labelPanel.add(cognomeLabel);
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

		checkPasswordLabel = new JLabel("Conferma password:");
		checkPasswordLabel.setLabelFor(checkPasswordField);
		labelPanel.add(checkPasswordLabel);
		checkPasswordField = new JTextField(DEFAULT_FIELD_LENGTH);
		fieldPanel.add(checkPasswordField);
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
}
