package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Grimdark extends Property{

    //default constructor sets name to Grimdark
    Grimdark(){
        super("Grimdark");
    }

    @Override
    int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Spooky") || attackProperty.equals("Grimdark"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Moist") || attackProperty.equals("Hot") || attackProperty.equals("Wood"))
            return Math.round(Damage * (3 / 4));
        if (attackProperty.equals("Holy"))
            return Damage * 2;
        return Damage;
    }
}