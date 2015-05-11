package Characters.Skills.Monster;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class selfDestruct extends Skill{

    //default constructor
    public selfDestruct(){
        super("Explode", "", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        //this skill does not take SP
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override //deal absolute damage equal to the caster's remaining HP
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Defender.getHP(), "Neutral");
        Caster.takeAbsoluteDamage(Defender.getHP());
    }
}
