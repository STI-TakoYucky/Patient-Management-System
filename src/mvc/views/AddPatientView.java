package mvc.views;
import mvc.controllers.AddPatientController;
import mvc.controllers.DeletePatientController;
import mvc.controllers.GetRooms;
import mvc.models.PatientModel;
import mvc.views.constants.Constants;
import com.toedter.calendar.JDateChooser;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import mvc.views.utility.SetDefaultFont;
import mvc.views.utility.SetFocusListenerToJTextFields;
import org.bson.Document;

public class AddPatientView extends JFrame {
    PatientModel patientModel;
    PatientView patientView;
    Dashboard dashboard;
    RoomView roomView;
    JFrame frame = this;

    public AddPatientView() {
        System.out.println("Default Constructor");
    }

    public AddPatientView(PatientModel patientModel, PatientView patientView, Dashboard dashboard, RoomView roomView) {
        this.dashboard = dashboard;
        this.patientModel = patientModel;
        this.patientView = patientView;
        this.roomView = roomView;
        initComponents();
    }

    public JTextField patientNameFieldFN = new JTextField("First Name",18);
    public JDateChooser birthDate = new JDateChooser();
    public JDateChooser admissionDate = new JDateChooser();
    public JTextField patientNameFieldLN = new JTextField("Last Name",13);
    public JTextField patientNameFieldMN = new JTextField("Middle Name",13);
    public JTextField allergiesTextField = new JTextField(20);
    public JTextField medicationTextField = new JTextField(20);
    public JTextField symptomsTextField = new JTextField(20);
    public JTextField phoneNumberField = new JTextField("Phone Number", 15);
    public JTextField emailAddressField = new JTextField("Email",20);
    public JTextField emergencyContactNumberField = new JTextField("Emergency Contact no.",15);
    public JTextField streetAddressField = new JTextField("Street Name",20);
    public JTextField cityField = new JTextField("City",20);
    public JTextField regionField = new JTextField("Region",20);
    public JTextField municipalityField = new JTextField("Municipality",20);
    public JTextField postalCodeField = new JTextField("4106",8);
    public JTextField nationalityTextField = new JTextField("Nationality", 15);
    public JTextField civilStatusField = new JTextField("Civil Status", 15);
    public JRadioButton maleRadioButtonn = new JRadioButton("Male");
    public JRadioButton femaleRadioButton = new JRadioButton("Female");

    ImageIcon closeButtonIcon = new ImageIcon(getClass().getResource("/src/assets/images/x-icon.png"));
    Image image = closeButtonIcon.getImage();
    Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon resizedCloseButtonIcon = new ImageIcon(resizedImage);

    // Create a JLabel with the PNG image
    JLabel closeButton = new JLabel(resizedCloseButtonIcon);

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
        repaint();
        revalidate();

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
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(addPatientHeader);
        closeButton.setBorder(new EmptyBorder(0, 650, 0, 0));
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

        headerPanel.add(closeButton);
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

        ButtonGroup group = new ButtonGroup();
        group.add(maleRadioButtonn);
        group.add(femaleRadioButton);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(genderFieldLabel);
        genderPanel.add(maleRadioButtonn);
        genderPanel.add(femaleRadioButton);

        // Contact Information Section
        JLabel contactInfoLabel = new JLabel("Contact Information");

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
        contactInfoPanel.add(emergencyContactNumberField, gbc);

        // Address Section
        JLabel addressLabel = new JLabel("Address");

        JPanel addressPanel = new JPanel(new GridBagLayout());
        JPanel addressPanelWrapper = new JPanel();
        addressPanelWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
        addressPanelWrapper.add(addressPanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        addressPanel.add(addressLabel, gbc);
        gbc.gridy = 1;
        addressPanel.add(regionField, gbc);
        gbc.gridx = 1;
        addressPanel.add(municipalityField, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        addressPanel.add(cityField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        addressPanel.add(postalCodeField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addressPanel.add(streetAddressField, gbc);


        //Nationality Section
        JPanel nationalityWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel nationalityPanel = new JPanel(new GridBagLayout());
        JLabel nationalityLabel = new JLabel("Nationality");

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

        gbc.gridx = 0;
        gbc.gridy = 0;
        civilStatusPanel.add(civilStatusLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        civilStatusPanel.add(civilStatusField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        civilStatusPanel.add(nationalityTextField, gbc);
        civilStatusWrapper.add(civilStatusPanel);

        // Admission date
        JLabel admissionDateLabel = new JLabel("Enter Admission Date");

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
        addSymptomsBttn.addActionListener(_ -> {
            String symptomText = symptomsTextField.getText();
            if (!symptomText.isEmpty()) {
                if(symptomsArray.contains(symptomsTextField.getText())){
                    symptomsTextField.setText("");
                    return;
                };
                symptomsArray.add(symptomText);
                updateSymptomsContainer();
                symptomsTextField.setText("");
            }
        });

        JPanel SYMPTOMS_ITEM_CONTAINER = new JPanel(new FlowLayout(FlowLayout.LEFT));
        SYMPTOMS_ITEM_CONTAINER.add(symptomsContainer);


        // Medication Section
        JLabel medicationLabel = new JLabel("Medication");
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

        addMedication.addActionListener(_ -> {
            String text = medicationTextField.getText();
            if (!text.isEmpty()) {
                if(medicationArray.contains(medicationTextField.getText())){
                    medicationTextField.setText("");
                    return;
                };
                medicationArray.add(text);
                updateMedicationContainer();
                medicationTextField.setText("");
            }
        });

        JPanel MEDICATION_ITEM_CONTAINER = new JPanel(new FlowLayout(FlowLayout.LEFT));
        MEDICATION_ITEM_CONTAINER.add(medicationContainer);

        // Allergies Section
        JLabel allergiesLabel = new JLabel("Allergies");
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

        addAllergy.addActionListener(_ -> {
            String text = allergiesTextField.getText();
            if (!text.isEmpty()) {
                if(allergiesArray.contains(allergiesTextField.getText())){
                    allergiesTextField.setText("");
                    return;
                };
                allergiesArray.add(text);
                updateAllergiesContainer();
                allergiesTextField.setText("");
            }
        });

        JPanel ALLERGIES_ITEM_CONTAINER = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ALLERGIES_ITEM_CONTAINER.add(allergiesContainer);

        JPanel chooseRoomAndStaffPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel chooseRoomAndStaffPanel = new JPanel(new GridBagLayout());

        GetRooms getRooms = new GetRooms();
        List<Document> roomData = getRooms.getRoomData();


        JComboBox<String> chooseRoomComboBox = new JComboBox<>();
        JComboBox<String> chooseMedicalStaffComboBox = new JComboBox<>();
        chooseRoomComboBox.addItem("Select Room");
        chooseRoomComboBox.setSelectedItem(0);

        if (roomData != null) {
            for (Document room : roomData) {
                Map<String, String> patientMap = (Map<String, String>) room.get("Patients");
                int patientMapSize = patientMap.size();
                String roomName = room.getString("Room Name");
                if (roomName != null && !(room.getInteger("Room Capacity") == patientMapSize)) { // Ensure roomName is not null
                    chooseRoomComboBox.addItem(roomName);
                    chooseMedicalStaffComboBox.addItem(roomName);
                }
            }
        }


        JLabel chooseRoomAndStaffLabel = new JLabel("Assigned Medical Staff");

        gbc.gridx = 0;
        gbc.gridy = 0;
        chooseRoomAndStaffPanel.add(chooseRoomAndStaffLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        chooseRoomAndStaffPanel.add(chooseMedicalStaffComboBox, gbc);

        chooseRoomAndStaffPanelWrapper.add(chooseRoomAndStaffPanel);

        // Add Patient Button
        JButton addPatientButton = new JButton("Add Patient");
        JPanel addPatientButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPatientButtonPanel.add(addPatientButton);


        addPatientButton.addActionListener(e -> {
            patientModel.setFirstName(patientNameFieldFN.getText());
            patientModel.setLastName(patientNameFieldLN.getText());
            patientModel.setMiddleName(patientNameFieldMN.getText());
            patientModel.setEmail(emailAddressField.getText());
            patientModel.setStreetName(streetAddressField.getText());
            patientModel.setCity(cityField.getText());
            patientModel.setRegion(regionField.getText());
            patientModel.setCivilStatus(civilStatusField.getText());
            patientModel.setPhoneNumber(phoneNumberField.getText());
            patientModel.setEmergencyContactNumber(emergencyContactNumberField.getText());
            patientModel.setMunicipality(municipalityField.getText());
            patientModel.setNationality(nationalityTextField.getText());
            patientModel.setBirthdate(birthDate.getDate());
            patientModel.setAdmissionDate(admissionDate.getDate());
            patientModel.setRoom((String) chooseRoomComboBox.getSelectedItem());
            if (maleRadioButtonn.isSelected()) {
                patientModel.setSex(maleRadioButtonn.getText());
            } else if (femaleRadioButton.isSelected()) {
                patientModel.setSex(femaleRadioButton.getText());
            }
            patientModel.setSymptoms(symptomsArray);
            patientModel.setMedication(medicationArray);
            patientModel.setAllergies(allergiesArray);
            patientModel.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            if (validatePatientModel(patientModel)) {
                addPatientToDatabase();
            } else {
                System.out.println("Validation failed.");
            }

        });

        // Add All Sections to Main Panel
        mainHeader.add(headerPanel);
        mainContent.add(patientNameLabelPanel);
        mainContent.add(patientTextFieldPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(datePanelWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(genderPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(contactInfoPanelWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(addressPanelWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(civilStatusWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(admissionDatePanelWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(chooseRoomAndStaffPanelWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(symptomsPanelWrapper);
        mainContent.add(SYMPTOMS_ITEM_CONTAINER);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(medicationPanelWrapper);
        mainContent.add(MEDICATION_ITEM_CONTAINER);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(allergiesPanelWrapper);
        mainContent.add(ALLERGIES_ITEM_CONTAINER);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        addPatientHeader.setFont(Constants.HEADING_FONT);
        setJTextFieldPadding(this);

        new SetFocusListenerToJTextFields(this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                addPatientButton.requestFocusInWindow();
            }
        });

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

//    private void setOnChangeEvent(Container container, PatientModel model) {
//        for (Component component : container.getComponents()) {
//            if (component instanceof JTextField) {
//                JTextField textField = (JTextField) component;
//                String[] previousValue = { textField.getText() };
//                ((JTextField) component).getDocument().addDocumentListener(new DocumentListener() {
//                    @Override
//                    public void insertUpdate(DocumentEvent e) {
//                        handleTextChange();
//                    }
//
//                    @Override
//                    public void removeUpdate(DocumentEvent e) {
//                        handleTextChange();
//                    }
//
//                    @Override
//                    public void changedUpdate(DocumentEvent e) {
//                        handleTextChange();
//                    }
//
//                    private void handleTextChange() {
//                        SwingUtilities.invokeLater(() -> {
////                        String pattern = "^[a-zA-Z\\s]*$";
////                        String pattern2 = "^09\\d{9}$";    // Numbers starting with 09, exactly 11 digits
////                        String pattern3 = "^\\d{4}$";
//                        JTextField source = (JTextField) component;
//                        String text = source.getText();
////
////
////
////                        if (component == patientNameFieldFN || component == patientNameFieldLN ||
////                                component == patientNameFieldMN || component == cityField ||
////                                component == municipalityField || component == nationalityTextField ||
////                                component == civilStatusField) {
////                            if (!text.matches(pattern)) {
////                                JOptionPane.showMessageDialog(null, "Invalid input. Please input letters and spaces only.");
////                                source.setText(""); // Clear invalid input
////
////                            }}
////                        else if (component == emergencyContactNumberField || component == phoneNumberField ) {
////                            if (text.length() == 11 && !text.matches(pattern2)|| text.length() > 11) {
////                                JOptionPane.showMessageDialog(null, "Enter a valid number.");
////                                source.setText(""); // Clear invalid input
////
////                            }
////                        }
////                        else if (component == postalCodeField) {
////
////                            // Validate only when text length is exactly 4
////                            if (text.length() == 4 && !text.matches(pattern3)) {
////                                JOptionPane.showMessageDialog(null, "Postal code must be exactly 4 digits.");
////                                source.setText("");  // Clear invalid input
////                            }
////                            else if (text.length() > 4) {
////                                JOptionPane.showMessageDialog(null, "Postal code must be exactly 4 digits.");
////                                source.setText("");  // Clear input if it's more than 4 digits
////                            }}
//
//                          // Handle text change for specific fields
//                        if (component == patientNameFieldFN) {
//                            model.setFirstName(text);
//                        } else if (component == patientNameFieldLN) {
//                            model.setLastName(text);
//                        } else if (component == patientNameFieldMN) {
//                            model.setMiddleName(text);
//                        } else if (component == emailAddressField) {
//                            model.setEmail(text);
//                        } else if (component == streetAddressField) {
//                            model.setStreetName(text);
//                        } else if (component == cityField) {
//                            model.setCity(text);
//                        } else if (component == regionField) {
//                            model.setRegion(text);
//                        } else if (component == civilStatusField) {
//                            model.setCivilStatus(text);
//                        } else if (component == phoneNumberField) {
//                            model.setPhoneNumber(text);
//                        } else if (component == emergencyContactNumberField) {
//                            model.setEmergencyContactNumber(text);
//                        } else if (component == municipalityField) {
//                            model.setMunicipality(text);
//                        } else if (component == nationalityTextField) {
//                            model.setNationality(text);
//                        }
//
//                }
//                );}
//                });
//            } else if (component instanceof Container) {
//                setOnChangeEvent((Container) component, model);
//            }
//        }
//    }

    public void addPatientToDatabase() {
        int choice = JOptionPane.showConfirmDialog(null, "Confirm?",
                "Add patient", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                new AddPatientController(patientModel, roomView);
                patientView.updateUI();
                JOptionPane.showMessageDialog(null, "Successfully added a patient");
            }catch (NumberFormatException err) {
                System.out.println(err);
            } catch (Exception err) {
                System.out.println("System Error");
            }
            int addMorePatient = JOptionPane.showConfirmDialog(null, "Add another patient?",
                    "Add patient", JOptionPane.YES_NO_OPTION);
            if (addMorePatient == JOptionPane.YES_OPTION) {

            } else {
                dispose();
                dashboard.setEnabled(true);
                dashboard.setFocusable(true);
                dashboard.setAlwaysOnTop(true);
            }

        }
    }

    public boolean validatePatientModel(PatientModel patientModel) {
        // Regex patterns
        String NAME_REGEX = "^[A-Za-z]+$"; // Letters only for names
        String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // Basic email regex
        String GENERAL_TEXT_REGEX = "^[A-Za-z0-9-.\\s]+$"; // Allow letters and numbers with spaces
        String PH_PHONE_NUMBER_REGEX = "^(09|\\+639)\\d{9}$"; // Philippine phone number (starts with 09 or +639)

        // Validate first name
        if (patientModel.getFirstName() == null || !patientModel.getFirstName().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid first name. It must contain only letters.");
            return false;
        }

        // Validate last name
        if (patientModel.getLastName() == null || !patientModel.getLastName().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid last name. It must contain only letters.");
            return false;
        }

        // Validate middle name
        if (patientModel.getMiddleName() == null || !patientModel.getMiddleName().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid middle name. It must contain only letters.");
            return false;
        }

        // Validate email
        if (patientModel.getEmail() == null || !patientModel.getEmail().matches(EMAIL_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid email address.");
            return false;
        }

        // Validate street name
        if (patientModel.getStreetName() == null || !patientModel.getStreetName().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid street name.");
            return false;
        }

        // Validate city name
        if (patientModel.getCity() == null || !patientModel.getCity().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid city name. It must contain only letters.");
            return false;
        }

        // Validate region name
        if (patientModel.getRegion() == null || !patientModel.getRegion().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid region name. It must contain only letters.");
            return false;
        }

        // Validate civil status
        if (patientModel.getCivilStatus() == null || !patientModel.getCivilStatus().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid civil status. It must contain only letters.");
            return false;
        }

        // Validate phone number
        if (patientModel.getPhoneNumber() == null || !patientModel.getPhoneNumber().matches(PH_PHONE_NUMBER_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid phone number.");
            return false;
        }

        // Validate emergency contact number
        if (patientModel.getEmergencyContactNumber() == null || !patientModel.getEmergencyContactNumber().matches(PH_PHONE_NUMBER_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid emergency contact number.");
            return false;
        }

        // Validate municipality name
        if (patientModel.getMunicipality() == null || !patientModel.getMunicipality().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid municipality name. It must contain only letters.");
            return false;
        }

        // Validate postal code
        if (patientModel.getPostalCode() == 0) {
            JOptionPane.showMessageDialog(null, "Postal code must be provided.");
            return false;
        }
        int postalCode = patientModel.getPostalCode();
        if (postalCode < 1000 || postalCode > 9999) {
            JOptionPane.showMessageDialog(null, "Postal code must be a 4-digit number.");
            return false;
        }

        // Validate nationality
        if (patientModel.getNationality() == null || !patientModel.getNationality().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid nationality. It must contain only letters.");
            return false;
        }

        // Validate birthdate
        if (patientModel.getBirthdate() == null) {
            JOptionPane.showMessageDialog(null, "Birthdate cannot be empty.");
            return false;
        }

        // Validate admission date
        if (patientModel.getAdmissionDate() == null) {
            JOptionPane.showMessageDialog(null, "Admission date cannot be empty.");
            return false;
        }

        // Validate sex
        if (patientModel.getSex() == null) {
            JOptionPane.showMessageDialog(null, "Please select the patient's sex.");
            return false;
        }

        // Validate Philippine phone numbers (start with 09 and contain 10 digits)
        if (patientModel.getPhoneNumber() != null && !patientModel.getPhoneNumber().matches(PH_PHONE_NUMBER_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid phone number. It must start with '09' and contain 10 digits.");
            return false;
        }

        if (patientModel.getEmergencyContactNumber() != null && !patientModel.getEmergencyContactNumber().matches(PH_PHONE_NUMBER_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid emergency contact number. It must start with '09' and contain 10 digits.");
            return false;
        }

        return true; // All validations passed
    }



}
