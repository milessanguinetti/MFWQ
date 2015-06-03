package Profile;

import Characters.Classes.*;
import Characters.Inventory.Weapons.generic1hEdged;
import Characters.Inventory.Weapons.genericGun;
import Characters.Monsters.Kobold;
import Characters.Monsters.babyKobold;
import Characters.gameCharacter;
import Characters.playerCharacter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class Game {
    //the player's user profile; public and static because only one will be used at a time;
    //it would be nonsensical to pass it around into virtually every function I call
    public static userProfile Player; //the game's player.
    private HashMap<KeyCode, Boolean> Input = new HashMap<KeyCode, Boolean>(); //map keys to usable key codes.
    public static mainMenu mainmenu = new mainMenu();
    Stage primaryStage;
    Scene currentScene;

    public Game(Stage primarystage){
        primaryStage = primarystage;
        currentScene = mainmenu.getScene();
        primaryStage.setTitle("MFWQ");
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public mainMenu getMainmenu(){
        return mainmenu;
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

        Random Rand = new Random();
        gameCharacter[] Foes = new gameCharacter[4];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 4; ++j)
                Foes[j] = null; //clean out foes
            if(Rand.nextInt(2) == 0){
                Foes[0] = new Kobold();
                Foes[1] = new babyKobold();
                Foes[2] = new Kobold();
            }
            else{
                Foes[0] = new babyKobold();
                Foes[1] = new Kobold();
                Foes[2] = new babyKobold();
            }
            if(!Game.Player.getCurrentBattle().commenceBattle(Game.Player.getParty(), Foes))
                break;
        }
    }
}
