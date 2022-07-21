import java.util.ArrayList;

public class Instructor extends User{

    public Instructor(String username) {
        // TODO
        super("A0000",username);
    }

    public boolean answerQuestion(Post p, String response) {
        // TODO
        ((Question) p).answerQuestion(response);
        this.posts.add(p);
        this.numOfPostsAnswered++;
        return true;
    }

    @Override
    public boolean endorsePost(Post p) {
        // TODO
        if (p.endorsedByCourseStaff) return false;
        p.poster.numOfEndorsement++;
        p.endorsedByCourseStaff = true;
        p.endorsementCount++;
        return true;
    }

    @Override
    public String displayName() {
        // TODO
        String output = "Instructor: " + this.username +", PID: " + this.PID;
        return output;
    }

    public Post deletePost(Post p, PiazzaExchange piazza) {
        // TODO
        try{
            piazza.deletePostFromDatabase(this,p);
            return p;
        }
        catch (OperationDeniedException e) {
            return null;
        }
    }

    public boolean inactivatePiazza(PiazzaExchange piazza) {
        // TODO
        return piazza.deactivatePiazza(this);
    }

    public boolean editPost(Post p, String newText){
        p.editText(newText);
        if (!this.posts.contains(p)) {
            this.numOfPostSubmitted++;
            this.posts.add(p);
        }
        return true;
    }

    /**
     * get the top k urgent questions from a specific piazza
     *
     * @param pe the target Piazza
     * @param k the amount of urgent post that we want to get
     * @return the k urgent posts
     * @throws OperationDeniedException when the operation is denied
     */
    public Post[] getTopKUrgentQuestion(PiazzaExchange pe, int k) throws OperationDeniedException {
        // TODO
        Post[] output;
        if (k == 1){
            output = new Post[1];
            output[0] = pe.computeMostUrgentQuestion();
        }
        else{
            output = pe.computeTopKUrgentQuestion(k);
        }
        return output;
    }
}
