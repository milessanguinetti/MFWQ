package Structures;

import java.io.PrintWriter;

/**
 * Created by Miles Sanguinetti on 3/13/15.
 */
public class splayTree extends Structure{
    splayNode root;

    //rotational function. if dir is true, we rotate right. otherwise, we rotate left.
    private splayNode rotate(boolean dir, splayNode toRotate) {
        splayNode temp = toRotate.getBool(!dir);
        toRotate.setBool(!dir, temp.getBool(dir));
        temp.setBool(dir, toRotate);
        return temp;
    }

    //splay function; the function that allows this data structure to function
    //with runtime more on par with a balanced tree than with a common BST
    public splayNode splay(splayNode toSplay, splayNode root) {
        if (root == null)
            return null; //can't splay nothing
        if (root.isEqual(toSplay))
            return root; //can't splay something already at root, either.
        Boolean dir = !root.goesToRight(toSplay); //get whatever direction tosplay goes, relative to
        //root. true is right; we use ! to ensure that we're
        //checking toSplay's direction rather than root's.
        if (root.getBool(dir) == null)
            return root; //nothing in that direction; root will be closest in tree
        Boolean scdir = !root.getBool(dir).goesToRight(toSplay);
        //second comparison value
        if(root.getBool(dir).isEqual(toSplay)) //if these two are equal...
            return rotate(!dir, root); //return rotation
        root.getBool(dir).setBool(scdir, splay(toSplay, root.getBool(dir).getBool(scdir)));
        //set root's l/r child's l/r pointer to the return value of its own splay operation
        if (dir != scdir) {
            if (root.getBool(dir).getBool(scdir) != null)
                root.setBool(dir, rotate(dir, root.getBool(dir)));
            //if the directions are opposites and root's child/grandchild in these two directions is extant
        }   //we set root's child in the first direction to the return value of a rotational operation
        //in the same direction with it.
        else
            root = rotate(!dir, root); //root rotates in the opposite direction
        if(root.getBool(dir) == null)
            return root; //return root if it's child in the first direction is not extant
        return rotate(!dir, root); //otherwise, return a rotation in the opposite of the first direction
    }

    //splay function with a string, so that we can still splay on functions that just take
    //text from the user following a prompt or something in that vein.
    public splayNode splay(String toSplay, splayNode root) {
        if (root == null)
            return null; //can't splay nothing
        if (root.isEqual(toSplay))
            return root; //can't splay something already at root, either.
        Boolean dir = !root.goesToRight(toSplay); //get whatever direction tosplay goes, relative to
        //root. true is right; we use ! to ensure that we're
        //checking toSplay's direction rather than root's.
        if (root.getBool(dir) == null)
            return root; //nothing in that direction.
        Boolean scdir = !root.getBool(dir).goesToRight(toSplay);
        //second comparison value;
        if(root.getBool(dir).isEqual(toSplay)) //if these two are equal...
            return rotate(!dir, root); //return rotation
        root.getBool(dir).setBool(scdir, splay(toSplay, root.getBool(dir).getBool(scdir)));
        //set root's l/r child's l/r pointer to the return value of its own splay operation
        if (dir != scdir) {
            if (root.getBool(dir).getBool(scdir) != null)
                root.setBool(dir, rotate(dir, root.getBool(dir)));
            //if the directions are opposites and root's child/grandchild in these two directions is extant
        }   //we set root's child in the first direction to the return value of a rotational operation
        //in the same direction with it.
        else
            root = rotate(!dir, root); //root rotates in the opposite direction
        if(root.getBool(dir) == null)
            return root; //return root if it's child in the first direction is not extant
        return rotate(!dir, root); //otherwise, return a rotation in the opposite of the first direction
    }

    @Override //display all data contained in the structure
    public void Display(){
        recursiveDisplay(root);
        //call recursive display function with root as parameter
    }

    //recursive method for display.
    public void recursiveDisplay(splayNode root){
        if(root == null)
            return;
        recursiveDisplay(root.getBool(false));
        root.Display();
        recursiveDisplay(root.getBool(true));
        //display root and make recursive display calls
        //with its left and right children as parameters.
    }

    @Override //displays both the current structure and its substructures.
    public void multidimensionalDisplay(int indent){
        multidimensionalDisplay(root, indent);
    }

    //recursive method for multidimensional displays.
    public void multidimensionalDisplay(splayNode root, int indent){
        if(root == null)
            return;
        multidimensionalDisplay(root.getBool(false), indent);
        root.Display(indent);
        if(root.returnStructure() != null) //if root has a subclass...
            root.returnStructure().multidimensionalDisplay(indent + 1); //display it as well
        multidimensionalDisplay(root.getBool(true), indent);
        //display root and make recursive display calls
        //with its left and right children as parameters.
    }

    @Override //writes out to file via towrite parameter
    public void writeOut(PrintWriter toWrite, int tier){
        writeOutRecursively(toWrite, tier, root);
        //call the recursive function to write out the structure's data
    }

    //recursive method for writeout.
    public void writeOutRecursively(PrintWriter toWrite, int tier, splayNode root){
        if(root == null)
            return; //can't write out a null node
        root.writeOut(toWrite, tier); //write root out
        writeOutRecursively(toWrite, tier, root.getBool(false));
        writeOutRecursively(toWrite, tier, root.getBool(true));
        //call function recursively on children.
    }

    @Override //removal method based on string input; 1 signifies success
    //whereas 0 signifies failure.
    public int Remove(String toRemove) {
        Boolean didSucceed = true; //bool to pass to check if we succeeded.
        root = splay(toRemove, root);
        //set root to a splay call on the string that we're removing and root to
        //bring the node to the top of the data structure.
        if(root.isEqual(toRemove)){ //if we successfully brought the node in question to root...
            if(root.Decrement(1) <= 0) { //if the data isn't incrementable or we reach 0...
                if (root.getBool(false) == null)
                    root = root.getBool(true); //if root has no left child, root becomes its right child,
                    //effectively removing the old root.
                else { //otherwise...
                    splayNode temp = root.getBool(true); //temp ptr to root's right
                    root = root.getBool(false); //root becomes its left child, removing the node in question
                    root = splay(toRemove, root); //splay root with key
                    root.setBool(true, temp);
                }   //since toremove is larger than anything to its left, the new root will always have a
                //vacant right child after we set it to root's left.
                return 1; //success
            }
        }
        return 0; //root and toremove are not equal after the splay, toremove was not in the structure
    }

    @Override //retrieval method based on string input. null return value signifies that
    //a node corresponding to the string was not found.
    public Node Retrieve(String toRetrieve){
        root = splay(toRetrieve, root); //attempt to place the node corresponding to toretrieve at
        //root.
        if(root.isEqual(toRetrieve))
            return root; //if this is indeed the node we're looking for, return it.
        return null; //otherwise, return null since it isn't what we're looking for.
    }

    @Override //insertion method with node parameter
    public int Insert(Node nodeToInsert) {
        if(nodeToInsert == null || !(nodeToInsert instanceof splayNode))
            return 0; //can't insert a null reference or a node object that isn't of type BSTnode
        splayNode toInsert = ((splayNode)nodeToInsert);
        if(root == null) //if the tree is empty...
            root = toInsert; //we insert at root.
        else { //otherwise...
            root = splay(toInsert, root);
            if(!(root.returnData() instanceof incrementableData) || !root.isEqual(nodeToInsert)) {
                //if root isn't incrementable or it does not exist in this tree...
                boolean dir = toInsert.goesToRight(root); //directional bool; true is right
                toInsert.setBool(dir, root.getBool(dir)); //toinsert points to root's child in that direction
                root.setBool(dir, null); //root's pointer in that direction becomes null
                toInsert.setBool(!dir, root); //point toinsert's other pointer to root
                root = toInsert; //root now points to toinsert, placing it at the top of the structure
            }
            else{
                    root.Increment(1); //try to increment the data, doing nothing if it's
                                       //non-incrementable data.
            }
        }
        return 1;
        //recursive call; return 1 because the method cannot be expected to fail at this point
    }
}
