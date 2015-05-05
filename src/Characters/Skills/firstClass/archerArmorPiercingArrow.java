package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class archerArmorPiercingArrow extends Skill{
    public archerArmorPiercingArrow(){
        super("Armor Piercing Arrow",
                "Shoots a heavy, steel-tipped arrow that can penetrate even the toughest armor.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
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
        if(toCheck.getSP() >= 10)
            return false; //SP requirements
        if(!toCheck.hasWeaponType("Bow", true))
            return false; //skill can only be used with a bow
        return true; //otherwise, the skill can be used
    }

    @Override //deal dex + weapon damage damage. ignores armor.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage(Caster.getWeaponDamage(true) +
                Caster.getTempDex());
    }
}
