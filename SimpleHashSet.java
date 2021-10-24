
/**
 * An abstract class implementing SimpleSet. Intended to function as a format for
 *  OpenHashSet and ClosedHashSet classes.
 */
public abstract class SimpleHashSet implements SimpleSet {
    
    
    // CONSTANTS //
    protected static final int INITIAL_CAPACITY = 16;
    protected static final float DEFAULT_LOWER_LOAD_FACTOR = 0.25f;
    protected static final float DEFAULT_UPPER_LOAD_FACTOR = 0.75f;
    
    // Member Variables (common to all sub-classes) //
    protected float upperLoadFactor;
    protected float lowerLoadFactor;
    protected int currentSize;
    protected int currentCapacity;
    
    
    /**
     * Add a specified element to the set if it's not already in it,
     *  as specified by (the super-class) SimpleSet.
     */
    public abstract boolean add(String newValue);
    
    
    /**
     * Look for a specified value in the set,
     *  as specified by (the super-class) SimpleSet.
     */
    public abstract boolean contains(String searchVal);
    
    
    /**
     * Remove the input element from the set,
     *  as specified by (the super-class) SimpleSet.
     */
    public abstract boolean delete(String toDelete);
    
    
    /**
     * Reset the Hash Table with a new capacity (given as parameter),
     *  and Rehash, i.e add all the elements from the old Hash Table to the newly created one,
     *  with a new hash (according to the new capacity).
     * @param newCapacity The new size of Hash Table.
     */
    protected abstract void resizeAndRehash(int newCapacity);
    
    
    /**
     * @return The number of elements currently in the hash-set.
     */
    public int size() {
        return this.currentSize;
    }
    
    
    /**
     * Returns the current capacity of the hash-set.
     * @return The number of spots in the set.
     */
    public int capacity() {
        return currentCapacity;
    }
    
    
    /**
     * Make sure an addition isn't causing the load factor (size / capacity) to exceed the
     *  it's upper limit.
     *  if load factor after addition would exceed the upper load factor, calls resize and rehash
     *   method with double capacity.
     */
    protected void ensureSpaceToAdd() {
        if ((float) (size() + 1) / capacity() > upperLoadFactor) {
            resizeAndRehash(capacity() * 2);
        }
    }
    
    
    /**
     * Return true if the current load factor (size / capacity) is smaller than
     *  the lower load factor.
     * @return true if true load factor is bigger than lowerLoadFactor.
     */
    protected boolean isSpaceUnused() {
        return (float) size() / capacity() < lowerLoadFactor;
    }
}
