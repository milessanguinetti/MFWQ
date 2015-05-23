package Characters;

import Characters.Properties.Property;
import Characters.Skills.Passive.passiveSkill;
import Characters.Status.Counter;
import Characters.Status.damageEffect;
import Characters.Status.endOfTurn;
import Characters.Status.statChange;
import Profile.Game;
import Structures.LLLnode;
import Structures.statusEffectData;
import Structures.statusStructure;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 3/17/15.
 */

//abstract character base class--derived from stats
public abstract class gameCharacter extends Stats {
    protected String Name; //character name
    protected passiveSkill currentPassive; //the character's passive skill.
    protected Property charProperty; //the character's property
    protected Property tempProperty; //temporary battle property based on buffs
    private Counter charCounter; //a character's potential ability to counter.
    private statusStructure statChangeList = new statusStructure(); //LLL structure for effects that change stats
    private statusStructure damageEffectList = new statusStructure(); //effects that impact damage taken
    private statusStructure endOfTurnList = new statusStructure(); //effects that take place at the end of a turn


    //default constructor
    public gameCharacter(){}

    //constructor with name
    public gameCharacter(String toName){
        Name = toName;
    }

    //constructor with name and stats object.
    public gameCharacter(String toName, Stats toCopy){
        super(toCopy); //copy stats
        Name = toName;
    }

    //constructor with name and stats
    public gameCharacter(String toName, int hp, int sp, int str, int dex, int spd, int vit, int inte, int fth, int arm){
        super(hp, sp, str, dex, spd, vit, inte, fth, arm);
        Name = toName;
    }

    public String getName(){
        return Name;
    }

    public void setCounter(Counter counter){
        charCounter = counter;
    }

    public void executeCounter(gameCharacter Attacker){
        if(charCounter != null)
            charCounter.executeCounter(Attacker, this);
    }

    //sets the character's temp property to a passed property.
    public void setTempProperty(Property tempProperty) {
        this.tempProperty = tempProperty;
    }

    //retrieves a combat effect to use during the character's turn.
    public abstract combatEffect chooseSkill();

    //picks a target (via AI or input, depending on whether this is a monster, player or minion)
    //from two passed arrays of game characters representing appropriate targets--minions or game chars
    public abstract int chooseTarget(gameCharacter[] CTargets, gameCharacter[] MTargets,
                                     boolean notUsableOnDead);

    //takes damage; returns remaining health
    public int takeDamage(int toTake, String property){
        Random Rand = new Random(); //initiate RNG

        float Roll = (Rand.nextInt(31) - 15)*.01f; //get a value between .15 and -.15
        toTake = Math.round(toTake * (1 + Roll)); //we deal somewhere between 85% and 115% damage.
        //this gives the game some variation in damage numbers.
        toTake = calculateDamage(toTake, property); //take status into account
        toTake = tempProperty.calculateDamage(toTake, property); //take property into account
        if(toTake <= 0){
            Game.Player.getCurrentBattle().getInterface().printLeft("The attack had no effect.", 1);
            return HP;
        }
        toTake -= tempArmor; //subtract armor from damage value
        if(toTake <= 0)
            toTake = 1; //damage is 1 minimum
        subtractHP(toTake); //take the damage
        if(!isAlive())
            Game.Player.getCurrentBattle().getInterface().printLeft(Name + " took "
                    + toTake + " damage and was downed!", 1);
        else
            Game.Player.getCurrentBattle().getInterface().printLeft(Name + " took "
                    +toTake + " damage.", 1);
        return HP; //some skills' effects hinge on whether or not the target died.
    }

    //takes absolute damage, ignoring defenses; returns remaining health
    public int takeAbsoluteDamage(int toTake){
        if(!isAlive())
            return 0; //dead characters cannot be healed and cannot take damage.
        takeAbsoluteDamage(toTake);
        if(toTake > 0){
            if(!isAlive())
                Game.Player.getCurrentBattle().getInterface().printLeft(Name + " took "
                        + toTake + " damage and was downed!", 1);
            else
                Game.Player.getCurrentBattle().getInterface().printLeft(Name + " took "
                        +toTake + " damage.", 1);
        }
        else{
            Game.Player.getCurrentBattle().getInterface().printLeft(Name + " was healed for "
                    + toTake + " health.", 1);
        }
        return HP;
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
        charCounter = null;
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
            subtractSP(Math.round((SP / 20) * -19)); //every turn, regenerate 5% SP.
            endOfTurnList.endTurn(this); //apply end of turn effects
            statChangeList.decrementAll(); //decrement duration of
            endOfTurnList.decrementAll();  //all effects by one
            damageEffectList.decrementAll();
            updateStatTemps(); //update stats in case any were removed.
            if(charCounter != null){
                if(charCounter.Decrement() == 0)
                    charCounter = null;
            }
        }
        if(!isAlive()){ //if the character was dead, or died after the end of turn effects...
            if(clearStatus()) //clear status if the character is dead.
                updateStatTemps(); //if we removed any stat changes, update temps.
        }
    }

    //update the character's temporary stat values
    public void updateStatTemps(){
        setTemps();
        statChangeList.changeStats(this);
    }

    //prints the character's name and stats.
    public void printStats(){
        System.out.print("Name: ");
        System.out.println(Name);
        System.out.print("Property");
        System.out.println(charProperty.getName());
        System.out.print("HP: " + HP + "/");
        System.out.println(MHP);
        System.out.print("SP: " + SP + "/");
        System.out.println(MSP);
        System.out.print("Vitality: ");
        System.out.println(Vit);
        System.out.print("Armor: ");
        System.out.println(Armor);
        System.out.print("Strength: ");
        System.out.println(Str);
        System.out.print("Dexterity: ");
        System.out.println(Dex);
        System.out.print("Intelligence: ");
        System.out.println(Int);
        System.out.print("Faith: ");
        System.out.println(Fth);
    }

    //prints the character's name and TEMPORARY stats
    public void printTempStats(){
        System.out.print("Name: ");
        System.out.println(Name);
        System.out.print("Property");
        System.out.println(tempProperty.getName());
        System.out.print("HP: " + HP + "/");
        System.out.println(MHP);
        System.out.print("SP: " + SP + "/");
        System.out.println(MSP);
        System.out.print("Vitality: ");
        System.out.println(tempVit);
        System.out.print("Armor: ");
        System.out.println(tempArmor);
        System.out.print("Strength: ");
        System.out.println(tempStr);
        System.out.print("Dexterity: ");
        System.out.println(tempDex);
        System.out.print("Intelligence: ");
        System.out.println(tempInt);
        System.out.print("Faith: ");
        System.out.println(tempFth);
    }

    abstract public boolean hasTwoHandedWeapon();

    //does the character have the passed weapon type?
    abstract public boolean hasWeaponType(String toCompare, boolean isRight);

    //checks to see if the character's weapon's property matches the passed string
    abstract public boolean hasWeaponProperty(String toCompare, boolean isRight);

    //gets the character's attack property.
    abstract public String getWeaponProperty(boolean isRight);

    public boolean hasProperty(String toCompare){
        return tempProperty.isProperty(toCompare);
    }

    //gets attack damage
    abstract public int getWeaponDamage(boolean isRight);


}
