package mvc.views.utility;

import mvc.views.AddPatientView;
import mvc.views.EditPatientView;
import mvc.views.constants.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

public class SetFocusListenerToJTextFields implements FocusListener {
    Container container;
    AddPatientView addPatientView;
    EditPatientView editPatientView;

    public SetFocusListenerToJTextFields(Container container, EditPatientView editPatientView) {
        this.container = container;
        this.editPatientView = editPatientView;
        addFocusListenerToJTextFields(container);
    }

    public SetFocusListenerToJTextFields(Container container) {
        this.container = container;
        this.addPatientView = addPatientView;
        addFocusListenerToJTextFields(container);
    }

    public void addFocusListenerToJTextFields(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                component.setForeground(Color.GRAY);
                JTextField textField = (JTextField) component;
                textField.addFocusListener(this);

                if (Objects.equals(this.container, editPatientView)) {
                    component.setForeground(Color.BLACK);
                }

                // Store placeholder text if not already stored
                if (textField.getClientProperty("placeholder") == null) {
                    textField.putClientProperty("placeholder", textField.getText());
                }
            } else if (component instanceof Container) {
                addFocusListenerToJTextFields((Container) component);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField source = (JTextField) e.getSource();
        String placeholder = (String) source.getClientProperty("placeholder");

        if (source.getText().trim().equals(placeholder) && Objects.equals(container, editPatientView)) {
            source.setText(source.getText());
            source.setForeground(Color.BLACK); // Set text color to default
            Border borderColor = BorderFactory.createLineBorder(Constants.primary, 2, true);
            source.setBorder(BorderFactory.createCompoundBorder(
                    borderColor, new EmptyBorder(2, 10, 2, 10) // Inner padding
            ));
        }else if (source.getText().trim().equals(placeholder) && !Objects.equals(container, editPatientView)) {
            source.setText(""); // Clear placeholder text
            source.setForeground(Color.BLACK); // Set text color to default
            Border borderColor = BorderFactory.createLineBorder(Constants.primary, 2, true);
            source.setBorder(BorderFactory.createCompoundBorder(
                    borderColor, new EmptyBorder(2, 10, 2, 10) // Inner padding
            ));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField source = (JTextField) e.getSource();
        String placeholder = (String) source.getClientProperty("placeholder");
        Border borderColor = BorderFactory.createLineBorder(Color.GRAY, 1, true);

        if (source.getText().trim().isEmpty()) {
            source.setText(placeholder);
            source.setForeground(Color.GRAY);
        }

        source.setBorder(BorderFactory.createCompoundBorder(
                borderColor, new EmptyBorder(2, 10, 2, 10) // Inner padding
        ));

    }
}
