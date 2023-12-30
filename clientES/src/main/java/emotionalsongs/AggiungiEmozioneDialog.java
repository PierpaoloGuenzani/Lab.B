package emotionalsongs;

import common.Emozione;
import common.Percezione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

public class AggiungiEmozioneDialog implements MyDialog
{
	private String songId;
	
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
//		finestra.pack();
//		finestra.setVisible(true);
//		finestra.setLocationRelativeTo(MainView.finestra);
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
	
	public void setSongId(String songId)
	{
		this.songId = songId;
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
					if(!mainModel.controllaCanzonePersona(songId))
					{
						//TODO callback
						SelectPlaylistDialog selectPlaylistDialog = new SelectPlaylistDialog(SelectPlaylistDialog.SELEZIONA_PLAYLIST);
						selectPlaylistDialog.setIdCanzone(songId);
						selectPlaylistDialog.setMainModel(mainModel);
						selectPlaylistDialog.draw();
					}
				} catch (RemoteException ex)
				{
					ex.printStackTrace();
				}
				
				boolean flag = false;
				Emozione[] emozioni = Emozione.values();
				for(int i = 0; i < emozioni.length; i++)
				{
					int score = comboBoxes[i].getSelectedIndex();
					if(score == 0) continue;
					Percezione nuovaPercezione = new Percezione(emozioni[i], score, songId, mainModel.getUserId());
					String nota = textAreas[i].getText();
					if(!nota.isBlank())
						nuovaPercezione.aggiungiNote(nota);
					try
					{
						if(mainModel.inserisciEmozioni(nuovaPercezione))
							flag = true;
					} catch (IOException ex)
					{
						JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(flag)
				{
					JOptionPane.showMessageDialog(mainPanel, "Emozione inserita con successo", "SUCCESSO", JOptionPane.INFORMATION_MESSAGE);
					finestra.dispose();
				}
			}
		});
	}
	
	@Override
	public void draw()
	{
		finestra.pack();
		finestra.setLocationRelativeTo(MainView.finestra);
		finestra.setVisible(true);
	}
}
