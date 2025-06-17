package gui;

import controller.Controller;
import model.Organizzatore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private JList giudiciList;
    private Controller controller;


    public HackatonForm(Controller controller) {
        this.setTitle("Crea Hackaton");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        setGiudiciList();
//        JScrollPane scrollPane = new JScrollPane(giudiciList);


        this.controller = controller;


        handleClicks();
    }

    private void setGiudiciList() {
        giudiciList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //aggiungere i giudici
        String[] giudici = { "Giudice 1", "Giudice 2", "Giudice 3", "Giudice 5" };
        giudiciList.setVisibleRowCount(4);
        giudiciList.setListData(giudici);
    }

    private void handleClicks() {
        addHackatonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                formatter = formatter.withLocale( Locale.ITALIAN );

                controller.saveHackaton(
                        titoloInput.getText(),
                        sedeInput.getText(),
                        LocalDate.parse(dataInizioInput.getText(), formatter),
                        LocalDate.parse(dataFineInput.getText(), formatter),
                        Integer.parseInt(numeroMaxIscrittiInput.getText()),
                        Integer.parseInt(dimensioneMaxTeamInput.getText()),
                        giudiciList.getSelectedValuesList()
                );
                controller.backToHomeFrame(HackatonForm.this);
            }
        });
    }

}
