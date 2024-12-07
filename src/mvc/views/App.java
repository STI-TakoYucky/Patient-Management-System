package mvc.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame {
    JButton roombtn,patientsbtn,medicalstaffBtn,medicalrecordBtn,settingBtn;
     public Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);

    public App() {
        initComponents();
    }

    public void initComponents() {

        JPanel header = new JPanel();
        JPanel buttons = new JPanel();

        JLabel appName = new JLabel("App Name");
        JTextField searchField = new RoundJTextField("Search", 30);
        JLabel patientCount = new JLabel("Patient Count: ");

        //Jframe setup
        setVisible(true);
        setSize(1440, 924);
        setTitle("PatientManagementSystem");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //ADD HEADER
        header.setBackground(Color.gray);
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        buttons.setBackground(Color.gray);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));



        appName.setBorder(new EmptyBorder(40, 50, 40, 100));
        appName.setFont(new Font("Arial", Font.BOLD, 32));
        header.add(appName);

        searchField.setMaximumSize(new Dimension(250, 35));
        searchField.setMargin(new Insets(0,10,0,10));
        searchField.addFocusListener(new searchFieldClicked(searchField));
        searchField.setFont(DEFAULT_FONT);
        header.add(searchField);

        patientCount.setFont(DEFAULT_FONT);
        patientCount.setBorder(new EmptyBorder(0, 50, 0, 0));
        header.add(patientCount);
        // Buttons
         roombtn = new JButton("Rooms");
         patientsbtn= new JButton("Patients");
         medicalstaffBtn = new JButton("Medical Staffs");
         medicalrecordBtn = new JButton("Medical Records");
         settingBtn = new JButton("Settings");

        //Button sizes
        roombtn.setPreferredSize(new Dimension(150, 50));
        settingBtn.setPreferredSize(new Dimension(150, 50));
        patientsbtn.setPreferredSize(new Dimension(150,50));
        medicalstaffBtn.setPreferredSize(new Dimension(150,50));
        medicalrecordBtn.setPreferredSize(new Dimension(150,50));
        // Action listener


        buttons.setLayout(new FlowLayout(FlowLayout.CENTER , 20, 20));
        buttons.setPreferredSize(new Dimension(200,600));
        // add buttons
        buttons.add(roombtn);
        buttons.add(patientsbtn);
        buttons.add(medicalstaffBtn);
        buttons.add(settingBtn);
        buttons.add(medicalrecordBtn);


  MedicalStaffView medicalStaffList = new MedicalStaffView();

        //add all main panels to the JFrame
        add(header, BorderLayout.NORTH);
        add(medicalStaffList, BorderLayout.CENTER);
        add(buttons, BorderLayout.WEST);

        revalidate();
        repaint();


    }

    public class searchFieldClicked implements FocusListener {

        JTextField searchField;

        public searchFieldClicked (JTextField searchField) {
            this.searchField = searchField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            searchField.setText("");
        }

        @Override
        public void focusLost(FocusEvent e) {
            searchField.setText("Search");
        }
    }


    //To create a custom textField with rounded borders
    class RoundJTextField extends JTextField {

        private Shape shape;
        private int Size;
        private String Text;
        public RoundJTextField(String text, int size) {
            super(size);
            this.Size = size;
            setOpaque(false);
            this.Text = text;
            setText(Text);
        }

        @Override
        public void setText(String t) {
            super.setText(t);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, Size, Size);
            super.paintComponent(g);
        }
        protected void paintBorder(Graphics g) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, Size, Size);
        }
    }

}
