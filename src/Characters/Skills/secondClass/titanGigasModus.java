package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Guard;
import Characters.statusEffects.Modus;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class titanGigasModus extends Skill{
    public titanGigasModus(){
        super("Gigas Modus",
                "Adapts the user's anatomy to serve as a mighty defender, boosting armor and shielding their allies.", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        //this spell does not take SP to cast.
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
        return 4;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override //buff caster's armor by 50%, shield allies for 50% of damage taken.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender == Caster){ //case for when the caster is casting the spell on themself
            Caster.addStatus(new Modus(1));
        }
        else{ //case for when the caster is guarding an ally
            Defender.addStatus(new Guard(.5f, 6, Caster));
        }
    }
}
