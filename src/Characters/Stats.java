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
    protected int Int; //intellect parameter
    protected int tempInt; //temp int param
    protected int Fth; //faith parameter
    protected int tempFth; //temp fth param
    protected int Armor; //armor parameter; entirely from equipment and buffs
    protected int tempArmor; //temporary armor parameter; for combat

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
        Int = toCopy.Int;
        Fth = toCopy.Fth;
        Armor = toCopy.Armor;
    }

    //constructor with a mountain of arguments
    public Stats(int hp, int sp, int str, int dex, int spd, int vit, int inte, int fth, int arm){
        MHP = hp;
        HP = MHP;
        MSP = sp;
        SP = MSP;
        Str = str;
        Dex = dex;
        Spd = spd;
        Vit = vit;
        Int = inte;
        Fth = fth;
        Armor = arm;
    }

    //checks to see if the character is alive
    public boolean isAlive(){
        if(HP != 0)
            return true;
        return false;
    }

    //scales all stats by a factor included as a parameter. Used to ramp up difficulty
    //for new game + or perhaps for inclusion of a tougher version of an extant monster.
    public void scaleDifficulty(float scaleFactor){
        MHP = Math.round(MHP * scaleFactor);
        HP = MHP;
        SP = Math.round(MSP * scaleFactor);
        SP = MSP;
        Str = Math.round(Str * scaleFactor);
        Dex = Math.round(Dex * scaleFactor);
        Spd = Math.round(Spd * scaleFactor);
        Vit = Math.round(Vit * scaleFactor);
        Int = Math.round(Int * scaleFactor);
        Fth = Math.round(Fth * scaleFactor);
        Armor = Math.round(Armor * scaleFactor);
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

    public int getTempInt() {
        return tempInt;
    }

    public int getSP(){
        return SP;
    }

    public int getSPCap(){return MSP;}

    public int getHP(){
        return HP;
    }

    public int getHPCap(){ return MHP; }

    //temp setters
    public void setTempFth(int ptempFth) {
        tempFth = ptempFth;
    }

    public void setTempStr(int ptempStr) {
        tempStr = ptempStr;
    }

    public void setTempDex(int ptempDex) {
        tempDex = ptempDex;
    }

    public void setTempSpd(int ptempSpd) {
        tempSpd = ptempSpd;
    }

    public void setTempVit(int ptempVit) {
        tempVit = ptempVit;
    }

    public void setTempInt(int ptempInt) {
        tempInt = ptempInt;
    }

    public void setTempArmor(int ptempArmor){
        tempArmor = ptempArmor;
    }

    //display function
    public void Display(){
        System.out.println("HP:" + HP + '/' + MHP + " SP: " + SP + '/' + MSP);
        System.out.println("Str: " + Str + " Dex: " + Dex + " Spd: " + Spd);
        System.out.println(" Vit: " + Vit + "Int: " + Int + " Fth: " + Fth);
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
        if(!(SP - toSubtract < 0))
            return false; //return false if we don't have the SP to cast or whatever
        SP -= toSubtract; //subtract
        if(SP > MSP)
            SP = MSP; //no SP pools above max after gaining SP
        if(SP < 0)
            SP = 0;
        return true;
        }

    //multiply SP by passed integer; returns number of sp lost; rounds
    public int multiplySP(float toMultiply){
        int toReturn = (Math.round(SP * toMultiply) - SP) *-1; //find out how much SP we are losing
        SP -= toReturn; //actually lose SP now that we know how much we lost
        if(SP > MSP)
            SP = MSP; //no SP pools above max after gaining SP
        else if(SP < 0)
            SP = 0; //no SP pools lower than 0 either
        return toReturn;
    }


    //set all temps to their base values, a la the beginning of a fight.
    public void setTemps(){
        tempStr = Str;
        tempDex = Dex;
        tempSpd = Spd;
        tempVit = Vit;
        tempInt = Int;
        tempFth = Fth;
        tempArmor = Armor;
    }

    //multiply and return a given temp parameter
    //based on the passed integer and float
    public int tempMultiply(int toIncrement, float Boost){
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

        else if(toIncrement == 4) { //int case
            tempInt = Math.round(tempInt * Boost);
            return tempInt;
        }

        else if(toIncrement == 5){ //fth case
            tempFth = Math.round(tempFth * Boost);
            return tempFth;
        }

        else if(toIncrement == 6){ //armor case.
            tempArmor = Math.round(tempArmor * Boost);
            return tempArmor;
        }

        return 0; //stat not found
    }

    //increment and return a given temp parameter
    //based on the passed integer
    public int incrementTemp(int toIncrement, float Value){
        if(toIncrement == 0) { //str case
            tempStr += Value;
            return tempStr;
        }

        else if(toIncrement == 1) { // dex case
            tempDex += Value;
            return tempDex;
        }

        else if(toIncrement == 2) { //speed case
            tempSpd += Value;
            return tempSpd;
        }

        else if(toIncrement == 3) { //vit case
            tempVit += Value;
            return tempVit;
        }

        else if(toIncrement == 4) { //int case
            tempInt += Value;
            return tempInt;
        }

        else if(toIncrement == 5){ //fth case
            tempFth += Value;
            return tempFth;
        }

        else if(toIncrement == 6){ //armor case
            tempArmor += Value;
            return tempArmor;
        }

        return 0; //stat not found
    }

    //increment and return a given parameter
    //based on the passed integer
    public int incrementStat(int toIncrement, int Value){
        if(toIncrement == 0) { //str case
            Str += Value;
            return Str;
        }

        else if(toIncrement == 1) { // dex case
            Dex += Value;
            return Dex;
        }

        else if(toIncrement == 2) { //speed case
            Spd += Value;
            return Spd;
        }

        else if(toIncrement == 3) { //vit case
            Vit += Value;
            MHP += Value * 10;
            if(MHP < HP)
                HP = MHP;
            return Vit;
        }

        else if(toIncrement == 4) { //int case
            Int += Value;
            MSP += Value * 5;
            if(MSP < SP)
                SP = MSP;
            return Int;
        }

        else if(toIncrement == 5){ //fth case
            Fth += Value;
            MSP += Value * 5;
            if(MSP < SP)
                SP = MSP;
            return Fth;
        }

        else if(toIncrement == 6){ //armor case
            Armor += Value;
            return Armor;
        }

        return 0; //stat not found
    }

    //increments all stats by one; kind of a weird function, but useful because
    //all jobs give +1 to all stats at jlvl20
    public void incrementAll() {
            ++Str;
            ++Dex;
            ++Spd;
            ++Vit;
            MHP += 10;
            if (MHP < HP)
                HP = MHP;
            ++Int;
            ++Fth;
            MSP += 5;
            if (MSP < SP)
                SP = MSP;
    }
}