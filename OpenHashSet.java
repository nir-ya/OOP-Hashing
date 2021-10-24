
/**
 * A class representing a hash-set based on chaining,
 *  using a hash table (in this case, array of StringLinkedList objects).
 * Extends SimpleHashSet.
 * The capacity of an OpenHashSet object is the number of buckets (cells in the table).
 */
public class OpenHashSet extends SimpleHashSet {
    
    
    // the hash table used as an internal representation of the hash-set
    private StringLinkedList[] hashTable;
    
    
    /**
     * A default constructor.
     * Constructs a new, empty table with default initial capacity (16),
     *  upper load factor (0.75), and lower load factor (0.25).
     */
    public OpenHashSet() {
        this(DEFAULT_UPPER_LOAD_FACTOR, DEFAULT_LOWER_LOAD_FACTOR);
    }
    
    
    /**
     * constructs a new, empty table with the specified load factors,
     *  and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        hashTable = new StringLinkedList[INITIAL_CAPACITY];
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.currentSize = 0;
        this.currentCapacity = INITIAL_CAPACITY;
    }
    
    
    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values are ignored.
     * The new table has the default values of initial capacity (16),
     *  upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data) {
        this();
        for (String newElement: data) {
            add(newElement);
        }
    }
    
    
    /**
     * Return a hash index for a specified value.
     * i.e the absolute value of (value.hashCode() % the current capacity).
     * @param value the value to call hashCode() on.
     * @return The hash index, as described above.
     */
    private int hashIndex(String value) {
        return value.hashCode() & (this.capacity() -1);
    }
    
    
    /**
     * Returns the bucket (StringLinkedList object) of the specified value.
     *  i.e its cell in the hash table.
     * @param value the value to find its bucket.
     * @return a pointer to the bucket of the input value.
     */
    private StringLinkedList getBucket(String value) {
        return hashTable[hashIndex(value)];
    }
    
    
    /**
     * Make sure the cell in the hash table isn't null, in order to add elements to it.
     *  if null, Creates a new StringLinkedList object in the specified index of the table.
     * @param index the index of a cell in the hash table.
     */
    private void ensureValidBucket(int index) {
        if (hashTable[index] == null) {
            hashTable[index] = new StringLinkedList();
        }
    }
    
    
    /**
     * Add a specified element to the hash-set, if it's not already in it.
     * Resize and rehash if load factor exceeds upper load factor.
     * @param newValue new value to add.
     * @return True iff the the value was added successfully.
     */
    public boolean add(String newValue) {
        
        if (!contains(newValue)) {
            ensureSpaceToAdd();
            currentSize++;
            ensureValidBucket(hashIndex(newValue));
            return hashTable[hashIndex(newValue)].add(newValue);
        }
        return false;
    }
    
    
    /**
     * Look for a certain value in the set, and return true if found.
     * @param searchVal the value to search for.
     * @return true iff the input value was found in the set.
     */
    public boolean contains(String searchVal) {
        return getBucket(searchVal) != null && getBucket(searchVal).contains(searchVal);
    }
    
    
    /**
     * Remove the input element from the set, if found in it.
     * Resize and rehash if load factor is below the lower load factor.
     * @param toDelete the value to remove.
     * @return True iff the element was found and removed.
     */
    public boolean delete(String toDelete) {
        
        StringLinkedList bucket = getBucket(toDelete);
        
        if (bucket != null && bucket.delete(toDelete)) {
            currentSize--;
            if (isSpaceUnused()) {
                resizeAndRehash(capacity() / 2);
            }
            return true;
        }
        return false;
    }
    
    
    /**
     * Reset the Hash Table with a new capacity (given as parameter),
     *  and Rehash, as specified by SimpleHashSet.
     * @param newCapacity The capacity of new hash-table to create.
     */
    protected void resizeAndRehash(int newCapacity) {
    
        StringLinkedList[] oldTable = hashTable;
        hashTable = new StringLinkedList[newCapacity];
        currentCapacity = newCapacity;
        
        for (StringLinkedList cell : oldTable) {
            if (cell != null) {
                for (String element : cell.getList()) {
                    ensureValidBucket(hashIndex(element));
                    hashTable[hashIndex(element)].add(element);
                }
            }
        }
    }
    
}
