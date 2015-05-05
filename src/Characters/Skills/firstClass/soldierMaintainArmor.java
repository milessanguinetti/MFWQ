package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.armorBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class soldierMaintainArmor extends Skill {
    public soldierMaintainArmor(){
        super("Maintain Armor", "Repairs and polishes the caster's armor, increasing armor value.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(!(toCheck.getSP() >= 10))
            return false; //doesn't have SP for this
        return false;
    }

    @Override //20% armor buff for 10 turns
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new armorBuff(10, 1.2f));
        Defender.printName();
        System.out.println(" repaired their armor!");
    }
}
