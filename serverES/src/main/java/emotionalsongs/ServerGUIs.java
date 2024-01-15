package emotionalsongs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Rappresenta l'interfaccia grafica per la configurazione dell'accesso al server del database.
 * Include campi di testo per l'inserimento delle informazioni richieste (URL, nome utente e password) e pulsanti per confermare l'accesso o annullare l'operazione.
 * In caso di credenziali non valide, l'utente ha la possibilità di riprovare o chiudere l'applicazione.
 * Gestisce eventuali errori di connessione.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 *
 */
public class ServerGUIs {
    private JFrame frame;
    private JPanel interno, urlPanel, userPanel, passwordPanel, buttonPanel;
    private JButton conferma, annulla;
    private JTextField urlField, userField;
    private JPasswordField passwordField;
    private JLabel urlLabel, userLabel, passwordLabel;

    public ServerGUIs() {
        // Crea la finestra principale in cui vengono visualizzati gli elementi dell'interfaccia utente
        frame = new JFrame();
        frame.setTitle("ServerGUI");
        frame.setSize(400, 300);
        // Garantisce che l'applicazione termini quando l'utente chiude la finestra
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Crea un nuovo pannello principale interno
        interno = new JPanel();
        interno.setVisible(true);

        // Crea pannelli specifici per URL, utente e password utilizzando il metodo createPanel
        urlPanel = createPanel("Server url:");
        userPanel = createPanel("User:");
        passwordPanel = createPanel("Password:");

        // Crea un nuovo pannello per i pulsanti
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

        // Crea un nuovo pulsante di conferma con il testo "Conferma"
        conferma = new JButton("Conferma");
        // Aggiunge un ascoltatore di azione al pulsante di conferma
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupera le credenziali inserite dall'utente
                String url = urlField.getText();
                String user = userField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Verifica le credenziali con quelle fisse richieste
                if (url.equals("jdbc:postgresql:dbES") && user.equals("postgres") && password.equals("admin")) {
                    try {
                        // Ottieni una connessione utilizzando ConnectionFactory
                        Connection connection = ConnectionFactory.getConnection();
                        JOptionPane.showMessageDialog(frame, "Connessione riuscita!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Errore durante la connessione al database:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        // todo: NON MI PIACE, NON DOVREI AVERE UNA CHIUSURA PIU' CONTROLLATA?
                        System.exit(0);
                    }
                } else {
                    // Se le credenziali non corrispondono, mostra un messaggio di errore con opzioni "Riprova" e "Chiudi"
                    //JOptionPane.showMessageDialog(frame, "Credenziali non valide!", "Errore", JOptionPane.ERROR_MESSAGE);
                    Object[] options = {"Riprova", "Chiudi"};
                    int choice = JOptionPane.showOptionDialog(frame,
                            "Credenziali non valide! Vuoi riprovare o chiudere l'applicazione?",
                            "Errore",
                            JOptionPane.YES_NO_OPTION,  //  tipo di opzioni che verranno visualizzate nella finestra di dialogo
                            JOptionPane.ERROR_MESSAGE,  // tipo di icona da visualizzare
                            null,                       // icona personalizzata da utilizzare (quella di default qui)
                            options,                    // array di oggetti che rappresentano le opzioni da visualizzare nella finestra di dialogo
                            options[0]);                // operazione preselezionata -> "Riprova"
                    // Gestisci la scelta dell'utente
                    if (choice == JOptionPane.YES_OPTION) {
                        // Se l'utente sceglie "Riprova", pulisci i campi di testo
                        urlField.setText("");
                        userField.setText("");
                        passwordField.setText("");
                    } else {
                        // Se l'utente sceglie "Chiudi", chiudi l'applicazione
                        // todo: NON MI PIACE, NON DOVREI AVERE UNA CHIUSURA PIU' CONTROLLATA?
                        System.exit(0);
                    }
                }
            }
        });
        // Aggiunge il pulsante di conferma al pannello dei pulsanti
        buttonPanel.add(conferma);

        // Crea un nuovo pulsante di cancellazione con il testo "Annulla"
        annulla = new JButton("Annulla");
        // Aggiunge un ascoltatore di azione al pulsante Annulla
        annulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pulisci i campi di testo
                urlField.setText("");
                userField.setText("");
                passwordField.setText("");
            }
        });
        // Aggiunge il pulsante Annulla al pannello dei pulsanti
        buttonPanel.add(annulla);

        // Aggiunge i pannelli contenenti i campi URL, utente, password e i pulsanti al pannello principale interno
        interno.add(urlPanel);
        interno.add(userPanel);
        interno.add(passwordPanel);
        interno.add(buttonPanel);

        // Aggiunge il pannello principale interno al frame
        frame.add(interno);
        // Rende il frame visibile
        frame.setVisible(true);
    }

    /**
     * Metodo per creare e configurare un pannello contenente una etichetta e un campo di testo.
     * La configurazione varia in base alla label specificata.
     *
     * @param label Etichetta del pannello che influenzerà la sua configurazione.
     * @return Pannello configurato con etichetta e campo di testo associato.
     */
    private JPanel createPanel(String label) {
        // Crea un nuovo pannello Swing
        JPanel panel = new JPanel();
        // Configura il layout del pannello come un layout lineare orizzontale
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        // Imposta un bordo nero intorno al pannello
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Crea una nuova etichetta con il testo specificato
        JLabel titleLabel = new JLabel(label);
        // Allinea l'etichetta a sinistra all'interno del pannello
        titleLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        // Aggiungi l'etichetta al pannello
        panel.add(titleLabel);

        // Verifica il testo dell'etichetta per determinare il tipo di componente da aggiungere
        // Se l'etichetta è "Password:", aggiungi un campo di password al pannello
        // Altrimenti (per "Server url:" e "User:"), aggiungi un campo di testo normale al pannello
        if (label.equals("Password:")) {
            passwordField = new JPasswordField(20);
            passwordField.setAlignmentX(JPasswordField.RIGHT_ALIGNMENT);
            panel.add(passwordField);
        } else {
            // Crea un nuovo campo di testo
            JTextField textField = new JTextField(20);
            // Allinea il campo di testo a destra all'interno del pannello
            textField.setAlignmentX(JTextField.RIGHT_ALIGNMENT);

            // Assegna i campi di testo alle variabili appropriate in base all'etichetta
            if (label.equals("Server url:")) {
                urlField = textField;
            } else if (label.equals("User:")) {
                userField = textField;
            }

            // Aggiungi il campo di testo al pannello
            panel.add(textField);
        }

        // Restituisci il pannello configurato
        return panel;
    }
}

