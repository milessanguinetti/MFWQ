package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.intBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class occultistReadGrimoire extends Skill{
    public occultistReadGrimoire(){
        super("Read Grimoire",
                "Reads from a book of esoteric knowledge, changing the user's and an enemy's intelligence" +
                " in proportion to one another.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
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
        return toCheck.getSP() >= 50;
    }

    @Override //caster's int is buffed by their int divided by the defender's and vis versa
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float casterBuff = Caster.getTempInt();
        float defenderBuff = Defender.getTempInt();
        casterBuff /= defenderBuff;
        defenderBuff /= Caster.getTempInt();
        Caster.printName();
        if(casterBuff > defenderBuff) {
            System.out.println("'s intelligence was buffed!");
            Defender.printName();
            System.out.println("'s intelligence was debuffed!");
            if (casterBuff > 3)
                casterBuff = 3;
            if(defenderBuff < .25f)
                defenderBuff = .25f;
        }
        else {
            System.out.println("'s intelligence was debuffed!");
            Defender.printName();
            System.out.println("'s intelligence was buffed!");
            if (casterBuff < .25f)
                casterBuff = .25f;
            if (defenderBuff > 3)
                defenderBuff = 3;
        }
        Defender.addStatus(new intBuff(11, defenderBuff));
        Caster.addStatus(new intBuff(11, casterBuff));
    }
}
