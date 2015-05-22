package Characters.Skills.secondClass;

import Characters.Monsters.sphaeraeEversioSui;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class geneSplicerSphaeraeEversioSui extends Skill{
    public geneSplicerSphaeraeEversioSui(){
        super("Create Sphaerae Eversio Sui",
                "Creates a living globe that spontaneously explodes on the enemy", 30);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
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
        return toCheck.getSP() >= 30;
    }

    @Override //adds a self-destructing sphere minion that explodes upon the enemy.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Game.Player.getCurrentBattle().addMinion(true,
                new sphaeraeEversioSui(Caster.getTempDex()*2));
    }
}
