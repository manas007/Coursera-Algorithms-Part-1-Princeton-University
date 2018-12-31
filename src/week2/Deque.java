/* *****************************************************************************
 *  Name: Manas Tripathi
 *  Date: 12/28/2018
 *  Description: A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure. Create a generic data type Deque that implements the following API:
 **************************************************************************** */

package src.week2;



import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Deque<Item> implements Iterable<Item>
{

    private Node topNode;
    private Node bottomNode;
    private int size;

    private class Node {
        private Node next;
        private Node previous;
        private Item item;

        public Node()
        {
            next = null;
            item = null;
            previous = null;
        }
    }

    public Deque()
    {
        topNode = new Node();
        bottomNode = topNode;
        size = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return size;
    }

    public void addFirst(Item item)         // add the item to the front
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Called addFirst with null");
        }

        if (size == 0)
        {
            topNode.item = item;
            size++;
        }

        else
        {
            Node newTopNode = new Node();
            newTopNode.item = item;
            topNode.next = newTopNode;
            newTopNode.previous = topNode;
            topNode = newTopNode;
            size++;
        }

    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Called addFirst with null");
        }

        if (size == 0)
        {
            bottomNode.item = item;
            bottomNode.next = null;
            bottomNode.previous = null;
            topNode = bottomNode;
            size++;
        }
        else {
            Node newBottomNode = new Node();
            newBottomNode.item = item;
            bottomNode.previous = newBottomNode;
            newBottomNode.next = bottomNode;
            newBottomNode.previous = null;
            bottomNode = newBottomNode;
            size++;
        }

    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (size == 0)
        {
            throw new NoSuchElementException("The Dequeue is empty. Add some data first.");
        }

        Item itemToReturn;

        if (size == 1)
        {
            itemToReturn = topNode.item;
            topNode.previous = null;
            topNode.next = null;
            topNode.item = null;
            bottomNode = topNode;
            size--;
        }
        else
        {
            itemToReturn = topNode.item;
            Node newTopNode = topNode.previous; // changes
            topNode.previous = null;
            topNode.next = null;
            topNode.item = null;
            topNode = newTopNode;
            size--;
        }

        return itemToReturn;

    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (size == 0)
        {
            throw new NoSuchElementException("The Dequeue is empty. Add some data first.");
        }
        Item itemToReturn;

        if(size == 1)
        {
            itemToReturn = bottomNode.item;
            bottomNode.previous = null;
            bottomNode.next = null;
            bottomNode.item = null;
            topNode = bottomNode;
            size--;
        }
        else
        {
            itemToReturn = bottomNode.item;
            Node newBottomNode = bottomNode.next; // chnanges 4 lines
            bottomNode.item = null;
            bottomNode.previous = null;
            bottomNode.next = null;
            bottomNode = newBottomNode;
            size--;
        }
        return itemToReturn;
    }

    public Iterator<Item> iterator()        // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {

        private Node current = topNode;

        @Override
        public boolean hasNext() {
            return !(current.previous == null && current.next == null && Objects.isNull(current.item));
        }

        @Override
        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("No more items in deque");
            }

            Item toReturnItem = current.item;
            current = current.previous;
            return toReturnItem;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        Iterator<Integer> it = deque.iterator();
        while (it.hasNext())
        {
            System.out.println(it.next());
        }
    }
}
