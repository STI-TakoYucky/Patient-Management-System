package mvc.views;
import com.toedter.calendar.JDateChooser;
import mvc.controllers.AddPatientController;
import mvc.controllers.AddStaffController;
import mvc.models.PatientModel;
import mvc.models.StaffModel;
import mvc.views.constants.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiConsumer;

import mvc.views.utility.SetDefaultFont;
import mvc.views.utility.SetFocusListenerToJTextFields;
import org.bson.Document;

public class MedicalRecordsView extends JFrame {
    StaffModel staffModel;
    MedicalStaffView medicalStaffView;
    Dashboard dashboard;
    RoomView roomView;
    JFrame frame = this;
    Document patientDocument;

    public MedicalRecordsView() {
        System.out.println("Default Constructor");
    }

    public MedicalRecordsView(Document patient, Dashboard dashboard) {
        this.patientDocument = patient;
        this.dashboard = dashboard;
        this.staffModel = staffModel;
        this.medicalStaffView = medicalStaffView;
        initComponents();
    }


    ImageIcon closeButtonIcon = new ImageIcon(getClass().getResource("/src/assets/images/x-icon.png"));
    Image image = closeButtonIcon.getImage();
    Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon resizedCloseButtonIcon = new ImageIcon(resizedImage);

    // Create a JLabel with the PNG image
    JLabel closeButton = new JLabel(resizedCloseButtonIcon);


    public void initComponents() {
        setAlwaysOnTop(true);
        repaint();
        revalidate();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 15);

        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        // Header Icon
        ImageIcon EPHedP;
        Image resizedEPHed;
        ImageIcon EPHedIcon;
        int wid = 45;
        int hei = 45;
        EPHedP = new ImageIcon("src/assets/images/icons8-medical-records-66.png");
        resizedEPHed = EPHedP.getImage().getScaledInstance(wid,hei, Image.SCALE_SMOOTH);
        EPHedIcon = new ImageIcon(resizedEPHed);
        // Header Panel
        JPanel mainHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainHeader.setBackground(Constants.secondary);
        mainHeader.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel addPatientHeader = new JLabel("Medical Records",EPHedIcon, JLabel.LEFT);
        addPatientHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        closeButton.setBorder(new EmptyBorder(0, 580, 0, 0));
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

        mainHeader.add(addPatientHeader);
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
        mainContent.add(createLabel("Blood Type", patientDocument.getString("BloodType")));
        mainContent.add(createLabel("Birthdate", formattedBirthDate));
        mainContent.add(createLabel("Sex", patientDocument.getString("Sex")));
        mainContent.add(createLabel("Nationality", patientDocument.getString("Nationality")));
        mainContent.add(createLabel("Civil Status", patientDocument.getString("Civil Status")));
        mainContent.add(createLabel("Phone Number", patientDocument.get("Phone Number").toString()));
        mainContent.add(createLabel("Email Address", patientDocument.getString("Email")));
        mainContent.add(createLabel("Emergency Contact", patientDocument.get("Emergency Contact Number").toString()));
        mainContent.add(createLabel("Street Address", patientDocument.getString("Street Name")));
        mainContent.add(createLabel("City", patientDocument.getString("City")));
        mainContent.add(createLabel("Region", patientDocument.getString("Region")));
        mainContent.add(createLabel("Municipality", patientDocument.getString("Municipality")));
        mainContent.add(createLabel("Postal Code", patientDocument.get("Postal Code").toString()));


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

        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        new SetFocusListenerToJTextFields(this);
        addPatientHeader.setFont(Constants.HEADING_FONT);
    }

    /**
     * Utility method to create a field-value JLabel pair.
     */
    private JPanel createLabel(String fieldName, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 15, 5, 15));
        panel.setBackground(Color.WHITE);

        JLabel fieldLabel = new JLabel(fieldName + ": ");
        fieldLabel.setFont(Constants.DEFAULT_FONT);
        fieldLabel.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(Constants.DEFAULT_FONT);
        valueLabel.setForeground(Color.BLACK);

        panel.add(fieldLabel, BorderLayout.WEST);
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
////                        String pattern = "^[a-zA-Zs]*$";
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


}
