package Characters.Inventory;
;
import Characters.playerCharacter;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public interface equipableItem{
    //the "equip" method is relegated to the item class's 'use' method
    void Unequip(playerCharacter toEquipTo);
    void applyBuffs(playerCharacter toBuff);
    //apply buffs that don't fit into persistent stat bonuses, like
    //a trinket that buffs speed by 100% for just one turn.
    void addStats(playerCharacter toAdd);
    void subtractStats(playerCharacter toSubtract);
    //these methods are item-specific, but help to compartmentalize
    //the use and unequip methods to more general tasks while
    //still being able to call these to handle specific tasks
    String getStatText(); //returns a string describing any bonuses to stats granted by the item
}
