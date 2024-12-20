package mvc.views;

import mvc.controllers.AddPatientController;
import mvc.models.PatientModel;
import mvc.views.constants.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Dashboard extends JFrame implements ActionListener  {
    String role;

    JButton roombtn,patientsbtn,medicalstaffBtn,medicalrecordBtn, adminBttn;
    public Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);
    CardLayout cl1 = new CardLayout();
    JPanel container = new JPanel(cl1);
    RoomView roomView = new RoomView(this, new PatientModel());

    public Dashboard(String userRole) {
        this.role = userRole;
        initComponents();
    }

    public void initComponents() {
        String userRole = role;

        JPanel header = new JPanel();
        JPanel buttons = new JPanel();

        JLabel appName = new JLabel("HealthSync");
        appName.setForeground(Color.white);
        JTextField searchField = new RoundJTextField("Search", 30);
        JLabel patientCount = new JLabel("Patient Count: ");
        JLabel logoutBtn = new JLabel("Logout");

        //Jframe setup
        setVisible(true);
        setSize(1440, 724);
        setTitle("PatientManagementSystem");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //ADD HEADER
        header.setBackground(Constants.primary);
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        buttons.setBackground(Constants.primary);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));



        appName.setBorder(new EmptyBorder(30, 50, 30, 100));
        appName.setFont(new Font("Arial", Font.BOLD, 32));
        header.add(appName);


        searchField.setMaximumSize(new Dimension(250, 35));
        searchField.setMargin(new Insets(0,10,0,10));
        searchField.addFocusListener(new searchFieldClicked(searchField));
        searchField.setFont(DEFAULT_FONT);
        header.add(searchField);

        patientCount.setFont(DEFAULT_FONT);
        patientCount.setForeground(Color.white);
        patientCount.setBorder(new EmptyBorder(0, 50, 0, 430));
        header.add(patientCount);
        header.add(logoutBtn);
        logoutBtn.setFont(DEFAULT_FONT);
        logoutBtn.setForeground(Color.white);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setAlwaysOnTop(false);
                int choice = JOptionPane.showConfirmDialog(null, "Confirm?",
                        "Logout", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    new LogIn();
                    dispose();

                }
            }

        });

        // Buttons
         roombtn = new JButton("Rooms");
         patientsbtn= new JButton("Patients");
         medicalstaffBtn = new JButton("Medical Staffs");
         adminBttn = new JButton("Admins");
        roombtn.setFocusPainted(false);
        roombtn.setBorderPainted(false);
        patientsbtn.setFocusPainted(false);
        patientsbtn.setBorderPainted(false);
        medicalstaffBtn.setFocusPainted(false);
        medicalstaffBtn.setBorderPainted(false);
        adminBttn.setFocusPainted(false);
        adminBttn.setBorderPainted(false);

        roombtn.setFont(new Font("Arial", Font.PLAIN, 20));
        patientsbtn.setFont(new Font("Arial", Font.PLAIN, 20));
        medicalstaffBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        adminBttn.setFont(new Font("Arial", Font.PLAIN, 20));

        //Button sizes
        roombtn.setPreferredSize(new Dimension(200, 50));
        roombtn.setBorder(new EmptyBorder(0, 47, 0, 0));
        patientsbtn.setPreferredSize(new Dimension(200,50));
        patientsbtn.setBorder(new EmptyBorder(0, 47, 0, 0));
        medicalstaffBtn.setPreferredSize(new Dimension(200,50));
        medicalstaffBtn.setBorder(new EmptyBorder(0, 47, 0, 0));
        adminBttn.setPreferredSize(new Dimension(200,50));
                adminBttn.setBorder(new EmptyBorder(0, 47, 0, 0));
        // clear button background
        roombtn.setOpaque(false);
        roombtn.setContentAreaFilled(false);
        roombtn.setBorderPainted(false);
        adminBttn.setBorderPainted(false);
        adminBttn.setContentAreaFilled(false);
        adminBttn.setOpaque(false);
        patientsbtn.setOpaque(false);
        patientsbtn.setContentAreaFilled(false);
        patientsbtn.setBorderPainted(false);

        medicalstaffBtn.setOpaque(false);
        medicalstaffBtn.setContentAreaFilled(false);
        medicalstaffBtn.setBorderPainted(false);

        roombtn.setHorizontalAlignment(SwingConstants.LEFT);
        patientsbtn.setHorizontalAlignment(SwingConstants.LEFT);
        medicalstaffBtn.setHorizontalAlignment(SwingConstants.LEFT);
        adminBttn.setHorizontalAlignment(SwingConstants.LEFT);


        // set text color
        roombtn.setForeground(Color.WHITE);
        patientsbtn.setForeground(Color.WHITE);
        medicalstaffBtn.setForeground(Color.WHITE);
        adminBttn.setForeground(Color.WHITE);
        buttons.setBackground(Constants.primary);
        // Action listener
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER , 50, 30));
        buttons.setPreferredSize(new Dimension(200,600));


        roombtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                roombtn.setBackground(Constants.hoverColor2);// Change background color on hover
                roombtn.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                roombtn.setOpaque(false);// Revert background color
                roombtn.setForeground(Color.WHITE);
            }

        });

        patientsbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                patientsbtn.setBackground(Constants.hoverColor2); // Change background color on hover
                patientsbtn.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                patientsbtn.setOpaque(false); // Revert background color
            }
        });

        medicalstaffBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                medicalstaffBtn.setBackground(Constants.hoverColor2); // Change background color on hover
                medicalstaffBtn.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                medicalstaffBtn.setOpaque(false); // Revert background color
                medicalstaffBtn.setForeground(Color.WHITE);
            }
        });

        adminBttn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                adminBttn.setBackground(Constants.hoverColor2); // Change background color on hover
                adminBttn.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                adminBttn.setOpaque(false); // Revert background color
                adminBttn.setForeground(Color.WHITE);
            }
        });

        // add buttons
        if (Objects.equals(userRole, "Staff")) {
            buttons.add(roombtn);
            buttons.add(patientsbtn);
        } else if (Objects.equals(userRole, "Admin")) {
            buttons.add(roombtn);
            buttons.add(patientsbtn);
            buttons.add(medicalstaffBtn);

        }else if (Objects.equals(userRole, "Superadmin")) {
            buttons.add(roombtn);
            buttons.add(patientsbtn);
            buttons.add(medicalstaffBtn);
            buttons.add(adminBttn);
        }
        MedicalStaffView medicalStaffList = new MedicalStaffView(this);
        PatientView patientView = new PatientView(this, roomView);
        AdminView adminView = new AdminView(this);


        //
        roombtn.addActionListener(this);
        adminBttn.addActionListener(this);
        medicalstaffBtn.addActionListener(this);
        patientsbtn.addActionListener(this);
        //container
        container.add(roomView, "roomView");
        container.add(medicalStaffList, "med");
        container.add(patientView, "patientView");
        container.add(adminView, "admin");

        //add all main panels to the JFrame
        add(header, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
        add(buttons, BorderLayout.WEST);

        revalidate();
        repaint();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == roombtn){
        cl1.show(container, "roomView");
        roomView.updateUI();
        roomView.repaint();
        roomView.revalidate();
        }
        if(e.getSource() == patientsbtn){
            cl1.show(container, "patientView");
        }
        if(e.getSource() == medicalstaffBtn){
            cl1.show(container, "med");
        }

        if (e.getSource() == adminBttn) {
            cl1.show(container, "admin");
        }

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
