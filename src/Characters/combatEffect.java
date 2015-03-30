package Characters;

/**
 * Created by Miles Sanguinetti on 3/21/15.
 */
abstract public class combatEffect {
    public abstract void printName();
    public abstract boolean canUse(gameCharacter toCheck);
    public abstract void takeAction(gameCharacter Caster, gameCharacter Defender);
}
