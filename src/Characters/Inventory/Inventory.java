package Characters.Inventory;

import Characters.combatEffect;
import Structures.LLLnode;
import Structures.orderedLLL;
import Structures.splayNode;
import Structures.splayTree;

import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public class Inventory {
    private orderedLLL [] Items = new orderedLLL[5];
    //0 = consumables
    //1 = weapons
    //2 = armor
    //3 = accessories
    //4 = quest items

    public Inventory(){
        for(int i = 0; i < 5; ++i){
            Items[i] = new orderedLLL(); //allocate all ordered LLLs.
        }
    }

    //interfaces the inventory with the user.
    public void Interface(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an item category to view. '0' will exit the inventory.");
        System.out.println("consumables('1') weapons('2') armor('3') accessories('4') quest items('5')");
        int input = scanner.nextInt();
        scanner.nextLine();
        if(input < 1 || input > 5)
            return; //escape input
        orderedLLL selected = Items[input - 1]; //select a tree
        selected.Display();
        if(input == 1)
            System.out.println("Would you like to use an item?");
        else if(input < 5 && input > 1)
            System.out.println("Would you like to equip an item?");
        else{
            System.out.println("Enter '0' to exit or '1' to view another category.");
            input = scanner.nextInt();
            scanner.nextLine();
            if(input == 1){
                Interface(); //recursive call
            }
            return;
        }
        System.out.println("Enter '0' to exit, '1' to select an item, or '2' to view another category.");
        input = scanner.nextInt();
        scanner.nextLine();
        if(input == 0){
            return;
        }
        else if(input == 1){
            System.out.println("Enter the item's name or enter '0' to cancel.");
            String strInput = scanner.nextLine();
            if(!strInput.equals("0")){ //only do anything if the user didn't enter 0
                Item Retrieved = ((Item)selected.Retrieve(strInput).returnData());
                if(Retrieved.compareTo(strInput) == 0) {
                    if(Retrieved.Use())
                        selected.Remove(Retrieved.returnKey()); //use and remove from inventory
                }
                else{
                    System.out.println("Item not found. Closest match is:");
                    Retrieved.Display();
                    System.out.println("Use this item? Enter '0' for no or '1' for yes.");
                    input = scanner.nextInt();
                    scanner.nextLine();
                    if(input == 1) {
                        if(Retrieved.Use()); //use and remove from inventory
                            selected.Remove(Retrieved.returnKey());
                    }
                }
            }
            System.out.println("Would you like to do more with your inventory?");
            System.out.println("Enter '0' for no or '1' for yes.");
            input = scanner.nextInt() + 1; //add 1 to match with previous other category call
            scanner.nextLine();
        }
        if(input == 2)
            Interface();
    }

    //returns an array of extant consumable items
    public combatEffect [] getConsumableArray() {
        return Items[0].toArray();
    }

    public int getConsumablesSize(){
        return Items[0].getSize();
    }

    public void Insert(Item toInsert){
        orderedLLL itemType = null;
        if(toInsert instanceof Consumable)
            itemType = Items[0];
        else if(toInsert instanceof Weapon)
            itemType = Items[1];
        else if(toInsert instanceof Armor)
            itemType = Items[2];
        else if(toInsert instanceof Accessory)
            itemType = Items[3];
        else if(toInsert instanceof questItem)
            itemType = Items[4];
        if(itemType == null) //if this isn't actually an item, we can't insert it safely.
            return;
        itemType.Insert(new LLLnode(toInsert));
    }
}
