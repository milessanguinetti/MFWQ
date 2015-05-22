package Characters.Skills.secondClass;

import Characters.Monsters.caperEmissarius;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class geneSplicerCreateHomunculus extends Skill{
    public geneSplicerCreateHomunculus(){
        super("Create Homunculus",
                "Gives life to simple homunculus that attacks the enemy. Expires after combat", 40);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(40);
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
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
        return toCheck.getSP() >= 50;
    }

    @Override //adds a caper emissarius minion that defends whoever this spell was cast on.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Game.Player.getCurrentBattle().addMinion(true,
                new caperEmissarius(Caster.getTempDex(), Defender));
    }
}
