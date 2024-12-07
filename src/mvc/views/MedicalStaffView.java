package mvc.views;

import mvc.controllers.GetStaff;
import mvc.views.components.MedicalStaffItem;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import org.bson.Document;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicalStaffView extends Panel {

    MedicalStaffView medView = this;

    public MedicalStaffView() {
        initComponents();
    }
    JPanel staffListItemPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(staffListItemPanel);
    JPanel addStaffPanel = new JPanel();
    JButton addStaffBttn = new JButton("Add New Staff");

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

        addStaffBttn.addActionListener(new addStaffBttn());

        addStaffPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addStaffPanel.setBorder(new EmptyBorder(40,80,0,0));
        addStaffPanel.add(addStaffBttn);

        add(addStaffPanel, BorderLayout.NORTH);

        staffListItemPanel.setLayout(new BoxLayout(staffListItemPanel, BoxLayout.Y_AXIS));
        staffListItemPanel.setBorder(new EmptyBorder(40,80,80,80));

        updateUI();

        add(scrollPane, BorderLayout.CENTER);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
    }

    public void updateUI(){
        staffListItemPanel.removeAll();
        GetStaff getStaff = new GetStaff();
        List<Document> staffList = getStaff.getStaffData();

        if (staffList == null) {
            JLabel noStaff = new JLabel("No Staff Yet");
            staffListItemPanel.add(noStaff);
        } else {
            for (Document staff : staffList) {
                MedicalStaffItem item = new MedicalStaffItem(staff, medView);
                staffListItemPanel.add(item);
                staffListItemPanel.add(Box.createVerticalStrut(20));
                item.revalidate();
                item.repaint();
            }
        }
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
    }

    class addStaffBttn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddStaffView view = new AddStaffView(medView);
        }
    }

}
