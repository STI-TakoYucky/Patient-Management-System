package mvc.views.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SetFocusListenerToJTextFields implements FocusListener {

    public SetFocusListenerToJTextFields(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                component.setForeground(Color.GRAY);
                JTextField textField = (JTextField) component;
                textField.addFocusListener(this);

                // Store placeholder text if not already stored
                if (textField.getClientProperty("placeholder") == null) {
                    textField.putClientProperty("placeholder", textField.getText());
                }
            } else if (component instanceof Container) {
                new SetFocusListenerToJTextFields((Container) component);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField source = (JTextField) e.getSource();
        String placeholder = (String) source.getClientProperty("placeholder");

        if (source.getText().trim().equals(placeholder)) {
            source.setText(""); // Clear placeholder text
            source.setForeground(Color.BLACK); // Set text color to default
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField source = (JTextField) e.getSource();
        String placeholder = (String) source.getClientProperty("placeholder");

        if (source.getText().trim().isEmpty()) {
            source.setText(placeholder); // Restore placeholder text
            source.setForeground(Color.GRAY); // Set placeholder text color
        }
    }
}
