package mvc.views;
import mvc.controllers.GetPatients;
import mvc.controllers.GetRooms;
import mvc.controllers.GetStaff;
import mvc.models.PatientModel;
import mvc.views.constants.Constants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.bson.Document;
import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.util.Objects;

public class Dashboard extends JFrame implements ActionListener  {
    String role;
    //default view because the room view is what you can see first when opening the app
    String currentView = "Room View";
    Dashboard dashboard = this;

    JButton roombtn,patientsbtn,medicalstaffBtn,medicalrecordBtn, adminBttn;
    public Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);
    CardLayout cl1 = new CardLayout();
    JPanel container = new JPanel(cl1);
    RoomView roomView = new RoomView(this, new PatientModel());
    MedicalStaffView medicalStaffList = new MedicalStaffView(this);
    PatientView patientView = new PatientView(this, roomView);
    AdminView adminView = new AdminView(this);
    public JTextField searchField;

    public Dashboard(String userRole) {
        this.role = userRole;
        initComponents();

    }

    static String count;
    static JLabel HeaderCount = new JLabel();
    public static void HeaderCount(String header, String count) {
        // Fetch the updated count
        HeaderCount.setText(header + count);
    }

    public void initComponents() {
        // Header Icon
        ImageIcon MainHdrP;
        Image resizedMainHdr;
        ImageIcon MainHdrIcon;
        int wid = 65;
        int hei = 65;
        MainHdrP = new ImageIcon("src/assets/images/JWWy8xWz-removebg-preview.png");
        resizedMainHdr = MainHdrP.getImage().getScaledInstance(wid,hei, Image.SCALE_SMOOTH);
        MainHdrIcon = new ImageIcon(resizedMainHdr);

        String userRole = role;
        HeaderCount("Room Count: ", String.valueOf(GetRooms.getRoomCount())); // Update the patient count dynamically

        JPanel header = new JPanel();
        JPanel buttons = new JPanel();

        JLabel appName = new JLabel("HealthSync",MainHdrIcon,JLabel.LEFT);
        appName.setForeground(Color.white);

        searchField = new RoundJTextField("Search", 8);

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
        searchField.setMinimumSize(new Dimension(250, 35));
        searchField.setMargin(new Insets(0,10,0,10));
        searchField.addFocusListener(new searchFieldClicked(searchField));
        searchField.setFont(DEFAULT_FONT);

        HeaderCount.setFont(DEFAULT_FONT);
        HeaderCount.setForeground(Color.white);
        HeaderCount.setBorder(new EmptyBorder(0, 50, 0, 420));
        HeaderCount.setMinimumSize(new Dimension(670, 90));
        HeaderCount.setMaximumSize(new Dimension(670, 90));

        header.add(searchField);
        header.add(HeaderCount);
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
        // icon paths
        ImageIcon roomPath, patientPath,staffPath,adminPath;
         roomPath = new ImageIcon("src/assets/images/hospital-bed.png");
        patientPath = new ImageIcon("src/assets/images/patient.png");
        staffPath = new ImageIcon("src/assets/images/surgeon.png");
        adminPath = new ImageIcon("src/assets/images/admin.png");


        int width = 35;
        int height = 35;

        // Resize the image to the desired width and height
        Image resizedRoom, resizedPatient,resizedStaff,resizedAdmin;
        resizedRoom = roomPath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        resizedStaff = staffPath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        resizedPatient = patientPath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        resizedAdmin = adminPath.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the resized image
        ImageIcon roomIcon, patientIcon,staffIcon,adminIcon;
        roomIcon = new ImageIcon(resizedRoom);
        patientIcon = new ImageIcon(resizedPatient);
        staffIcon = new ImageIcon(resizedStaff);
        adminIcon = new ImageIcon(resizedAdmin);


        // Buttons
         roombtn = new JButton("  Rooms",roomIcon);
         patientsbtn= new JButton("  Patients",patientIcon);
         medicalstaffBtn = new JButton("  Staffs", staffIcon);
         adminBttn = new JButton("  Admins",adminIcon);

         roombtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        patientsbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        medicalstaffBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminBttn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        roombtn.setFocusPainted(false);
        roombtn.setBorderPainted(false);
        patientsbtn.setFocusPainted(false);
        patientsbtn.setBorderPainted(false);
        medicalstaffBtn.setFocusPainted(false);
        medicalstaffBtn.setBorderPainted(false);
        adminBttn.setFocusPainted(false);
        adminBttn.setBorderPainted(false);
        roombtn.setFont(new Font("Arial", Font.PLAIN, 18));
        patientsbtn.setFont(new Font("Arial", Font.PLAIN, 18));
        medicalstaffBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        adminBttn.setFont(new Font("Arial", Font.PLAIN, 18));

        //Button sizes
        roombtn.setPreferredSize(new Dimension(200, 85));
        roombtn.setBorder(new EmptyBorder(0, 47, 0, 0));
        patientsbtn.setPreferredSize(new Dimension(200,85));
        patientsbtn.setBorder(new EmptyBorder(0, 47, 0, 0));
        medicalstaffBtn.setPreferredSize(new Dimension(200,85));
        medicalstaffBtn.setBorder(new EmptyBorder(0, 47, 0, 0));
        adminBttn.setPreferredSize(new Dimension(200,85));
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
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER , 50, 40));
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
        setOnChangeEvent(dashboard);

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == roombtn){
            currentView = "Room View";
        cl1.show(container, "roomView");
        roomView.updateUI();
        roomView.repaint();
        roomView.revalidate();
        String newCount = String.valueOf(GetRooms.getRoomCount());
        HeaderCount("Room Count: " , newCount);
        }
        if(e.getSource() == patientsbtn){
            currentView = "Patients View";
            cl1.show(container, "patientView");
            String newCount = String.valueOf(GetPatients.getPatientCount());
            HeaderCount("Patient Count: " , newCount);
        }
        if(e.getSource() == medicalstaffBtn){
            currentView = "Staff View";
            cl1.show(container, "med");
            String newCount = String.valueOf(GetStaff.getStaffCount());
            HeaderCount("Staff Count: " , newCount);
        }

        if (e.getSource() == adminBttn) {
            currentView = "Admin View";
            cl1.show(container, "admin");
            String newCount = String.valueOf(GetStaff.getAdminCount());
            HeaderCount("Admin Count: " , newCount);
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

    private void setOnChangeEvent(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {

                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        roomView.updateUI();
                        adminView.updateUI();
                        medicalStaffList.updateUI();
                        patientView.updateUI();
                    }
                });

                textField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        handleTextChange(textField);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleTextChange(textField);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        handleTextChange(textField);
                    }

                    private void handleTextChange(JTextField source){
                        if (source == searchField ) {
                            if (!source.getText().equals("Search") && !source.getText().equals("")) {

                            if (currentView.equals("Room View")) {
                                GetRooms getRooms = new GetRooms();
                                List<Document> rooms = getRooms.getRoomDataByInputtedText(source.getText());

                                if (rooms != null && !rooms.isEmpty()) {
                                    roomView.updateUI(rooms);
                                } else {
                                    roomView.updateUI(rooms);
                                }

                            } else if (currentView.equals("Patients View")) {
                                List<Document> patientList = GetPatients.filterPatientData(source.getText());
                                if (patientList != null && !patientList.isEmpty()) {
                                    patientView.updateUI(patientList);
                                } else {
                                    patientView.updateUI(patientList);
                                }

                            } else if (currentView.equals("Staff View")) {
                                List<Document> staffList = GetStaff.filterStaffData(source.getText());

                                if (staffList != null && !staffList.isEmpty()) {
                                    medicalStaffList.updateUI(staffList);
                                } else {
                                    medicalStaffList.updateUI(staffList);
                                }
                            }
                            else if (currentView.equals("Admin View")) {
                                GetStaff getStaff = new GetStaff();
                                List<Document> adminList = getStaff.filterAdminData(source.getText());
                                adminView.updateUI(adminList);

                                if (adminList != null && !adminList.isEmpty()) {
                                    adminView.updateUI(adminList);
                                } else {
                                    adminView.updateUI(adminList);
                                }
                            }
                            }
                        }
                    }
                });
            } else if (component instanceof Container) {
                setOnChangeEvent((Container) component);
            }
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
            g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
            super.paintComponent(g);

        }
        protected void paintBorder(Graphics g) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
        }
    }

}
