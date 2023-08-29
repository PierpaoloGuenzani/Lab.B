package emotionalsongs;

import common.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiCanzoneDialog
{
	private JDialog finestra;
	private JPanel mainPanel, buttonPanel;
	private JButton confermaButton, annullaButton;
	private JList<Playlist> playlistJList;
	
	public AggiungiCanzoneDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("SELEZIONA LA PLAYLIST");
		finestra.setModal(true);
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		finestra.setMinimumSize(new Dimension(300, 200));
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeMain();
		initializeButton();
		
		finestra.add(mainPanel);
		finestra.setVisible(true);
		finestra.setLocationRelativeTo(MainView.finestra);
	}
	
	private void initializeMain()
	{
		JLabel label = new JLabel("Seleziona la playlist in cui inserire la canzone:", JLabel.CENTER);
		mainPanel.add(label, BorderLayout.NORTH);
		
		playlistJList = new JList<>();
		playlistJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		mainPanel.add(new JScrollPane(playlistJList), BorderLayout.CENTER);
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
