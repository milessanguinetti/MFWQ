package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class occultistAberrateArmor extends Skill{
    public occultistAberrateArmor(){
        super("Aberrate Armor",
                "Warps the target's armor with otherworldly forces, dealing high damage based on their armor.", 30);
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
        return true;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 30;
    }

    @Override //deals true damage equal to the caster's int plus the defender's armor.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage(Caster.getTempInt() + Defender.getTempArmor());
    }
}
