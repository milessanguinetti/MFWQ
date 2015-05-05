package Characters.Skills.Monster;

import Characters.Monsters.babyKobold;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class poopOutaBaby extends Skill {
    public poopOutaBaby(){
        super("Poop Out a Baby", "", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.getSP() >= 50)
            return true;
        return false;
    }

    @Override //poops out a baby
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Game.Player.getCurrentBattle().addMinion(false, new babyKobold());
    }
}
