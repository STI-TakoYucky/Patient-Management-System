package mvc.views;

import mvc.controllers.GetStaff;
import mvc.models.RoomModel;
import mvc.models.StaffModel;
import mvc.views.components.AdminItem;
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

public class AdminView extends Panel {

    AdminView adminView = this;
    Dashboard dashboard;

    public AdminView(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
    }
    JPanel staffListItemPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(staffListItemPanel);
    JPanel addStaffPanel = new JPanel();
    JButton addStaffBttn = new JButton("Add New Admin");

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
        addStaffPanel.setBorder(new EmptyBorder(30,80,0,0));
        addStaffPanel.add(addStaffBttn);

        add(addStaffPanel, BorderLayout.NORTH);

        staffListItemPanel.setLayout(new BoxLayout(staffListItemPanel, BoxLayout.Y_AXIS));
        staffListItemPanel.setBorder(new EmptyBorder(30,80,80,80));

        updateUI();

        add(scrollPane, BorderLayout.CENTER);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
    }

    public void updateUI(){
        staffListItemPanel.removeAll();
        GetStaff getStaff = new GetStaff();
        List<Document> adminList = getStaff.getAdminData();

        if (adminList == null) {
            JLabel noStaff = new JLabel("No Admins Yet");
            staffListItemPanel.add(noStaff);
        } else {
            for (Document staff : adminList) {
                AdminItem item = new AdminItem(staff, this, dashboard);
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
            AddAdminView view = new AddAdminView(new StaffModel(), adminView, dashboard);
            dashboard.setEnabled(false);
            dashboard.setFocusable(false);
            dashboard.setAlwaysOnTop(false);
        }
    }

}
