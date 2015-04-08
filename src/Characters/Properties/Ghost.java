package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
//spooky property class
public class Ghost extends Property {

    //default constructor sets name to ghost
    Ghost(){
        super("Ghost");
    }

    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if(attackProperty.equals("Neutral"))
            return Math.round(Damage/2);
        if(attackProperty.equals("Holy"))
            return Damage*2;
        return Damage;
    }
}
