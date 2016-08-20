package Characters;

import Characters.Skills.Skill;
import Characters.Stats;
import Characters.gameCharacter;
import Profile.Game;
import javafx.scene.input.KeyEvent;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/6/15.
 */
public abstract class Monster extends gameCharacter{
    protected Skill Combo; //skill reference to let us chain skills with the AI
    protected Skill [] monsterSkills = new Skill[6]; //skill array
    protected int [] skillProbabilities = new int[6]; //chance of each skill
    protected boolean hasBeenStolenFrom = false;

    //default constructor
    public Monster(){}

    //constructor with name
    public Monster(String toName){
        Name = toName;
    }

    //constructor with name and stats object
    public Monster(String toName, Stats toCopy){
        super(toName, toCopy); //copy stats
    }

    //constructor with name and stats
    public Monster(String toName, int hp, int sp, int str, int dex, int spd, int vit, int inte, int fth, int arm){
        super(toName, hp, sp, str, dex, spd, vit, inte, fth, arm);
    }

    public abstract int getExp(); //abstract functions to return variable amounts of exp and jexp.
    public abstract int getJexp();

    //initializes combat data; filling it for non-player chars.
    @Override
    public void initializeCombatData(){
        //set currently targeting based on the return value of an initializeSkill
        //call to commands based on a skill that we have chosen.
        currentlyTargeting = Commands.initializeSkill(chooseSkill());
        if(currentlyTargeting == 0) //if we're currently targeting ourselves
            Commands.setTarget(0); //set target index to 0.
        else{
            Commands.setTarget(chooseTarget(Game.battle.getParty(currentlyTargeting > 0),
                    Game.battle.getMinions(currentlyTargeting > 0), Commands.notUsableOnDead()));
            //otherwise, we'll need the more detailed targeting algorithm written in chooseTarget.
        }
    }
    
    //handles a piece of input. does nothing for non-player characters
    @Override
    public void handleInput(KeyEvent toHandle){
        //literally do nothing with the input; this is a monster
    }

    @Override
    public boolean hasTwoHandedWeapon() {
        return true;
    }

    //sets the monster's combo to the passed skill index
    public void setCombo(int toSet){
        if(toSet < 0 || toSet > 5)
            return; //error
        Combo = monsterSkills[toSet];
    }

    //the AI for choosing a target from two arrays of targets. Targets characters rather
    //than minions 2/3 of the time to avoid minion stacking as a defense mechanism. returns
    //what is effectively an index specified between the two arrays.
    public int chooseTarget(gameCharacter [] chars, gameCharacter [] mins, boolean notUsableOnDead){
        int playsidemod = 1;
        if(Commands.isPlayerSide())
            playsidemod = -1; //reverse commands if the monster is on the player side.
        Random Rand = new Random();
        int roll = Rand.nextInt(12);
        if(roll < 8) { //it's more likely to target a character than a minion
            roll %= 4; //get a number between 0 and 3
            if (chars[roll] != null) {
                if (chars[roll].isAlive() == notUsableOnDead) {
                    return (roll + 1)*playsidemod; //if the character exists and is alive, return roll + 1
                }
            } //if we get here, the initial roll chose a nonexistent/dead character
            //we use a slightly less-than-optimal algorithm that privileges proximity rather than just
            //a sequential search of the array for a valid target; we check 1 above and 1 below, then 2
            //above and 2 below and so on until a valid target is found.
            for (int i = 1; i < 4; ++i) { //first we search every other character in the array
                if ((roll + i) < 4) { //if this index is part of the array
                    if (chars[roll + i] != null) {
                        if (chars[roll + i].isAlive() == notUsableOnDead)
                            return (roll + i + 1)*playsidemod; //return the character's index if they exist
                    }
                }
                if ((roll - i) >= 0) { //if this index is part of the array
                    if (chars[roll - i] != null) {
                        if (chars[roll - i].isAlive() == notUsableOnDead)
                            return (roll - i + 1)*playsidemod; //return the character's index if they exist
                    }
                }
            }
            if(mins[roll] != null){
                if(mins[roll].isAlive() == notUsableOnDead)
                    return (roll + 5)*playsidemod; //if a minion at the index exists, target them.
            } //otherwise, search for another minion.
            for (int j = 1; j < 4; ++j){
                if ((roll + j) < 4) { //if this index is part of the array
                    if (mins[roll + j] != null) {
                        if (mins[roll + j].isAlive() == notUsableOnDead)
                            return (roll + j + 5)*playsidemod; //return the minion's index if they exist
                    }
                }
                if ((roll - j) >= 0) { //if this index is part of the array
                    if (mins[roll - j] != null) {
                        if (mins[roll - j].isAlive() == notUsableOnDead)
                            return (roll - j + 5)*playsidemod; //return the minion's index if they exist
                    }
                }
            }
        }
        else{ //if it's a minion that we're targeting; minions' positions are offset by 4.
            roll = roll % 4; //again, get a number between 0 and 3
            if (mins[roll] != null) {
                if (mins[roll].isAlive() == notUsableOnDead){
                    return (roll + 5)*playsidemod; //if the minion exists and is alive, return roll + 5
                }
            } //if we get here, the initial roll chose a nonexistent/dead minion
            for (int i = 1; i < 4; ++i) { //first we search every other character in the array
                if ((roll + i) < 4) { //if this index is part of the array
                    if (mins[roll + i] != null) {
                        if (mins[roll + i].isAlive() == notUsableOnDead)
                            return (roll + i + 5)*playsidemod; //return the minion's index if they exist
                    }
                }
                if ((roll - i) >= 0) { //if this index is part of the array
                    if (mins[roll - i] != null) {
                        if (mins[roll - i].isAlive() == notUsableOnDead)
                            return (roll - i + 5)*playsidemod; //return the character's index if they exist
                    }
                }
            }
            if(chars[roll] != null){ //next we check for a character at this index
                if(chars[roll].isAlive() == notUsableOnDead)
                    return (roll + 1)*playsidemod; //if a character at the index exists, target them.
            } //otherwise, search for another character.
            for (int j = 1; j < 4; ++j){
                if ((roll + j) < 4) { //if this index is part of the array
                    if (chars[roll + j] != null) {
                        if (chars[roll + j].isAlive() == notUsableOnDead)
                            return (roll + j + 1)*playsidemod; //return the character's index if they exist
                    }
                }
                if ((roll - j) >= 0) { //if this index is part of the array
                    if (chars[roll - j] != null) {
                        if (chars[roll - j].isAlive() == notUsableOnDead)
                            return (roll - j + 1)*playsidemod; //return the character's index if they exist
                    }
                }
            }
        }
        return 0; //there is not a viable target if we get to this line. bug.
    }

    //basically the AI for choosing a skill to use.
    public combatEffect chooseSkill(){
        if(Combo != null)
            return Combo; //return combo if we're in the midst of a combo
        //int i = Math.random();
        Random Rand = new Random();
        int roll = Rand.nextInt(100) + 1;
        for(int j = 0; j < 6; ++j){ //for each skill
            if(roll <= skillProbabilities[j]) { //if the roll is less than the probability...
                if(!monsterSkills[j].canUse(this))
                    return chooseSkill(); //reroll if we don't have mana for this skill or something
                return monsterSkills[j]; //otherwise return the corresponding skill
            }
            else
                roll -= skillProbabilities[j]; //otherwise decrement the roll and continue looping
        }
        return monsterSkills[0]; //return index 0 as default to avoid bugs
    }

    //build the monster's skillset.
    public abstract void buildSkills();

    @Override //apply the monster's passive buff
    public void applyAutoBuffs() {
        tempProperty = charProperty;
        setTemps();
        if(currentPassive != null)
            currentPassive.passiveEffect(this);
    }

    //since monsters have no weapons, it is assumed that this is always true.
    //this and the following functions are in place to allow reusability
    //between player skills that hinge on specific player character class
    //attributes and monsters that lack those attributes.
    public boolean hasWeaponType(String toCompare, boolean isRight){
        return true;
    }

    //compares the monster's defense property with the requested property.
    public boolean hasWeaponProperty(String toCompare, boolean isRight){
        if(tempProperty.getName().compareTo(toCompare) == 0)
            return true;
        return false;
    }

    //monsters use their current defense property as their attack property.
    public String getWeaponProperty(boolean isRight){
        return tempProperty.getName();
    }

    //since monsters have no real weapon, their weapon damage is registered
    //as their highest offensive base stat.
    public int getWeaponDamage(boolean isRight){
        int Highest = Fth;
        if(Int > Highest)
            Highest = Int;
        if(Str > Highest)
            Highest = Str;
        if(Dex > Highest)
            Highest = Dex;
        return Highest;
    }

    public boolean hasBeenStolenFrom(){
        return hasBeenStolenFrom;
    }

    public void setHasBeenStolenFrom(boolean toset){
        hasBeenStolenFrom = toset;
    }
}
