package mvc.views;

import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame implements ActionListener {
    private static JButton logInbtn;
    JTextField text, LogPassword;

    public LogIn (){ LogInComponents();}
    public void LogInComponents(){

        JFrame Login = new JFrame();
        JPanel logPanel = new JPanel();
        logInbtn = new JButton("Log In");
        logInbtn.setSize(20,10);
        logInbtn.addActionListener(this);


        JPanel Logheader = new JPanel();
        JLabel headerName= new JLabel("Log In");
        headerName.setBorder(new EmptyBorder(40, 150, 40, 100));
        headerName.setFont(new Font("Arial",Font.BOLD, 20));
        Logheader.add(headerName);
        Logheader.setLayout(new BoxLayout(Logheader, BoxLayout.X_AXIS));
        Logheader.setBackground(Constants.primary);


         text = new JTextField(20);
        JLabel userLabel = new JLabel("Enter username: ");
         LogPassword = new JTextField(20);
        JLabel PasswordLabel = new JLabel("Enter password: ");
        logPanel.setBackground(Color.WHITE);
        logPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 25, 25));


        logPanel.add(userLabel);
        logPanel.add(text);
        logPanel.add(PasswordLabel);
        logPanel.add(LogPassword);
        logPanel.add(logInbtn);
        Login.add(logPanel);

        Login.add(Logheader, BorderLayout.NORTH);
        SetDefaultFont.setFontForAllLabels(this,Constants.DEFAULT_FONT);
        Login.setSize(400, 400);
        Login.setLocationRelativeTo(null);
        Login.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Login.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = "celine";
        if(e.getSource()==logInbtn){
            if(text.getText().equals(name)&& LogPassword.getText().equals(name)){
                this.setEnabled(false);
            new App();
            }
            else if(!text.getText().equals(name)&& !LogPassword.getText().equals(name)){

                JOptionPane.showMessageDialog(this,"Invalid input");
            }

        }
    }
}


