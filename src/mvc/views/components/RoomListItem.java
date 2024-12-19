package mvc.views.components;
import mvc.controllers.GetPatients;
import mvc.models.PatientModel;
import mvc.models.RoomModel;
import mvc.views.Dashboard;
import mvc.views.EditRoomView;
import mvc.views.RoomView;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RoomListItem extends CustomRoundedPanel {
    int width = 1060;
    Document roomItem;
    RoomView roomView;
    Dashboard dashboard;
    PatientModel patientModel;
    public RoomListItem(Document room, RoomView roomView, Dashboard dashboard, PatientModel patientModel) {
        this.roomItem = room;
        this.patientModel = patientModel;
        this.roomView = roomView;
        this.dashboard = dashboard;
        initComponents();
    }
    JPanel roomHeaderWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel roomMainContentPanel = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();


    public void initComponents() {
        Map<String, String> patientMap = (Map<String, String>) roomItem.get("Patients");
        int patientMapSize = patientMap.size();


        setLayout(new BorderLayout());
        gbc.insets = new Insets(0, 12, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel roomHeaderPanel = new CustomRoundedPanel();
        roomHeaderPanel.setPreferredSize(new Dimension(1060, 50));
        roomHeaderPanel.setLayout(new GridBagLayout());
        roomHeaderPanel.setBackground(Constants.secondary);
        JLabel roomName = new JLabel(roomItem.getString("Room Name"));
        JLabel roomCapacity = new JLabel(String.valueOf(patientMapSize + "/" + roomItem.getInteger("Room Capacity")));
        JLabel roomType = new JLabel(roomItem.getString("Room Type"));
        JLabel editBttn = new JLabel("Edit room");
        editBttn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        editBttn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.setEnabled(false);
                dashboard.setAlwaysOnTop(false);
                dashboard.setFocusable(false);
                new EditRoomView(roomItem.getString("_id"), new RoomModel(), roomView, dashboard, patientModel);
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
        roomHeaderWrapper.setOpaque(false);
        roomHeaderPanel.setOpaque(false);

        roomMainContentPanel.setLayout(new BoxLayout(roomMainContentPanel, BoxLayout.Y_AXIS));


        createPatientListItem(patientMap);

        roomMainContentPanel.setOpaque(false);

        add(roomHeaderWrapper, BorderLayout.NORTH);
        add(roomMainContentPanel, BorderLayout.CENTER);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        roomName.setFont(new Font("Arial", Font.BOLD, 20));
    }



    public void createPatientListItem(Map<String, String> patientMap) {
        if (patientMap != null) {
            for (Map.Entry<String, String> entry : patientMap.entrySet()) {
            JPanel patientItemPanel = new CustomRoundedPanel();
            patientItemPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
            patientItemPanel.setLayout(new GridBagLayout());
            JLabel patientName = new JLabel("Patient Name: " + entry.getValue());
            JLabel assignedStaff = new JLabel("Assigned Staff");
            JLabel medicalRecords = new JLabel("Medical Records");
            JLabel confinedDate = new JLabel("Confined Since: " + GetPatients.getPatientAdmissionDate(entry.getKey()));

                gbc.weightx = 1;
                gbc.weighty = 1;
                gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
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
            }
        }
    }

    public void fixedJLabel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setPreferredSize(new Dimension(500, 30));  // Adjusted size
                label.setMaximumSize(new Dimension(500, 30));    // Adjusted size
                label.setForeground(Color.white);                // Set text color
                label.setFont(new Font("Arial", Font.PLAIN, 15)); // Set font
            } else if (component instanceof Container) {
                fixedJLabel((Container) component);  // Recursive call for nested containers
            }
        }
    }

    public void fixedHeaderJLabel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setPreferredSize(new Dimension(150, 60));  // Adjusted size
                label.setMaximumSize(new Dimension(150, 60));    // Adjusted size
                label.setFont(new Font("Arial", Font.PLAIN, 15)); // Set font
            } else if (component instanceof Container) {
                fixedJLabel((Container) component);  // Recursive call for nested containers
            }
        }
    }

}
