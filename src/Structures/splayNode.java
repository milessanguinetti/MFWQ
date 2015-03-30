package Structures;

/**
 * Created by Miles Sanguinetti on 3/2/15.
 * A node class for the BST structure.
 */
public class splayNode extends Node {
    private splayNode Left;
    private splayNode Right;

    public splayNode() {
    }

    public splayNode(String name) {
        super(name);
    }

    public splayNode(String name, Structure toInsert){ //name for the data and a pre-allocated structure
        super(name, toInsert);                       //reference to add in.
    }

    //returns a directional child reference based on the passed direction.
    //if true, we go right, if false, we left.
    public splayNode getBool(boolean dir) {
        if(dir)
            return Right;
        return Left;
    }

    //sets a directional child reference to the passed splaynode according to
    //the passed bool. right is true, left is false.
    public void setBool(boolean dir, splayNode toSet) {
        if(dir)
            Right = toSet;
        else
            Left = toSet;
    }
}
