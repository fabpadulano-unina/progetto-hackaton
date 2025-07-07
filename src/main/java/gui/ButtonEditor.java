package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class ButtonRenderer extends JButton implements TableCellRenderer {
    private static final String LABEL = "Dettaglio";
    public ButtonRenderer() {
        setText(LABEL);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        return this;
    }
}

// Editor per il bottone
class ButtonEditor extends DefaultCellEditor {
    public static final String BUTTON_TEXT = "Dettaglio";
    private JButton button;
    private int row = -1;
    private boolean clicked;
    private final Controller controller;

    public ButtonEditor(JCheckBox checkBox, Controller controller) {
        super(checkBox);
        button = new JButton(BUTTON_TEXT);
        button.addActionListener(e -> fireEditingStopped());
        this.controller = controller;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            showHackatonDetails();
        }
        clicked = false;
        return BUTTON_TEXT;
    }

    private void showHackatonDetails() {
        controller.openHackatonDetail(row);
    }
}