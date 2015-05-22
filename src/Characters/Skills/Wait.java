package Characters.Skills;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 4/28/15.
 */
public class Wait extends Skill{
    public Wait(){
        super("Wait", "", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {

    }

    @Override
    public boolean notUsableOnDead() {
        return true;
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
        return true;
    }

    @Override //waits a turn.
    public void takeAction(gameCharacter Caster, gameCharacter Defender){
            Caster.printName();
            System.out.println(" waited.");
    }
}
