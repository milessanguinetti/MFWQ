package Characters.Skills;

import Characters.combatEffect;
import Characters.gameCharacter;
import Structures.Data;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public abstract class Skill implements Data, combatEffect{
    String skillName;
    String Description;
    int mpCost;

    //default constructor
    Skill(){}

    //special constructor
    Skill(String passedName, String passedDescription, int passedCost){
        skillName = passedName;
        Description = passedDescription;
        mpCost = passedCost;
    }

    @Override
    public void Display() {
        System.out.println(skillName + ':');
        System.out.println(Description);
        System.out.println("MP Cost: " + mpCost);
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
        System.out.println("MP Cost: " + mpCost);
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
}
