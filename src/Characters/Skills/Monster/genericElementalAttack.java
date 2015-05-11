package Characters.Skills.Monster;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class genericElementalAttack extends Skill{
    String Element;

    //default constructor
    public genericElementalAttack(){
        super("Elemental Attack", "", 15);
    }

    //constructor with element
    public genericElementalAttack(String element){
        super("A Blast of ", "", 15);
        Element = element; //copy down element
        if(element.equals("Organic")){ //organic sounds weird, so we change that to acid.
            skillName += "Acid!";
        }
        else if(element.equals("Undead")){
            skillName += "Rot!";
        }
        else if(element.equals("Holy") || element.equals("Unholy") || element.equals("Ghost")){
            skillName += element + "Energy!";
        }
        else{ //for other elements
            skillName += element + "!";
        }
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 15;
    }

    @Override //deal damage equal to twice the (monster) caster's highest stat.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Caster.getWeaponDamage(true) * 2, Element);
    }
}
