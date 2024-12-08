package mvc.views.components;

import mvc.views.PatientView;
import org.bson.Document;
import mvc.views.constants.Constants;
import javax.swing.*;
import java.awt.*;



public class PatientItem extends CustomRoundedPanel {
    Document patientItem;

    public PatientItem(Document patientItem, PatientView patientView) {
        this.patientItem = patientItem;
        initComponents();
    }

    public void initComponents() {
        JLabel patientID = new JLabel("Patient ID: " + patientItem.getString("_id"));
        JLabel patientName = new JLabel(patientItem.getString("First Name") + " " + patientItem.getString("Middle Name") + " " + patientItem.getString("Last Name"));
        JLabel patientInfoBttn = new JLabel("Patient Info");
        JLabel editBttn = new JLabel("Edit Info");

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

        add(patientID, gbc);
        add(patientName, gbc);
        add(patientInfoBttn, gbc);
        add(editBttn, gbc);

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
