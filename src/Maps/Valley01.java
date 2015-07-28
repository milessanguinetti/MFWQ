package Maps;

import Characters.Inventory.Item;
import Characters.Inventory.Weapons.koboldSlayingSword;
import Characters.Monster;
import Characters.Monsters.Kobold;
import Characters.Monsters.babyKobold;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 7/28/2015.
 */
public class Valley01 extends Map{
    public Valley01(){
        super("valley", 10, 10, 15, null, null, null, null);
        //map name, xbound, ybound, encounter rate, north connection, east connection, south connection, west connection
        Difficulty = Game.Player.getAverageLevel(); //set difficulty modifier to the player's average level.
    }

    @Override
    public Monster[] generateEnemies() {
        switch (Rand.nextInt(3)){
            case 0:{
                return new Monster[]{new babyKobold(), new Kobold(), new babyKobold(), null};
            }
            case 1:{
                return new Monster[]{new Kobold(), new babyKobold(), new Kobold(), null};
            }
            case 2:{
                return new Monster[]{new babyKobold(), new babyKobold(), new babyKobold(), new babyKobold()};
            }
        }
        return new Monster[0]; //at this point, we're really just looking at a bug
    }

    @Override
    public void Build() {
        connectionRooms[3] = Rand.nextInt(yBound)*xBound; //add west entrance
        Rooms[connectionRooms[3]] = new Room();
        connectionRooms[1] = (Rand.nextInt(yBound) + 1)*xBound - 1; //add east entrance
        Rooms[connectionRooms[1]] = new Room();
        for(int i = 0; i < 3; ++i) { //add rooms containing randomized loot.
            Rooms[Rand.nextInt(xBound * yBound)] = new Room(generateLoot());
        }
    }

    @Override
    public Item generateMapSpecificLoot(){
        return new koboldSlayingSword(Difficulty);
    }

    @Override
    public void enterFromOverworld(){
        Enter(4);
    }
}
