package mvc.views.components;

import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MedicalStaffItem extends CustomRoundedPanel {
    public MedicalStaffItem(Document staff) {
        JLabel staffID = new JLabel("a");
        JLabel staffName = new JLabel("Staff Name: " + staff.getString("First Name") + staff.getString("Last Name"));
        JLabel staffPosition = new JLabel("Staff Position: " + staff.getString("Position"));
        JLabel editStaffItemButton = new JLabel("Edit Info");

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(900, 90));
        setLayout(new GridBagLayout());

        add(staffID);
        add(staffName);
        add(staffPosition);
        add(editStaffItemButton);

    }
}
