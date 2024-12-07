package mvc.views;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import mvc.controllers.DeleteStaffController;
import mvc.controllers.EditStaffController;
import mvc.models.StaffModel;
import mvc.views.constants.Constants;
import mvc.views.utility.SetDefaultFont;
import org.bson.types.ObjectId;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditStaffView extends JFrame {
    MedicalStaffView view;

    JTextField staffNameFieldFN = new JTextField("First Name", 30);
    JTextField staffNameFieldLN = new JTextField("Last Name", 30);
    JTextField staffPositionTextField = new JTextField("Staff Postion", 30);
    JLabel staffId = new JLabel("A");

    StaffModel model = new StaffModel();

    public EditStaffView(String id, String firstName, String lastName, String position, MedicalStaffView view) {
        initComponents();
        this.view = view;
        staffNameFieldFN.setText(firstName);
        staffNameFieldLN.setText(lastName);
        staffPositionTextField.setText(position);
        staffId.setText(String.valueOf(id));
        model.setId(id);
    }

    public void initComponents() {
        JPanel mainPanel = new JPanel();

        JLabel addStaffHeader = new JLabel("Add Staff");
        addStaffHeader.setFont(Constants.HEADING_FONT);

        JLabel staffNameLabel = new JLabel("Medical Staff Name");
        JPanel staffNameTextFieldPanel = new JPanel();

        staffNameTextFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        staffNameTextFieldPanel.setMaximumSize(new Dimension(1500, 60));
        staffNameTextFieldPanel.add(staffNameFieldFN);
        staffNameTextFieldPanel.add(staffNameFieldLN);

        JLabel staffPosition = new JLabel("Staff Postion");
        JPanel staffPositionPanel = new JPanel();
        staffPositionPanel.add(staffPositionTextField);
        staffPositionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        staffPositionPanel.setMaximumSize(new Dimension(1500, 60));

        JButton addStaffButton = new JButton("Confirm Edit");
        addStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditStaffController(model);
                view.updateUI();
            }
        });

        JButton deleteButton = new JButton("Delete Staff");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteStaffController(model);
                view.updateUI();
            }
        });

        //setup JFrame
        setVisible(true);
        setSize(900, 700);
        setLocationRelativeTo(null);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(addStaffHeader);
        mainPanel.add(staffNameLabel);
        mainPanel.add(staffNameTextFieldPanel);
        mainPanel.add(staffPosition);
        mainPanel.add(staffPositionPanel);
        mainPanel.add(addStaffButton);
        mainPanel.add(deleteButton);
        add(mainPanel);

        setOnChangeEvent(this, model);

        SetDefaultFont.setFontForAllLabels(this, Constants.DEFAULT_FONT);
    }

    private void setOnChangeEvent(Container container, StaffModel model) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                ((JTextField) component).getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        handleTextChange(e);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleTextChange(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        handleTextChange(e);
                    }

                    private void handleTextChange(DocumentEvent e) {
                        JTextField source = (JTextField) component;
                        String text = source.getText();
                        // Handle text change for specific fields
                        if (source == staffNameFieldFN) {
                            model.setFirstName(text);
                        } else if (source == staffNameFieldLN) {
                            model.setLastName(text);
                        } else if (source == staffPositionTextField) {
                            model.setPosition(text);
                        }
                    }
                });
            } else if (component instanceof Container) {
                setOnChangeEvent((Container) component, model);
            }
        }
    }
}
