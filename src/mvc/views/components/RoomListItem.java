package mvc.views.components;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;

import javax.swing.*;
import java.awt.*;

public class RoomListItem extends CustomRoundedPanel {
    public RoomListItem() {
        initComponents();
    }

    public void initComponents() {
        JLabel roomNumber = new JLabel("Room 231");
        JLabel patientName = new JLabel("Patient Name: Lucky Estrada");
        JLabel assignedStaff = new JLabel("Assigned Staff");
        JLabel medicalRecords = new JLabel("Medical Records");
        JLabel confinedDate = new JLabel("Confined Since: November 11, 2024");
        JLabel diagnosis = new JLabel("Diagnosis");

        //setup Jpanel
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(900, 130));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 20, 5, 20);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        add(roomNumber, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(patientName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(confinedDate, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(assignedStaff, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(medicalRecords, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        add(diagnosis, gbc);


        //set the default font for all the Labels
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);

        //set Custom Font for the room number label
        roomNumber.setFont(new Font("Arial", Font.BOLD, 24));

    }
}
