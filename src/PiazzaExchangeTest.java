import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PiazzaExchangeTest {

    @Before
    public void setUp() throws Exception {
        Instructor liao = new Instructor("liao");
        Tutor ta1 = new Tutor("A0001", "ta1");
        Student s1 = new Student("A1111", "Bob");
        Student s2 = new Student("A2222", "Cat");
        Student s3 = new Student("A3333", "Dog");
        ArrayList<User> roster1 = new ArrayList<>();
        roster1.add(liao);
        roster1.add(ta1);
        roster1.add(s1);
        roster1.add(s2);
        roster1.add(s3);
        PiazzaExchange Piazza_1 = new PiazzaExchange(liao,roster1);

        Piazza_1.activatePiazza(liao);

        Question q1 = new Question(s1,"question 1","q1");
        Question q2 = new Question(s1,"question 2","q2");
        Question q3= new Question(s1,"question 3","q3");
        Question q4 = new Question(s1,"question 4","how to do this sheet","how",Piazza_1.courseID, "q4");
        Question q_ta = new Question(ta1,"ta's question","q5");
        Question q_liao = new Question(liao,"prof's question","How r y'all feelin","prof",Piazza_1.courseID,"q6");

        Note n1 = new Note(s1,"student's note","this is just a note", "s note", Piazza_1.courseID,"n1");
        Note n1_ta= new Note(ta1,"ta's note","n2");
        Note n_liao = new Note(liao,"liao's note","this is just a note", "s note", Piazza_1.courseID,"n3");

/**
 * Testing Forest and computeSimilarPost
 */


        Question ll1 = new Question(s1,"question ll1","how to do this sheet","linked list",Piazza_1.courseID, "ll1");
        Question ll2 = new Question(s1,"question ll2","how to do this sheet","linked list",Piazza_1.courseID, "ll2");
        Question ll3 = new Question(s1,"question ll3","how to do this sheet","SLL",Piazza_1.courseID, "ll3");

        Question sll1 = new Question(ta1,"question sll1","how to do this sheet","SLL",Piazza_1.courseID, "sll1");
        Question sll2 = new Question(ta1,"question sll2","how to do this sheet","SLL",Piazza_1.courseID, "sll2");

        Question dll1 = new Question(liao,"question dll1","how to do this sheet","DLL",Piazza_1.courseID, "dll1");
        Question dll2 = new Question(liao,"question dll2","how to do this sheet","DLL",Piazza_1.courseID, "dll2");
        Question dll3 = new Question(liao,"question dll3","how to do this sheet","DLL",Piazza_1.courseID, "dll3");
        Question dll4 = new Question(s1,"question dll4","how to do this sheet","DLL",Piazza_1.courseID, "dll4");
        Question dll5 = new Question(s1,"question dll5","how to do this sheet","DLL",Piazza_1.courseID, "dll5");

        s2.answerQuestion(ll1,"answered");
        s1.addPost(Piazza_1,ll1);
        s1.addPost(Piazza_1,ll1);
        s1.addPost(Piazza_1,ll1);
        s1.addPost(Piazza_1,n1);

        ta1.addPost(Piazza_1,sll1);
        ta1.addPost(Piazza_1,sll2);
        liao.addPost(Piazza_1,dll1);
        liao.addPost(Piazza_1,dll2);
        liao.addPost(Piazza_1,dll3);

        System.out.println(Arrays.toString(Piazza_1.retrievePost(s1)));
        System.out.println(Arrays.toString(Piazza_1.retrievePost(ta1)));
        System.out.println(Arrays.toString(Piazza_1.retrievePost(liao)));


        s1.addPost(Piazza_1,dll4);
        s1.addPost(Piazza_1,dll5);

        System.out.println(Arrays.toString(Piazza_1.computeKSimilarPosts("linked list", 7)));
        System.out.println(Arrays.toString(Piazza_1.computeKSimilarPosts("SLL", 7)));
        System.out.println(Arrays.toString(Piazza_1.computeKSimilarPosts("dll", 2)));
        System.out.println(Arrays.toString(Piazza_1.computeKSimilarPosts("dll", 1)));
        System.out.println(Arrays.toString(Piazza_1.computeKSimilarPosts("tree", 2)));

        System.out.println();
        System.out.println(Piazza_1.getKeywordForest().nodeLookUp("midterm").getChildren());
        System.out.println(Piazza_1.getKeywordForest().nodeLookUp("linked list"));

        System.out.println(Arrays.toString(Piazza_1.computeKSimilarPosts("midterm", 7,1)));






/**
 * Testing User and it's children
 */
        // testing user abstract class
//        q3.setDate(LocalDate.of(2022,1,10));
//
//        Piazza_1.activatePiazza(liao);
//        s1.addPost(Piazza_1,q4);
//        s1.addPost(Piazza_1,n1);
//        s1.addPost(Piazza_1,q3);
//        ta1.addPost(Piazza_1,n1_ta);
//        liao.addPost(Piazza_1,q_liao);
//
//        liao.endorsePost(q3);
//        s1.endorsePost(q4);
//        s1.endorsePost(q4);
//        s1.endorsePost(q4);
//        s1.endorsePost(q4);
//        s1.endorsePost(q4);
//        System.out.println(Piazza_1);
//
//        System.out.println(Piazza_1.computeMostUrgentQuestion());
//        System.out.println(Arrays.toString(Piazza_1.computeTopKUrgentQuestion(1)));
//        System.out.println(Arrays.toString(Piazza_1.computeTopKUrgentQuestion(2)));
//        System.out.println(Arrays.toString(Piazza_1.computeTopKUrgentQuestion(3)));



/**
 * Testing Piazza Main class
 */
//        Instructor marina = new Instructor("marina");
//        PiazzaExchange Piazza_2 = new PiazzaExchange(marina,"DSC20",true);
//        Piazza_2.activatePiazza(marina);
//        s1.enrollClass(Piazza_2);
//
//        q3.setDate(LocalDate.of(2022,1,10));
//
//        Piazza_1.activatePiazza(liao);
//        s1.addPost(Piazza_1,q4);
//        s1.addPost(Piazza_1,n1);
//        s1.addPost(Piazza_1,q3);
//        ta1.addPost(Piazza_1,n1_ta);
//        liao.addPost(Piazza_1,q_liao);
//
//
//        q4.isPrivate = true;
//        s1.answerQuestion(q4,"this is a cool question");
//        ta1.answerQuestion(q4, "you don't say");
//        System.out.println(q4.isPrivate);
//        s2.answerQuestion(q4,"unfortunate");
//        q4.isPrivate = true;
//
//        System.out.println(q4.isPrivate);
//        ta1.answerQuestion(q4,"fortunate");
//
//        System.out.println(q4);
//        System.out.println(q4.getStatus());
//
//
//        s1.endorsePost(q4);
//        s1.endorsePost(q_liao);
//        ta1.endorsePost(q_liao);
//        s2.endorsePost(n1);
//        liao.endorsePost(n1);
//
//        System.out.println(ta1.endorsePost(n1));
//
//        System.out.println(Arrays.toString(s1.getTopTwoEndorsedPosts(Piazza_1)));
//
//        System.out.println(Piazza_1.posts);
//
//        System.out.println(Arrays.toString(s1.getPost("hi", 1, Piazza_1)));
//        System.out.println(Arrays.toString(s1.getPost("hi", 2, Piazza_1)));
//        System.out.println(Arrays.toString(s1.getPost("how", 2, Piazza_1)));
//        System.out.println(Arrays.toString(s1.getPost("prof", 3, Piazza_1)));
//        System.out.println(Arrays.toString(s1.getPost("huh", 3, Piazza_1)));
//
//        System.out.println(Arrays.toString(s1.getLog(11, 1, Piazza_1)));
//        System.out.println(Arrays.toString(s1.getLog(1, 2, Piazza_1)));
//        System.out.println(Arrays.toString(s1.getLog(2, 2, Piazza_1)));
//        System.out.println(Arrays.toString(s1.getLog(11, 2, Piazza_1)));
//
//        System.out.println(s1);
//        System.out.println(ta1);
//        System.out.println(liao);
//        System.out.println(marina);
//
//        System.out.println(Arrays.toString(s1.requestStats(Piazza_1, 1)));
//        System.out.println(Arrays.toString(s1.requestStats(Piazza_1, 2)));




/**
 * Testing Post and it's children
 */
//        //printing the posts above
//        System.out.println(q1);
//        System.out.println(q2);
//        System.out.println(q4);
//        System.out.println(q_liao);
//
//        System.out.println(n1);
//        System.out.println(n1_ta);
//
////        getters
//        System.out.println(q1.getKeyword());
//        System.out.println(q4.getKeyword());
//        System.out.println(q1.getDate());
//        System.out.println(q1.getPoster());
//        System.out.println(q_liao.getPoster());
//        System.out.println(n1_ta.getPoster());
//
////         other methods
//        q2.editText("new text for q2");
//        q4.editText("Actually How tho");
//        System.out.println(q2);
//        System.out.println(q4);
//
//        System.out.println(q2.compareTo(q4));
//        s1.endorsePost(q2);
//        System.out.println(q2.compareTo(q4));
//        s1.endorsePost(q4);
//        System.out.println(q2.compareTo(q4));
//        s1.endorsePost(q4);
//        System.out.println(q2.compareTo(q4));
//        System.out.println(q2);
//        System.out.println(q4);
//
////         question child class methods
//        System.out.println(q2);
//        System.out.println(q4);
//        Piazza_1.activatePiazza(liao);
//        s1.addPost(Piazza_1,q2);
//        s1.answerQuestion(q2, "answer1");
//        System.out.println(q2);
//        System.out.println(q4);
//        System.out.println(s1.answerQuestion(q4, "answer1"));
//        s1.answerQuestion(q2, "answer2");
//        s2.answerQuestion(q2, "answer3");
//        liao.answerQuestion(q2, "teacher's answer");
//        System.out.println(q2);
//        System.out.println(liao.endorsePost(q2));
//        System.out.println(ta1.endorsePost(q2));
//        System.out.println(q2);
//        System.out.println(q2.getStatus());
//        System.out.println(q4.getStatus());
//        q4.answerQuestion("answered");
//        System.out.println(q4);

    }

    @Test
    public void testAnswerQuestion() {
    }

}