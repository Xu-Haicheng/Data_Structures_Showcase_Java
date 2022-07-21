import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Post implements Comparable<Post> {

    String UID;
    String parentPEID;
    int endorsementCount;
    boolean endorsedByCourseStaff;
    private String header;
    protected String text;
    boolean isPrivate;
    User poster;
    LocalDate date;
    int priority;
    private String keyword;

    /**
     * Constructor for Post
     *
     * @param poster the poster of the post
     * @param header the header of the post
     */
    public Post(User poster, String header, String UID) {
        // TODO
        this.UID = UID;
        this.poster = poster;
        this.header = header;
        this.date = LocalDate.now();
        this.priority = 0;
        this.text = "";
        this.keyword = null;
        this.parentPEID = null;
        this.endorsementCount = 0;
        this.endorsedByCourseStaff = false;
        this.isPrivate = false;
    }

    /**
     * Overloaded constructor for Post
     */
    public Post(User poster, String header, String text, String keyword, String PEID, String UID) {
        // TODO
        this.UID = UID;
        this.poster = poster;
        this.header = header;
        this.date = LocalDate.now();
        this.priority = 0;
        this.text = text;
        this.keyword = keyword;
        this.parentPEID = PEID;
        this.endorsementCount = 0;
        this.endorsedByCourseStaff = false;
        this.isPrivate = false;

    }

    /**
     * Getter method of the keyword of the post
     * @return the keyword of the post
     */
    public String getKeyword() {
        // TODO
        return this.keyword;
    }

    /**
     * Getter method of the text of the post
     * @return the text of the post
     */
    public abstract String getText(User u) throws OperationDeniedException;

    public LocalDate getDate() {
        // TODO
        return this.date;
    }

    /**
     * Set the date of the post to the provided new date
     * 
     * @param newDate the new date we are setting the post to
     */
    public void setDate(LocalDate newDate) {
        // TODO
        this.date = newDate;
    }

    public User getPoster() {
        // TODO
        return this.poster;
    }

    public void editText(String text) {
        // TODO
        this.text = text;
    }

//    public String toString() {
//        // for testing
//        return "Post ID: " + this.UID + " | "+"Text: " + this.text + " | "+"Date: " + this.date + " | "+ "Course ID:" + this.parentPEID +
//                " | "+ "#Endorsement:" + this.endorsementCount + " | "+ "endorsed by staff:" + this.endorsedByCourseStaff;
//    }
        public String toString() {
        // for testing
        return "Post ID: " + this.UID + " | " + "priority:" + this.calculatePriority();
    }

    /**
     * Compare two posts by their priority
     *
     * @param other the other post that we are comparing
     * @return whether this post is larger than the other post
     */
    public int compareTo(Post other){
        // TODO
        int p1 = this.calculatePriority();
        int p2 = other.calculatePriority();
        return Integer.compare(p1, p2);
    }

    public int calculatePriority() {
        int output = this.endorsementCount+(int)(this.date.until(LocalDate.now()).getDays()/3.0);
        this.priority = output;
        return this.priority;
    }




}
