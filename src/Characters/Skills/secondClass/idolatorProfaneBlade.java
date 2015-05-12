package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class idolatorProfaneBlade extends Skill{
    public idolatorProfaneBlade(){
        super("Profane Blade", "Strikes the target ghastly weapon, damaging them and healing the user.", 40);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(40);
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
        if(toCheck.hasWeaponType("Bow", true)) //can only be used with right handed melee weapons
            return false;
        if(toCheck.hasWeaponType("2h Staff", true))
            return false;
        return toCheck.getSP() >= 40;
    }

    @Override //deals (str + fth + weapon damage) of unholy property, heals for same amount.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Dealt = Defender.getHP();
        Dealt -= Defender.takeDamage(Caster.getTempStr()
                + Caster.getTempFth() + Caster.getWeaponDamage(true), "Unholy");
        Caster.takeAbsoluteDamage(-Dealt); //heal for however much damage was dealt
    }
}
