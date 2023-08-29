package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuovaPlaylistDialog
{
	
	private JDialog finestra;
	private JPanel mainPanel, buttonPanel, fieldPanel;
	private JButton confermaButton, annullaButton;
	private JLabel nomeLabel;
	private JTextField nomeField;
	
	public NuovaPlaylistDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("NUOVA PLAYLIST");
		finestra.setModal(true);
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeButton();
		initializeField();
		
		finestra.add(mainPanel);
		finestra.pack();
		finestra.setVisible(true);
		finestra.setLocationRelativeTo(MainView.finestra);
	}
	
	private void initializeField()
	{
		fieldPanel = new JPanel();
		
		nomeLabel = new JLabel("Nome della playlist:");
		fieldPanel.add(nomeLabel);
		
		nomeField = new JTextField(25);
		fieldPanel.add(nomeField);
		
		mainPanel.add(fieldPanel, BorderLayout.CENTER);
	}
	
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		buttonPanel.setVisible(true);
		
		confermaButton = new JButton("Conferma");
		confermaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//TODO
			}
		});
		buttonPanel.add(confermaButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) { finestra.dispose(); }
		});
		buttonPanel.add(annullaButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
}
