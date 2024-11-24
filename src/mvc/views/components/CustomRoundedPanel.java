package mvc.views.components;

import javax.swing.*;
import java.awt.*;

public class CustomRoundedPanel extends JPanel {


    public CustomRoundedPanel() {
        setOpaque(false); // Ensure the corners are transparent
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
    }
}

