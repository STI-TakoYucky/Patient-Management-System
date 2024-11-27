package mvc.views;

import mvc.controllers.AddStaffController;
import mvc.controllers.GetStaff;
import mvc.models.AddStaffModel;
import mvc.views.components.CustomRoundedPanel;
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

    MedicalStaffView med = this;

    public MedicalStaffView() {
        initComponents();
    }
    JPanel staffListItemPanel = new JPanel();
    JPanel addStaffPanel = new JPanel();
    JButton addStaffBttn = new JButton("Add New Staff");

    public void initComponents() {


        setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 20, 5, 20);

        addStaffBttn.addActionListener(new addStaffBttn());

        addStaffPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addStaffPanel.setBorder(new EmptyBorder(0,50,0,0));
        addStaffPanel.add(addStaffBttn);
        add(addStaffPanel, BorderLayout.NORTH);

        staffListItemPanel.setLayout(new FlowLayout());

        updateUI();

        add(staffListItemPanel, BorderLayout.CENTER);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
    }

    public void updateUI(){
        staffListItemPanel.removeAll();
        GetStaff getStaff = new GetStaff();
        List<Document> staffList = getStaff.getStaffData();

        if (staffList == null) {
            JLabel noStaff = new JLabel("No Staff");
            staffListItemPanel.add(noStaff);
        } else {
            for (Document staff : staffList) {
                MedicalStaffItem item = new MedicalStaffItem(staff);
                staffListItemPanel.add(item);
                item.revalidate();
                item.repaint();
            }
        }
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);

    }

    class addStaffBttn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddStaffView view = new AddStaffView(med);
        }
    }

}
