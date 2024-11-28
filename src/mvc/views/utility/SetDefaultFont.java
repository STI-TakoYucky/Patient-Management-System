package mvc.views.utility;

import javax.swing.*;
import java.awt.*;

public class SetDefaultFont {
    public static void setFontForAllLabels(Container container, Font font) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel || component instanceof JButton) {
                component.setFont(font);
            } else if (component instanceof Container) {
                setFontForAllLabels((Container) component, font);
            }
        }
    }
}
