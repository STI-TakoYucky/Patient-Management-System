package mvc.views;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AddPatientView extends JFrame {
    public AddPatientView() {
        initComponents();
    }

    public JTextField patientNameFieldFN = new JTextField(30);
    public JTextField patientNameFieldLN = new JTextField(30);

    public void initComponents() {
        JPanel mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        JLabel addPatientHeader = new JLabel("Add Staff");
        addPatientHeader.setFont(Constants.HEADING_FONT);

        JLabel patientNameLabel = new JLabel("Patient Name");
        JPanel patientTextFieldPanel = new JPanel();

        patientTextFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        patientTextFieldPanel.setMaximumSize(new Dimension(1500, 60));
        patientTextFieldPanel.add(patientNameFieldFN);
        patientTextFieldPanel.add(patientNameFieldLN);

        JPanel datePanel = new JPanel();
        JLabel dateFieldLabel = new JLabel("Enter Date (YYYY-MM-DD):");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(dateChooser);

        JRadioButton option1 = new JRadioButton("Male");
        JRadioButton option2 = new JRadioButton("Female");
        JPanel genderPanel = new JPanel();

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);

        genderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(option1);
        genderPanel.add(option2);


        JButton addStaffButton = new JButton("Add Staff");

        //setup JFrame
        setVisible(true);
        setSize(900, 700);
        setLocationRelativeTo(null);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(addPatientHeader);
        mainPanel.add(patientNameLabel);
        mainPanel.add(patientTextFieldPanel);
        mainPanel.add(addStaffButton);
        mainPanel.add(dateFieldLabel);
        mainPanel.add(datePanel);
        mainPanel.add(genderPanel);
        add(scrollPane);

        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
    }
}
