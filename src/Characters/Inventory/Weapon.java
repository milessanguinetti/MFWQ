package Characters.Inventory;

import Characters.combatEffect;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class Weapon extends Item implements equipableItem, combatEffect {
    protected int Damage; //the weapon's damage value (a shield's equals its armor bonus)
    private String weaponType; //the class of the weapon in question
    protected String Property; //the weapon's property

    //default constructor
    public Weapon(){}

    //constructor that effectively handles all data members
    public Weapon(String Name, String Description, int damage, String weapontype, String property){
        super(Name, Description);
        Damage = damage;
        weaponType = weapontype;
        Property = property;
    }

    //constructor that handles all data except name and property. Principally used to
    //provide an efficient way to construct randomly generated weapons with names and
    //properties that are not known when I make a "super" call in the constructor
    public Weapon(String Description, int damage, String weapontype){
        super(Description);
        Damage = damage;
        weaponType = weapontype;
    }

    //returns whether or not this weapon goes in the right hand.
    public abstract boolean isRightHand();

    //returns whether or not this weapon is two-handed.
    public abstract boolean isTwoHand();

    @Override
    public String getDescription(){
        return Description;
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override //weapons are literally always offensive
    public boolean isOffensive() {
        return true;
    }

    public boolean isOfProperty(String toCheck){
        if(Property.compareTo(toCheck) == 0)
            return true;
        return false;
    }

    public boolean isOfType(String toCheck){
        if(weaponType.compareTo(toCheck) == 0)
            return true;
        return false;
    }

    public int getDamage(){
        return Damage;
    }

    public String getProperty(){
        return Property;
    }

    @Override
    public void printName() {
        System.out.print("their " + itemName);
    }

    @Override //equip the item to the passed character
    public boolean Use(gameCharacter toUseOn){
        if(!canUse(toUseOn))
            return false; //item was not used
        playerCharacter useOn = ((playerCharacter) toUseOn);
        Weapon rightTemp = useOn.getRight();
        Weapon leftTemp = useOn.getLeft();
        if(isTwoHand()){ //unequip both weapons
            if(rightTemp != null) {
                rightTemp.Unequip(useOn);
                if (!rightTemp.isTwoHand()  && leftTemp != null) //separate case to ensure we don't add two of the left
                    leftTemp.Unequip(useOn);                     //handed weapon to our inventory
            }
            else if(leftTemp != null){
                leftTemp.Unequip(useOn);
            }
            useOn.setRight(this);
            useOn.setLeft(this);
        }
        else if(isRightHand()){ //unequip just the right hand weapon
            if(rightTemp != null)
                rightTemp.Unequip(useOn);
            useOn.setRight(this);
        }
        else{ //unequip just the left hand weapon
            if(leftTemp != null)
                leftTemp.Unequip(useOn);
            useOn.setLeft(this);
        }
        addStats(useOn); //finally, we add this weapon's stats to the character
        return true;
    }

    @Override //unequip the item from the passed character
    public void Unequip(playerCharacter toEquipTo) {
        if(isTwoHand()){
            toEquipTo.setRight(null);
            toEquipTo.setLeft(null);
        }
        else if(isRightHand()){
            toEquipTo.setRight(null);
        }
        else{
            toEquipTo.setLeft(null);
        }
        subtractStats(toEquipTo); //subtract stats
        Game.Player.Insert(this); //insert the item into global inventory
    }

    @Override
    public StackPane buildSpecificItemDisplay() {
        StackPane toReturn = new StackPane();
        toReturn.setAlignment(Pos.CENTER);
        //decide size for the specific display
        if(getStatText() == null){
            toReturn.setMaxSize(300, 120);
            toReturn.setMinSize(300, 120);
        }
        else{
            toReturn.setMaxSize(300, 150);
            toReturn.setMinSize(300, 150);
        }
        //text for weapon type
        Text typeText = new Text(weaponType);
        typeText.setTextAlignment(TextAlignment.CENTER);
        typeText.setWrappingWidth(260);
        typeText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        typeText.setTranslateY(toReturn.getHeight()/-2);
        toReturn.getChildren().add(typeText);
        //text for damage dealt by the weapon
        Text damageText = new Text("Damage: " + Damage);
        damageText.setTextAlignment(TextAlignment.CENTER);
        damageText.setWrappingWidth(260);
        damageText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        damageText.setTranslateY(typeText.getTranslateY()+35);
        toReturn.getChildren().add(damageText);
        //Text for hand the weapon goes in
        Text handText = new Text();
        if(isTwoHand())
            handText.setText("Two-handed");
        else if(isRightHand())
            handText.setText("Main Hand");
        else
            handText.setText("Off Hand");
        handText.setTextAlignment(TextAlignment.CENTER);
        handText.setWrappingWidth(260);
        handText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        handText.setTranslateY(damageText.getTranslateY()+35);
        toReturn.getChildren().add(handText);
        //Text for property
        Text propText = new Text(Property + " Property");
        switch (Property){
            case "Fire":
                propText.setFill(Color.RED);
                break;
            case "Ghost":
                propText.setFill(Color.ALICEBLUE);
                break;
            case "Holy":
                propText.setFill(Color.GOLDENROD);
                break;
            case "Organic":
                propText.setFill(Color.FORESTGREEN);
                break;
            case "Undead":
                propText.setFill(Color.GREENYELLOW);
                break;
            case "Unholy":
                propText.setFill(Color.PURPLE);
                break;
            case "Water":
                propText.setFill(Color.BLUE);
                break;
        }
        propText.setTextAlignment(TextAlignment.CENTER);
        propText.setWrappingWidth(260);
        propText.setTranslateY(handText.getTranslateY()+35);
        propText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        toReturn.getChildren().add(propText);
        //potential stat text
        if(getStatText() != null) {
            Text statText = new Text(getStatText());
            statText.setTextAlignment(TextAlignment.CENTER);
            statText.setTranslateY(propText.getTranslateY()+35);
            statText.setWrappingWidth(260);
            statText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
            toReturn.getChildren().add(statText);
        }

        return toReturn;
    }
}
