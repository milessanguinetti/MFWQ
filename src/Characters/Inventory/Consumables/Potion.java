package Characters.Inventory.Consumables;

import Characters.Inventory.Consumable;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class Potion extends Consumable{
    public Potion(){
        super("Potion", "A red draught that heals 50Hp. Consumed after use.");
    }

    @Override
    public boolean Use(gameCharacter toUseOn) {
        toUseOn.takeAbsoluteDamage(-50); //heal 50 damage
        //NOT FULLY IMPLEMENTED.
        return true;
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage(-50);
    }
}
