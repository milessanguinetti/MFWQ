package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
//neutral property class
public class Neutral extends Property {

    //default constructor sets name to neutral.
    Neutral(){
        super("Neutral");
    }

    //spooky attacks deal half damage b/c i ain't afraid of no ghost
    @Override
    int calculateDamage(int Damage, String attackProperty) {
        if(attackProperty.equals("Spooky"))
            return Math.round(Damage/2);
        return Damage;
    }
}
