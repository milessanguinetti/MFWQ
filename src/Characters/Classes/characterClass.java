package Characters.Classes;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Structures.Data;
import Structures.LLLnode;
import Structures.orderedLLL;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/21/15.
 */
public abstract class characterClass implements Data {
    String className; //the name of the class
    int jlevel = 1;
    int jexp = 0; //job experience and job experience need to reach next level.
    int jexpCap = 1000;
    orderedLLL Skills; //skills for this class

    //add job experience to the character's class.
    public void gainJexp(int Gains, playerCharacter toLevel){
        if((jexp + Gains) >= jexpCap){ //if we've received enough jexp to level...
            Gains -= (jexpCap - jexp);
            ++jlevel;
            jexpCap += Math.round(jexpCap*1.2); //jexp cap goes up by 20%
            jexp = 0; //reset jexp to 0 now that we've leveled.
            jobDing(toLevel);
            gainJexp(Gains, toLevel); //recursive call to add the rest of the jexp
                                      //onto the next level.
        }
        else
            jexp += Gains; //otherwise, we just add it to our exp.
    }

    //adds stat bonuses to a character according to the class in question
    public abstract void baseDing(playerCharacter toLevel, int level);

    //adds skills according to the class in question
    //passive skills are returned, others are added directly.
    public abstract Skill jobDing(playerCharacter toLevel);

    public Skill getSkill(int toGet){
        LLLnode Retrieved = Skills.retrieveInt(toGet);
        if(Retrieved == null)
            return null;
        return ((Skill)Retrieved.returnData());
    }

    public void displaySkills(){
        Skills.displayNumbered();
    }

    public abstract boolean canUseHeavyArmor();

    public void writeOut(PrintWriter toWrite) {
        //writes the data out to a passed printwriter.
        toWrite.println(className);
        toWrite.println(jlevel);
    }

    public String returnKey(){
        return className;
    }

    //common display function for the entire data set

    public void Display() {
        System.out.println(className);
    }

    //display function with an integer to indent with
    public void Display(int indent) {
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(className);
    }

    //sets the data's key to the passed string
    public void setKey(String name) {
        className = name;
    }

    //Basically a dumbed down string compare
    public int compareTo(String toCompare) {
        return className.compareTo(toCompare);
    }

    //same as above except that this takes a data reference as a parameter
    public int compareTo(Data toCompare) {
        return toCompare.compareTo(className);
    }
}
