
/**
 * A class representing a hash-set based on closed-hashing with quadratic probing.
 * Extends SimpleHashSet.
 */
public class ClosedHashSet extends SimpleHashSet {
    
    
    // A flag marking the spot of a deleted element
    private static final String DELETED_ELEMENT_MARK = new String();
    
    // A constant marking a valid spot wasn't found yet by 'findSpot()' method
    private static final int SPOT_NOT_FOUND = -1;
    
    // the hash table, used as an internal representation of the hash-set
    private String[] hashTable;
    
    
    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     *  upper load factor (0.75), and lower load factor (0.25).
     */
    public ClosedHashSet() {
        this(DEFAULT_UPPER_LOAD_FACTOR, DEFAULT_LOWER_LOAD_FACTOR);
    }
    
    
    /**
     * Constructs a new, empty table with the specified load factors,
     *  and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        hashTable = new String[INITIAL_CAPACITY];
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.currentSize = 0;
        this.currentCapacity = INITIAL_CAPACITY;
    }
    
    
    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values are ignored. The new table has the default values of initial capacity (16),
     *  upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data) {
        this();
        for (String newElement: data) {
            add(newElement);
        }
    }
    
    
    /**
     * Return a hash index for a value, using quadratic probing.
     * i.e an index of a cell in the hash table, calculated as a function of
     *  the table's size, the hash code of the value, and the number attempts to find it.
     * @param value the value to call hashCode() on.
     * @param i The number of attempts to probe further to find an empty spot for the value.
     * @return The hash index, computed by the formula below.
     */
    private int hashIndex(String value, int i) {
        return (value.hashCode() + ((i + i*i) / 2)) & (capacity() - 1);
    }
    
    
    /**
     * Find a valid spot in the table for a specified new value.
     * i.e find the first index in the table, which fits the value's hash,
     *  and is null or contains a deleted element flag.
     *  if the same value is found during the search, return its index.
     * @param value the value to find a spot for.
     * @return a valid spot in the table, or the value's index (if already exists in the set),
     *          as specified above.
     */
    private int findSpot(String value) {
        int spot = SPOT_NOT_FOUND;
        
        for (int i = 0; i < capacity(); i++) {
            int currentHash = hashIndex(value, i);
            String candidate = hashTable[currentHash];
            
            // if this cell contained a deleted element (and we haven't found an available spot yet),
            //  keep it's index, and keep searching to ensure the value isn't already in the set.
            if (candidate == DELETED_ELEMENT_MARK && spot == SPOT_NOT_FOUND) {
                spot = currentHash;
            } else if ((candidate == null && spot == SPOT_NOT_FOUND) ||
                       (candidate.equals(value) && candidate != DELETED_ELEMENT_MARK)) {
                spot = currentHash;
                break;
            }
        }
        return spot;
    }
    
    
    /**
     * Return true if the specified spot in the table is empty (i.e null or deleted).
     * Otherwise, return false.
     * @param spot an index of a cell in the hash table.
     * @return true iff the specified spot is empty.
     */
    private boolean isSpotAvailable(int spot) {
        return hashTable[spot] == null || hashTable[spot] == DELETED_ELEMENT_MARK;
    }
    
    
    /**
     * Add a specified element to the hash-set, if it's not already in it.
     * Resize and rehash if load factor exceeds upper load factor.
     * @param newValue new value to add.
     * @return True iff the the value was added successfully.
     */
    public boolean add(String newValue) {
        
        int spot = findSpot(newValue);
        int capacityBeforeAttempt = capacity();
        
        if (isSpotAvailable(spot)) {
            ensureSpaceToAdd();
            if (this.capacity() > capacityBeforeAttempt) spot = findSpot(newValue);
            hashTable[spot] = newValue;
            currentSize++;
            return true;
        }
        return false;
    }
    
    
    /**
     * Look for a certain value in the set, and return true if found.
     * @param searchVal the value to search for.
     * @return true iff the input value was found in the set.
     */
    public boolean contains(String searchVal) {
        String toCompare = hashTable[findSpot(searchVal)];
        if (toCompare != null) {
            return toCompare != DELETED_ELEMENT_MARK && toCompare.equals(searchVal);
        }
        return false;
    }
    
    
    /**
     * Remove the input element from the set, if found in it.
     * Resize and rehash if load factor is below the lower load factor.
     * @param toDelete the value to remove.
     * @return True iff the element was found and removed.
     */
    public boolean delete(String toDelete) {
        int spotToCheck = findSpot(toDelete);
        
        if (!isSpotAvailable(spotToCheck)) {
            hashTable[spotToCheck] = DELETED_ELEMENT_MARK;
            currentSize--;
            if (isSpaceUnused()) resizeAndRehash(capacity() / 2);
            return true;
        }
        return false;
    }
    
    
    /**
     * Add a specified value to the set (to the hash table), without checking for duplicates.
     * @param value the value to add.
     */
    private void rehashHelper(String value) {
        
        for (int i = 0; i < capacity(); i++) {
            int currentHash = hashIndex(value, i);
            
            if (hashTable[currentHash] == null) {
                hashTable[currentHash] = value;
                break;
            }
        }
    }
    
    
    /**
     * Reset the Hash Table with a new capacity (given as parameter),
     *  and Rehash, as specified by SimpleHashSet.
     * @param newCapacity The capacity of new hash-table to create.
     */
    protected void resizeAndRehash(int newCapacity) {
        
        String[] oldTable = hashTable;
        hashTable = new String[newCapacity];
        currentCapacity = newCapacity;
        
        for (String value : oldTable) {
            if (value != null && value != DELETED_ELEMENT_MARK) {
                rehashHelper(value);
            }
        }
    }
    
}
