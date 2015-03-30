package Structures;

import Characters.Status.damageEffect;
import Characters.Status.endOfTurn;
import Characters.Status.statChange;
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
}
