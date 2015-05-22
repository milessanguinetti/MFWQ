package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.Status.dexBuff;
import Characters.Status.strBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class defenderShieldSmash extends Skill{
    public defenderShieldSmash(){
        super("Shield Smash", "Smashes the target with a shield, dealing damage and debuffing physical stats.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(!toCheck.hasWeaponType("Shield", false)) //can only be used with a shield
            return false;
        return toCheck.getSP() >= 30;
    }

    @Override //deal str + shield "damage" damage, debuff str and dex by 25% each
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Defender.getTempStr() + Defender.getWeaponDamage(false),
                Defender.getWeaponProperty(false));
        Defender.addStatus(new strBuff(6, .75f));
        Defender.addStatus(new dexBuff(6, .75f));
    }
}
