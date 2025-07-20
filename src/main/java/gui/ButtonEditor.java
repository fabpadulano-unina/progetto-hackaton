package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Mostra un pulsante "Dettaglio" in ogni cella della colonna dedicata
 * agli hackathon per permettere l'accesso ai dettagli.
 */
class ButtonRenderer extends JButton implements TableCellRenderer {
    private static final String LABEL = "Dettaglio";

    /**
     * Costruttore del renderer.
     * Imposta il testo del pulsante che verrà mostrato in tabella.
     */
    public ButtonRenderer() {
        setText(LABEL);
    }

    /**
     * Restituisce il componente da visualizzare nella cella della tabella.
     * Ritorna sempre questo pulsante indipendentemente dal contenuto della cella.
     *
     * @param table la tabella che contiene la cella
     * @param value il valore della cella
     * @param isSelected se la cella è selezionata
     * @param hasFocus se la cella ha il focus
     * @param row l'indice della riga
     * @param column l'indice della colonna
     * @return il componente pulsante da visualizzare
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        return this;
    }
}


/**
 * Permette di aprire i dettagli di un hackathon quando viene premuto
 * il pulsante "Dettaglio" nella rispettiva riga.
 */
class ButtonEditor extends DefaultCellEditor {

    public static final String BUTTON_TEXT = "Dettaglio";
    private JButton button;
    private int row = -1;
    private boolean clicked;
    private final Controller controller;

    /**
     * Costruttore dell'editor del pulsante.
     * Configura il pulsante e collega il controller per gestire
     * l'apertura dei dettagli hackathon.
     *
     * @param checkBox il checkbox richiesto dal costruttore padre
     * @param controller il controller per gestire l'apertura dei dettagli
     */
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

    /**
     * Apre i dettagli dell'hackathon se il pulsante è stato premuto.
     *
     * @return il testo del pulsante
     */
    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            showHackatonDetails();
        }
        clicked = false;
        return BUTTON_TEXT;
    }

    /**
     * Apre la finestra dei dettagli per l'hackathon selezionato.
     * Utilizza il controller per mostrare le informazioni della riga scelta.
     */
    private void showHackatonDetails() {
        controller.openHackatonDetail(row);
    }
}