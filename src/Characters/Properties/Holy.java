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
    int calculateDamage(int Damage, String attackProperty) {
        if(attackProperty.equals("Spooky") || attackProperty.equals("Holy"))
            return Math.round(Damage/2);
        if(attackProperty.equals("Moist") || attackProperty.equals("Hot") || attackProperty.equals("Wood"))
            return Math.round(Damage*(3/4));
        if(attackProperty.equals("Grimdark"))
            return Damage*2;
        return Damage;
    }
}
