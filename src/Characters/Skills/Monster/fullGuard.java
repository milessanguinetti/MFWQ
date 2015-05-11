package Characters.Skills.Monster;

import Characters.Skills.Skill;
import Characters.Status.Guard;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class fullGuard extends Skill{
    gameCharacter toGuard;

    //default constructor
    public fullGuard(){
        super("Guard",
                "", 0);
    }

    //constructor with a specified target.
    public fullGuard(gameCharacter toguard){
        super("Guard", "", 0);
        toGuard = toguard;
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        //this skill uses no sp
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override //shield an ally for 75% damage taken; increase their armor by 25%
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(toGuard == Caster) {
            Caster.printName();
            System.out.println(" cannot guard themself!");
        }
        else{ //case for when the caster is guarding an ally
            Caster.printName();
            System.out.print(" defends ");
            toGuard.printName();
            System.out.println(" with its life!");
            toGuard.addStatus(new Guard(1, 21, Caster));
        }
    }
}
