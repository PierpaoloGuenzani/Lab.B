package emotionalsongs;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NuovaPlaylistDialog implements MyDialog
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

	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}

	public void setMainModel(MainModel mainModel)
	{
		confermaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					mainModel.RegistraPlaylist(nomeField.getText());
				}
				catch (IOException ex)
				{
					String titolo = "CONNECTION ERROR";
					String message = "Errore di comunicazione con il server!";
					JOptionPane.showMessageDialog(MainView.finestra, message, titolo, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
