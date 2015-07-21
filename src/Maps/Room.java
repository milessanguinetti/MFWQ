package Maps;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public class Room extends Pane {
    private static Map currentMap; //the name of the current map; having a static variable
    private int roomType = 0; //denotes the format of the room's tiles.
    private short [] Tiles;
    /*
    Each normal room contains 9 * 9 tiles; this is not a multidimensional array, but to the end of saving memory
    is rather a single lengthy block of contiguous shorts. To go up or down, one would simply add or subtract 9.
    9x9 tiles denotes 769 spaces.
    */
    private int m, s, f;
    //m is the player's main coordinate in the array of tiles; s denotes the space within that tile; f is their facing

    Room(){

    }

    Room(Map toSet){
        currentMap = toSet;
    }

    public int getRoomType(){
        return roomType;
    }

    public void Enter(int Direction) { //enter the room from a cardinal direction
        s = 5; //s will always be set to 5, the middle space, in this scenario.
        if(Direction == 1){ //north case
            m = 4;
            f = 3;
        }
        else if(Direction == 2){ //east case
            m = 44;
            f = 4;
        }
        else if(Direction == 3){ //south case
            m = 76;
            f = 1;
        }
        else{ //west case
            m = 36;
            f = 2;
        }
    }

    public int Interact(){
        if(Tiles[m] > 4 && Tiles[m] < 9){ //possible exit case
            if(s == 5 && f == Tiles[m] - 12) //if we're in the middle of the tile and facing the right way...
                return f; //return the direction that the player is facing. (1-4)
        }
        else if(Tiles[m] == 20){ //possible treasure case
            if(f == 3 && s == 2 || f == 4 && s == 6 || f == 1 && s == 8 || f == 2 && s == 4){
                return 5; //return 5 if the player is facing towards space 5 in their tile.
            }
        }
        else if(Tiles[m] < 17 && Tiles[m] > 12){ //possible boss case
            if(f == Tiles[m] - 12){ //if the player is facing the correct direction...
                if(Tiles[m] == 13 && s == 2 || Tiles[m] == 14 && s == 6 ||
                        Tiles[m] == 15 && s == 8 || Tiles[m] == 16 && s == 3)
                    return 6; //return 6 if the player is in the right spot as well.
            }
        }
        return 0; //0 denotes nothing happened.
    }

    public boolean Move(int Direction) { //if possible, moves the player one space in the specified direction
        //1 = north, 2 = east, 3 = south, 4 = west
        f = Direction; //set facing to chosen direction.
        int targetedTile = m; //set our targets to the player's current location for starts.
        int targetedSpace = s;
        if(Direction == 1){ //north case

        }
        else if(Direction == 2){ //east case

        }
        else if(Direction == 3){ //south case

        }
        else{ //west case

        }

        return false; //return false if the player could not move.
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

        //fill the pane with images according to the specified tiles
        setPrefSize(1280, 800);
        //setTranslateY(600); TBD IF WE NEED THIS
        Rectangle Background = new Rectangle(1280, 800); //establish a mostly-translucent shaded background
        Background.setFill(Color.BLACK);
        getChildren().add(Background);
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
            toPlace.setTranslateY(-48 + 96 * (i/9));
            toPlace.setTranslateX(208 + 96 * (i%9));
            getChildren().add(toPlace);
        }
    }
}
