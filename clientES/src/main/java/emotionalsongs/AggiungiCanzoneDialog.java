package emotionalsongs;

import common.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AggiungiCanzoneDialog implements MyDialog
{
	private String idCanzone;
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
//		finestra.setVisible(true);
//		finestra.setLocationRelativeTo(MainView.finestra);
	}
	
	public AggiungiCanzoneDialog(String idCanzone)
	{
		this();
		this.idCanzone = idCanzone;
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
	
	public void setMainModel(MainModel mainModel)
	{
		confermaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					mainModel.aggiungiCanzone(idCanzone, playlistJList.getSelectedValue().getIdPlaylist());
				} catch (IOException ex)
				{
					JOptionPane.showMessageDialog(MainView.finestra, "Impossibile salvare la canzone nella playlist", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	

	@Override
	public void draw()
	{
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
