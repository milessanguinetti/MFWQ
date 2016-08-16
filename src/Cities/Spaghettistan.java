package Cities;

import Characters.Inventory.Consumables.Potion;
import Characters.Inventory.Weapons.Nodachi;
import Characters.Inventory.Weapons.generic2hBlunt;
import Characters.Inventory.Weapons.generic2hEdged;
import Characters.Inventory.Weapons.genericGun;

/**
 * Created by Spaghetti on 8/8/2016.
 */
public class Spaghettistan extends City{
    private static Shop spaghettishop;
    private static Inn spaghettiinn;

    public Spaghettistan(){
        super(
                getInn(), //no inn
                getShop());
    }

    private static Shop getShop(){
        if(spaghettishop == null)
            spaghettishop = new Shop("Spaghetti Shop", //initialize new shop with the following items:
                    new generic2hBlunt(100),
                    new Nodachi(100),
                    new Potion(),
                    new generic2hEdged(100),
                    new genericGun(50));
        return spaghettishop;
    }

    private static Inn getInn(){
        if(spaghettiinn == null){
            spaghettiinn = new Inn("Spaghetti Inn", 100);
        }
        return spaghettiinn;
    }


}
