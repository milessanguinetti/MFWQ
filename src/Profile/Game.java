package Profile;

import Characters.Classes.*;
import Characters.Inventory.Weapons.Nodachi;
import Characters.Inventory.Weapons.genericGun;
import Characters.playerCharacter;
import Maps.Map;
import Maps.Valley01;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    public static Map currentMap;
    private Stage primaryStage;
    private long delayStartTime; //variable to track time at which a delay was requested.
    private int delayDuration; //variable to track requested delay
    private MediaPlayer mediaPlayer; //media player variable for playing music.

    public Game(Stage primarystage){
        mainmenu.setGame(this);
        primaryStage = primarystage;
        primaryStage.setTitle("MFWQ");
        primaryStage.minWidthProperty().bind(battle.getScene().heightProperty().multiply(1.6));
        primarystage.minHeightProperty().bind(battle.getScene().widthProperty().divide(1.6));
        //maintain aspect ratio of graphics if window is resized.
        swapToMainMenu();
        primaryStage.show();
    }

    public void newPlayer(){
        Player = new userProfile();
        playerCharacter bob = new playerCharacter("Sergeant Pepper", "Faithful",
                350, 100, 10, 10, 10, 10, 10, 10, 0);
        Player.addCharacter(bob);
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
        swapToMap();
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
            System.err.println("Load unsuccessful; "+ e.getMessage() + " not serializable");
        }
        catch (ClassNotFoundException e){
            System.err.println("Load unsuccessful.");
        }

        currentMap = new Valley01();
        currentMap.enterFromOverworld();
        swapToMap();
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

    public void swapToBattle(){
        primaryStage.setScene(battle.getScene());
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("music/battletheme.mp3").toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void swapToMainMenu(){
        primaryStage.setScene(mainmenu.getScene());
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media((getClass().getResource("music/titletheme.mp3")).toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


    public void swapToMap(){
        if(currentMap != null){
            primaryStage.setScene(currentMap.getMapScene());
            if(mediaPlayer != null)
                mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(new Media((getClass().getResource("music/maptheme.mp3")).toString()));
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public void swapToOverworld(){
        //NOT YET IMPLEMENTED
        writeToDisk();
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
