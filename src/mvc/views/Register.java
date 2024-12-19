package mvc.views;

import mvc.views.Dashboard;
import mvc.views.LogIn;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import mvc.views.utility.SetFocusListenerToJTextFields;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame implements ActionListener {

    JButton registerBtn, backBtn;
    CardLayout cl2 = new CardLayout();
    JPanel con = new JPanel(cl2);
    public JTextField text, regText;


    public Register(){RegisComponents();}

    public void RegisComponents(){
        JPanel regPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerBtn = new JButton("Register");

        registerBtn.setSize(20,10);
        registerBtn.addActionListener(this);

        backBtn = new JButton("Back to log in");
        backBtn.setSize(20,10);
        backBtn.addActionListener(this);

        JPanel regheader = new JPanel();
        JLabel regName = new JLabel("Register");
        regheader.add(regName);
        regheader.setLayout(new FlowLayout(FlowLayout.CENTER));
        regheader.setBorder(new EmptyBorder(30, 0, 30, 0));

        regheader.setBackground(Constants.primary);


        text = new JTextField("Username",20);

        JLabel regUsr = new JLabel("Enter username: ");
        regText = new JTextField("Password",20);
        JLabel regPass = new JLabel("Enter password: ");
        regPanel.setBackground(Color.WHITE);
        regPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 25, 65));



        regPanel.add(regUsr);
        regPanel.add(text);
        regPanel.add(regPass);
        regPanel.add(regText);
        regPanel.add(backBtn);
        regPanel.add(registerBtn);

        add(regPanel);

        add(regheader, BorderLayout.NORTH);
        SetDefaultFont.setFontForAllLabels(this,Constants.DEFAULT_FONT);
        regheader.setFont(Constants.HEADING_FONT);
        new SetFocusListenerToJTextFields(this);
        setSize(590, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);





    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String name = "Matel";
        String pname = "Aragon";
        if(e.getSource()== registerBtn){
            if(text.getText().equals(name)&& regText.getText().equals(pname)){
                this.setEnabled(false);
                this.dispose();
                new LogIn();
            }
            else if(!text.getText().equals(name)&& !regText.getText().equals(pname)){

                JOptionPane.showMessageDialog(this,"Invalid input");
            }

        }
        if(e.getSource()==backBtn){
            this.setEnabled(false);
            this.dispose();
            new LogIn();
        }
    }



}