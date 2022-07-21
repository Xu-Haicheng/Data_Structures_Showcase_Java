import com.sun.org.apache.bcel.internal.generic.ATHROW;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class User {

    String PID;
    String username;
    int numOfEndorsement;
    int numOfPostSubmitted;
    int numOfPostsAnswered;
    ArrayList<Post> posts;
    HashMap<String, PiazzaExchange> courses;

    /**
     * Constructor for the User abstract class
     *
     * @param PID the PID of the user
     * @param username the username of the user
     */
    public User(String PID, String username) {
        // TODO
        this.PID = PID;
        this.username = username;
        this.numOfEndorsement = 0;
        this.numOfPostsAnswered = 0;
        this.numOfPostSubmitted = 0;
        this.posts = new ArrayList<>();
        this.courses = new HashMap<String, PiazzaExchange>();
    }

    /**
     * Enroll this user to the pizza.
     *
     * @param piazza the piazza to join
     * @return whether the action is successful
     */
    public boolean enrollClass(PiazzaExchange piazza) {
        // TODO
        boolean output = piazza.enrollUserToDatabase(this);
        if (output) this.courses.put(piazza.courseID, piazza);
        return output;
    }


    /**
     * Edit the content of the given post
     *
     * @param p the post we are editing
     * @param newText new content of the post that need to be set
     * @return
     */
    public abstract boolean editPost(Post p, String newText);

    /**
     * User can add the post to the piazza exchange database
     *
     * @param pe the target Piazza
     * @param p the post we want to add
     * @return whether the action is successful or not
     * @throws OperationDeniedException when the action is denied
     */
    public boolean addPost(PiazzaExchange pe, Post p) throws OperationDeniedException {
        // TODO
        pe.addPostToDatabase(this, p);
        return true;
    }

    /**
     * Request status of this specific user
     *
     * @param option daily or monthly
     * @return the statistic of the user
     */
    public int[] requestStats(PiazzaExchange p, int option) throws OperationDeniedException{
        // TODO
        if (option == 1) return p.computeDailyPostStats();
        else if (option == 2) return p.computeMonthlyPostStats();
        else throw new OperationDeniedException();
    }

    ////////////// Stats querying method BEGINS /////////////

    /**
     * FOREST PART
     * Complete this after you finish the forest part
     *
     * Initiating Search K similar posts for a specific piazza exchange.
     *
     * @param pe the target piazza
     * @param keyword the initiating keyword of the search
     * @param k the amount of posts we want to get back
     * @return the k posts
     */
    public Post[] searchKSimilarPosts(PiazzaExchange pe, String keyword, int k) {
        // TODO
        return null;
    }

    /**
     * Requests the PiazzaExchange class to provide a set of posts associated with the user.
     * The option parameter specifies additional search filters.
     *
     * @param keyword keyword we are searching
     * @param option the different search type we want to perform
     * @param p the target piazza exchange
     * @return the post array
     */
    public Post[] getPost(String keyword, int option, PiazzaExchange p) {
        // TODO
        if (option == 1) return p.retrievePost(this);
        else if (option == 2) return p.retrievePost(this,keyword);
        else return p.retrievePost(keyword);
    }

    /**
     * initiate the action of getLog of a specific piazza
     * @param pe the target piazza
     * @param length the amount of logs to be retrieved
     * @param option the query type when retrieving log 
     * @return the post array
     */
    public Post[] getLog(int length, int option, PiazzaExchange pe) throws OperationDeniedException{
        // TODO
        if (option == 1) return pe.retrieveLog(this);
        else if (option == 2) return pe.retrieveLog(this,length);
        else throw new OperationDeniedException();
    }

    ////////////// Stats querying method END /////////////

    /**
     * answer a given post instance with the response
     *
     * @param p the post we want to answer
     * @param response the response of the post
     * @return whether the action is successful
     */
    public abstract boolean answerQuestion(Post p, String response);

    /**
     * Endorse a specific post instance
     *
     * @param p the post the user want to endorse
     * @return whether the action is successful or not
     */
    public abstract boolean endorsePost(Post p);

    /**
     * gets top two endorsed posts (by number of endorsements)
     *
     * @param pe piazzaExchange
     * @return top two posts
     */
    public Post[] getTopTwoEndorsedPosts(PiazzaExchange pe){
        return pe.computeTopTwoEndorsedPosts();
    }

    /**
     * Display name for the user
     *
     * @return the name of the user
     */
    public abstract String displayName();

    public String toString() {
        // for testing
        return "PID: " + this.PID + " | " + "User Name: " + this.username +
                " | " + "# post answered:" + this.numOfPostsAnswered +
                " | " + "# Posts endorsed by staff:" + this.numOfEndorsement +
                " | " + "# posts:" + this.numOfPostSubmitted +
                " | " + "posts:" + this.posts +
                " | " + "courses:" + this.courses;
    }
}
