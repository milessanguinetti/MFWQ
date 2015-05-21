package Maps;

/**
 * Created by Miles Sanguinetti on 5/20/2015.
 */
public abstract class Tile {
    //returns whether or not the tile can be traversed.
    public abstract boolean isPassable();

    //returns whether or not the tile contains an event
    public abstract boolean isEvent();

}
