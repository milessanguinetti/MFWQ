package Characters;

import Characters.Properties.Property;
import Characters.Skills.Skill;
import Characters.Skills.passiveSkill;
import Characters.Status.damageEffect;
import Characters.Status.endOfTurn;
import Characters.Status.statChange;
import Structures.LLLnode;
import Structures.statusEffectData;
import Structures.statusStructure;

/**
 * Created by Miles Sanguinetti on 3/17/15.
 */

//abstract character base class--derived from stats
public abstract class gameCharacter extends Stats {
    protected String Name; //character name
    protected passiveSkill currentPassive; //the character's passive skill.
    protected Property charProperty; //the character's property
    protected Property tempProperty; //temporary battle property based on buffs
    private statusStructure statChangeList; //LLL structure for effects that change stats
    private statusStructure damageEffectList; //effects that impact damage taken
    private statusStructure endOfTurnList; //effects that take place at the end of a turn


    //default constructor
    public gameCharacter(){}

    //constructor with name
    public gameCharacter(String toName){
        Name = toName;
    }

    //constructor with name and stats
    public gameCharacter(String toName, Stats toAdd){
        super(toAdd);
        Name = toName;
    }

    //retrieves a combat effect to use during the character's turn.
    public abstract combatEffect chooseSkill();

    //picks a target (via AI or input, depending on whether this is a monster, player or minion)
    //from two passed arrays of game characters representing appropriate targets--minions or game chars
    public abstract int chooseTarget(gameCharacter[] CTargets, gameCharacter[] MTargets);

    //takes damage; returns remaining health.
    boolean takeDamage(int toTake, String property){
        toTake = calculateDamage(toTake, property); //take status into account
        if(toTake <= 0)
            return isAlive();
        toTake = tempProperty.calculateDamage(toTake, property); //take property into account
        if(toTake <= 0){
            System.out.println("The attack had no effect.");
            return(isAlive());
        }
        toTake -= tempArmor; //subtract armor from damage value
        if(toTake <= 0)
            toTake = 1; //damage is 1 minimum
        subtractHP(toTake); //take the damage
        System.out.println(Name + " took " + toTake + " damage.");
        return(isAlive());
    }

    //takes absolute damage, ignoring defenses; returns remaining health
    boolean takeAbsoluteDamage(int toTake){
        if(toTake > 0)
            System.out.println(Name + " took " + toTake + " damage.");
        else
            System.out.println(Name + " was healed for " + toTake + ".");
        return(subtractHP(toTake));
    }

    //print name, but not stats
    public void printName(){
        System.out.print(Name);
    }

    //full display function
    public void Display(){
        System.out.println(Name); //print the character's name
        super.Display(); //display the character's stats, too
    }

    //function for dropping loot at the end of a battle.
    //if a player character dies, they drop their equipment
    //so that it isn't lost.
    public abstract void Loot();

    public abstract void applyAutoBuffs();

    //removes all status effects; returns true if we need to readjust temp stats
    public boolean clearStatus(){
        boolean statAdjustment = (statChangeList.Peek() != null);
        //checks if we're going to need to adjust stats
        statChangeList.removeAll();
        damageEffectList.removeAll();
        endOfTurnList.removeAll();
        return statAdjustment;
    }

    //removes a specific status effect; returns true if any statuses were removed.
    //removes the string from all trees because some status effects fit multiple
    //status parameters and thus exist as multiple data pieces in separate trees.
    public boolean statusRemove(String toRemove){
        boolean didRemove = false; //was at least one status removed?
        if(damageEffectList.Remove(toRemove, 10000) == 1)
            didRemove = true;
        if(endOfTurnList.Remove(toRemove, 10000) == 1)
            didRemove = true;
        if(statChangeList.Remove(toRemove, 10000) == 1) {
            didRemove = true;
            updateStatTemps(); //update the character's stats if we removed any stat changes
        }
        return didRemove; //return a boolean showing whether or not anything was removed.
    }

    //inserts a status effect; returns true if we need to readjust temp stats
    public boolean addStatus(statusEffectData toInsert){
        LLLnode nodeToInsert; //LLLnode reference
        if(toInsert instanceof damageEffect){ //insert to damage effects if this condition
            nodeToInsert = new LLLnode(toInsert); //has damage effect properties
            damageEffectList.Insert(nodeToInsert);
        }

        if(toInsert instanceof endOfTurn){
            nodeToInsert = new LLLnode(toInsert); //insert to end of turn effects if this
            endOfTurnList.Insert(nodeToInsert);   //condition has end of turn properties
        }

        if(toInsert instanceof statChange){ //insert to statchange effects if this condition
            nodeToInsert = new LLLnode(toInsert); //has stat change properties
            statChangeList.Insert(nodeToInsert);
            return true; //return true to readjust temp stats
        }
        return false;
    }

    //wrapper for damage calculation via the damage effect list.
    public int calculateDamage(int toCalculate, String Property){
        toCalculate = damageEffectList.calculateDamage(toCalculate, Property);
        return toCalculate;
    }

    //called at the end of each turn to decrement durations and handle end of turn effects.
    public void endTurn(){
        if(isAlive()) { //if the character is still alive...
            endOfTurnList.endTurn(this); //apply end of turn effects
            statChangeList.decrementAll(); //decrement duration of
            endOfTurnList.decrementAll();  //all effects by one
            damageEffectList.decrementAll();
            updateStatTemps(); //updata stats in case any were removed.
        }
        if(!isAlive()){ //if the character was dead, or died after the end of turn effects...
            if(clearStatus()) //clear status if the character is dead.
                updateStatTemps(); //if we removed any stat changes, update temps.
        }
    }

    //update the character's temporary stat values
    public void updateStatTemps(){
        statChangeList.changeStats(this);
    }

    //does the character have the passed weapon type?
    abstract public boolean hasWeaponType(String toCompare, boolean isRight);

    //checks to see if the character's weapon's property matches the passed string
    abstract public boolean hasWeaponProperty(String toCompare, boolean isRight);

    //gets the character's attack property.
    abstract public String getWeaponProperty(boolean isRight);

    //gets attack damage
    abstract public int getWeaponDamage(boolean isRight);
}
