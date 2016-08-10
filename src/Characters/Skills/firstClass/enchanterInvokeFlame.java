package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Burning;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterInvokeFlame extends Skill{
    public enchanterInvokeFlame(){
        super("Invoke Flame",
                "Scorches the target with a gout of flame.", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
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
        if((toCheck.getSP() >= 15))
            return true; //has sp for this skill
        return false;
    }

    @Override //2.2 * int damage (with staff mod) apply a ticking DoT.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float staffMod = 1;
        if(Caster.hasWeaponType("1h staff", false) || Caster.hasWeaponType("2h staff", false))
            staffMod = 1.5f; //magic with staves deals 150% damage.
        Defender.takeDamage(Math.round(Caster.getTempInt() * 2.2f * staffMod), "Fire");
        Random Rand = new Random();
        if(Rand.nextInt(2) == 0){
            Defender.addStatus(new Burning(Caster.getTempInt(), 5));
            Defender.printName();
            System.out.println(" was set on fire!");
        }
        //50% of the time, the defender will also burn for the caster's int over 5 turns
    }
}
