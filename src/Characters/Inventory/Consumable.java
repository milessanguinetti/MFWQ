package Characters.Inventory;

import Characters.combatEffect;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class Consumable extends Item implements combatEffect {
    public Consumable(){}

    public Consumable(String name, String description){
        super(name, description);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        return; //items unilaterally have no SP loss.
    }

    @Override
    public void printName() {
        System.out.print(itemName);
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.isAlive();
    }
}
