package Profile;

import Characters.gameCharacter;
import Structures.LLLnode;
import Structures.battleData;
import Structures.orderedLLL;

/**
 * Created by Miles Sanguinetti on 4/9/15.
 */
public class Battle {
    //two parties of four characters each each party has an
    //empty pool of minions that they can add to with some skills.
    gameCharacter [] playerParty = new gameCharacter[4];
    gameCharacter [] playerMinions = new gameCharacter[4];
    gameCharacter [] enemyParty = new gameCharacter[4];
    gameCharacter [] enemyMinions = new gameCharacter[4];
    orderedLLL turnOrder; //ordered LLL to handle turns.

    //bool signifies whether or not the player won or escaped.
    public boolean commenceBattle(gameCharacter [] Allies, gameCharacter [] Enemies){
        while(true) { //infinite loop until one side is victorious.
            if (playerVictory()) {
                for (int i = 0; i < 4; ++i) { //for the entirety of the user's party
                    if (playerParty[i] != null) { //if a character exists
                        if (!playerParty[i].isAlive()) { //if they are dead
                            playerParty[i].Loot(); //get their equipment
                            Game.Player.killCharacter(i); //and kill them
                        }
                    }
                    if (enemyParty[i] != null) {
                        enemyParty[i].Loot(); //loot all enemies as well.
                    }
                }
                return true; //player won the battle
            }
            if (enemyVictory()) {
                for (int i = 0; i < 4; ++i) { //for the entirety of the user's party
                    if (playerParty[i] != null) { //if a character exists
                        if (!playerParty[i].isAlive()) { //if they are dead
                            playerParty[i].Loot(); //get their equipment
                            Game.Player.killCharacter(i); //and kill them
                        }
                    }
                }
                return false; //player lost the battle
            }
        }
    }

    //conducts a single turn from start to finish.
    //some of this function is poorly optimized, so look at it later to improve runtime
    public void takeTurn(){
        battleData BTemp;
        for(int i = 0; i < 4; ++i) { //enter all combatants into the turn LLL structure
            if (playerParty[i] != null) { //if a player char exists at this index
                if(playerParty[i].isAlive()) {  //and is alive
                    BTemp = new battleData(playerParty[i], true); //create a new turn data object
                    turnOrder.Insert(new LLLnode(initializeTurn(BTemp))); //insert the fully initialized object
                }
            }
            if (playerMinions[i] != null) { //if a player minion exists at this index
                if(playerMinions[i].isAlive()) {  //and is alive
                    BTemp = new battleData(playerMinions[i], true);
                    turnOrder.Insert(new LLLnode(initializeTurn(BTemp))); //insert the fully initialized object
                }
            }
            if (enemyParty[i] != null) { //if an enemy character exists at this index
                if(enemyParty[i].isAlive()) {  //and is alive
                    BTemp = new battleData(enemyParty[i], false);
                    turnOrder.Insert(new LLLnode(initializeTurn(BTemp))); //insert the fully initialized object
                }
            }
            if (enemyMinions[i] != null) { //if an enemy minion exists at this index
                if(enemyMinions[i].isAlive()) {  //and is alive
                    BTemp = new battleData(enemyMinions[i], false);
                    turnOrder.Insert(new LLLnode(initializeTurn(BTemp))); //insert the fully initialized object
                }
            }
        }

        while(turnOrder.Peek() != null){ //while characters still need to take their turns
            battleData whoseTurn = ((battleData)turnOrder.Pop()); //get a turn to execute
            int toFind = whoseTurn.getTargetIndex(); //execute it and store the target
            if(toFind == 0){ //if this is a single target, user-only skill...
                whoseTurn.executeCombat(null); //ignore the target selection process.
            }
            else{

            }


        }

        for(int i = 0; i < 4; ++i) { //end turn for all extant combatants
            if (playerParty[i] != null)
                playerParty[i].endTurn();
            if(enemyParty[i] != null)
                enemyParty[i].endTurn();
            if(playerMinions[i] != null)
                playerMinions[i].endTurn();
            if(enemyMinions[i] != null)
                enemyMinions[i].endTurn();
        }
    }

    //initializes a turn; written as a function to cut down on duplicate code
    public battleData initializeTurn(battleData toInitialize){
        if(toInitialize.initializeSkill()) {
            if(toInitialize.initializeTarget(playerParty, playerMinions, 1) == 0)
                return initializeTurn(toInitialize); //recursive call if the user cancelled
        }                                            //the skill they selected.
        else {
            if(toInitialize.initializeTarget(enemyParty, enemyMinions, -1) == 0)
                return initializeTurn(toInitialize); //see above
        }
        return toInitialize;
    }

    //checks to see if the player has won the battle.
    public boolean playerVictory(){
        for (int i = 0; i < 4; ++i) { //for the entirety of the enemy's party
            if (enemyParty[i] != null) { //if a character exists
                if (enemyParty[i].isAlive()) { //if they are alive
                    return false; //return false since at least one enemy is alive.
                }
            }
        }
        return true; //if we get here, all of the enemy party is dead.
    }

    //checks to see if the enemy has won the battle.
    public boolean enemyVictory(){
        for (int i = 0; i < 4; ++i) { //for the entirety of the user's party
            if (playerParty[i] != null) { //if a character exists
                if (playerParty[i].isAlive()) { //if they are alive
                    return false; //return false since at least one ally is alive
                }
            }
        }
        return true; //if we get here, all of the player's party is dead.
    }


}
