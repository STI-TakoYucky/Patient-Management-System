package mvc.views;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import database.URI;
import mvc.controllers.AddAdminController;
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

import mvc.views.utility.SetDefaultFont;
import mvc.views.utility.SetFocusListenerToJTextFields;
import org.bson.Document;

public class AddAdminView extends JFrame {
    StaffModel staffModel;
    AdminView adminView;
    Dashboard dashboard;
    RoomView roomView;
    JFrame frame = this;

    public AddAdminView() {
        System.out.println("Default Constructor");
    }

    public AddAdminView(StaffModel staffModel, AdminView adminView, Dashboard dashboard) {
        this.dashboard = dashboard;
        this.staffModel = staffModel;
        this.adminView = adminView;
        initComponents();
    }

    public JTextField staffNameFieldFN = new JTextField("First Name",18);
    public JTextField staffNameFieldLN = new JTextField("Last Name",18);
    public JTextField staffNameFieldMN = new JTextField("Middle Name",18);
    public JTextField staffPosition = new JTextField("Position",18);
    public JTextField staffUserName = new JTextField("Username",18);
    public JTextField staffPassword = new JTextField("Password",18);


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
        // Header Icon
        ImageIcon addAdminP;
        Image resizedAdmin;
        ImageIcon addAdminIcon;
        int wid = 50;
        int hei = 50;
        addAdminP = new ImageIcon("src/assets/images/admin.png");
        resizedAdmin =addAdminP.getImage().getScaledInstance(wid,hei, Image.SCALE_SMOOTH);
        addAdminIcon = new ImageIcon(resizedAdmin);
        // Header Section
        JLabel addPatientHeader = new JLabel("Add Admin",addAdminIcon,JLabel.LEFT);
        addPatientHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(addPatientHeader);
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

        headerPanel.add(closeButton);
        headerPanel.setBackground(Constants.secondary);


        // Patient Name Section
        JLabel staffName = new JLabel("Admin Name");

        JPanel namePanel = new JPanel(new GridBagLayout());
        JPanel namePanelWrapper = new JPanel();
        namePanelWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
        namePanelWrapper.add(namePanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        namePanel.add(staffName, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        namePanel.add(staffNameFieldFN, gbc);
        gbc.gridx = 1;
        namePanel.add(staffNameFieldMN, gbc);
        gbc.gridx = 2;
        namePanel.add(staffNameFieldLN, gbc);






        JLabel staffDetailsPanel = new JLabel("Admin Account");

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        JPanel detailsPanelWrappeer = new JPanel();
        detailsPanelWrappeer.setLayout(new FlowLayout(FlowLayout.LEFT));
        detailsPanelWrappeer.add(detailsPanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(staffDetailsPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(staffUserName, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        detailsPanel.add(staffPassword, gbc);

        // Add Patient Button
        JButton addPatientButton = new JButton("Add Admin");
        JPanel addPatientButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPatientButtonPanel.add(addPatientButton);


        addPatientButton.addActionListener(e -> {
            setAlwaysOnTop(false);
            staffModel.setFirstName(staffNameFieldFN.getText());
            staffModel.setMiddleName(staffNameFieldMN.getText());
            staffModel.setLastName(staffNameFieldLN.getText());
            staffModel.setPosition(staffPosition.getText());
            staffModel.setUsername(staffUserName.getText());
            staffModel.setPassword(staffPassword.getText());
            if (validateStaffModel(staffModel)) {
                addStaffToDatabase();
            }

        });

        // Add All Sections to Main Panel
        mainHeader.add(headerPanel);
        mainContent.add(namePanelWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(detailsPanelWrappeer);

        mainContent.add(addPatientButtonPanel);
        mainContent.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.add(mainHeader, BorderLayout.NORTH);
        mainPanel.add(mainContent, BorderLayout.CENTER);


        // Set Up Frame
        setContentPane(scrollPane);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setAlwaysOnTop(true);

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

    private boolean isUsernameTaken(String username) {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            // Check in adminDB -> admins collection
            MongoDatabase adminDatabase = mongoClient.getDatabase("adminDB");
            MongoCollection<Document> adminUsers = adminDatabase.getCollection("admins");
            Document adminUser = adminUsers.find(Filters.eq("Username", username)).first();

            if (adminUser != null) {
                return true; // Username already exists in adminDB
            }

            // Check in staffDB -> medical staff collection
            MongoDatabase staffDatabase = mongoClient.getDatabase("staffDB");
            MongoCollection<Document> medicalStaff = staffDatabase.getCollection("medical staff");
            Document staffUser = medicalStaff.find(Filters.eq("Username", username)).first();

            if (staffUser != null) {
                return true; // Username already exists in staffDB
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return false; // Username does not exist in either database
    }


    public void addStaffToDatabase() {
        String username = staffModel.getUsername();

        if (isUsernameTaken(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists in the system. Please choose a different username.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if the username is already taken
        }

        int choice = JOptionPane.showConfirmDialog(null, "Confirm?",
                "Add Admin", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                new AddAdminController(staffModel);
                adminView.updateUI();
                JOptionPane.showMessageDialog(null, "Successfully added an admin");
            }catch (NumberFormatException err) {
                System.out.println(err);
            } catch (Exception err) {
                System.out.println("System Error");
            }
                dispose();
                dashboard.setEnabled(true);
                dashboard.setFocusable(true);
                dashboard.setAlwaysOnTop(true);

        }
    }

    public boolean validateStaffModel(StaffModel staffModel) {
        // Regex patterns
        String NAME_REGEX = "^[A-Za-z]+$"; // Letters only for names
        String GENERAL_TEXT_REGEX = "^[A-Za-z0-9-.\\s]+$"; // Allow letters and numbers with spaces
        String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$"; // Password regex

        // Validate firstName
        if (staffModel.getFirstName() == null || !staffModel.getFirstName().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid first name! Please use letters only.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate lastName
        if (staffModel.getLastName() == null || !staffModel.getLastName().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid last name! Please use letters only.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate middleName (optional, but must match if provided)
        if (staffModel.getMiddleName() != null && !staffModel.getMiddleName().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid middle name! Please use letters only.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate username (optional, but must match if provided)
        if (staffModel.getUsername() != null && !staffModel.getUsername().matches(GENERAL_TEXT_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid username! Please use alphanumeric characters and valid symbols.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate password using the password regex
        if (staffModel.getPassword() == null || !staffModel.getPassword().matches(PASSWORD_REGEX)) {
            JOptionPane.showMessageDialog(null, "Invalid password! Password must be at least 8 characters long, include uppercase, lowercase, a number, and a special character.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validation passed for all fields
        return true;
    }






}
