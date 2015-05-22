package Characters.Inventory;

import Characters.combatEffect;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class Weapon extends Item implements equipableItem, combatEffect {
    protected int Damage; //the weapon's damage value (a shield's equals its armor bonus)
    private String weaponType; //the class of the weapon in question
    private boolean isRightHand; //boolean value denotes which hand the weapon goes in
    private boolean isTwoHand; //denotes whether or not the weapon requires two hands
    protected String Property; //the weapon's property

    //default constructor
    public Weapon(){}

    //constructor that effectively handles all data members
    public Weapon(String Name, String Description, int damage, String weapontype, String property){
        super(Name, Description);
        Damage = damage;
        weaponType = weapontype;
        Property = property;
        //beyond setting those, we set the righthand/twohand variables based on
        //the type that we are looking at.
        if(weapontype.equals("1h Edged")){
            isRightHand = true;
            isTwoHand = false;
        }
        else if(weapontype.equals("2h Edged")){
            isRightHand = true;
            isTwoHand = true;
        }
        else if(weapontype.equals("1h Blunt")){
            isRightHand = true;
            isTwoHand = false;
        }
        else if(weapontype.equals("2h Blunt")){
            isRightHand = true;
            isTwoHand = true;
        }
        else if(weapontype.equals("1h Staff")){
            isRightHand = false;
            isTwoHand = false;
        }
        else if(weapontype.equals("2h Staff")){
            isRightHand = false;
            isTwoHand = true;
        }
        else if(weapontype.equals("Shield")){
            isRightHand = false;
            isTwoHand = false;
        }
        else if(weapontype.equals("Gun")){
            isRightHand = false;
            isTwoHand = false;
        }
        else if(weapontype.equals("Bow")){
            isRightHand = true;
            isTwoHand = true;
        }
    }

    //constructor that handles all data except name and property. Principally used to
    //provide an efficient way to construct randomly generated weapons with names and
    //properties that are not known when I make a "super" call in the constructor
    public Weapon(String Description, int damage, String weapontype){
        super(Description);
        Damage = damage;
        weaponType = weapontype;
        //beyond setting those, we set the righthand/twohand variables based on
        //the type that we are looking at.
        if(weapontype.equals("Knife")){
            isRightHand = true;
            isTwoHand = false;
        }
        else if(weapontype.equals("1h Melee")){
            isRightHand = true;
            isTwoHand = false;
        }
        else if(weapontype.equals("2h Melee")){
            isRightHand = true;
            isTwoHand = true;
        }
        else if(weapontype.equals("1h Staff")){
            isRightHand = false;
            isTwoHand = false;
        }
        else if(weapontype.equals("2h Staff")){
            isRightHand = false;
            isTwoHand = true;
        }
        else if(weapontype.equals("Shield")){
            isRightHand = false;
            isTwoHand = false;
        }
        else if(weapontype.equals("Gun")){
            isRightHand = false;
            isTwoHand = false;
        }
        else if(weapontype.equals("Bow")){
            isRightHand = true;
            isTwoHand = true;
        }
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override //weapons are literally always offensive
    public boolean isOffensive() {
        return true;
    }

    public boolean isOfProperty(String toCheck){
        if(Property.compareTo(toCheck) == 0)
            return true;
        return false;
    }

    public boolean isOfType(String toCheck){
        if(weaponType.compareTo(toCheck) == 0)
            return true;
        return false;
    }

    public int getDamage(){
        return Damage;
    }

    public String getProperty(){
        return Property;
    }

    @Override
    public void printName() {
        System.out.print("their " + itemName);
    }

    @Override //equip the item to the passed character
    public boolean Use(gameCharacter toUseOn){
        if(!canUse(toUseOn))
            return false; //item was not used
        playerCharacter useOn = ((playerCharacter) toUseOn);
        Weapon rightTemp = useOn.getRight();
        Weapon leftTemp = useOn.getLeft();
        if(isTwoHand){ //unequip both weapons
            if(rightTemp != null)
                rightTemp.Unequip(useOn);
            else if(leftTemp != null)
                leftTemp.Unequip(useOn);
            useOn.setRight(this);
            useOn.setLeft(this);
        }
        else if(isRightHand){ //unequip just the right hand weapon
            if(rightTemp != null)
                leftTemp.Unequip(useOn);
            useOn.setRight(this);
        }
        else{ //unequip just the left hand weapon
            if(leftTemp != null)
                leftTemp.Unequip(useOn);
            useOn.setLeft(this);
        }
        addStats(useOn); //finally, we add this weapon's stats to the character
        return true;
    }

    @Override //unequip the item from the passed character
    public void Unequip(playerCharacter toEquipTo) {
        if(isTwoHand){
            toEquipTo.setRight(null);
            toEquipTo.setLeft(null);
        }
        else if(isRightHand){
            toEquipTo.setRight(null);
        }
        else{
            toEquipTo.setLeft(null);
        }
        subtractStats(toEquipTo); //subtract stats
        Game.Player.Insert(this); //insert the item into global inventory
    }
}
