package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
//neutral property class
public class Neutral extends Property {

    //default constructor sets name to neutral.
    public Neutral(){
        super("Neutral");
    }

    //spooky attacks deal half damage b/c i ain't afraid of no ghost
    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if(attackProperty.equals("Ghost"))
            return Math.round(Damage/2);
        return Damage;
    }
}
