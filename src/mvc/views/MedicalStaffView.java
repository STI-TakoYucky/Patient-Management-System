package mvc.views;

import mvc.controllers.AddStaffController;
import mvc.models.AddStaffModel;
import mvc.views.components.CustomRoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicalStaffView extends Panel {
    public MedicalStaffView() {
        initComponents();
    }

    public void initComponents() {
        JButton addStaffBttn = new JButton("Add New Staff");
        JPanel addStaffPanel = new JPanel();
        JPanel staffListItem = new CustomRoundedPanel();
        JPanel staffListItemPanel = new JPanel();
        JLabel staffID = new JLabel("Staff ID: 1232131");
        JLabel staffName = new JLabel("Staff Name: Lucky Estrada");
        JLabel editStaffItemButton = new JLabel("Edit Info");

        setLayout(new BorderLayout());

        staffListItem.setBackground(Color.LIGHT_GRAY);
        staffListItem.setPreferredSize(new Dimension(900, 90));
        staffListItem.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 20, 5, 20);

        staffListItem.add(staffID);
        staffListItem.add(staffName);
        staffListItem.add(editStaffItemButton);

        addStaffBttn.addActionListener(new addStaffBttn());

        addStaffPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addStaffPanel.setBorder(new EmptyBorder(0,50,0,0));
        addStaffPanel.add(addStaffBttn);
        add(addStaffPanel, BorderLayout.NORTH);
        staffListItemPanel.add(staffListItem);
        add(staffListItemPanel, BorderLayout.CENTER);

    }

    class addStaffBttn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddStaffView view = new AddStaffView();
        }
    }
}
