package Characters;

/**
 * Created by Miles Sanguinetti on 3/17/15.
 */
public class Stats {
    protected int HP; //health parameter
    protected int MHP; //maximum health parameter
    protected int SP; //skill points parameter
    protected int MSP; //maximum skill points parameter
    protected int Str; //strength parameter
    protected int tempStr; //temporary str param for fights
    protected int Dex; //dexterity parameter
    protected int tempDex; //temp dex param
    protected int Spd; //speed parameter
    protected int tempSpd; //temp spd param
    protected int Vit; //vitality parameter
    protected int tempVit; //temp vit param
    protected int Swg; //swag parameter
    protected int tempSwg; //temp swg param
    protected int Luk; //luck parameter
    protected int tempLuk; //temp luk param
    protected int Int; //intellect parameter
    protected int tempInt; //temp int param
    protected int Fth; //faith parameter
    protected int tempFth; //temp fth param

    //default constructor
    public Stats(){}

    //copy constructor
    public Stats(Stats toCopy){
        MHP = toCopy.MHP;
        MSP = toCopy.MSP;
        Str = toCopy.Str;
        Dex = toCopy.Dex;
        Spd = toCopy.Spd;
        Vit = toCopy.Vit;
        Swg = toCopy.Swg;
        Luk = toCopy.Luk;
        Int = toCopy.Int;
        Fth = toCopy.Fth;
    }

    //checks to see if the character is alive
    public boolean isAlive(){
        if(HP != 0)
            return true;
        return false;
    }

    //long list of getters. annoying, but important for combat calculations in other classes.
    public int getTempFth() {
        return tempFth;
    }

    public int getTempStr() {
        return tempStr;
    }

    public int getTempDex() {
        return tempDex;
    }

    public int getTempSpd() {
        return tempSpd;
    }

    public int getTempVit() {
        return tempVit;
    }

    public int getTempSwg() {
        return tempSwg;
    }

    public int getTempLuk() {
        return tempLuk;
    }

    public int getTempInt() {
        return tempInt;
    }

    public int getSP(){
        return SP;
    }

    //temp setters
    public void setTempFth(int tempFth) {
        this.tempFth = tempFth;
    }

    public void setTempStr(int tempStr) {
        this.tempStr = tempStr;
    }

    public void setTempDex(int tempDex) {
        this.tempDex = tempDex;
    }

    public void setTempSpd(int tempSpd) {
        this.tempSpd = tempSpd;
    }

    public void setTempVit(int tempVit) {
        this.tempVit = tempVit;
    }

    public void setTempSwg(int tempSwg) {
        this.tempSwg = tempSwg;
    }

    public void setTempLuk(int tempLuk) {
        this.tempLuk = tempLuk;
    }

    public void setTempInt(int tempInt) {
        this.tempInt = tempInt;
    }

    //display function
    public void Display(){
        System.out.println("HP:" + HP + '/' + MHP + " SP: " + SP + '/' + MSP);
        System.out.println("Str: " + Str + " Dex: " + Dex + " Spd: " + Spd + " Vit: " + Vit);
        System.out.println("Swg: " + Swg + " Luk: " + Luk + " Int: " + Int + " Fth: " + Fth);
    }

    //subtract passed int from HP; returns true if alive and false if dead
    public boolean subtractHP(int toSubtract){
        HP -= toSubtract; //subtract
        if(HP > MHP)
            HP = MHP; //no health pools above max after taking negative damage
        else if(HP < 0)
            HP = 0; //no health pools lower than 0 either
        if(HP > 0)
            return true;
        return false; //return value based on whether or not character is alive
    }

    //multiply HP by passed integer; returns true if alive and false if dead; rounds
    public boolean multiplyHP(float toMultiply){
        HP = Math.round(HP * toMultiply); //multiply
        if(HP > MHP)
            HP = MHP; //no health pools above max after taking negative damage
        else if(HP < 0)
            HP = 0; //no health pools lower than 0 either
        if(HP > 0)
            return true;
        return false; //return value based on whether or not character is alive
    }

    //subtract passed int from SP; returns true if they have the MP to begin with, false if not
    public boolean subtractSP(int toSubtract){
        if(!(SP - toSubtract > 0))
            return false; //return false if we don't have the SP to cast or whatever
        SP -= toSubtract; //subtract
        if(SP > MSP)
            SP = MSP; //no SP pools above max after gaining SP
        return true;
        }

    //multiply SP by passed integer; returns true if not oom and false if oom; rounds
    public boolean multiplySP(float toMultiply){
        SP = Math.round(SP * toMultiply); //multiply
        if(SP > MSP)
            SP = MSP; //no SP pools above max after gaining SP
        else if(SP < 0)
            SP = 0; //no SP pools lower than 0 either
        if(SP > 0)
            return true;
        return false; //return value based on whether or not character is alive
    }


    //set all temps to their base values, a la the beginning of a fight.
    public void setTemps(){
            tempStr = Str;
            tempDex = Dex;
            tempSpd = Spd;
            tempVit = Vit;
            tempSwg = Swg;
            tempLuk = Luk;
            tempInt = Int;
            tempFth = Fth;
    }

    //increment and return a given temp parameter
    //based on the passed integer and float
    public int tempIncrement(int toIncrement, float Boost){
        if(toIncrement == 0) { //str case
            tempStr = Math.round(tempStr * Boost);
            return tempStr;
        }

        else if(toIncrement == 1) { // dex case
            tempDex = Math.round(tempDex * Boost);
            return tempDex;
        }

        else if(toIncrement == 2) { //speed case
            tempSpd = Math.round(tempSpd * Boost);
            return tempSpd;
        }

        else if(toIncrement == 3) { //vit case
            tempVit = Math.round(tempVit * Boost);
            return tempVit;
        }

        else if(toIncrement == 4) { //swag case
            tempSwg = Math.round(tempSwg * Boost);
            return tempSwg;
        }

        else if(toIncrement == 5) { //luck case
            tempLuk = Math.round(tempLuk * Boost);
            return tempLuk;
        }

        else if(toIncrement == 6) { //int case
            tempInt = Math.round(tempInt * Boost);
            return tempInt;
        }

        else if(toIncrement == 7){ //fth case
            tempFth = Math.round(tempFth * Boost);
            return tempFth;
        }

        return 0; //stat not found
    }

    //increment and return a given parameter
    //based on the passed string
    public int incrementStat(String toIncrement){
        if(toIncrement.toUpperCase().equals("STR")) { //str case
            ++Str;
            return Str;
        }

        else if(toIncrement.toUpperCase().equals("DEX")) { // dex case
            ++Dex;
            return Dex;
        }

        else if(toIncrement.toUpperCase().equals("SPD")) { //speed case
            ++Spd;
            return Spd;
        }

        else if(toIncrement.toUpperCase().equals("VIT")) { //vit case
            ++Vit;
            HP += 10;
            MHP += 10; //vit incrementation at level up boosts HP
            return Vit;
        }

        else if(toIncrement.toUpperCase().equals("SWG")) { //swag case
            ++Swg;
            return Swg;
        }

        else if(toIncrement.toUpperCase().equals("LUK")) { //luck case
            ++Luk;
            return Luk;
        }

        else if(toIncrement.toUpperCase().equals("INT")) { //int case
            ++Int;
            SP += 10;
            MSP += 10; //int incrementation at level up boosts SP
            return Int;
        }

        else if(toIncrement.toUpperCase().equals("FTH")) { //fth case
            ++Fth;
            SP += 10;
            MSP += 10; //int incrementation at level up boosts SP
            return Fth;
        }

        return 0; //stat not found
    }

    //increment and return a given parameter
    //based on the passed integer
    public int incrementStat(int toIncrement){
        if(toIncrement == 0) { //str case
            ++Str;
            return Str;
        }

        else if(toIncrement == 1) { // dex case
            ++Dex;
            return Dex;
        }

        else if(toIncrement == 2) { //speed case
            ++Spd;
            return Spd;
        }

        else if(toIncrement == 3) { //vit case
            ++Vit;
            return Vit;
        }

        else if(toIncrement == 4) { //swag case
            ++Swg;
            return Swg;
        }

        else if(toIncrement == 5) { //luck case
            ++Luk;
            return Luk;
        }

        else if(toIncrement == 6) { //int case
            ++Int;
            return Int;
        }

        else if(toIncrement == 7){ //fth case
            ++Fth;
            return Fth;
        }

        return 0; //stat not found
    }
}
