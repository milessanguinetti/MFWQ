package Characters.Status;

import Characters.gameCharacter;
import Structures.LLLnode;
import Structures.statusEffectData;
import Structures.statusStructure;

/**
 * Created by Miles Sanguinetti on 3/18/15.
 */
//class for managing status effects. A little heavy on the front of wrappers.
//Predominantly written to compartmentalize duties of gameCharacter class.
public class Status {
    statusStructure statChangeList; //LLL structure for effects that change stats
    statusStructure damageEffectList; //effects that impact damage taken
    statusStructure endOfTurnList; //effects that take place at the end of a turn

    //removes all status effects; returns true if we need to readjust temp stats
    public boolean clearAll(){
        boolean statAdjustment = (statChangeList.Peek() != null);
        //checks if we're going to need to adjust stats
        statChangeList.removeAll();
        damageEffectList.removeAll();
        endOfTurnList.removeAll();
        return statAdjustment;
    }

    //removes a specific status effect; returns true if we need to readjust temp stats
    public boolean Remove(String toRemove){
        damageEffectList.Remove(toRemove, 10000);
        endOfTurnList.Remove(toRemove, 10000);
        if(statChangeList.Remove(toRemove, 10000) == 1)
            return true; //return true if we removed any stat changes
        return false; //otherwise, return false
    }

    //inserts a status effect; returns true if we need to readjust temp stats
    public boolean Insert(statusEffectData toInsert){
        LLLnode nodeToInsert; //LLLnode reference
        if(toInsert instanceof damageEffect){ //insert to damage effects if this condition
            nodeToInsert = new LLLnode(toInsert); //has damage effect properties
            damageEffectList.Insert(nodeToInsert);
        }

        if(toInsert instanceof endOfTurn){
            nodeToInsert = new LLLnode(toInsert); //insert to end of turn effects if this
            endOfTurnList.Insert(nodeToInsert);   //condition has end of turn properties
        }

        if(toInsert instanceof statChange){ //insert to statchange effects if this condition
            nodeToInsert = new LLLnode(toInsert); //has stat change properties
            statChangeList.Insert(nodeToInsert);
            return true; //return true to readjust temp stats
        }
        return false;
    }

    //wrapper for damage calculation in endofturnlist
    public int calculateDamage(int toCalculate, String Property){
        toCalculate = damageEffectList.calculateDamage(toCalculate, Property);
        return toCalculate;
    }

    public void endTurn(gameCharacter toAffect){
        endOfTurnList.endTurn(toAffect);
    }

    public void changeStats(gameCharacter toAffect){
        statChangeList.changeStats(toAffect);
    }
}
