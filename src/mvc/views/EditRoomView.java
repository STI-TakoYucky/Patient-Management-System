package mvc.views;
import mvc.controllers.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mvc.views.utility.SetDefaultFont;
import mvc.views.utility.SetFocusListenerToJTextFields;
import org.bson.Document;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class EditRoomView extends JFrame {
    RoomModel roomModel;
    RoomView roomView;
    Dashboard dashboard;
    EditRoomView addRoomView = this;
    Map<String, String> assignedPatients = new HashMap<>();
    Document roomData;
    String roomID;

    public EditRoomView() {
        System.out.println("Default Constructor");
    }

    public EditRoomView(String roomID, RoomModel roomModel, RoomView roomView, Dashboard dashboard) {
        this.dashboard = dashboard;
        this.roomModel = roomModel;
        this.roomView = roomView;
        this.roomID = roomID;
        GetRooms getRooms = new GetRooms();
        this.roomData = getRooms.getRoomDataByID(roomID);
        initComponents();
    }
    JPanel patientListPanel;
    JPanel toBeAssignedPanel = new JPanel();
    JTextField patientSearchField = new JTextField("Search Patient",18);

    public JTextField roomName, roomType, roomCapacity;
    ArrayList<String> PatientsNameArray = new ArrayList<>();
    ArrayList<String> PatientsIDArray = new ArrayList<>();

    ImageIcon closeButtonIcon = new ImageIcon(getClass().getResource("/src/assets/images/x-icon.png"));
    Image image = closeButtonIcon.getImage();
    Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon resizedCloseButtonIcon = new ImageIcon(resizedImage);

    // Create a JLabel with the PNG image
    JLabel closeButton = new JLabel(resizedCloseButtonIcon);

    public void initComponents() {

        if (roomData != null) {
            roomName = new JTextField(roomData.getString("Room Name"),18);
            roomType = new JTextField(roomData.getString("Room Type"),18);
            roomCapacity = new JTextField(String.valueOf(roomData.getInteger("Room Capacity")),7);
        }

        Map<String, String> patientMap = (Map<String, String>) roomData.get("Patients");
        if (patientMap != null) {
            for (Map.Entry<String, String> entry : patientMap.entrySet()) {
                PatientsNameArray.add(entry.getValue());
                PatientsIDArray.add(entry.getKey());
            }
        }

        updateAssignedPatients();

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
        JLabel Header = new JLabel("Edit Room");
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

        toBeAssignedPanel.setLayout(new BoxLayout(toBeAssignedPanel, BoxLayout.Y_AXIS));

        patientListPanel = new JPanel();
        patientListPanel.setLayout(new BoxLayout(patientListPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(patientListPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
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

        // To Be Assigned Panel
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH; // Allow scroll pane to expand
        gbc.weightx = 1;
        gbc.weighty = 1;
        AssignPatientsPanel.add(toBeAssignedPanel, gbc);

        assignPatientsWrapper.add(AssignPatientsPanel);

        updateUI(patientListPanel);

        //Room Bttn
        JButton editRoomBttn = new JButton("Edit Room");
        JButton deleteRoomBttn = new JButton("Delete Room");
        JPanel addRoomBttnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addRoomBttnPanel.add(editRoomBttn);
        addRoomBttnPanel.add(deleteRoomBttn);

        deleteRoomBttn.addActionListener(_ -> {

            // Display a confirmation dialog with Yes, No, and Cancel options
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this room?",
                    "Delete room", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                new DeleteRoomController(this.roomID);
                roomView.updateUI();
                JOptionPane.showMessageDialog(null, "Deleted Successfully");
                dispose();
                dashboard.setEnabled(true);
                dashboard.setFocusable(true);
                dashboard.setAlwaysOnTop(true);
            }
        });

        editRoomBttn.addActionListener(_ -> {
            this.setAlwaysOnTop(false);
            try {
                if (!(Integer.parseInt(roomCapacity.getText()) <= 0 )) {

                    int choice = JOptionPane.showConfirmDialog(null, "Confirm?",
                            "Edit Room", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        roomModel.setRoomID(roomData.getString("_id"));
                        roomModel.setRoomName(roomName.getText());
                        roomModel.setRoomCapacity(Integer.parseInt(roomCapacity.getText()));
                        roomModel.setRoomType(roomType.getText());
                        for (int i = 1; i <= PatientsNameArray.size(); i++) {
                            assignedPatients.put(PatientsIDArray.get(i - 1), PatientsNameArray.get(i - 1));
                        }
                        roomModel.setAssignedPatients(assignedPatients);
                        new EditRoomController(roomModel);
                        JOptionPane.showMessageDialog(null, "Successfully edited a room");
                        roomView.updateUI();

                            dispose();
                            dashboard.setEnabled(true);
                            dashboard.setFocusable(true);
                            dashboard.setAlwaysOnTop(true);
                        }
                }else {
                    this.setAlwaysOnTop(false);
                    JOptionPane.showMessageDialog(null, "Capacity should not be less than or equal to zero.");
                }
            }catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Capacity should be a number.");
            } catch (Exception err) {
                System.out.println("System Error");
            }

        });

        // Add All Sections to Main Panel
        mainHeader.add(headerPanel);
        mainContent.add(RoomDetailsWrapper);
        mainContent.add(assignPatientsWrapper);

        mainContent.add(addRoomBttnPanel);
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

        SetDefaultFont.setFontForAllLabels(mainContent, Constants.DEFAULT_FONT);
        Header.setFont(Constants.HEADING_FONT);
        setOnChangeEvent(this, roomModel);
        new SetFocusListenerToJTextFields(this);
        setJTextFieldPadding(this);
        scrollPane.setHorizontalScrollBar(null);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                editRoomBttn.requestFocusInWindow();
            }
        });


    }

    public void assignPatientsToRoom(String name, String id) {
        try {
            if (!PatientsIDArray.contains(id) && !PatientsNameArray.contains(name)) {
                if (Integer.parseInt(roomCapacity.getText()) <= 0) {
                    this.setAlwaysOnTop(false);
                    JOptionPane.showMessageDialog(null, "Capacity should not be less than or equal to zero.");
                } else if (PatientsNameArray.size() == Integer.parseInt(roomCapacity.getText())) {
                    this.setAlwaysOnTop(false);
                    JOptionPane.showMessageDialog(null, "Maximum capacity reached.");
                } else {
                    PatientsIDArray.add(id);
                    PatientsNameArray.add(name);
                    updateAssignedPatients();
                }
            } else {
                this.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "Patient already added.");
            }
        } catch (NumberFormatException err) {
            this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Capacity should be a number.");
        }
    }

    public void updateAssignedPatients() {
        toBeAssignedPanel.removeAll();
        for (int i = 0; i <= PatientsNameArray.size() - 1; i++) {
            JPanel toBeAssignedItem = new JPanel();
            toBeAssignedItem.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel PatientName = new JLabel("Patient Name: " + PatientsNameArray.get(i));
            PatientName.setPreferredSize(new Dimension(400, 50));
            PatientName.setMaximumSize(new Dimension(400, 50));
            PatientName.setBorder(new EmptyBorder(0, 11, 0, 11));
            JLabel PatientID = new JLabel("Patient ID: " + PatientsIDArray.get(i));
            PatientID.setPreferredSize(new Dimension(250, 50));
            PatientID.setMaximumSize(new Dimension(250, 50));
            PatientID.setForeground(Constants.primary);

            JLabel removeBttn = new JLabel("X");
            removeBttn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            int index = i;
            removeBttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PatientsNameArray.remove(index);
                    PatientsIDArray.remove(index);
                    updateAssignedPatients();
                    repaint();
                    revalidate();
                }
            });
            toBeAssignedItem.add(PatientID);
            toBeAssignedItem.add(PatientName);
            toBeAssignedItem.add(removeBttn);
            toBeAssignedPanel.add(toBeAssignedItem);
            PatientName.setFont(Constants.DEFAULT_FONT);
            PatientID.setFont(new Font("Arial", Font.BOLD, 18));
            removeBttn.setFont(new Font("Arial", Font.BOLD ,18));
            revalidate();
            repaint();
        }
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
                PatientItem item = new PatientItem(patient, 780, addRoomView);
                patientListPanel.add(item);
                patientListPanel.add(Box.createVerticalStrut(20));
                item.revalidate();
                item.repaint();
            }
        }
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
                                    PatientItem item = new PatientItem(patient, 780, addRoomView);
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
                        } else if (source == roomCapacity) {
                            try {
                                int roomCap = Integer.parseInt(text);
                                if (!(roomCap <= 0)) {
                                    if (roomCap < PatientsNameArray.size()) {
                                        for (int i = PatientsNameArray.size(); i > roomCap; i--) {
                                            PatientsNameArray.remove(i - 1);
                                            PatientsIDArray.remove(i - 1);
                                            updateAssignedPatients();
                                        }
                                    }
                                }

                            }catch (NumberFormatException err) {

                            }
                        }
                    }});
            } else if (component instanceof Container) {
                setOnChangeEvent((Container) component, model);
            }
        }
    }


}
