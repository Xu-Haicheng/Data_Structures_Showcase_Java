public class Student extends User{

    public Student(String PID, String username) {
        // TODO
        super(PID,username);
    }

    public boolean answerQuestion(Post p, String response) {
        // TODO
        if (response.split(" ").length>50 || (p.isPrivate && this != p.poster) || !(p instanceof Question)) return false;
        else{
            ((Question) p).answerQuestion(response);
            this.posts.add(p);
            this.numOfPostsAnswered++;
//            try {
//                PiazzaExchange pe =  this.courses.get(p.parentPEID);
//                pe.answerQuestion(this, p, response);
//            }
//            catch (OperationDeniedException e){
//                return true;
//            }
            return true;
        }
    }

    @Override
    public boolean endorsePost(Post p) {
        // TODO
        if (!p.isPrivate || p.poster == this){
            p.endorsementCount++;
            return true;
        }
        return false;
    }

    @Override
    public boolean editPost(Post p, String newText) {
        //TODO
        if(p.isPrivate && this != p.poster) return false;

        p.editText(newText);
        if (!this.posts.contains(p)) {
            this.numOfPostSubmitted++;
            this.posts.add(p);
        }
        return true;
    }

    @Override
    public String displayName() {
        // TODO
        String output = "Student: " + this.username +", PID: " + this.PID;
        return output;
    }
}
