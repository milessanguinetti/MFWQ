package Characters.Status;

/**
 * Created by Miles Sanguinetti on 3/18/15.
 */
//interface for effects that impact damage dealt to the afflicted
public interface damageEffect {
        //damage calculation method
        public int calculateDamage(int toCalculate, String Property);
}
