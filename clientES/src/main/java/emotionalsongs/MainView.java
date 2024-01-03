package emotionalsongs;

import common.Canzone;

import javax.swing.*;
import java.awt.*;

/**
 * La classe MainView rappresenta l'interfaccia utente grafica (GUI) dell'applicazione "EmotionalSongs".
 * Gestisce la creazione e l'aggiornamento degli elementi dell'interfaccia utente, inclusi pulsanti, campi di ricerca e liste di canzoni.
 * Gestisce anche la visibilità dei menu e dei pulsanti in base allo stato dell'utente (loggato o non loggato) e alla modalità di ricerca selezionata.
 *
 * La classe MainView è parte integrante del pattern Model-View-Controller (MVC), insieme a MainController e MainModel,
 * per garantire una chiara separazione tra la presentazione (Vista), la gestione delle interazioni utente (Controller) e i dati dell'applicazione (Modello).
 *
 * Questa classe è connessa al Controller (MainController) per gestire gli eventi generati dall'utente, come clic sui pulsanti e selezione delle opzioni di ricerca.
 * Comunica inoltre con il Modello (MainModel) per visualizzare e aggiornare i dati relativi alle canzoni.
 */
public class MainView
{
	/**
	 * La larghezza predefinita della finestra dell'interfaccia utente grafica (GUI)
	 */
	private final int DEFAULT_WIDTH = 600;
	/**
	 * L'altezza predefinita della finestra dell'interfaccia utente grafica (GUI)
	 */
	private final int DEFAULT_HEIGHT = 400;

	// Componenti per la GUI
	static JFrame finestra;
	private JPanel mainPanel, searchPanel, buttonPanel, listPanel;
	private JMenuBar menuBar;
	private JMenu playlistMenu, accountMenu, helpMenu;
	JMenuItem nuovaPlaylistItem, visualizzaPlaylistItem, nuovoAccountItem, helpItem;
	JTextField searchField, annoField;
	private ButtonGroup buttonGroup;
	JRadioButton titoloRadioButton, autoreRadioButton, autoreEAnnoRadioButton;
	JList<Canzone> canzoneJList;
	JButton accediButton, logOutButton, ricercaButton, visualizzaEmozioniButton, inserisciEmozioneButton;

	/**
	 * Costruttore della classe MainView.
	 * Inizializza l'interfaccia utente grafica (GUI) dell'applicazione.
	 * Questo costruttore crea la finestra principale, imposta il menu, la barra degli strumenti di ricerca, la lista delle canzoni e i pulsanti principali.
	 * Viene chiamato quando si crea un'istanza di MainView e avvia il processo di inizializzazione dell'interfaccia utente.
	 */
	public MainView()
	{
		initializeGUI();
	}

	/**
	 * Inizializza l'interfaccia utente grafica (GUI) dell'applicazione "EmotionalSongs".
	 * Questo metodo crea la finestra principale, imposta il menu, la barra degli strumenti di ricerca, la lista delle canzoni e i pulsanti principali.
	 */
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

	/**
	 * Inizializza il menu della finestra, inclusi i menu "Playlist", "Account" e "Help", con le relative voci di menu.
	 */
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
		//accountMenu.setVisible(false);
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

	/**
	 * Inizializza la barra di ricerca dell'interfaccia utente, che include campi di ricerca per titolo e autore, pulsanti radio per la modalità di ricerca
	 * e un campo per l'anno (nella modalità "Autore e Anno").
	 */
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

	/**
	 * Inizializza la lista delle canzoni nell'interfaccia utente, fornendo uno scroll pane per la visualizzazione delle canzoni.
	 */
	private void initializeList()
	{
		listPanel = new JPanel(new BorderLayout());
		canzoneJList = new JList<>();
		JScrollPane scrollPane = new JScrollPane(canzoneJList);
		listPanel.add(scrollPane, BorderLayout.CENTER);
		
		mainPanel.add(listPanel, BorderLayout.CENTER);
	}

	/**
	 * Inizializza i pulsanti dell'interfaccia utente, inclusi i pulsanti "Accedi", "Logout", "Ricerca", "Visualizza Emozione" e "Inserisci Emozione".
	 */
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

	/**
	 * Imposta l'interfaccia utente come "loggata", visualizzando il menu "Playlist" e i pulsanti "Inserisci Emozione" e "Logout".
	 * Nasconde il pulsante "Accedi".
	 */
	public void setLoggedIn()
	{
		playlistMenu.setVisible(true);
		
		inserisciEmozioneButton.setVisible(true);
		accediButton.setVisible(false);
		logOutButton.setVisible(true);
		update(buttonPanel);
	}

	/**
	 * Imposta l'interfaccia utente come "non loggata", nascondendo il menu "Playlist" e i pulsanti "Inserisci Emozione" e "Logout".
	 * Visualizza il pulsante "Accedi".
	 */
	public void setLoggedOut()
	{
		playlistMenu.setVisible(false);
		
		inserisciEmozioneButton.setVisible(false);
		logOutButton.setVisible(false);
		accediButton.setVisible(true);
		update(buttonPanel);
	}

	/**
	 * Imposta la barra di ricerca in modalità "Ricerca per Autore e Anno" con campi di ricerca separati per autore e anno.
	 */
	public void setDoubleBar()
	{
		searchField.setColumns(15);
		annoField.setColumns(5);
		annoField.setVisible(true);
		update(searchPanel);
	}

	/**
	 * Imposta la barra di ricerca in modalità "Ricerca per Titolo" con un campo di ricerca più ampio.
	 */
	public void setMonoBar()
	{
		searchField.setColumns(25);
		annoField.setVisible(false);
		annoField.setText("");
		update(searchPanel);
	}

	/**
	 * Imposta il modello della lista delle canzoni con un nuovo modello fornito come argomento.
	 *
	 * @param model Il modello da impostare sulla lista delle canzoni.
	 */
	public void setJListModel(ListModel model)
	{
		canzoneJList.setModel(model);
		update(listPanel);
	}

	/**
	 * Aggiorna un pannello dell'interfaccia utente specificato, validandolo e ridisegnandolo.
	 *
	 * @param panel Il pannello da aggiornare.
	 */
	private void update(JPanel panel)
	{
		panel.validate();
		panel.repaint();
	}

	/**
	 * Imposta il controller principale (MainController) per gestire gli eventi generati dall'interfaccia utente.
	 *
	 * @param mainController Il controller principale da associare agli elementi dell'interfaccia utente.
	 */
	public void setMainController(MainController mainController)
	{
		titoloRadioButton.addActionListener(mainController);
		autoreRadioButton.addActionListener(mainController);
		autoreEAnnoRadioButton.addActionListener(mainController);
		accediButton.addActionListener(mainController);
		logOutButton.addActionListener(mainController);
		ricercaButton.addActionListener(mainController);
		visualizzaEmozioniButton.addActionListener(mainController);
		inserisciEmozioneButton.addActionListener(mainController);
		nuovoAccountItem.addActionListener(mainController);
		nuovaPlaylistItem.addActionListener(mainController);
		visualizzaPlaylistItem.addActionListener(mainController);
	}
}
