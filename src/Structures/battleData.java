package Structures;

import Characters.combatEffect;
import Characters.gameCharacter;

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

    //constructor with attacker argument and boolean denoting the side
    //that the attacker is on.
    public battleData(gameCharacter whoseTurn, boolean isPlayerSide) {
        attackerSpeed = Integer.toString(whoseTurn.getTempSpd());
        Attacker = whoseTurn;
        playerSide = isPlayerSide;
    }

    //chooses a skill via AI or user input, depending.
    //returns whether or not the skill will be targeting the player (true)
    //or an enemy (false).
    public boolean initializeSkill() {
        toCast = Attacker.chooseSkill();
        if (toCast.getAoE() == -1) { //if this is a single target user-only skill.
            targetIndex = 0; //set target index to 0.
            primaryTarget = false;
        }
        if (!playerSide)
            return toCast.isOffensive();
        else
            return !toCast.isOffensive();
    }

    //choose a target from a pair of passed arrays of game characters. isAlly is a positive or
    //negative integer that turns the returned int from chooseTarget into meaningful data because
    //negatives denote a non-player target whereas positive values denote a player target.
    public int initializeTarget(gameCharacter[] CTargets, gameCharacter[] MTargets, int isAlly){
        if(primaryTarget == false) { //if we set this to false because this is a single target skill
            return 1; //return 1, an arbitrary value. we already set target index in initializeSkill.
        }
        targetIndex = isAlly * Attacker.chooseTarget(CTargets, MTargets);
        return targetIndex; //if this value is 0, a user cancelled the target decision and will
                            //need to select different skill in Battle class's interface.
    }

    public int getTargetIndex(){
        return targetIndex;
    }

    //executes the skill, used when this piece of data is popped from the
    //LLL structure in the battle class. returns the defender.
    public void executeCombat(gameCharacter Defender){
        if(Attacker.isAlive() && Defender.isAlive()) {
            if(targetIndex == 0) { //if the character is targeting themselves
                Attacker.printName(); //display a simple message
                System.out.println(" used ");
                toCast.printName();
                System.out.println("!!");
                Defender = Attacker; //change defender to attacker.
            }
            if (primaryTarget) { //only display this message once.
                if (toCast.isOffensive()) {
                    Attacker.printName();
                    System.out.print(" attacked ");
                    Defender.printName();
                    System.out.print(" with ");
                    toCast.printName();
                    System.out.println("!!");
                } else {
                    Attacker.printName();
                    System.out.print(" used ");
                    toCast.printName();
                    System.out.print(" on ");
                    Defender.printName();
                    System.out.println("!!");
                }
                primaryTarget = false; //after the first cast, the defender is no
            }                          //longer the primary target.
            toCast.takeAction(Attacker, Defender);
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
