package emotionalsongs;

import emotionalsongs.logic.EmotionalSongsService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
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
public class ServerGUIs
{
    private JFrame frame;
    private JPanel fieldPanel, buttonPanel, urlPanel, userPanel, passwordPanel;
    private JButton conferma, annulla;
    private JTextField urlField, userField, passwordField;
    private JLabel urlLabel, userLabel, passwordLabel;

    public ServerGUIs()
    {
        frame = new JFrame();
        frame.setTitle("ServerGUI");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        initializeFieldPanel();
        initializeButtonPanel();
        
        frame.pack();
        frame.setVisible(true);
    }
    
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

