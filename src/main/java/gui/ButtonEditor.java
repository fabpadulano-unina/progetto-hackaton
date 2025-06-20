package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setText("Dettaglio");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        return this;
    }
}

// Editor per il bottone
class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private boolean clicked;
    private Controller controller;

    public ButtonEditor(JCheckBox checkBox, Controller controller) {
        super(checkBox);
        button = new JButton("Dettaglio");
        button.addActionListener(e -> fireEditingStopped());
        this.controller = controller;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            showHackatonDetails();
        }
        clicked = false;
        return "Dettaglio";
    }

    private void showHackatonDetails() {
        controller.openHackatonDetail();
    }
}