package Characters.Inventory;

import Characters.gameCharacter;
import Structures.Data;
import Structures.incrementableData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import sun.security.krb5.internal.crypto.Des;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public abstract class Item implements incrementableData, Serializable {
    protected String itemName;
    protected String Description;
    protected int Value = 0;
    protected boolean canBeSold = true;
    private int Quantity;

    //default constructor
    Item(){}

    //constructor with just a description.
    Item(String description){
        Description = description;
        Quantity = 1;
    }

    //constructor with name and description
    Item(String name, String description){
        itemName = name;
        Description = description;
        Quantity = 1;
    }

    //returns an imageview corresponding to the item in question
    public abstract ImageView getIcon();

    public abstract boolean Use(gameCharacter toUseOn);

    public String getDescription(){
        return Description;
    }

    @Override
    public int Increment(int toIncrement) {
        Quantity += toIncrement;
        return Quantity;
    }

    @Override
    public int Decrement(int toDecrement) {
        if(Quantity+toDecrement <= 0) //lowering this to 0 will bug out equipment in this case; other items
            return 0;                 //are discarded, so not decrementing quantity is irrelevent for them.
        Quantity += toDecrement;
        return Quantity;
    }

    @Override
    public void writeOut(PrintWriter toWrite) {
        toWrite.println(itemName);
        toWrite.println(Quantity);
    }

    @Override
    public String returnKey() {
        return itemName;
    }

    public int getQuantity(){return Quantity;}

    @Override
    public void Display() {
        System.out.println(itemName + " (" + Quantity + " in stock)");
        System.out.println(Description);
    }

    @Override
    public void Display(int indent) {
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(itemName);
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(Description);
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println("Quantity: " + Quantity);
    }

    public StackPane getItemDisplay(){
        StackPane detailed = buildSpecificItemDisplay();
        double detailedHeight = 0;
        if(detailed != null)
            detailedHeight = detailed.getMaxHeight() + 20;

        StackPane toReturn = new StackPane();
        toReturn.setTranslateX(450);
        toReturn.setTranslateY(50);
        Rectangle displayBackground = new Rectangle(300, 400 + detailedHeight);
        displayBackground.setFill(Color.LIGHTGRAY);
        toReturn.getChildren().add(displayBackground);
        Text nametext = new Text(itemName);
        nametext.setTextAlignment(TextAlignment.CENTER);
        nametext.setTranslateY(displayBackground.getHeight()/-2 + 40);
        nametext.setWrappingWidth(260);
        nametext.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.BOLD, 25));
        toReturn.getChildren().add(nametext);
        Text quanttext = new Text("Quantity: " + Quantity);
        quanttext.setTextAlignment(TextAlignment.CENTER);
        quanttext.setTranslateX(-66);
        quanttext.setTranslateY(nametext.getTranslateY() + 50);
        quanttext.setWrappingWidth(130);
        quanttext.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        toReturn.getChildren().add(quanttext);
        Text valuetext = new Text("Value: " + Value);
        valuetext.setTextAlignment(TextAlignment.CENTER);
        valuetext.setTranslateX(66);
        valuetext.setTranslateY(nametext.getTranslateY() + 50);
        valuetext.setWrappingWidth(130);
        valuetext.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        toReturn.getChildren().add(valuetext);
        ImageView icon = getIcon();
        icon.setTranslateY(valuetext.getTranslateY() + 105);
        toReturn.getChildren().add(icon);
        Text destext = new Text(Description);
        destext.setTranslateY(displayBackground.getHeight()/2 -80);
        destext.setWrappingWidth(260);
        destext.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
        toReturn.getChildren().add(destext);

        if(detailed != null){
            detailed.setTranslateY(icon.getTranslateY() + icon.getFitHeight()/2 + detailedHeight/2);
        }

        return toReturn;
    }

    public abstract StackPane buildSpecificItemDisplay();

    @Override
    public void setKey(String name) {
        itemName = name;
    }

    @Override
    public int compareTo(String toCompare) {
        return itemName.toLowerCase().compareTo(toCompare.toLowerCase());
    }

    @Override
    public int compareTo(Data toCompare) {
        return itemName.toLowerCase().compareTo(toCompare.returnKey().toLowerCase());
    }

    public boolean CanBeSold(){
        return canBeSold;
    }

    public int getValue(){
        return Value;
    }

}
