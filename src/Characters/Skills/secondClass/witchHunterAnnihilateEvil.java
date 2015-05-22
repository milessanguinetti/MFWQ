package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class witchHunterAnnihilateEvil extends Skill{
    public witchHunterAnnihilateEvil(){
        super("Annihilate Evil",
                "Repeatedly assaults the target with blade and righteous power alike.", 60);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(60);
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
        return toCheck.getSP() >= 60;
    }

    @Override //deal 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Strikes = (Caster.getTempSpd() + 10) / 10; //strike once per ten speed
        Caster.printName();
        System.out.println(" attacked " + Strikes + " times!");
        for (int i = Strikes; i > 0; --i) {
            Defender.takeDamage(Math.round(.4f * (Caster.getTempStr() +
                    Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
            Defender.takeDamage(Math.round((.8f) * Caster.getTempFth()), "Holy");
        }
    }
}
