package emotionalsongs;

import common.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Questa classe rappresenta una finestra di dialogo per aggiungere una canzone a una playlist.
 * L'utente può selezionare la playlist di destinazione e confermare o annullare l'operazione.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 */
public class AggiungiCanzoneDialog implements MyDialog
{
	private String idCanzone;						// ID della canzone da aggiungere
	private JDialog finestra;						// La finestra di dialogo
	private JPanel mainPanel, buttonPanel;			// Pannelli principale e dei pulsanti
	private JButton confermaButton, annullaButton;	// Pulsanti di conferma e annulla
	private JList<Playlist> playlistJList;			// Lista delle playlist disponibili

	/**
	 * Costruisce una nuova finestra di dialogo per aggiungere una canzone a una playlist.
	 */
	public AggiungiCanzoneDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("AGGIUNGI CANZONE");
		finestra.setModal(true);
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		finestra.setMinimumSize(new Dimension(300, 200));
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeMain();		// Inizializza il pannello principale
		initializeButton();		// Inizializza i pulsanti
		
		finestra.add(mainPanel);
		finestra.setVisible(true);
		finestra.setLocationRelativeTo(MainView.finestra);
	}

	/**
	 * Costruisce una nuova finestra di dialogo per aggiungere una canzone a una playlist,
	 * specificando l'ID della canzone.
	 *
	 * @param idCanzone L'ID della canzone da aggiungere.
	 */
	public AggiungiCanzoneDialog(String idCanzone)
	{
		this();
		this.idCanzone = idCanzone;
	}

	/**
	 * Inizializza l'etichetta e la lista delle playlist nel pannello principale.
	 */
	private void initializeMain()
	{
		JLabel label = new JLabel("Seleziona la playlist in cui inserire la canzone:", JLabel.CENTER);
		mainPanel.add(label, BorderLayout.NORTH);

		playlistJList = new JList<>();
		playlistJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		mainPanel.add(new JScrollPane(playlistJList), BorderLayout.CENTER);
	}

	/**
	 * Inizializza e gestisce i pulsanti di conferma e annulla nel pannello dei pulsanti.
	 */
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

	/**
	 * Imposta il modello principale e gestisce l'azione del pulsante di conferma.
	 *
	 * @param mainModel Il modello principale dell'applicazione.
	 */
	public void setMainModel(MainModel mainModel)
	{
		confermaButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					if(!mainModel.controllaCanzonePersona(idCanzone))
					{
						SelectPlaylistDialog selectPlaylistDialog = new SelectPlaylistDialog(SelectPlaylistDialog.SELEZIONA_PLAYLIST);
						selectPlaylistDialog.setIdCanzone(idCanzone);
						selectPlaylistDialog.setMainModel(mainModel);
						selectPlaylistDialog.draw();
					}
					//mainModel.aggiungiCanzone(idCanzone, playlistJList.getSelectedValue().getIdPlaylist());
				} catch (RemoteException ex)
				{
					ex.printStackTrace();
					//JOptionPane.showMessageDialog(MainView.finestra, "Impossibile salvare la canzone nella playlist", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
				boolean flag = false;
				try{
					if(mainModel.aggiungiCanzone(idCanzone, playlistJList.getSelectedValue().getIdPlaylist()))
						flag=true;
				}
				 catch (IOException ex)
				{
					JOptionPane.showMessageDialog(MainView.finestra, "Impossibile salvare la canzone nella playlist", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
				if(flag)
				{
					JOptionPane.showMessageDialog(mainPanel, "Canzone inserita con successo", "SUCCESSO", JOptionPane.INFORMATION_MESSAGE);
					finestra.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(mainPanel, "Canzone già presente in playlist, non puoi inserirla nuovamente", "WARNING", JOptionPane.WARNING_MESSAGE);
					finestra.dispose();
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
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
