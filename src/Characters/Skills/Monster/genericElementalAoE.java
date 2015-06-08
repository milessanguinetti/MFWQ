package Characters.Skills.Monster;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class genericElementalAoE extends Skill{
    String Element;

    //default constructor
    public genericElementalAoE(){
        super("Elemental Spray", "", 25);
    }

    //constructor with element
    public genericElementalAoE(String element){
        super("A Spray of ", "", 25);
        Element = element; //copy down element
        if(element.equals("Organic")){ //organic sounds weird, so we change that to acid.
            skillName += "Acid!";
        }
        else if(element.equals("Undead")){
            skillName += "Rot!";
        }
        else if(element.equals("Holy") || element.equals("Unholy") || element.equals("Ghost")){
            skillName += element + " Energy!";
        }
        else{ //for other elements
            skillName += element + "!";
        }
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(25);
    }

    @Override
    public boolean notUsableOnDead() {
        return true;
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 25;
    }

    @Override //deal damage equal to 1.5 times a monster's highest stat..
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(Caster.getWeaponDamage(true) * 1.5f), Element);
    }
}
