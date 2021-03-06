package Structures;

import Characters.Inventory.Consumable;
import Characters.Inventory.Item;
import Characters.Skills.Flee;
import Characters.Skills.Skill;
import Characters.Skills.Wait;
import Characters.Skills.fleeObject;
import Characters.combatEffect;
import Characters.gameCharacter;
import Profile.Game;
import javafx.scene.media.Media;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by Miles Sanguinetti on 4/9/15.
 */
//a class that represents an individual unit's turn in the midst of a battle.
public class battleData implements Data, Serializable {
    private int attackerSpeed; //the attacker's speed; used for sorted insertion
    private gameCharacter Attacker; //the character whose turn this datum represents
    private int targetIndex; //index of the character being attacked (or healed, etc)
    private combatEffect toCast; //the combat effect that the character is using this turn
    private boolean playerSide; //is the attacker on the player's side?
    private boolean primaryTarget = true;
    private static boolean isAnimating;

    //default constructor
    public battleData() {
    }

    //constructor with attacker argument
    public battleData(gameCharacter whoseTurn) {
        Attacker = whoseTurn;
    }

    public void setPlayerSide(boolean isPlayerSide){
        playerSide = isPlayerSide;
    }

    public boolean attackerIsDead(){
        return (!Attacker.isAlive());
    }

    //wipes the target index and skill selection data
    public void Wipe(){
        toCast = null; //set tocast to null
        targetIndex = 10; //use a relatively large value to differentiate from realistic values -8 through 8.
    }

    //returns whether or not the gamecharacter owning this piece of battle data has filled it with a target or not.
    public boolean isFilled(){
        if(toCast != null && targetIndex != 10)
            return true;
        return false;
    }

    //sets tocast to a passed skill
    //returns whether or not the skill will be targeting the player (true)
    //or an enemy (false).
    public int initializeSkill(combatEffect tocast) {
        attackerSpeed = Attacker.getTempSpd(); //get the attacker's speed.
        primaryTarget = true; //primary target needs to be reset to true.
        toCast = tocast;
        if (toCast.getAoE() == -1) { //if this is a single target user-only skill.
            targetIndex = 0; //set target index to 0.
            return 0;
        }
        if (!playerSide) {
            if(toCast.isOffensive()){
                if(toCast.notUsableOnDead())
                    return 1; //targeting living player characters
                return 2; //targeting dead player characters
            }
            if(toCast.notUsableOnDead())
                return -1; //targeting living enemies of the player
            return -2; //targeting dead enemies of the player
        }
        else {
            if(toCast.isOffensive()){
                if(toCast.notUsableOnDead())
                    return -1; //targeting living enemies of the player
                return -2; //targeting dead enemies of the player
            }
            if(toCast.notUsableOnDead())
                return 1; //targeting living player characters
            return 2; //targeting dead player characters
        }
    }

    public boolean isPlayerSide(){
        return playerSide;
    }

    //set the battledata's target
    public int setTarget(int toTarget){
        if(toTarget != 0 && toCast instanceof Consumable){
            Game.Player.Remove((Item)toCast);
        }
        targetIndex = toTarget;
        return targetIndex; //if this value is 0, a user cancelled the target decision and will
                            //need to select different skill in Battle class's interface.
    }

    public int getTargetIndex(){
        return targetIndex;
    }

    //executes the skill, used when this piece of data is popped from the
    //LLL structure in the battle class. returns true if the attacker was dead.
    public boolean executeCombat(gameCharacter Defender) throws fleeObject{
        if(Attacker.isAlive()){
            if(targetIndex == 0) { //if the character is targeting themselves
                //display a simple message
                Defender = Attacker; //change defender to attacker.
            }
            if(Defender != null) {
                if (Defender.isAlive() == toCast.notUsableOnDead()) {
                    boolean succeeded = true;
                    if(toCast.isOffensive())
                        succeeded = !Defender.didCounterEvade(Attacker);
                    if (primaryTarget) { //only display this message once.
                        toCast.performAnimation(Game.battle.getPane(), Attacker, Defender);
                        if (succeeded && toCast.isOffensive()) {
                            Game.battle.getInterface().printLeft(Attacker.getName() +
                                    " attacked " + Defender.getName() + " with " + ((Data) toCast).returnKey() + "!!");
                            if (toCast.getAoE() > 0)
                                Game.battle.getInterface().printLeftAtNextAvailable("Their teammates were struck as well!");
                            if(Defender.isAlive() && Attacker.isAlive())
                                Defender.executeCounter(Attacker);
                        } else if(succeeded){
                            if(Defender != Attacker) {
                                Game.battle.getInterface().printLeft(Attacker.getName() +
                                        " cast " + ((Data) toCast).returnKey() + " on " +
                                        Defender.getName() + "!!");
                            }
                            else if(!(toCast instanceof Flee) && !(toCast instanceof Wait)){
                                Game.battle.getInterface().printLeft(Attacker.getName() +
                                        " cast " + ((Data) toCast).returnKey() + "!!");
                            }
                            if(toCast.getAoE() > 0)
                                Game.battle.getInterface().printLeftAtNextAvailable("Their teammates were affected as well!");
                        }
                        primaryTarget = false; //after the first cast, the defender is no
                    }                          //longer the primary target.
                    try {
                        if(succeeded) {
                            toCast.takeAction(Attacker, Defender);
                            if (toCast instanceof Item)
                                Game.battle.playMedia("basicattack");
                            else
                                Game.battle.playMedia("skill");
                        }
                    }
                    catch(fleeObject Caught){
                        throw Caught; //if the "attacker" fled, throw the caught flee object
                    }
                }
            }

            return false; //if we got here, the attacker is alive.
        }
        if(toCast instanceof Consumable)
            Game.Player.Insert((Item)toCast);
        return true; //if the attacker is dead, return true and refund any items that were used as combat effects..
    }

    //returns the number of characters that the used skill's aoe will rebound through
    public int getRebound(){
        if(toCast == null || !Attacker.isAlive())
            return 0; //if we didn't cast anything or the attacker is dead, not a whole
                      //lot of stuff is going to be rebounding.
        return toCast.getAoE();
    }

    //ends a turn by executing SP loss.
    public void endCombat(){
        if(toCast == null || !Attacker.isAlive())
            return;
        toCast.spLoss(Attacker);
    }

    @Override
    public void writeOut(PrintWriter toWrite) {
        toWrite.println(attackerSpeed);
    }

    @Override
    public String returnKey() {
        return Integer.toString(attackerSpeed);
    }

    @Override
    public void Display() {
        if(Attacker != null)
            Attacker.printName();
    }

    @Override
    public void Display(int indent) {
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        Attacker.printName();
    }

    public boolean notUsableOnDead(){
        if (toCast == null)
            return true;
        return toCast.notUsableOnDead();
    }

    public static boolean IsAnimating(){
        return isAnimating;
    }

    public static void setAnimating(boolean toset){
        isAnimating = toset;
    }

    @Override
    public void setKey(String name) {
        attackerSpeed = Integer.parseInt(name);
    }

    @Override
    public int compareTo(String toCompare) {
        return Integer.parseInt(toCompare) - attackerSpeed;
    }

    @Override
    public int compareTo(Data toCompare) {
        return Integer.parseInt(toCompare.returnKey()) - attackerSpeed;
    }
}
