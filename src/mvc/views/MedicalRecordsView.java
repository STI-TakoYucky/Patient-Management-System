package mvc.views;

import mvc.controllers.GetPatients;
import mvc.views.constants.Constants;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import mvc.views.utility.SetFocusListenerToJTextFields;
import org.bson.Document;

public class MedicalRecordsView extends JFrame {
    Dashboard dashboard;
    JFrame frame = this;
    Document patientDocument;
    String patientID;

    public MedicalRecordsView() {
        System.out.println("Default Constructor");
    }

    public MedicalRecordsView(String patientID, Dashboard dashboard) {
        this.patientID = patientID;
        GetPatients getPatients = new GetPatients();
        this.patientDocument = getPatients.getPatientDataById(this.patientID);
        this.dashboard = dashboard;
        initComponents();
    }

    public MedicalRecordsView(Document patient, Dashboard dashboard) {
        this.patientDocument = patient;
        this.dashboard = dashboard;
        initComponents();
    }

    ImageIcon closeButtonIcon = new ImageIcon(getClass().getResource("/src/assets/images/x-icon.png"));
    Image image = closeButtonIcon.getImage();
    Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon resizedCloseButtonIcon = new ImageIcon(resizedImage);

    JLabel closeButton = new JLabel(resizedCloseButtonIcon);

    public void initComponents() {
        dashboard.setEnabled(false);
        dashboard.setFocusable(false);
        dashboard.setAlwaysOnTop(false);
        setAlwaysOnTop(true);
        repaint();
        revalidate();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 15);

        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        ImageIcon EPHedP;
        Image resizedEPHed;
        ImageIcon EPHedIcon;
        int wid = 45;
        int hei = 45;
        EPHedP = new ImageIcon("src/assets/images/icons8-medical-records-66.png");
        resizedEPHed = EPHedP.getImage().getScaledInstance(wid, hei, Image.SCALE_SMOOTH);
        EPHedIcon = new ImageIcon(resizedEPHed);

        // Header Panel
        JPanel mainHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainHeader.setBackground(Constants.secondary);
        mainHeader.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel Header = new JLabel("Medical Records", EPHedIcon, JLabel.LEFT);
        Header.setAlignmentX(Component.LEFT_ALIGNMENT);
        Header.setBorder(new EmptyBorder(0, 0, 0, 560));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                dashboard.setEnabled(true);
                dashboard.setFocusable(true);
                dashboard.setAlwaysOnTop(true);
            }
        });

        mainHeader.add(Header);
        mainHeader.add(closeButton);

        // Content Panel for Patient Data
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(new EmptyBorder(15, 25, 15, 25));
        mainContent.setBackground(Color.WHITE);

        // Populate Patient Data
        mainContent.add(createLabel("First Name", (String) patientDocument.get("First Name")));
        mainContent.add(createLabel("Last Name", patientDocument.getString("Last Name")));
        mainContent.add(createLabel("Middle Name", patientDocument.getString("Middle Name")));
        Date birthDate = patientDocument.getDate("Birthdate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String formattedBirthDate = dateFormat.format(birthDate);

        // Add the formatted birthdate to the label
        mainContent.add(createLabel("Admission Date", patientDocument.getDate("Admission Date").toString()));
        mainContent.add(createLabel("Blood Type", patientDocument.getString("Blood Type")));
        mainContent.add(createLabel("Birthdate", formattedBirthDate));
        mainContent.add(createLabel("Phone Number", patientDocument.get("Phone Number").toString()));
        mainContent.add(createLabel("Email Address", patientDocument.getString("Email")));
        mainContent.add(createLabel("Emergency Contact", patientDocument.get("Emergency Contact Number").toString()));
        mainContent.add(createLabel("Street Address", patientDocument.getString("Street Name")));
        mainContent.add(createLabel("City", patientDocument.getString("City")));
        mainContent.add(createLabel("Region", patientDocument.getString("Region")));
        mainContent.add(createLabel("Municipality", patientDocument.getString("Municipality")));
        mainContent.add(createLabel("Postal Code", patientDocument.get("Postal Code").toString()));
        mainContent.add(createLabel("Nationality", patientDocument.getString("Nationality")));
        mainContent.add(createLabel("Civil Status", patientDocument.getString("Civil Status")));
        mainContent.add(createLabel("Sex", patientDocument.getString("Sex")));

        // Extract Symptoms, Allergies, and Medications from patient document
        List<String> symptoms = patientDocument.getList("Symptoms", String.class);
        List<String> allergies = patientDocument.getList("Allergies", String.class);
        List<String> medications = patientDocument.getList("Medications", String.class);

        // Add Symptoms, Allergies, and Medications to the panel
        mainContent.add(createListLabel("Symptoms", symptoms));
        mainContent.add(createListLabel("Allergies", allergies));
        mainContent.add(createListLabel("Medications", medications));

        // Wrap Content in JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add Components to Main Panel
        mainPanel.add(mainHeader, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Set Up Frame
        setContentPane(mainPanel);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new SetFocusListenerToJTextFields(this);
        Header.setFont(Constants.HEADING_FONT);
    }

    /**
     * Utility method to create a field-value JLabel pair.
     */
    private JPanel createLabel(String fieldName, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(8, 15, 8, 15));
        panel.setBackground(Color.WHITE);

        JLabel fieldLabel = new JLabel(fieldName + ": ");
        fieldLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldLabel.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(Constants.DEFAULT_FONT);
        valueLabel.setForeground(Color.BLACK);

        panel.add(fieldLabel, BorderLayout.WEST);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    // Create a method to handle the array lists for Symptoms, Allergies, and Medications
    private JPanel createListLabel(String fieldName, List<String> items) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(8, 15, 8, 15));
        panel.setBackground(Color.WHITE);

        JLabel fieldLabel = new JLabel(fieldName + ": ");
        fieldLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldLabel.setForeground(Color.DARK_GRAY);

        // Format the items list
        StringBuilder valueText = new StringBuilder();
        if (items != null && !items.isEmpty()) {
            for (String item : items) {
                valueText.append(item).append("<br>");
            }
        } else {
            valueText.append("No data available.");
        }

        JLabel valueLabel = new JLabel("<html>" + valueText.toString() + "</html>");
        valueLabel.setFont(Constants.DEFAULT_FONT);
        valueLabel.setForeground(Color.BLACK);

        panel.add(fieldLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    // Helper Method to Add Fields
    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    public void setJTextFieldPadding(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                component.setFont(new Font("Arial", Font.PLAIN, 16));
                Border borderColor = BorderFactory.createLineBorder(Color.gray, 1, true);
                ((JTextField) component).setBorder(BorderFactory.createCompoundBorder(
                        borderColor, new EmptyBorder(2, 10, 2, 10) // Inner padding
                ));
            } else if (component instanceof Container) {
                setJTextFieldPadding((Container) component);
            }
        }
    }
}
