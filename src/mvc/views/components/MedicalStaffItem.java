package mvc.views.components;

import mvc.views.EditStaffView;
import mvc.views.MedicalStaffView;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MedicalStaffItem extends CustomRoundedPanel {
    MedicalStaffView view;
    public MedicalStaffItem(Document staff, MedicalStaffView view) {
        this.view = view;
        JLabel staffID = new JLabel(String.valueOf(staff.getObjectId("_id")));
        JLabel staffName = new JLabel(staff.getString("First Name") + " " +  staff.getString("Last Name"));
        JLabel staffPosition = new JLabel(staff.getString("Position"));
        JLabel editStaffItemButton = new JLabel("Edit Info");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 50, 5, 0);
        gbc.fill = GridBagConstraints.BOTH; // Fill the cell both horizontally and vertically
        gbc.weightx = 1;  // Evenly distribute horizontally
        gbc.weighty = 1;

        setBackground(Color.LIGHT_GRAY);
        setMaximumSize(new Dimension(1255, 220));
        setPreferredSize(new Dimension(1255, 90));
        setLayout(new GridBagLayout());
        JFrame frame = new JFrame("JLabel Click Event Example");

        editStaffItemButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editStaffItemButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ObjectId id = staff.getObjectId("_id");
                String firstName = staff.getString("First Name");
                String lastName = staff.getString("Last Name");
                String position = staff.getString("Position");
                new EditStaffView(id, firstName, lastName, position, view);
            }
        });

        add(staffID, gbc);
        add(staffName, gbc);
        add(staffPosition,gbc );
        add(editStaffItemButton, gbc);

        fixedJLabel(this);
    }

    public void fixedJLabel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                component.setPreferredSize(new Dimension(200, 30));
            } else if (component instanceof Container) {
                fixedJLabel((Container) component);
            }
        }
    }

}
