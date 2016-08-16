package Characters;

import Characters.Classes.characterClass;
import Characters.Inventory.*;
import Characters.Properties.Neutral;
import Characters.Skills.Flee;
import Characters.Skills.Skill;
import Characters.Skills.Wait;
import Characters.Skills.Passive.passiveSkill;
import Profile.Game;
import Profile.battleUI;
import Profile.experienceNotification;
import Structures.LLLnode;
import Structures.orderedDLLNode;
import Structures.orderedLLL;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/31/15.
 */
public class playerCharacter extends gameCharacter {
    //Data for game logistics
    private String Race;
    private int Level = 1;
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
    private boolean displayingError; //true if the player is displaying an error.
    private int Selection = 0; //the user's current selection
    private int uSelectionView = 0; //upper selection view
    private int lSelectionView = 3; //lower selection view
    private int lowerBound; //the upper bound of whatever menu we're in
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
        if(Race.equals("Faithful") || Race.equals("Homunculus"))
            charType = 1; //male character
        else
            charType = 2; //female character
        expCap = 1000;
        charProperty = new Neutral();
    }

    public String getRace(){
        return Race;
    }

    public String getExp(){
        return "          Experience: " + Exp + "/" + expCap;
    }

    public String getCurrentClassName(){
        if(primaryClass != null)
            return primaryClass.getClassName();
        else
            return "";
    }

    public String getSecondaryClassName(){
        if(secondaryClass != null)
            return secondaryClass.getClassName();
        return "None";
    }

    public String getPassiveName(){
        if(currentPassive != null)
            return currentPassive.getSkillName();
        else
            return "None";
    }

    public orderedLLL getClasses(){
        return Classes;
    }

    public orderedLLL getPassives(){
        return Passives;
    }

    //initializes combat data; resetting state information for player characters.
    @Override
    public void initializeCombatData(){
        setTurn(); //indicate that it is now this character's turn.
        Selection = 0; //reset variables linked to UI selection.
        uSelectionView = 0;
        lSelectionView = 3;
        selectedNode = null;
        State = 0;
        //since most printing occurs via event-driven mechanisms, we take this opportunity to print
        //something before the user enters any input to give them some reference on what to do next
        Game.battle.getInterface().printLeft("Attack", "Skill", "Item", "Flee");
        Game.battle.printDefaultStats();
        Game.battle.getInterface().setTextFocus(0);
    }

    //handles a piece of input entered during a battle.
    @Override
    public void handleInput(KeyEvent toHandle){
        if(displayingError)
            displayingError = false;
        int Input; //variable for storing what amounts to a translated keyevent.
        if(toHandle.getCode() == KeyCode.ENTER || toHandle.getCode() == KeyCode.D) //enter case; input = 3
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
                if(currentlyTargeting != 0 && !displayingError) { //assuming we actually need to print anything...
                    boolean whichSide = currentlyTargeting > 0;
                    chooseTarget(Game.battle.getParty(whichSide), Game.battle.getMinions(whichSide), 1);
                    //utilize the choosetarget method to just print possible targets, as an up command will do
                    //nothing at selection 0.
                }
                else
                    setPlain(); //otherwise, set plain as it is no longer our turn.
            }
        }
        else{ //strictly target selection
            lowerBound = 0; //reset lower bound.
            boolean whichSide = currentlyTargeting > 0;
            int targetIndex = chooseTarget(Game.battle.getParty(whichSide), Game.battle.getMinions(whichSide), Input);
            if(targetIndex != 0){ //if the user hit enter and selected a valid target...
                if(whichSide) //if we're targeting a player...
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

        for (int i = 0; i < 4; ++i) { //add all attackable main units into the targeting array
            if (chars[i] != null) {
                if (chars[i].isAlive() == notUsableOnDead) {
                    //if the character exists and the skill can be used on them
                    targetArray[lowerBound] = chars[i]; //insert into targeting array
                    ++lowerBound; //increment our lower bound
                }
            }
        }
        for (int i = 0; i < 4; ++i) { //add all attackable minions into the targeting array
            if (mins[i] != null) { //this could be done with 1 for loop, but that makes the layout inelegant
                if (mins[i].isAlive() == notUsableOnDead) {
                    //if the character exists and the skill can be used on them
                    targetArray[lowerBound] = mins[i]; //insert minion into targeting array
                    ++lowerBound; //increment our lower bound
                }
            }
        }

        --lowerBound; //decrement lower bound by 1 to account for the fact that it is currently where we
                      //were just inserting rather than the actual lower bound in the above for-loop.

        //here we actually have the user select a target.
        //in any given case, we re-evaluate what we're highlighting in the GUI.
        targetArray[Selection].setPlain();
        if(Input == 0){ //cancel case
            initializeCombatData(); //essentially reset the combat data so that we're back in the original menus
            return 0; //and return.
        } else if (Input == 1) { //up case
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
                        if (Input == 1) {
                            targetArray[Selection].setPlain();
                            setPlain(); //set the character + their target to an unselected graphic state.
                            return l + 1; //return l+1 to essentially have a meaningful
                        }                 //reference to where the target is located.
                        else
                            --Input; //otherwise decrement input by 1 so that we know that
                    }                //we have passed a character and are closer to the input value
                }
            }
            for (int m = 0; m < 4; ++m) {
                if (mins[m] != null) {
                    if (mins[m].isAlive() == notUsableOnDead) {
                        //if the character exists and the skill can be used on them
                        if (Input == 1) {
                            targetArray[Selection].setPlain();
                            setPlain(); //set the character + their target to an unselected graphic state.
                            return m + 5; //return l+5 to essentially have a meaningful
                        }                 //reference to where the target is located; +5 denotes a minion.
                        else
                            --Input; //otherwise decrement input by m so that we know that
                    }                //we have passed a character and are closer to the input value
                }
            }
        }
        if(Input != 3 && Input != 0 && !displayingError){
            //we print the targets currently within view. there are multiple cases
            //because realistically, there can be anywhere between 1 and 8 possible targets
            if(lowerBound == -1){
                Interface.printLeft("This ability has no viable targets!");
                displayingError = true;
                Interface.setTextFocus(0);
            }
            else if (lowerBound == 0) { //case for a single target
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

            //print some info about the target, assuming that there is one.
            if(lowerBound != -1) {
                Interface.printRight(targetArray[Selection].getName(), //print the selected
                        "HP: " + targetArray[Selection].getHP() + "/" + //target's HP and HP cap
                                targetArray[Selection].getHPCap());
                Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
                targetArray[Selection].setTargeted();
            }
        }
        else
            targetArray[Selection].setPlain();
        return 0; //return 0, signifying that we have not retrieved a valid target yet.
    }

    //selects a skill to use/attacks with weapons/runs
    private combatEffect chooseSkill(int Input) {
        battleUI Interface = Game.battle.getInterface();
        Game.battle.printDefaultStats();
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
                    Selection = 0; //reset selection
                    chooseSkill(1); //'up' input will only display available items in this case, which is what we want.
                } else if (Selection == 2) { //item case
                    State = 4;
                    Selection = 0; //reset selection
                    chooseSkill(1); //'up' input will only display available items in this case, which is what we want.
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
            if(primaryClass != null && secondaryClass != null) {
                Interface.printLeft(primaryClass.getClassName() + " Skills",
                        secondaryClass.getClassName() + " Skills");
            }
            else if(primaryClass == null && secondaryClass == null){
                Interface.printLeft("No classes selected.");
                Selection = 0;
                if(Input == 3){
                    Input = 1;
                }
            }
            else if(primaryClass == null){
                Interface.printLeft("No primary class selected.",
                        secondaryClass.getClassName() + " Skills");
                Selection = 1;
                lowerBound = 1;
            }
            else {
                Interface.printLeft(primaryClass.getClassName() + " Skills",
                        "No secondary class selected.");
                Selection = 0;
                lowerBound = 0;
            }
            if (Input == 1) { //up case
                if (Selection > 0) //unless we're at the lowest selection value
                    --Selection; //decrement selection
            } else if (Input == 2) { //down case
                if (Selection < lowerBound)
                    ++Selection;
            } else if (Input == 3) { //enter case
                if (Selection == 0) { //Primary class case
                    if (primaryClass != null) {
                        State = 2; //select primary class skill
                        Selection = 0; //reset selection
                        chooseSkill(1); //'up' input will only display available items in this case, which is what we want.
                    }
                    else {
                        displayingError = true;
                        Interface.printRight("No primary class selected.");
                    }
                } else if (Selection == 1) { //Secondary class case
                    if (secondaryClass != null) {
                        State = 3; //select secondary class skill
                        Selection = 0; //reset selection
                        chooseSkill(1); //'up' input will only display available items in this case, which is what we want.
                    }
                    else {
                        Interface.printRight("No secondary class selected.");
                        displayingError = true;
                    }
                }
            } else if (Input == 0) { //cancel case
                State = 0; //base selection case
                chooseSkill(1); //'up' input will only display available items in this case, which is what we want.
            }
            Interface.setTextFocus(Selection);
        }
        else if(State == 2) { //primary class skill selection state
            if(selectedNode == null)
                selectedNode = primaryClass.getSkillHead();
            lowerBound = primaryClass.getNumSkills() - 1;
            //here we print the skills currently within view. there are actually a good
            //number of cases for this because a class can have between 1 and 8 skills.

            if (Input == 1) { //up case
                if (Selection > 0) { //unless we're at the lowest selection value
                    --Selection; //decrement selection
                    selectedNode = selectedNode.getPrev();
                }
                if (Selection < uSelectionView) {
                    --lSelectionView;
                    --uSelectionView;
                }
            } else if (Input == 2) { //down case
                if (Selection < lowerBound) {
                    ++Selection;
                    selectedNode = selectedNode.getNext();
                }
                if (Selection > lSelectionView) {
                    ++lSelectionView;
                    ++uSelectionView;
                }
            } else if (Input == 3) { //enter case
                if(((combatEffect)selectedNode.returnData()).canUse(this))
                    return ((combatEffect) selectedNode.returnData()); //return the selected skill
                else {
                    Interface.printRight(Name + " does not have enough mana to use this skill!");
                    displayingError = true;
                }
            } else if (Input == 0) { //cancel case
                State = 1; //class selection case
                uSelectionView = 0; //set views to 0 and 3 so we're at the
                lSelectionView = 3; //bottom of the new submenu
                Selection = 0; //reset selection as we change menus
                selectedNode = null;
                chooseSkill(1); //call chooseskill with input value 1 to reprint whatever we've cancelled back to.
            }
            if(Input != 0 && !displayingError){
                if (lowerBound == 0) { //case for a single skill
                    Interface.printLeft(selectedNode.returnData().returnKey());
                } else if (lowerBound == 1) { //case for 2 skills
                    Interface.printLeft(primaryClass.getSkillHead().returnData().returnKey(),
                            primaryClass.getSkillHead().getNext().returnData().returnKey());
                } else if (lowerBound == 2) { //case for 3 skills
                    Interface.printLeft(primaryClass.getSkillHead().returnData().returnKey(),
                            primaryClass.getSkillHead().getNext().returnData().returnKey(),
                            primaryClass.getSkillHead().getNext().getNext().returnData().returnKey());
                } else { //case for 4 or more skills to print.
                    orderedDLLNode topViewNode = selectedNode;
                    for (int i = Selection; i > uSelectionView; --i)
                        topViewNode = topViewNode.getPrev();
                    Interface.printLeft(topViewNode.returnData().returnKey(),
                            topViewNode.getNext().returnData().returnKey(),
                            topViewNode.getNext().getNext().returnData().returnKey(),
                            topViewNode.getNext().getNext().getNext().returnData().returnKey());
                }
                Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
                //print some information about the skill in question.
                Interface.printRight(selectedNode.returnData().returnKey(),
                        "Costs " + ((Skill)selectedNode.returnData()).getSPCost() +
                                " SP. (" + SP + "/" + MSP + " SP remaining)",
                        ((Skill)selectedNode.returnData()).getDescription());
            }
        }
        else if(State == 3) { //secondary class skill selection state
            if (selectedNode == null)
                selectedNode = secondaryClass.getSkillHead();
            lowerBound = secondaryClass.getNumSkills() - 1;
            //here we print the skills currently within view. there are actually a good
            //number of cases for this because a class can have between 1 and 8 skills.
            if (Input == 1) { //up case
                if (Selection > 0) { //unless we're at the lowest selection value
                    --Selection; //decrement selection
                    selectedNode = selectedNode.getPrev();
                }
                if (Selection < uSelectionView) {
                    --lSelectionView;
                    --uSelectionView;
                }
            } else if (Input == 2) { //down case
                if (Selection < lowerBound) {
                    ++Selection;
                    selectedNode = selectedNode.getNext();
                }
                if (Selection > lSelectionView) {
                    ++lSelectionView;
                    ++uSelectionView;
                }
            } else if (Input == 3) { //enter case
                if(((combatEffect)selectedNode.returnData()).canUse(this))
                    return ((combatEffect) selectedNode.returnData()); //return the selected skill
                else {
                    Interface.printRight(Name + " does not have enough mana to use this skill!");
                    displayingError = true;
                }
            } else if (Input == 0) { //cancel case
                State = 1; //class selection case
                uSelectionView = 0; //set views to 0 and 3 so we're at the
                lSelectionView = 3; //bottom of the new submenu
                Selection = 0; //reset selection as we change menus
                selectedNode = null;
                chooseSkill(1); //call chooseskill with input value 1 to reprint whatever we've cancelled back to.
            }
            if(Input != 0 && !displayingError){
                if (lowerBound == 0) { //case for a single skill
                    Interface.printLeft(selectedNode.returnData().returnKey());
                } else if (lowerBound == 1) { //case for 2 skills
                    Interface.printLeft(secondaryClass.getSkillHead().returnData().returnKey(),
                            secondaryClass.getSkillHead().getNext().returnData().returnKey());
                } else if (lowerBound == 2) { //case for 3 skills
                    Interface.printLeft(secondaryClass.getSkillHead().returnData().returnKey(),
                            secondaryClass.getSkillHead().getNext().returnData().returnKey(),
                            secondaryClass.getSkillHead().getNext().getNext().returnData().returnKey());
                } else { //case for 4 or more skills to print.
                    orderedDLLNode topViewNode = selectedNode;
                    for (int i = Selection; i > uSelectionView; --i)
                        topViewNode = topViewNode.getPrev();
                    Interface.printLeft(topViewNode.returnData().returnKey(),
                            topViewNode.getNext().returnData().returnKey(),
                            topViewNode.getNext().getNext().returnData().returnKey(),
                            topViewNode.getNext().getNext().getNext().returnData().returnKey());
                }
                Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
                //print some information about the skill in question.
                Interface.printRight(selectedNode.returnData().returnKey(),
                        "Costs " + ((Skill) selectedNode.returnData()).getSPCost() +
                                " SP. (" + SP + "/" + MSP + " SP remaining)",
                        ((Skill) selectedNode.returnData()).getDescription());
            }
        }
        else if(State == 4) { //items state
            lowerBound = Game.Player.getConsumablesSize() - 1;
            if (selectedNode == null)
                selectedNode = Game.Player.getConsumables(); //ensure that we have an item selected.
            if(lowerBound == -1) {
                Interface.printLeft("You have no consumable items to use!");
                Interface.setTextFocus(0);
                if(Input != 0) //do nothing but return in this case.
                    return null;
            }
            if (Input == 1) { //up case
                if (Selection > 0) { //unless we're at the lowest selection value
                    --Selection; //decrement selection
                    selectedNode = selectedNode.getPrev();
                }
                if (Selection < uSelectionView) {
                    --lSelectionView;
                    --uSelectionView;
                }
            } else if (Input == 2) { //down case
                if (Selection < lowerBound) {
                    ++Selection;
                    selectedNode = selectedNode.getNext();
                }
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
                chooseSkill(1); //'up' input will only display available items in this case, which is what we want.
            }
            if(Input != 0 && Input != 3 && !displayingError){
                if (lowerBound == 0) { //case for a single item
                    Interface.printLeft(selectedNode.returnData().returnKey());
                } else if (lowerBound == 1) { //case for 2 items
                    Interface.printLeft(Game.Player.getConsumables().returnData().returnKey(),
                            Game.Player.getConsumables().getNext().returnData().returnKey());
                } else if (lowerBound == 2) { //case for 3 items
                    Interface.printLeft(Game.Player.getConsumables().returnData().returnKey(),
                            Game.Player.getConsumables().getNext().returnData().returnKey(),
                            Game.Player.getConsumables().getNext().getNext().returnData().returnKey());
                } else { //case for 4 or more items to print.
                    orderedDLLNode topViewNode = selectedNode;
                    for (int i = Selection; i > uSelectionView; --i)
                        topViewNode = topViewNode.getPrev();
                    Interface.printLeft(topViewNode.returnData().returnKey(),
                            topViewNode.getNext().returnData().returnKey(),
                            topViewNode.getNext().getNext().returnData().returnKey(),
                            topViewNode.getNext().getNext().getNext().returnData().returnKey());
                }
                if(lowerBound != -1) { //in any cases where we actually print anything...
                    Interface.setTextFocus(Selection - uSelectionView); //make whatever is selected bold
                    //print some information about the item in question.
                    Interface.printRight(selectedNode.returnData().returnKey(),
                            "Quantity: " + ((Consumable) selectedNode.returnData()).getQuantity(),
                            ((Consumable) selectedNode.returnData()).getDescription());
                }
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
        if(toAdd != null)
            Classes.Insert(new LLLnode(toAdd));
    }

    //add a passive skill to the ordered LLL
    public void addPassive(passiveSkill toAdd){
        if(toAdd != null) {
            LLLnode temp = new LLLnode(toAdd);
            Passives.Insert(temp);
        }
    }

    public void Ding(int exp, int jexp){
        if((Exp + exp) >= expCap){ //if we've received enough exp to level...
            experienceNotification.queueExpEvent(Name + " gained a level! " + Name + "'s stats increased!",
                    (1f*Exp)/expCap, 1f, true, Level, primaryClass.getJlevel());
            exp -= (expCap - Exp);
            ++Level;
            expCap += Math.round(expCap*0.2); //exp cap goes up by 20%
            Exp = 0; //reset Exp to 0 now that we've leveled.
            baseStatGains(); //increase base stats.
            if(primaryClass != null)
                primaryClass.baseDing(this, Level); //gain auxilary class stat boosts
            fullyHeal();
            Ding(exp, 0); //recursive call; jexp == 0 so that we don't gain it twice.
        }
        else if(exp > 0){
            experienceNotification.queueExpEvent(Name + " gained " + exp + " exp!",
                    (1f*Exp)/expCap, (1f*exp + Exp)/expCap, true, Level, primaryClass.getJlevel());
            Exp += exp; //otherwise, we just add it to our exp.
        }
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

    public void setPrimaryClass(characterClass primaryclass) {
        if(primaryclass == secondaryClass)
            secondaryClass = null;
        primaryClass = primaryclass;
    }

    public boolean setSecondaryClass(characterClass secondaryclass) {
        if(secondaryclass != primaryClass) {
            secondaryClass = secondaryclass;
            return true;
        }
        return false;
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
    public Item Loot(){
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
        return null;
    }

    @Override
    public boolean hasTwoHandedWeapon() {
        if(Right == Left)
            return true;
        return false;
    }

    public int getLevel(){
        return Level;
    }

    public String getArmorName(){
        if(Armor1 == null)
            return "None";
        else
            return Armor1.returnKey();
    }

    public String getAccessoryName(int which){
        if(which == 1){
            if(Accessory1 == null)
                return "None";
            else
                return Accessory1.returnKey();
        }
        else if(which == 2){
            if(Accessory2 == null)
                return "None";
            else
                return Accessory2.returnKey();
        }
        return "";
    }

    public String getWeaponName(int which){
        if(which == 1){
            if(Right == null)
                return "None";
            else
                return Right.returnKey();
        }
        else if(which == 2){
            if(Left == null)
                return "None";
            else
                return Left.returnKey();
        }
        return "";
    }

    public rotarySelectionPane getSkillPane(){
        return new rotarySelectionPane(getPassives().Peek());
    }

    public rotarySelectionPane getPrimaryClassesPane(){
        return new rotarySelectionPane(getClasses().Peek(), true);
    }

    public rotarySelectionPane getSecondaryClassesPane(){
        return new rotarySelectionPane(getClasses().Peek(), false);
    }
}
