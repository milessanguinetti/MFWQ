package Characters;

/**
 * Created by Miles Sanguinetti on 3/21/15.
 */
public interface combatEffect {
    public void printName();
    public boolean canUse(gameCharacter toCheck);
    public void takeAction(gameCharacter Caster, gameCharacter Defender);
}
