public class InternalNode extends Node
{
    private Node left;
    private Node middle;
    private Node right;

    public InternalNode (int primaryKey, int secondaryKey, Node left, Node middle)
    {
        super (primaryKey, secondaryKey);
        this.left = left;
        this.middle = middle;
        this.right = null;
    }

    public InternalNode (int primaryKey, int secondaryKey)
    {
        this(primaryKey,secondaryKey,null,null);
    }

}
