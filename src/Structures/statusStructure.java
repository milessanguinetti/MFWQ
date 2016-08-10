package Structures;

import Characters.statusEffects.damageEffect;
import Characters.statusEffects.endOfTurn;
import Characters.statusEffects.statChange;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 3/18/15.
 */
public class statusStructure extends orderedLLL {
    //removal function that takes an int value; basically used to clear
    //incrementable data rather than just decrement it by 1

    public int Remove(String toRemove, int Value){
        LLLnode previous = null;
        LLLnode current = head; //current reference for traverse
        while(current != null){
            if(current.isEqual(toRemove)) {
                if (current.Decrement(Value) <= 0) { //if we decrement to 0 or the data is non-decrementable
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

    public int calculateDamage(int toCalculate, String Property){
        LLLnode current = head;
        while(current != null){
            toCalculate = ((damageEffect)current.returnData()).calculateDamage(toCalculate, Property);
            current = current.getNext();
        }
        return toCalculate;
    }

    public void endTurn(gameCharacter toAffect){
        LLLnode current = head;
        while(current != null){
            ((endOfTurn)current.returnData()).endOfTurnEffects(toAffect);
            current = current.getNext();
        }
    }

    public void changeStats(gameCharacter toAffect){
        LLLnode current = head;
        while(current != null){
            ((statChange)current.returnData()).changeStats(toAffect);
            current = current.getNext();
        }
    }

    @Override //insertion method with node parameter; does not insert repeats
    public int Insert(Node nodeToInsert) {
        if(nodeToInsert == null || !(nodeToInsert instanceof LLLnode))
            return 0; //can't insert a null reference or a node object that isn't of type BSTnode
        LLLnode toInsert = ((LLLnode)nodeToInsert);
        if(!toInsert.goesToRight(head)) { //if head is empty or toInsert is larger...
            if(head != null){ //prevents stacking of extant debuffs at head.
                if(head.isEqual(toInsert) && head.returnData() instanceof incrementableData){
                    ((LLLnode) nodeToInsert).setNext(head.getNext());
                    head = (LLLnode) nodeToInsert;
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
                    Remove(current.returnData().returnKey()); //overwrite the identical node with the new one.
                    Insert(nodeToInsert);
                    return 1;
                }
                if (!toInsert.goesToRight(current.getNext())) {
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

}
