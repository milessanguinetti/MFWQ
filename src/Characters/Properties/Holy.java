package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Holy extends Property{

    //default constructor sets name to holy
    Holy(){
        super("Holy");
    }

    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if(attackProperty.equals("Ghost") || attackProperty.equals("Holy"))
            return Math.round(Damage/2);
        if(attackProperty.equals("Water") || attackProperty.equals("Fire") || attackProperty.equals("Organic"))
            return Math.round(Damage*(3/4));
        if(attackProperty.equals("Dark"))
            return Damage*2;
        return Damage;
    }
}
