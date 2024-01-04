package emotionalsongs;

import common.Emozione;
import common.ProspettoRiassuntivo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Questa classe rappresenta una finestra di dialogo per la visualizzazione di un prospetto riassuntivo
 * delle emozioni associate a una canzone, comprese le medie delle emozioni e le eventuali note.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 */
public class ProspettoRiassuntivoDialog implements MyDialog
{
	private JDialog finestra;
	private JPanel mainPanel, scrollPanel, tablePanel, buttonPanel;
	private JScrollPane scrollPane;
	private JLabel[] valoreEmozioniLabel;
	private JTextArea note;
	private JButton visualizzaNoteButton, nascondiNoteButton, esciButton;

	/**
	 * Costruttore della classe che accetta un oggetto ProspettoRiassuntivo come parametro.
	 * Inizializza la finestra di dialogo e visualizza le medie delle emozioni e le eventuali note associate al prospetto riassuntivo fornito.
	 *
	 * @param prospettoRiassuntivo Il prospetto riassuntivo da visualizzare.
	 */
	public ProspettoRiassuntivoDialog(ProspettoRiassuntivo prospettoRiassuntivo)
	{
		this();
		for (int i = 0; i < prospettoRiassuntivo.getMedieEmozioni().length; i++)
		{
			String s = String.valueOf(prospettoRiassuntivo.getMedieEmozioni()[i]);
			valoreEmozioniLabel[i].setText(s);
		}
		note.setText("NESSUNA NOTA DISPONIBILE");
		if(prospettoRiassuntivo.getNote().size() != 0)
		{
			note.setText("");
			for (String s : prospettoRiassuntivo.getNote())
			{
				note.append(s + "\n");
			}
		}
		scrollPanel.validate();
		scrollPanel.repaint();
	}

	/**
	 * Costruttore della classe. Crea una nuova istanza di ProspettoRiassuntivoDialog.
	 */
	public ProspettoRiassuntivoDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("PROSPETTO RIASSUNTIVO");
		finestra.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		finestra.setPreferredSize(new Dimension(850, 400));
		finestra.setMinimumSize(new Dimension(850, 400));
		finestra.setModal(true);
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeTable();
		initializeScroll();
		initializeButtons();
		
		finestra.add(mainPanel);
//		finestra.setVisible(true);
//		finestra.setLocationRelativeTo(MainView.finestra);
	}

	/**
	 * Inizializza il pannello della tabella che visualizza le emozioni e le loro medie.
	 */
	private void initializeTable()
	{
		tablePanel = new JPanel(new GridLayout(0, (Emozione.values().length + 1)));
		
		int n = Emozione.values().length;
		JLabel emozioneLabel = new JLabel("Emozione: ");
		emozioneLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		tablePanel.add(emozioneLabel);
		
		for(Emozione e : Emozione.values())
		{
			JLabel emozione = new JLabel(e.name(), JLabel.CENTER);
			emozione.setBorder(BorderFactory.createLineBorder(Color.black));
			tablePanel.add(emozione);
		}
		
		JLabel mediaLabel = new JLabel("Media: ");
		mediaLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		tablePanel.add(mediaLabel);
		valoreEmozioniLabel = new JLabel[n];
		
		for(int i = 0;  i < n; i++ )
		{
			valoreEmozioniLabel[i] = new JLabel("0", JLabel.CENTER);
			valoreEmozioniLabel[i].setBorder(BorderFactory.createLineBorder(Color.black));
			tablePanel.add(valoreEmozioniLabel[i]);
		}
		
		mainPanel.add(tablePanel, BorderLayout.NORTH);
	}

	/**
	 * Inizializza il pannello scroll che contiene una JTextArea in cui vengono visualizzate le eventuali note,
	 * con la possibilità di scorrere il testo se necessario.
	 */
	private void initializeScroll()
	{
		scrollPanel = new JPanel(new BorderLayout());
		// JTextArea viene inizializzata con un testo predefinito e viene resa non editabile
		note = new JTextArea("Nessuna nota disponibile per questa canzone");
		note.setEditable(false);
		// visibilità impostata su false inizialmente
		note.setVisible(false);
		// viene creato un componente di scorrimento (JScrollPane) che avvolge la JTextArea (note)
		scrollPane = new JScrollPane(note);
		// viene aggiunto al JPanel (scrollPanel)
		scrollPanel.add(scrollPane);
		// pannello scroll viene quindi aggiunto al pannello principale (mainPanel) della finestra
		mainPanel.add(scrollPanel, BorderLayout.CENTER);
	}

	/**
	 * Inizializza e gestisce i pulsanti per la visualizzazione e l'occultamento delle note, nonché un pulsante per chiudere la finestra.
	 */
	private void initializeButtons()
	{
		// Viene creato un pannello per contenere i pulsanti
		buttonPanel = new JPanel();

		// Pulsante per visualizzare le note
		visualizzaNoteButton = new JButton("Visualizza Note");
		visualizzaNoteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Mostra la JTextArea delle note e aggiorna l'aspetto dei componenti
				note.setVisible(true);   // imposta la visibilità di JTextArea
				scrollPane.validate();   // ricalcola il layout per adattarsi a note
				scrollPane.repaint();    // richiesta di ridisegnarsi per adattarsi a note
				visualizzaNoteButton.setVisible(false);
				nascondiNoteButton.setVisible(true);
				buttonPanel.validate();
				buttonPanel.repaint();
			}
		});
		buttonPanel.add(visualizzaNoteButton);

		// Pulsante per nascondere le note
		nascondiNoteButton = new JButton("Nascondi Note");
		nascondiNoteButton.setVisible(false);
		nascondiNoteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Nasconde la JTextArea delle note e aggiorna l'aspetto dei componenti
				note.setVisible(false);
				scrollPane.validate();
				scrollPane.repaint();
				visualizzaNoteButton.setVisible(true);
				nascondiNoteButton.setVisible(false);
				buttonPanel.validate();
				buttonPanel.repaint();
			}
		});
		buttonPanel.add(nascondiNoteButton);

		// Pulsante per chiudere la finestra
		esciButton = new JButton("Esci");
		esciButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) { finestra.dispose(); }
		});
		buttonPanel.add(esciButton);

		// Aggiunge il pannello dei pulsanti al pannello principale nella parte inferiore
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Mostra la finestra di dialogo.
	 */
	@Override
	public void draw()
	{
		finestra.setVisible(true);
		finestra.setLocationRelativeTo(MainView.finestra);
	}
}
