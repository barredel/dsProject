public class TwoThreeTree<E>
{
    private Node<E> root;
    private Node<E> max;

    public TwoThreeTree()
    {
        Node <E> l = new Node<E>(Integer.MIN_VALUE,0, null);
        Node <E> m = new Node<E>(Integer.MAX_VALUE,0, null);
        this.root = new Node<E>(m.getPrimaryKey(), m.getSecondaryKey(), null, l, m);
        l.setParent(root);
        m.setParent(root);
    }

    public Node<E> search(int pk, int sk)
    {
        return search(this.root, pk, sk);
    }

    public Node<E> search(Node<E> x, int pk, int sk)
    {
        if (x.getLeft() == null)
        {
            if (x.getPrimaryKey() == pk && x.getSecondaryKey() == sk)
            {
                return x;
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

    public Node<E> successor(Node<E> x)
    {
        Node<E> successor;
        Node<E> z = x.getParent();
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
            return successor;
        }
        else
        {
            return null;
        }
    }

    public void updateKey (Node<E> x)
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

    public Node<E> predecessor(Node<E> x)
    {
        Node<E> z = x.getParent();
        Node<E> y;
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
            return y;
        else return null;
    }

    public void setChildren(Node<E> x,Node<E> l,Node<E> m,Node<E> r)
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

    public Node<E> insertAndSplit(Node<E> x,Node<E> z)
    {
        Node<E> l = x.getLeft();
        Node<E> m = x.getMiddle();
        Node<E> r = x.getRight();
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
        Node<E> y = new Node<E>(-1, -1, null);
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


    public void insert(Node<E> z)
    {
        Node<E> lz = z;
        Node<E> y = this.root;
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
        Node<E> x = y.getParent();
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
           Node<E> w =  new Node<E>(Integer.MAX_VALUE, 0, null);
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

    public Node<E> borrowOrMerge(Node<E> y)
    {
        Node<E> z = y.getParent();
        if (y == z.getLeft())
        {
            Node<E> x = z.getMiddle();
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
            Node<E> x = z.getLeft();
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
        Node<E> x = z.getMiddle();
        if(x.getRight() != null)
        {
            setChildren(y, x.getRight(), y.getLeft(), null);
            setChildren(x, x.getLeft(), x.getMiddle(), null);
        }
        else
        {
            setChildren(x, x.getLeft(), x.getMiddle(), y.getLeft());
            setChildren(z, z.getLeft(), x, null);
        }
        return z;
    }

    public void delete(Node<E> x)
    {
        Node<E> y = x.getParent();
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

    public Node<E> getMax() {
        return max;
    }
}