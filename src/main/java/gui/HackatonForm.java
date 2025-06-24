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
import java.util.Locale;

public class HackatonForm extends JFrame {
    private JTextField titoloInput;
    private JTextField sedeInput;
    private JTextField dataFineInput;
    private JButton addHackatonBtn;
    private JTextField numeroMaxIscrittiInput;
    private JTextField dataInizioInput;
    private JTextField dimensioneMaxTeamInput;
    private JPanel panel;
    private JList<String> giudiciList;
    private Controller controller;
    private List<Giudice> giudici;

    public HackatonForm(Controller controller, List<Giudice> giudici) {
        this.setTitle("Crea Hackaton");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.giudici = giudici;
        setGiudiciList();

        this.controller = controller;


        handleClicks();
    }

    private void setGiudiciList() {
        giudiciList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        String[] nomiGiudici = new String[giudici.size()];
        for (int i = 0; i < giudici.size(); i++) {
            nomiGiudici[i] = giudici.get(i).getNome();
        }

        giudiciList.setVisibleRowCount(nomiGiudici.length);
        giudiciList.setListData(nomiGiudici);
    }

    private void handleClicks() {
        addHackatonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                formatter = formatter.withLocale( Locale.ITALIAN );

                List<Giudice> giudiciSelezionati = new ArrayList<>();
                for (int i : giudiciList.getSelectedIndices()) {
                    giudiciSelezionati.add(giudici.get(i));
                }
                controller.saveHackaton(
                        titoloInput.getText(),
                        sedeInput.getText(),
                        LocalDate.parse(dataInizioInput.getText(), formatter),
                        LocalDate.parse(dataFineInput.getText(), formatter),
                        Integer.parseInt(numeroMaxIscrittiInput.getText()),
                        Integer.parseInt(dimensioneMaxTeamInput.getText()),
                        giudiciSelezionati
                );
                controller.backToHomeFrame(HackatonForm.this);
            }
        });
    }

}
