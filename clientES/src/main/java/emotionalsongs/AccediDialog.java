package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class AccediDialog
{
	public static final int DEFAULT_FIELD_LENGTH = 15;
	private JDialog finestra;
	private JPanel mainPanel, fieldPanel, buttonPanel, userPanel, passwordPanel;
	JTextField userField;
	JPasswordField passwordField;
	JButton accediButton;
	private JButton	annullaButton;
	private JLabel userLabel, passwordLabel;
	private MainModel mainModel;
	
	public AccediDialog(MainModel mainModel)
	{
		this();
		this.mainModel = mainModel;

	}
	
	public AccediDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("ACCEDI");
		finestra.setSize(250, 150);
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		initializeField();
		initializeButtons();
		
		finestra.add(mainPanel);
		finestra.setVisible(true);
	}
	
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
	
	private void initializeButtons()
	{
		buttonPanel = new JPanel();
		
		accediButton = new JButton("Accedi");
		accediButton.addActionListener(new MainController());
		accediButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String user = userField.getText();
				String password = String.valueOf(passwordField.getPassword());
				try
				{
					if(mainModel.accedi(user, password))
					{
						finestra.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(mainPanel, "Password o username errati", "ERRORE", JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException ex)
				{
					JOptionPane.showMessageDialog(mainPanel, "Connessione con il server non disponibile", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
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
}
