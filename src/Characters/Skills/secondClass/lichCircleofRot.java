package Characters.Skills.secondClass;

import Characters.Monsters.Zombie;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class lichCircleofRot extends Skill {
    public lichCircleofRot(){
        super("Circle of Rot",
                "Encircles several foes within a field of decay. Raises them as zombies if it kills them.", 25);
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
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 25;
    }

    @Override //deals damage; raises a zombie if the target dies.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.takeDamage(2 * Caster.getTempInt(), "Undead") == 0)
            Game.Player.getCurrentBattle().addMinion(true,
                    new Zombie(Caster));
    }
}
