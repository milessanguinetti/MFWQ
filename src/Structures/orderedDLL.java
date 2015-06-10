package Structures;

import java.io.PrintWriter;

/**
 * Created by Miles on 6/2/2015.
 */
//kind of an odd structure in terms of how data structures are usually used, but
//this essentially just provides a resizable storage and printing apparatus for small
//sets of data like character skills and item that stores data in an orderly fashion and
//is traversable both forwards and backwards.
public class orderedDLL extends Structure{
    protected orderedDLLNode head; //head reference
    private int size = 0;

    //retrieves the head of the DLL; used for printing things outside of the structure.
    public orderedDLLNode getHead(){
        return head; //return head.
    }

    //retrieves the size of the DLL; used for printing things outside of the structure.
    public int getSize(){
        return size;
    }

    @Override //display all data contained in the structure
    public void Display(){
        orderedDLLNode current = head;
        while(current != null){
            current.Display();
            current = current.getNext(); //traverse
        }
        //call recursive display function with root as parameter
    }

    @Override //displays both the current structure and its substructures.
    public void multidimensionalDisplay(int indent){
        orderedDLLNode current = head;
        while(current != null){
            current.Display(indent);
            if(current.returnStructure() != null) //if current has a substructure...
                current.returnStructure().multidimensionalDisplay(indent); //display it as well
            current = current.getNext(); //traverse
        }
    }

    @Override //writes out to file via towrite parameter
    public void writeOut(PrintWriter toWrite, int tier){
        orderedDLLNode current = head;
        while(current != null){
            current.writeOut(toWrite, tier); //write out
            current = current.getNext(); //traverse
        }
        //writes out the structure's data
    }

    @Override //removal method based on string input; 1 signifies success
    //whereas 0 signifies failure.
    public int Remove(String toRemove) {
        orderedDLLNode current = head; //current reference for traverse
        while(current != null){
            if(current.isEqual(toRemove)) {
                if (current.Decrement(1) <= 0) { //if we decrement to 0 or the data is non-decrementable
                    --size; //decrement size.
                    if (current == head) { //if current is head
                        head = head.getNext();
                        if(head != null)
                            head.setPrev(null);
                    }
                    else {
                        if(head.getNext() != null)
                            head.getNext().setPrev(head.getPrev());
                        head.getPrev().setNext(head.getNext());
                    }
                }
                return 1; //if this is indeed the node we're looking for, remove & return 1.
            }
            current = current.getNext(); //otherwise traverse
        }
        return 0; //not found
    }

    @Override //retrieval method based on string input. null return value signifies that
    //a node corresponding to the string was not found.
    public Node Retrieve(String toRetrieve){
        orderedDLLNode current = head; //current reference for traverse
        while(current != null){
            if(current.isEqual(toRetrieve))
                return current; //if this is indeed the node we're looking for, return it.
            current = current.getNext(); //otherwise traverse
        }
        return null;
    }

    @Override //insertion method with node parameter; does not insert repeats
    public int Insert(Node nodeToInsert) {
        if(nodeToInsert == null || !(nodeToInsert instanceof orderedDLLNode))
            return 0; //can't insert a null reference or a node object that isn't of type BSTnode
        orderedDLLNode toInsert = ((orderedDLLNode)nodeToInsert);
        if(!toInsert.goesToRight(head)) { //if head is empty or toInsert is larger...
            if(head != null){ //prevents stacking of extant debuffs at head.
                if(head.isEqual(toInsert) && head.returnData() instanceof incrementableData){
                    head.Increment(1); //increment by one if it's a repeat and return
                    return 1;
                }
                else{
                    head.setPrev(toInsert);
                }
            }
            toInsert.setNext(head);
            head = toInsert; //we insert at head.
            ++size; //increment the size of the structure.
        }
        else { //otherwise...
            orderedDLLNode current = head; //current ref for traversal
            while(current != null) {
                if(current.isEqual(toInsert) && current.returnData() instanceof incrementableData){
                    current.Increment(1); //increment by one if it's a repeat and return
                    return 1;
                }
                if (!toInsert.goesToRight(current.getNext())) {
                    if(current.getNext() != null) {
                        current.getNext().setPrev(toInsert);
                    }
                    toInsert.setNext(current.getNext()); //toinsert's next ref gets current's next
                    current.setNext(toInsert); //current's next ref gets toinsert
                    toInsert.setPrev(current);
                    ++size;
                    return 1; //success
                }
                current = current.getNext(); //otherwise traverse
            }
        }
        return 1;
        //return 1 because the method cannot be expected to fail at this point
    }
}
