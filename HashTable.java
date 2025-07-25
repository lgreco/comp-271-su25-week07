/**
 * A simple hasht table is an array of linked lists. In its simplest form, a
 * linked list is represented by its first node. Typically we label this node as
 * "head". Here, however, we'll know it's the first node of the list because it
 * will be placed in an array element. For example, if we have 4 linked lists,
 * we know that the "head" of the third one can be found in position [2] of the
 * underlying array.
 */
public class HashTable<E extends Comparable<E>> {

    /**
     * Underlying array of nodes. Each non empty element of this array is the first
     * node of a linked list.
     */
    private Node[] underlying;

    /** Counts how many places in the underlying array are occupied */
    private int usage;

    /** Counts how many nodes are stored in this hashtable */
    private int totalNodes;

    /**
     * Default size for the underlying array. Users may specify any size, but the
     * default constructor will use this size.
     */
    private static final int DEFAULT_SIZE = 4;

    /** Basic constructor with user-specified size */
    public HashTable(int size) {
        if (size <= 0)
            size = DEFAULT_SIZE;
        this.underlying = new Node[size];
        this.usage = 0;
        this.totalNodes = 0;
    }

    /** Default constructor, passes defauilt size to basic constructor */
    public HashTable() {
        this(DEFAULT_SIZE);
    }

    /**
     * Adds a node, with the specified content, to a linked list in the underlying
     * array.
     * 
     * @param content E The content of a new node, to be placed in the array.
     */
    @SuppressWarnings("unchecked")
    public void add(E content) {
        // Create the new node to add to the hashtable
        Node newNode = new Node<E>(content);
        // Find the target position in the underlying array
        // Hash the content to one of the array positions
        int position = Math.abs(content.hashCode()) % this.underlying.length;
        // Check if underlying array has someone else already at target position
        if (this.underlying[position] == null) {
            // Good news, no one is here
            this.underlying[position] = newNode;
            this.usage += 1;
        } else {
            // Someone is here. We have to do some work.
            // Prepare to append the existing node in the target position
            // below the new node:
            newNode.setNext(this.underlying[position]);
            // Now place the new node in the target position
            this.underlying[position] = newNode;
        }
        // Update the number of nodes
        this.totalNodes += 1;
    } // method add

    /** Constants for toString */
    private static final String LINKED_LIST_HEADER = "\n[ %2d ]: ";
    private static final String EMPTY_LIST_MESSAGE = "null";
    private static final String ARRAY_INFORMATION = "Underlying array usage / length: %d/%d";
    private static final String NODES_INFORMATION = "\nTotal number of nodes: %d";
    private static final String NODE_CONTENT = "%s --> ";

    /** String representationf for the object */
    public String toString() {
        // Initialize the StringBuilder object with basic info
        StringBuilder sb = new StringBuilder(
                String.format(ARRAY_INFORMATION,
                        this.underlying.length, this.usage));
        sb.append(String.format(NODES_INFORMATION, this.totalNodes));
        // Iterate the array
        for (int i = 0; i < underlying.length; i++) {
            sb.append(String.format(LINKED_LIST_HEADER, i));
            Node head = this.underlying[i];
            if (head == null) {
                // message that this position is empty
                sb.append(EMPTY_LIST_MESSAGE);
            } else {
                // traverse the linked list, displaying its elements
                Node cursor = head;
                while (cursor != null) {
                    // update sb
                    sb.append(String.format(NODE_CONTENT, cursor));
                    // move to the next node of the ll
                    cursor = cursor.getNext();
                } // done traversing the linked list
            } // done checking the current position of the underlying array
        } // done iterating the underlying array
        return sb.toString();
    } // method toString

} // class HashTable
