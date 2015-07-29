package Characters.Inventory.Weapons;

import Characters.Inventory.Weapon;
import Characters.gameCharacter;
import Characters.playerCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles on 7/28/2015.
 */
public class Nodachi extends Weapon {
    public Nodachi(){
        super("Blazing Nodachi",
                "An unspeakably powerful weapon from a foreign land.",
                100, "1h Melee", "Fire");
    }

    public Nodachi(int Damage){
        super("Blazing Nodachi",
                "An unspeakably powerful weapon from a foreign land.",
                100 + Damage, "1h Melee", "Fire");
    }

    //combat effect methods
    @Override
    public int getAoE() {
        return 4;
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
            Game.battle.getInterface().printLeftAtNextAvailable("The nodachi lets out a howl!");
            Defender.takeDamage(Math.round(3.25f * (Damage + Caster.getTempStr())) + Caster.getTempSpd(), "Fire");
        }
        else{ //but most of the time it just does neutral damage
            Defender.takeDamage(Math.round((Damage + Caster.getTempStr())), "Fire");
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
        toAdd.incrementStat(2, 30);
    }

    @Override
    public void subtractStats(playerCharacter toSubtract) {
        toSubtract.incrementStat(2, 30);
    }
}
