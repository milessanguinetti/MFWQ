package Characters.Classes;

import Characters.playerCharacter;
import Profile.experienceNotification;
import Structures.Data;
import Structures.orderedDLL;
import Structures.orderedDLLNode;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by Miles Sanguinetti on 3/21/15.
 */
public abstract class characterClass implements Data, Serializable {
    private String className; //the name of the class
    protected int jlevel = 1;
    private int jexp = 0; //job experience and job experience need to reach next level.
    private int jexpCap = 800;
    protected orderedDLL Skills = new orderedDLL(); //skills for this class

    //default constructor
    public characterClass(){}

    //constructor with class name
    public characterClass(String toName){
        className = toName;
    }

    //constructor with class name and replacement jexpcap value (used for second classes)
    public characterClass(String toName, int experiencecap){
        className = toName;
        jexpCap = experiencecap;
    }

    public String getClassName(){
        return className;
    }

    public int getJlevel(){
        return jlevel;
    }

    public int getNumSkills(){
        return Skills.getSize();
    }

    //add job experience to the character's class.
    public void gainJexp(int Gains, playerCharacter toLevel){
        if(jlevel == 20 || jexpCap > 60000) {
            experienceNotification.queueExpEvent(toLevel.getName() + " is at level cap for " + className + ".",
                    1f, 1f, false, jlevel, 0);
            return; //no leveling past 20 for first classes or 10 for second classes
        }
        if((jexp + Gains) >= jexpCap){ //if we've received enough jexp to level...
            experienceNotification.queueExpEvent(toLevel.getName() + " gained a job level as a " + className + "!"
                    + '\n' + jobDing(toLevel), (1f*jexp)/jexpCap, 1f, false, jlevel, 0);
            Gains -= (jexpCap - jexp);
            ++jlevel;
            jexpCap += Math.round(jexpCap*0.2); //jexp cap goes up by 20%
            jexp = 0; //reset jexp to 0 now that we've leveled.
            gainJexp(Gains, toLevel); //recursive call to add the rest of the jexp
                                      //onto the next level.
        }
        else if(Gains > 0){
            experienceNotification.queueExpEvent(toLevel.getName()
                    + " gained " + Gains + " experience as a " + className + "!",
                    (1f*jexp)/jexpCap, (1f*jexp+Gains)/jexpCap, false, jlevel, 0);
            jexp += Gains; //otherwise, we just add it to our exp.
        }
    }

    //adds stat bonuses to a character according to the class in question
    public abstract void baseDing(playerCharacter toLevel, int level);

    //adds skills according to the class in question. Character is included
    //to the end of adding passive skills to their passive skill list.
    public abstract String jobDing(playerCharacter toLevel);

    public void displaySkills(){
        Skills.Display();
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
        return className.compareTo(toCompare.returnKey());
    }

    public orderedDLLNode getSkillHead(){
        return Skills.getHead();
    }
}
