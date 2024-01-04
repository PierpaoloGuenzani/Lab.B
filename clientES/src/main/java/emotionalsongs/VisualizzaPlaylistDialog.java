package emotionalsongs;

import common.Canzone;
import common.Playlist;
import common.ProspettoRiassuntivo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Questa classe rappresenta una finestra di dialogo per la visualizzazione delle canzoni di una playlist
 * e per la successiva visualizzazione delle emozioni associate a una canzone selezionata.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 */
public class VisualizzaPlaylistDialog implements MyDialog
{
	private JDialog finestra;
	private JPanel mainPanel, buttonPanel;
	private JScrollPane scrollPane;
	private JList<Canzone> lista;
	private JButton confermaButton, annullaButton;
	private String idPlaylist;

	/**
	 * Costruisce una nuova finestra di dialogo per la visualizzazione delle canzoni di una playlist.
	 */
	public VisualizzaPlaylistDialog()
	{
		finestra = new JDialog();
		finestra.setModal(true);
		finestra.setTitle("VISUALIZZA CANZONE");
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Seleziona la canzone da visualizzare:");
		mainPanel.add(label, BorderLayout.NORTH);
		
		initializeList();
		initializeButton();
		
		finestra.add(mainPanel);
	}

	/**
	 * Inizializza la lista delle canzoni.
	 */
	private void initializeList()
	{
		lista = new JList<>();
		scrollPane = new JScrollPane(lista);
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Inizializza e gestisce i pulsanti per la visualizzazione delle emozioni o l'annullamento dell'operazione.
	 */
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		
		confermaButton = new JButton("Visualizza Emozioni");
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
	 * Imposta l'identificatore della playlist.
	 *
	 * @param idPlaylist L'identificatore della playlist
	 */
	public void setIdPlaylist(String idPlaylist)
	{
		this.idPlaylist = idPlaylist;
	}

	/**
	 * Imposta il modello principale per la finestra di dialogo
	 * e gestisce l'azione del pulsante di visualizzazione delle emozioni.
	 *
	 * @param mainModel Il modello principale dell'applicazione
	 */
	public void setMainModel(MainModel mainModel)
	{
		if(idPlaylist == null) return;
		try
		{
			lista.setModel(mainModel.canzoniDaIdPlaylist(idPlaylist));
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(MainView.finestra,"Errore di comunicazione col server", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		confermaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(lista.isSelectionEmpty())
				{
					JOptionPane.showMessageDialog(MainView.finestra,"Seleziona una canzone", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					try
					{
						mainModel.visualizzaEmozioni(lista.getSelectedValue().getId());
					} catch (RemoteException ex)
					{
						JOptionPane.showMessageDialog(MainView.finestra,"Errore di comunicazione col server", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
	}

	/**
	 * Mostra la finestra di dialogo al centro della finestra principale.
	 */
	@Override
	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
