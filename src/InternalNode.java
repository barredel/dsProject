public class InternalNode {
    protected int primaryKey;
    protected int secondaryKey;
    protected InternalNode left;
    protected InternalNode middle;
    protected InternalNode right;
    protected InternalNode parent;

    public InternalNode(int primaryKey, int secondaryKey, InternalNode left, InternalNode middle) {
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
        this.left = left;
        this.middle = middle;
        this.right = null;
        this.parent = null;

    }


    public InternalNode(int primaryKey, int secondaryKey) {
        this(primaryKey, secondaryKey, null, null);
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public int getSecondaryKey() {
        return secondaryKey;
    }



    public InternalNode getParent() {
        return parent;
    }

    public InternalNode getLeft() {
        return left;
    }

    public InternalNode getMiddle() {
        return middle;
    }

    public InternalNode getRight() {
        return right;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setSecondaryKey(int secondaryKey) {
        this.secondaryKey = secondaryKey;
    }
    public void setParent(InternalNode parent) {
        this.parent = parent;
    }

    public void setLeft(InternalNode left) {
        this.left = left;
    }

    public void setMiddle(InternalNode middle) {
        this.middle = middle;
    }

    public void setRight(InternalNode right) {
        this.right = right;
    }

}

