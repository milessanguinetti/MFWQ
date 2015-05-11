package Characters.Skills.secondClass;

import Characters.Monsters.skeletalArcher;
import Characters.Monsters.skeletalMagus;
import Characters.Monsters.skeletalSoldier;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class lichRaiseSkeleton extends Skill{
    public lichRaiseSkeleton(){
        super("Raise Skeleton",
                "Raises a skeleton from the dead. Skeletons have a variety of different combat specialties.", 35);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(35);
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
        return toCheck.getSP() >= 35;
    }

    @Override //adds a random skeleton minion to the player's party.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(3);
        if (Roll == 0) { //skeletal archer case
            Game.Player.getCurrentBattle().addMinion(true,
                    new skeletalArcher(Caster, Caster.getTempInt()));
        } else if (Roll == 1) { //skeletal soldier case
            Game.Player.getCurrentBattle().addMinion(true,
                    new skeletalSoldier(Caster, Caster.getTempInt()));
        } else { //skeletal magus case
            Game.Player.getCurrentBattle().addMinion(true,
                    new skeletalMagus(Caster, Caster.getTempInt()));
        }
    }
}
