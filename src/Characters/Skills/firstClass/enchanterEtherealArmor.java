package Characters.Skills.firstClass;

import Characters.Properties.Ghost;
import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterEtherealArmor extends Skill{
    public enchanterEtherealArmor(){
        super("Ethereal Armor", "Imbues a piece of armor with ghostly power.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(!(toCheck.getSP() >= 50))
            return false; //doesn't have SP for this
        return false;
    }

    @Override //20% strength buff for 10 turns
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.setTempProperty(new Ghost());
        Defender.printName();
        System.out.println("'s armor shines with a ghostly aura!");
    }
}
