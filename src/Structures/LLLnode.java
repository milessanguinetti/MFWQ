package Structures;

/**
 * Created by Miles Sanguinetti on 3/17/15.
 */
public class LLLnode extends Node{
    private LLLnode Next;

    //default constructor
    public LLLnode() {
    }

    //constructor with name
    public LLLnode(String name) {
        super(name);
    }

    //constructor with name and substructure
    public LLLnode(String name, Structure toInsert){ //name for the data and a pre-allocated structure
        super(name, toInsert);                       //reference to add in.
    }

    //constructor with pre-allocated data
    public LLLnode(Data toInsert){
        super(toInsert);
    }

    //returns the node's next reference
    public LLLnode getNext(){
        return Next;
    }

    //sets the node's next reference to the parameter
    public void setNext(LLLnode toSet) {
        Next = toSet;
    }
}
