package Characters.Inventory;

import Characters.gameCharacter;
import Structures.Data;
import Structures.incrementableData;
import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public abstract class Item implements incrementableData {
    protected String itemName;
    private String Description;
    private int Quantity;

    //default constructor
    Item(){}

    //constructor with name and description
    Item(String name, String description){
        itemName = name;
        Description = description;
        Quantity = 1;
    }

    public void Use(){
        //INTERFACING METHOD FOR CHARACTERS
    }

    public abstract boolean Use(gameCharacter toUseOn);

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
