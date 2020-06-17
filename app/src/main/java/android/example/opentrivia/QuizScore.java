package android.example.opentrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class QuizScore extends AppCompatActivity {

    private TextView displayTheScore;
    private TextView displayTheFinishMessage;
    private TextView displayTheAnsweredQuestions;
    private TextView displayTheTotalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);



        Intent intent = getIntent();
        String score = intent.getStringExtra("SCORE");
        String NumberOfQuestionsAnswered = intent.getStringExtra("NUMBER_OF_QUESTIONS_ANSWERED");
        String totalNumberOfQuestions = intent.getStringExtra("TOTAL_QUESTIONS");
        String playersName = intent.getStringExtra("PLAYERS_NAME");

        displayTheScore = findViewById(R.id.scoreView);
        displayTheFinishMessage = findViewById(R.id.textForEndGame);
        displayTheAnsweredQuestions = findViewById(R.id.answeredView);
        displayTheTotalQuestions = findViewById(R.id.answsersView);
        Toast toast = Toast.makeText(this, "Your sore is "+ score, Toast.LENGTH_LONG);
        toast.show();
        displayTheScore.setText(score);
        displayTheFinishMessage.setText(playersName+" the game is finished! Thank you");
        displayTheAnsweredQuestions.setText(NumberOfQuestionsAnswered);
        displayTheTotalQuestions.setText(totalNumberOfQuestions);

    }
}