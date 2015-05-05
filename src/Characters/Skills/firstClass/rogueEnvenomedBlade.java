package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.Poisoned;
import Characters.Status.deliriantPoison;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles on 4/29/2015.
 */
public class rogueEnvenomedBlade extends Skill {
    public rogueEnvenomedBlade(){
        super("Envenomed Blade",
                "Strikes the target with a poisoned weapon. Can cause a variety of negative effects.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
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
        return toCheck.getSP() >= 10;
    }

    @Override //deal 80% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round((.8f)*(Caster.getTempStr() +
                (Caster).getWeaponDamage(true))), "Organic");
        //deal 80% weapon/str damage of organic type
        Random Rand = new Random();
        int Roll = Rand.nextInt(3);
        if(Roll == 1){ //1/3 of the time, inflict standard poison
            Defender.addStatus(new Poisoned(Caster.getWeaponDamage(true), 5));
            Defender.printName();
            System.out.println(" was afflicted with poison!");
        }
        if(Roll == 2){ //1/3 of the time, inflict deliriant poison
            Defender.addStatus(new deliriantPoison(5));
            Defender.printName();
            System.out.println(" was afflicted with deliriant poison!");
        }
        //otherwise the skill fails to poison.
    }
}
