import java.time.LocalDate;
import java.util.*;

public class PiazzaExchange {

    String courseID;
    Instructor instructor;
    HashMap<String,User> users;
    HashMap<String, ArrayList<Post>> posts;
    PriorityQueue<Post> unanswered;
    String status;
    boolean selfEnroll;
    private Forest keywordForest;

    private static final String STATS_STRING = "%s submitted %d posts, answered %d posts, received %d endorsements\n";

    /**
     * Constructor of the PiazzaExchange.
     *
     * @param instructor the instructor of this class
     * @param courseID The course ID
     * @param selfEnroll whether the class allow self enrolling or not.
     */
    public PiazzaExchange(Instructor instructor, String courseID, boolean selfEnroll) {
        // TODO
        this.courseID = courseID;
        this.instructor = instructor;
        this.users = new HashMap<>();
        this.unanswered = new PriorityQueue<>(Comparator.reverseOrder());
        this.status = "inactive";
        this.selfEnroll = selfEnroll;
        this.keywordForest = new Forest();
        this.initializeForest();

        this.posts = new HashMap<>();

        this.users.put(instructor.PID,instructor);
        instructor.courses.put(this.courseID,this);

    }

    //is there a reason why we don't combine these two constructors?
    //by default, selfEnroll is false, and we're setting DSC30 as default courseID

    /**
     * Constructor of the PiazzaExchange with a roster of user.
     *
     * @param instructor the instructor who create this piazza
     * @param roster the list of Users that will be included in this piazza
     */
    public PiazzaExchange(Instructor instructor, ArrayList<User> roster) {
        // TODO
        this.courseID = "DSC30";
        this.instructor = instructor;
        this.users = new HashMap<>();
        this.unanswered = new PriorityQueue<>(Comparator.reverseOrder());
        this.status = "inactive";
        this.selfEnroll = false;
        this.keywordForest = new Forest();
        this.initializeForest();

        this.posts = new HashMap<>();

        for(User u : roster){
            this.users.put(u.PID,u);
        }

        for (User u : users.values()){
            u.courses.put(this.courseID,this);
        }
    }

    public Forest getKeywordForest() {
        // TODO
        return this.keywordForest;
    }

    /**
     * Query for the top two endorsed posts
     *
     * @return two posts that has the highest endorsed
     */
    public Post[] computeTopTwoEndorsedPosts() {
        // TODO

        ArrayList<Post> post_arr = new ArrayList<>();
        for (ArrayList<Post> inner : this.posts.values()) {
            post_arr.addAll(inner);
        }

        Post p0 = post_arr.get(0);
        Post p1 = null;
        for (int i = 1; i<this.posts.size(); i++){
            if (post_arr.get(i).endorsementCount > p0.endorsementCount){
                p1 = p0;
                p0 = post_arr.get(i);
            }
            else if (i==1 || post_arr.get(i).endorsementCount > p1.endorsementCount){
                p1 = post_arr.get(i);
            }
        }

        Post[] output = new Post[2];
        output[0] = p0;
        output[1] = p1;
        return output;
    }

    
    /* helper method for getTopStudentContributions() */

    public int getStudentContributions(User u) {
        return u.numOfPostSubmitted + u.numOfPostsAnswered + u.numOfEndorsement;
    }

    /**
     * get recent-30 day stats(including today), where index i corresponds to ith day away from current day
     *
     * @return integer array with the daily post status
     */
    public int[] computeDailyPostStats() { // use until
        // TODO
        int[] output = new int[30];
        LocalDate today = LocalDate.now();
        for (ArrayList<Post> inner : this.posts.values()) {
            for (Post temp : inner) {
                int index = temp.date.until(today).getDays();
                if (temp.date.until(today).getMonths() > 0) continue;
                if (index <= 29) output[index]++;
            }
        }
        return output;
    }

    /**
     * get the month post stats for the last 12 month
     *
     * @return integer array that indicates the monthly status.
     */
    public int[] computeMonthlyPostStats(){ // use until
        // TODO
        int[] output = new int[12];
        LocalDate today = LocalDate.now();

        for (ArrayList<Post> inner : this.posts.values()) {
            for (Post temp : inner) {

                int index = temp.date.until(today).getMonths();
                if (index < 11) output[index]++;
                }
            }
        return output;
    }

    /**
     * Activate the pizza. This action should be done by instructor only.
     *
     * @param u the instructor who wish to activate the class
     * @return successfulness of the action call
     */
    public boolean activatePiazza(User u){
        // TODO
        if (!(u == this.instructor) || this.status.equals("active")) return false;
        else{
            this.status = "active";
            return true;
        }
    }

    /**
     * Activate the pizza. This action should be done by instructor only.
     *
     * @param u the instructor who wish to activate the class
     * @return successfulness of the action call
     */
    public boolean deactivatePiazza(User u){
        // TODO
        if (!(u == this.instructor) || this.status.equals("inactive")) return false;
        else{
            this.status = "inactive";
            this.selfEnroll = false;
            return true;
        }
    }

    /**
     * Enroll the user to this PiazzaExchange. If the self enroll is disabled, only
     * instructor and tutor can request a new enrollment action.
     *
     * @param requester the requester of enrollment
     * @param u the user to enroll
     * @return successfulness of the action call
     */
    public boolean enrollUserToDatabase(User requester, User u){
        // TODO
        if (u instanceof Instructor && requester instanceof Instructor){
            this.users.put(u.PID,u);
            return true;
        }
        if (requester instanceof Instructor ){
            if (this.users.containsKey(u.PID)) return false;
            this.users.put(u.PID,u);
            return true;
        }
        if (this.users.containsKey(u.PID)) return false;
        if (this.status.equals("inactive")) return false;
        if (requester instanceof Tutor){
            this.users.put(u.PID,u);
            return true;
        }
        if (this.selfEnroll){
            this.users.put(u.PID,u);
            return true;
        }

        return false;
    }

    /**
     * Enroll this user to PiazzaExchange
     *
     * @param u the user to enroll
     * @return successfulness of the action call
     */
    public boolean enrollUserToDatabase(User u){
        // TODO
        return this.enrollUserToDatabase(u,u);
    }

    ////////////// BEGIN BENCHMARKED METHOD /////////////

    /**
     * Given a specific posts, add this post to the database
     *
     * @param u The user that initiate this add-post action
     * @param p the post that we are going to add to the database
     * @throws OperationDeniedException when the action is not allowed
     */
    public void addPostToDatabase(User u, Post p) throws OperationDeniedException {
        // TODO
        if (!this.users.containsKey(u.PID) || this.status.equals("inactive")) throw new OperationDeniedException();
        if (p instanceof Question && !((Question) p).answered) this.unanswered.add(p);
//        this.posts.add(p);
        if (this.posts.containsKey(p.poster.PID)){
            this.posts.get(p.poster.PID).add(p);
        }
        else{
            ArrayList<Post> inner = new ArrayList<>();
            inner.add(p);
            this.posts.put(p.poster.PID,inner);
        }

        this.keywordForest.insert(p);
        p.parentPEID = this.courseID;
        u.numOfPostSubmitted++;
        u.posts.add(p);
    }

    /**
     * Get the post posted by this user that has the specific keyword
     *
     * @param u the poster of the post
     * @param keyword the keywords that we are going to query on
     * @return the post array that contains every single post that has the keyword
     */
    public Post[] retrievePost(User u, String keyword){
        // TODO
        // put it in an arraylist then into an array
//        ArrayList <Post> output = new ArrayList<>();
//        for (Post p : this.posts){
//
//            if (p.getKeyword()==null) continue;
//            if (p.poster == u && p.getKeyword().equals(keyword)){
//                output.add(p);
//            }
//        }
//        Post[] output2 = new Post[output.size()];
//        for (int i = 0; i<output2.length; i++){
//            output2[i] = output.get(i);
//        }
//        return output2;

//        HashMap<String, Post> inner = this.posts.get(u.PID);
//        ArrayList <Post> output = new ArrayList<>();
//
//        for(Post p: inner.values()){
//            if (p.getKeyword()==null) continue;
//            if (p.getKeyword().equals(keyword)) output.add(p);
//        }
//
//        return output.toArray(new Post[0]);
        if (this.keywordForest.nodeLookUp(keyword)==null) return new Post[0];
        else{
            ArrayList<Post> output = new ArrayList<>();
            ArrayList<Post> temp = this.keywordForest.nodeLookUp(keyword).getPosts();
            for (Post p : temp){
                if (p.poster == u) output.add(p);
            }
            return output.toArray(new Post[0]);
        }

    }

    /**
     * Get the post that has the specific keyword
     *
     * @param keyword the keyword that we are searching on
     * @return the post array that contains every single post that has the keyword
     */
    public Post[] retrievePost(String keyword){
        // TODO
//        ArrayList <Post> output = new ArrayList<>();
//        for (Post p : this.posts){
//            if (keyword==null) continue;
//            if (Objects.equals(p.getKeyword(), keyword)){
//                output.add(p);
//            }
//        }
//        Post[] output2 = new Post[output.size()];
//        for (int i = 0; i<output2.length; i++){
//            output2[i] = output.get(i);
//        }
//        return output2;
        if (this.keywordForest.nodeLookUp(keyword)==null) return new Post[0];
        return this.keywordForest.nodeLookUp(keyword).getPosts().toArray(new Post[0]);
    }

    /**
     * Get the post with specific poster
     *
     * @param u the poster of posts
     * @return the post array that contains every single post that has specified poster u
     */
    public Post[] retrievePost(User u) {
//        ArrayList <Post> output = new ArrayList<>();
//        for (Post p : this.posts){
//            if (p.poster == u){
//                output.add(p);
//            }
//        }
//        Post[] output2 = new Post[output.size()];
//        for (int i = 0; i<output2.length; i++){
//            output2[i] = output.get(i);
//        }
//        return output2;

        ArrayList<Post> inner = this.posts.get(u.PID);
        int length = inner.size();
        Post[] output = new Post[length];
        int i = 0;
        for(Post p: inner){
            output[i] = p;
            i++;
        }
        return output;
    }

    /**
     * delete the post from the PE. User should be either the creator of the post or a course staff
     * return whether the post got successfully deleted or not
     *
     * @param u the user who initiate this action
     * @param p the post to delete
     * @return whether the action is successful
     * @throws OperationDeniedException when the action is denied
     */
    public boolean deletePostFromDatabase(User u, Post p) throws OperationDeniedException {
        // TODO
        if (!(u instanceof Instructor)) throw new OperationDeniedException();
        if (this.posts.containsKey(p.poster.PID)) {
            if (this.posts.get(p.poster.PID).contains(p)) {
                this.posts.get(p.poster.PID).remove(p);
                if (this.unanswered.contains(p)) this.unanswered.remove(p);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Compute the most urgent question in the unanswered post DS
     * for future answering.
     *
     * @return the Post with the highest urgency rating
     */
    public Post computeMostUrgentQuestion() {
        // TODO
        Post[] output = new Post[2];
        for (int i = 0; i<2; i++){
            output[i] = this.unanswered.poll();
        }
        for (int i = 0; i<2; i++){
            this.unanswered.add(output[i]);
        }
        return output[1];
    }

    /**
     * Compute the top K urgent question from the unanswered post DS
     *
     * @param k the number of unanswered post that we want to have
     * @return the post array that is sorted by the urgency of the post
     * @throws OperationDeniedException when the operation is denied
     */
    public Post[] computeTopKUrgentQuestion(int k) throws OperationDeniedException{
        // TODO
        if (k>unanswered.size()) throw new OperationDeniedException();
        Post[] output = new Post[k];
        for (int i = 0; i<k; i++){
            output[i] = this.unanswered.poll();
        }
        for (int i = 0; i<k; i++){
            this.unanswered.add(output[i]);
        }

        return output;
    }

    /**
     * answer the post. Removed from the unanswered post DataStructure
     *
     * @param u the answerer of the post
     * @param p the post that u is trying to answer
     * @param response the response to be appended to the post as an answer
     * @return the Post instance of the answered post
     * @throws OperationDeniedException when the operation is denied
     */
    public Post answerQuestion(User u, Post p, String response) throws OperationDeniedException{
        // TODO
        if (!(this.unanswered.contains(p))) throw new OperationDeniedException();
        this.unanswered.remove(p);
        return p;
    }

    ////////////// END BENCHMARKED METHOD /////////////

    /**
     *
     * @param u
     * @return
     */
    public String viewStats(User u){
        //TODO
        String output = "";
        if (u instanceof Student) {
            output = u.username + " submitted " + u.numOfPostSubmitted + " posts," +
                    " answered " + u.numOfPostsAnswered + " posts," +
                    " received " + u.numOfEndorsement + " endorsements";
        }
        else{
            for (User i : users.values()) {
                if (i instanceof Student) {
                    output += u.username + " submitted " + u.numOfPostSubmitted + " posts," +
                            " answered " + u.numOfPostsAnswered + " posts," +
                            " received " + u.numOfEndorsement + " endorsements";
                    output += "\n";
                }
            }
            output = output.substring(0,output.length()-1);
        }
        return output;
    }

    /**
     * Retrieve all the posts from this piazza
     *
     * @param u the user who initiate this action
     * @return the posts array that contains every single post
     *      in this piazza
     */
    public Post[] retrieveLog(User u){
        // TODO
        return this.retrievePost(u);
    }

    //If the length > 10, students only be able to access the first 10 posts right?

    /**
     * Retrieve posts log with specified length
     *
     * @param u the user who initiate this action
     * @param length of the posts that is allowed to fetch
     * @return the posts array that satisfy the conditions
     */
    public Post[] retrieveLog(User u, int length){
        // TODO
        Post[] temp = this.retrievePost(u);
        Post[] temp2 = new Post[length];
        if (u instanceof Student && length>10 && temp.length>10) System.arraycopy(temp, 0, temp2, 0, 10);
        else if (length>temp.length) temp2 = temp;
        else System.arraycopy(temp, 0, temp2, 0, length);
        return temp2;
    }

    private String[] getEleMultipleIndex(String[] arr, int[] indexes) {
        String[] output = new String[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            output[i] = arr[indexes[i]];
        }
        return output;
    }

    /**
     * Helper method that initialize the semantic connection
     * of the keywordForest.
     */
    private void initializeForest() {
        String[] allKeywords = new String[]{"tree", "midterm", "heap", "heap sort", "in place",
                "bst", "linked list", "queue",
                "priority queue", "SLL", "DLL", "hash table", "collision", "element", "hash function", "bloom filters",
                 "probing", "linear probing", "quadratic probing", "double hashing", "rehash", "chaining"};
        int[][] childrenIndex = {
                {2, 5}, // i = 0
                {5, 6},
                {7, 3},
                {4},
                {},
                {}, // i = 5
                {9, 10},
                {8},
                {},
                {},
                {}, // i = 10
                {12, 13, 14, 15},
                {16},
                {},
                {19, 20, 21},
                {}, // i = 15
                {17, 18},
                {},
                {},
                {},
                {}, // i = 20
                {}
        };
        for (int i = 0; i < allKeywords.length; i++) {
            keywordForest.addConnection(allKeywords[i], getEleMultipleIndex(allKeywords, childrenIndex[i]));
        }
    }

    /**
     * Sort by the title, return the first k occurrences of the posts with the keyword
     * Forest of tree and storing key using HashMap.
     *
     * @param keyword The keyword that we initiate the starting point of the search
     * @param k the number of similar post that we are querying
     */
    public Post[] computeKSimilarPosts(String keyword, int k) {
        // TODO
        Post[] output = new Post[k];
        int temp = k;
        ArrayList<Forest.InternalNode> pending = new ArrayList<>();
        pending.add(this.keywordForest.nodeLookUp(keyword));

        while (!pending.isEmpty() && temp>0){
            Forest.InternalNode current = pending.get(0);

            pending.remove(0);
            pending.addAll(current.getChildren());
            for (Post p : current.getPosts()){
                if (temp<1) break;
                output[k-temp] = p;
                temp--;
            }
        }
        return output;
    }

    /**
     * Sort by the title, return the first k occurrences of the posts with the keyword
     * Forest of tree of BST and store key using HashMap.
     */
    public Post[] computeKSimilarPosts(String keyword, int k, int level) {
        // TODO
        Post[] output = new Post[k];
        int temp = k;
        ArrayList<Forest.InternalNode> pending = new ArrayList<>();
        pending.add(this.keywordForest.nodeLookUp(keyword));

        while (!pending.isEmpty() && temp>0){
            Forest.InternalNode current = pending.get(0);
            pending.remove(0);
            pending.addAll(current.getChildren());
            for (Post p : current.getPosts()){
                if (temp<1) break;
                output[k-temp] = p;
                temp--;
            }
            level-=1;
            if (level<1) break;
            else level += current.getChildren().size();
        }
        return output;
    }

    /**
     * describes basic course info, user and post status
     * NOT GRADED, for your own debugging purposes
     */
    public String toString(){
        // TODO
        return posts.toString();
    }

}
