package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Poisoned;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class rogueAmplifyPoison extends Skill{
    public rogueAmplifyPoison(){
        super("Amplify Poison",
                "Transforms standard poisons afflicting foes into a particularly powerful poison.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
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
        return toCheck.getSP() >= 20;
    }

    @Override //replace existing poison with a poison that deals 2x right weapon damage each turn.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.statusRemove("Poisoned")){
            Defender.addStatus(new Poisoned(Caster.getWeaponDamage(true) * 2, 5));
            Defender.printName();
            System.out.println(" was afflicted with a deadly poison!");
        }
    }
}
