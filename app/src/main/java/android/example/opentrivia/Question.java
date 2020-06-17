package android.example.opentrivia;

import java.util.ArrayList;

public abstract class Question {
    private String question;

    private String difficulty;


    public Question(String question, String difficulty) {
        this.question = question;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    abstract ArrayList<String> displayAllTheAnswers();
    abstract boolean checkTheAnswer(String correctAnswer);
    abstract String displayTheCorrectAnswer();
}
