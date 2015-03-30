package Characters.Inventory;

import Characters.combatEffect;
import Structures.Data;
import Structures.incrementableData;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public abstract class Item extends combatEffect implements incrementableData {
    private String itemName;
    private String Description;
    private int Quantity;

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
        System.out.println(itemName);
        System.out.println(Description);
        System.out.println("Quantity: " + Quantity);
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
