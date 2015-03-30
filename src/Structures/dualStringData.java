package Structures;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/2/15.
 * Dual string data type. used, in this case, for
 * DLLnodes which house both a language name and a piece
 * of syntax. Due to the additional data member, some of this
 * data type's functions differ slightly.
 */
public class dualStringData implements Data{
    private String Name;
    private String Description;

    //default constructor
    public dualStringData() {
    }

    //constructor with single string parameter
    public dualStringData(String name) {
        Name = name;
    }

    //constructor with not one, but two string parameters
    public dualStringData(String name, String description) {
        Name = name;
        Description = description;
    }

    //writes the data out to the passed printwriter to more easily facilitate
    //the process of writing data out to an external file.
    public void writeOut(PrintWriter toWrite){
        toWrite.println(Name);
        toWrite.println(Description);
    } //write all of this data's information out to the printwriter.

    //return key for comparison.
    public String returnKey(){
        return Name;
    }

    //display function
    public void Display(){
        System.out.println(Name + "\n" + Description);
    }

    //display function with int parameter for indentation.
    public void Display(int indent){
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(Name);
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(Description);
    }

    //key setter
    public void setKey(String name) {
        Name = name;
    }

    //description setter
    public void setDescription(String description) {
        Description = description;
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
