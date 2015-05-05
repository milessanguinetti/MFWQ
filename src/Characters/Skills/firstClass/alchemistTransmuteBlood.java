package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
public class alchemistTransmuteBlood extends Skill {
    public alchemistTransmuteBlood(){
        super("Transmute: Blood",
                "Attacks the target and transmutes their blood into gold.", 5);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Game.Player.addCoins(-1*Caster.getWeaponDamage(false));
        //player loses coins equal to the caster's weapon damage
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
        if(toCheck.getSP() >= 5)
            return false; //SP requirements
        return true;
    }

    @Override //deal 50% damage calculated by dex and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Taken = Defender.getHP();
        Taken -= Defender.takeDamage(Math.round(.5f*(Caster.getWeaponDamage(true)
                + Caster.getTempDex())), Caster.getWeaponProperty(true));
        //get defender's hp and subtract their remaining HP after the attack from
        //it to see how much damage the attack actually dealt.
        Game.Player.addCoins(Taken);
        Caster.printName();
        System.out.println(" transmuted " + Taken + " gold.");
    }
}
