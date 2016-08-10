package Characters.Skills;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 4/28/15.
 */
public class Wait extends Skill{
    public Wait(){
        super("", "", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {

    }

    @Override
    public boolean notUsableOnDead() {
        return true;
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
        return true;
    }

    @Override //waits a turn.
    public void takeAction(gameCharacter Caster, gameCharacter Defender){
        Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " waited.");
    }
}
