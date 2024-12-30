package mvc.views;

import mvc.controllers.GetPatients;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AssignedStaffView extends JFrame {
    Dashboard dashboard;
    String patientID;
    public AssignedStaffView(Dashboard dashboard, String patientID) {
        this.patientID = patientID;
        this.dashboard = dashboard;
        initComponents();
    }

    ImageIcon closeButtonIcon = new ImageIcon(getClass().getResource("/src/assets/images/x-icon.png"));
    Image image = closeButtonIcon.getImage();
    Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon resizedCloseButtonIcon = new ImageIcon(resizedImage);

    // Create a JLabel with the PNG image
    JLabel closeButton = new JLabel(resizedCloseButtonIcon);

    private void initComponents() {

        // Header Icon
        ImageIcon EPHedP;
        Image resizedEPHed;
        ImageIcon EPHedIcon;
        int wid = 45;
        int hei = 45;
        EPHedP = new ImageIcon("src/assets/images/surgeon.png");
        resizedEPHed = EPHedP.getImage().getScaledInstance(wid,hei, Image.SCALE_SMOOTH);
        EPHedIcon = new ImageIcon(resizedEPHed);

        // Header Panel
        JPanel mainHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainHeader.setBackground(Constants.secondary);
        mainHeader.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel Header = new JLabel("  Assigned Staff",EPHedIcon, JLabel.LEFT);
        Header.setAlignmentX(Component.LEFT_ALIGNMENT);
        Header.setBorder(new EmptyBorder(0, 0, 0, 220));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                dashboard.setEnabled(true);
                dashboard.setFocusable(true);
                dashboard.setAlwaysOnTop(true);
            }
        });

        mainHeader.add(Header);
        mainHeader.add(closeButton);


        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);


        // Content Panel for staff list
        JPanel staffListPanel = new JPanel();
        staffListPanel.setLayout(new BoxLayout(staffListPanel, BoxLayout.Y_AXIS));
        staffListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        staffListPanel.setBackground(Color.WHITE);

        GetPatients patients = new GetPatients();
        Document patientDocument = patients.getPatientDataById(patientID);

        if (patientDocument.getString("Assigned Staff").equals("Select Medical Staff")) {
            JLabel noStaffLabel = new JLabel("No staff assigned.");
            noStaffLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            noStaffLabel.setForeground(Color.GRAY);
            staffListPanel.add(noStaffLabel);
        } else {
                JLabel staffLabel = new JLabel(patientDocument.getString("Assigned Staff"));
                staffLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                staffLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
                staffListPanel.add(staffLabel);
        }

        // Wrap staff list in a scroll pane
        JScrollPane scrollPane = new JScrollPane(staffListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add panels to the frame
        mainPanel.add(mainHeader, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);

        setAlwaysOnTop(true);
        setTitle("Assigned Staff");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }
}
