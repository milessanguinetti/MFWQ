package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.playerCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//physical attack based on speed and strength. Hits speed/10 + 1 times for 40% of (weapon damage + str)
public class rogueRapidStriking extends Skill {
    public rogueRapidStriking(){
        super("Rapid Strike",
                "Attacks the target repeatedly with an equipped weapon, striking times if the user is faster.", 40);
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
        return toCheck.getSP() >= 40;
    }

    @Override //deal 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Strikes = (Caster.getTempSpd() + 10) / 10; //strike once per ten speed
        Caster.printName();
        System.out.println(" struck " + Strikes + " times!");
        for (int i = Strikes; i > 0; --i) {
            Defender.takeDamage(Math.round(.4f * (Caster.getTempStr() +
                    ((playerCharacter) Caster).getWeaponDamage(true))), Caster.getWeaponProperty(true));
        }
    }
}
