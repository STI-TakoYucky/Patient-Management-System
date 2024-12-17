package mvc.views;

import mvc.controllers.GetPatients;
import mvc.controllers.GetStaff;
import mvc.models.PatientModel;
import mvc.models.RoomModel;
import mvc.views.components.MedicalStaffItem;
import mvc.views.components.PatientItem;
import mvc.views.components.RoomListItem;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomView extends Panel {

    RoomView roomView = this;
    Dashboard dashboard;

    public RoomView(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
    }
    JPanel roomListPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(roomListPanel);
    JPanel addRoomPanel = new JPanel();
    JButton addRoomBttn = new JButton("Add New Room");

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

        addRoomBttn.addActionListener(new addRoomBttn());

        addRoomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addRoomPanel.setBorder(new EmptyBorder(30,80,0,0));
        addRoomPanel.add(addRoomBttn);

        add(addRoomPanel, BorderLayout.NORTH);

        roomListPanel.setLayout(new BoxLayout(roomListPanel, BoxLayout.Y_AXIS));
        roomListPanel.setBorder(new EmptyBorder(30,80,80,80));

//        updateUI();

        add(scrollPane, BorderLayout.CENTER);
        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
        revalidate();
        repaint();
    }

//    public void updateUI(){
//        roomListPanel.removeAll();
//        GetRooms getRooms = new GetRooms();
//        List<Document> roomList = getRooms.getRoomData();
//
//        if (roomList == null) {
//            JLabel noRooms = new JLabel("No Rooms Yet");
//            roomListPanel.add(noRooms);
//        } else {
//            for (Document room : roomList) {
//                RoomListItem item = new RoomListItem(room, roomView, dashboard);
//                roomListPanel.add(item);
//                roomListPanel.add(Box.createVerticalStrut(20));
//                item.revalidate();
//                item.repaint();
//            }
//        }
//        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
//        revalidate();
//        repaint();
//    }

    class addRoomBttn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dashboard.setEnabled(false);
            dashboard.setFocusable(false);
            new AddRoomView(new RoomModel(), roomView, dashboard);
        }
    }

}
