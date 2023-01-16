public class Leaf <E> extends InternalNode
{

    protected E data;
    private Leaf<E> predecessor;

    public Leaf(int primaryKey, int secondaryKey, E data)
    {
        super (primaryKey, secondaryKey, null,null);
        this.data = data;
        this.predecessor = null;


    }

    public E getData() {
        return data;
    }


    public void setData(E data) {
        this.data = data;
    }

    public Leaf<E> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Leaf<E> predecessor) {
        this.predecessor = predecessor;
    }
}
