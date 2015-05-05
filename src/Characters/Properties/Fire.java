package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Fire extends Property{

    //default constructor sets name to Fire
    public Fire(){
        super("Fire");
    }

    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Fire") || attackProperty.equals("Organic"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Water"))
            return Damage * 2;
        return Damage;
    }
}