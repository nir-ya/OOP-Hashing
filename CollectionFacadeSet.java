
/**
 * Wraps an underlying Collection and serves to both simplify its API,
 *  and give it a common type with the implemented SimpleHash Sets.
 *  Delegates some methods of the collection.
 */
public class CollectionFacadeSet implements SimpleSet {
    
    
    // The collection wrapped by the class (initialized in the constructor)
    private java.util.Collection<java.lang.String> collection;
    
    
    
    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection The Collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
        this.collection = collection;
    }
    
    
    /**
     * Add a specified element to the collection if it's not already in it.
     * @param newValue New value to add to the collection.
     * @return False iff newValue already exists in the collection.
     */
    public boolean add(String newValue) {
        return !this.contains(newValue) && collection.add(newValue);
    }
    
    
    /**
     * Look for a specified value in the collection.
     * @return True iff searchVal is found in the collection.
     */
    public boolean contains(String searchVal) {
        return collection.contains(searchVal);
    }
    
    
    /**
     * Remove the input element from the collection (if exists in it).
     */
    public boolean delete(String toDelete) {
        return collection.remove(toDelete);
    }
    
    
    /**
     * @return The number of elements currently in the collection.
     */
    public int size() {
        return collection.size();
    }
}
