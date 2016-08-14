package Characters.Inventory;

import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class Accessory extends Item implements equipableItem{

    //default constructor
    public Accessory(){}

    //constructor that effectively handles all data members
    public Accessory(String Name, String Description){
        super(Name, Description);
    }

    @Override //equip the item to the passed character
    public boolean Use(gameCharacter toUseOn){
        playerCharacter useOn = ((playerCharacter) toUseOn);
        useOn.setAccessory(this);
        addStats(useOn); //finally, we add this weapon's stats to the character
        return true;
    }

    @Override //unequip the item from the passed character
    public void Unequip(playerCharacter toEquipTo) {
        toEquipTo.setAccessory(null); //set armor to null
        subtractStats(toEquipTo); //subtract stats
        Game.Player.Insert(this); //insert the item into global inventory
    }

    @Override
    public StackPane buildSpecificItemDisplay() {
        StackPane toReturn = new StackPane();
        toReturn.setAlignment(Pos.CENTER);
        //potential text for stats granted by the weapon
        toReturn.setMaxSize(300, 30);
        toReturn.setMinSize(300, 30);
        Text statText = new Text(getStatText());
        statText.setTextAlignment(TextAlignment.CENTER);
        statText.setWrappingWidth(260);
        statText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        toReturn.getChildren().add(statText);


        return toReturn;
    }
}
