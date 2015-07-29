package Profile;

import Characters.Classes.*;
import Characters.Inventory.Weapons.Nodachi;
import Characters.Inventory.Weapons.genericGun;
import Characters.playerCharacter;
import Maps.Map;
import Maps.Valley01;
import javafx.stage.Stage;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class Game {
    //the player's user profile; public and static because only one will be used at a time;
    //it would be nonsensical to pass it around into virtually every function I call
    public static userProfile Player = new userProfile(); //the game's player.
    public static mainMenu mainmenu = new mainMenu();
    public static Battle battle = new Battle();
    public static Map currentMap;
    private Stage primaryStage;
    private long delayStartTime; //variable to track time at which a delay was requested.
    private int delayDuration; //variable to track requested delay

    public Game(Stage primarystage){
        mainmenu.setGame(this);
        primaryStage = primarystage;
        primaryStage.setTitle("MFWQ");
        primaryStage.minWidthProperty().bind(battle.getScene().heightProperty().multiply(1.6));
        primarystage.minHeightProperty().bind(battle.getScene().widthProperty().divide(1.6));
        //maintain aspect ratio of graphics if window is resized.
        Test();
        swapToMainMenu();
        primaryStage.show();
    }

    public void Test(){
        playerCharacter bob = new playerCharacter("Sergeant Pepper", "Faithful",
                350, 100, 10, 10, 10, 10, 10, 10, 0);
        Player.addCharacter(bob);
        bob.setLeft(new genericGun(6));
        bob.setRight(new Nodachi()); //equip the good sergeant with a motherfucking nodachi
        bob.addClass(new Soldier());
        bob.addClass(new Rogue());
        bob.addClass(new Alchemist());
        bob.addClass(new Inquisitor());
        bob.setPrimaryClass(new Primalist());
        bob.setSecondaryClass(new geneSplicer());

        currentMap = new Valley01();
    }

    public void swapToBattle(){
        primaryStage.setScene(battle.getScene());
    }

    public void swapToMainMenu(){
        primaryStage.setScene(mainmenu.getScene());
    }

    public void swapToMap(){
        if(currentMap != null){
            primaryStage.setScene(currentMap.getScene());
        }
    }

    public void swapToOverworld(){
        //NOT YET IMPLEMENTED
        swapToMainMenu();
    }

    public void setDelay(int Delay){
        delayStartTime = System.currentTimeMillis();
        delayDuration = Delay;
    }

    public boolean isDelayOver(){
        if(System.currentTimeMillis() - delayDuration > delayStartTime)
            return true;
        return false;
    }
}
