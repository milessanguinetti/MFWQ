package Maps;

/**
 * Created by Miles on 5/20/2015.
 */
public class passableTile extends Tile {
    @Override
    public boolean isEvent() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
