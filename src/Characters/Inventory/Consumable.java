package Characters.Inventory;

import Characters.combatEffect;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class Consumable extends Item implements combatEffect {
    @Override
    public void spLoss(gameCharacter Caster) {
        return; //items unilaterally have no SP loss.
    }
}
