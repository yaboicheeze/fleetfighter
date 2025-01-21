package JavaFiles;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static JavaFiles.GameScreen.displayStartScreen;

public class Main {

    public static void main(String[] args){
        JFrame gameWindow = new JFrame();
        gameWindow.setLayout(null);
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(1920, 1080);
        gameWindow.getContentPane().setBackground(Color.DARK_GRAY);

        Vector<Ship> playerFleet = new Vector<>();
        Vector<Ship> enemyFleet = new Vector<>();

        displayStartScreen(gameWindow, enemyFleet, playerFleet);
    }
}
