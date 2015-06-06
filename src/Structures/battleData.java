package Structures;

import Characters.Skills.Skill;
import Characters.Skills.fleeObject;
import Characters.combatEffect;
import Characters.gameCharacter;
import Profile.Game;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 4/9/15.
 */
//a class that represents an individual unit's turn in the midst of a battle.
public class battleData implements Data {
    private String attackerSpeed; //the attacker's speed; used for sorted insertion
    private gameCharacter Attacker; //the character whose turn this datum represents
    private int targetIndex; //index of the character being attacked (or healed, etc)
    private combatEffect toCast; //the combat effect that the character is using this turn
    private boolean playerSide; //is the attacker on the player's side?
    private boolean primaryTarget = true;

    //default constructor
    public battleData() {
    }

    //constructor with attacker argument
    public battleData(gameCharacter whoseTurn) {
        attackerSpeed = Integer.toString(whoseTurn.getTempSpd());
        Attacker = whoseTurn;
    }

    public void setPlayerSide(boolean isPlayerSide){
        playerSide = isPlayerSide;
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
        toCast = tocast;
        if (toCast.getAoE() == -1) { //if this is a single target user-only skill.
            targetIndex = 0; //set target index to 0.
            primaryTarget = false;
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
        targetIndex = toTarget;
        return targetIndex; //if this value is 0, a user cancelled the target decision and will
                            //need to select different skill in Battle class's interface.
    }

    public int getTargetIndex(){
        return targetIndex;
    }

    //executes the skill, used when this piece of data is popped from the
    //LLL structure in the battle class. returns the defender.
    public void executeCombat(gameCharacter Defender) throws fleeObject{
        if(Attacker.isAlive()){
            if(targetIndex == 0) { //if the character is targeting themselves
                Attacker.printName(); //display a simple message
                Game.battle.getInterface().printLeft(Attacker.getName() +
                        " used " + ((Data) toCast).returnKey() + "!!");
                Defender = Attacker; //change defender to attacker.
            }
            if(Defender != null) {
                if (Defender.isAlive() == toCast.notUsableOnDead()) {
                    if (primaryTarget) { //only display this message once.
                        if (toCast.isOffensive()) {
                            Game.battle.getInterface().printLeft(Attacker.getName() +
                                    " attacked " + Defender.getName() + " with " +
                                    ((Data)toCast).returnKey() + "!!");
                            Defender = Attacker; //change defender to attacker.
                            if(toCast.getAoE() > 0)
                                Game.battle.getInterface().printLeft(Attacker.getName() +
                                        "Their teammates were struck as well!", 2);
                            if(Defender.isAlive() && Attacker.isAlive())
                                Defender.executeCounter(Attacker);
                        } else {
                            Game.battle.getInterface().printLeft(Attacker.getName() +
                                    " cast " + Defender.getName() + " on " +
                                    ((Data) toCast).returnKey() + "!!");
                            if(toCast.getAoE() > 0)
                                Game.battle.getInterface().printLeft(Attacker.getName() +
                                        "Their teammates were affected as well!", 2);
                        }
                        primaryTarget = false; //after the first cast, the defender is no
                    }                          //longer the primary target.
                    try {
                        toCast.takeAction(Attacker, Defender);
                    }
                    catch(fleeObject Caught){
                        throw Caught; //if the "attacker" fled, throw the caught flee object
                    }
                }
            }
        }
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
        return attackerSpeed;
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

    @Override
    public void setKey(String name) {
        attackerSpeed = name;
    }

    @Override
    public int compareTo(String toCompare) {
        return attackerSpeed.compareTo(toCompare);
    }

    @Override
    public int compareTo(Data toCompare) {
        return attackerSpeed.compareTo(toCompare.returnKey());
    }
}
