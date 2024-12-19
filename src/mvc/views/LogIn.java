package mvc.views;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import mvc.views.utility.SetFocusListenerToJTextFields;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame implements ActionListener {
    private static JButton logInbtn, registerBtn;
    JTextField text, LogPassword;

    public LogIn() {
        LogInComponents();
    }

    public void LogInComponents() {
        JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logInbtn = new JButton("Log In");
        logInbtn.setSize(20, 10);
        logInbtn.addActionListener(this);

        registerBtn = new JButton("Register");
        registerBtn.setSize(20, 10);
        registerBtn.addActionListener(this);

        JPanel Logheader = new JPanel();
        JLabel headerName = new JLabel("Log In");
        Logheader.add(headerName);
        Logheader.setLayout(new FlowLayout(FlowLayout.CENTER));
        Logheader.setBorder(new EmptyBorder(30, 0, 30, 0));

        Logheader.setBackground(Constants.primary);

        text = new JTextField("Username", 20);
        JLabel userLabel = new JLabel("Enter username: ");
        LogPassword = new JTextField("Password", 20);
        JLabel PasswordLabel = new JLabel("Enter password: ");
        logPanel.setBackground(Color.WHITE);
        logPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 65));

        logPanel.add(userLabel);
        logPanel.add(text);
        logPanel.add(PasswordLabel);
        logPanel.add(LogPassword);
        logPanel.add(logInbtn);
        logPanel.add(registerBtn);
        add(logPanel);

        add(Logheader, BorderLayout.NORTH);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        headerName.setFont(Constants.HEADING_FONT);
        new SetFocusListenerToJTextFields(this);
        setSize(590, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    String Role;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInbtn) {
            String username = text.getText();
            String password = LogPassword.getText();

            if (authenticate(username, password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new Dashboard(Role);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == registerBtn) {
            this.setEnabled(false);
            this.dispose();
            new Register();
        }
    }

    private boolean authenticate(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");
            MongoCollection<Document> users = database.getCollection("medical staff");

            Document user = users.find(Filters.eq("Username", username)).first();

            if (user != null && user.getString("Password").equals(password)) {
                Role = user.getString("Role");
                return true; // Authentication successful
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return false; // Authentication failed
    }
}
