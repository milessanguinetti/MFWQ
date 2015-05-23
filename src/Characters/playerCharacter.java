package Characters;

import Characters.Classes.characterClass;
import Characters.Inventory.Accessory;
import Characters.Inventory.Armor;
import Characters.Inventory.Consumable;
import Characters.Inventory.Weapon;
import Characters.Properties.Neutral;
import Characters.Skills.Flee;
import Characters.Skills.Skill;
import Characters.Skills.Wait;
import Characters.Skills.Passive.passiveSkill;
import Profile.Game;
import Profile.battleUI;
import Structures.LLLnode;
import Structures.orderedLLL;

import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public class playerCharacter extends gameCharacter {
    private String Race;
    private int Level;
    private int Exp;
    private int expCap;
    private Weapon Left; //weapons for each hand
    private Weapon Right;
    private Armor Armor1; //the character's armor
    private Accessory Accessory1; //two accessories
    private Accessory Accessory2;
    private orderedLLL Classes = new orderedLLL();
    private characterClass primaryClass;
    private characterClass secondaryClass;
    private orderedLLL Passives = new orderedLLL();

    //default constructor
    public playerCharacter() {
        expCap = 1000;
        charProperty = new Neutral();
    }

    //special constructor
    public playerCharacter(String name, String race, int hp, int sp, int str,
                           int dex, int spd, int vit, int inte, int fth, int arm) {
        super(name, hp, sp, str, dex, spd, vit, inte, fth, arm);
        Race = race;
        expCap = 1000;
        charProperty = new Neutral();
    }

    //chooses a target from two passed arrays of targets
    public int chooseTarget(gameCharacter [] chars, gameCharacter [] mins, boolean notUsableOnDead) {
        battleUI Interface = Game.Player.getCurrentBattle().getInterface();
        Scanner scanner = new Scanner(System.in);
        gameCharacter[] targetArray = new gameCharacter[8];
        //make an array of size 8 to fit the maximum possible sum of targetable characters
        int Selection = 0; //Selection var for whatever the user currently has selected
        int Input = 1; //user input storage variable
        int lowerBound = 0; //lowest possible selection. is incremented via the following loops
        int uSelectionView = 0; //the upper index that we can presently view
        int lSelectionView = 3; //the lower index that we can presently view
        for (int i = 0; i < 4; ++i) { //add all targetable units into the targeting array
            if (chars[i] != null) {
                if (chars[i].isAlive() == notUsableOnDead) {
                    //if the character exists and the skill can be used on them
                    targetArray[lowerBound] = chars[i]; //insert into targeting array
                    ++lowerBound; //increment our lower bound
                }
            }
            if (mins[i] != null) {
                if (mins[i].isAlive() == notUsableOnDead) {
                    //if the character exists and the skill can be used on them
                    targetArray[lowerBound] = mins[i]; //insert minion into targeting array
                    ++lowerBound; //increment our lower bound
                }
            }
        }
        //here we actually have the user select a target.
        while (Input != 3 && Input != 0) { //while input isn't enter or cancel
            //here we print the targets currently within view. there are multiple cases
            //because realistically, there can be anywhere between 1 and 8 possible targets
            if (lowerBound == 0) { //case for a single target
                Interface.printLeft(targetArray[Selection].getName());
            }
            else if (lowerBound == 1) { //case for 2 targets
                Interface.printLeft(targetArray[Selection].getName());
                Interface.printLeft(targetArray[Selection + 1].getName());
            }
            else if (lowerBound == 2) { //case for 3 targets
                Interface.printLeft(targetArray[Selection].getName());
                Interface.printLeft(targetArray[Selection + 2].getName());
                Interface.printLeft(targetArray[Selection + 3].getName());
            }
            else { //case for 4 or more targets to print.
                Interface.printLeft(targetArray[lSelectionView].getName());
                Interface.printLeft(targetArray[lSelectionView + 1].getName());
                Interface.printLeft(targetArray[lSelectionView + 2].getName());
                Interface.printLeft(targetArray[lSelectionView + 3].getName());
            }

            Interface.setTextFocus(Selection); //make whatever is selected bold
            if (Input != 4) { //this means we're presently printing an error message in the right pane
                Interface.printRight(targetArray[Selection].getName(), //otherwise, print the selected
                        "HP: " + targetArray[Selection].getHP() + "/" + //target's HP and HP cap
                                targetArray[Selection].getHPCap());
            }

            Input = Interface.getInput(); //get user input
            if (Input == 1) { //up case
                if (Selection > 0) //unless we're at the lowest selection value
                    --Selection; //decrement selection
                if (Selection < uSelectionView) {
                    --lSelectionView;
                    --uSelectionView;
                }
            }
            else if (Input == 2) { //down case
                if (Selection < lowerBound)
                    ++Selection;
                if (Selection > lSelectionView) {
                    ++lSelectionView;
                    ++uSelectionView;
                }
            }
            else if (Input == 3) { //enter case
                Input = Selection + 1; //temporarily repurpose input to a target indication variable
                for (int l = 0; l < 4; ++l) {
                    if (chars[l] != null) {
                        if (chars[l].isAlive() == notUsableOnDead) {
                            //if the character exists and the skill can be used on them
                            if (Input == 1)
                                return l + 1; //return l+1 to essentially have a meaningful
                            else              //reference to where the target is located.
                                --Input; //otherwise decrement input by 1 so that we know that
                        }                //we have passed a character and are closer to the input value
                    }
                }
                for (int m = 0; m < 4; ++m) {
                    if (chars[m] != null) {
                        if (chars[m].isAlive() == notUsableOnDead) {
                            //if the character exists and the skill can be used on them
                            if (Input == 1)
                                return m + 5; //return l+5 to essentially have a meaningful
                            else              //reference to where the target is located; +5 denotes a minion.
                                --Input; //otherwise decrement input by m so that we know that
                        }                //we have passed a character and are closer to the input value
                    }
                }   //if we get here, the user has not input a number denoting a valid target.
                Interface.printRight("Input invalid. Please choose a valid target."); //error message.
                Input = 4; //change input to 4 so we loop again.
            }
            else if (Input == 0) { //cancel case
                return 0; //return 0, cancelling the function
            }
        }
        return 0; //return 0, so the game doesn't outright crash if there's a bug that lets
                  //the user get to this point in the function.
    }

    //selects a skill to use/attacks with weapons/runs
    public combatEffect chooseSkill() {
        int Input; //user input
        int Selection = 0; //the user's current selection
        int uSelectionView = 0; //upper selection view
        int lSelectionView = 3; //lower selection view
        int lowerBound; //the upper bound of whatever menu we're in
        battleUI Interface = Game.Player.getCurrentBattle().getInterface();
        combatEffect [] primarySkills = null;
        combatEffect [] secondarySkills = null;
        combatEffect [] Items = null;
        //three arrays of combat effects; only initialized if the user specifies as much
        int State = 0;
        /* integer to keep track of what we are presenting showing the user
        0 = attack/skill/item/flee/wait selection
        1 = select class
        2 = select primary class skill
        3 = select secondary class skill
        4 = select item
         */
        while(true){ //loop infinitely until we arrive at a return value.
            Input = 1; //reset input.
            if(State == 0){ //attack/skill/item/flee/wait state
                lowerBound = 4; //this state only has 5 possible selections.
                while(Input != 3){ //while input isn't enter
                    if(lSelectionView != 4) {
                        Interface.printLeft("Attack", "Skill", "Item", "Flee");
                        Interface.setTextFocus(Selection);
                    }
                    else {
                        Interface.printLeft("Skill", "Item", "Flee", "Wait");
                        Interface.setTextFocus(Selection - 1);
                    }
                    Input = Interface.getInput(); //get user input
                    if(Input == 1){ //up case
                        if(Selection > 0) //unless we're at the lowest selection value
                            --Selection; //decrement selection
                        if(Selection < uSelectionView) { //if the selection has left our view
                            --uSelectionView;
                            --lSelectionView;
                        }
                    }
                    else if(Input == 2){ //down case
                        if(Selection < lowerBound)
                            ++Selection;
                        if(Selection > lSelectionView) {
                            ++uSelectionView;
                            ++lSelectionView;
                        }
                    }
                    else if(Input == 3) { //enter case
                        if(Selection == 0) //attack case
                            return Right; //return right hand weapon
                        else if(Selection == 1){ //skill case
                            State = 1; //select primary or secondary class
                        }
                        else if(Selection == 2){ //item case
                            State = 4;
                        }
                        else if(Selection == 3){ //flee case
                            return new Flee();
                        }
                        else if(Selection == 4){ //wait case
                            return new Wait();
                        }
                        uSelectionView = 0; //set views to 0 and 3 so we're at the
                        lSelectionView = 3; //bottom of the new submenu
                        Selection = 0; //reset selection as we change menus
                    }
                }
            }
            else if(State == 1){ //primary or secondary class state state
                lowerBound = 1; //this state only has 5 possible selections.
                while(Input != 3 && Input != 0){ //while input isn't enter or cancel
                    Interface.printLeft(primaryClass.getClassName() + " Skills",
                            secondaryClass.getClassName() + " Skills");
                    Interface.setTextFocus(Selection);
                    Input = Interface.getInput(); //get user input
                    if(Input == 1){ //up case
                        if(Selection > 0) //unless we're at the lowest selection value
                            --Selection; //decrement selection
                    }
                    else if(Input == 2){ //down case
                        if(Selection < lowerBound)
                            ++Selection;
                    }
                    else if(Input == 3) { //enter case
                        if(Selection == 0) { //Primary class case
                            if(primaryClass != null)
                                State = 2; //select primary class skill
                            else
                                Input = 1;
                        }
                        else if(Selection == 1){ //Secondary class case
                            if(secondaryClass != null)
                                State = 3; //select secondary class skill
                            else
                                Input = 2;
                        }
                        uSelectionView = 0; //set views to 0 and 3 so we're at the
                        lSelectionView = 3; //bottom of the new submenu
                        Selection = 0; //reset selection as we change menus
                    }
                    else if(Input == 0){ //cancel case
                        State = 0; //base selection case
                        uSelectionView = 0; //set views to 0 and 3 so we're at the
                        lSelectionView = 3; //bottom of the new submenu
                        Selection = 0; //reset selection as we change menus
                    }
                }
            }
            else if(State == 2) { //primary class skill selection state
                lowerBound = primaryClass.getNumSkills();
                if(primarySkills == null)
                    primarySkills = primaryClass.getSkillArray(); //ensure we have an array
                while (Input != 3 && Input != 0) { //while input isn't enter or cancel
                    //here we print the skills currently within view. there are actually a good
                    //number of cases for this because a class can have between 1 and 8 skills.
                    if(lowerBound == 0){ //case for a single skill
                        Interface.printLeft(((Skill) primarySkills[Selection]).returnKey());
                    }
                    else if(lowerBound == 1){ //case for 2 skills
                        Interface.printLeft(((Skill) primarySkills[Selection]).returnKey());
                        Interface.printLeft(((Skill) primarySkills[Selection + 1]).returnKey());
                    }
                    else if(lowerBound == 2){ //case for 3 skills
                        Interface.printLeft(((Skill) primarySkills[Selection]).returnKey());
                        Interface.printLeft(((Skill) primarySkills[Selection + 1]).returnKey());
                        Interface.printLeft(((Skill) primarySkills[Selection + 2]).returnKey());
                    }
                    else{ //case for 4 or more skills to print.
                        Interface.printLeft(((Skill) primarySkills[lSelectionView]).returnKey());
                        Interface.printLeft(((Skill) primarySkills[lSelectionView + 1]).returnKey());
                        Interface.printLeft(((Skill) primarySkills[lSelectionView + 2]).returnKey());
                        Interface.printLeft(((Skill) primarySkills[lSelectionView + 3]).returnKey());
                    }

                    Interface.setTextFocus(Selection); //make whatever is selected bold
                    //print some information about the skill in question.
                    Interface.printRight(((Skill)primarySkills[Selection]).returnKey(),
                            "Costs " + ((Skill)primarySkills[Selection]).getSPCost() +
                            " SP. (" + SP + "remaining)",
                            primarySkills[Selection].getDescription());

                    Input = Interface.getInput(); //get user input
                    if (Input == 1) { //up case
                        if (Selection > 0) //unless we're at the lowest selection value
                            --Selection; //decrement selection
                        if(Selection < uSelectionView){
                            --lSelectionView;
                            --uSelectionView;
                        }
                    } else if (Input == 2) { //down case
                        if (Selection < lowerBound)
                            ++Selection;
                        if(Selection > lSelectionView){
                            ++lSelectionView;
                            ++uSelectionView;
                        }
                    } else if (Input == 3) { //enter case
                        return primarySkills[Selection]; //return the selected skill
                    } else if (Input == 0) { //cancel case
                        State = 1; //class selection case
                        uSelectionView = 0; //set views to 0 and 3 so we're at the
                        lSelectionView = 3; //bottom of the new submenu
                        Selection = 0; //reset selection as we change menus
                    }
                }
            }
            else if(State == 3) { //secondary class skill selection state
                lowerBound = secondaryClass.getNumSkills();
                if(secondarySkills == null)
                    secondarySkills = secondaryClass.getSkillArray(); //ensure we have an array
                while (Input != 3 && Input != 0) { //while input isn't enter or cancel
                    //here we print the skills currently within view. there are actually a good
                    //number of cases for this because a class can have between 1 and 8 skills.
                    if(lowerBound == 0){ //case for a single skill
                        Interface.printLeft(((Skill) secondarySkills[Selection]).returnKey());
                    }
                    else if(lowerBound == 1){ //case for 2 skills
                        Interface.printLeft(((Skill) secondarySkills[Selection]).returnKey());
                        Interface.printLeft(((Skill)secondarySkills[Selection + 1]).returnKey());
                    }
                    else if(lowerBound == 2){ //case for 3 skills
                        Interface.printLeft(((Skill) secondarySkills[Selection]).returnKey());
                        Interface.printLeft(((Skill)secondarySkills[Selection + 1]).returnKey());
                        Interface.printLeft(((Skill) secondarySkills[Selection + 2]).returnKey());
                    }
                    else{ //case for 4 or more skills to print.
                        Interface.printLeft(((Skill) secondarySkills[lSelectionView]).returnKey());
                        Interface.printLeft(((Skill)secondarySkills[lSelectionView + 1]).returnKey());
                        Interface.printLeft(((Skill) secondarySkills[lSelectionView + 2]).returnKey());
                        Interface.printLeft(((Skill)secondarySkills[lSelectionView + 3]).returnKey());
                    }

                    Interface.setTextFocus(Selection); //make whatever is selected bold
                    //print some information about the skill in question.
                    Interface.printRight(((Skill) secondarySkills[Selection]).returnKey(),
                            "Costs " + ((Skill) secondarySkills[Selection]).getSPCost() +
                                    " SP. (" + SP + "remaining)",
                            secondarySkills[Selection].getDescription());

                    Input = Interface.getInput(); //get user input
                    if (Input == 1) { //up case
                        if (Selection > 0) //unless we're at the lowest selection value
                            --Selection; //decrement selection
                        if(Selection < uSelectionView){
                            --lSelectionView;
                            --uSelectionView;
                        }
                    } else if (Input == 2) { //down case
                        if (Selection < lowerBound)
                            ++Selection;
                        if(Selection > lSelectionView){
                            ++lSelectionView;
                            ++uSelectionView;
                        }
                    } else if (Input == 3) { //enter case
                        return secondarySkills[Selection]; //return the selected skill
                    } else if (Input == 0) { //cancel case
                        State = 1; //class selection case
                        uSelectionView = 0; //set views to 0 and 3 so we're at the
                        lSelectionView = 3; //bottom of the new submenu
                        Selection = 0; //reset selection as we change menus
                    }
                }
            }
            else if(State == 4) { //items state
                lowerBound = Game.Player.getConsumablesSize();
                if(Items == null)
                    Items = Game.Player.getConsumableArray(); //ensure we have an array
                while (Input != 3 && Input != 0) { //while input isn't enter or cancel
                    //here we print the items currently within view. there are actually a good
                    //number of cases for this because the number of consumables is quite variable.
                    if(lowerBound == 0){ //case for a single item
                        Interface.printLeft(((Consumable) Items[Selection]).returnKey());
                    }
                    else if(lowerBound == 1){ //case for 2 skills
                        Interface.printLeft(((Consumable) Items[Selection]).returnKey());
                        Interface.printLeft(((Consumable)Items[Selection + 1]).returnKey());
                    }
                    else if(lowerBound == 2){ //case for 3 skills
                        Interface.printLeft(((Consumable) Items[Selection]).returnKey());
                        Interface.printLeft(((Consumable)Items[Selection + 1]).returnKey());
                        Interface.printLeft(((Consumable) Items[Selection + 2]).returnKey());
                    }
                    else{ //case for 4 or more skills to print.
                        Interface.printLeft(((Consumable) Items[lSelectionView]).returnKey());
                        Interface.printLeft(((Consumable)Items[lSelectionView + 1]).returnKey());
                        Interface.printLeft(((Consumable) Items[lSelectionView + 2]).returnKey());
                        Interface.printLeft(((Consumable)Items[lSelectionView + 3]).returnKey());
                    }

                    Interface.setTextFocus(Selection); //make whatever is selected bold
                    //print some information about the skill in question.
                    Interface.printRight(((Consumable) Items[Selection]).returnKey(),
                            "Quantity: " + ((Consumable)Items[Selection]).getQuantity(),
                            Items[Selection].getDescription());

                    Input = Interface.getInput(); //get user input
                    if (Input == 1) { //up case
                        if (Selection > 0) //unless we're at the lowest selection value
                            --Selection; //decrement selection
                        if(Selection < uSelectionView){
                            --lSelectionView;
                            --uSelectionView;
                        }
                    } else if (Input == 2) { //down case
                        if (Selection < lowerBound)
                            ++Selection;
                        if(Selection > lSelectionView){
                            ++lSelectionView;
                            ++uSelectionView;
                        }
                    } else if (Input == 3) { //enter case
                        return Items[Selection]; //return the selected skill
                    } else if (Input == 0) { //cancel case
                        State = 0; //return to base selection case
                        uSelectionView = 0; //set views to 0 and 3 so we're at the
                        lSelectionView = 3; //bottom of the new submenu
                        Selection = 0; //reset selection as we change menus
                    }
                }
            }
        }
    }

    //is the character of the passed race?
    public boolean isOfRace(String toCompare){
        if(Race.compareTo(toCompare) == 0)
            return true;
        return false;
    }

    //does the character have the passed weapon type?
    public boolean hasWeaponType(String toCompare, boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return false; //false case
            return Right.isOfType(toCompare); //otherwise compare & return
        }
        else { //left weapon case
            if (Left == null) //if there is no left weapon
                return false; //false case
            return Left.isOfType(toCompare); //otherwise compare & return
        }
    }

    //checks to see if the character's weapon's property matches the passed string
    public boolean hasWeaponProperty(String toCompare, boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return toCompare.equals("Neutral"); //check neutral
            return Right.isOfProperty(toCompare); //otherwise compare & return
        }
        else{ //left weapon case
            if(Left == null) //if there is no left weapon
                return toCompare.equals("Neutral"); //check neutral
            return Left.isOfProperty(toCompare); //otherwise compare & return
        }
    }

    //gets the character's weapon's property.
    public String getWeaponProperty(boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return "Neutral"; //return neutral
            return Right.getProperty();
        }
        else{ //left weapon case
            if(Left == null) //if there is no left weapon
                return "Neutral"; //return neutral
            return Left.getProperty();
        }
    }

    public int getWeaponDamage(boolean isRight){
        if(isRight){ //right weapon case
            if(Right == null) //if there is no right weapon
                return Str; //return strength for a fist attack
            return Right.getDamage();
        }
        else{ //left weapon case
            if(Left == null) //if there is no left weapon
                return Str; //return strength for a fist attack
            return Left.getDamage();
        }
    }

    //add a class to the ordered LLL
    public void addClass(characterClass toAdd){
        Classes.Insert(new LLLnode(toAdd));
    }

    //add a passive skill to the ordered LLL
    public void addPassive(passiveSkill toAdd){
        LLLnode temp = new LLLnode(toAdd);
        Passives.Insert(temp);
    }

    //chooses a class, be it a primary or secondary class.
    public void chooseClass(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to adjust this character's primary or secondary class?");
        System.out.println("Enter '1' for primary, '2' for secondary or '0' to cancel.");
        int whichClass = scanner.nextInt();
        scanner.nextLine();
        if(whichClass == 0)
            return;
        System.out.println("Choose a class.");
        Classes.displayNumbered();
        int toGet = scanner.nextInt();
        scanner.nextLine();
        LLLnode Retrieved = Classes.retrieveInt(toGet);
        if(Retrieved == null){
            System.out.println("Invalid class.");
        }
        else {
            if(whichClass == 2) //set secondary class to retrieved.
                secondaryClass = ((characterClass)Retrieved.returnData());
            else //set primary class to retrieved.
                primaryClass = ((characterClass)Retrieved.returnData());
        }
    }

    //selects and 'equips' a passive skill that the character has learned
    public void choosePassive(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a passive skill.");
        Passives.displayNumbered();
        int toGet = scanner.nextInt();
        scanner.nextLine();
        LLLnode Retrieved = Passives.retrieveInt(toGet);
        if(Retrieved == null){
            System.out.println("Invalid passive skill.");
        }
        else
            currentPassive = ((passiveSkill)Retrieved.returnData());
    }

    public void Ding(int exp, int jexp){
        if((Exp + exp) >= expCap){ //if we've received enough exp to level...
            System.out.println(Name + " gained a level! " + Name + "'s stats increased!");
            exp -= (expCap - exp);
            ++Level;
            expCap += Math.round(expCap*1.2); //exp cap goes up by 20%
            Exp = 0; //reset Exp to 0 now that we've leveled.
            baseStatGains(); //increase base stats.
            if(primaryClass != null)
                primaryClass.baseDing(this, Level); //gain auxilary class stat boosts
            Ding(exp, 0); //recursive call; jexp == 0 so that we don't gain it twice.
        }
        else
            Exp += exp; //otherwise, we just add it to our exp.
        if(primaryClass != null) { //level the character's class with jexp
            primaryClass.gainJexp(jexp, this);
        }
    }

    //calculates base stat gains upon leveling
    public void baseStatGains(){
        MHP += 10; //MHP and SP necessarily increase
        MSP += 5;
        HP = MHP; //full heal
        SP = MSP;
        ++Vit;
        if(Level % 2 == 0) {
            ++Str;
            ++Int;
        }
        if((Level + 1) % 2 == 0) {
            ++Dex;
            ++Fth;
        }
        if(Level % 3 == 0)
            ++Spd;
    }

    public void setLeft(Weapon left) {
        Left = left;
    }

    public void setRight(Weapon right) {
        Right = right;
    }

    public Weapon getLeft(){
        return Left;
    }

    public Weapon getRight(){
        return Right;
    }

    public void setArmor(Armor armor1) {
        if(Armor1 != null)
            Armor1.Unequip(this);
        Armor1 = armor1;
    }

    public void setAccessory(Accessory toSet) {
        if(Accessory1 != null){
            Accessory1 = toSet;
        }
        else if(Accessory2 != null){
            Accessory2 =toSet;
        }
        else{
            Accessory1.Unequip(this);
            Accessory1 = toSet;
        }
    }

    //applies this character's automatic buffs for each equipped item
    public void applyAutoBuffs(){
        tempProperty = charProperty;
        setTemps();
        if(!(Right == Left)){ //if this isn't a two-handed weapon
            if(Right != null)
                Right.applyBuffs(this); //apply right's buffs
            if(Left != null)
                Left.applyBuffs(this); //and left's buffs
        }
        else{ //if this is a two-handed weapon
            if(Right != null)
                Right.applyBuffs(this);
        }
        if(Armor1 != null)
            Armor1.applyBuffs(this);
        if(Accessory1 != null)
            Accessory1.applyBuffs(this);
        if(Accessory2 != null)
            Accessory2.applyBuffs(this);
        if(currentPassive != null)
            currentPassive.passiveEffect(this);
    }

    public void setPrimaryClass(characterClass primaryClass) {
        primaryClass = primaryClass;
    }

    public void setSecondaryClass(characterClass secondaryClass) {
        secondaryClass = secondaryClass;
    }

    public boolean canUseHeavyArmor(){
        if(primaryClass == null) //no segfaults
            return false;
        if(primaryClass.canUseHeavyArmor())
            return true;
        return false;
    }

    //function for dropping loot at the end of a battle.
    //if a player character dies, they drop their equipment
    //so that it isn't lost.
    public void Loot(){
        System.out.println(Name + " dropped their equipment.");
        if(!(Right == Left)){ //if this isn't a two-handed weapon
            if(Right != null)
                Game.Player.Insert(Right); //add right to inventory
            if(Left != null)
                Game.Player.Insert(Left); //and left to inventory
        }
        else{ //if this is a two-handed weapon
            if(Right != null)
                Game.Player.Insert(Right); //add the weapon to inventory
        }
        if(Armor1 != null)
            Game.Player.Insert(Armor1);
        if(Accessory1 != null)
            Game.Player.Insert(Accessory1);
        if(Accessory2 != null)
            Game.Player.Insert(Accessory2);
    }

    @Override
    public boolean hasTwoHandedWeapon() {
        if(Right == Left)
            return true;
        return false;
    }
}
