package Profile;

import Characters.Classes.*;
import Characters.Inventory.Weapons.Nodachi;
import Characters.Inventory.Weapons.generic2hBlunt;
import Characters.Inventory.Weapons.genericGun;
import Characters.playerCharacter;
import Cities.City;
import Maps.Map;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
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
    public static experienceNotification  experiencenotification = new experienceNotification();
    public static userProfile Player; //the game's player.
    public static mainMenu mainmenu = new mainMenu();
    public static Battle battle = new Battle();
    public static overworldMap overworld = new overworldMap();
    public static settingsScreen settings = new settingsScreen();
    public static characterScreen characterscreen;
    public static Map currentMap;
    public static City currentCity;
    private static optionsOverlay options = new optionsOverlay();
    private static Stage primaryStage;
    private static StackPane gameRoot = new StackPane();
    private static MediaPlayer mediaPlayer; //media player variable for playing music.
    private static float musicVolume = 1; //a floating point between 0.0 and 1.0 that denotes the volume of
                               //any music that the game plays, with 0.0 == silent and 1.0 == full
    private static float masterVolume = 1; //see above, but impacts both music and sound effects.

    public Game(Stage primarystage){
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
        //gameRoot.getChildren().add(notification);
        //gameRoot.getChildren().add(experiencenotification);
        swapToMainMenu(null);
        primaryStage.show();
    }

    public static void newPlayer(){
        Player = new userProfile();
        //TEST CHARACTER
        playerCharacter bob = new playerCharacter("Spaghetti", "Faithful",
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
        //TEST CHARACTER 2
        playerCharacter oxy = new playerCharacter("Oxy", "Heretic", 350, 100, 10, 10, 10, 10, 10, 10, 0);
        Player.addCharacter(oxy);
        new generic2hBlunt(20).Use(oxy);
        characterClass oxyPrimary = new Rogue();
        oxy.setPrimaryClass(oxyPrimary);
        oxy.addClass(oxyPrimary);
        oxy.addClass(new Soldier());
        oxy.addClass(new Alchemist());
        oxy.addClass(new Inquisitor());

        swapToOverworld(mainmenu.getPane());
        writeToDisk();
    }

    public static void loadPlayer(){
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
        for(int i = 0; i < 4; ++i)
            if(Player.getParty()[i] != null)
            Player.getParty()[i].rebuildTransientValues(); //rebuild each character's transient values.
        swapToOverworld(mainmenu.getPane());
        writeToDisk();
    }

    public static void writeToDisk(){
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

    public static void setMusicVolume(float toSet){
        musicVolume = toSet;
        if(mediaPlayer != null)
            mediaPlayer.setVolume(masterVolume * toSet);
    }

    public static void setMasterVolume(float toSet){
        masterVolume = toSet;
        if(mediaPlayer != null)
            mediaPlayer.setVolume(musicVolume * toSet);
    }

    public static float getMasterVolume(){
        return masterVolume;
    }

    public static void swapToBattle(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(battle.getPane());
        battle.getPane().requestFocus();
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media(battle.getClass().getResource("music/battletheme.mp3").toString()));
        mediaPlayer.setVolume(musicVolume * masterVolume);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void swapToMainMenu(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(mainmenu.getPane());
        mainmenu.Reset();
        mainmenu.getPane().requestFocus();
        if(mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media((battle.getClass().getResource("music/titletheme.mp3")).toString()));
        mediaPlayer.setVolume(musicVolume * masterVolume);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


    public static void swapToMap(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        if(currentMap != null){
            gameRoot.getChildren().add(currentMap.getPane());
            currentMap.getPane().requestFocus();
            if(mediaPlayer != null)
                mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(new Media((battle.getClass().getResource("music/maptheme.mp3")).toString()));
            mediaPlayer.setVolume(musicVolume * masterVolume);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
        else{
            swapToOverworld(null);
            overworld.requestFocus();
        }
    }

    public static void swapToOverworld(Node toRemove){
        //NOT YET IMPLEMENTED
        currentMap = null;
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(overworld);
        overworld.requestFocus();
    }

    public static void swapToInventory(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(Player.getContentRoot());
        Player.getContentRoot().requestFocus();
        Player.setItemBox(0);
    }

    public static void swapToSettings(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(settings);
        settings.requestFocus();
    }

    public static void swapToCharacterScreen(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        if(characterscreen == null)
            characterscreen = new characterScreen();
        gameRoot.getChildren().add(characterScreen.getContentRoot());
        characterScreen.swapToMainPane();
    }

    public static void swapToCity(Node toRemove){
        if(toRemove != null)
            gameRoot.getChildren().remove(toRemove);
        gameRoot.getChildren().add(currentCity);
        currentCity.requestFocus();
        currentCity.focusShop();
    }

    public static void lootNotificationToFront(){
        gameRoot.getChildren().remove(experiencenotification);
        notification.Animate();
        if(notification.isActive()) {
            gameRoot.getChildren().remove(notification);
            gameRoot.getChildren().add(notification);
        }
    }

    public static void expNotificationToFront(){
        experiencenotification.updateAndAnimate();
        gameRoot.getChildren().remove(experiencenotification);
        gameRoot.getChildren().add(experiencenotification);
    }

    public static void removeNotifications(){
        gameRoot.getChildren().remove(experiencenotification);
        gameRoot.getChildren().remove(notification);
    }

    public static void addOptionsOverlay(){
        options.Reset();
        gameRoot.getChildren().add(options);
        options.requestFocus();
    }

    public static void removeOptionsOverylay(){
        gameRoot.getChildren().remove(options);
        if(currentCity != null){
            currentCity.requestFocus();
        }
        else if(currentMap != null) {
            currentMap.getPane().requestFocus();
        }
        else
            overworld.requestFocus();
    }
}
