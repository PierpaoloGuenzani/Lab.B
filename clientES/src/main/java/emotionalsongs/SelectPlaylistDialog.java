package emotionalsongs;

import common.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectPlaylistDialog implements MyDialog
{
	public static final int VISUALIZZA_PLAYLIST = 0;
	public static final int SELEZIONA_PLAYLIST = 1;
	//TODO altre selezioni?
	
	
	private JDialog finestra;
	private JPanel mainPanel, buttonPanel;
	private JScrollPane scrollPane;
	private JList<Playlist> lista;
	private JButton confermaButton, annullaButton;
	
	public SelectPlaylistDialog()
	{
		this(0);
	}
	
	public SelectPlaylistDialog(int selezione)
	{
		finestra = new JDialog();
		finestra.setModal(true);
		finestra.setTitle("SELEZIONA PLAYLIST");
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		JLabel label;
		if(selezione == VISUALIZZA_PLAYLIST)
			label = new JLabel("Seleziona la playlist da visualizzare");
		else
			label = new JLabel("Seleziona la playlist in cui inserire la canzone");
		mainPanel.add(label, BorderLayout.NORTH);
		
		initializeList();
		initializeButton();
		
		finestra.add(mainPanel);
	}
	
	private void initializeList()
	{
		lista = new JList<>();
		scrollPane = new JScrollPane(lista);
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		
		confermaButton = new JButton("Conferma");
		buttonPanel.add(confermaButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {finestra.dispose();}
		});
		buttonPanel.add(annullaButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
