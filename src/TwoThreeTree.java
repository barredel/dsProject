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





}
