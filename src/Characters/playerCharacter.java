package Characters;

import Characters.Classes.characterClass;
import Characters.Inventory.Accessory;
import Characters.Inventory.Armor;
import Characters.Inventory.Weapon;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public class playerCharacter extends gameCharacter{
    private Weapon Left; //weapons for each hand
    private Weapon Right;
    private Armor Armor1; //the character's armor
    private Accessory Accessory1; //two accessories
    private Accessory Accessory2;
    characterClass primaryClass;
    characterClass secondaryClass;

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

    public void setPrimaryClass(characterClass primaryClass) {
        this.primaryClass = primaryClass;
    }

    public void setSecondaryClass(characterClass secondaryClass) {
        this.secondaryClass = secondaryClass;
    }
}
