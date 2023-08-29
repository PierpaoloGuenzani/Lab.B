package emotionalsongs;

import common.Canzone;

import javax.swing.*;
import java.awt.*;

public class MainView
{
	private final int DEFAULT_WIDTH = 600;
	private final int DEFAULT_HEIGHT = 400;
	JFrame finestra;
	private JPanel mainPanel, searchPanel, buttonPanel, listPanel;
	private JMenuBar menuBar;
	private JMenu playlistMenu, accountMenu, helpMenu;
	JMenuItem nuovaPlaylistItem, visualizzaPlaylistItem, nuovoAccountItem, helpItem;
	JTextField searchField, annoField;
	private ButtonGroup buttonGroup;
	JRadioButton titoloRadioButton, autoreRadioButton, autoreEAnnoRadioButton;
	JList<Canzone> canzoneJList;
	JButton accediButton, logOutButton, ricercaButton, visualizzaEmozioniButton, inserisciEmozioneButton;
	
	private MainModel mainModel;
	private MainController mainController;
	
	public MainView()
	{
		initializeGUI();
	}
	
	private void initializeGUI()
	{
		finestra = new JFrame("EMOTIONAL SONG");
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finestra.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		initializeMenuBar();
		
		mainPanel = new JPanel(new BorderLayout());
		
		initializeSearchBar();
		initializeList();
		initializeButton();
		setMonoBar();
		
		finestra.add(mainPanel);
		finestra.setVisible(true);
		System.out.println(searchField.getSize());
	}
	
	private void initializeMenuBar()
	{
		menuBar = new JMenuBar();
		
		playlistMenu = new JMenu("Playlist");
		playlistMenu.setVisible(false);
		playlistMenu.setMnemonic('p');
		
		nuovaPlaylistItem = new JMenuItem("Nuova Playlist");
		nuovaPlaylistItem.setMnemonic('N');
		playlistMenu.add(nuovaPlaylistItem);
		
		visualizzaPlaylistItem = new JMenuItem("Visualizza Playlist");
		visualizzaPlaylistItem.setMnemonic('V');
		playlistMenu.add(visualizzaPlaylistItem);
		
		menuBar.add(playlistMenu);
		
		accountMenu = new JMenu("Account");
		accountMenu.setVisible(false);
		accountMenu.setMnemonic('A');
		
		nuovoAccountItem = new JMenuItem("Nuovo Account");
		nuovoAccountItem.setMnemonic('u');
		accountMenu.add(nuovoAccountItem);
		
		menuBar.add(accountMenu);
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('h');
		
		helpItem = new JMenuItem("?");
		helpMenu.add(helpItem);
		
		menuBar.add(helpMenu);
		finestra.setJMenuBar(menuBar);
	}
	
	private void initializeSearchBar()
	{
		searchPanel = new JPanel();
		
		searchField = new JTextField();
		searchField.setMinimumSize(new Dimension(100, 30));
		searchField.setColumns(25);
		searchPanel.add(searchField);
		
		annoField = new JTextField(5);
		annoField.setMinimumSize(new Dimension(40, 30));
		annoField.setColumns(5);
		annoField.setVisible(false);
		searchPanel.add(annoField);
		
		JLabel label = new JLabel("Ricerca per:");
		searchPanel.add(label);
		
		buttonGroup = new ButtonGroup();
		
		titoloRadioButton = new JRadioButton("Titolo");
		titoloRadioButton.setMnemonic('t');
		titoloRadioButton.setSelected(true);
		buttonGroup.add(titoloRadioButton);
		searchPanel.add(titoloRadioButton);
		
		autoreRadioButton = new JRadioButton("Autore");
		autoreRadioButton.setMnemonic('u');
		buttonGroup.add(autoreRadioButton);
		searchPanel.add(autoreRadioButton);
		
		autoreEAnnoRadioButton = new JRadioButton("Autore e Anno");
		autoreEAnnoRadioButton.setMnemonic('n');
		buttonGroup.add(autoreEAnnoRadioButton);
		searchPanel.add(autoreEAnnoRadioButton);
		
		mainPanel.add(searchPanel, BorderLayout.NORTH);
	}
	
	private void initializeList()
	{
		listPanel = new JPanel();
		canzoneJList = new JList<>();
		listPanel.add(new JScrollPane(canzoneJList));
		
		mainPanel.add(listPanel, BorderLayout.CENTER);
	}
	
	private void initializeButton()
	{
		buttonPanel = new JPanel();
		
		accediButton = new JButton("Accedi");
		accediButton.setMnemonic('a');
		buttonPanel.add(accediButton);
		
		logOutButton = new JButton("Logout");
		logOutButton.setVisible(false);
		logOutButton.setMnemonic('l');
		buttonPanel.add(logOutButton);
		
		ricercaButton = new JButton("Ricerca");
		ricercaButton.setMnemonic('r');
		buttonPanel.add(ricercaButton);
		
		visualizzaEmozioniButton = new JButton("Visualizza Emozione");
		visualizzaEmozioniButton.setMnemonic('v');
		buttonPanel.add(visualizzaEmozioniButton);
		
		inserisciEmozioneButton = new JButton("Inserisci Emozione");
		inserisciEmozioneButton.setVisible(false);
		inserisciEmozioneButton.setMnemonic('i');
		buttonPanel.add(inserisciEmozioneButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void setLoggedIn()
	{
		accediButton.setVisible(false);
		logOutButton.setVisible(true);
		update(buttonPanel);
	}
	
	public void setLoggedOut()
	{
		logOutButton.setVisible(false);
		accediButton.setVisible(true);
		update(buttonPanel);
	}
	
	public void setDoubleBar()
	{
		searchField.setColumns(15);
		annoField.setColumns(5);
		annoField.setVisible(true);
		update(searchPanel);
	}
	
	public void setMonoBar()
	{
		searchField.setColumns(25);
		annoField.setVisible(false);
		annoField.setText("");
		update(searchPanel);
	}
	
	public void setJListModel()
	{
		if(mainModel == null) return;
		canzoneJList.setModel(mainModel.getCanzoneJlist());
		update(listPanel);
	}
	
	private void update(JPanel panel)
	{
		panel.validate();
		panel.repaint();
	}
	
	public void setMainModel(MainModel mainModel)
	{
		this.mainModel = mainModel;
	}
	
	public void setMainController(MainController mainController)
	{
		this.mainController = mainController;
		titoloRadioButton.addActionListener(mainController);
		autoreRadioButton.addActionListener(mainController);
		autoreEAnnoRadioButton.addActionListener(mainController);
		accediButton.addActionListener(mainController);
		logOutButton.addActionListener(mainController);
		ricercaButton.addActionListener(mainController);
		visualizzaEmozioniButton.addActionListener(mainController);
		inserisciEmozioneButton.addActionListener(mainController);
	}
}
