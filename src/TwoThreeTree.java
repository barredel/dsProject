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

    public Leaf<E> successor(InternalNode x)
    {
        InternalNode successor;
        InternalNode z = x.getParent();
        while (x==z.getRight() || z.getRight() == null && x == z.getMiddle())
        {
            x = z;
            z = z.getParent();
        }
        if (x == z.getLeft())
        {
            successor = z.getMiddle();
        }
        else
        {
            successor = z.getRight();
        }
        while (successor.getLeft() != null)
        {
            successor = successor.getLeft();
        }

        if (successor.getPrimaryKey()<Integer.MAX_VALUE)
        {
            return (Leaf<E>) successor;
        }
        else
        {
            return null;
        }
    }

    public void updateKey (InternalNode x)
    {
        x.setPrimaryKey(x.getLeft().getPrimaryKey());
        x.setSecondaryKey(x.getLeft().getSecondaryKey());

        if (x.getMiddle() != null)
        {
            x.setPrimaryKey(x.getMiddle().getPrimaryKey());
            x.setSecondaryKey(x.getMiddle().getSecondaryKey());

        }
        if ( x.getRight() != null)
        {
            x.setPrimaryKey(x.getRight().getPrimaryKey());
            x.setSecondaryKey(x.getRight().getSecondaryKey());
        }

    }





}
