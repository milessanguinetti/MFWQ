package Structures;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/2/15.
 */
//contains different types of data and houses functions common to all of them.
public interface Data {

    public void writeOut(PrintWriter toWrite);
    //writes the data out to a passed printwriter.

    public String returnKey();

    public void Display();
    //common display function for the entire data set

    public void Display(int indent);
    //display function with an integer to indent with

    public void setKey(String name);
    //sets the data's key to the passed string

    public int compareTo(String toCompare);
    //Basically a dumbed down string compare

    public int compareTo(Data toCompare);
    //same as above except that this takes a data reference as a parameter
}
