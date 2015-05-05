package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Unholy extends Property{

    //default constructor sets name to Unholy
    public Unholy(){
        super("Unholy");
    }

    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Ghost") || attackProperty.equals("Unholy"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Water") || attackProperty.equals("Fire") || attackProperty.equals("Organic"))
            return Math.round(Damage * (3 / 4));
        if (attackProperty.equals("Holy"))
            return Damage * 2;
        return Damage;
    }
}