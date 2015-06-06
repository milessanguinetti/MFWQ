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
import Structures.orderedDLLNode;
import Structures.orderedLLL;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public class playerCharacter extends gameCharacter {
    //Data for game logistics
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

    //Data for interfacing states
    int Selection = 0; //the user's current selection
    int uSelectionView = 0; //upper selection view
    int lSelectionView = 3; //lower selection view
    int lowerBound; //the upper bound of whatever menu we're in
    orderedDLLNode selectedNode = null; //a node that we presently have selected; used for return info and keeping
                                        //track of where "selected" exists in a given data structure.
    int State = 0;
        /* integer to keep track of what we are presenting showing the user
        0 = attack/skill/item/flee/wait selection
        1 = select class
        2 = select primary class skill
        3 = select secondary class skill
        4 = select item
        5 = select target
         */

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

    //initializes combat data; resetting state information for player characters.
    @Override
    public void initializeCombatData(){
        Selection = 0; //reset variables linked to UI selection.
        uSelectionView = 0;
        lSelectionView = 3;
        State = 0;
        //since most printing occurs via event-driven mechanisms, we take this opportunity to print
        //something before the user enters any input to give them some reference on what to do next
        Game.battle.getInterface().printLeft("Attack", "Skill", "Item", "Flee");
        Game.battle.getInterface().setTextFocus(0);
    }

    //handles a piece of input entered during a battle.
    @Override
    public void handleInput(KeyEvent toHandle){
        int Input; //variable for storing what amounts to a translated keyevent.
        if(toHandle.getCode() == KeyCode.ENTER || toHandle.getCode() == KeyCode.RIGHT
                || toHandle.getCode() == KeyCode.D) //enter case; input = 3
            Input = 3;
        else if(toHandle.getCode() == KeyCode.UP || toHandle.getCode() == KeyCode.W) //up case; input = 1
            Input = 1;
        else if(toHandle.getCode() == KeyCode.DOWN || toHandle.getCode() == KeyCode.S) //down case; input = 2;
            Input = 2;
        else if(toHandle.getCode() == KeyCode.BACK_SPACE || toHandle.getCode() == KeyCode.LEFT
                || toHandle.getCode() == KeyCode.A) //cancel case; input = 0
            Input = 0;
        else{
            return; //we do not handle other keycodes here.
        }
        if(State < 5){ //any case other than target selection
            combatEffect toCast = chooseSkill(Input); //store any return value in a combat effect local var
            if(toCast != null){ //if we retrieved a skill...
                State = 5; //set state to 5.
                currentlyTargeting = Commands.initializeSkill(toCast);
                //record the set of characters that we are presently able to target.
                Selection = 0; //reset viewing paramaters as we enter the targeting menu.
                lowerBound = 0;
                uSelectionView = 0;
                lSelectionView = 3;
                boolean whichSide = currentlyTargeting > 0;
                chooseTarget(Game.battle.getParty(whichSide), Game.battle.getMinions(whichSide), 1);
                //utilize the choosetarget method to just print possible targets, as an up command will do
                //nothing at selection 0.
            }
        }
        else{ //strictly target selection
            if(Input == 0){ //cancel case
                initializeCombatData(); //essentially reset the combat data so that we're back in the original menus
                return; //and return.
            }
            lowerBound = 0; //reset lower bound.
            boolean whichSide = currentlyTargeting > 0;
            int targetIndex = chooseTarget(Game.battle.getParty(whichSide), Game.battle.getMinions(whichSide), Input);
            if(targetIndex != 0){ //if the user hit enter and selected a valid target...
                if(currentlyTargeting > 0) //if we're targeting a player...
                    Commands.setTarget(targetIndex); //set the target index to whatever was chosen.
                else
                    Commands.setTarget(targetIndex * -1); //otherwise, set the target index to a negative value
            }
        }
    }

    //chooses a target from two passed arrays of targets
    private int chooseTarget(gameCharacter [] chars, gameCharacter [] mins, int Input) {
        boolean notUsableOnDead = true; //boolean to track what we are displaying.
        if(currentlyTargeting == 2 || currentlyTargeting == -2)
            notUsableOnDead = false;
        battleUI Interface = Game.battle.getInterface();
        gameCharacter [] targetArray = new gameCharacter[8];
        //make an array of size 8 to fit the maximum possible sum of targetable characters

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

        --lowerBound; //decrement lower bound by 1 to account for the fact that it is where we
                      //were inserting rather than the actual lower bound in the above for-loop.

        //here we actually have the user select a target.
        if (Input == 1) { //up case
            if (Selection > 0) //unless we're at the lowest selection value
                --Selection; //decrement selection
            if (Selection < uSelectionView) {
                --lSelectionView;
                --uSelectionView;
            }
        } else if (Input == 2) { //down case
            if (Selection < lowerBound)
                ++Selection;
            if (Selection > lSelectionView) {
                ++lSelectionView;
                ++uSelectionView;
            }
        } else if (Input == 3) { //enter case
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
            }
        }
        if(Input != 3 && Input != 0){
            //we print the targets currently within view. there are multiple cases
            //because realistically, there can be anywhere between 1 and 8 possible targets
            if (lowerBound == 0) { //case for a single target
                Interface.printLeft(targetArray[uSelectionView].getName());
            } else if (lowerBound == 1) { //case for 2 targets
                Interface.printLeft(targetArray[uSelectionView].getName(),
                        targetArray[uSelectionView + 1].getName());
            } else if (lowerBound == 2) { //case for 3 targets
                Interface.printLeft(targetArray[uSelectionView].getName(),
                        targetArray[uSelectionView + 1].getName(),
                        targetArray[uSelectionView + 2].getName());
            } else { //case for 4 or more targets to print.
                Interface.printLeft(targetArray[uSelectionView].getName(),
                        targetArray[uSelectionView + 1].getName(),
                        targetArray[uSelectionView + 2].getName(),
                        targetArray[uSelectionView + 3].getName());
            }

            //print some info about the target.
            Interface.printRight(targetArray[Selection].getName(), //print the selected
                    "HP: " + targetArray[Selection].getHP() + "/" + //target's HP and HP cap
                            targetArray[Selection].getHPCap());

            Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
        }
        return 0; //return 0, signifying that we have not retrieved a valid target yet.
    }

    //selects a skill to use/attacks with weapons/runs
    private combatEffect chooseSkill(int Input) {
        battleUI Interface = Game.battle.getInterface();
        if(State == 0){ //attack/skill/item/flee/wait state
            lowerBound = 4; //this state only has 5 possible selections.
            if (Input == 1) { //up case
                if (Selection > 0) //unless we're at the lowest selection value
                    --Selection; //decrement selection
                if (Selection < uSelectionView) { //if the selection has left our view
                    --uSelectionView;
                    --lSelectionView;
                }
            } else if (Input == 2) { //down case
                if (Selection < lowerBound)
                    ++Selection;
                if (Selection > lSelectionView) {
                    ++uSelectionView;
                    ++lSelectionView;
                }
            } else if (Input == 3) { //enter case
                if (Selection == 0) //attack case
                    return Right; //return right hand weapon
                else if (Selection == 1) { //skill case
                    State = 1; //select primary or secondary class
                } else if (Selection == 2) { //item case
                    State = 4;
                } else if (Selection == 3) { //flee case
                    return new Flee();
                } else if (Selection == 4) { //wait case
                    return new Wait();
                }
            }
            if(Input != 3){
                if (lSelectionView != 4) {
                    Interface.printLeft("Attack", "Skill", "Item", "Flee");
                    Interface.setTextFocus(Selection - uSelectionView);
                } else {
                    Interface.printLeft("Skill", "Item", "Flee", "Wait");
                    Interface.setTextFocus(Selection - uSelectionView);
                }
            }
        }
        else if(State == 1){ //primary or secondary class state state
            lowerBound = 1; //this state only has 2 possible selections.
            Interface.printLeft(primaryClass.getClassName() + " Skills",
                    secondaryClass.getClassName() + " Skills");
            Interface.setTextFocus(Selection - uSelectionView);
            if (Input == 1) { //up case
                if (Selection > 0) //unless we're at the lowest selection value
                    --Selection; //decrement selection
            } else if (Input == 2) { //down case
                if (Selection < lowerBound)
                    ++Selection;
            } else if (Input == 3) { //enter case
                if (Selection == 0) { //Primary class case
                    if (primaryClass != null)
                        State = 2; //select primary class skill
                    else
                        Interface.printRight("No primary class selected.");
                } else if (Selection == 1) { //Secondary class case
                    if (secondaryClass != null)
                        State = 3; //select secondary class skill
                    else
                        Interface.printRight("No secondary class selected.");
                }
            } else if (Input == 0) { //cancel case
                State = 0; //base selection case
            }
        }
        else if(State == 2) { //primary class skill selection state
            if(selectedNode == null)
                selectedNode = primaryClass.getSkillHead();
            lowerBound = primaryClass.getNumSkills() - 1;
            //here we print the skills currently within view. there are actually a good
            //number of cases for this because a class can have between 1 and 8 skills.
            if (lowerBound == 0) { //case for a single skill
                Interface.printLeft(selectedNode.returnData().returnKey());
            } else if (lowerBound == 1) { //case for 2 skills
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey());
            } else if (lowerBound == 2) { //case for 3 skills
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().returnData().returnKey());
            } else { //case for 4 or more skills to print.
                orderedDLLNode botViewNode = selectedNode;
                for(int i = Selection; i < lSelectionView; --i)
                    botViewNode = botViewNode.getNext();
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().getNext().returnData().returnKey());
            }
            Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
            //print some information about the skill in question.
            Interface.printRight(selectedNode.returnData().returnKey(),
                    "Costs " + ((Skill)selectedNode.returnData()).getSPCost() +
                            " SP. (" + SP + "/" + MSP + "remaining)",
                    ((Skill)selectedNode.returnData()).getDescription());

            if (Input == 1) { //up case
                if (Selection > 0) //unless we're at the lowest selection value
                    --Selection; //decrement selection
                if (Selection < uSelectionView) {
                    --lSelectionView;
                    --uSelectionView;
                }
            } else if (Input == 2) { //down case
                if (Selection < lowerBound)
                    ++Selection;
                if (Selection > lSelectionView) {
                    ++lSelectionView;
                    ++uSelectionView;
                }
            } else if (Input == 3) { //enter case
                return ((combatEffect)selectedNode.returnData()); //return the selected skill
            } else if (Input == 0) { //cancel case
                State = 1; //class selection case
                uSelectionView = 0; //set views to 0 and 3 so we're at the
                lSelectionView = 3; //bottom of the new submenu
                Selection = 0; //reset selection as we change menus
                selectedNode = null;
                chooseSkill(1); //call chooseskill with input value 1 to reprint whatever we've cancelled back to.
            }
        }
        else if(State == 3) { //secondary class skill selection state
            if (selectedNode == null)
                selectedNode = secondaryClass.getSkillHead();
            lowerBound = secondaryClass.getNumSkills() - 1;
            //here we print the skills currently within view. there are actually a good
            //number of cases for this because a class can have between 1 and 8 skills.
            if (lowerBound == 0) { //case for a single skill
                Interface.printLeft(selectedNode.returnData().returnKey());
            } else if (lowerBound == 1) { //case for 2 skills
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey());
            } else if (lowerBound == 2) { //case for 3 skills
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().returnData().returnKey());
            } else { //case for 4 or more skills to print.
                orderedDLLNode botViewNode = selectedNode;
                for (int i = Selection; i < lSelectionView; --i)
                    botViewNode = botViewNode.getNext();
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().getNext().returnData().returnKey());
            }
            Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
            //print some information about the skill in question.
            Interface.printRight(selectedNode.returnData().returnKey(),
                    "Costs " + ((Skill) selectedNode.returnData()).getSPCost() +
                            " SP. (" + SP + "/" + MSP + "remaining)",
                    ((Skill) selectedNode.returnData()).getDescription());

            if (Input == 1) { //up case
                if (Selection > 0) //unless we're at the lowest selection value
                    --Selection; //decrement selection
                if (Selection < uSelectionView) {
                    --lSelectionView;
                    --uSelectionView;
                }
            } else if (Input == 2) { //down case
                if (Selection < lowerBound)
                    ++Selection;
                if (Selection > lSelectionView) {
                    ++lSelectionView;
                    ++uSelectionView;
                }
            } else if (Input == 3) { //enter case
                return ((combatEffect) selectedNode.returnData()); //return the selected skill
            } else if (Input == 0) { //cancel case
                State = 1; //class selection case
                uSelectionView = 0; //set views to 0 and 3 so we're at the
                lSelectionView = 3; //bottom of the new submenu
                Selection = 0; //reset selection as we change menus
                selectedNode = null;
                chooseSkill(1); //call chooseskill with input value 1 to reprint whatever we've cancelled back to.
            }
        }
        else if(State == 4) { //items state
            lowerBound = Game.Player.getConsumablesSize() - 1;
            if (selectedNode == null)
                selectedNode = Game.Player.getConsumables(); //ensure that we have an item selected.
            if (lowerBound == 0) { //case for a single item
                Interface.printLeft(selectedNode.returnData().returnKey());
            } else if (lowerBound == 1) { //case for 2 items
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey());
            } else if (lowerBound == 2) { //case for 3 items
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().returnData().returnKey());
            } else { //case for 4 or more items to print.
                orderedDLLNode botViewNode = selectedNode;
                for (int i = Selection; i < lSelectionView; --i)
                    botViewNode = botViewNode.getNext();
                Interface.printLeft(selectedNode.returnData().returnKey(),
                        selectedNode.getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().returnData().returnKey(),
                        selectedNode.getNext().getNext().getNext().returnData().returnKey());
            }
            Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
            //print some information about the item in question.
            Interface.printRight(selectedNode.returnData().returnKey(),
                    "Quantity: " + ((Consumable) selectedNode.returnData()).getQuantity(),
                    ((Consumable) selectedNode.returnData()).getDescription());
            if (Input == 1) { //up case
                if (Selection > 0) //unless we're at the lowest selection value
                    --Selection; //decrement selection
                if (Selection < uSelectionView) {
                    --lSelectionView;
                    --uSelectionView;
                }
            } else if (Input == 2) { //down case
                if (Selection < lowerBound)
                    ++Selection;
                if (Selection > lSelectionView) {
                    ++lSelectionView;
                    ++uSelectionView;
                }
            } else if (Input == 3) { //enter case
                return ((Consumable) selectedNode.returnData()); //return the selected skill
            } else if (Input == 0) { //cancel case
                State = 0; //return to base selection case
                uSelectionView = 0; //set views to 0 and 3 so we're at the
                lSelectionView = 3; //bottom of the new submenu
                Selection = 0; //reset selection as we change menus
            }
        }
        return null;
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
