package emotionalsongs;

import emotionalsongs.logic.EmotionalSongsService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * La classe fornisce una finestra grafica che consente all'utente di inserire le informazioni necessarie per la connessione al server del database.
 * Include campi di testo per l'URL del database, il nome utente e la password. L'utente può confermare la connessione o annullare l'operazione tramite appositi pulsanti.
 * In caso di errori di connessione o di credenziali non valide, viene visualizzato un messaggio di errore e l'applicazione viene chiusa.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 *
 * @see EmotionalSongsService
 */
public class ServerGUI
{
    private JFrame frame;
    private JPanel fieldPanel, buttonPanel, urlPanel, userPanel, passwordPanel;
    private JButton conferma, annulla;
    private JTextField urlField, userField, passwordField;
    private JLabel urlLabel, userLabel, passwordLabel;

    /**
     * Costruisce una nuova istanza di ServerGUI.
     * Inizializza la finestra e i componenti grafici necessari per la configurazione dell'accesso al server del database.
     * La finestra viene visualizzata al centro dello schermo.
     */
    public ServerGUI()
    {
        frame = new JFrame();
        frame.setTitle("CONNESSIONE AL SERVER");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        initializeFieldPanel();
        initializeButtonPanel();
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Inizializza il pannello dei campi (URL, nome utente, password) della finestra.
     * Utilizza un layout di tipo BoxLayout per organizzare i campi in verticale.
     */
    private void initializeFieldPanel()
    {
        fieldPanel = new JPanel(new GridBagLayout());
        
        urlPanel = new JPanel();
        urlField = new JTextField(20);
        urlLabel = new JLabel("url:");
        urlLabel.setLabelFor(urlField);
        urlPanel.add(urlLabel);
        urlPanel.add(urlField);;
        
        userPanel = new JPanel();
        userField = new JTextField(20);
        userLabel = new JLabel("username:");
        userLabel.setLabelFor(userField);
        userPanel.add(userLabel);
        userPanel.add(userField);
        
        passwordPanel = new JPanel();
        passwordField = new JTextField(20);
        passwordLabel = new JLabel("password:");
        passwordLabel.setLabelFor(passwordField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        fieldPanel.add(urlPanel);
        fieldPanel.add(userPanel);
        fieldPanel.add(passwordPanel);
        
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        frame.add(fieldPanel);
    }

    /**
     * Inizializza il pannello dei pulsanti (Connetti, Annulla) della finestra.
     * Aggiunge i listener ai pulsanti per gestire le azioni dell'utente.
     */
    private void initializeButtonPanel()
    {
        buttonPanel = new JPanel();
        
        conferma = new JButton("Connetti");
        conferma.addActionListener(e ->
        {
            try
            {
                EmotionalSongsService.getInstance().setDBs(
                        urlField.getText().isBlank()? null: urlField.getText(),
                        userField.getText().isBlank()? null: urlField.getText(),
                        passwordField.getText().isBlank()? null: urlField.getText()
                );
            } catch (SQLException | IOException ex )
            {
                JOptionPane.showMessageDialog(frame, "Impossibile connettersi al DB", "Connection Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            finally
            {
                frame.dispose();
            }
        });
        buttonPanel.add(conferma);
        
        annulla = new JButton("Annulla");
        annulla.addActionListener(e -> frame.dispose());
        buttonPanel.add(annulla);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }
}

