package Profile;

import Characters.Classes.*;
import Characters.Inventory.Weapons.Nodachi;
import Characters.Inventory.Weapons.genericGun;
import Characters.Inventory.Weapons.koboldSlayingSword;
import Characters.playerCharacter;
import Maps.Map;
import Maps.Valley01;
import Maps.overWorld;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public class Game {
    //the player's user profile; public and static because only one will be used at a time;
    //it would be nonsensical to pass it around into virtually every function I call
    public static Notification notification = new Notification();
    public static userProfile Player; //the game's player.
    public static mainMenu mainmenu = new mainMenu();
    public static Battle battle = new Battle();
    public static overWorld overworld = new overWorld();
    public static Map currentMap;
    private Stage primaryStage;
    private StackPane gameRoot = new StackPane();
    private long delayStartTime; //variable to track time at which a delay was requested.
    private int delayDuration; //variable to track requested delay
    private MediaPlayer mediaPlayer; //media player variable for playing music.
    private float musicVolume = 1; //a floating point between 0.0 and 1.0 that denotes the volume of
                               //any music that the game plays, with 0.0 == silent and 1.0 == full
    private float masterVolume = 1; //see above, but impacts both music and sound effects.

    public Game(Stage primarystage){
        mainmenu.setGame(this);
        primaryStage = primarystage;
        primaryStage.setTitle("MFWQ");
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        gameRoot.setAlignment(Pos.CENTER);
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        /*primarystage.setX(bounds.getWidth() - primarystage.getWidth()/2);
        primarystage.setY(bounds.getHeight() - primarystage.getHeight()/2);
        Scene gameScene = new Scene(gameRoot, bounds.getWidth(),
                bounds.getHeight(), Color.BLACK);*/
        Scene gameScene = new Scene(gameRoot, Color.BLACK);
        gameScene.setRoot(gameRoot);
        Scale scale = new Scale((bounds.getHeight()/864), (bounds.getHeight()/864));
        scale.setPivotX(bounds.getWidth()/2);
        scale.setPivotY(bounds.getHeight()/2);
        gameRoot.getTransforms().setAll(scale);
        primaryStage.setScene(gameScene);
        Rectangle background = new Rectangle(bounds.getWidth(), bounds.getHeight());
        background.setFill(Color.BLACK);
        gameRoot.getChildren().add(background);
        gameRoot.getChildren().add(notification);
        swapToMainMenu(null);
        primaryStage.show();
    }

    public void newPlayer(){
        Player = new userProfile();
        playerCharacter bob = new playerCharacter("Spaghetti", "Faithful",
                350, 100, 10, 10, 10, 10, 10, 10, 0);
        Player.addCharacter(bob);
        Player.Insert(new Nodachi(5));
        Player.Insert(new koboldSlayingSword(5));
        Player.Insert(new koboldSlayingSword(15));
        Player.Insert(new koboldSlayingSword(25));
        Player.Insert(new koboldSlayingSword(35));
        Player.Insert(new koboldSlayingSword(45));
        Player.Insert(new koboldSlayingSword(55));
        Player.Insert(new koboldSlayingSword(65));
        Player.Insert(new koboldSlayingSword(75));
        Player.Insert(new koboldSlayingSword(85));
        Player.Insert(new koboldSlayingSword(95));
        Player.Insert(new koboldSlayingSword(105));
        Player.Insert(new koboldSlayingSword(115));
        Player.Insert(new koboldSlayingSword(125));

        new genericGun(6).Use(bob);
        new Nodachi().Use(bob); //equip the good sergeant with a motherfucking nodachi
        bob.addClass(new Soldier());
        bob.addClass(new Rogue());
        bob.addClass(new Alchemist());
        bob.addClass(new Inquisitor());
        bob.setPrimaryClass(new Primalist());
        bob.setSecondaryClass(new geneSplicer());

        currentMap = new Valley01();
        currentMap.enterFromOverworld();
        swapToMap(mainmenu.getPane());
        writeToDisk();
    }

    public void loadPlayer(){
        try {
            FileInputStream fileInput = new FileInputStream("MFWQsave.dat");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Player = (userProfile) objectInput.readObject(); //set player to the loaded object cast as a userprofile
            objectInput.close();
        }
        catch (IOException e){
            System.err.println("Load unsuccessful.");
        }
        catch (ClassNotFoundException e){
            System.err.println("Load unsuccessful.");
        }

        currentMap = new Valley01();
        currentMap.enterFromOverworld();
        swapToMap(mainmenu.getPane());
        writeToDisk();
    }

    public void writeToDisk(){
        try {
            FileOutputStream FileOutput = new FileOutputStream("MFWQsave.dat");
            ObjectOutputStream ObjectOutput = new ObjectOutputStream(FileOutput);
            ObjectOutput.writeObject(Player); //write the game out to file
            ObjectOutput.close(); //close the stream
        }
        catch (IOException e) {
            System.err.println ("Save unsuccessful; "+ e.getMessage() + " not serializable");
        }
    }

    public void setMusicVolume(float toSet){
        musicVolume = toSet;
        if(mediaPlayer != null)
            mediaPlayer.setVolume(masterVolume * toSet);
    }

    public void setMasterVolume(float toSet){
        masterVolume = toSet;
        if(mediaPlayer != null)
            mediaPlayer.setVolume(musicVolume * toSet);
    }

    public float getMasterVolume(){
        return masterVolume;
    }

    public void swapToBattle(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(battle.getPane());
        battle.getPane().requestFocus();
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("music/battletheme.mp3").toString()));
        mediaPlayer.setVolume(musicVolume);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void swapToMainMenu(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(mainmenu.getPane());
        mainmenu.getPane().requestFocus();
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media((getClass().getResource("music/titletheme.mp3")).toString()));
        mediaPlayer.setVolume(musicVolume * masterVolume);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


    public void swapToMap(Node toRemove){
        gameRoot.getChildren().remove(toRemove);
        if(currentMap != null){
            gameRoot.getChildren().add(currentMap.getPane());
            currentMap.getPane().requestFocus();
            if(mediaPlayer != null)
                mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(new Media((getClass().getResource("music/maptheme.mp3")).toString()));
            mediaPlayer.setVolume(musicVolume * masterVolume);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
        else{
            swapToOverworld(null);
            overworld.getPane().requestFocus();
        }
    }

    public void swapToOverworld(Node toRemove){
        //NOT YET IMPLEMENTED
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        writeToDisk();
        swapToMainMenu(null);
    }

    public void swapToInventory(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(Player.getContentRoot());
        Player.getContentRoot().requestFocus();
        Player.setItemBox(0);
    }

    public void setDelay(int Delay){
        delayStartTime = System.currentTimeMillis();
        delayDuration = Delay;
    }

    public boolean isDelayOver(){
        if(delayDuration == 0)
            return true;
        if(System.currentTimeMillis() - delayDuration > delayStartTime) {
            delayDuration = 0;
            return true;
        }
        return false;
    }

    public void notificationToFront(){
        gameRoot.getChildren().remove(notification);
        gameRoot.getChildren().add(notification);
    }
}
