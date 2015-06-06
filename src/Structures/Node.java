package Structures;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/2/15.
 * Node base class. Handles potential data and substructure components to
 * any node that Implement. Frankly, a little heavy on setters and getters on
 * account of the fact that data and structure are interfaces/abstract base classes
 * and I cannot have an "is-a" relationship with either of them. Would arguably
 * be better implemented with public data members, but this approach would not
 * be object oriented.
 */
public class Node {
    protected Data Data; //reference to a piece of data;
    //data is an interface.
    protected Structure subStructure; //reference to the node's possible substructure;
    //will remain null if this is the last in a hierarchy
    //of structures across the program.

    //default constructor
    public Node(){
    }

    //constructor with data and structure parameters to add
    public Node(String dInsert, Structure sInsert){
        Data = new singleStringData(dInsert);
        subStructure = sInsert;
    }

    //constructor with two string parameters
    public Node(String first, String second){
        Data = new dualStringData(first, second);
    }

    //constructor with one string parameter
    public Node(String toInsert){
        Data = new singleStringData(toInsert);
    }

    //constructor with pre-allocated data parameter
    public Node(Data toInsert){
        Data = toInsert;
    }

    //constructor with substructure argument and pre-allocated data parameter
    public Node(Data dInsert, Structure sInsert){
        Data = dInsert;
        subStructure = sInsert;
    }

    //constructor with 2 string arguments and pre-allocated data parameter
    public Node(String firstString, String secondString, Structure toInsert){
        Data = new dualStringData(firstString, secondString);
        subStructure = toInsert;
    }

    //incrementation wrapper function for incrementable data
    public int Increment(int toIncrement){
        if(Data instanceof incrementableData){
            return ((incrementableData)Data).Increment(toIncrement);
        }
        return 1;
    }

    //decrementation wrapper function for incrementable data
    public int Decrement(int toDecrement){
        if(Data instanceof incrementableData){
            return ((incrementableData)Data).Increment(toDecrement);
        }
        return 0;
    }

    //write out to file method; writes the node's data to a printwriter
    //and then does the same thing with its substructure if that exists.
    //tier specifies the node's position in a hierarchy of data structures
    public void writeOut(PrintWriter toWrite, int tier){
        if(Data != null) {
            int modtier = tier % 10;
            //lets us use proper suffixes regardless
            //of how high the tier is. I.E. "4322nd"
            if(modtier == 1)
                toWrite.println(tier + "st tier");
            else if(modtier == 2)
                toWrite.println(tier + "nd tier");
            else if(modtier == 3)
                toWrite.println(tier + "rd tier");
            else
                toWrite.println(tier + "th tier");
            //on one line, we specify the tier of the node.

            Data.writeOut(toWrite); //write out our data if it exists
        }
        if(subStructure != null)
            subStructure.writeOut(toWrite, (tier + 1));
        //if the substructure exists, we write that out as well,
        //but increment the tier by one to differentiate between
        //the two different structures.
    }

    //displays the node's data.
    public void Display(){
        if(Data != null)
            Data.Display();
    }

    //displays the node's data along with indentation, the length
    //of which is dictated by the passed integer.
    public void Display(int indent){
        if(Data != null)
            Data.Display(indent);
    }

    //returns true if this node's data is smaller than
    //the passed string.
    public Boolean goesToRight(String toCompare) {
        if(Data.compareTo(toCompare) >= 0)
            return true;
        return false;
    }

    //returns true if this node's data is bigger than
    //that of the passed node.
    public Boolean goesToRight(Node toCompare){
        if(toCompare == null)
            return false; //null is obviously smaller
        if(Data.compareTo(toCompare.Data) >= 0)
            return true;
        return false;
    }

    //equality comparison with string argument
    public Boolean isEqual(String toCompare){
        if(Data.compareTo(toCompare) == 0)
            return true;
        return false;
    }

    //equality comparison with node argument.
    public Boolean isEqual(Node toCompare){
        if(this.Data.compareTo(toCompare.Data) == 0)
            return true;
        return false;
    }

    public void setData(Data toSet){
        //set data
        Data = toSet;
    }

    //returns a reference to the node's data;
    //used so that I can grab a node's (derived class)
    //data even from a node interface pointer.
    public Data returnData(){
        return Data;
    }

    //returns a reference to the node's structure;
    //if the node has no structure, returns null.
    //used for multi-dimensional method calls
    //across different structures
    public Structure returnStructure(){
        return subStructure;
    }
}
