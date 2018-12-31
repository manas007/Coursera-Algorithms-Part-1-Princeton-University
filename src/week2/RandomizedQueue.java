/* *****************************************************************************
 *  Name: Manas Tripathi
 *  Date: 12/30/2018
 *  Description:  A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure.
 */

package src.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>
{


    private Item[] storageArr;
    private int size;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        storageArr = (Item[]) new Object[2];
        size = 0;
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return size;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Null is not a valid item to enqueue");
        }

        if (size == storageArr.length)
        {
            resizeArray(2 * storageArr.length);
        }

        storageArr[size] = item;
        size++;
    }


    public Item dequeue()                    // remove and return a random item
    {
        if (size == 0)
        {
            throw new NoSuchElementException("Queue is empty. Nothing to dequeue");
        }

        if (size > 0 && size == (storageArr.length)/4)
        {
            resizeArray(storageArr.length/2);
        }
        int randomNumber = StdRandom.uniform(size); // 0 and 3

        Item toReturn = storageArr[randomNumber];
        storageArr[randomNumber] = storageArr[size - 1];
        storageArr[size - 1] = null; // to avoid loitering
        size--;
        return  toReturn;
    }

    public Item sample()// return a random item (but do not remove it)
    {
        if (size == 0)
        {
            throw new NoSuchElementException("Queue is empty. Nothing to dequeue");
        }

        int sample = StdRandom.uniform(size);
        return storageArr[sample];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int counter = size;

        @Override
        public boolean hasNext() {
            return counter != 0;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException("Nothing to return");
            }
            int randomInt = StdRandom.uniform(counter--);
            return storageArr[randomInt];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove function is Not supported ");
        }
    }

    private void resizeArray(int newcapacity)
    {
        Item[] resizedArray = (Item[]) new Object[newcapacity];
        for (int i = 0; i < size; i++)
        {
            resizedArray[i] = storageArr[i];
        }
        storageArr = resizedArray;
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.size();
        rq.size();
        rq.enqueue(4);

        Iterator<Integer> it = rq.iterator();
        while (it.hasNext())
        {
            System.out.println(it.next());
        }
    }
}
