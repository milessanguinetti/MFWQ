package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Moist extends Property{

    //default constructor sets name to Moist
    Moist(){
        super("Moist");
    }

    @Override
    int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Moist") || attackProperty.equals("Hot"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Wood"))
            return Damage * 2;
        return Damage;
    }
}