package Structures;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/18/15.
 */
public class statusEffectData implements Data, incrementableData{
    private String Name; //name of status effect
    private int Turns; //turns remaining

    //default constructor
    public statusEffectData() {
    }

    //constructor with individual name parameter
    public statusEffectData(String name) {
        Name = name;
    }

    //constructor with duration
    public statusEffectData(String toName, int Duration){
        Name = toName;
        Turns = Duration;
    }

    //increments the turn value
    @Override
    public int Increment(int toIncrement) {
        Turns += toIncrement;
        return Turns;
    }


    //decrements the turn value
    @Override
    public int Decrement(int toDecrement) {
        Turns -= toDecrement;
        return Turns;
    }

    //writes the data out to file to facilitate saving functionality
    public void writeOut(PrintWriter toWrite){
        toWrite.println(Name); //write out this piece of data's name
    }

    //return key
    public String returnKey(){
        return Name;
    }

    //display method for name
    public void Display(){
        System.out.println(Name);
    }

    //display method with an integer parameter for indentation
    public void Display(int indent){
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(Name);
    }

    //key setter
    public void setKey(String name) {
        Name = name;
    }

    //Basically a dumbed down string compare
    public int compareTo(String toCompare){
        return Name.compareTo(toCompare);
    }

    //same, except that this takes a data reference as a parameter
    public int compareTo(Data toCompare){
        return Name.compareTo(toCompare.returnKey());
    }
}
