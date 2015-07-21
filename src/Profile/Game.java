package Profile;

import Characters.Classes.*;
import Characters.Inventory.Weapons.generic1hEdged;
import Characters.Inventory.Weapons.genericGun;
import Characters.Monsters.Kobold;
import Characters.Monsters.babyKobold;
import Characters.gameCharacter;
import Characters.playerCharacter;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class Game {
    //the player's user profile; public and static because only one will be used at a time;
    //it would be nonsensical to pass it around into virtually every function I call
    public static userProfile Player = new userProfile(); //the game's player.
    public static mainMenu mainmenu = new mainMenu();
    public static Battle battle = new Battle();
    private Stage primaryStage;

    public Game(Stage primarystage){
        battle.setGame(this);
        mainmenu.setGame(this);
        primaryStage = primarystage;
        primaryStage.setTitle("MFWQ");
        primaryStage.minWidthProperty().bind(battle.getScene().heightProperty().multiply(1.6));
        primarystage.minHeightProperty().bind(battle.getScene().widthProperty().divide(1.6));
        //maintain aspect ratio of graphics if window is resized.
        Test();
        swapToBattle();
        primaryStage.show();
    }

    public mainMenu getMainmenu(){
        return mainmenu;
    }

    public Battle getBattle(){
        return battle;
    }

    public void Test(){
        playerCharacter bob = new playerCharacter("Sergeant Pepper", "Faithful",
                350, 100, 10, 10, 10, 10, 10, 10, 0);
        Player.addCharacter(bob);
        bob.setLeft(new genericGun(6));
        bob.setRight(new generic1hEdged(6)); //equip the good sergeant with a randomly generated weapon
        bob.addClass(new Soldier());
        bob.addClass(new Rogue());
        bob.addClass(new Alchemist());
        bob.addClass(new Inquisitor());
        bob.setPrimaryClass(new Primalist());
        bob.setSecondaryClass(new geneSplicer());

        Random Rand = new Random();
        gameCharacter[] Foes = new gameCharacter[4];
        if (Rand.nextInt(2) == 0) {
            Foes[0] = new Kobold();
            Foes[1] = new babyKobold();
            Foes[2] = new Kobold();
        } else {
            Foes[0] = new babyKobold();
            Foes[1] = new Kobold();
            Foes[2] = new babyKobold();
        }
        battle.commenceBattle(Game.Player.getParty(), Foes);
    }

    public void swapToBattle(){
        primaryStage.setScene(battle.getScene());
    }

    public void swapToMainMenu(){
        primaryStage.setScene(mainmenu.getScene());
    }
}
