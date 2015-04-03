package Characters;

import Characters.Classes.characterClass;
import Characters.Inventory.Accessory;
import Characters.Inventory.Armor;
import Characters.Inventory.Weapon;
import Characters.Skills.passiveSkill;
import Structures.LLLnode;
import Structures.orderedLLL;

import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public class playerCharacter extends gameCharacter{
    private String Race;
    private int Level;
    private int Exp;
    private int expCap;
    private Weapon Left; //weapons for each hand
    private Weapon Right;
    private Armor Armor1; //the character's armor
    private Accessory Accessory1; //two accessories
    private Accessory Accessory2;
    private orderedLLL Classes;
    private characterClass primaryClass;
    private characterClass secondaryClass;
    private orderedLLL Passives;

    //default constructor
    public playerCharacter(){
        expCap = 1000;
    }

    //special constructor
    public playerCharacter(String name, String race, Stats toAdd){
        super(name, toAdd);
        Race = race;
        expCap = 1000;
    }

    public boolean isOfRace(String toCompare){
        if(Race.compareTo(toCompare) == 0)
            return true;
        return false;
    }

    //add a class to the ordered LLL
    public void addClass(characterClass toAdd){
        LLLnode temp = new LLLnode(toAdd);
        Classes.Insert(temp);
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

    void Ding(int exp, int jexp){
        if((Exp + exp) >= expCap){ //if we've received enough exp to level...
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
    void baseStatGains(){
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
}
