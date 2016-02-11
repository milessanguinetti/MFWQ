package Maps;

import Characters.Inventory.Item;
import Characters.Monster;
import Profile.Game;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public class Room extends StackPane {
    private static ImageView playerIcon;
    private static int mostRecentKeyPressed;
    private static boolean [] directionalKeysPressed = new boolean[4];
    private static boolean characterInMotion = false;
    //array of boolean vars; [0] = north, [1] = east, etc
    public static Map currentMap; //the name of the current map; having a static variable
    private int roomType = 0; //denotes the format of the room's tiles.
    private short [] Tiles;
    /*
    Each normal room contains 9 * 9 tiles; this is not a multidimensional array, but to the end of saving memory
    is rather a single lengthy block of contiguous shorts. To go up or down, one would simply add or subtract 9.
    9x9 tiles denotes 769 spaces.
    */
    private int m, s, f;
    private Monster [] Boss; //empty variable for a boss encounter
    private Item Loot; //empty variable for a piece of loot in this room.
    //m is the player's main coordinate in the array of tiles; s denotes the space within that tile; f is their facing

    Room(){
        setAlignment(Pos.CENTER);
    }

    Room(Monster [] boss){
        setAlignment(Pos.CENTER);
        Boss = new Monster[4];
        for(int i = 0; i < 4; ++i){
            Boss[i] = boss[i];
        }
    }

    Room(Item loot){
        setAlignment(Pos.CENTER);
        Loot = loot;
    }

    public int getRoomType(){
        return roomType;
    }

    public void Enter(int Direction) { //enter the room from a cardinal direction

        if(playerIcon == null){ // load player icon
            try(InputStream imginput = Files.newInputStream(Paths.get("resources/images/player.png"))){
                ImageView playerimage = new ImageView(new Image(imginput));
                playerimage.setFitWidth(32);
                playerimage.setFitHeight(32); //preserve aspect ratio
                playerIcon = playerimage;
            }

            catch (IOException e){
                System.out.println("Error loading player image.");
            }
        }

        s = 5; //s will always be set to 5, the middle space, in a room entry scenario.
        if(Direction == 1){ //north case
            for(int i = 0; i < 81; ++i){ //search map for northern entrance
                if(Tiles[i] == 5){
                    m = i;
                    playerIcon.setTranslateY(-384 + 96 * (m/9)); //add player icon @ this posiiton
                    playerIcon.setTranslateX(-384 + 96 * (m%9));
                    getChildren().add(playerIcon);
                    break;
                }
            }
            f = 3;
        }
        else if(Direction == 2){ //east case
            for(int i = 0; i < 81; ++i){ //search map for eastern entrance
                if(Tiles[i] == 6){
                    m = i;
                    playerIcon.setTranslateY(-384 + 96 * (m/9)); //add player icon @ this posiiton
                    playerIcon.setTranslateX(-384 + 96 * (m%9));
                    getChildren().add(playerIcon);
                    break;
                }
            }
            f = 4;
        }
        else if(Direction == 3){ //south case
            for(int i = 0; i < 81; ++i){ //search map for southern entrance
                if(Tiles[i] == 7){
                    m = i;
                    playerIcon.setTranslateY(-384 + 96 * (m/9)); //add player icon @ this posiiton
                    playerIcon.setTranslateX(-384 + 96 * (m%9));
                    getChildren().add(playerIcon);
                    break;
                }
            }
            f = 1;
        }
        else{ //west case
            for(int i = 0; i < 81; ++i){ //search map for western entrance
                if(Tiles[i] == 8){
                    m = i;
                    playerIcon.setTranslateY(-384 + 96 * (m/9)); //add player icon @ this posiiton
                    playerIcon.setTranslateX(-384 + 96 * (m%9));
                    getChildren().add(playerIcon);
                    break;
                }
            }
            f = 2;
        }
    }

    public int Interact(){
        if(directionalKeysPressed[mostRecentKeyPressed])
            return 0; //ignore interaction calls if the player is in motion.
        if(Tiles[m] > 4 && Tiles[m] < 10){ //possible exit case
            if(s == 5 && f == Tiles[m] - 4) { //if we're in the middle of the tile and facing the right way...
                getChildren().remove(playerIcon); //exit, removing the player icon from the pane
                return f; //return the direction that the player is facing. (1-4)
            }
        }
        else if(Tiles[m] == 20){ //possible treasure case
            if(f == 3 && s == 2 || f == 4 && s == 6 || f == 1 && s == 8 || f == 2 && s == 4){
                Tiles[m] = 0;
                ImageView toPlace = currentMap.getBlankGround(); //replace treasure with blank ground
                toPlace.setTranslateY(-384 + 96 * (m/9)); //on the player's current tile.
                toPlace.setTranslateX(-384 + 96 * (m % 9));
                getChildren().add(toPlace);
                getChildren().remove(playerIcon);
                getChildren().add(playerIcon);
                return 5; //return 5 if the player is facing towards space 5 in their tile.
            }
        }
        else if(Tiles[m] < 17 && Tiles[m] > 12){ //possible boss case
            if(f == Tiles[m] - 12){ //if the player is facing the correct direction...
                if(Tiles[m] == 13 && s == 2 || Tiles[m] == 14 && s == 6 ||
                        Tiles[m] == 15 && s == 8 || Tiles[m] == 16 && s == 3) {
                    Tiles[m] -= 8;
                    ImageView toPlace = currentMap.getExit(Tiles[m]); //replace boss with empty exit
                    toPlace.setTranslateY(-384 + 96 * (m/9)); //on the player's current tile.
                    toPlace.setTranslateX(-384 + 96 * (m%9));
                    getChildren().add(toPlace);
                    return 6; //return 6 if the player is in the right spot as well.
                }
            }
        }
        return 0; //0 denotes nothing happened.
    }

    public boolean Move(int Direction) { //if possible, moves the player at least one space in the specified direction
        //1 = north, 2 = east, 3 = south, 4 = west
        f = Direction; //set facing to chosen direction.
        int targetedTile = m; //set our targets to the player's current location for starts.
        int targetedSpace = s;
        boolean Valid = false; //boolean value to store valid moves.
        if(Direction == 1){ //north case
            targetedSpace -= 3;
            if(targetedSpace < 1){
                targetedTile -= 9; //one tile up
                targetedSpace += 9; //to a space on the bottom
            }
        }
        else if(Direction == 2){ //east case
            targetedSpace += 1;
            if(targetedSpace%3 == 1){
                targetedSpace -= 3; //stay in the same row, switch sides
                targetedTile += 1; //go one tile east
            }
        }
        else if(Direction == 3){ //south case
            targetedSpace += 3;
            if(targetedSpace > 9){
                targetedTile += 9; //one tile down
                targetedSpace -= 9; //to a space on the top.
            }
        }
        else{ //west case
            targetedSpace -= 1;
            if(targetedSpace%3 == 0){
                targetedSpace += 3; //stay in same row; switch sides
                targetedTile -= 1; //go one tile west
            }
        }
        switch(Tiles[targetedTile]){ //a switch hingent on what tile we're attempting to move through/into
            case 0: { //ground
                //empty ground is always valid for movement
                Valid = true; //note that the proposed movement is valid.
                break; //break the switch
            }
            case 1: { //walls (north)
                //walls are never valid for movement.
                break; //break the switch
            }
            case 2:{ //east
                //walls are never valid for movement.
                break; //break the switch
            }
            case 3:{ //south
                //walls are never valid for movement.
                break; //break the switch
            }
            case 4:{ //west
                //walls are never valid for movement.
                break; //break the switch
            }
            case 5: { //exits (north)
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 5 || targetedSpace == 8){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 6:{ //east
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 5 || targetedSpace == 4){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 7:{ //south
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 5 || targetedSpace == 2){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 8:{ //west
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 5 || targetedSpace == 6){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 9: { //corners (northwest)
                //corners are never valid for movement.
                break; //break the switch
            }
            case 10:{ //northeast
                //corners are never valid for movement.
                break; //break the switch
            }
            case 11:{ //southeast
                //corners are never valid for movement.
                break; //break the switch
            }
            case 12: { //northwest
                //corners are never valid for movement.
                break; //break the switch
            }
            case 13: { //exits with bosses (north)
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 8){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 14:{ //east
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 4){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 15:{ //south
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 2){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 16:{ //west
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace == 6){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 17: { //1 * 2 obstacle
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace != 3 && targetedSpace != 6){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 18: { //2 * 2 obstacle
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace != 2 && targetedSpace != 3 && targetedSpace != 5 && targetedSpace != 6){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 19: { //3 * 2 obstacle
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace > 6){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }
            case 20: { //treasure
                //if the space that we're targeting is valid in the context of its tile...
                if(targetedSpace != 5){
                    Valid = true; //note that the proposed movement is valid.
                }
                break; //break the switch
            }

        }
        if(Valid){ //if the move was valid.
            //Game.mainmenu.getCurrentGame().setDelay(250);
            s = targetedSpace; //set s to targeted space.
            m = targetedTile; //set m to the original targeted tile.
            characterInMotion = true;
            final TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerIcon);
            transition.setFromX(playerIcon.getTranslateX());
            transition.setFromY(playerIcon.getTranslateY());
            transition.setToX(playerIcon.getTranslateX() + 32 * (-1 * (Direction - 3)) * ((Direction + 1) % 2));
            transition.setToY(playerIcon.getTranslateY() + 32 * (Direction - 2) * (Direction % 2));
            transition.playFromStart();
            //TEST
            Timeline continueWalk = new Timeline(new KeyFrame( //after the walking animation has played...
                    Duration.millis(500),
                    ae -> {
                        characterInMotion = false;
                        if(currentMap.rollForEncounter()){
                            for(int i = 0; i < 4; ++i)
                                directionalKeysPressed[i] = false; //stop all movement if we encounter an enemy.
                        }
                        else { //otherwise try and move again if we haven't stopped.
                            if (directionalKeysPressed[mostRecentKeyPressed])
                                Move(mostRecentKeyPressed + 1);
                        }
                    }));
            continueWalk.play();
            //TEST
            return true;
        }
        for(int i = 0; i < 4; ++i){ //if we were forced to stop moving for one reason or another, reset key bools
            directionalKeysPressed[i] = false;
        }
        characterInMotion = false;
        return false; //return false if the player could not move.
    }

    public void pressKey(int dir, boolean pressedOrReleased){
        if(pressedOrReleased){ //if a key was pressed
            if(!directionalKeysPressed[dir]) { //ignore input if the key was already pressed
                if(!directionalKeysPressed[mostRecentKeyPressed]) {
                    mostRecentKeyPressed = dir; //record the new key
                    directionalKeysPressed[mostRecentKeyPressed] = true; //set its boolean to true
                    if(!characterInMotion)
                        Move(dir + 1); //begin motion if we were stationary
                }
                mostRecentKeyPressed = dir; //record the new key
                directionalKeysPressed[mostRecentKeyPressed] = true; //set its boolean to true
            }
        }
        else{ //if a key was released
            directionalKeysPressed[dir] = false; //set the specified key's boolean to false
            if(!directionalKeysPressed[mostRecentKeyPressed]){
                for(int i = 0; i < 4; ++i){
                    directionalKeysPressed[i] = false; //reset all keys if we're ceasing movement
                }
            }
        }
    }

    public void generateTiles(int roomtype){ //generates tiles according to a passed room format:
        /*
        1 = ^ up only
        2 = > right only
        3 = \/ down only
        4 = < left only
        5 = ^ \/ up-down
        6 = < > left-right
        7 = < ^ left-up
        8 = ^ > up-right
        9 = > \/ right-down
        10 = < \/ down-left
        11 = < \/ > all but up
        12 = < ^ \/ all but right
        13 = < ^ > all but down
        14 ^ > /\ all but left
        15 = < ^ > \/ all directions
         */
        if(roomType != 0){ //just return if this room was already built.
            return;
        }
        roomType = roomtype;
        Random Rand = new Random();
        int roll = Rand.nextInt(2);
        switch(roomtype){ //fill the room with tiles according to the room type and our roll.
            /*
            0 = ground;
            1-4 = walls
            5-8 = exits
            9-12 = corners
            13-16 = exits with bosses
            17 = 1 * 2 obstacle
            18 = 2 * 2 obstacle
            19 = 3 * 2 obstacle
            20 = treasure
             */
            case 1: //up only
                if(roll == 0){
                    Tiles = new short[]
                            {9, 1, 1, 1, 5, 1, 1, 1, 10,
                            4, 0, 0, 0, 0, 0, 0, 0, 2,
                            4, 0, 0, 0, 0, 0, 0, 0, 2,
                            4, 0, 0, 0, 0, 0, 0, 0, 2,
                            4, 0, 0, 0, 0, 0, 0, 0, 2,
                            4, 0, 0, 0, 0, 0, 0, 0, 2,
                            4, 0, 0, 0, 0, 0, 0, 0, 2,
                            4, 0, 0, 0, 0, 0, 0, 0, 2,
                            12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11,
                                    0, 17, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 18, 0, 0};
                }
                break;
            case 2: //right only
                if(roll == 0){
                    Tiles = new short[]
                                    {18, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 9, 1, 1, 1, 1, 1, 1, 10,
                                    0, 4, 0, 0, 0, 0, 0, 0, 2,
                                    0, 4, 0, 0, 0, 0, 0, 0, 2,
                                    0, 4, 0, 0, 0, 0, 0, 0, 6,
                                    0, 4, 0, 0, 0, 0, 0, 0, 2,
                                    0, 4, 0, 0, 0, 0, 0, 0, 2,
                                    17, 12, 3, 3, 3, 3, 3, 3, 11,
                                    0, 0, 0, 0, 19, 0, 0, 0, 0,};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 18, 0, 0, 19, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 17, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                break;
            case 3: //down only
                if(roll == 0){
                    Tiles = new short[]
                                    {0, 9, 1, 1, 1, 1, 1, 1, 10,
                                    0, 4, 0, 0, 0, 0, 0, 0, 2,
                                    0, 4, 0, 0, 0, 0, 0, 0, 2,
                                    0, 4, 0, 17, 0, 0, 0, 0, 2,
                                    0, 4, 0, 0, 0, 0, 19, 0, 2,
                                    18, 4, 0, 0, 0, 0, 0, 0, 2,
                                    18, 4, 0, 0, 0, 0, 0, 0, 2,
                                    0, 4, 0, 0, 0, 0, 0, 0, 2,
                                    0, 12, 3, 3, 7, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;
            case 4: //left only
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                break;
            case 5: //up-down
                if(roll == 0){
                    Tiles = new short[]
                                    {0, 0, 9, 1, 5, 1, 10, 0, 0,
                                    0, 0, 4, 0, 0, 0, 2, 0, 0,
                                    0, 18, 4, 0, 0, 0, 2, 0, 0,
                                    0, 0, 4, 0, 0, 0, 2, 0, 0,
                                    0, 0, 4, 0, 0, 0, 2, 19, 0,
                                    0, 0, 4, 0, 0, 0, 2, 0, 0,
                                    0, 17, 4, 0, 0, 0, 2, 0, 0,
                                    17, 0, 4, 0, 0, 0, 2, 0, 0,
                                    0, 0, 12, 3, 7, 3, 11, 0, 0};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;
            case 6: //left-right
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                break;
            case 7: //up-left
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                break;
            case 8: //up-right
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                break;
            case 9: //down-right
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;
            case 10: //down-left
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;
            case 11: //all but up
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 1, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;
            case 12: //all but right
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;
            case 13: //all but down
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 3, 3, 3, 3, 11};
                }
                break;
            case 14: //all but left
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;
            case 15: //all directions
                if(roll == 0){
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                else{
                    Tiles = new short[]
                                    {9, 1, 1, 1, 5, 1, 1, 1, 10,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    8, 0, 0, 0, 0, 0, 0, 0, 6,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    4, 0, 0, 0, 0, 0, 0, 0, 2,
                                    12, 3, 3, 3, 7, 3, 3, 3, 11};
                }
                break;

        }
        if(Loot != null){
            Tiles[40] = 20;
        }

        //fill the pane with images according to the specified tiles
        int tileType;
        ImageView toPlace;
        for(int i = 0; i < 81; ++i){ //for every tile in the room
            tileType = Tiles[i]; //determine what kind of tile it is
            //then retrieve the corresponding image for that type of tile.
            if(tileType == 0){ //ground case
                toPlace = currentMap.getGround();
            }
            else if(tileType < 5){ //wall case
                toPlace = currentMap.getWall(tileType - 1);
            }
            else if(tileType < 9){ //exit case
                toPlace = currentMap.getExit(tileType - 5);
            }
            else if(tileType < 13){ //corner case
                toPlace = currentMap.getCorner(tileType - 9);
            }
            /*else if(tileType < 17){ //exit with boss case
                toPlace = currentMap.getExit(tileType - 13);
                //CODE TO PLACE BOSS IMAGE
            }*/
            else if(tileType < 20){ //obstacle case
                toPlace = currentMap.getObstacle(tileType - 16);
            }
            else{ //treasure case
                toPlace = currentMap.getTreasure();
            }
            //by now we have (barring some kind of bug) gotten the imageview that we want.
            //toPlace.setTranslateY(-48 + 96 * (i / 9));
            //toPlace.setTranslateX(208 + 96 * (i % 9));
            toPlace.setTranslateY(-384 + 96 * (i / 9));
            toPlace.setTranslateX(-384 + 96 * (i % 9));
            getChildren().add(toPlace);
        }
    }

    public Monster [] getBoss(){
        return Boss;
    }

    public Item getLoot(){
        return Loot;
    }
}
