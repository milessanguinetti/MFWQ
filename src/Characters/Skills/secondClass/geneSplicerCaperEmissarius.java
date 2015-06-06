package Characters.Skills.secondClass;

import Characters.Monsters.caperEmissarius;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class geneSplicerCaperEmissarius extends Skill{
    public geneSplicerCaperEmissarius(){
        super("Create Caper Emissarius",
                "Gives life to a homunculus that serves as a scapegoat, protecting an ally", 50);
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
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 50;
    }

    @Override //adds a caper emissarius minion that defends whoever this spell was cast on.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Game.battle.addMinion(true,
                new caperEmissarius(Caster.getTempDex(), Defender));
    }
}
