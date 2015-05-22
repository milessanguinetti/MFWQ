package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class occultistQuintessenceFlood extends Skill{
    public occultistQuintessenceFlood(){
        super("Quintessence Flood",
                "Floods the enemy team with a storm of quintessence, fully restoring their SP, but" +
                        " dealing damage equal to the SP restored.", 70);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(70);
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
        return toCheck.getSP() >= 70;
    }

    @Override //deals damage equal to the difference of the defender's max SP and their current SP.
              //fully restores each target's SP.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage(Defender.getSPCap() - Defender.getSP());
        Defender.subtractSP(Defender.getSP() - Defender.getSPCap());
    }
}
