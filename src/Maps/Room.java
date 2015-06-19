package Maps;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public abstract class Room {

    private int [] Tiles = new int[81];
    /*
    Each normal room contains 9 * 9 tiles; this is not a multidimensional array, but to the end of saving memory,
    but is rather a single lengthy block of contiguous integers. To go up or down, one would simply add or subtract 9.
    9x9 tiles denotes 91 spaces.
    */
    private int x; //player's x and y coordinates
    private int y;

    Room(){
        for(int i = 0; i < 81; ++i){

        }
    }

    public abstract void Draw();

    public abstract void Enter(int Direction); //enter the room from a cardinal direction

    public abstract boolean moveTo(int x, int y); //returns false if the desired coordinate is innavigable
    
}
