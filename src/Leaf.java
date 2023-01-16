public class Leaf <E>
{
    protected int primaryKey;
    protected int secondaryKey;
    protected E data;

    public Leaf(E data, int primaryKey, int secondaryKey)
    {
        this.data = data;
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
    }

    public E getData() {
        return data;
    }


    public int getPrimaryKey() {
        return primaryKey;
    }


    public int getSecondaryKey() {
        return secondaryKey;
    }


    public void setData(E data) {
        this.data = data;
    }


    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }


    public void setSecondaryKey(int secondaryKey) {
        this.secondaryKey = secondaryKey;
    }
}
