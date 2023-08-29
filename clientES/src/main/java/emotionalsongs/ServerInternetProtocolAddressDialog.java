package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ServerInternetProtocolAddressDialog
{
	private JDialog finestra;
	private JLabel serverLabel;
	private JTextField serverIPField;
	private JButton connettiButton, annullaButton;
	private JPanel buttonPanel, fieldPanel, mainPanel;
	
	private MainModel mainModel;
	
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
		finestra.setVisible(true);
	}
	
	public ServerInternetProtocolAddressDialog(MainModel mainModel)
	{
		this();
		this.mainModel = mainModel;
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
				try
				{
					if (serverIPField.getText().equals(""))
					{
						mainModel.setStub(null);
					}
					mainModel.setStub(serverIPField.getText());
					finestra.dispose();
				}
				catch(RemoteException exception)
				{
					JOptionPane.showMessageDialog(mainPanel, exception.getMessage(), "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonPanel.add(connettiButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				serverIPField.setText("");
			}
		});
		buttonPanel.add(annullaButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
}
