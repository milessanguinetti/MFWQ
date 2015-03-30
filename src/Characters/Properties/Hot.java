package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Hot extends Property{

    //default constructor sets name to Hot
    Hot(){
        super("Hot");
    }

    @Override
    int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Hot") || attackProperty.equals("Wood"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Moist"))
            return Damage * 2;
        return Damage;
    }
}