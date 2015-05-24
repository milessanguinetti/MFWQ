package Maps;

/**
 * Created by Miles on 5/20/2015.
 */
public class monsterEventTile extends eventTile {
    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public boolean isEvent() {
        return true;
    }
}
