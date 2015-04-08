package Characters.Properties;

/**
 * Created by Miles "Come on and" Slamguinetti on 3/20/15.
 */
//a property is essentially an element, like 'fire' or 'holy.'
//some properties take more or less damage from others.
public abstract class Property {
    private String name;

    //default constructor
    public Property(){}

    //constructor with passed name
    public Property(String toName){
        name = toName;
    }

    //calculates damaged based on elements specified in passed string. offensive elements
    //are strings rather than Property objects because this calculation only needs to
    //happen with one of them and this saves memory.
    public abstract int calculateDamage(int Damage, String attackProperty);

    //checks if this property matches the passed string
    public boolean isProperty(String toCheck){
        if(toCheck.equals(name))
            return true;
        return false;
    }
}
