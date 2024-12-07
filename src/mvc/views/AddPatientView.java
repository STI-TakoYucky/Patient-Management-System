package mvc.views;
import mvc.views.constants.Constants;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import mvc.views.utility.SetDefaultFont;

public class AddPatientView extends JFrame {
    public AddPatientView() {
        initComponents();
    }

    public JTextField patientNameFieldFN = new JTextField("First Name",18);
    public JTextField patientNameFieldLN = new JTextField("Last Name",13);
    public JTextField patientNameFieldMN = new JTextField("Middle Name",13);

    JPanel symptomsContainer = new JPanel();
    GridBagConstraints symptomsgbc = new GridBagConstraints();
    ArrayList<String> symptomsArray = new ArrayList<>();

    JPanel medicationContainer = new JPanel();
    GridBagConstraints medicationgbc = new GridBagConstraints();
    ArrayList<String> medicationArray = new ArrayList<>();

    JPanel allergiesContainer = new JPanel();
    GridBagConstraints allergiesgbc = new GridBagConstraints();
    ArrayList<String> allergiesArray = new ArrayList<>();

    public void initComponents() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 2, 7);

        // Main Panel with BoxLayout for Vertical Stacking
        JPanel mainPanel = new JPanel();
        JPanel mainContent = new JPanel();
        JPanel mainHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.setLayout(new BorderLayout());
        mainContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainHeader.setBackground(Constants.secondary);
        mainHeader.setBorder(new EmptyBorder(15, 25, 15, 25));
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);

        // Header Section
        JLabel addPatientHeader = new JLabel("Add Patient");
        addPatientHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Header aligned left
        headerPanel.add(addPatientHeader);
        headerPanel.setBackground(Constants.secondary);


        // Patient Name Section
        JLabel patientNameLabel = new JLabel("Patient Name");
        JPanel patientNameLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        patientNameLabelPanel.add(patientNameLabel);

        JPanel patientTextFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        patientTextFieldPanel.add(patientNameFieldFN);
        patientTextFieldPanel.add(patientNameFieldMN);
        patientTextFieldPanel.add(patientNameFieldLN);

        // Date of Birth Section
        GridBagConstraints dategbc = new GridBagConstraints();
        dategbc.fill = GridBagConstraints.HORIZONTAL;
        dategbc.anchor = GridBagConstraints.WEST;

        JLabel dateFieldLabel = new JLabel("Date of Birth");
        JDateChooser birthDate = new JDateChooser();
        JTextField dateField = (JTextField) birthDate.getDateEditor().getUiComponent();

        dateField.setMaximumSize(new Dimension(220, 20));

        birthDate.setDate(new Date());

        birthDate.getCalendarButton().setPreferredSize(new Dimension(30, 20));
        birthDate.setPreferredSize(new Dimension(220, 30));

        JPanel datePanel = new JPanel(new GridBagLayout());
        datePanel.setPreferredSize(new Dimension(220, 70));
        JPanel datePanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanelWrapper.add(datePanel);

        dategbc.gridx = 0;
        dategbc.gridy = 0;
        datePanel.add(dateFieldLabel, dategbc);
        dategbc.gridx = 0;
        dategbc.gridy = 1;
        datePanel.add(birthDate, dategbc);

        // Gender Section
        JLabel genderFieldLabel = new JLabel("Sex");
        JRadioButton option1 = new JRadioButton("Male");
        JRadioButton option2 = new JRadioButton("Female");

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(genderFieldLabel);
        genderPanel.add(option1);
        genderPanel.add(option2);

        // Contact Information Section
        JLabel contactInfoLabel = new JLabel("Contact Information");
        JTextField phoneNumberField = new JTextField("Phone Number", 15);
        JTextField emailAddressField = new JTextField("Email",20);
        JTextField emergencyContactNumber = new JTextField("Emergency Contact No.",15);

        JPanel contactInfoPanel = new JPanel(new GridBagLayout());
        JPanel contactInfoPanelWrapper = new JPanel();
        contactInfoPanelWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
        contactInfoPanelWrapper.add(contactInfoPanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contactInfoPanel.add(contactInfoLabel, gbc);
        gbc.gridy = 1;
        contactInfoPanel.add(phoneNumberField, gbc);
        gbc.gridx = 1;
        contactInfoPanel.add(emailAddressField, gbc);
        gbc.gridx = 2;
        contactInfoPanel.add(emergencyContactNumber, gbc);

        // Address Section
        JLabel addressLabel = new JLabel("Address");
        JTextField streetAddressField = new JTextField("Street Name",20);
        JTextField cityField = new JTextField("City",20);
        JTextField regionField = new JTextField("Region",20);
        JTextField postalCodeField = new JTextField("Postal Code",5);

        JPanel addressPanel = new JPanel(new GridBagLayout());
        JPanel addressPanelWrapper = new JPanel();
        addressPanelWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
        addressPanelWrapper.add(addressPanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        addressPanel.add(addressLabel, gbc);
        gbc.gridy = 1;
        addressPanel.add(streetAddressField, gbc);
        gbc.gridx = 1;
        addressPanel.add(regionField, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        addressPanel.add(cityField, gbc);
        gbc.gridx = 1;
        addressPanel.add(postalCodeField, gbc);

        //Nationality Section
        JPanel nationalityWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel nationalityPanel = new JPanel(new GridBagLayout());
        JLabel nationalityLabel = new JLabel("Nationality");
        JTextField nationalityTextField = new JTextField("Nationality", 30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        nationalityPanel.add(nationalityLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        nationalityPanel.add(nationalityTextField, gbc);
        nationalityWrapper.add(nationalityPanel);

        JPanel civilStatusWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel civilStatusPanel = new JPanel(new GridBagLayout());
        JLabel civilStatusLabel = new JLabel("Civil Status");
        JTextField civilStatusField = new JTextField("Civil Status", 30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        civilStatusPanel.add(civilStatusLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        civilStatusPanel.add(civilStatusField, gbc);
        civilStatusWrapper.add(civilStatusPanel);

        // Admission date
        JLabel admissionDateLabel = new JLabel("Enter Admission Date");
        JDateChooser admissionDate = new JDateChooser();
        JTextField admissionDateField = (JTextField) admissionDate.getDateEditor().getUiComponent();

        admissionDate.setDate(new Date());
        admissionDateField.setMaximumSize(new Dimension(220, 20));

        admissionDate.setPreferredSize(new Dimension(220, 30));

        admissionDate.getCalendarButton().setPreferredSize(new Dimension(30, 20));

        JPanel admissionDatePanel = new JPanel(new GridBagLayout());
        admissionDatePanel.setPreferredSize(new Dimension(220, 70));
        JPanel admissionDatePanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        admissionDatePanelWrapper.add(admissionDatePanel);

        dategbc.gridx = 0;
        dategbc.gridy = 0;
        admissionDatePanel.add(admissionDateLabel, dategbc);
        dategbc.gridx = 0;
        dategbc.gridy = 1;
        admissionDatePanel.add(admissionDate, dategbc);

        // Symptoms Section
        JLabel symptomsLabel = new JLabel("Symptoms");
        JTextField symptomsTextField = new JTextField(20);
        JButton addSymptomsBttn = new JButton("Add");

        JPanel symptomsPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel symptomsPanel = new JPanel(new GridBagLayout());
        symptomsPanelWrapper.add(symptomsPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        symptomsPanel.add(symptomsLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        symptomsPanel.add(symptomsTextField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        symptomsPanel.add(addSymptomsBttn, gbc);

        symptomsContainer.setLayout(new GridBagLayout());

        // Add Symptoms Button Listener
        addSymptomsBttn.addActionListener(e -> {
            String symptomText = symptomsTextField.getText();
            if (!symptomText.isEmpty()) {
                symptomsArray.add(symptomText);
                updateSymptomsContainer();
            }
        });

        JPanel SYMPTOMS_ITEM_CONTAINER = new JPanel(new FlowLayout(FlowLayout.LEFT));
        SYMPTOMS_ITEM_CONTAINER.add(symptomsContainer);


        // Medication Section
        JLabel medicationLabel = new JLabel("Medication");
        JTextField medicationTextField = new JTextField(20);
        JButton addMedication = new JButton("Add");

        JPanel medicationPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel medicationPanel = new JPanel(new GridBagLayout());
        medicationPanelWrapper.add(medicationPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        medicationPanel.add(medicationLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        medicationPanel.add(medicationTextField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        medicationPanel.add(addMedication, gbc);

        medicationContainer.setLayout(new GridBagLayout());

        addMedication.addActionListener(e -> {
            String medicationText = medicationTextField.getText();
            if (!medicationText.isEmpty()) {
                medicationArray.add(medicationText);
                updateMedicationContainer();
            }
        });

        JPanel MEDICATION_ITEM_CONTAINER = new JPanel(new FlowLayout(FlowLayout.LEFT));
        MEDICATION_ITEM_CONTAINER.add(medicationContainer);

        // Allergies Section
        JLabel allergiesLabel = new JLabel("Allergies");
        JTextField allergiesTextField = new JTextField(20);
        JButton addAllergy = new JButton("Add");

        JPanel allergiesPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel allergiesPanel = new JPanel(new GridBagLayout());
        allergiesPanelWrapper.add(allergiesPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        allergiesPanel.add(allergiesLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        allergiesPanel.add(allergiesTextField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        allergiesPanel.add(addAllergy, gbc);

        allergiesContainer.setLayout(new GridBagLayout());

        addAllergy.addActionListener(e -> {
            String allergyText = allergiesTextField.getText();
            if (!allergyText.isEmpty()) {
                allergiesArray.add(allergyText); // Add to the allergies list
                updateAllergiesContainer();     // Refresh the container to display the new allergy
            }
        });

        JPanel ALLERGIES_ITEM_CONTAINER = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ALLERGIES_ITEM_CONTAINER.add(allergiesContainer);

        JPanel chooseRoomAndStaffPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel chooseRoomAndStaffPanel = new JPanel(new GridBagLayout());
        String[] options = { "Option 1", "Option 2", "Option 3", "Option 4" , "Option 2", "Option 3", "Option 4" , "Option 2", "Option 3", "Option 4" , "Option 2", "Option 3", "Option 4" };
        JComboBox<String> chooseRoomComboBox = new JComboBox<>(options);
        JComboBox<String> chooseMedicalStaffComboBox = new JComboBox<>(options);
        JLabel chooseRoomAndStaffLabel = new JLabel("Room and Medical Staff");

        gbc.gridx = 0;
        gbc.gridy = 0;
        chooseRoomAndStaffPanel.add(chooseRoomAndStaffLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        chooseRoomAndStaffPanel.add(chooseRoomComboBox, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        chooseRoomAndStaffPanel.add(chooseMedicalStaffComboBox, gbc);

        chooseRoomAndStaffPanelWrapper.add(chooseRoomAndStaffPanel);

        // Add Patient Button
        JButton addPatientButton = new JButton("Add Patient");
        JPanel addPatientButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPatientButtonPanel.add(addPatientButton);

        // Add All Sections to Main Panel
        mainHeader.add(headerPanel);
        mainContent.add(patientNameLabelPanel);
        mainContent.add(patientTextFieldPanel);
        mainContent.add(datePanelWrapper);
        mainContent.add(genderPanel);
        mainContent.add(contactInfoPanelWrapper);
        mainContent.add(addressPanelWrapper);
        mainContent.add(civilStatusWrapper);
        mainContent.add(admissionDatePanelWrapper);
        mainContent.add(chooseRoomAndStaffPanelWrapper);
        mainContent.add(symptomsPanelWrapper);
        mainContent.add(SYMPTOMS_ITEM_CONTAINER);
        mainContent.add(medicationPanelWrapper);
        mainContent.add(MEDICATION_ITEM_CONTAINER);
        mainContent.add(allergiesPanelWrapper);
        mainContent.add(ALLERGIES_ITEM_CONTAINER);

        mainContent.add(addPatientButtonPanel);
        mainContent.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.add(mainHeader, BorderLayout.NORTH);
        mainPanel.add(mainContent, BorderLayout.CENTER);


        // Set Up Frame
        setContentPane(scrollPane);
        setTitle("Add Patient");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        addPatientHeader.setFont(Constants.HEADING_FONT);
        setJTextFieldPadding(this);

//        dateField.setFont(new Font("Arial", Font.PLAIN, 16));
//        dateField.setColumns(30);
    }


    public void updateSymptomsContainer() {
        symptomsContainer.removeAll(); // Clear the container before re-adding the updated symptoms

        int row = 0;
        int columns = 0;
        int shift = 0;

        // Iterate over the symptoms list and create a panel for each symptom
        for (int i = 0; i < symptomsArray.size(); i++) {
            String symptom = symptomsArray.get(i);

            // Create the panel for each symptom
            JPanel symptomPanel = new JPanel();
            JLabel symptomItem = new JLabel(symptom);
            JLabel deleteBttn = new JLabel("X");

            // Define the correct index for deletion inside the loop
            final int finalIndex = i;
            deleteBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Remove the item from the symptoms array
                    symptomsArray.remove(finalIndex);

                    // Update the UI after removal
                    updateSymptomsContainer(); // Refresh the panel with the updated list
                }
            });

            // Add components to the panel
            symptomPanel.add(symptomItem);
            symptomPanel.add(deleteBttn);

            // Styling for the item
            symptomItem.setBorder(new EmptyBorder(0, 10, 0, 10));

            // Set GridBagConstraints for layout (assuming symptomsgbc is initialized properly)
            symptomsgbc.gridx = row;
            symptomsgbc.gridy = columns;

            symptomsContainer.add(symptomPanel, symptomsgbc);

            row++; // Move to the next row
            shift++;

            // Check if it should be reset to the next column
            if (shift == 6) {
                shift = 0;
                row = 0;
                columns++;
            }
        }

        symptomsContainer.revalidate(); // Revalidate the container layout
        symptomsContainer.repaint(); // Repaint the container to reflect the changes
    }

    public void updateAllergiesContainer() {
        allergiesContainer.removeAll(); // Clear the container before re-adding the updated allergies

        int row = 0;
        int columns = 0;
        int shift = 0;

        // Iterate over the allergies list and create a panel for each allergy
        for (int i = 0; i < allergiesArray.size(); i++) {
            String allergy = allergiesArray.get(i);

            // Create the panel for each allergy
            JPanel allergyPanel = new JPanel();
            JLabel allergyItem = new JLabel(allergy);
            JLabel deleteButton = new JLabel("X");

            // Define the correct index for deletion inside the loop
            final int finalIndex = i;
            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Remove the item from the allergies array
                    allergiesArray.remove(finalIndex);

                    // Update the UI after removal
                    updateAllergiesContainer(); // Refresh the panel with the updated list
                }
            });

            // Add components to the panel
            allergyPanel.add(allergyItem);
            allergyPanel.add(deleteButton);

            // Styling for the item
            allergyItem.setBorder(new EmptyBorder(0, 10, 0, 10));

            // Set GridBagConstraints for layout (assuming allergiesgbc is initialized properly)
            allergiesgbc.gridx = row;
            allergiesgbc.gridy = columns;

            allergiesContainer.add(allergyPanel, allergiesgbc);

            row++; // Move to the next row
            shift++;

            // Check if it should be reset to the next column
            if (shift == 6) {
                shift = 0;
                row = 0;
                columns++;
            }
        }

        allergiesContainer.revalidate(); // Revalidate the container layout
        allergiesContainer.repaint();    // Repaint the container to reflect the changes
    }

    public void updateMedicationContainer() {
        medicationContainer.removeAll(); // Clear the container before re-adding the updated symptoms

        int row = 0;
        int columns = 0;
        int shift = 0;

        // Iterate over the symptoms list and create a panel for each symptom
        for (int i = 0; i < medicationArray.size(); i++) {
            String medication = medicationArray.get(i);

            // Create the panel for each symptom
            JPanel medicationPanel = new JPanel();
            JLabel medicationItem = new JLabel(medication);
            JLabel deleteBttn = new JLabel("X");

            // Define the correct index for deletion inside the loop
            final int finalIndex = i;
            deleteBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Remove the item from the symptoms array
                    medicationArray.remove(finalIndex);

                    // Update the UI after removal
                    updateMedicationContainer(); // Refresh the panel with the updated list
                }
            });

            // Add components to the panel
            medicationPanel.add(medicationItem);
            medicationPanel.add(deleteBttn);

            // Styling for the item
            medicationItem.setBorder(new EmptyBorder(0, 10, 0, 10));

            medicationgbc.gridx = row;
            medicationgbc.gridy = columns;

            medicationContainer.add(medicationPanel, medicationgbc);

            row++; // Move to the next row
            shift++;

            // Check if it should be reset to the next column
            if (shift == 6) {
                shift = 0;
                row = 0;
                columns++;
            }
        }

        medicationContainer.revalidate(); // Revalidate the container layout
        medicationContainer.repaint(); // Repaint the container to reflect the changes
    }

    public void setJTextFieldPadding(Container container) {
        for (Component component: container.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setBorder(BorderFactory.createCompoundBorder(
                        ((JTextField) component).getBorder(),                 // Outer border (original)
                        new EmptyBorder(2, 10, 2, 10) // Inner padding: top, left, bottom, right
                ));
            } else if (component instanceof Container) {
                setJTextFieldPadding((Container) component);
            }
        }
    }

}
