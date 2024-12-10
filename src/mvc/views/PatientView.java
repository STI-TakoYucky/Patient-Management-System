package mvc.views;

import mvc.controllers.GetPatients;
import mvc.controllers.GetStaff;
import mvc.models.PatientModel;
import mvc.views.components.MedicalStaffItem;
import mvc.views.components.PatientItem;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientView extends Panel {

    PatientView patientView = this;
    Dashboard dashboard;

    public PatientView(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
    }
    JPanel patientListPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(patientListPanel);
    JPanel addPatientPanel = new JPanel();
    JButton addPatientBttn = new JButton("Add New Patient");

    public void initComponents() {
        setLayout(new BorderLayout());

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 20, 5, 20);

        addPatientBttn.addActionListener(new addPatientBttn());

        addPatientPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addPatientPanel.setBorder(new EmptyBorder(30,80,0,0));
        addPatientPanel.add(addPatientBttn);

        add(addPatientPanel, BorderLayout.NORTH);

        patientListPanel.setLayout(new BoxLayout(patientListPanel, BoxLayout.Y_AXIS));
        patientListPanel.setBorder(new EmptyBorder(30,80,80,80));

        updateUI();

        add(scrollPane, BorderLayout.CENTER);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
    }

    public void updateUI(){
        patientListPanel.removeAll();
        GetPatients getPatients = new GetPatients();
        List<Document> patientList = getPatients.getPatientData();

        if (patientList == null) {
            JLabel noPatient = new JLabel("No Patients Yet");
            patientListPanel.add(noPatient);
        } else {
            for (Document patient : patientList) {
                PatientItem item = new PatientItem(patient, patientView);
                patientListPanel.add(item);
                patientListPanel.add(Box.createVerticalStrut(20));
                item.revalidate();
                item.repaint();
            }
        }
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
    }

    class addPatientBttn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dashboard.setEnabled(false);
            dashboard.setFocusable(false);
            new AddPatientView(new PatientModel(), patientView, dashboard);
        }
    }

}
