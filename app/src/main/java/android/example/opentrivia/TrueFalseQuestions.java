package android.example.opentrivia;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class TrueFalseQuestions extends Question {
    private String correctAnswer;
    private String inCorrectAnswer;
    private ArrayList<String> answers = new ArrayList<>();

    public TrueFalseQuestions(String question, String difficulty, String correctAnswer) {
        super(question, difficulty);
        this.correctAnswer = correctAnswer;
        this.inCorrectAnswer = correctAnswer.equals("True") ? "False" : "True";
    }


    @Override
    protected ArrayList<String> displayAllTheAnswers() {
        answers.add(this.correctAnswer);
        answers.add(this.inCorrectAnswer);
        return answers;
    }

    @Override
    boolean checkTheAnswer(String correctAnswer) {
        return this.correctAnswer.equals(correctAnswer)? true : false;
    }

    @Override
    String displayTheCorrectAnswer() {
        return this.correctAnswer;
    }

}
