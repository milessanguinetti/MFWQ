package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.Poisoned;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/1/15.
 */
public class rogueKnifeBarrage extends Skill {
    public rogueKnifeBarrage(){
        super("Knife Barrage",
                "Hurls hand-fulls of poisoned knives at the enemy. Liable to miss.", 25);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(25);
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
        return 4;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 25;
    }

    @Override //deal 20% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        if (Rand.nextInt(2) == 0) { //half of the time, the skill misses.
            Defender.takeDamage(Math.round((.2f) * (Caster.getTempStr() +
                    (Caster).getWeaponDamage(true))), "Organic");
            //deal 20% weapon/str damage of organic type
            int Roll = Rand.nextInt(3);
            if (Roll == 1 || Roll == 0) { //1/3 of the time, inflict standard poison
                Defender.addStatus(new Poisoned(Caster.getWeaponDamage(true), 5));
                Defender.printName();
                System.out.println(" was afflicted with poison!");
            }
            //otherwise the skill fails to poison.
        }
    }
}
