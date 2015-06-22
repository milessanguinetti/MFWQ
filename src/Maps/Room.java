package Maps;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public class Room {
    private static String mapName; //the name of the current map; having a static variable
    private int [] Tiles = new int[81];
    /*
    Each normal room contains 9 * 9 tiles; this is not a multidimensional array, but to the end of saving memory,
    is rather a single lengthy block of contiguous integers. To go up or down, one would simply add or subtract 9.
    9x9 tiles denotes 769 spaces.
    */
    private int x; //player's x and y coordinates
    private int y;

    Room(){

    }

    public void setMapName(String toName){
        mapName = toName;
    }

    public void Draw(){

    }

    public void Enter(int Direction) { //enter the room from a cardinal direction

    }

    public boolean moveTo(int x, int y) { //returns false if the desired coordinate is innavigable

        return true;
    }

    public void generateTiles(int roomType){ //generates tiles according to a passed room format:
        /*
        1 = ^
        2 = >
        3 = \/
        4 = <
        5 = ^ \/
        6 = < >
        7 = < ^ >
        8 = < \/ >
        9 = < ^ > \/
         */

    }
}
