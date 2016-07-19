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

    public boolean Use(){
        //INTERFACING METHOD FOR CHARACTERS
        return true;
    }

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
        Quantity -= toDecrement;
        if(Quantity < 0)
            Quantity = 0;
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
}
