package mvc.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class App extends JFrame {

    public Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);

    public App() {
        initComponents();
    }

    public void initComponents() {
        JPanel header = new JPanel();
        JLabel appName = new JLabel("App Name");
        JTextField searchField = new RoundJTextField("Search", 30);
        JLabel patientCount = new JLabel("Patient Count: ");

        //Jframe setup
        setVisible(true);
        setSize(1440, 924);
        setTitle("PatientManagementSystem");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //add header
        header.setBackground(Color.gray);
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        appName.setBorder(new EmptyBorder(40, 50, 40, 100));
        appName.setFont(new Font("Arial", Font.BOLD, 32));
        header.add(appName);

        searchField.setMaximumSize(new java.awt.Dimension(250, 35));
        searchField.setMargin(new Insets(0,10,0,10));

        searchField.setFont(DEFAULT_FONT);
        header.add(searchField);

        patientCount.setFont(DEFAULT_FONT);
        patientCount.setBorder(new EmptyBorder(0, 50, 0, 0));
        header.add(patientCount);
        add(header, BorderLayout.NORTH);
    }

    public class RoundJTextField extends JTextField {

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
