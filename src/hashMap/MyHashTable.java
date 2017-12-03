package hashMap;

import java.util.ArrayList;
import java.util.Iterator;

class MyHashTable<K, V> {
    /*
     * Number of entries in the HashTable.
     */
    private int entryCount = 0;

    /*
     * Number of buckets. The constructor sets this variable to its initial
     * value, which eventually can get changed by invoking the rehash() method.
     */
    private int numBuckets;

    /**
     * Threshold load factor for rehashing.
     */
    private final double MAX_LOAD_FACTOR = 0.75;

    /**
     * Buckets to store lists of key-value pairs. Traditionally an array is used
     * for the buckets and a linked list is used for the entries within each
     * bucket. We use an Arraylist rather than an array, since the former is
     * simpler to use in Java.
     */

    ArrayList<HashLinkedList<K, V>> buckets;

    /*
     * Constructor.
     * 
     * numBuckets is the initial number of buckets used by this hash table
     */

    MyHashTable(int numBuckets) {

        // ADD YOUR CODE BELOW HERE
        this.numBuckets = numBuckets;
        buckets = new ArrayList<HashLinkedList<K, V>>(numBuckets);
        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new HashLinkedList<K, V>());
        }

        // ADD YOUR CODE ABOVE HERE

    }

    /**
     * Given a key, return the bucket position for the key.
     */
    private int hashFunction(K key) {

        return Math.abs(key.hashCode()) % numBuckets;
    }

    /**
     * Checking if the hash table is empty.
     */

    public boolean isEmpty() {
        if (entryCount == 0)
            return true;
        else
            return (false);
    }

    /**
     * return the number of entries in the hash table.
     */

    public int size() {
        return (entryCount);
    }

    /**
     * Adds a key-value pair to the hash table. If the load factor goes above
     * the MAX_LOAD_FACTOR, then call the rehash() method after inserting.
     * 
     * If there was a previous value for the given key in this hashtable, then
     * overwrite it with new value and return the old value. Otherwise return
     * null.
     */

    public V put(K key, V value) {

        // ADD YOUR CODE BELOW HERE
        int index;

        if (this.getNumBuckets() == 0) {
            HashLinkedList<K, V> newList = new HashLinkedList<K, V>();
            newList.add(key, value);
            buckets.add(newList);
            this.entryCount++;
            this.numBuckets++;

            if ((((double) this.size()) / ((double) this.getNumBuckets())) > this.MAX_LOAD_FACTOR) {
                this.rehash();
            }
        } else {
            index = this.hashFunction(key);

            if (this.isEmpty()) {
                buckets.get(index).add(key, value);
                this.entryCount++;

                if ((((double) this.size()) / ((double) this.getNumBuckets())) > this.MAX_LOAD_FACTOR) {
                    this.rehash();
                }
            } else {
                if (this.containsKey(key)) {
                    V oldValue = this.get(key);
                    if (oldValue == null) {
                        System.out.println("logic error - in containsKey(K), then get(K)");
                        return null;
                    }

                    HashNode<K, V> node = buckets.get(index).getListNode(key);
                    if (node == null) {
                        System.out.println("logic error - in containsKey(K), then getListNode(K)");
                        return null;
                    } else {
                        node.setValue(value);
                    }

                    if ((((double) this.size()) / ((double) this.getNumBuckets())) > this.MAX_LOAD_FACTOR) {
                        this.rehash();
                    }

                    return oldValue;
                } else {
                    buckets.get(index).add(key, value);
                    this.entryCount++;

                    if ((((double) this.size()) / ((double) this.getNumBuckets())) > this.MAX_LOAD_FACTOR) {
                        this.rehash();
                    }
                }
            }
        }

        // ADD YOUR CODE ABOVE HERE
        return null;
    }

    /**
     * Retrieves a value associated with some given key in the hash table.
     * Returns null if the key could not be found in the hash table)
     */
    public V get(K key) {

        // ADD YOUR CODE BELOW HERE
        if (this.getNumBuckets() != 0) {
            if (!this.isEmpty()) {
                if (this.containsKey(key)) {
                    int index = this.hashFunction(key);
                    HashNode<K, V> node = buckets.get(index).getListNode(key);
                    if (node == null) {
                        System.out.println("logic error - in containsKey(K), then getListNode(K)");
                        return null;
                    }
                    else {
                        return node.getValue();
                    }
                }
            }
        }
        

        // ADD YOUR CODE ABOVE HERE
        return null;
    }

    /**
     * Removes a key-value pair from the hash table. Return value associated
     * with the provided key. If the key is not found, return null.
     */
    public V remove(K key) {

        // ADD YOUR CODE BELOW HERE
        if (this.getNumBuckets() == 0) {
            return null;
        }
        else {
            int index = this.hashFunction(key);
            
            if (this.isEmpty()) {
                return null;
            }
            else if (this.size() == 1) {
                if (this.containsKey(key)) {
                    HashNode<K, V> node = buckets.get(index).remove(key);
                    if (node == null) {
                        System.out.println("logic error - in containsKey(K)");
                        return null;
                    }
                    else {
                        this.clear();
                        return node.getValue();
                    }
                }
            }
            else {
                if (this.containsKey(key)) {
                    HashNode<K, V> node = buckets.get(index).remove(key);
                    if (node == null) {
                        System.out.println("logic error - in containsKey(K)");
                        return null;
                    }
                    else {
                        return node.getValue();
                    }
                }
            }
        }
        

        // ADD YOUR CODE ABOVE HERE

        return (null);
    }

    /*
     * This method is used for testing rehash(). Normally one would not provide
     * such a method.
     */

    public int getNumBuckets() {
        return numBuckets;
    }

    /*
     * Returns an iterator for the hash table.
     */

    public MyHashTable<K, V>.HashIterator iterator() {
        return new HashIterator();
    }

    /*
     * Removes all the entries from the hash table, but keeps the number of
     * buckets intact.
     */
    public void clear() {
        for (int ct = 0; ct < buckets.size(); ct++) {
            buckets.get(ct).clear();
        }
        entryCount = 0;
    }

    /**
     * Create a new hash table that has twice the number of buckets.
     */

    public void rehash() {
        // ADD YOUR CODE BELOW HERE
        if (this.getNumBuckets() == 0) {
            System.out.println("Sorry there, there aren't any buckets in here");
            return;
        }
        else {
            int newTableSize = this.numBuckets * 2;
            MyHashTable<K, V> newTable = new MyHashTable<K, V>(newTableSize);
            
            for (HashLinkedList<K, V> list : buckets) {
                if (list.isEmpty()) {
                    continue;
                }
                else {
                    HashNode<K, V> cur = list.getHead();
                    while (cur != null) {
                        newTable.put(cur.getKey(), cur.getValue());
                        cur = cur.next;
                    }
                }
            }
            
            this.entryCount = newTable.size();
            this.numBuckets = newTable.getNumBuckets();
            this.buckets = newTable.buckets;
        }

        // ADD YOUR CODE ABOVE HERE

    }

    /*
     * Checks if the hash table contains the given key. Return true if the hash
     * table has the specified key, and false otherwise.
     */

    public boolean containsKey(K key) {
        int hashValue = hashFunction(key);
        if (buckets.get(hashValue).getListNode(key) == null) {
            return false;
        }
        return true;
    }

    /*
     * return an ArrayList of the keys in the hashtable
     */

    public ArrayList<K> keys() {

        ArrayList<K> listKeys = new ArrayList<K>();

        // ADD YOUR CODE BELOW HERE
        Iterator<HashNode<K, V>> iter = this.iterator();

        while (iter.hasNext()) {
            HashNode<K, V> entry = iter.next();
            listKeys.add(entry.getKey());
        }

        return listKeys;

        // ADD YOUR CODE ABOVE HERE

    }

    /*
     * return an ArrayList of the values in the hashtable
     */
    public ArrayList<V> values() {
        ArrayList<V> listValues = new ArrayList<V>();

        // ADD YOUR CODE BELOW HERE
        Iterator<HashNode<K, V>> iter = this.iterator();

        while (iter.hasNext()) {
            HashNode<K, V> entry = iter.next();
            listValues.add(entry.getValue());
        }

        return listValues;

        // ADD YOUR CODE ABOVE HERE

    }

    @Override
    public String toString() {
        /*
         * Implemented method. You do not need to modify.
         */
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buckets.size(); i++) {
            sb.append("Bucket ");
            sb.append(i);
            sb.append(" has ");
            sb.append(buckets.get(i).size());
            sb.append(" entries.\n");
        }
        sb.append("There are ");
        sb.append(entryCount);
        sb.append(" entries in the hash table altogether.");
        return sb.toString();
    }


    /*
     * Inner class: Iterator for the Hash Table.
     */
    public class HashIterator implements Iterator<HashNode<K, V>> {
        HashLinkedList<K, V> allEntries;

        /**
         * Constructor: make a linkedlist (HashLinkedList) 'allEntries' of all
         * the entries in the hash table
         */
        public HashIterator() {

            // ADD YOUR CODE BELOW HERE
            allEntries = new HashLinkedList<K, V>();

            if (getNumBuckets() == 0 || isEmpty()) {
                return;
            } else {
                for (HashLinkedList<K, V> list : buckets) {
                    if (list.isEmpty()) {
                        continue;
                    } else {
                        HashNode<K, V> curr = list.getHead();
                        while (curr != null) {
                            allEntries.add(curr.getKey(), curr.getValue());
                            curr = curr.next;
                        }
                    }
                }
            }

            // ADD YOUR CODE ABOVE HERE

        }

        // Override
        @Override
        public boolean hasNext() {
            return !allEntries.isEmpty();
        }

        // Override
        @Override
        public HashNode<K, V> next() {
            return allEntries.removeFirst();
        }

        @Override
        public void remove() {
            // not implemented, but must be declared because it is in the
            // Iterator interface

        }
    }

    // helper methods that are need for my code

}
