package Structures;

import Characters.combatEffect;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/17/15.
 */
public class orderedLLL extends Structure{
    protected LLLnode head; //head reference

    //retrieves a pointer to a node based on integer input
    //basically an expandable array, though traversal makes this
    //less than ideal in larger data structures.
    public LLLnode retrieveInt(int toRetrieve){
        LLLnode current = head;
        for(int i = 1; i < toRetrieve; ++i){
            if(current == null)
                return null; //no segfaults on my watch
            current = current.getNext(); //traverse otherwise
        }
        return current; //return current if we can get there.
    }

    //look at the first item (not a real stack, but nice functionality to have
    public LLLnode Peek(){
        return head;
    }

    //remove the first item from the data structure (useful for emulating stack removal)
    public Data Pop(){
        if(head != null){ //if there's something to pop
            Data temp = head.returnData();
            head = head.getNext(); //go to next item
            return temp; //return the popped item's data
        }
        return null;
    }

    //remove all by setting head to null
    public void removeAll(){
        head = null;
    }

    //decrements all data, removing if it reaches 0
    public void decrementAll(){
        head = decrementAllRecursively(head); //call recursive function
    }

    public LLLnode decrementAllRecursively(LLLnode current){
        if(current == null)
            return null;
        if(current.Decrement(1) <= 0) //if this data item has a value of 0 now
            return decrementAllRecursively(current.getNext()); //cut out of the structure
        current.setNext(decrementAllRecursively(current.getNext())); //recursive call
        return current; //maintain current structure
    }

    @Override //display all data contained in the structure
    public void Display(){
        LLLnode current = head;
        while(current != null){
            current.Display();
            current = current.getNext(); //traverse
        }
        //call recursive display function with root as parameter
    }

    //similar to display, but numbers each item.
    public void displayNumbered(){
        int number = 1;
        LLLnode current = head;
        while(current != null){
            System.out.print(number + " ");
            current.Display();
            current = current.getNext(); //traverse
            ++number;
        }
        //call recursive display function with root as parameter
    }

    @Override //displays both the current structure and its substructures.
    public void multidimensionalDisplay(int indent){
        LLLnode current = head;
        while(current != null){
            current.Display(indent);
            if(current.returnStructure() != null) //if current has a substructure...
                current.returnStructure().multidimensionalDisplay(indent); //display it as well
            current = current.getNext(); //traverse
        }
    }

    @Override //writes out to file via towrite parameter
    public void writeOut(PrintWriter toWrite, int tier){
        LLLnode current = head;
        while(current != null){
            current.writeOut(toWrite, tier); //write out
            current = current.getNext(); //traverse
        }
        //writes out the structure's data
    }

    @Override //removal method based on string input; 1 signifies success
    //whereas 0 signifies failure.
    public int Remove(String toRemove) {
        LLLnode previous = null;
        LLLnode current = head; //current reference for traverse
        while(current != null){
            if(current.isEqual(toRemove)) {
                if (current.Decrement(1) <= 0) { //if we decrement to 0 or the data is non-decrementable
                    if (current == head) //if current is head
                        head = null; //remove head
                    else {
                        previous.setNext(current.getNext()); //previous points past current now
                    }
                    return 1; //if this is indeed the node we're looking for, remove & return 1.
                }
            }
            previous = current;
            current = current.getNext(); //otherwise traverse
        }
        return 0; //not found
    }

    @Override //retrieval method based on string input. null return value signifies that
    //a node corresponding to the string was not found.
    public Node Retrieve(String toRetrieve){
        LLLnode current = head; //current reference for traverse
        while(current != null){
            if(current.isEqual(toRetrieve))
                return current; //if this is indeed the node we're looking for, return it.
            current = current.getNext(); //otherwise traverse
        }
        return null;
    }

    @Override //insertion method with node parameter; does not insert repeats
    public int Insert(Node nodeToInsert) {
        if(nodeToInsert == null || !(nodeToInsert instanceof LLLnode))
            return 0; //can't insert a null reference or a node object that isn't of type BSTnode
        LLLnode toInsert = ((LLLnode)nodeToInsert);
        if(!toInsert.goesToRight(head)) { //if head is empty or toInsert is larger...
            if(head != null){ //prevents stacking of extant debuffs at head.
                if(head.isEqual(toInsert)){
                    head.Increment(1); //increment by one if it's a repeat and return
                    return 1;
                }
            }
            toInsert.setNext(head);
            head = toInsert; //we insert at head.
        }
        else { //otherwise...
            LLLnode current = head; //current ref for traversal
            while(current != null) {
                if(current.isEqual(toInsert)){
                    current.Increment(1); //increment by one if it's a repeat and return
                    return 1;
                }
                if (toInsert.goesToRight(current)) {
                    toInsert.setNext(current.getNext()); //toinsert's next ref gets current's next
                    current.setNext(toInsert); //current's next ref gets toinsert
                    return 1; //success
                }
                current = current.getNext(); //otherwise traverse
            }
        }
        return 1;
        //return 1 because the method cannot be expected to fail at this point
    }

    public combatEffect [] toArray(){
        int Size = getSize();
        if(Size == 0)
            return null;
        combatEffect [] toReturn = new combatEffect[Size];
        LLLnode Current = head;
        for(int i = 0; i < Size; ++i){
            toReturn[i] = ((combatEffect)Current.Data);
            Current = Current.getNext();
        }
        return toReturn;
    }

    public int getSize(){
        LLLnode Current = head;
        if(head == null)
            return 0;
        int count = 0;
        while(Current != null){
            ++count;
            Current = Current.getNext();
        }
        return count;
    }
}
