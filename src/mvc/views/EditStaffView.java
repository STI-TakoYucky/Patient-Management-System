package mvc.views;
import mvc.controllers.*;
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

public class EditStaffView extends JFrame {
    StaffModel staffModel;
    MedicalStaffView medicalStaffView;
    Dashboard dashboard;
    RoomView roomView;
    JFrame frame = this;
    String staffID;
    Document staffList;

    public EditStaffView() {
        System.out.println("Default Constructor");
    }

    public EditStaffView(String id, StaffModel staffModel, MedicalStaffView view, Dashboard dashboard, Document staffList) {
        this.staffID = id;
        this.staffList = staffList;
        this.dashboard = dashboard;
        this.staffModel = staffModel;
        this.medicalStaffView = view;
        initComponents();
    }

    public JTextField staffNameFieldFN;
    public JTextField staffNameFieldLN;
    public JTextField staffNameFieldMN;
    public JTextField staffPosition;
    public JTextField staffUserName;
    public JTextField staffPassword;


    ImageIcon closeButtonIcon = new ImageIcon(getClass().getResource("/src/assets/images/x-icon.png"));
    Image image = closeButtonIcon.getImage();
    Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon resizedCloseButtonIcon = new ImageIcon(resizedImage);

    // Create a JLabel with the PNG image
    JLabel closeButton = new JLabel(resizedCloseButtonIcon);


    public void initComponents() {

        if (this.staffList != null) {
            staffNameFieldFN = new JTextField(staffList.getString("First Name"),18);
            staffNameFieldLN = new JTextField(staffList.getString("Middle Name"),18);
            staffNameFieldMN = new JTextField(staffList.getString("Last Name"),18);
            staffPosition = new JTextField(staffList.getString("Position"),18);
            staffUserName = new JTextField(staffList.getString("Username"),18);
            staffPassword = new JTextField(staffList.getString("Password"),18);
        }

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
        JLabel addPatientHeader = new JLabel("Add Medical Staff");
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
        JLabel staffName = new JLabel("Medical Staff Name");

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






        JLabel staffDetailsPanel = new JLabel("Medical Staff Details");

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        JPanel detailsPanelWrappeer = new JPanel();
        detailsPanelWrappeer.setLayout(new FlowLayout(FlowLayout.LEFT));
        detailsPanelWrappeer.add(detailsPanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(staffDetailsPanel, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        detailsPanel.add(staffPosition, gbc);
        gbc.gridx = 1;
        detailsPanel.add(staffUserName, gbc);
        gbc.gridx = 2;
        detailsPanel.add(staffPassword, gbc);

        // Add Patient Button
        JButton addPatientButton = new JButton("Add Staff");
        JButton deleteStaff = new JButton("Delete Staff");
        JPanel addPatientButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPatientButtonPanel.add(addPatientButton);
        addPatientButtonPanel.add(deleteStaff);


        addPatientButton.addActionListener(e -> {
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

        deleteStaff.addActionListener(e -> {

            // Display a confirmation dialog with Yes, No, and Cancel options
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this staff?",
                    "Delete Staff", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                new DeleteStaffController(staffID);
                medicalStaffView.updateUI();
                JOptionPane.showMessageDialog(null, "Deleted Successfully");
                dispose();
                dashboard.setEnabled(true);
                dashboard.setFocusable(true);
                dashboard.setAlwaysOnTop(true);
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

    public void addStaffToDatabase() {
        int choice = JOptionPane.showConfirmDialog(null, "Confirm?",
                "Edit Staff", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                new EditStaffController(staffModel);
                medicalStaffView.updateUI();
                JOptionPane.showMessageDialog(null, "Successfully edited a staff");
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

        // Validate position (optional, but should not be empty)
        if (staffModel.getPosition() != null && staffModel.getPosition().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Position cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validation passed for all fields
        return true;
    }






}
