package Profile;

import Characters.Inventory.Inventory;
import Characters.playerCharacter;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class userProfile extends Inventory{
    private int Coins = 0; //how much money the player has.
    private int philosophersStones; //quantity of philosopher's stones, a non-inventory "item" that
                                    //can be used to resurrect downed characters rather than having
                                    //them permanently die after a battle.
    private playerCharacter [] Party = new playerCharacter[4]; //party of 4

    public playerCharacter [] getParty(){
        return Party;
    }

    //adds a new character into the party. returns 0 if the party is full.
    public int addCharacter(playerCharacter toAdd){
        for(int i = 0; i < 4; ++i){
            if(Party[i] == null){
                Party[i] = toAdd;
                return 1;
            }
        }
        return 0;
    }

    //essentially kills a character if they died. passed integer is index of character
    public void killCharacter(int Index){
        if(philosophersStones >= 1){
            Party[Index].printName();
            System.out.println(" was brought back from the brink of death with a philosopher's stone.");
            --philosophersStones; //decrement stones.
        }
        else {
            Party[Index].printName();
            System.out.println(" has succumbed to their wounds and died."); //death message
            Party[Index] = null; //set index to null
        }
    }

    //adds a passed sum of philosopher's stones
    public int addStones(int toAdd){
        philosophersStones += toAdd;
        return philosophersStones;
    }

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

    public int getCoins(){
        return Coins;
    }

    public int getAverageLevel(){
        int toReturn = 0;
        int toDivide = 0;
        for(int i = 0; i < 4; ++i){
            if(Party[i] != null){
                ++toDivide; //increment todivide to account for the average of more values
                toReturn += Party[i].getLevel();
            }
        }
        return (toReturn/toDivide);
    }
}
