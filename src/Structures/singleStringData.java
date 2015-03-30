package Structures;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/5/15.
 * Single string data class. Just includes an individual string
 * and features functions that easily interface it with the rest of
 * the program.
 */
public class singleStringData implements Data {
    private String Name;

    //default constructor
    public singleStringData() {
    }

    //constructor with individual name parameter
    public singleStringData(String name) {
        Name = name;
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

