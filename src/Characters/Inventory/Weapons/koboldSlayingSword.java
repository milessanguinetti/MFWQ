package Characters.Inventory.Weapons;

import Characters.Inventory.Weapon;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class koboldSlayingSword extends Weapon{
    public koboldSlayingSword(){
        super("Kobold Slaying Sword",
                "A battle-worn sword that has slain countless kobolds throughout its years.",
                6, "1h Melee", "Fire");
    }

    public koboldSlayingSword(int Damage){
        super("Kobold Slaying Sword",
                "A battle-worn sword that has slain countless kobolds throughout its years.",
                Damage, "1h Melee", "Fire");
    }

    //combat effect methods
    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(5);
        if(Roll == 0){ //20% of the time, the attack does extra fire damage
            Game.battle.getInterface().printLeftAtNextAvailable("A critical hit!");
            Defender.takeDamage(Math.round(1.25f * (Damage + Caster.getTempStr())), "Fire");
        }
        else{ //but most of the time it just does neutral damage
            Defender.takeDamage(Math.round(.75f *(Damage + Caster.getTempStr())), "Neutral");
        }
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        return; //this weapon causes no SP loss.
    }

    //equippable item methods
    @Override
    public void applyBuffs(playerCharacter toBuff) {
        return; //this weapon applies no buffs.
    }

    @Override //this weapon adds one to strength.
    public void addStats(playerCharacter toAdd) {
        toAdd.incrementStat(0, 1);
    }

    @Override
    public void subtractStats(playerCharacter toSubtract) {
        toSubtract.incrementStat(0, -1);
    }
}
