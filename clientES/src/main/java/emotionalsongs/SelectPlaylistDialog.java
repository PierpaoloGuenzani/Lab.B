package emotionalsongs;

import common.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Questa classe rappresenta una finestra di dialogo per la selezione delle playlist.
 * Può essere utilizzata per la visualizzazione delle playlist o per la selezione di una playlist in cui inserire una canzone.
 * La finestra di dialogo include una lista delle playlist dell'utente e pulsanti di conferma e annullamento.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 **/
public class SelectPlaylistDialog implements MyDialog
{
	/**
	 * Costante per visualizzare le playlist.
	 */
	public static final int VISUALIZZA_PLAYLIST = 0;
	/**
	 * Costante per selezionare una playlist in cui inserire una canzone.
	 */
	public static final int SELEZIONA_PLAYLIST = 1;
	//TODO altre selezioni? se no trasformare in boolean
	
	
	private JDialog finestra;
	private JPanel mainPanel, buttonPanel;
	private JScrollPane scrollPane;
	private JList<Playlist> lista;
	private JButton confermaButton, annullaButton;
	
	private String idCanzone;
	private int state;

	/**
	 * Costruttore della classe.
	 * @param selezione Costante che indica se la finestra è utilizzata per visualizzare o selezionare una playlist.
	 */
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

	/**
	 * Inizializza la lista delle playlist nella finestra di dialogo.
	 */
	private void initializeList()
	{
		lista = new JList<>();
		scrollPane = new JScrollPane(lista);
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Inizializza i pulsanti della finestra di dialogo.
	 */
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

	/**
	 * Disegna la finestra di dialogo.
	 */
	@Override
	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}

	/**
	 * Imposta il modello principale per la finestra di dialogo.
	 *
	 * @param mainModel Modello principale dell'applicazione.
	 */
	public void setMainModel(MainModel mainModel)
	{
		try
		{
			lista.setModel(mainModel.cercaPlaylistUtente());
		} catch (RemoteException e)	{
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
						//TODO: creare nuovo dialogo per visualizzare tutte le canzoni della playlist
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

	/**
	 * Imposta l'ID della canzone da inserire nella playlist.
	 * @param idCanzone ID della canzone.
	 */
	public void setIdCanzone(String idCanzone)
	{
		this.idCanzone = idCanzone;
	}
}
