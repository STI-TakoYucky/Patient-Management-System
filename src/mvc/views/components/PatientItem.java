package mvc.views.components;

import mvc.controllers.GetAssignedPatients;
import mvc.models.PatientModel;
import mvc.views.*;
import mvc.views.utility.SetDefaultFont;
import org.bson.Document;
import mvc.views.constants.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class PatientItem extends CustomRoundedPanel {
    Document patientItem;
    String patientID;
    PatientView patientView;
    Dashboard dashboard;
    AddRoomView addRoomView;
    EditRoomView editRoomView;
    int width = 1090;


    JLabel patientIDLabel, patientName;

    public PatientItem(Document patientItem, int width, AddRoomView addRoomView) {
        this.addRoomView = addRoomView;
        this.width = width;
        this.patientItem = patientItem;
        this.patientID = patientItem.getString("_id");
        patientIDLabel = new JLabel("Patient ID: " + patientItem.getString("_id"));
        patientName = new JLabel(patientItem.getString("First Name") + " " + patientItem.getString("Middle Name") + " " + patientItem.getString("Last Name"));
        initSearchPatientComponent();
    }

    public PatientItem(Document patientItem, int width, EditRoomView editRoomView) {
        this.editRoomView = editRoomView;
        this.width = width;
        this.patientItem = patientItem;
        this.patientID = patientItem.getString("_id");
        patientIDLabel = new JLabel("Patient ID: " + patientItem.getString("_id"));
        patientName = new JLabel(patientItem.getString("First Name") + " " + patientItem.getString("Middle Name") + " " + patientItem.getString("Last Name"));
        initSearchPatientComponentForEditRoom();
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

    public void initSearchPatientComponent() {
        // Check if patient is already assigned
        if (addRoomView.isPatientAssigned(patientID)) {
            System.out.println("Patient with ID " + patientID + " is already assigned.");
            return;
        }

        JLabel assignBttn = new JLabel("Assign");

        assignBttn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        assignBttn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addRoomView.assignPatientsToRoom(patientItem.getString("First Name") + " " + patientItem.getString("Middle Name") + " " + patientItem.getString("Last Name"), patientID);
                addRoomView.revalidate();
                addRoomView.repaint();
            }
        });

        setupComponentLayout(assignBttn);
    }

    public void initSearchPatientComponentForEditRoom() {
        // Check if patient is already assigned
        if (editRoomView.isPatientAssigned(patientID)) {
            System.out.println("Patient with ID " + patientID + " is already assigned.");
            return;
        }

        JLabel assignBttn = new JLabel("Assign");

        assignBttn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        assignBttn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editRoomView.assignPatientsToRoom(patientItem.getString("First Name") + " " + patientItem.getString("Middle Name") + " " + patientItem.getString("Last Name"), patientID);
                editRoomView.revalidate();
                editRoomView.repaint();
            }
        });

        setupComponentLayout(assignBttn);
    }

    private void setupComponentLayout(JLabel assignBttn) {
        setLayout(new GridBagLayout());
        GridBagConstraints itemgbc = new GridBagConstraints();
        itemgbc.anchor = GridBagConstraints.WEST;
        itemgbc.insets = new Insets(5, 50, 5, 0);
        itemgbc.fill = GridBagConstraints.BOTH;
        itemgbc.weightx = 1;
        itemgbc.weighty = 1;
        setBackground(Constants.primary);
        setMaximumSize(new Dimension(width, 45));
        setPreferredSize(new Dimension(width, 45));

        add(patientIDLabel, itemgbc);
        add(patientName, itemgbc);
        add(assignBttn, itemgbc);

        fixedJLabel(this);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
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


    public void initComponents() {

        JLabel patientInfoBttn = new JLabel("Medical Records");
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
}
