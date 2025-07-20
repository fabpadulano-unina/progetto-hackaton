package gui;

import controller.Controller;
import model.Giudice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Form per la creazione di un nuovo hackathon.
 * Permette all'organizzatore di inserire i dettagli e selezionare i giudici.
 */
public class HackatonForm extends JFrame {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JTextField titoloInput;
    private JTextField sedeInput;
    private JTextField dataFineInput;
    private JButton addHackatonBtn;
    private JTextField numeroMaxIscrittiInput;
    private JTextField dataInizioInput;
    private JTextField dimensioneMaxTeamInput;
    private JPanel panel;
    private JList<String> giudiciList;
    private final Controller controller;
    private final List<Giudice> giudici;

    /**
     * Crea il form con i campi precompilati e la lista dei giudici disponibili.
     * Inizializza le date di default e configura i componenti.
     *
     * @param controller il controller per gestire le operazioni
     * @param giudici la lista dei giudici disponibili per la selezione
     */
    public HackatonForm(Controller controller, List<Giudice> giudici) {
        this.setTitle("Crea Hackaton");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.giudici = giudici;
        setGiudiciList();

        this.controller = controller;

        this.dataInizioInput.setText(LocalDate.now().format(DATE_TIME_FORMATTER));
        this.dataFineInput.setText(LocalDate.now().plusDays(2).format(DATE_TIME_FORMATTER));

        handleClicks();
    }

    /**
     * Configura la lista dei giudici disponibili per la selezione multipla.
     * Mostra i nomi dei giudici e permette di selezionarne più di uno.
     */
    private void setGiudiciList() {
        giudiciList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        String[] nomiGiudici = new String[giudici.size()];
        for (int i = 0; i < giudici.size(); i++) {
            nomiGiudici[i] = giudici.get(i).getNome();
        }

        giudiciList.setVisibleRowCount(nomiGiudici.length);
        giudiciList.setListData(nomiGiudici);
    }

    /**
     * Gestisce il click sul pulsante di creazione hackathon.
     * Raccoglie i dati inseriti e salva il nuovo hackathon con i giudici selezionati.
     */
    private void handleClicks() {
        addHackatonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Giudice> giudiciSelezionati = new ArrayList<>();
                for (int i : giudiciList.getSelectedIndices()) {
                    giudiciSelezionati.add(giudici.get(i));
                }
                controller.saveHackaton(
                        titoloInput.getText(),
                        sedeInput.getText(),
                        LocalDate.parse(dataInizioInput.getText(), DATE_TIME_FORMATTER),
                        LocalDate.parse(dataFineInput.getText(), DATE_TIME_FORMATTER),
                        Integer.parseInt(numeroMaxIscrittiInput.getText()),
                        Integer.parseInt(dimensioneMaxTeamInput.getText()),
                        giudiciSelezionati
                );
                controller.backToHomeFrame(HackatonForm.this);
            }
        });
    }

}
