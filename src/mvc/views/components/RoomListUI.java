package mvc.views.components;

import javax.swing.*;
import java.awt.*;

public class RoomListUI extends JPanel {
    public RoomListUI() {
        initComponents();
    }

    public void initComponents(){
        for (int i = 0; i < 2; i++) {
            RoomListItem cardItem = new RoomListItem();
            add(cardItem);
        }

        setPreferredSize(new Dimension(500,0));
    }
}
