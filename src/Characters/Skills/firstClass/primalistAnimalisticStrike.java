package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.damageAdditive;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class primalistAnimalisticStrike extends Skill{
    public primalistAnimalisticStrike(){
        super("Animalistic Strike",
                "Ferociously attacks the target for heavy damage, but leaves the user vulnerable", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
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
        return toCheck.getSP() >= 15;
    }

    @Override //Deals double speed + right weapon damage, but adds the caster's speed
    //to any hits that they take over the next 3 turns (following the one this is cast on).
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(2*(Caster.getTempSpd() +
                Caster.getWeaponDamage(true)), "Organic");
        Caster.addStatus(new damageAdditive(Caster.getTempSpd(), 4));
    }
}
