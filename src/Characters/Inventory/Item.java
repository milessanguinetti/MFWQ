package Characters.Inventory;

import Characters.gameCharacter;
import Structures.Data;
import Structures.incrementableData;
import javafx.scene.image.ImageView;

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
