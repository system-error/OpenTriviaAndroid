package android.example.opentrivia;

import java.util.ArrayList;
import java.util.Collections;

class MultipleChoiceQuestions extends Question {
    private String correctAnswer;
    private String[] incorrectAnswers;
    private ArrayList<String> allTheAnswers = new ArrayList<>();

    public MultipleChoiceQuestions(String question, String difficulty, String correctAnswer, String[] incorrectAnswers) {
        super(question, difficulty);
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    @Override
    protected ArrayList<String> displayAllTheAnswers() {
        int numberOfAnswers = this.incorrectAnswers.length;
        for(int i=0; i < numberOfAnswers; i++){
            allTheAnswers.add(this.incorrectAnswers[i]);
        }
        allTheAnswers.add(this.correctAnswer);
        Collections.shuffle(allTheAnswers);
        return allTheAnswers;
    }

    @Override
    boolean checkTheAnswer(String correctAnswer) {

//        if(this.correctAnswer.equals(correctAnswer)){
//            return true;
//        }else {
//            return false;
//        }

        return this.correctAnswer.equals(correctAnswer)? true : false;


    }

    @Override
    String displayTheCorrectAnswer() {
        return this.correctAnswer;
    }
}
