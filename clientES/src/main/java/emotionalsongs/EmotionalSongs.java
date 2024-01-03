package emotionalsongs;

import javax.swing.*;
import java.rmi.RemoteException;
/**
 * La classe EmotionalSongs rappresenta il punto d'ingresso dell'applicazione "EmotionalSongs" e segue il pattern architetturale Model-View-Controller (MVC) per gestire la sua struttura.
 *
 * Il pattern MVC suddivide l'applicazione in tre componenti principali:
 * - **Il Modello (Model):** Rappresenta i dati e la logica dell'applicazione, compresi dettagli sulle canzoni, playlist e altre informazioni relative alla musica.
 * - **La Vista (View):** Gestisce l'interfaccia utente grafica, compresi pulsanti, finestre e componenti per visualizzare e interagire con i dati musicali.
 * - **Il Controller (Controller):** Gestisce le interazioni dell'utente con l'interfaccia utente e coordina il flusso di dati tra il Modello e la Vista, gestendo ad esempio l'apertura di finestre di dialogo.
 *
 * Nell'implementazione di questa classe, la Vista (`MainView`) è creata e collegata al Controller (`MainController`).
 * Il Controller, a sua volta, è collegato al Modello (`MainModel`) per coordinare le operazioni relative alla musica e alla gestione delle interazioni utente.
 *
 * Questa separazione delle responsabilità secondo il pattern MVC aiuta a mantenere il codice dell'applicazione ben strutturato, facilitando la manutenzione e lo sviluppo futuro.
 */
public class EmotionalSongs
{
	/**
	 * Variabile statica per memorizzare l'indirizzo IP del server.
	 */
	static String IPAddress;

	/**
	 * Costruttore della classe EmotionalSongs. Inizializza gli oggetti principali
	 * dell'applicazione e gestisce eventuali eccezioni legate alla connessione al server.
	 */
	public EmotionalSongs()
	{
		// Creazione della vista principale
		MainView mainView = new MainView();
		// Creazione del controller principale
		MainController mainController = new MainController();

		// Collegamento tra vista e controller
		mainController.setMainView(mainView);
		mainView.setMainController(mainController);

		// Apertura della finestra per l'inserimento dell'indirizzo IP del server
		new ServerInternetProtocolAddressDialog().draw();
		try
		{
			// Creazione del modello principale e impostazione dell'indirizzo IP
			MainModel mainModel = new MainModel();
			mainModel.setStub(IPAddress);

			// Collegamento tra controller e modello
			mainController.setMainModel(mainModel);
			
		} catch (RemoteException e)
		{
			// Gestione dell'eccezione in caso di errore di connessione
			JOptionPane.showMessageDialog(MainView.finestra, "Impossibile connettersi al server", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Metodo principale dell'applicazione. Crea un'istanza di EmotionalSongs per avviare il programma.
	 *
	 * @param args Gli argomenti della riga di comando (non utilizzati in questo caso).
	 */
	public static void main(String[] args)
	{
		new EmotionalSongs();
	}
}
