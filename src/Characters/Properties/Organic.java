package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Organic extends Property{

    //default constructor sets name to organic
    Organic(){
        super("Organic");
    }

    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Organic") || attackProperty.equals("Water"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Fire"))
            return Damage * 2;
        return Damage;
    }
}