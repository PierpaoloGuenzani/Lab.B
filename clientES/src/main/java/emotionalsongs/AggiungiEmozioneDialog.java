package emotionalsongs;

import common.Emozione;
import common.Percezione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AggiungiEmozioneDialog
{
	private String songId;
	private MainModel mainModel;
	
	private JDialog finestra;
	private JComboBox[] comboBoxes;
	private JTextArea[] textAreas;
	private JButton confermaButton, annullaButton;
	private JPanel mainPanel, insidePanel, buttonPanel;
	
	public AggiungiEmozioneDialog()
	{
		finestra = new JDialog();
		finestra.setTitle("NUOVA EMOZIONE");
		finestra.setModal(true);
		finestra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeScrollPanel();
		initializeButton();
		
		finestra.add(mainPanel);
		finestra.pack();
		finestra.setVisible(true);
	}
	
	public AggiungiEmozioneDialog(MainModel mainModel, String songId)
	{
		this();
		this.mainModel = mainModel;
		this.songId = songId;
	}
	
	private void initializeScrollPanel()
	{
		insidePanel = new JPanel(new GridLayout(0,3));
		insidePanel.setVisible(true);
		insidePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		String[] array = {"0", "1", "2", "3", "4", "5"};
		Emozione[] emozioni = Emozione.values();
		comboBoxes = new JComboBox[emozioni.length];
		textAreas = new JTextArea[emozioni.length];
		
		for(int i = 0; i < emozioni.length; i++)
		{
			
			JLabel label = new JLabel(emozioni[i].name());
			insidePanel.add(label);
			comboBoxes[i] = new JComboBox(array);
			comboBoxes[i].setMaximumSize(new Dimension(50, 25));
			JPanel mono = new JPanel();
			mono.add(comboBoxes[i]);
			insidePanel.add(mono);
			textAreas[i] = new JTextArea(4, 20);
			textAreas[i].setBorder(BorderFactory.createLoweredBevelBorder());
			insidePanel.add(new JScrollPane(textAreas[i]));
		}
		
		mainPanel.add(insidePanel, BorderLayout.CENTER);
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
				Emozione[] emozioni = Emozione.values();
				for(int i = 0; i < emozioni.length; i++)
				{
					int score = comboBoxes[i].getSelectedIndex();
					if(score == 0) return;
					Percezione nuovaPercezione = new Percezione(emozioni[i], score, songId, mainModel.getUserId());
					String nota = textAreas[i].getText();
					if(!nota.isBlank())
						nuovaPercezione.aggiungiNote(nota);
					try
					{
						mainModel.inserisciEmozioni(nuovaPercezione);
						
					} catch (IOException ex)
					{
						JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
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
	
}