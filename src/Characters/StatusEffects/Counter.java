package Characters.statusEffects;

import Characters.gameCharacter;

import java.io.Serializable;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
//a class for effects that skills and passives set which counter attacks.
//similar to a damage effect, but changed to a separate class/programming mechanism
//because it takes dramatically different arguments and behaves differently in some respects.
public abstract class Counter implements Serializable{
    private int Duration;

    //constructor with name and duration
    public Counter(int duration){
        Duration = duration;
    }

    //decrements the duration and then returns it.
    public int Decrement(){
        --Duration;
        return Duration;
    }

    //abstract function for actually executing the movie.
    abstract public void executeCounter(gameCharacter Attacker, gameCharacter Defender);

    public abstract boolean canEvadeAttack(gameCharacter Attacker, gameCharacter Defender);
}
