package Characters.Inventory;
;
import Characters.playerCharacter;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public interface equipableItem{
    //the "equip" method is relegated to the item class's 'use' method
    public void Unequip(playerCharacter toEquipTo);
    public void applyBuffs(playerCharacter toBuff);
    //apply buffs that don't fit into persistent stat bonuses, like
    //a trinket that buffs speed by 100% for just one turn.
    public void addStats(playerCharacter toAdd);
    public void subtractStats(playerCharacter toSubtract);
    //these methods are item-specific, but help to compartmentalize
    //the use and unequip methods to more general tasks while
    //still being able to call these to handle specific tasks
}
