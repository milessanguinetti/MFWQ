package Characters;

import Characters.Skills.fleeObject;

/**
 * Created by Miles Sanguinetti on 3/21/15.
 */
//basically an interface that contains a combat-oriented reference to something
//like a skill or weapon that is being used to attack.
public interface combatEffect {
    //prints the combat effect's name
    public void printName();
    public boolean canUse(gameCharacter toCheck);
    public void takeAction(gameCharacter Caster, gameCharacter Defender)throws fleeObject;
    public boolean isOffensive();
    public int getAoE();
    public void spLoss(gameCharacter Caster);
}
