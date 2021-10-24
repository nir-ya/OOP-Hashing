import java.util.LinkedList;


/**
 * A wrapper class for LinkedList of Strings, which contains such object,
 *  and delegates methods of it.
 */
public class StringLinkedList {
    
    
    // The wrapped LinkedList object
    private LinkedList<String> stringList = new LinkedList<String>();
    
    
    /**
     * Adds the specified element to the end of the list.
     * @param newElement String.
     * @return true.
     */
    public boolean add(String newElement) {
        return stringList.add(newElement);
    }
    
    
    /**
     * Delete the specified element (its first occurrence) from the list.
     * @param toDelete The string to delete.
     * @return true if the element was found and removed. otherwise, returns false.
     */
    public boolean delete(String toDelete) {
        return stringList.remove(toDelete);
    }
    
    
    /**
     * Returns true if the list contains the specified element.
     * @param searchVal element to search for in the list.
     * @return true if list contains searchVal.
     */
    public boolean contains(String searchVal) {
        return stringList.contains(searchVal);
    }
    
    
    /**
     * Returns stringList - The Linked List represented by this object.
     * @return The member variable stringList (LinkedList of Strings).
     */
    public LinkedList<String> getList() {
        return stringList;
    }
}
