package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Wood extends Property{

    //default constructor sets name to Wood
    Wood(){
        super("Wood");
    }

    @Override
    int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Moist") || attackProperty.equals("Wood"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Hot"))
            return Damage * 2;
        return Damage;
    }
}