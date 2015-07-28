package Profile;

import Characters.Monster;
import Characters.Skills.fleeObject;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Structures.LLLnode;
import Structures.battleData;
import Structures.orderedLLL;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


/**
 * Created by Miles Sanguinetti on 4/9/15.
 */
public class Battle {
    //two parties of four characters each; each party has an
    //empty pool of minions that they can add to with some skills.
    private gameCharacter[] playerParty = new gameCharacter[4];
    private gameCharacter[] playerMinions = new gameCharacter[4];
    private gameCharacter[] enemyParty = new gameCharacter[4];
    private gameCharacter[] enemyMinions = new gameCharacter[4];
    private orderedLLL turnOrder = new orderedLLL(); //ordered LLL to handle turns.
    private battleUI Interface = new battleUI();
    private Scene scene;
    private battleData BTemp;
    private int State = 0;
    /*
    The current state of the battle; values are as follows:
    0-3 = currently filling a player's commands at index n
    4 = done filling player commands and ready to fill AI commands
    5 = currently executing extant combat commands.
     */
    private gameCharacter toRoute = null; //a variable to route input to.

    public Battle() {
        Pane contentRoot = new Pane();
        contentRoot.setPrefSize(1280, 800);
        scene = new Scene(contentRoot);

        //TEST TEST TEST TEST
        javafx.scene.shape.Rectangle Background = new javafx.scene.shape.Rectangle(1280, 800);
        Background.setFill(Color.ALICEBLUE);
        contentRoot.getChildren().add(Background);

        contentRoot.getChildren().add(Interface);

        scene.setOnKeyReleased(event -> {
            if (toRoute != null) { //if we're currently storing user commands from input
                toRoute.handleInput(event); //let the character handle the input
                fillBattleData(); //and call the fillbattledata method to see what to do next.
            } else { //otherwise, we're currently mid-turn
                if (turnOrder.Peek() == null) { //if we've executed all commands for this given turn...
                    for (int i = 0; i < 4; ++i) { //end turn for all extant combatants
                        if (playerParty[i] != null)
                            playerParty[i].endTurn();
                        if (enemyParty[i] != null)
                            enemyParty[i].endTurn();
                        if (playerMinions[i] != null)
                            playerMinions[i].endTurn();
                        if (enemyMinions[i] != null)
                            enemyMinions[i].endTurn();
                        if (playerVictory() || enemyVictory())
                            endBattle();
                    }
                    State = 0; //and if there aren't, we reset state to 0.
                    for (int i = 0; i < 4; ++i) { //find a new character that needs their battle data filled...
                        if (playerParty[i] != null) {
                            if (playerParty[i].isAlive()) {
                                BTemp = playerParty[i].getAndWipeBattleData(true); //set BTemp to their data...
                                toRoute = playerParty[i]; //route user input to them...
                                playerParty[i].initializeCombatData(); //and begin initializing their combat data
                                break;
                            }
                        }
                        ++State; //incrementing the state as we go to ensure that we know what char we're on.
                    }
                } else {
                    if (executeTurn()) //so we continue executing the turn
                        endBattle();
                    while (turnOrder.Peek() != null) {
                        if (((battleData) turnOrder.Peek().returnData()).attackerIsDead())
                            turnOrder.Pop(); //ensure that the next data entry is not one in which the attacker is dead.
                        else
                            break;
                    }
                }
            }
        });
    }

    public Scene getScene() {
        return scene;
    }

    public battleUI getInterface() {
        return Interface;
    }

    //bool signifies whether or not the player won or escaped.
    public void commenceBattle(gameCharacter[] Allies, gameCharacter[] Enemies) {
        turnOrder.removeAll(); //nullifies any extant turn order data from previous battles
        for (int i = 0; i < 4; ++i) {
            if (Allies[i] != null)
                Allies[i].applyAutoBuffs(); //initialize stats
            if (Enemies[i] != null) {
                Enemies[i].applyAutoBuffs();
            }
        }
        playerParty = Allies;
        enemyParty = Enemies;
        State = 0; //set state to 0.
        for(int i = 0; i < 4; ++i){
            if(playerParty[i] != null){
                if(playerParty[i].isAlive()){
                    BTemp = playerParty[i].getAndWipeBattleData(true); //set BTemp to this character's battle data.
                    toRoute = playerParty[i];
                    playerParty[i].initializeCombatData(); //begin initializing their combat data
                    break;
                }
            }
            ++State; //if the character at this index is dead or nonexistant, increment the state so we know that.
        }
    }

    public void endBattle(){
        int totalexp = 0;
        int totaljexp = 0; //total values for exp and jexp.
        for (int i = 0; i < 4; ++i) { //for the entirety of the user's party
            if (playerParty[i] != null) { //if a character exists
                playerParty[i].clearStatus(); //clear their status.
                if (!playerParty[i].isAlive()) { //if they are dead
                    playerParty[i].Loot(); //get their equipment
                    Game.Player.killCharacter(i); //and kill them
                }
            }
            if (enemyParty[i] != null) {
                if (!enemyParty[i].isAlive()) {
                    enemyParty[i].Loot(); //loot all enemies as well.
                    totalexp += ((Monster) enemyParty[i]).getExp(); //and gain exp
                    totaljexp += ((Monster) enemyParty[i]).getJexp();
                }
            }
            playerMinions[i] = null; //clear minions
            enemyMinions[i] = null;
        }

        Interface.printLeftAtNextAvailable("Your party gained " + totalexp + " experience and " + totaljexp +
                " job experience.");

        for (int i = 0; i < 4; ++i) {
            if (playerParty[i] != null) {
                if (playerParty[i].isAlive()) { //let all living player party members gain exp
                    ((playerCharacter) playerParty[i]).Ding(totalexp, totaljexp);
                }
            }
        }
        if(enemyVictory()) { //since this state can also be reached via a flee command, we print !enemyvictory
            Game.mainmenu.getCurrentGame().swapToMainMenu();
        }
        else
            Game.mainmenu.getCurrentGame().swapToMap();
    }

    //Executes a turn that has all of its commands decided upon by the combatants
    public void fillBattleData() {
        if (State < 4) {
            if(BTemp.isFilled()){ //if recent user input filled up the current party member's battle data...
                turnOrder.Insert(new LLLnode(BTemp)); //insert the fully initialized object
                ++State; //increment state.
                while(State < 4) { //if state is STILL less than 4...
                    if(playerParty[State] != null) {
                        if(playerParty[State].isAlive()) {
                            BTemp = playerParty[State].getAndWipeBattleData(true); //get the next party member's data
                            playerParty[State].initializeCombatData(); //and initialize it.
                            toRoute = playerParty[State]; //route all user input to this player party member.
                            break; //break the loop; we found a party member that needs to have commands entered.
                        }
                    }
                    ++State; //if we get here, a party member was not found in the loop and we need to increment state.
                }
            }
        }
        if (State == 4) {
            printDefaultStats(); //now we reset the right hand display to default stats.
            for (int i = 0; i < 4; ++i) { //enter all non-player combatants into the turn LLL structure
                if (playerMinions[i] != null) { //if a player minion exists at this index
                    if (playerMinions[i].isAlive()) {  //and is alive
                        BTemp = playerMinions[i].getAndWipeBattleData(true);
                        playerMinions[i].initializeCombatData();
                        turnOrder.Insert(new LLLnode(BTemp)); //insert the fully initialized object
                    }
                }
                if (enemyParty[i] != null) { //if an enemy character exists at this index
                    if (enemyParty[i].isAlive()) {  //and is alive
                        BTemp = enemyParty[i].getAndWipeBattleData(false);
                        enemyParty[i].initializeCombatData();
                        turnOrder.Insert(new LLLnode(BTemp)); //insert the fully initialized object
                    }
                }
                if (enemyMinions[i] != null) { //if an enemy minion exists at this index
                    if (enemyMinions[i].isAlive()) {  //and is alive
                        BTemp = enemyMinions[i].getAndWipeBattleData(false);
                        enemyMinions[i].initializeCombatData();
                        turnOrder.Insert(new LLLnode(BTemp)); //insert the fully initialized object
                    }
                }
            }
            toRoute = null;
            if(executeTurn()){
                endBattle();
            }
        }
    }

    //Executes a turn that has all of its commands decided upon by the combatants
    public boolean executeTurn() {
        battleData whoseTurn = ((battleData) turnOrder.Pop()); //get a turn to execute
        if(whoseTurn == null) { //just a check to ensure that we don't execute a nonexistent turn.
            if(enemyVictory() || playerVictory())
                return true;
            return false;
        }
        int toFind = whoseTurn.getTargetIndex(); //store the target specified in the turn
        if (toFind == 0) { //if this is a single target, user-only skill...
            try {
                if(whoseTurn.executeCombat(null)) { //ignore the target selection process; this if statement and recursive
                    return executeTurn(); //call handle dead characters' turns to avoid confusing ui situations
                }
            } catch (fleeObject Caught) {
                return true;
            }
        } //execute with a null reference since the data will target the attacker by default.
        else {
            gameCharacter[] Primary; //empty references for the side that we will be targeting.
            gameCharacter[] Secondary; //more efficient than writing the same code 4 times.
            if (toFind < 0) { //case for enemy targets.
                toFind = toFind * (-1); //make the index positive; we know we're targeting enemies by now.
                if (toFind < 5) { //case for a character primary target.
                    --toFind; //decrement toFind to make it into a usable index.
                    Primary = enemyParty;
                    Secondary = enemyMinions; //set both empty references to appropriate values
                } else { //case for a minion primary target.
                    toFind -= 5; //subtract five to make toFind into a usable index.
                    Primary = enemyMinions;
                    Secondary = enemyParty; //set both empty references to appropriate values
                }
            } else { //case for player targets.
                if (toFind < 5) { //case for a character primary target.
                    --toFind; //decrement toFind to make it into a usable index.
                    Primary = playerParty;
                    Secondary = playerMinions; //set both empty references to appropriate values
                } else { //case for a minion primary target.
                    toFind -= 5; //subtract five to make toFind into a usable index.
                    Primary = playerMinions;
                    Secondary = playerParty; //set both empty references to appropriate values
                }
            }
            gameCharacter Target = Primary[toFind]; //establish a primary target.
            if (!(Target.isAlive() == whoseTurn.notUsableOnDead())) { //if the target is dead, we find a new target.
                for (int i = 1; i < 3; ++i) { //first we search every other character in the array
                    if ((toFind + i) < 4) { //if this index is part of the array
                        if (Primary[toFind + i] != null) {
                            if (Primary[toFind + i].isAlive() == whoseTurn.notUsableOnDead()) {
                                toFind += i; //adjust toFind to compensate
                                Target = Primary[toFind]; //set target to this unit
                                break; //and break the for loop
                            }
                        }
                    }
                    if ((toFind - i) >= 0) { //if this index is part of the array
                        if (Primary[toFind - i] != null) {
                            if (Primary[toFind - i].isAlive() == whoseTurn.notUsableOnDead()) {
                                toFind -= i; //adjust toFind to compensate
                                Target = Primary[toFind]; //set target to this unit
                                break; //and break the for loop.
                            }
                        }
                    }
                }
                if (!(Target.isAlive() == whoseTurn.notUsableOnDead())) { //if we didn't find any replacements in the primary array...
                    for (int i = 1; i < 3; ++i) { //we check the secondary array.
                        if ((toFind + i) < 4) { //if this index is part of the array
                            if (Secondary[toFind + i] != null) {
                                if (Secondary[toFind + i].isAlive() == whoseTurn.notUsableOnDead()) {
                                    toFind += i;
                                    Target = Secondary[toFind]; //set target to this unit
                                    break; //and break the for loop
                                }
                            }
                        }
                        if ((toFind - i) >= 0) { //if this index is part of the array
                            if (Secondary[toFind - i] != null) {
                                if (Secondary[toFind - i].isAlive() == whoseTurn.notUsableOnDead()) {
                                    toFind -= i;
                                    Target = Secondary[toFind]; //set target to this unit
                                    break; //and break the for loop.
                                }
                            }
                        }
                    }//additionally, if we chose a secondary target, we swap which array
                    //is primary and which is secondary so that aoe is executed properly.
                    gameCharacter[] Temp = Primary;
                    Primary = Secondary;
                    Secondary = Temp;
                }
            }//by the time the program gets here, we definitely have a live target.
            try {
                if(whoseTurn.executeCombat(Target))
                    return executeTurn(); //execute another turn if the attacker is dead.
            } catch (fleeObject Caught) {
                return true;
            }
            int AoE = whoseTurn.getRebound(); //get the aoe value of the attack used.
            if (AoE > 0) { //if this is an aoe spell
                try {
                    if(whoseTurn.executeCombat(Secondary[toFind])) //deal damage to target at same index as target.
                        return executeTurn();//execute another turn if the attacker is dead.
                } catch (fleeObject Caught) {
                    return true;
                }
                for (int i = 1; i < 3; ++i) { //we deal damage to other targets within range, too.
                    if ((toFind + i) < 4) { //if this index is part of the array
                        if (Primary[toFind + i] != null && i <= AoE) { //if primary @ this index isn't null
                            try {
                                if(whoseTurn.executeCombat(Primary[toFind + i])) //deal damage if they're in range
                                    return executeTurn();//execute another turn if the attacker is dead.
                            } catch (fleeObject Caught) {
                                return true;
                            }
                        }
                        if (Secondary[toFind + i] != null && i <= AoE - 1) { //secondary @ this index - 1
                            try {
                                if(whoseTurn.executeCombat(Secondary[toFind + i])) //deal damage if they're in range
                                    return executeTurn();//execute another turn if the attacker is dead.
                            } catch (fleeObject Caught) {
                                return true;
                            }
                        }
                    }
                    if ((toFind - i) >= 0) { //if this index is part of the array
                        if (Primary[toFind - i] != null && i <= AoE) { //if primary @ this index isn't null
                            try {
                                if(whoseTurn.executeCombat(Primary[toFind - i])) //deal damage if they're in range\
                                    return executeTurn();//execute another turn if the attacker is dead.
                            } catch (fleeObject Caught) {
                                return true;
                            }
                        }
                        if (Secondary[toFind - i] != null && i <= AoE - 1) { //secondary @ this index - 1
                            try {
                                if(whoseTurn.executeCombat(Secondary[toFind - i])) //deal damage if they're in range
                                    return executeTurn();//execute another turn if the attacker is dead.
                            } catch (fleeObject Caught) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        whoseTurn.endCombat(); //in any case, take care of SP loss and such.
        printDefaultStats(); //update hp statistics following the combat.

        if (enemyVictory()) //return true if either side has won
            return true;
        if (playerVictory())
            return true;

        return false; //otherwise, if we get here, no one has won yet.
    }

    public gameCharacter[] getParty(boolean isPlayerSide){
        if(isPlayerSide)
            return playerParty;
        return enemyParty;
    }

    public gameCharacter[] getMinions(boolean isPlayerSide) {
        if(isPlayerSide)
            return playerMinions;
        return enemyMinions;
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

    public void printDefaultStats() {
        if(playerParty[1] == null){ //case for 3 missing party members
            Interface.printRight("HP:" + playerParty[0].getHP() + "/" + playerParty[0].getHPCap() +
                    " SP: " + playerParty[0].getSP() + "/" + playerParty[0].getSPCap()
                    + " " + playerParty[0].getName());
        }
        else if(playerParty[2] == null){ //case for 2 missing party members
            Interface.printRight("HP:" + playerParty[0].getHP() + "/" + playerParty[0].getHPCap() +
                            " SP: " + playerParty[0].getSP() + "/" + playerParty[0].getSPCap()
                            + " " + playerParty[0].getName(),
                    "HP:" + playerParty[1].getHP() + "/" + playerParty[1].getHPCap() +
                            " SP: " + playerParty[1].getSP() + "/" + playerParty[1].getSPCap()
                            + " " + playerParty[1].getName());
        }
        else if(playerParty[3] == null){ //case for 1 missing party member
            Interface.printRight("HP:" + playerParty[0].getHP() + "/" + playerParty[0].getHPCap() +
                            " SP: " + playerParty[0].getSP() + "/" + playerParty[0].getSPCap()
                            + " " + playerParty[0].getName(),
                    "HP:" + playerParty[1].getHP() + "/" + playerParty[1].getHPCap() +
                            " SP: " + playerParty[1].getSP() + "/" + playerParty[1].getSPCap()
                            + " " + playerParty[1].getName(),
                    "HP:" + playerParty[2].getHP() + "/" + playerParty[2].getHPCap() +
                            " SP: " + playerParty[2].getSP() + "/" + playerParty[2].getSPCap()
                            + " " + playerParty[2].getName());
        }
        else{ //case for a full party
            Interface.printRight("HP:" + playerParty[0].getHP() + "/" + playerParty[0].getHPCap() +
                            " SP: " + playerParty[0].getSP() + "/" + playerParty[0].getSPCap()
                            + " " + playerParty[0].getName(),
                    "HP:" + playerParty[1].getHP() + "/" + playerParty[1].getHPCap() +
                            " SP: " + playerParty[1].getSP() + "/" + playerParty[1].getSPCap()
                            + " " + playerParty[1].getName(),
                    "HP:" + playerParty[2].getHP() + "/" + playerParty[2].getHPCap() +
                            " SP: " + playerParty[2].getSP() + "/" + playerParty[2].getSPCap()
                            + " " + playerParty[2].getName(),
                    "HP:" + playerParty[3].getHP() + "/" + playerParty[3].getHPCap() +
                            " SP: " + playerParty[3].getSP() + "/" + playerParty[3].getSPCap()
                            + " " + playerParty[3].getName());
        }
    }

    //adds a minion to a team depending on the passed boolean (true = player's side)
    public void addMinion(boolean whichSide, gameCharacter toAdd){
        gameCharacter [] Minions; //empty reference filled by below if/else statements
        toAdd.applyAutoBuffs(); //initialize autobuffs if this minion has any.
        if(whichSide) {
            Minions = playerMinions;
            System.out.print("A ");
            toAdd.printName();
            System.out.println(" joined your party!");
        }
        else {
            Minions = enemyMinions; //set minions to the appropriate side.
            System.out.print("A ");
            toAdd.printName();
            System.out.println(" joined the enemy!");
        }
        for(int i = 0; i < 4; ++i){
            if(Minions[i] == null){ //if an index is empty
                Minions[i] = toAdd; //add the minion into that index
                return; //and return; we're done.
            }
        }//if we get here, all indices are full.
        int weakest = 0; //so we find the weakest extant minion and replace it.
        for(int i = 1; i < 4; ++i){
            if(Minions[i].getHP() < Minions[weakest].getHP()) //if minion i has lower hp
                weakest = i; //it becomes the weakest
        }
        Minions[weakest].printName(); //display a message explaining the replaced minion
        System.out.println(" was called off to make room for ");
        toAdd.printName();
        System.out.println(".");
        Minions[weakest] = toAdd; //replace weakest with toadd.
    }

    //removes a passed combatant from the fight.
    public void nullCombatant(gameCharacter toRemove){
        for(int i = 0; i < 4; ++i){
            if(toRemove == playerMinions[i]) {
                playerMinions[i] = null;
                return;
            }
            if(toRemove == playerParty[i]) {
                playerParty[i] = null;
                return;
            }
            if(toRemove == enemyParty[i]) {
                enemyParty[i] = null;
                return;
            }
            if(toRemove == enemyMinions[i]) {
                enemyMinions[i] = null;
                return;
            }
        }
    }
}
