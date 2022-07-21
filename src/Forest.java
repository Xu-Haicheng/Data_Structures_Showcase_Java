import java.util.HashMap;
import java.util.ArrayList;
import java.util.Locale;

public class Forest {

    // Connects the InternalNode with their specific keywords in the hashmap
    private HashMap<String, InternalNode> forest;
    int treeCount;

    protected class InternalNode {

        String key;
        ArrayList<Post> posts;
        ArrayList<InternalNode> children;

        /**
         * A constructor that initializes the InternalNode instance variables.
         *
         * @param key      Node's key
         */
        public InternalNode(String key, Post post) {
            // TODO
            this.key = key;
            this.posts = new ArrayList<>();
            this.children = new ArrayList<>();
            this.posts.add(post);
        }

        /**
         * A constructor that initializes InternalNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty ArrayList for the node.
         *
         * @param key Node's key
         */
        public InternalNode(String key) {
            // TODO
            this.key = key;
            this.posts = new ArrayList<>();
            this.children = new ArrayList<>();

        }

        /**
         * Return the key
         *
         * @return The key
         */
        public String getKey() {
            // TODO
            return this.key;
        }


        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public ArrayList<Post> getPosts() {
            // TODO
            return this.posts;
        }

        public ArrayList<InternalNode> getChildren() {
            // TODO
            return this.children;
        }

        public void addChildren(InternalNode node) {
            // TODO
            this.children.add(node);
        }

        public void setChildren(ArrayList<InternalNode> children) {
            // TODO
            this.children = children;
        }

        public boolean removeChildren(InternalNode node) {
            // TODO
            if (!this.children.contains(node)) return false;
            else{
                this.children.remove(node);
                return true;
            }
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newPosts New linked list
         */
        public void setPostsList(ArrayList<Post> newPosts) {
            // TODO
            this.posts = newPosts;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewPost(Post data) {
            // TODO
            this.posts.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removePost(Post data) {
            // TODO
            if (!this.posts.contains(data)) return false;
            else {
                this.posts.remove(data);
                return true;
            }
        }
    }

    /**
     * Constructor that initialize the instance variable of the forest
     */
    public Forest() {
        // TODO
        this.forest = new HashMap<>();
    }


    /**
     * Insert the specific key into the forest with InternalNode with empty posts
     *
     * @param key the key of the internal node
     */
    public void insert(String key) {
       // TODO
        if (!this.forest.containsKey(key)) {
            key = key.toLowerCase();
            InternalNode input = new InternalNode(key);
            this.forest.put(key, input);
        }
    }

    /**
     * Insert the specific key and value pairs into the forest
     *
     * @param post insert the post according to the post's key
     */
    public void insert(Post post) {
        // TODO
        if (post.getKeyword()!=null) {
            String key = post.getKeyword().toLowerCase();
            if (!this.forest.containsKey(key)) {
                InternalNode input = new InternalNode(key, post);
                this.forest.put(key, input);
            } else {
                this.forest.get(key).addNewPost(post);
            }
        }
    }

    /**
     * Helper method. Returns the node with the given key. 
     * If the key doesn’t exist in the forest, return null.
     * 
     * @param key querying the internal node with this specific key
     */
    public InternalNode nodeLookUp(String key) {
        // TODO
        key = key.toLowerCase();
        return this.forest.getOrDefault(key, null);
    }

    /**
     * Get the posts that relate to a specific key. If the key does
     * not exist in the forest, throw IllegalArgumentException
     *
     * @param key the key
     * @return the Arraylist of posts
     */
    public ArrayList<Post> getPosts(String key) {
        // TODO
        key = key.toLowerCase();
        if (!this.forest.containsKey(key)) throw new IllegalArgumentException();
        else return this.forest.get(key).getPosts();
    }

    /**
     * add Connection of more than one internal nodes by their keys
     *
     * @param parent the parent node's key
     * @param children the array of children node's keys
     */
    public void addConnection(String parent, String[] children) {
        // TODO
        parent = parent.toLowerCase();
        InternalNode parent_node;
        if (!this.forest.containsKey(parent)){
            this.insert(parent);
        }
        parent_node = this.forest.get(parent);

        for (String c : children){
            String lower = c.toLowerCase();
            InternalNode child;
            if (!this.forest.containsKey(lower)) {
                this.insert(lower);
            }
            child = this.forest.get(lower);
            parent_node.addChildren(child);
        }
    }

    /**
     * add connection for one internal nodes by their keys
     *
     * @param parent the key of the parent key
     * @param child the key of the child key
     */
    public void addConnection(String parent, String child) {
        // TODO
        parent = parent.toLowerCase();
        child = child.toLowerCase();

        if (!this.forest.containsKey(parent)) throw new IllegalArgumentException();
        if (!this.forest.containsKey(child)) throw new IllegalArgumentException();

        InternalNode parent_node = this.forest.get(parent);
        InternalNode child_node = this.forest.get(child);
        parent_node.addChildren(child_node);


    }

    /**
     * query the connection between the internal node by traversing the edge
     * of the forest
     *
     * @param key the initial start point of
     * @return the children of that specific node
     */
    public String[] queryConnection(String key) {
        // TODO
        key = key.toLowerCase();
        if (!this.forest.containsKey(key)) return null;
        else{
            InternalNode node = this.forest.get(key);
            ArrayList<InternalNode> children = node.getChildren();
            int length = children.size();
            String[] output = new String[length];

            for (int i=0; i<length; i++){
                output[i] = children.get(i).getKey();
            }
            return output;
        }
    }
}
