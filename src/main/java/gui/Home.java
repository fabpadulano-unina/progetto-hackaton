package gui;

import controller.Controller;

import javax.swing.*;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private static Controller controller;

    public static void main(String[] args) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);

        controller.openLoginForm();



    }

    public Home() {
        controller = new Controller(frameHome);
        // Add action listeners or other initialization code here

    }


}
