package mvc.views;
import com.mongodb.client.FindIterable;
import mvc.controllers.GetPatients;
import mvc.models.RoomModel;
import mvc.views.components.PatientItem;
import mvc.views.constants.Constants;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import mvc.views.utility.SetDefaultFont;
import mvc.views.utility.SetFocusListenerToJTextFields;
import org.bson.Document;

public class AddRoomView extends JFrame {
    RoomModel roomModel;
    RoomView roomView;
    Dashboard dashboard;

    public AddRoomView() {
        System.out.println("Default Constructor");
    }

    public AddRoomView(RoomModel roomModel, RoomView roomView, Dashboard dashboard) {
        this.dashboard = dashboard;
        this.roomModel = roomModel;
        this.roomView = roomView;
        initComponents();
    }

    public JTextField roomName = new JTextField("Room Name",18);
    public JTextField roomType = new JTextField("Room Type",18);
    public JTextField roomCapacity = new JTextField("1",7);
    public JTextField patientSearchField = new JTextField("Search Patient",18);
    JPanel patientListPanel;

    ImageIcon closeButtonIcon = new ImageIcon(getClass().getResource("/src/assets/images/x-icon.png"));
    Image image = closeButtonIcon.getImage();
    Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon resizedCloseButtonIcon = new ImageIcon(resizedImage);

    // Create a JLabel with the PNG image
    JLabel closeButton = new JLabel(resizedCloseButtonIcon);

    public void initComponents() {
        this.setAlwaysOnTop(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 7);

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
        JLabel Header = new JLabel("Add Room");
        Header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(Header);
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


        // RoomDetails Section
        JLabel RoomDetailsLabel = new JLabel("Room Name");
        JLabel RoomTypeLabel = new JLabel("Room Type");
        JLabel roomCapacityLabel = new JLabel("Room Capacity");
        JPanel RoomDetailsWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel RoomDetailsPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        RoomDetailsPanel.add(RoomDetailsLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        RoomDetailsPanel.add(roomName,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        RoomDetailsPanel.add(roomType, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        RoomDetailsPanel.add(roomCapacityLabel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        RoomDetailsPanel.add(roomCapacity, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        RoomDetailsPanel.add(RoomTypeLabel, gbc);
        RoomDetailsWrapper.add(RoomDetailsPanel);


        //Assign Patients Section
        JLabel AssignPatientsLabel = new JLabel("Assign Patient/s");

        JPanel assignPatientsWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel AssignPatientsPanel = new JPanel(new GridBagLayout());

        patientListPanel = new JPanel();
        patientListPanel.setLayout(new BoxLayout(patientListPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(patientListPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroll.setPreferredSize(new Dimension(820, 200));

        // Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Align label to the left
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding
        AssignPatientsPanel.add(AssignPatientsLabel, gbc);

        // Search Field
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE; // Prevent stretching
        AssignPatientsPanel.add(patientSearchField, gbc);

        // Scroll Pane
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH; // Allow scroll pane to expand
        gbc.weightx = 1;
        gbc.weighty = 1;
        AssignPatientsPanel.add(scroll, gbc);

        assignPatientsWrapper.add(AssignPatientsPanel);

        updateUI(patientListPanel);

        //Room Bttn
        JButton addRoomBttn = new JButton("Add Room");
        JPanel addPatientButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPatientButtonPanel.add(addRoomBttn);

        // Add All Sections to Main Panel
        mainHeader.add(headerPanel);
        mainContent.add(RoomDetailsWrapper);
        mainContent.add(assignPatientsWrapper);

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
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        Header.setFont(Constants.HEADING_FONT);


        setOnChangeEvent(this, roomModel);
        new SetFocusListenerToJTextFields(this);
        setJTextFieldPadding(this);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
//
//        this.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowOpened(java.awt.event.WindowEvent e) {
//                addPatientButton.requestFocusInWindow();
//            }
//        });

    }

    public void updateUI(JPanel patientListPanel){
        patientListPanel.removeAll();
        GetPatients getPatients = new GetPatients();
        List<Document> patientList = getPatients.getPatientData();

        if (patientList == null) {
            JLabel noPatient = new JLabel("No Patients Yet");
            patientListPanel.add(noPatient);
        } else {
            for (Document patient : patientList) {
                PatientItem item = new PatientItem(patient, 780);
                patientListPanel.add(item);
                patientListPanel.add(Box.createVerticalStrut(20));
                item.revalidate();
                item.repaint();
            }
        }
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
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

    private void setOnChangeEvent(Container container, RoomModel model) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                ((JTextField) component).getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        handleTextChange();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleTextChange();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        handleTextChange();
                    }

                    private void handleTextChange() {
                        JTextField source = (JTextField) component;
                        String text = source.getText();

                        // Handle text change for specific fields
                        if (source == patientSearchField) {
                            System.out.println(text);
                            List<Document> patientList = GetPatients.filterPatientData(text);

                            if (patientList.isEmpty() && !text.equals("Search Patient") && !text.isEmpty()) {
                                patientListPanel.removeAll();
                                JLabel noPatient = new JLabel("Patient does not exist");
                                patientListPanel.add(noPatient);
                            } else if(!text.isEmpty() && !patientList.isEmpty()){
                                patientListPanel.removeAll();
                                for (Document patient : patientList) {
                                    PatientItem item = new PatientItem(patient, 780);
                                    patientListPanel.add(item);
                                    patientListPanel.add(Box.createVerticalStrut(20));
                                    item.revalidate();
                                    item.repaint();
                                    revalidate();
                                    repaint();
                                }
                            }else {
                               updateUI(patientListPanel);
                            }
                            revalidate();
                            repaint();
                        }
                    }});
            } else if (component instanceof Container) {
                setOnChangeEvent((Container) component, model);
            }
        }
    }


}
