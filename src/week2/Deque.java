/* *****************************************************************************
 *  Name: Manas Tripathi
 *  Date: 12/28/2018
 *  Description: A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure. Create a generic data type Deque that implements the following API:
 **************************************************************************** */

package src.week2;


import java.util.NoSuchElementException;

public class Deque {

    private Node topNode;
    private Node bottomNode;
    private int size;

    private class Node {
        private Node next;
        private String item;

        public Node()
        {
            next = null;
            item = null;
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

    public void addFirst(String item)         // add the item to the front
    {
        if(item == null)
        {
            throw new IllegalArgumentException("Called addFirst with null");
        }

        if(size == 0)
        {
            topNode.item = item;
            size++;
        }

        else if(size == 1 && topNode.next == null)
        {
            topNode.item = item;
            topNode.next = bottomNode;
            size++;
        }
        else
        {
            Node newTopNode = new Node();
            newTopNode.item = item;
            newTopNode.next = topNode;
            topNode = newTopNode;
            size++;
        }

    }

    public void addLast(String item)           // add the item to the end
    {
        if(item == null)
        {
            throw new IllegalArgumentException("Called addFirst with null");
        }

        if(size == 0)
        {
            bottomNode.item = item;
            topNode = bottomNode;
            size++;
        }
        else {
            Node oldBottomNode = bottomNode;
            Node newBottomNode = new Node();
            newBottomNode.item = item;
            bottomNode = newBottomNode;
            bottomNode.next = oldBottomNode;
            size++;
        }

    }

    public String removeFirst()                // remove and return the item from the front
    {
        if(size == 0)
        {
            throw new NoSuchElementException("The Dequeue is empty. Add some data first.");
        }
        String itemToReturn = null;

        if(size == 1)
        {
            itemToReturn = topNode.item;
            topNode = bottomNode;
            size--;
        }
        else
        {
            itemToReturn = topNode.item;
            Node newTopNode = topNode.next;
            topNode = null;
            topNode = newTopNode;
            size--;
        }
        return itemToReturn;
    }

    public String removeLast()                 // remove and return the item from the end
    {
        if(size == 0)
        {
            throw new NoSuchElementException("The Dequeue is empty. Add some data first.");
        }

        String itemToReturn = bottomNode.item;
        Node newBottomNode = bottomNode.next;
        bottomNode = null;
        bottomNode = newBottomNode;
        size--;
        return itemToReturn;
    }

    /*public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {

    }
*/
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque testObj = new Deque();
        testObj.addFirst("a");
        testObj.addFirst("b");
        testObj.addFirst("c");
        testObj.addFirst("d");

        System.out.println(testObj.size);

        System.out.println(testObj.removeLast());

        System.out.println(testObj.size());

    }
}
