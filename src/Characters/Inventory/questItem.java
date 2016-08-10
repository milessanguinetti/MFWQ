package Characters.Inventory;

import javafx.scene.layout.StackPane;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class questItem extends Item {

    @Override
    public StackPane buildSpecificItemDisplay() {
        return null; //quest items do not have a specific item display to build at this time.
    }
}
