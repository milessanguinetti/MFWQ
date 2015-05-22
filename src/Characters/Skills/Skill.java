package Characters.Skills;

import Characters.combatEffect;
import Structures.Data;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public abstract class Skill implements Data, combatEffect{
    protected String skillName;
    private String Description;
    int spCost;

    //default constructor
    public Skill(){}

    //special constructor
    public Skill(String passedName, String passedDescription, int passedCost){
        skillName = passedName;
        Description = passedDescription;
        spCost = passedCost;
    }

    @Override
    public void Display() {
        System.out.println(skillName + ':');
        System.out.println(Description);
        System.out.println("SP Cost: " + spCost);
    }

    @Override
    public String getDescription(){
        return Description;
    }

    public int getSPCost(){
        return spCost;
    }

    @Override
    public void Display(int indent) {
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(skillName + ':');
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(Description);
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println("MP Cost: " + spCost);
    }

    @Override
    public void printName(){
        System.out.print(skillName);
    }

    @Override
    public String returnKey() {
        return skillName;
    }

    @Override
    public void setKey(String name) {
        skillName = name;
    }

    @Override
    public int compareTo(String toCompare) {
        return skillName.compareTo(toCompare);
    }

    @Override
    public int compareTo(Data toCompare) {
        return skillName.compareTo(toCompare.returnKey());
    }

    @Override
    public void writeOut(PrintWriter toWrite) {
        //TO BE IMPLEMENTED
    }
}
