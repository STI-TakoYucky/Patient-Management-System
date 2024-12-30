package mvc.views.utility;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

public class SetFocusListenerToJTextFields implements FocusListener {
    private final Container container;

    public SetFocusListenerToJTextFields(Container container) {
        this.container = container;
        addFocusListenerToJTextFields(container);
    }

    private void addFocusListenerToJTextFields(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                // Store placeholder text if not already stored
                if (textField.getClientProperty("placeholder") == null) {
                    textField.putClientProperty("placeholder", textField.getText());
                    textField.setForeground(Color.GRAY);
                }
                textField.addFocusListener(this);
            } else if (component instanceof Container) {
                addFocusListenerToJTextFields((Container) component);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField source = (JTextField) e.getSource();
        String placeholder = (String) source.getClientProperty("placeholder");
        if (source.getText().equals(placeholder)) {
            source.setText(""); // Clear placeholder text
            source.setForeground(Color.BLACK); // Set text color to default
        }
        Border borderColor = BorderFactory.createLineBorder(Color.BLUE, 2, true);
        source.setBorder(BorderFactory.createCompoundBorder(
                borderColor, new EmptyBorder(2, 10, 2, 10) // Inner padding
        ));
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField source = (JTextField) e.getSource();
        String placeholder = (String) source.getClientProperty("placeholder");
        if (source.getText().isEmpty()) {
            source.setText(placeholder); // Restore placeholder text
            source.setForeground(Color.GRAY);
        }
        Border borderColor = BorderFactory.createLineBorder(Color.GRAY, 1, true);
        source.setBorder(BorderFactory.createCompoundBorder(
                borderColor, new EmptyBorder(2, 10, 2, 10) // Inner padding
        ));
    }
}
