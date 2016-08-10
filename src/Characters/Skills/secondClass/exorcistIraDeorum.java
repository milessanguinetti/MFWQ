package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//wrath of the gods
public class exorcistIraDeorum extends Skill {
    public exorcistIraDeorum(){
        super("Ira Deorum",
                "Invokes the wrath of God to smite the caster's enemies.", 25);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(25);
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
        return toCheck.getSP() >= 25;
    }

    @Override //deal 20% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {

    }
}
