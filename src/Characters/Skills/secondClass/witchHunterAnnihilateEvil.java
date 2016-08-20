package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class witchHunterAnnihilateEvil extends Skill{
    public witchHunterAnnihilateEvil(){
        super("Annihilate Evil",
                "Rapidly assaults the target with blade and righteous power alike.", 60);
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

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Strikes = 1;
        if(Defender.hasProperty("Unholy") || Defender.hasProperty("Undead")) {
            Strikes += (1 + Math.round((Caster.getTempSpd()/1f)/Defender.getTempSpd()));
            Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " struck with fury at the evil within "
            + Defender.getName() + "!");
        }
        Defender.takeDamage(Math.round(1.4f * (Caster.getTempStr() +
                Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
        for(int i = 0; i < Strikes; ++i)
            Defender.takeDamage(Math.round((1.8f) * Caster.getTempFth()), "Holy");
    }
}
