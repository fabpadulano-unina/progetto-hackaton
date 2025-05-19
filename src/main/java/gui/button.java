package gui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setText("Apri");
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

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton("Apri");
        button.addActionListener(e -> fireEditingStopped());
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
            showDialog();
        }
        clicked = false;
        return "Apri";
    }

    private void showDialog() {
        JDialog dialog = new JDialog((Frame) null, "Dialog aperta", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("Hai premuto il bottone!"), BorderLayout.CENTER);
        JButton closeBtn = new JButton("Chiudi");
        closeBtn.addActionListener(e -> dialog.dispose());
        dialog.add(closeBtn, BorderLayout.SOUTH);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}