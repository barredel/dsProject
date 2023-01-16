public class Node
{
    protected Node parent;
    protected int primaryKey;
    protected int secondaryKey;

    public Node (int primaryKey, int secondaryKey)
    {
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
        this.parent = null;
    }


    public int getPrimaryKey() {
        return primaryKey;
    }

    public int getSecondaryKey() {
        return secondaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setSecondaryKey(int secondaryKey) {
        this.secondaryKey = secondaryKey;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
