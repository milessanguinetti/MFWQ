package Profile;

import Characters.Inventory.Inventory;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class userProfile extends Inventory{
    private int Coins; //how much money the player has.
    private int difficultyModifier; //difficulty modifier; typically used for new game+

    //add a passed sum of coins
    public int addCoins(int toAdd){
        Coins += toAdd;
        return Coins;
    }

    //checks if player has coins greater than or equal to the passed int; returns boolean
    public boolean hasCoins(int toCheck){
        if(Coins >= toCheck)
            return true;
        return false;
    }

    //subtract a passed number of coins
    public int subtractCoins(int toSubtract){
        Coins -= toSubtract;
        if(Coins < 0)
            Coins = 0;
        return Coins;
    }

    public int getDifficulty(){
        return difficultyModifier;
    }

    public int incrementDifficulty(int toIncrement){
        difficultyModifier += toIncrement;
        return difficultyModifier;
    }
}
