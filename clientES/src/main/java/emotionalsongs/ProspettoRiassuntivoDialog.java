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
	 * Questo pannello contiene una tabella con le emozioni e le relative medie.
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
	 * Inizializza il pannello scroll che contiene le eventuali note associate al prospetto riassuntivo.
	 * Questo pannello contiene una JTextArea in cui vengono visualizzate le note, con la possibilità di scorrere il testo se necessario.
	 */
	private void initializeScroll()
	{
		scrollPanel = new JPanel(new BorderLayout());
		note = new JTextArea("Nessuna nota disponibili per questa canzone");
		note.setEditable(false);
		note.setVisible(false);
		scrollPane = new JScrollPane(note);
		scrollPanel.add(scrollPane);
		
		mainPanel.add(scrollPanel, BorderLayout.CENTER);
	}

	/**
	 * Inizializza i pulsanti per visualizzare, nascondere le note e uscire dalla finestra di dialogo.
	 * Questo pannello contiene i pulsanti "Visualizza Note", "Nascondi Note" e "Esci".
	 */
	private void initializeButtons()
	{
		buttonPanel = new JPanel();
		visualizzaNoteButton = new JButton("Visualizza Note");
		visualizzaNoteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				note.setVisible(true);
				scrollPane.validate();
				scrollPane.repaint();
				visualizzaNoteButton.setVisible(false);
				nascondiNoteButton.setVisible(true);
				buttonPanel.validate();
				buttonPanel.repaint();
			}
		});
		buttonPanel.add(visualizzaNoteButton);
		
		nascondiNoteButton = new JButton("Nascondi Note");
		nascondiNoteButton.setVisible(false);
		nascondiNoteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
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
		
		esciButton = new JButton("Esci");
		esciButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) { finestra.dispose(); }
		});
		buttonPanel.add(esciButton);
		
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
