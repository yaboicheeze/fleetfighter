package JavaFiles;

import java.util.Random;
import java.util.Vector;

public class Ship {
    private int health;
    private int maxHealth;
    private int armor;
    private int cost;
    private int hitMod;
    private int attackPower;

    protected static int globalArmorBuff = 0;
    protected static int globalAttackBuff = 0;
    protected static int globalHPBuff = 0;
    protected static int globalMaxHPBuff = 0;
    protected static int globalHitModBuff = 0;

    protected static int enemyBuffFrequency = 10;
    protected static int buffRepeater = 0;

    private static int enemyShipsDestroyed = 0;
    private static int damageDone = 0;

    //Ship general constructor
    public Ship(int health, int maxHealth, int armor, int cost, int hitMod, int attackPower, boolean isEnemy) {
        //if statement that applies global variables if the ship does not have the enemy tag (boolean)
        if (!isEnemy) {
            this.health = health + globalHPBuff;
            this.maxHealth = maxHealth + globalMaxHPBuff;
            this.armor = armor + globalArmorBuff;
            this.cost = cost;
            this.hitMod = hitMod + globalHitModBuff;
            this.attackPower = attackPower + globalAttackBuff;
        } else {
            this.health = health;
            this.maxHealth = maxHealth;
            this.armor = armor;
            this.cost = cost;
            this.hitMod = hitMod;
            this.attackPower = attackPower;
        }

    }

    //Getters and Setters
    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getArmor() {
        return this.armor;
    }

    public int getHitMod() {
        return this.hitMod;
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public static int getDamageDone() {
        return damageDone;
    }

    public static int getShipsDestroyed() {
        return enemyShipsDestroyed;
    }

    //In general, these functions will modify the ship's stats
    public static void updateGlobalBuffs(int healthBuff, int maxHPBuff, int armorBuff, int hitModBuff, int attackBuff) {
        globalArmorBuff += armorBuff;
        globalAttackBuff += attackBuff;
        globalHPBuff += healthBuff;
        globalMaxHPBuff += maxHPBuff;
        globalHitModBuff += hitModBuff;
    }

    public void modifyAttributes(int healthDelta, int maxHealthDelta, int armorDelta, int costDelta, int hitModDelta, int attackPowerDelta) {
        this.health += healthDelta;
        this.maxHealth += maxHealthDelta;
        this.armor += armorDelta;
        this.cost += costDelta;
        this.hitMod += hitModDelta;
        this.attackPower += attackPowerDelta;
    }


    //Functions to generate different variations of fleets
    public static void generateEnemyFleet(Vector<Ship> fleet, int roundNum) {
        Random rand = new Random();

        if (roundNum == 50) {
            fleet.add(new EnemyRuinousFortress());
        } else {
            if (roundNum < 5) {
                for (int i = 0; i < 5; i++) {
                    int shipDecidingNum = rand.nextInt(14) + 1;

                    if (shipDecidingNum <= 7) {
                        fleet.add(new EnemyCorvette());
                    } else if (shipDecidingNum <= 13) {
                        fleet.add(new EnemyFrigate());
                    } else {
                        fleet.add(new EnemyBattleship());
                    }
                }
            } else {
                for (int i = 0; i < roundNum; i++) {
                    int shipDecisionBonus = roundNum / 4;
                    int shipDecidingNum = rand.nextInt(20) + 1 + shipDecisionBonus;

                    if (shipDecidingNum <= 8) {
                        fleet.add(new EnemyCorvette());
                    } else if (shipDecidingNum <= 15) {
                        fleet.add(new EnemyFrigate());
                    } else if (shipDecidingNum <= 25) {
                        fleet.add(new EnemyBattleship());
                    } else if (shipDecidingNum <= 35) {
                        fleet.add(new EnemyPhantom());
                    } else {
                        fleet.add(new EnemyRuinousFortress());
                    }
                }
            }
        }
    }

    public static void generateFleet(Vector<Ship> fleet, int roundNum) {
        Random rand = new Random();

        if (roundNum == 50) {
            fleet.add(new RuinousFortress());
        } else {
            if (roundNum < 5) {
                for (int i = 0; i < 5; i++) {
                    int shipDecidingNum = rand.nextInt(14) + 1;

                    if (shipDecidingNum <= 7) {
                        fleet.add(new Corvette());
                    } else if (shipDecidingNum <= 13) {
                        fleet.add(new Frigate());
                    } else {
                        fleet.add(new Battleship());
                    }
                }
            } else {
                for (int i = 0; i < roundNum; i++) {
                    int shipDecisionBonusEasier = (roundNum / 10) % 10;
                    int shipDecisionBonus = roundNum / 5; //For higher difficulty, add this to shipDecidingNum
                    int shipDecidingNum = rand.nextInt(20) + 1 + shipDecisionBonus;

                    if (shipDecidingNum <= 7) {
                        fleet.add(new Corvette());
                    } else if (shipDecidingNum <= 13) {
                        fleet.add(new Frigate());
                    } else if (shipDecidingNum <= 22) {
                        fleet.add(new Battleship());
                    } else if (shipDecidingNum <= 30) {
                        fleet.add(new Phantom());
                    } else {
                        fleet.add(new RuinousFortress());
                    }
                }
            }
        }
    }

    public static void exampleGenerateFleet(Vector<Ship> fleet) {
        fleet.add(new Battleship());
        fleet.add(new Battleship());
        fleet.add(new Frigate());
        fleet.add(new Frigate());
        for (int i = 0; i < 5; i++) {
            fleet.add(new Corvette());
        }
    }

    //Combat related functions
    public static String shipAttack(Ship attacker, Ship target, Vector<Ship> targetFleet) {
        Random rand = new Random();
        int randomRoll = rand.nextInt(20) + 1 + attacker.hitMod;
        System.out.println(attacker);
        if (randomRoll >= target.armor) {
            target.health -= attacker.attackPower;
            if (target.health > 0) {
                damageDone += attacker.attackPower;
                return "Your " + attacker.getClass().getSimpleName() + " has attacked an enemy " + target.getClass().getSimpleName() + " for " + attacker.attackPower + " damage.";
            } else {
                damageDone += attacker.attackPower - target.health;
                enemyShipsDestroyed++;
                targetFleet.remove(target);
                return "Your " + attacker.getClass().getSimpleName() + " has been destroyed by an enemy " + target.getClass().getSimpleName();
            }
        } else {
            return "Your " + attacker.getClass().getSimpleName() + " has failed an attack against an enemy " + target.getClass().getSimpleName();
        }

    }

    public static String enemyShipAttack(Ship attacker, Ship target, Vector<Ship> targetFleet) {
        Random rand2 = new Random();
        int randomRoll = rand2.nextInt(20) + 1 + attacker.hitMod;
        System.out.println(attacker);
        if (randomRoll >= target.armor) {
            target.health -= attacker.attackPower;
            if (target.health > 0) {
                return "An enemy " + attacker.getClass().getSimpleName() + " has attacked your " + target.getClass().getSimpleName() + " for " + attacker.attackPower + " damage.";
            } else {
                targetFleet.remove(target);
                return "An enemy " + attacker.getClass().getSimpleName() + " has been destroyed by your " + target.getClass().getSimpleName();
            }
        } else {
            return "An enemy " + attacker.getClass().getSimpleName() + " has failed an attack against your " + target.getClass().getSimpleName();
        }
    }

    public static String shipDefend(Ship defender) {
        return defender.getClass().getSimpleName() + " has defended";
    }

    public static String enemyShipDefend(Ship defender) {
        return "Enemy " + defender.getClass().getSimpleName() + " has defended";
    }


    //Ship buffs
    public static void defensiveShipBuff(Vector<Ship> fleet) {
        Corvette.updateGlobalBuffs(2, 2, 1, 0, 0); // Apply +3 armor to all future player ships
//        Frigate.updateGlobalBuffs(2, 2, 1, 0, 0); // Apply +3 armor to all future player ships
//        Battleship.updateGlobalBuffs(2, 2, 1, 0, 0); // Apply +3 armor to all future player ships
        for (Ship curShip : fleet) {
            curShip.modifyAttributes(2, 2, 1, 0, 0, 0); // Apply to current ships
        }
    }

    public static void offensiveShipBuff(Vector<Ship> fleet) {
        Corvette.updateGlobalBuffs(0, 0, 0, 1, 2); // +2 attack power and +3 hit modifier for future Corvettes
//        Frigate.updateGlobalBuffs(0, 0, 0, 1, 2);  // +4 attack power and +3 hit modifier for future Frigates
//        Battleship.updateGlobalBuffs(0, 0, 0, 1, 2); // +6 attack power and +3 hit modifier for future Battleships
        for (Ship curShip : fleet) {
            curShip.modifyAttributes(0, 0, 0, 0, 1, 2);
        }
    }

    public static void superShipBuff(Vector<Ship> fleet) {
        Corvette.updateGlobalBuffs(3, 3, 2, 2, 3);
        for (Ship curShip : fleet) {
            curShip.modifyAttributes(3, 3, 2, 0, 2, 3);
        }
    }

    public static void buffEnemyShips(Vector<Ship> fleet, int roundNum){
        if(roundNum%enemyBuffFrequency == 0){
            buffRepeater += 1;
        }
        for(int i=0; i<buffRepeater; i++){
            for (Ship curShip : fleet) {
                curShip.modifyAttributes(2, 2, 1, 0, 2, 1);
            }
        }
    }

}

class Corvette extends Ship {
    public Corvette(){
        super(5, 5, 10, 5, 0, 3, false);
    }

    public static void interceptIncomingFire(){
        // Give selected ship a defence buff
    }
}

class Frigate extends Ship {
    public Frigate(){
        super(10, 10, 14, 10, 3, 6, false);
    }

    public static void repairAllyShip(){
        // Heal selected ship for an amount
    }
}

class Battleship extends Ship {
    public Battleship(){
        super(20, 20, 18, 15, 6, 9, false);
    }

    public static void provideAllyAttackBuff(){
        // Give selected ship an attack buff
    }
}

class Phantom extends Ship {
    public Phantom(){
        super(30, 30, 23, 100000, 9, 13, false);
    }
}

class RuinousFortress extends Ship {
    public RuinousFortress(){
        super(100, 100, 15, 10000000, 20, 16, false);
    }
}




class EnemyCorvette extends Ship {
    public EnemyCorvette(){
        super(5, 5, 10, 5, 0, 3, true);
    }

    public static void interceptIncomingFire(){
        // Give selected ship a defence buff
    }
}

class EnemyFrigate extends Ship {
    public EnemyFrigate(){
        super(10, 10, 14, 10, 3, 6, true);
    }

    public static void repairAllyShip(){
        // Heal selected ship for an amount
    }
}

class EnemyBattleship extends Ship {
    public EnemyBattleship(){
        super(20, 20, 18, 15, 6, 9, true);
    }

    public static void provideAllyAttackBuff(){
        // Give selected ship an attack buff
    }
}

class EnemyPhantom extends Ship {
    public EnemyPhantom(){
        super(30, 30, 23, 100000, 9, 13, true);
    }
}

class EnemyRuinousFortress extends Ship {
    public EnemyRuinousFortress(){
        super(100, 100, 15, 10000000, 20, 16, true);
    }
}