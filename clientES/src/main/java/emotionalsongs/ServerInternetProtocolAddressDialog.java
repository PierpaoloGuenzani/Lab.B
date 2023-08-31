package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ServerInternetProtocolAddressDialog implements MyDialog
{
	private JDialog finestra;
	private JLabel serverLabel;
	private JTextField serverIPField;
	private JButton connettiButton, annullaButton;
	private JPanel buttonPanel, fieldPanel, mainPanel;
	
	public ServerInternetProtocolAddressDialog()
	{
		finestra = new JDialog();
		finestra.setModal(true);
		finestra.setTitle("SERVER IP");
		finestra.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		mainPanel = new JPanel(new BorderLayout());
		finestra.setMinimumSize(new Dimension(250, 150));
		
		initializeField();
		initializeButton();
		
		finestra.add(mainPanel);
//		finestra.setLocationRelativeTo(MainView.finestra);
//		finestra.setVisible(true);
	}
	
	private void initializeField()
	{
		fieldPanel = new JPanel();
		serverLabel = new JLabel("IP server:");
		fieldPanel.add(serverLabel);
		serverIPField = new JTextField(10);
		fieldPanel.add(serverIPField);
		
		mainPanel.add(fieldPanel, BorderLayout.CENTER);
	}
	
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

	@Override
	public void draw()
	{
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
