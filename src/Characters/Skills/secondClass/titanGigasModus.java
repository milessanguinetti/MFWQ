package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class titanGigasModus extends Skill{
    public titanGigasModus(){
        super("Gigas Modus",
                "Adapts the user's anatomy to serve as a mighty defender, shielding allies and boosting armor.", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        //this spell does not take SP to cast.
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

    @Override //deal 20% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {

    }
}
