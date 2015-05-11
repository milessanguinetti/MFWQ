package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.Status.matchBestStats;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class geneSplicerGenomeBinding extends Skill {
    public geneSplicerGenomeBinding(){
        super("Genome Binding", "Mixes the best aspects of the caster's and an allies genomes," +
                "giving each character the greatest strengths of the other.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 50;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Caster == Defender){
            Caster.printName();
            System.out.println(" cannot mix their own genes together!");
            return;
        }
        Defender.addStatus(new matchBestStats(Caster));
        Caster.addStatus(new matchBestStats(Defender));
    }
}
