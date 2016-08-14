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
public abstract class Armor extends Item implements equipableItem {
    private int Armor; //the weapon's damage value (a shield's equals its armor bonus)
    private boolean isHeavy; //denotes whether or not this is heavy armor.

    //default constructor
    public Armor(){}

    //constructor that effectively handles all data members
    public Armor(String Name, String Description, int armor, boolean isheavy){
        super(Name, Description);
        Armor = armor;
        isHeavy = isheavy;
    }

    @Override //equip the item to the passed character
    public boolean Use(gameCharacter toUseOn){
        if(!((playerCharacter)toUseOn).canUseHeavyArmor())
            return false; //item was not used
        playerCharacter useOn = ((playerCharacter) toUseOn);
        useOn.setArmor(this);
        addStats(useOn); //finally, we add this weapon's stats to the character
        return true;
    }

    @Override //unequip the item from the passed character
    public void Unequip(playerCharacter toEquipTo) {
        toEquipTo.setArmor(null); //set armor to null
        subtractStats(toEquipTo); //subtract stats
        Game.Player.Insert(this); //insert the item into global inventory
    }

    @Override
    public StackPane buildSpecificItemDisplay() {
        StackPane toReturn = new StackPane();
        toReturn.setAlignment(Pos.CENTER);
        //potential text for stats granted by the weapon
        if(getStatText() == null){
            toReturn.setMaxSize(300, 90);
            toReturn.setMinSize(300, 90);
        }
        else{
            toReturn.setMaxSize(300, 120);
            toReturn.setMinSize(300, 120);
        }
        //text for weapon type
        Text typeText = new Text();
        if(isHeavy)
            typeText.setText("Heavy Armor");
        else
            typeText.setText("Light Armor");
        typeText.setTextAlignment(TextAlignment.CENTER);
        typeText.setWrappingWidth(260);
        typeText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        typeText.setTranslateY(toReturn.getHeight()/-2);
        toReturn.getChildren().add(typeText);
        //text for the armor of the item
        Text armorText = new Text("Armor: " + Armor);
        armorText.setTextAlignment(TextAlignment.CENTER);
        armorText.setWrappingWidth(260);
        armorText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        armorText.setTranslateY(typeText.getTranslateY()+35);
        toReturn.getChildren().add(armorText);
        //potential stat text
        if(getStatText() != null) {
            Text statText = new Text(getStatText());
            statText.setTextAlignment(TextAlignment.CENTER);
            statText.setTranslateY(armorText.getTranslateY()+35);
            statText.setWrappingWidth(260);
            statText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
            toReturn.getChildren().add(statText);
        }

        return toReturn;
    }
}
