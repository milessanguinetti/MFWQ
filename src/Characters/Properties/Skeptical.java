package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Skeptical extends Property{

    //default constructor sets name to Skeptical
    Skeptical(){
        super("Skeptical");
    }

    @Override
    int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Spooky") || attackProperty.equals("Grimdark")
                || attackProperty.equals("Holy"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Moist") || attackProperty.equals("Hot") || attackProperty.equals("Wood"))
            return Math.round(Damage * (5/4));
        return Damage;
    }
}