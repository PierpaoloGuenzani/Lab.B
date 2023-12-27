package emotionalsongs;

import common.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

public class SelectPlaylistDialog implements MyDialog
{
	public static final int VISUALIZZA_PLAYLIST = 0;
	public static final int SELEZIONA_PLAYLIST = 1;
	//TODO altre selezioni? se no trasformare in boolean
	
	
	private JDialog finestra;
	private JPanel mainPanel, buttonPanel;
	private JScrollPane scrollPane;
	private JList<Playlist> lista;
	private JButton confermaButton, annullaButton;
	
	private String idCanzone;
	private int state;
	
	public SelectPlaylistDialog(int selezione)
	{
		state = selezione;
		
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
	
	public void setMainModel(MainModel mainModel)
	{
		try
		{
			lista.setModel(mainModel.cercaPlaylistUtente());
		} catch (RemoteException e)
		{
			JOptionPane.showMessageDialog(MainView.finestra, "Errore di comunicazione col server", "ERROR", JOptionPane.INFORMATION_MESSAGE);
			//log
		}
		confermaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(lista.isSelectionEmpty())
				{
					JOptionPane.showMessageDialog(MainView.finestra, "Nessun playlist selezionata", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					if (state == 0)
					{
						//TODO: creare nuovo dialogo per visualizzare tutte le canzoni della playlis
						VisualizzaPlaylistDialog visualizzaPlaylistDialog = new VisualizzaPlaylistDialog();
						visualizzaPlaylistDialog.setIdPlaylist(lista.getSelectedValue().getIdPlaylist());
						visualizzaPlaylistDialog.setMainModel(mainModel);
						visualizzaPlaylistDialog.draw();
					}
					else
					{
					
						try
						{
							mainModel.aggiungiCanzone(idCanzone, lista.getSelectedValue().getIdPlaylist());
						}
						catch (IOException ex)
						{
							JOptionPane.showMessageDialog(MainView.finestra,
									"Errore di comunicazione col server/ nessuna canzone selezionata", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(MainView.finestra,"Errore di comunicazione col server", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
	}
	
	public void setIdCanzone(String idCanzone)
	{
		this.idCanzone = idCanzone;
	}
}
