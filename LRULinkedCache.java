/**
 * 
 * @author iiokhamafe
 *
 * @param <K> The type of the key
 * @param <V> The type of the value
 */


public class LRULinkedCache<K,V> 
{
	
	
	
	
	/*************
	 *	attributes
	 ************/
	
	/** current number of items in cache */
	private int theSize;
	
	/** The capacity of cache. */
	private int capacity;
	
	/** reference to list header node */
	private CacheNode<K,V> head;
	
	/** reference to list tail node */
	private CacheNode<K,V> tail;
	
		/***************
	 *	constructors
	 **************/

	/**
	 *	return a new, empty cache with a given capacity
	 */
	public LRULinkedCache(int capacity)
	{
		this.capacity=capacity;
		// empty this LinkedList
		clear();
	}


	/**********
	 *	methods
	 *********/

	//main method example
	public static void main(String[] args) {
		LRULinkedCache<Integer, Integer> cache = new LRULinkedCache<Integer, Integer>(4);
		cache.LRUPut(1, 5);
		System.out.println("cache after calling LRUPUT(1,5): " + cache.toString());
		cache.LRUPut(2, 2);
		System.out.println("cache after calling LRUPUT(2,2): " + cache.toString());
		cache.LRUPut(3, 7);
		System.out.println("cache after calling LRUPUT(3,7): " + cache.toString());
		cache.LRUPut(4, 9);
		System.out.println("cache after calling LRUPUT(4,9): " + cache.toString());
		cache.LRUPut(1, 9);
		System.out.println("cache after calling LRUPUT(1,9): " + cache.toString());
		System.out.println("LRUGET(3) returned: " + cache.LRUGet(3));
		System.out.println("cache after calling LRUGET(3): " + cache.toString());
		cache.LRUPut(5, 10);
		System.out.println("cache after calling LRUPUT(5,10): " + cache.toString());
		cache.LRUGet(4);
		System.out.println("LRUGET(4) returned: " + cache.LRUGet(4));
		System.out.println("cache after calling LRUGET(4): " + cache.toString());
		cache.LRUGet(10);
		System.out.println("cache after calling LRUGET(10): " + cache.toString());
		
	}
	
	/**************************************
	 *	methods inherited from class Object
	 *************************************/

	/**
	 *	return a String representation of the LinkedList
	 *
	 *	items are listed in order from beginning to end in comma-delimited fashion
	 */
	public String toString()
	{
		String s = "";
		
		CacheNode<K,V> current= head.next;
		while(current!=tail)
		{
			s+= "("+current.key +"," +current.value+")";
			if (current.next!= tail)
				s+=",";
			current= current.next;
		}
		
		return s;
	}


	/**********************************************
	 *	methods inherited from interface Collection
	 *********************************************/
	
	

	/**
	 *	empty the LRUCache
	 *	size will be set to zero
	 */
	public void clear()
	{
		// reset header node
		head = new CacheNode<K,V>(null,null,null ,null);
		// reset tail node
        tail = new CacheNode<K,V>(null,null,head, null);
        head.next=tail;
        tail.previous=head;
        // reset size to 0
		theSize = 0;
		
		
	}


	/**
	 *	return the number of items in the cache
	 */
	public int size()
	{
		return theSize;
	}


	/**
	 *	return true if the cache is empty
	 */
	public boolean isEmpty()
	{
		return theSize == 0;
	}

		
	
	/**
	 *	This operation returns the value for a given key in the cache. It returns null if the data is not currently in the cache.
	 *  It also moves the data that is accessed to the end of the list and inserts it before tail
	 */
	public V LRUGet(K key)
	{
		
		CacheNode<K,V> theNode = head.next;
		
		//To do: Your implementation goes here
		
		while (theNode != tail) {
			
			if (theNode.key.equals(key)) {
				
				moveCacheNodeToEnd(theNode);
				return theNode.value;
			}
			theNode = theNode.next; // move onto the next node and attempt to do a check on the index once again. 
		}
		return null;
		 
	}
	
	
	/**
	 * puts a new node with key and value in the cache and adds it to the end of the cache
	 * If the cache is full, it removes the first node (The least recently used node)before adding the new node.
	 * If a node with the given key already exists in the cache, it updates the value for the key and moves the node with the key to the
	 * end of the cache
	 * @param key
	 * @param value
	 */
	public void LRUPut(K key, V value)
	{
		//To Do: Your implementation goes here
		CacheNode<K,V> testNode = head.next;
		CacheNode<K,V> tailPrevious = tail.previous;
		CacheNode<K, V> newCachedNode = new CacheNode<K,V>(key, value, tail.previous, tail); 
		tailPrevious.next = newCachedNode; //Connect preceding node to newCachedNode
		tail.previous = newCachedNode;
		theSize++;
		
		/*
		 * All statements previous to while statement operate at O(1) time or constant time. 
		 */
		
		while (testNode != tail) {
			if(newCachedNode.key.equals(testNode.next.key)) { //O(n) traversal through linked list
				newCachedNode.next.value = testNode.value;
			}
			
			
			if (theSize > capacity) {
				//remove firstNode after head
				CacheNode<K,V> firstNode = head.next;
				CacheNode<K,V> nodeAfterFirstNode = firstNode.next;
				head.next = nodeAfterFirstNode; //Effectively set the next node after head to the 2nd node
				nodeAfterFirstNode.previous = head;
				
				theSize--; //Decrement size of LRULinkedList by 1 after removing the node.
			}
			testNode = testNode.next;
		}
			
			
			
		
		
		
	}
	
	
	public void moveCacheNodeToEnd(CacheNode<K,V> theNode) {
		CacheNode<K,V> priorNode = theNode.previous;
		CacheNode<K,V> nextNode = theNode.next;
		priorNode.next = nextNode;
		nextNode.previous = priorNode;
		theSize--;
		
		CacheNode<K,V> nodeBeforeTail = tail.previous;
		theNode.next = tail;
		theNode.previous = nodeBeforeTail;
		nodeBeforeTail.next = theNode;
		tail.previous = theNode;
		theSize++;
		
	}
	
	/**
	 *	nested class ListNode
	 *
	 *	encapsulates the fundamental building block of a LRU cache node
	 *	contains a key and value, as well as references to both the next and previous nodes
	 *	in the cache
	 *K is the type of the key and V is the type of value
	 */
	private static class CacheNode<K,V>
	{

		/*************
		 *	attributes
		 ************/
		
		//TODO: define attributes and constructor for CacheNode
		K key; //the Key item associated with the Value
		V value; //the Value item associated with the Key
		
		CacheNode<K,V> previous; //Reference to previous node in LRU LinkedList
		CacheNode<K,V> next; //Reference to next node in LRU LinkedList
		
		public CacheNode(K theKey, V theValue, CacheNode<K,V> prevNode, CacheNode<K,V> nextNode) {
			key = theKey;
			value = theValue;
			
			previous = prevNode;
			next = nextNode;
		}

	}
	
	
	

}



