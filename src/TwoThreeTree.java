public class TwoThreeTree<E>
{
    private InternalNode root;
    private Leaf<E> max;

    public TwoThreeTree()
    {
        Leaf <E> l = new Leaf<E>(Integer.MIN_VALUE,0, null);
        Leaf <E> m = new Leaf<E>(Integer.MAX_VALUE,0, null);
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
            if (x.getPrimaryKey() == pk && x.getSecondaryKey() == sk)
            {
                return (Leaf<E>) x;
            }
            else
            {
                return null;
            }
        }
        if (pk<x.getLeft().getPrimaryKey()||
                (pk==x.getLeft().getPrimaryKey() && sk >= x.getLeft().getSecondaryKey()))
        {
            return search(x.getLeft(),pk,sk);
        }
        else if (pk<x.getMiddle().getPrimaryKey()||
                (pk==x.getMiddle().getPrimaryKey() && sk >= x.getMiddle().getSecondaryKey()))
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
        if (x.getRight() != null)
        {
            x.setPrimaryKey(x.getRight().getPrimaryKey());
            x.setSecondaryKey(x.getRight().getSecondaryKey());
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
            else y = y.getMiddle();
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

    public InternalNode insertAndSplit(InternalNode x,InternalNode z)
    {
        InternalNode l = x.getLeft();
        InternalNode m = x.getMiddle();
        InternalNode r = x.getRight();
        if (r == null) {
            if (z.getPrimaryKey() < l.getPrimaryKey() ||
                    (z.getPrimaryKey() == l.getPrimaryKey() && z.getSecondaryKey() > l.getSecondaryKey()))
            {
                setChildren(x, z, l, m);
            }
            else if (z.getPrimaryKey() < m.getPrimaryKey() ||
                    (z.getPrimaryKey() == m.getPrimaryKey() && z.getSecondaryKey() > m.getSecondaryKey()))
            {
                setChildren(x, l, z, m);
            }
            else setChildren(x, l, m, z);
            return null;
        }
        InternalNode y = new InternalNode(-1, -1);
        if (z.getPrimaryKey() < l.getPrimaryKey() ||
                (z.getPrimaryKey() == l.getPrimaryKey() && z.getSecondaryKey() > l.getSecondaryKey()))
        {
            setChildren(x, z, l, null);
            setChildren(y, m, r, null);
        }
        else if (z.getPrimaryKey() < m.getPrimaryKey() ||
                (z.getPrimaryKey() == m.getPrimaryKey() && z.getSecondaryKey() > m.getSecondaryKey()))
        {
            setChildren(x, l, z, null);
            setChildren(y, m, r, null);
        }
        else if (z.getPrimaryKey() < r.getPrimaryKey() ||
                (z.getPrimaryKey() == r.getPrimaryKey() && z.getSecondaryKey() > r.getSecondaryKey()))
        {
            setChildren(x, l, m, null);
            setChildren(y, z, r, null);
        }
        else
        {
            setChildren(x, l, m, null);
            setChildren(y, r, z, null);
        }
        return y;
    }


    public void insert(InternalNode z)
    {
        Leaf<E> lz =(Leaf<E>)z;
        InternalNode y = this.root;
       while (y.getLeft()!= null)
       {
           if ( z.getPrimaryKey() < y.getLeft().getPrimaryKey() ||
                   z.getPrimaryKey() == y.getLeft().getPrimaryKey() &&
                           z.getSecondaryKey() >= y.getLeft().getSecondaryKey())
           {
               y = y.getLeft();
           }
           else if( z.getPrimaryKey() < y.getMiddle().getPrimaryKey() ||
                        z.getPrimaryKey() == y.getMiddle().getPrimaryKey() &&
                           z.getSecondaryKey() >= y.getMiddle().getSecondaryKey())
           {
               y = y.getMiddle();
           }

           else
           {
               y = y.getRight();
           }

       }
       InternalNode x = y.getParent();
       z = insertAndSplit(x,z);
       while (x != this.root)
       {
           x = x.getParent();
           if (z != null)
               z = insertAndSplit(x,z);
           else
               updateKey(x);
       }
       if (z != null)
       {
          InternalNode w =  new InternalNode(Integer.MAX_VALUE, 0);
          setChildren(w,x,z,null);
          this.root = w;
       }
        lz.setPredecessor((predecessor(lz)));
        if (successor(lz)==null)
        {
            this.max = lz;
        }
        else
        {
            successor(lz).setPredecessor(lz);
        }
    }

    public InternalNode borrowOrMerge(InternalNode y)
    {
        InternalNode z = y.getParent();
        if (y == z.getLeft())
        {
            InternalNode x = z.getMiddle();
            if (x.getRight() != null)
            {
                setChildren(y, y.getLeft(), x.getLeft(), null);
                setChildren(x, x.getMiddle(), x.getRight(), null);
            }
            else
            {
                setChildren(x, y.getLeft(), x.getLeft(), x.getMiddle());
                setChildren(z, x, z.getRight(), null);
            }
            return z;
        }
        else if (y == z.getMiddle())
        {
            InternalNode x = z.getLeft();
            if (x.getRight() != null)
            {
                setChildren(y, x.getRight(), y.getLeft(), null);
                setChildren(x, x.getLeft(), x.getMiddle(), null);
            }
            else
            {
                setChildren(x, x.getLeft(), x.getMiddle(), y.getLeft());
                setChildren(z, x, z.getRight(), null);
            }
            return z;
        }
        InternalNode x = z.getMiddle();
        if(x.getRight() != null)
        {
            setChildren(y, x.getRight(), y.getLeft(), null);
            setChildren(x, x.getLeft(), x.getMiddle(), null);
        }
        else
        {
            setChildren(x, x.getLeft(), x.getMiddle(), y.getLeft());
            setChildren(z, z.getRight(), x, null);
        }
        return z;
    }

    public void delete(InternalNode x)
    {
        InternalNode y = x.getParent();
        if (successor(x) == null)
        {
            this.max = predecessor(x);
        }
        else
        {
            successor(x).setPredecessor(predecessor(x));
        }
        if (x == y.getLeft())
        {
            setChildren(y, y.getMiddle(), y.getRight(), null);
        }
        else if (x == y.getMiddle())
        {
            setChildren(y, y.getLeft(), y.getRight(), null);
        }
        else setChildren(y, y.getLeft(), y.getMiddle(), null);
        while(y != null)
        {
            if(y.getMiddle() == null)
            {
                if (y != this.root)
                {
                    y = borrowOrMerge(y);
                }
                else
                {
                    this.root = y.getLeft();
                    y.getLeft().setParent(null);
                    return;
                }
            }
            else
            {
                updateKey(y);
                y = y.getParent();
            }
        }
    }

    public Leaf<E> getMax() {
        return max;
    }
}