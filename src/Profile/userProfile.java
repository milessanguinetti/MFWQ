package Profile;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class userProfile {
    private int Coins; //how much money the player has.

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
}
