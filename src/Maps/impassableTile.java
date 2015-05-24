package Maps;

/**
 * Created by Miles on 5/20/2015.
 */
public class impassableTile extends Tile {
    @Override
    public boolean isEvent() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
