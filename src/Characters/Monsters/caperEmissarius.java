package Characters.Monsters;

import Characters.Inventory.Item;
import Characters.Monster;
import Characters.Properties.Neutral;
import Characters.Skills.Monster.fullGuard;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class caperEmissarius extends Monster{

    public caperEmissarius(){
        super("Caper Emissarius", 50, 20, 1, 1, 500, 1, 1, 1, 0);
        charProperty = new Neutral();
        monsterSkills[0] = new fullGuard();
        buildSkills(); //build the monster's skills.
    }

    public caperEmissarius(int hp, gameCharacter todefend){
        super("Caper Emissarius", hp, 20, 1, 1, 500, 1, 1, 1, 0);
        charProperty = new Neutral();
        monsterSkills[0] = new fullGuard(todefend);
        buildSkills(); //build the monster's skills.
    }

    @Override
    public Item Loot() {
        return null;
        //this is a homunculus minion and does not drop loot
    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public int getJexp() {
        return 0;
    }

    @Override
    public void buildSkills() {
        skillProbabilities[0] = 100;
    }
}
