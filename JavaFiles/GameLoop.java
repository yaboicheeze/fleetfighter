package JavaFiles;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import static JavaFiles.GameScreen.displayOwnFleet;
import static JavaFiles.GameScreen.updateText;
import static JavaFiles.Ship.*;


public class GameLoop {
    private static int roundNum = 1;
    private static int playerGold = 100;
    private static int numCorvettesBought = 0;
    private static int numFrigatesBought = 0;
    private static int numBattleshipsBought = 0;

    private static int corvettePrice = 5;
    private static int frigatePrice = 10;
    private static int battleshipPrice = 15;

    public static int getRoundNum(){return roundNum;}

    public static void setCorvButtonName(JButton corvBuyButton){
        corvBuyButton.setText("Buy Corvette - " + corvettePrice + " Credits");
    }

    public static void setFrigButtonName(JButton frigBuyButton){
        frigBuyButton.setText("Buy Frigate - " + frigatePrice + " Credits");
    }

    public static void setBatShipButtonName(JButton bsBuyButton){
        bsBuyButton.setText("Buy Battleship - " + battleshipPrice + " Credits");
    }

    public static void buyCorvette(Vector<Ship> fleet, JButton corvBuyButton){
        if(playerGold >= corvettePrice){
            corvettePrice = 5 + (numCorvettesBought+1);
            playerGold = playerGold - corvettePrice + 1;
            corvBuyButton.setText("Buy Corvette - " + corvettePrice + " Credits");
            numCorvettesBought++;
            fleet.add(new Corvette());
        }
    }

    public static void buyFrigate(Vector<Ship> fleet, JButton frigBuyButton){
        if(playerGold >= frigatePrice){
            frigatePrice = 10 + ((numFrigatesBought+1)*2);
            playerGold = playerGold - frigatePrice + 2;
            frigBuyButton.setText("Buy Frigate - " + frigatePrice + " Credits");
            numFrigatesBought++;
            fleet.add(new Frigate());
        }
    }

    public static void buyBattleship(Vector<Ship> fleet, JButton bsBuyButton){
        if(playerGold >= battleshipPrice){
            battleshipPrice = 15 + ((numBattleshipsBought+1) * 3);
            playerGold = playerGold - battleshipPrice + 3;
            bsBuyButton.setText("Buy Battleship - " + battleshipPrice + " Credits");
            numBattleshipsBought++;
            fleet.add(new Battleship());
        }
    }

    public static void displayPlayerStats(JLabel statsWidget){
        statsWidget.setText("<html>You have destroyed " + getShipsDestroyed() + " ships<br/>You have dealth " + getDamageDone() + " damage<html>");
    }

//    public static void displayGold(JLabel goldWidget){
//        goldWidget.setText(playerGold + " Credits");
//    }

    public static void displayGold(JLabel goldWidget){
        goldWidget.setText("<html>" + playerGold + " Credits<br/><br/>"
        + "Corvettes bought: " + numCorvettesBought + "<br/>"
        + "Frigates bought: " + numFrigatesBought + "<br/>"
        + "Battleships bought: " + numBattleshipsBought + "<html>");
    }

//    public static void displayGold(JLabel goldWidget){
//        goldWidget.setText(Integer.toString(playerGold) + " Credits\n\n"
//        + "Corvettes bought: " + Integer.toString(numCorvettesBought) + "\n"
//        + "Frigates bought: " + Integer.toString(numFrigatesBought) + "\n"
//        + "Battleships bought: " + Integer.toString(numBattleshipsBought) + "\n");
//    }

//    public static void pauseProgram(){
//        Timer timer = new Timer(1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//    }

    public static void goldPrize(){
        playerGold += roundNum*6;
    }

    public static void goldBigPrize() {
        playerGold += roundNum*14;
    }

    public static void basicShipLootBox(Vector<Ship> fleet) {
        Random lootBoxRand = new Random();
        int boxRoll = lootBoxRand.nextInt(100) + 1 + roundNum;

        if(boxRoll <= 25){
            fleet.add(new Battleship());
        }
        else if(boxRoll <= 65){
            fleet.add(new Battleship());
            fleet.add(new Battleship());
        }
        else if(boxRoll <= 90){
            fleet.add(new Battleship());
            fleet.add(new Battleship());
            fleet.add(new Battleship());
        }
        else if(boxRoll <= 110){
            fleet.add(new Phantom());
            fleet.add(new Battleship());
            fleet.add(new Battleship());
        }
        else if(boxRoll <= 120){
            fleet.add(new Phantom());
            fleet.add(new Phantom());
            fleet.add(new Battleship());
        }
        else if(boxRoll <= 130){
            fleet.add(new Phantom());
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 140){
            fleet.add(new RuinousFortress());
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 150){
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            fleet.add(new Phantom());
        }
        else {
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            for(int i = 1; i < roundNum/50; i++){
                fleet.add(new RuinousFortress());
            }
        }
    }

    public static void improvedShipLootBox(Vector<Ship> fleet) {
        Random lootBoxRand = new Random();
        int boxRoll = lootBoxRand.nextInt(100) + 1 + roundNum;

        if(boxRoll <= 60){
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 80){
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 100){
            fleet.add(new Phantom());
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 110){
            fleet.add(new RuinousFortress());
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 120){
            fleet.add(new RuinousFortress());
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 130){
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            fleet.add(new Phantom());

        }
        else if(boxRoll <= 140){
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
        }
        else {
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            for(int i = 1; i < roundNum/35; i++){
                fleet.add(new RuinousFortress());
            }
        }
    }

    //Used if I make a special prize every 5 levels
    //Maybe also include a defensive/offensive buff
    //see rollForSpecialPrize in GameScreen.java
    public static void specialShipLootBox(Vector<Ship> fleet) {
        Random lootBoxRand = new Random();
        int lootBoxMod = roundNum/4;
        int boxRoll = lootBoxRand.nextInt(20) + 1 + lootBoxMod;

        if(boxRoll <= 10){
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 15){

            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 20){
            fleet.add(new Phantom());
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 30){
            fleet.add(new RuinousFortress());
            fleet.add(new Phantom());
            fleet.add(new Phantom());
        }
        else if(boxRoll <= 35){
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            fleet.add(new Phantom());
        }
        else{
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            fleet.add(new RuinousFortress());
            for(int i = 1; i < roundNum/15; i++){
                fleet.add(new RuinousFortress());
            }
        }
    }

    public static void postWinEffects(JLabel combatLog){
        combatLog.setText("Congratulations! You live to fight another day.");
        if (roundNum <= 3){
            playerGold += 15;
        }
        else{
            playerGold += (int) Math.pow(roundNum, 1.85);
        }

        roundNum++;
    }

    public static void displayRoundNumber(JLabel roundNumLabel){
        roundNumLabel.setText("Round " + roundNum);
    }

    public static void combatLoop(Vector<Ship> enemyFleet, Vector<Ship> playerFleet, JLabel combatLog, JButton attackButton, JButton defendButton, JList<String> playerFleetDisplay, JList<String> enemyFleetDisplay, JButton backToMainMenu, JButton sendToPrize){
//        Random rand = new Random();
//        final String[] playerAction = new String[]{"attack"};
        int turnNum = 0;
        //This needs to be written wherever you need a random number --> rand.nextInt(20) + 1;

        //Setup
        if(enemyFleet.isEmpty()){
            generateEnemyFleet(enemyFleet, roundNum);
        }
//        if(playerFleet.isEmpty()){
//            generateFleet(playerFleet, roundNum);
//        }
//        exampleGenerateFleet(enemyFleet);
//        exampleGenerateFleet(playerFleet);

//        attackButton.addActionListener(e -> {
//
//            }
//        });

//        defendButton.addActionListener(e -> {
//
//            }
//        });


        //Sorting Turn Order
        Queue<Ship> turnOrder = new LinkedList<>();

        int maxFleetSize = Math.min(playerFleet.size(), enemyFleet.size());

        for (int i = 0; i < maxFleetSize; i++) {
            turnOrder.add(playerFleet.get(i));
            turnOrder.add(enemyFleet.get(i));
        }

        if (playerFleet.size() > maxFleetSize) {
            turnOrder.addAll(playerFleet.subList(maxFleetSize, playerFleet.size()));
        }
        if (enemyFleet.size() > maxFleetSize) {
            turnOrder.addAll(enemyFleet.subList(maxFleetSize, enemyFleet.size()));
        }

        //Combat officially starts here
        while (!playerFleet.isEmpty() && !enemyFleet.isEmpty()) {
            displayOwnFleet(playerFleet, playerFleetDisplay);
            displayOwnFleet(enemyFleet, enemyFleetDisplay);
            Ship currentShip = turnOrder.poll();

            //Write combat logic here
            if (playerFleet.contains(currentShip)){
                System.out.println("Player turn " + turnNum);
                //Player turn
//                combatLog.setText("Your turn! Choose an action (Attack or Defend).");
                updateText(shipAttack(currentShip, enemyFleet.firstElement(), enemyFleet), combatLog);

            }
            else{
                combatLog.setText("Enemy's Turn");
                System.out.println("Enemy turn " + turnNum);
                //Enemy Turn
                updateText(enemyShipAttack(currentShip, playerFleet.firstElement(), playerFleet), combatLog);


//                Same as player, except action is randomly determined. For sake of simplicity (for now), exclude special actions that make user select a ship.
//                int actionDecision = rand.nextInt(20) + 1;
//                if (actionDecision <= 15) {
//                    updateText(enemyShipAttack(currentShip, playerFleet.firstElement(), playerFleet), combatLog);
//                }
//                else{
//                    updateText(enemyShipDefend(currentShip), combatLog);
//                }
            }

            //Increase turn number and re-display ships
            turnNum++;
            displayOwnFleet(playerFleet, playerFleetDisplay);
            displayOwnFleet(enemyFleet, enemyFleetDisplay);


//            Prospective Timer
//            Timer pauseTimer = new Timer(2000, e -> {
//
//            });
//            pauseTimer.setRepeats(false);
//            pauseTimer.start();

            //re-queue ships into the turn order
            if(turnOrder.isEmpty()){
                maxFleetSize = Math.min(playerFleet.size(), enemyFleet.size());

                for (int i = 0; i < maxFleetSize; i++) {
                    turnOrder.add(playerFleet.get(i));
                    turnOrder.add(enemyFleet.get(i));
                }

                if (playerFleet.size() > maxFleetSize) {
                    turnOrder.addAll(playerFleet.subList(maxFleetSize, playerFleet.size()));
                }
                if (enemyFleet.size() > maxFleetSize) {
                    turnOrder.addAll(enemyFleet.subList(maxFleetSize, enemyFleet.size()));
                }
            }


            //TODO: Find a way to pause program so player can read combat log. Maybe have different speed settings linked to a couple different buttons in the action button menu



        }



        //Post-Combat
        if (playerFleet.isEmpty()) {
            combatLog.setText("You've lost! Game over.");
            enemyFleet.clear();
            backToMainMenu.setVisible(true);
        } else if (enemyFleet.isEmpty()) {
            postWinEffects(combatLog);
            sendToPrize.setVisible(true);
        }
        else{
            combatLog.setText("No idea what happened man. Apparently both or neither fleets are empty.");
            backToMainMenu.setVisible(true);
        }
    }
}
