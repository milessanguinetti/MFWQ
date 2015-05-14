package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class occultistSpiritBurn extends Skill{
    public occultistSpiritBurn(){
        super("Spirit Burn",
                "Ignites the target's soul with occult magic, dealing SP damage in addition to regular damage.", 30);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
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
        return toCheck.getSP() >= 30;
    }

    @Override //deals (str + fth + weapon damage) of unholy property, heals for same amount.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float staffMod = 1;
        if(Caster.hasWeaponType("1h Staff", false) || Caster.hasWeaponType("2h Staff", false))
            staffMod = 1.5f;
        Defender.takeDamage(Math.round(staffMod * (Caster.getTempInt() + Defender.getTempInt())), "Neutral");
        Defender.subtractSP(Math.round(staffMod * (Caster.getTempInt() + Defender.getTempInt())));
        Defender.printName();
        System.out.println("'s SP was burned!");
    }
}
