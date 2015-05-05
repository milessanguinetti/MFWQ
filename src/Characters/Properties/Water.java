package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Water extends Property{

    //default constructor sets name to Water
    public Water(){
        super("Water");
    }

    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Water") || attackProperty.equals("Fire"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Organic"))
            return Damage * 2;
        return Damage;
    }
}