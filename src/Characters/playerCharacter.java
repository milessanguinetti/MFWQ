package Characters;

import Characters.Classes.characterClass;
import Characters.Inventory.Accessory;
import Characters.Inventory.Armor;
import Characters.Inventory.Weapon;
import Characters.Properties.Neutral;
import Characters.Skills.Flee;
import Characters.Skills.Skill;
import Characters.Skills.Wait;
import Characters.Skills.Passive.passiveSkill;
import Profile.Game;
import Structures.LLLnode;
import Structures.orderedLLL;

import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public class playerCharacter extends gameCharacter {
    private String Race;
    private int Level;
    private int Exp;
    private int expCap;
    private Weapon Left; //weapons for each hand
    private Weapon Right;
    private Armor Armor1; //the character's armor
    private Accessory Accessory1; //two accessories
    private Accessory Accessory2;
    private orderedLLL Classes = new orderedLLL();
    private characterClass primaryClass;
    private characterClass secondaryClass;
    private orderedLLL Passives = new orderedLLL();

    //default constructor
    public playerCharacter() {
        expCap = 1000;
        charProperty = new Neutral();
    }

    //special constructor
    public playerCharacter(String name, String race, int hp, int sp, int str,
                           int dex, int spd, int vit, int inte, int fth, int arm) {
        super(name, hp, sp, str, dex, spd, vit, inte, fth, arm);
        Race = race;
        expCap = 1000;
        charProperty = new Neutral();
    }

    //chooses a target from two passed arrays of targets
    public int chooseTarget(gameCharacter [] chars, gameCharacter [] mins){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a target from the following or enter 0 to cancel:");
        int k = 1; //counter for the number of targets that have been displayed
        for(int i = 0; i < 4; ++i){
            if(chars[i] != null) {
                if(chars[i].isAlive()) { //if the character exists and is still alive
                    System.out.print(k + ": ");
                    chars[i].printName();
                    System.out.println();
                    ++k; //increment k
                }
            }
        }
        for(int j = 0; j < 4; ++j) {
            if (mins[j] != null) {
                if (mins[j].isAlive()) { //if the character exists and is still alive
                    System.out.print(k + ": ");
                    mins[j].printName();
                    System.out.println();
                    ++k; //increment k
                }
            }
        }
        int Input = scanner.nextInt(); //user input variable
        scanner.nextLine(); //remove anything typed after the first int
        if(Input == 0)
            return 0; //return 0 if the user cancels
        for(int l = 0; l < 4; ++l) {
            if (chars[l] != null) {
                if (chars[l].isAlive()) { //if the character exists and is still alive
                    if (Input == 1)
                        return l + 1; //return l+1 to essentially have a meaningful
                    else              //reference to where the target is located.
                        --Input; //otherwise decrement input by 1 so that we know that
                }                //we have passed a character and are closer to the input value
            }
        }
        for(int m = 0; m < 4; ++m) {
            if (chars[m] != null) {
                if (chars[m].isAlive()) { //if the character exists and is still alive
                    if (Input == 1)
                        return m + 5; //return l+5 to essentially have a meaningful
                    else              //reference to where the target is located; +5 denotes a minion.
                        --Input; //otherwise decrement input by m so that we know that
                }                //we have passed a character and are closer to the input value
            }
        }   //if we get here, the user has not input a number denoting a valid target.
            System.out.println("Input invalid. Please choose a valid target."); //error message.
            return chooseTarget(chars, mins); //recursive call
    }

    //selects a skill to use/attacks with weapons/runs
    public combatEffect chooseSkill() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to attack with a weapon or use a skill?");
        System.out.println("Enter '1' for a weapon attack or '2' for a skill. '0' will make the character wait.");
        System.out.println("'3' will use an item and '4' will attempt to flee the battle.");
        int Input = scanner.nextInt();
        scanner.nextLine();
        if (Input == 0)
            return new Wait(); //return a new "wait" skill object
        if (Input == 2) {
            System.out.println("Would you like to use a skill from the character's primary or secondary class?");
            System.out.println("Enter '1' for primary or '2' for secondary class. '0' will cancel skill use.");
            Input = scanner.nextInt();
            scanner.nextLine();
            if (Input == 1) {
                if (primaryClass == null) {
                    System.out.println("This character has no primary class.");
                    return chooseSkill();
                }
                System.out.println("SP remaining: " + SP + "/" + MSP);
                primaryClass.displaySkills();
                System.out.println("Enter the number of the corresponding skill or '0' to cancel.");
                Input = scanner.nextInt();
                scanner.nextLine();
                if (Input != 0) {
                    Skill toReturn = primaryClass.getSkill(Input);
                    if (toReturn == null) {
                        System.out.println("Input invalid. Please enter a valid skill.");
                    }
                    else
                        return toReturn;
                }
            } else if (Input == 2) {
                System.out.println("SP remaining: " + SP + "/" + MSP);
                if (secondaryClass == null) {
                    System.out.println("This character has no secondary class.");
                    return chooseSkill();
                }
                secondaryClass.displaySkills();
                System.out.println("Enter the number of the corresponding skill or '0' to cancel.");
                Input = scanner.nextInt();
                scanner.nextLine();
                if (Input != 0) {
                    Skill toReturn = secondaryClass.getSkill(Input);
                    if (toReturn == null) {
                        System.out.println("Input invalid. Please enter a valid skill.");
                    }
                    else
                        return toReturn;
                }
            }
            return chooseSkill(); //return recursive call's return value if the user cancelled.
        } else if (Input == 1) {
            if (Right != null)
                return Right;
            if (Left != null)
                return Left;
            return null; //wait if the character cannot use a weapon.
            //implement a fist damage case for this in the future
        } else if (Input == 3) {
            //CASE FOR INVENTORY USE
            combatEffect itemReturn = Game.Player.combatInterface();
            if (itemReturn == null)
                return chooseSkill(); //return recursive call's value if the user cancelled.
            return itemReturn; //otherwise return the selected item.
        } else if (Input == 4) {
            return new Flee();
        }
        else{
            System.out.println("Input invalid.");
            return chooseSkill();
        }
    }

    //is the character of the passed race?
    public boolean isOfRace(String toCompare){
        if(Race.compareTo(toCompare) == 0)
            return true;
        return false;
    }

    //does the character have the passed weapon type?
    public boolean hasWeaponType(String toCompare, boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return false; //false case
            return Right.isOfType(toCompare); //otherwise compare & return
        }
        else { //left weapon case
            if (Left == null) //if there is no left weapon
                return false; //false case
            return Left.isOfType(toCompare); //otherwise compare & return
        }
    }

    //checks to see if the character's weapon's property matches the passed string
    public boolean hasWeaponProperty(String toCompare, boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return toCompare.equals("Neutral"); //check neutral
            return Right.isOfProperty(toCompare); //otherwise compare & return
        }
        else{ //left weapon case
            if(Left == null) //if there is no left weapon
                return toCompare.equals("Neutral"); //check neutral
            return Left.isOfProperty(toCompare); //otherwise compare & return
        }
    }

    //gets the character's weapon's property.
    public String getWeaponProperty(boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return "Neutral"; //return neutral
            return Right.getProperty();
        }
        else{ //left weapon case
            if(Left == null) //if there is no left weapon
                return "Neutral"; //return neutral
            return Left.getProperty();
        }
    }

    public int getWeaponDamage(boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return Str; //return strength for a fist attack
            return Right.getDamage();
        }
        else{ //left weapon case
            if(Left == null) //if there is no left weapon
                return Str; //return strength for a fist attack
            return Left.getDamage();
        }
    }

    //add a class to the ordered LLL
    public void addClass(characterClass toAdd){
        Classes.Insert(new LLLnode(toAdd));
    }

    //add a passive skill to the ordered LLL
    public void addPassive(passiveSkill toAdd){
        LLLnode temp = new LLLnode(toAdd);
        Passives.Insert(temp);
    }

    //chooses a class, be it a primary or secondary class.
    public void chooseClass(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to adjust this character's primary or secondary class?");
        System.out.println("Enter '1' for primary, '2' for secondary or '0' to cancel.");
        int whichClass = scanner.nextInt();
        scanner.nextLine();
        if(whichClass == 0)
            return;
        System.out.println("Choose a class.");
        Classes.displayNumbered();
        int toGet = scanner.nextInt();
        scanner.nextLine();
        LLLnode Retrieved = Classes.retrieveInt(toGet);
        if(Retrieved == null){
            System.out.println("Invalid class.");
        }
        else {
            if(whichClass == 2) //set secondary class to retrieved.
                secondaryClass = ((characterClass)Retrieved.returnData());
            else //set primary class to retrieved.
                primaryClass = ((characterClass)Retrieved.returnData());
        }
    }

    //selects and 'equips' a passive skill that the character has learned
    public void choosePassive(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a passive skill.");
        Passives.displayNumbered();
        int toGet = scanner.nextInt();
        scanner.nextLine();
        LLLnode Retrieved = Passives.retrieveInt(toGet);
        if(Retrieved == null){
            System.out.println("Invalid passive skill.");
        }
        else
            currentPassive = ((passiveSkill)Retrieved.returnData());
    }

    public void Ding(int exp, int jexp){
        if((Exp + exp) >= expCap){ //if we've received enough exp to level...
            System.out.println(Name + " gained a level! " + Name + "'s stats increased!");
            exp -= (expCap - exp);
            ++Level;
            expCap += Math.round(expCap*1.2); //exp cap goes up by 20%
            Exp = 0; //reset Exp to 0 now that we've leveled.
            baseStatGains(); //increase base stats.
            if(primaryClass != null)
                primaryClass.baseDing(this, Level); //gain auxilary class stat boosts
            Ding(exp, 0); //recursive call; jexp == 0 so that we don't gain it twice.
        }
        else
            Exp += exp; //otherwise, we just add it to our exp.
        if(primaryClass != null) { //level the character's class with jexp
            primaryClass.gainJexp(jexp, this);
        }
    }

    //calculates base stat gains upon leveling
    public void baseStatGains(){
        MHP += 10; //MHP and SP necessarily increase
        MSP += 5;
        HP = MHP; //full heal
        SP = MSP;
        ++Vit;
        if(Level % 2 == 0) {
            ++Str;
            ++Int;
        }
        if((Level + 1) % 2 == 0) {
            ++Dex;
            ++Fth;
        }
        if(Level % 3 == 0)
            ++Spd;
    }

    public void setLeft(Weapon left) {
        Left = left;
    }

    public void setRight(Weapon right) {
        Right = right;
    }

    public Weapon getLeft(){
        return Left;
    }

    public Weapon getRight(){
        return Right;
    }

    public void setArmor(Armor armor1) {
        if(Armor1 != null)
            Armor1.Unequip(this);
        Armor1 = armor1;
    }

    public void setAccessory(Accessory toSet) {
        if(Accessory1 != null){
            Accessory1 = toSet;
        }
        else if(Accessory2 != null){
            Accessory2 =toSet;
        }
        else{
            Accessory1.Unequip(this);
            Accessory1 = toSet;
        }
    }

    //applies this character's automatic buffs for each equipped item
    public void applyAutoBuffs(){
        tempProperty = charProperty;
        setTemps();
        if(!(Right == Left)){ //if this isn't a two-handed weapon
            if(Right != null)
                Right.applyBuffs(this); //apply right's buffs
            if(Left != null)
                Left.applyBuffs(this); //and left's buffs
        }
        else{ //if this is a two-handed weapon
            if(Right != null)
                Right.applyBuffs(this);
        }
        if(Armor1 != null)
            Armor1.applyBuffs(this);
        if(Accessory1 != null)
            Accessory1.applyBuffs(this);
        if(Accessory2 != null)
            Accessory2.applyBuffs(this);
        if(currentPassive != null)
            currentPassive.passiveEffect(this);
    }

    public void setPrimaryClass(characterClass primaryClass) {
        primaryClass = primaryClass;
    }

    public void setSecondaryClass(characterClass secondaryClass) {
        secondaryClass = secondaryClass;
    }

    public boolean canUseHeavyArmor(){
        if(primaryClass == null) //no segfaults
            return false;
        if(primaryClass.canUseHeavyArmor())
            return true;
        return false;
    }

    //function for dropping loot at the end of a battle.
    //if a player character dies, they drop their equipment
    //so that it isn't lost.
    public void Loot(){
        System.out.println(Name + " dropped their equipment.");
        if(!(Right == Left)){ //if this isn't a two-handed weapon
            if(Right != null)
                Game.Player.Insert(Right); //add right to inventory
            if(Left != null)
                Game.Player.Insert(Left); //and left to inventory
        }
        else{ //if this is a two-handed weapon
            if(Right != null)
                Game.Player.Insert(Right); //add the weapon to inventory
        }
        if(Armor1 != null)
            Game.Player.Insert(Armor1);
        if(Accessory1 != null)
            Game.Player.Insert(Accessory1);
        if(Accessory2 != null)
            Game.Player.Insert(Accessory2);
    }
}
