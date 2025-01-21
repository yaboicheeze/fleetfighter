package JavaFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

import static JavaFiles.GameLoop.*;
import static JavaFiles.Ship.*;

public class GameScreen {


    static Font bigButtonFont = new Font("Arial", Font.PLAIN, 30);
    static Font smallButtonFont = new Font("Arial", Font.PLAIN, 15);

    public static void displayOwnFleet(Vector<Ship> yourFleet, JList myList){
        DefaultListModel<String> fleetDetails = new DefaultListModel<>();

        IntStream.range(0, yourFleet.size()).forEach(i -> {
            Ship ship = yourFleet.get(i);
            String shipDetails =  ship.getClass().getSimpleName() + ": " +  // Something to add if I want to display the ship's index:   "[" + (i + 1) + "] " +
                    "[HP: " + ship.getHealth() + " / " + ship.getMaxHealth() + "], " +
                    "[Armor: " + ship.getArmor() + "], " +
                    "[Hit Mod: +" + ship.getHitMod() + "]" +
                    "[Attack Power " + ship.getAttackPower() + "]";
            fleetDetails.addElement(shipDetails);
        });

        myList.setModel(fleetDetails);
    }

    public static void displayEnemyFleet(Vector<Ship> yourFleet, JList myList){
        DefaultListModel<String> fleetDetails = new DefaultListModel<>();

        IntStream.range(0, yourFleet.size()).forEach(i -> {
            Ship ship = yourFleet.get(i);
            String shipDetails =  ship.getClass().getSimpleName() + ": " +  // Something to add if I want to display the ship's index:   "[" + (i + 1) + "] " +
                    "[HP: " + ship.getHealth() + " / " + ship.getMaxHealth() + "], " +
                    "[Armor: " + ship.getArmor() + "], " +
                    "[Hit Mod: +" + ship.getHitMod() + "]" +
                    "[Attack Power " + ship.getAttackPower() + "]";
            fleetDetails.addElement(shipDetails);
        });

        myList.setModel(fleetDetails);
    }

    public static void updateText(String text, JLabel myLabel){
        myLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myLabel.setText(text);
    }

    public static void prizeTextUpdate(int rollNum, JButton prizeButton){
        if(rollNum <= 20){
            prizeButton.setText("Small Credit Prize");
        }
        else if(rollNum <= 50){
            prizeButton.setText("Ship Loot Box");
        }
        else if(rollNum <= 75){
            prizeButton.setText("Big Gold Prize");
        }
        else if(rollNum <= 80){
            prizeButton.setText("<html>Improved Ship<br/>Loot Box<html>");
        }
        else if(rollNum <= 94){
            prizeButton.setText("Offensive Ship Buff");
        }
        else if(rollNum <= 99){
            prizeButton.setText("Defensive Ship Buff");
        }
        else{
            prizeButton.setText("<html>SuperTech Systems<br/>Upgrade<html>");
        }
    }

    public static void rollForPrize(Vector<Ship> playerFleet, int rollNum){
        if(rollNum <= 20){
            goldPrize();
        }
        else if(rollNum <= 50){
            basicShipLootBox(playerFleet);
        }
        else if(rollNum <= 75){
            goldBigPrize();
        }
        else if(rollNum <= 80){
            improvedShipLootBox(playerFleet);
        }
        else if(rollNum <= 94){
            offensiveShipBuff(playerFleet);
        }
        else if(rollNum <= 99){
            defensiveShipBuff(playerFleet);
        }
        else {
            superShipBuff(playerFleet);
        }
    }

    //Used if I make a special prize every 5 levels
    //Maybe also include a defensive/offensive buff
    //see specialLootBox in GameLoop.java
//    public static void rollForSpecialPrize(JLabel myLabel){
//          set label to visible
//          roll a random number
//          if(getRoundNum()%10==0)
//          set the label's text to what the player won
//          give player the ship/buff/etc.
//    }

    public static void erasePreviousScreen(JFrame gameWindow){
        gameWindow.getContentPane().removeAll();
        gameWindow.repaint();
    }

    public static void displayStartScreen(JFrame gameWindow, Vector<Ship> enemyFleet, Vector<Ship> playerFleet){
        JPanel gameStartPanel = new JPanel();
        gameStartPanel.setSize(1920, 1080);
        gameStartPanel.setLayout(null);
        gameStartPanel.setOpaque(false);

        JLabel titleText = new JLabel("Fleet Fighter");
        titleText.setBounds(460, 200, 1000, 150);
        titleText.setOpaque(true);
        titleText.setBackground(Color.CYAN);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Arial", Font.PLAIN, 70));
        titleText.setHorizontalAlignment(JLabel.CENTER);

        JButton startButton = new JButton("Start");
        startButton.setBounds(860, 800, 200, 100);
        startButton.setFont(bigButtonFont);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                erasePreviousScreen(gameWindow);
                displayGameMainMenu(gameWindow, enemyFleet, playerFleet);
            }
        });

        gameStartPanel.add(titleText);
        gameStartPanel.add(startButton);

        gameWindow.add(gameStartPanel);
    }

    public static void displayGameMainMenu(JFrame gameWindow, Vector<Ship> enemyFleet, Vector<Ship> playerFleet){

        //General elements
        JPanel gameMainMenuPanel = new JPanel();
        gameMainMenuPanel.setSize(1920, 1080);
        gameMainMenuPanel.setLayout(null);
        gameMainMenuPanel.setOpaque(false);

        //Gold/Credits Widget
        JLabel goldWidget = new JLabel();
        goldWidget.setBounds(860, 100, 200, 300);
        goldWidget.setOpaque(true);
        goldWidget.setHorizontalAlignment(SwingConstants.CENTER);
        goldWidget.setBackground(Color.CYAN);
        displayGold(goldWidget);

        //Round number display
        JLabel roundNumberLabel = new JLabel();
        roundNumberLabel.setBounds(860, 450, 200, 100);
        roundNumberLabel.setOpaque(true);
        roundNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roundNumberLabel.setBackground(Color.CYAN);
        displayRoundNumber(roundNumberLabel);

        //Game stats widget
        JLabel playerStats = new JLabel();
        playerStats.setBounds(860, 600, 200, 100);
        playerStats.setOpaque(true);
        playerStats.setHorizontalAlignment(SwingConstants.CENTER);
        playerStats.setBackground(Color.CYAN);
        displayPlayerStats(playerStats);

        //Start Combat Button
        Button beginCombat = new Button("Start Combat");
        beginCombat.setBounds(860, 800, 200, 100);
        beginCombat.setFont(new Font("Arial", Font.PLAIN, 30));

        beginCombat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                erasePreviousScreen(gameWindow);
                displayCombatScreen(gameWindow, enemyFleet, playerFleet);
            }
        });

        // List Stuff
        JList<String> playerFleetDisplay = new JList<>();
        JScrollPane playerScrollPane = new JScrollPane(playerFleetDisplay);
        playerScrollPane.setBounds(120, 40, 500, 650);
        playerFleetDisplay.setOpaque(true);
        playerFleetDisplay.setBackground(Color.CYAN);
        displayOwnFleet(playerFleet, playerFleetDisplay);

        //Shop Stuff
        JPanel gameShopPanel = new JPanel();
        gameShopPanel.setBounds(1300, 40, 500, 650);
        gameShopPanel.setLayout(null);
        gameShopPanel.setOpaque(true);
        gameShopPanel.setBackground(Color.CYAN);

        //TO DO: Add boxes for stats of each ship right above the buy button

        JButton buyCorvette = new JButton("Buy Corvette - 5 Credits");
        setCorvButtonName(buyCorvette);
        buyCorvette.setFont(smallButtonFont);
        buyCorvette.setBounds(50, 100, 300, 50);

        buyCorvette.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyCorvette(playerFleet, buyCorvette);
                displayGold(goldWidget);
                System.out.println("Added Corvette to fleet");
                displayOwnFleet(playerFleet, playerFleetDisplay);

            }
        });

        JButton buyFrigate = new JButton("Buy Frigate - 10 Credits");
        setFrigButtonName(buyFrigate);
        buyFrigate.setFont(smallButtonFont);
        buyFrigate.setBounds(50, 200, 300, 50);

        buyFrigate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyFrigate(playerFleet, buyFrigate);
                displayGold(goldWidget);
                System.out.println("Added Frigate to fleet");
                displayOwnFleet(playerFleet, playerFleetDisplay);
            }
        });

        JButton buyBattleship = new JButton("Buy Battleship - 15 Credits");
        setBatShipButtonName(buyBattleship);
        buyBattleship.setFont(smallButtonFont);
        buyBattleship.setBounds(50, 300, 300, 50);

        buyBattleship.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyBattleship(playerFleet, buyBattleship);
                displayGold(goldWidget);
                System.out.println("Added Battleship to fleet");
                displayOwnFleet(playerFleet, playerFleetDisplay);
            }
        });



        //Adding buy buttons to shop
        gameShopPanel.add(buyBattleship);
        gameShopPanel.add(buyFrigate);
        gameShopPanel.add(buyCorvette);

        //Adding player's fleet
        gameMainMenuPanel.add(playerScrollPane);

        //Adding to gameWindow
        gameMainMenuPanel.add(roundNumberLabel);
        gameMainMenuPanel.add(playerStats);
        gameMainMenuPanel.add(goldWidget);
        gameMainMenuPanel.add(gameShopPanel);
        gameMainMenuPanel.add(beginCombat);

        gameWindow.add(gameMainMenuPanel);
        gameMainMenuPanel.revalidate();
        gameMainMenuPanel.repaint();
    }

    public static void displayCombatScreen(JFrame gameWindow, Vector<Ship> enemyFleet, Vector<Ship> playerFleet) {

        //General Stuff
        JPanel gameCombatPanel = new JPanel();
        gameCombatPanel.setSize(1920, 1080);
        gameCombatPanel.setLayout(null);
        gameCombatPanel.setOpaque(false);

        JLabel combatLog = new JLabel("This is a combat log.");
        combatLog.setBounds(1000, 850, 880, 150);
        combatLog.setOpaque(true);
        combatLog.setBackground(Color.CYAN);

        //Round number display
        JLabel roundNumberLabel = new JLabel();
        roundNumberLabel.setBounds(860, 100, 200, 100);
        roundNumberLabel.setOpaque(true);
        roundNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roundNumberLabel.setBackground(Color.CYAN);
        displayRoundNumber(roundNumberLabel);

        //Attack and Defense buttons
        JButton attackButton = new JButton("Attack");
        JButton defendButton = new JButton("Defend");

        //Ship display lists
        //      Player fleet
        JList<String> playerFleetDisplay = new JList<>();
        JScrollPane playerScrollPane = new JScrollPane(playerFleetDisplay);
        playerScrollPane.setBounds(120, 40, 500, 650);
        playerFleetDisplay.setOpaque(true);
        playerFleetDisplay.setBackground(Color.CYAN);
        displayOwnFleet(playerFleet, playerFleetDisplay);

        //      Enemy Fleet
        JList<String> enemyFleetDisplay = new JList<>();
        JScrollPane enemyScrollPane = new JScrollPane(enemyFleetDisplay);
        enemyScrollPane.setBounds(1300, 40, 500, 650);
        enemyFleetDisplay.setOpaque(true);
        enemyFleetDisplay.setBackground(Color.CYAN);
        generateEnemyFleet(enemyFleet, getRoundNum());
        buffEnemyShips(enemyFleet, getRoundNum());
        displayEnemyFleet(enemyFleet, enemyFleetDisplay);

        //Buttons
        JPanel combatActions = new JPanel();
        combatActions.setBounds(40, 850, 880, 150);
        combatActions.setOpaque(true);
        combatActions.setBackground(Color.CYAN);

        //Button to send player to prize menu
        JButton sendToPrizeMenu = new JButton("Display Prizes");
        sendToPrizeMenu.setBounds(760, 500, 400, 100);
        sendToPrizeMenu.setVisible(false);

        sendToPrizeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                erasePreviousScreen(gameWindow);
                displayPrizeScreen(gameWindow, enemyFleet, playerFleet);
            }
        });

        //Button to send player back to main menu
        JButton backToMainMenu = new JButton("Back to Main Menu");
        backToMainMenu.setBounds(760, 500, 400, 100);
        backToMainMenu.setVisible(false);

        backToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                erasePreviousScreen(gameWindow);
                displayGameMainMenu(gameWindow, enemyFleet, playerFleet);
            }
        });

        //Temporary commence battle button
        JButton commenceBattle = new JButton("Commence Battle");
        commenceBattle.setBounds(760, 500, 400, 100);
        commenceBattle.setVisible(true);

        //Adding stuff to other stuff
        gameCombatPanel.add(playerScrollPane);
        gameCombatPanel.add(enemyScrollPane);

        combatActions.add(attackButton);
        combatActions.add(defendButton);

        gameCombatPanel.add(roundNumberLabel);
        gameCombatPanel.add(commenceBattle);
        gameCombatPanel.add(combatActions);
        gameCombatPanel.add(combatLog);
        gameCombatPanel.add(sendToPrizeMenu);
        gameCombatPanel.add(backToMainMenu);
        gameWindow.add(gameCombatPanel);
        gameCombatPanel.revalidate();
        gameCombatPanel.repaint();

        commenceBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commenceBattle.setVisible(false);
                combatLoop(enemyFleet, playerFleet, combatLog, attackButton, defendButton, playerFleetDisplay, enemyFleetDisplay, backToMainMenu, sendToPrizeMenu);
            }
        });


    }

    public static void displayPrizeScreen(JFrame gameWindow, Vector<Ship> enemyFleet, Vector<Ship> playerFleet){

        //Create screen widgets
        JLabel pickPrize = new JLabel("Pick One Reward");
        pickPrize.setBounds(760, 350, 400, 100);
        pickPrize.setOpaque(true);
        pickPrize.setHorizontalAlignment(SwingConstants.CENTER);
        pickPrize.setBackground(Color.CYAN);

        JButton prizeOne = new JButton();
        prizeOne.setBounds(460, 700, 200, 100);

        JButton prizeTwo = new JButton();
        prizeTwo.setBounds(860, 700, 200, 100);

        JButton prizeThree = new JButton();
        prizeThree.setBounds(1260, 700, 200, 100);


        //Roll numbers to decide the post-battle prizes
        //Do-While loops make sure the numbers are all different
        Random prizeRand = new Random();

        int randomPrizeOne = prizeRand.nextInt(100) + 1;
        int randomPrizeTwo;
        do {
            randomPrizeTwo = prizeRand.nextInt(100) + 1;
        } while (randomPrizeTwo == randomPrizeOne);

        int randomPrizeThree;
        do {
            randomPrizeThree = prizeRand.nextInt(100) + 1;
        } while (randomPrizeThree == randomPrizeOne || randomPrizeThree == randomPrizeTwo);

        //Update text on prize buttons
        prizeTextUpdate(randomPrizeOne, prizeOne);
        prizeTextUpdate(randomPrizeTwo, prizeTwo);
        prizeTextUpdate(randomPrizeThree, prizeThree);

        //Action listeners to give prizes and switch screen
        prizeOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                erasePreviousScreen(gameWindow);
                rollForPrize(playerFleet, randomPrizeOne);
                displayGameMainMenu(gameWindow, enemyFleet, playerFleet);
            }
        });

        int finalRandomPrizeTwo = randomPrizeTwo;
        prizeTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                erasePreviousScreen(gameWindow);
                rollForPrize(playerFleet, finalRandomPrizeTwo);
                displayGameMainMenu(gameWindow, enemyFleet, playerFleet);
            }
        });

        int finalRandomPrizeThree = randomPrizeThree;
        prizeThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                erasePreviousScreen(gameWindow);
                rollForPrize(playerFleet, finalRandomPrizeThree);
                displayGameMainMenu(gameWindow, enemyFleet, playerFleet);
            }
        });


        //Adding widgets to window
        gameWindow.add(prizeOne);
        gameWindow.add(prizeTwo);
        gameWindow.add(prizeThree);
        gameWindow.add(pickPrize);
    }
}
