package mvc.views.components;

import mvc.models.PatientModel;
import mvc.views.Dashboard;
import mvc.views.EditPatientView;
import mvc.views.EditStaffView;
import mvc.views.PatientView;
import org.bson.Document;
import mvc.views.constants.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PatientItem extends CustomRoundedPanel {
    Document patientItem;
    String patientID;
    PatientView patientView;
    Dashboard dashboard;
    int width = 1090;

    JLabel patientIDLabel, patientName;

    public PatientItem(Document patientItem, int width) {
        this.width = width;
        patientIDLabel = new JLabel("Patient ID: " + patientItem.getString("_id"));
        patientName = new JLabel(patientItem.getString("First Name") + " " + patientItem.getString("Middle Name") + " " + patientItem.getString("Last Name"));
        initSearchPatientComponent();
    }

    public PatientItem(Document patientItem, PatientView patientView, Dashboard dashboard) {
        this.patientItem = patientItem;
        this.patientView = patientView;
        this.dashboard = dashboard;
        this.patientID = patientItem.getString("_id");
        patientIDLabel = new JLabel("Patient ID: " + patientItem.getString("_id"));
        patientName = new JLabel(patientItem.getString("First Name") + " " + patientItem.getString("Middle Name") + " " + patientItem.getString("Last Name"));
        initComponents();
    }

    public void initComponents() {

        JLabel patientInfoBttn = new JLabel("Patient Info");
        JLabel editBttn = new JLabel("Edit Info");

        setLayout(new GridBagLayout());
        GridBagConstraints itemgbc = new GridBagConstraints();
        itemgbc.anchor = GridBagConstraints.WEST;
        itemgbc.insets = new Insets(5, 50, 5, 0);
        itemgbc.fill = GridBagConstraints.BOTH; // Fill the cell both horizontally and vertically
        itemgbc.weightx = 1;  // Evenly distribute horizontally
        itemgbc.weighty = 1;
        setBackground(Constants.primary);
        setMaximumSize(new Dimension(width, 50));
        setPreferredSize(new Dimension(width, 50));
        setLayout(new GridBagLayout());

        add(patientIDLabel, itemgbc);
        add(patientName, itemgbc);
        add(patientInfoBttn, itemgbc);
        add(editBttn, itemgbc);

        fixedJLabel(this);

        editBttn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editBttn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.setEnabled(false);
                dashboard.setFocusable(false);
                dashboard.setAlwaysOnTop(false);
                new EditPatientView(patientID, new PatientModel(), patientView, dashboard);
                System.out.println(patientID);

            }
        });
    }

    public void initSearchPatientComponent() {
        JLabel assignBttn = new JLabel("Assign");

        setLayout(new GridBagLayout());
        GridBagConstraints itemgbc = new GridBagConstraints();
        itemgbc.anchor = GridBagConstraints.WEST;
        itemgbc.insets = new Insets(5, 50, 5, 0);
        itemgbc.fill = GridBagConstraints.BOTH; // Fill the cell both horizontally and vertically
        itemgbc.weightx = 1;  // Evenly distribute horizontally
        itemgbc.weighty = 1;
        setBackground(Constants.primary);
        setMaximumSize(new Dimension(width, 45));
        setPreferredSize(new Dimension(width, 45));
        setLayout(new GridBagLayout());

        add(patientIDLabel, itemgbc);
        add(patientName, itemgbc);
        add(assignBttn, itemgbc);

        fixedJLabel(this);
    }

    public void fixedJLabel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                component.setPreferredSize(new Dimension(200, 30));
                component.setForeground(Color.white);
                component.setFont(new Font("Arial", Font.PLAIN, 15));
            } else if (component instanceof Container) {
                fixedJLabel((Container) component);
            }
        }
    }
}
