package Characters.Skills;

import Characters.combatEffect;
import Characters.gameCharacter;
import Structures.Data;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public abstract class Skill extends combatEffect implements Data{
    String skillName;
    String Description;
    int mpCost;

    @Override
    public boolean canUse(gameCharacter toCheck){
        if(toCheck.getSP() < mpCost)
            return false;
        return true;
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
