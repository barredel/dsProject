public class TwoThreeTree<E>
{
    private InternalNode root;

    public TwoThreeTree()
    {
        Leaf <E> l = new Leaf<E>(Integer.MIN_VALUE,0, null);
        Leaf <E> m = new Leaf<E>(Integer.MIN_VALUE,0, null);
        this.root = new InternalNode(m.getPrimaryKey(), m.getSecondaryKey(), l, m);
        l.setParent(root);
        m.setParent(root);
    }

    public Leaf<E> search(int pk, int sk)
    {
        return search(this.root, pk, sk);
    }
    public Leaf<E> search(InternalNode x, int pk, int sk)
    {
        if (x instanceof Leaf)
        {
            if (x.getLeft().getPrimaryKey() == pk && x.getLeft().getSecondaryKey() == sk)
            {
                return (Leaf<E>) x;
            }
            else
            {
                return null;
            }
        }
        if (pk<x.getMiddle().getPrimaryKey()||(pk==x.getMiddle().getPrimaryKey() && sk >= x.getSecondaryKey()))
        {
            return search(x.getLeft(),pk,sk);
        }
        else if (pk<x.getPrimaryKey()||(pk==x.getPrimaryKey() && sk >= x.getSecondaryKey()))
        {
            return search(x.getMiddle(),pk,sk);
        }
        else
        {
            return search(x.getRight(),pk,sk);
        }
    }

    public Leaf<E> predecessor(InternalNode x)
    {
        InternalNode z = x.getParent();
        InternalNode y;
        while(x == z.getLeft())
        {
            x = z;
            z = z.getParent();
        }
        if (x == z.getRight())
            y=z.getMiddle();
        else
            y = z.getLeft();
        while (y.getLeft() != null)
        {
            if (y.getRight() != null)
                y = y.getRight();
            else y = y.getLeft();
        }
        if (y.getPrimaryKey() > Integer.MIN_VALUE)
            return (Leaf<E>)y;
        else return null;
    }

    public void setChildren(InternalNode x,InternalNode l,InternalNode m,InternalNode r)
    {
        x.setLeft(l);
        x.setMiddle(m);
        x.setRight(r);
        l.setParent(x);
        if (m != null)
            m.setParent(x);
        if (r != null)
            r.setParent(x);
        updateKey(x);
    }

    public void insertAndSplit(InternalNode x,InternalNode z)
    {

    }





}
