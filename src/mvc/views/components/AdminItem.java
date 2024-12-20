package mvc.views.components;

import mvc.models.RoomModel;
import mvc.models.StaffModel;
import mvc.views.*;
import org.bson.Document;
import mvc.views.constants.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdminItem extends CustomRoundedPanel {
    AdminView view;
    public AdminItem(Document staffList, AdminView view, Dashboard dashboard) {
        this.view = view;
        JLabel staffID = new JLabel("Admin ID: " + staffList.getString("_id"));
        JLabel staffName = new JLabel(staffList.getString("First Name") + " " +  staffList.getString("Last Name"));
        JLabel staffPosition = new JLabel(staffList.getString("Position"));
        JLabel editStaffItemButton = new JLabel("Edit Info");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 50, 5, 0);
        gbc.fill = GridBagConstraints.BOTH; // Fill the cell both horizontally and vertically
        gbc.weightx = 1;  // Evenly distribute horizontally
        gbc.weighty = 1;

        setBackground(Constants.primary);
        setMaximumSize(new Dimension(1090, 50));
        setPreferredSize(new Dimension(1090, 50));
        setLayout(new GridBagLayout());

        editStaffItemButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editStaffItemButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.setEnabled(false);
                dashboard.setFocusable(false);
                dashboard.setAlwaysOnTop(false);
                new EditAdminView(staffList.getString("_id"), new StaffModel(), view, dashboard, staffList);
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
                component.setForeground(Color.white);
            } else if (component instanceof Container) {
                fixedJLabel((Container) component);
            }
        }
    }

}
