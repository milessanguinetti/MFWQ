package Characters.Properties;

/**
 * Created by Miles Sanguinetti on 3/20/15.
 */
public class Undead extends Property{

    //default constructor sets name to undead
    public Undead(){
        super("Undead");
    }

    @Override
    public int calculateDamage(int Damage, String attackProperty) {
        if (attackProperty.equals("Ghost") || attackProperty.equals("Unholy")
                || attackProperty.equals("Undead"))
            return Math.round(Damage / 2);
        if (attackProperty.equals("Fire") || attackProperty.equals("Holy"))
            return Math.round(Damage * (5/4));
        return Damage;
    }
}