package Characters.Inventory;

import Structures.splayTree;

import java.util.Scanner;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public class Inventory {
    private splayTree [] Items = new splayTree[5];
    //0 = consumables
    //1 = weapons
    //2 = armor
    //3 = accessories
    //4 = quest items

    //interfaces the inventory with the user.
    public void Interface(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an item category to view. '0' will exit the inventory.");
        System.out.println("consumables('1') weapons('2') armor('3') accessories('4') quest items('5')");
        int input = scanner.nextInt();
        scanner.nextLine();
        if(input < 1 || input > 5)
            return; //escape input
        splayTree selected = Items[input - 1]; //select a tree
        
    }


}
