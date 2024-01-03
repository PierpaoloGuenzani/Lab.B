package emotionalsongs;

import common.Emozione;
import common.Percezione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Questa classe rappresenta una finestra di dialogo per l'aggiunta di nuove emozioni a una canzone.
 * L'utente può assegnare un punteggio e aggiungere note per ciascuna emozione disponibile.
 *
 * Implementa l'interfaccia MyDialog che fornisce il metodo draw per disegnare la finestra di dialogo.
 * @see MyDialog
 */
public class AggiungiEmozioneDialog implements MyDialog
{
	private String songId;                // ID della canzone a cui aggiungere emozioni

	private JDialog finestra;             // La finestra di dialogo
	private JComboBox[] comboBoxes;       // Array di menu a discesa per i punteggi delle emozioni
	private JTextArea[] textAreas;        // Array di aree di testo per le note delle emozioni
	private JButton confermaButton, annullaButton;  // Pulsanti di conferma e annulla
	private JPanel mainPanel, insidePanel, buttonPanel; // Pannelli principale, interno e dei pulsanti

	/**
	 * Costruisce una nuova finestra di dialogo per l'aggiunta di nuove emozioni a una canzone.
	 */
	public AggiungiEmozioneDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("NUOVA EMOZIONE");
		finestra.setModal(true);
		finestra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());

		initializeScrollPanel();    // Inizializza il pannello interno con i menu a discesa e le aree di testo
		initializeButton();         // Inizializza i pulsanti
		
		finestra.add(mainPanel);
//		finestra.pack();
//		finestra.setVisible(true);
//		finestra.setLocationRelativeTo(MainView.finestra);
	}

	/**
	 * Inizializza il pannello interno con i menu a discesa e le aree di testo per le emozioni.
	 * Il pannello include tre colonne: una per il nome dell'emozione, una per il punteggio (menu a discesa),
	 * e una per eventuali note (area di testo). Questo pannello viene quindi aggiunto al pannello principale nella regione centrale.
	 */
	private void initializeScrollPanel()
	{
		// Crea un nuovo pannello con layout a griglia
		insidePanel = new JPanel(new GridLayout(0,3));
		insidePanel.setVisible(true);
		// Aggiunge un bordo verde al pannello interno per una migliore visibilità
		insidePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		// Array di stringhe per rappresentare i possibili punteggi
		String[] array = {"0", "1", "2", "3", "4", "5"};
		// Recupera tutte le possibili emozioni dall'enum Emozione
		Emozione[] emozioni = Emozione.values();
		// Inizializza gli array di JComboBox e JTextArea con la lunghezza dell'enum Emozione
		comboBoxes = new JComboBox[emozioni.length];
		textAreas = new JTextArea[emozioni.length];

		// Itera su tutte le emozioni
		for(int i = 0; i < emozioni.length; i++)
		{
			// Aggiunge una JLabel con il nome dell'emozione al pannello interno
			JLabel label = new JLabel(emozioni[i].name());
			insidePanel.add(label);
			// Inizializza un JComboBox con i punteggi possibili e lo aggiunge al pannello interno
			comboBoxes[i] = new JComboBox(array);
			comboBoxes[i].setMaximumSize(new Dimension(50, 25));
			JPanel mono = new JPanel();
			mono.add(comboBoxes[i]);
			insidePanel.add(mono);
			// Inizializza un JTextArea per inserire eventuali note e lo aggiunge al pannello interno
			textAreas[i] = new JTextArea(4, 20);
			textAreas[i].setBorder(BorderFactory.createLoweredBevelBorder());
			insidePanel.add(new JScrollPane(textAreas[i]));
		}
		// Aggiunge il pannello interno al pannello principale nella regione centrale
		mainPanel.add(insidePanel, BorderLayout.CENTER);
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
			public void actionPerformed(ActionEvent e)
			{
				finestra.dispose();
			}
		});
		buttonPanel.add(annullaButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Imposta l'ID della canzone a cui aggiungere emozioni.
	 *
	 * @param songId L'ID della canzone.
	 */
	public void setSongId(String songId)
	{
		this.songId = songId;
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
				// Flag per tenere traccia dello stato di inserimento delle emozioni
				boolean flag = false;
				// Ottieni l'array delle emozioni disponibili
				Emozione[] emozioni = Emozione.values();
				// Itera attraverso le emozioni
				for(int i = 0; i < emozioni.length; i++)
				{
					// Ottieni il punteggio selezionato dall'utente dal menu a discesa
					int score = comboBoxes[i].getSelectedIndex();
					// Se il punteggio è zero, salta questa emozione
					if(score == 0) continue;
					// Crea una nuova percezione con l'emozione corrente, il punteggio, l'ID della canzone e l'ID dell'utente
					Percezione nuovaPercezione = new Percezione(emozioni[i], score, songId, mainModel.getUserId());
					// Ottieni eventuali note inserite dall'utente
					String nota = textAreas[i].getText();
					// Se le note non sono vuote, aggiungile alla percezione
					if(!nota.isBlank())
						nuovaPercezione.aggiungiNote(nota);
					try
					{
						// Prova ad inserire la percezione nel modello principale
						if(mainModel.inserisciEmozioni(nuovaPercezione))
							flag = true; // Imposta il flag a true se almeno una percezione è stata inserita con successo
					} catch (IOException ex)
					{
						// Gestisci eventuali errori di connessione
						JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
				// Se almeno una percezione è stata inserita con successo, mostra un messaggio di successo e chiudi la finestra
				if(flag)
				{
					JOptionPane.showMessageDialog(mainPanel, "Emozione inserita con successo", "SUCCESSO", JOptionPane.INFORMATION_MESSAGE);
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
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
