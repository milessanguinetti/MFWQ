package Characters.Inventory;

import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class Armor extends Item implements equipableItem {
    private int Armor; //the weapon's damage value (a shield's equals its armor bonus)
    private boolean isHeavy; //denotes whether or not this is heavy armor.

    //default constructor
    public Armor(){}

    //constructor that effectively handles all data members
    public Armor(String Name, String Description, int armor, boolean isheavy){
        super(Name, Description);
        Armor = armor;
        isHeavy = isheavy;
    }

    @Override //equip the item to the passed character
    public boolean Use(gameCharacter toUseOn){
        if(!((playerCharacter)toUseOn).canUseHeavyArmor())
            return false; //item was not used
        playerCharacter useOn = ((playerCharacter) toUseOn);
        useOn.setArmor(this);
        addStats(useOn); //finally, we add this weapon's stats to the character
        return true;
    }

    @Override //unequip the item from the passed character
    public void Unequip(playerCharacter toEquipTo) {
        toEquipTo.setArmor(null); //set armor to null
        subtractStats(toEquipTo); //subtract stats
        Game.Player.Insert(this); //insert the item into global inventory
    }
}
