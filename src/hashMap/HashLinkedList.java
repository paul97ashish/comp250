package hashMap;


public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE
	    HashNode<K,V> temp = new HashNode<K,V>(key, value);
        temp.next = head;
        head = temp;
        size = size + 1;

		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE
	    if (this.isEmpty()) {
            return null;
        }
        HashNode<K,V> currNode = this.head;
        while (currNode.next != null) {
            if (currNode.getKey().equals(key)) {
                return currNode;
            }
            
            else {
                currNode = currNode.next;
            }
        }
        
        if (currNode.getKey().equals(key)) {
            return currNode;
        }
        else {
            return null;
        }
        
   
		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE
	    if (this.isEmpty()) {
            return null;
        }
	    
	    HashNode<K,V> currNode = this.head;
	    if (this.size() == 1) {
            this.clear();
        }
        else {
            this.head =currNode.next;
            size = size - 1;
        }
        
        return currNode;

		// ADD CODE ABOVE HERE
		
	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){
		// ADD CODE BELOW HERE
	    if (this.isEmpty()) {
            return null;
        }
	    
	    HashNode<K, V> currNode = getListNode(key);
        if (currNode == null) {
            return null;       
        }
        else if (currNode.equals(getHead()) || size() == 1) {     //nodeToRemove is the head node of the list
            return this.removeFirst();
        }
        else {                                                                  //nodeToRemove is not the head of the list
            HashNode<K, V> prevNode = getPreviousNode(currNode);
            if (prevNode == null) {
                System.out.println("logic error - in getPreviousNode()");
                return null;        
            }
            prevNode.next = currNode.getNext();
            currNode.next = null;
            size-- ;
            return currNode;
        }

		// ADD CODE ABOVE HERE
	}



	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS
	public HashNode<K, V> getHead() {
        return head;
    }
	
	
	HashNode<K, V> getPreviousNode(HashNode<K, V> node) {
        HashNode<K, V> cur = getHead().getNext();
        HashNode<K, V> prevCur = getHead();
        
        while (cur.getKey() != node.getKey() && cur.getNext() != null) {
            cur = cur.getNext();
            prevCur = prevCur.getNext();
        }
        
        if (cur.getKey() == node.getKey()) {        
            return prevCur;
        }
        
        return null;
    }
	   


	//ADD YOUR HELPER METHODS ABOVE THIS


}
