package Structures;

/**
 * Created by Miles on 6/2/2015.
 */
public class orderedDLLNode extends Node{
    private orderedDLLNode Next = null;
    private orderedDLLNode Prev = null;

    //default constructor
    public orderedDLLNode() {
    }

    //constructor with name
    public orderedDLLNode(String name) {
        super(name);
    }

    //constructor with name and substructure
    public orderedDLLNode(String name, Structure toInsert){ //name for the data and a pre-allocated structure
        super(name, toInsert);                              //reference to add in.
    }

    //constructor with pre-allocated data
    public orderedDLLNode(Data toInsert){
        super(toInsert);
    }

    //returns the node's next reference
    public orderedDLLNode getNext(){
        return Next;
    }

    //sets the node's next reference to the parameter
    public void setNext(orderedDLLNode toSet) {
        Next = toSet;
    }

    //returns the node's next reference
    public orderedDLLNode getPrev(){
        return Prev;
    }

    //sets the node's next reference to the parameter
    public void setPrev(orderedDLLNode toSet) {
        Prev = toSet;
    }
}
