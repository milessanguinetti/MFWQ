package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class primalistDevouringBloom extends Skill{
    public primalistDevouringBloom(){
        super("Devouring Bloom",
                "Causes a devouring growth on an enemy to bloom, dealing heavy damage, but removing the effect.", 5);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(5);
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
        return toCheck.getSP() >= 5;
    }

    @Override //Removes an instance of devouring bloom, but deals heavy true damage based on right weapon
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.statusRemove("Devouring Growth")){
            Defender.printName();
            System.out.println("'s Devouring Growth bloomed!");
            Defender.takeAbsoluteDamage(Math.round(Caster.getWeaponDamage(true) * 3.5f));
        }
        else
            System.out.println("The spell had no effect.");
    }
}
