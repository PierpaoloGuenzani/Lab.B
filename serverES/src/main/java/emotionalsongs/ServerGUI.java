package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ServerGUI
{
	//todo DA RIMUOVERE PRIMA DELLA CONSEGNA
	private final String myUrl = "jdbc:postgresql:dbES";
	private final String myUser = "postgres";
	private final String myPassword = "admin";
	JFrame frame;
	JPanel interno, urlPanel, userPanel, passwordPanel, buttonPanel;
	JButton conferma, annulla;
	JTextField urlField, userField;
	JPasswordField passwordField;
	JLabel urlLabel, userLabel, passwordLabel;
	
	public ServerGUI()
	{
		ClearTextFieldOnFocus cof = new ClearTextFieldOnFocus();
		frame = new JFrame();
		frame.setTitle("ServerGUI");
		//frame.setIconImage();
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		interno = new JPanel();
		//interno.setSize(800,400);
		interno.setVisible(true);
		
		urlPanel = new JPanel();
		urlPanel.setLayout(new BoxLayout(urlPanel , BoxLayout.LINE_AXIS));
		urlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel , BoxLayout.LINE_AXIS));
		userPanel.setSize(300,30);
		userPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		passwordPanel = new JPanel();
		passwordPanel.setLayout(new BoxLayout(passwordPanel , BoxLayout.LINE_AXIS));
		passwordPanel.setSize(300,30);
		passwordPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel , BoxLayout.LINE_AXIS));
		buttonPanel.setSize(300,30);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		urlLabel = new JLabel("Server url:");
		urlLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		urlPanel.add(urlLabel);
		
		urlField = new JTextField(20);
		urlField.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
		urlField.setText(myUrl);
		urlField.addFocusListener(cof);
		urlPanel.add(urlField);
		
		userLabel = new JLabel("User:");
		userLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		userPanel.add(userLabel);
		
		userField = new JTextField(20);
		userField.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
		userField.setText(myUser);
		userField.addFocusListener(cof);
		userPanel.add(userField);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		passwordPanel.add(passwordLabel);
		
		passwordField = new JPasswordField(20);
		passwordField.setAlignmentX(JPasswordField.RIGHT_ALIGNMENT);
		passwordField.setText(myPassword);
		passwordField.addFocusListener(cof);
		passwordPanel.add(passwordField);
		
		conferma = new JButton("Conferma");
		//frame.getRootPane().setDefaultButton(conferma);
		conferma.requestFocus();
		buttonPanel.add(conferma);
		
		annulla = new JButton("Annulla");
		annulla.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				urlField.setText("");
				userField.setText("");
				passwordField.setText("");
			}
		});
		buttonPanel.add(annulla);
		
		urlPanel.setSize(300,35);
		interno.add(urlPanel);
		urlPanel.setSize(300,35);
		interno.add(userPanel);
		urlPanel.setSize(300,35);
		interno.add(passwordPanel);
		urlPanel.setSize(300,35);
		interno.add(buttonPanel);
		
		frame.add(interno);
		//frame.pack(); non necessario
		frame.setVisible(true);
		System.out.println(urlPanel.getSize());
		System.out.println(userPanel.getSize());
		System.out.println(passwordPanel.getSize());
		System.out.println(buttonPanel.getSize());
	}
	
	private class ClearTextFieldOnFocus implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent e)
		{
			JTextField text = (JTextField)e.getSource();
			text.setText("");
		}
		
		@Override
		public void focusLost(FocusEvent e) {}
	}
}

