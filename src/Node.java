public class Node <E>
{
    private int primaryKey;
    private int secondaryKey;
    private Node<E> left;
    private Node<E> middle;
    private Node<E> right;
    private Node<E> parent;
    private E data;
    private Node<E> predecessor;

    public Node(int primaryKey, int secondaryKey, E data, Node<E> left, Node<E> middle)
    {
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
        this.left = left;
        this.middle = middle;
        this.right = null;
        this.parent = null;
        this.data = data;
        this.predecessor = null;
    }

    public Node(int primaryKey, int secondaryKey, E data) {
        this(primaryKey, secondaryKey, data, null, null);
    }

    public E getData() {
        return data;
    }


    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node<E> predecessor) {
        this.predecessor = predecessor;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public int getSecondaryKey() {
        return secondaryKey;
    }



    public Node<E> getParent() {
        return parent;
    }

    public Node<E> getLeft() {
        return left;
    }

    public Node<E> getMiddle() {
        return middle;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setSecondaryKey(int secondaryKey) {
        this.secondaryKey = secondaryKey;
    }
    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public void setMiddle(Node<E> middle) {
        this.middle = middle;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }
}
