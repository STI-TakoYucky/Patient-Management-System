package mvc.views.components;
import mvc.controllers.GetPatients;
import mvc.models.RoomModel;
import mvc.views.Dashboard;
import mvc.views.EditRoomView;
import mvc.views.MedicalRecordsView;
import mvc.views.RoomView;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import org.bson.Document;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class RoomListItem extends CustomRoundedPanel {
    int width = 1060;
    Document roomItem;
    RoomView roomView;
    Dashboard dashboard;

    public RoomListItem(Document room, RoomView roomView, Dashboard dashboard) {
        this.roomItem = room;
        this.roomView = roomView;
        this.dashboard = dashboard;
        initComponents();
    }
    JPanel roomHeaderWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel roomMainContentPanel = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();


    public void initComponents() {
        ImageIcon roomPath, capacityPath,typePath,editPath;
        roomPath = new ImageIcon("src/assets/images/room.png");
        capacityPath = new ImageIcon("src/assets/images/capacity.png");
        typePath = new ImageIcon("src/assets/images/roomtype.png");
        editPath = new ImageIcon("src/assets/images/edit_black.png");

        int width = 35;
        int height = 35;

        // Resize the image to the desired width and height
        Image resizedRoom, resizedType,resizedCapacity,resizedEdit;
        resizedRoom = roomPath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        resizedCapacity = capacityPath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        resizedType = typePath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        resizedEdit = editPath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the resized image
        ImageIcon roomIcon, CapacityIcon,TypeIcon,EditIcon;
        roomIcon = new ImageIcon(resizedRoom);
        CapacityIcon = new ImageIcon(resizedCapacity);
        TypeIcon = new ImageIcon(resizedType);
        EditIcon = new ImageIcon(resizedEdit);

        Map<String, String> patientMap = (Map<String, String>) roomItem.get("Patients");
        int patientMapSize = patientMap.size();


        setLayout(new BorderLayout());
        gbc.insets = new Insets(0, 12, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel roomHeaderPanel = new CustomRoundedPanel();
        roomHeaderPanel.setPreferredSize(new Dimension(1060, 50));
        roomHeaderPanel.setLayout(new GridBagLayout());
        JLabel roomName = new JLabel(roomItem.getString("Room Name"),roomIcon,JLabel.LEFT);
        JLabel roomCapacity = new JLabel(String.valueOf(patientMapSize + "/" + roomItem.getInteger("Room Capacity")),CapacityIcon,JLabel.LEFT);
        JLabel roomType = new JLabel(roomItem.getString("Room Type"),TypeIcon,JLabel.LEFT);
        JLabel editBttn = new JLabel("Edit room",EditIcon,JLabel.LEFT);
        editBttn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        editBttn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.setEnabled(false);
                dashboard.setAlwaysOnTop(false);
                dashboard.setFocusable(false);
                new EditRoomView(roomItem.getString("_id"), new RoomModel(), roomView, dashboard);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 12, 0, 22);
        roomHeaderPanel.add(roomName, gbc);
        gbc.gridx = 1;
        roomHeaderPanel.add(roomCapacity, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        roomHeaderPanel.add(roomType, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        roomHeaderPanel.add(editBttn, gbc);
        fixedHeaderJLabel(roomHeaderPanel);

        roomHeaderWrapper.add(roomHeaderPanel);

        roomMainContentPanel.setLayout(new BoxLayout(roomMainContentPanel, BoxLayout.Y_AXIS));

        createPatientListItem(patientMap);

        add(roomHeaderWrapper, BorderLayout.NORTH);
        add(roomMainContentPanel, BorderLayout.CENTER);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);

        roomMainContentPanel.setOpaque(false);
        roomHeaderWrapper.setBackground(Constants.secondary);
        roomHeaderPanel.setBackground(Constants.secondary);
    }



    public void createPatientListItem(Map<String, String> patientMap) {
        System.out.println(patientMap);
        if (!patientMap.isEmpty()) {
            for (Map.Entry<String, String> entry : patientMap.entrySet()) {
            JPanel patientItemPanel = new JPanel();
            patientItemPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
            patientItemPanel.setLayout(new GridBagLayout());
            JLabel patientName = new JLabel("Patient Name: " + entry.getValue());
            JButton assignedStaff = new JButton("Assigned Staff");
                assignedStaff.setFocusPainted(false);
                assignedStaff.setBorderPainted(false);

                // Custom Painting
                assignedStaff.setContentAreaFilled(false);
                assignedStaff.setOpaque(true);
                assignedStaff.setBackground(Constants.primary);
                assignedStaff.setForeground(Color.WHITE);
                assignedStaff.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Add Hover Effect
                assignedStaff.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        assignedStaff.setBackground(Constants.hoverColor2);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        assignedStaff.setBackground(Constants.primary);
                        assignedStaff.setForeground(Color.white);
                    }
                });
                assignedStaff.setMaximumSize(new Dimension(199,55));
                assignedStaff.setPreferredSize(new Dimension(199,55));

            JButton medicalRecords = new JButton("Medical Records");
                medicalRecords.setFocusPainted(false);
                medicalRecords.setBorderPainted(false);

                // Custom Painting
                medicalRecords.setContentAreaFilled(false);
                medicalRecords.setOpaque(true);
                medicalRecords.setBackground(Constants.primary);
                medicalRecords.setForeground(Color.WHITE);
                medicalRecords.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Add Hover Effect
                medicalRecords.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        medicalRecords.setBackground(Constants.hoverColor2);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        medicalRecords.setBackground(Constants.primary);
                        medicalRecords.setForeground(Color.white);
                    }
                });
                medicalRecords.setMaximumSize(new Dimension(220,55));
                medicalRecords.setPreferredSize(new Dimension(220,55));
                JLabel confinedDate = new JLabel("Confined Since: " + GetPatients.getPatientAdmissionDate(entry.getKey()));

            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 29, 0, 0);

            gbc.gridx = 0;
            gbc.gridy = 0;
            patientItemPanel.add(patientName, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            patientItemPanel.add(confinedDate, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            patientItemPanel.add(assignedStaff, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            patientItemPanel.add(medicalRecords, gbc);


            roomMainContentPanel.add(patientItemPanel);
            patientItemPanel.setBackground(Constants.primary);

            //set the default font for all the Labels
            fixedJLabel(patientItemPanel);
            roomMainContentPanel.add(Box.createVerticalStrut(12));

            //show medical records
                medicalRecords.setCursor(new Cursor(Cursor.HAND_CURSOR));
                medicalRecords.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        new MedicalRecordsView(entry.getKey(), dashboard);
                    }
                });
            }
        } else {
            JLabel roomEmpty  = new JLabel("Room is empty.");
            JPanel patientItemPanel = new JPanel();
            patientItemPanel.setBackground(Constants.primary);
            patientItemPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
            patientItemPanel.setLayout(new GridBagLayout());
            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 29, 0, 0);
            patientItemPanel.add(roomEmpty, gbc);
            roomMainContentPanel.add(patientItemPanel);

            fixedJLabel(patientItemPanel);
            roomMainContentPanel.add(Box.createVerticalStrut(12));
        }
    }

    public void fixedJLabel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setBorder(new EmptyBorder(20,43,20,70));    // Adjusted size
                label.setForeground(Color.white);                // Set text color
                label.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
            } else if (component instanceof Container) {
                fixedJLabel((Container) component);  // Recursive call for nested containers
            }
        }
    }

    public void fixedHeaderJLabel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;

                label.setBorder(new EmptyBorder(20,70,20,70));    // Adjusted size
                label.setFont(new Font("Arial", Font.PLAIN, 15)); // Set font
            } else if (component instanceof Container) {
                fixedJLabel((Container) component);  // Recursive call for nested containers
            }
        }
    }

}
