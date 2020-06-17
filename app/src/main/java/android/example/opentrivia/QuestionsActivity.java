package android.example.opentrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {
   private Context context;
   private ArrayList<Question> questions;
   private Question q;
   private int score = 0;
   private String currentScore;
   private int current;
   private boolean check = false;
   private TextView questionView;
   private RadioButton rdbtn1;
   private RadioButton rdbtn2;
   private RadioButton rdbtn;
   private RadioGroup radioGroup;
   private String clickedAnswer;
   private int totalNumberOfQuestions = 0;
   private int NumberOfQuestionsAnsweredCorrectly = 0;
    private String playersName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Intent intent = getIntent();
        int categoryId = Integer.parseInt(intent.getStringExtra("id"));
        String categoryName = intent.getStringExtra("categoryName");
        playersName = intent.getStringExtra("PLAYERS_NAME");

        TextView nameOfCategory = findViewById(R.id.questionTypeName);
        questionView = findViewById(R.id.questionTitle);
        nameOfCategory.setText(categoryName);
        questions = new ArrayList<>();
        getQuestions(categoryId);
        current = 0;
        radioGroup = findViewById(R.id.radioGroup);


//        Toast tostaki = Toast.makeText(this,categoryName + " " + categoryId,Toast.LENGTH_SHORT);
//        tostaki.show();

    }

    public void getQuestions(int id){
        RequestQueue queue = Volley.newRequestQueue(this);
        context = getApplicationContext();
        String url = "https://opentdb.com/api.php?amount=3&category=" + id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray questionArray = jsonObject.getJSONArray("results");
                    JSONObject quest;
                    boolean firstQuestion = true;
                    for(int i=0; i< questionArray.length(); i++){
                        quest = questionArray.getJSONObject(i);
                        questions.add(QuestionFactory.getQuestion(quest));
                        displayNextQuestion();
                        if (firstQuestion){
                            addRadioButtons(q.displayAllTheAnswers());
                            firstQuestion=false;
                        }

                        Button next = (Button) findViewById(R.id.nextButton);
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(check != true){
                                    nextQuestion();
                                }else{
                                    displayTheScore();
                                }
                            }
                        });
                    }
                    totalNumberOfQuestions = questions.size();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        queue.add(request);
    }

    private void displayNextQuestion(){
        q = questions.get(current);
        questionView.setText(q.getQuestion());
    }

    private void addRadioButtons(ArrayList<String> answers) {
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        int counter = radioGroup.getChildCount();
        if(counter > 0 ) {
            for (int i=counter-1;i>=0;i--) {
                    radioGroup.removeViewAt(i);
            }
        }
        if(answers.contains("True") || answers.contains("False")){
            rdbtn1 = new RadioButton(this);
            rdbtn2 = new RadioButton(this);
            rdbtn1.setId(View.generateViewId());
            rdbtn1.setText("True");
            rdbtn2.setId(View.generateViewId());
            rdbtn2.setText("False");
            radioGroup.addView(rdbtn1);
            radioGroup.addView(rdbtn2);
        }else{
            for (String answer : answers){
                rdbtn = new RadioButton(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(answer);
                radioGroup.addView(rdbtn);
            }
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for(int i=0; i < group.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) group.getChildAt(i);
                    if(btn.getId() == checkedId) {
                        clickedAnswer = btn.getText().toString();
                        Log.i("Clicked",clickedAnswer);
                        checkTheAnswer();
                    }
                }
            }
        });
    }

    private void nextQuestion() {
        if (current < questions.size()-1) {
            current++;
            addRadioButtons(questions.get(current).displayAllTheAnswers());
            displayNextQuestion();
        }else {
            check = true;
            Toast toast = Toast.makeText(context, "This is the last question", Toast.LENGTH_LONG);
            toast.show();
            Button b = findViewById(R.id.nextButton);
            b.setText("SCORE");
        }
    }

//    private void getTheCheckedAnswers(){
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                for(int i=0; i < group.getChildCount(); i++) {
//                    RadioButton btn = (RadioButton) group.getChildAt(i);
//                    if(btn.getId() == checkedId) {
//                        clickedAnswer = btn.getText().toString();
//                        Log.i("Clicked",clickedAnswer);
//                        checkTheAnswer();
//                    }
//                }
//            }
//        });
//    }

    private void checkTheAnswer(){
        Log.i("Correct in question",q.displayTheCorrectAnswer());
        if(q.checkTheAnswer(clickedAnswer)){
            NumberOfQuestionsAnsweredCorrectly++;
            score++;
            currentScore = String.valueOf(score);
            Log.i("Is it correct?","Yes it is!");

        }else{
            score = score == 0 ? 0 : score--;
            currentScore = String.valueOf(score);
            Log.i("The score is ", currentScore);
            Log.i("Is it correct?","No it is not!");
        }
    }

    public void displayTheScore(){
        currentScore = currentScore == null ? String.valueOf(0) : currentScore;
        playersName = playersName.isEmpty() ? "Stranger" : playersName;
        Intent intent = new Intent(this, QuizScore.class);
        intent.putExtra("SCORE", currentScore);
        intent.putExtra("NUMBER_OF_QUESTIONS_ANSWERED",String.valueOf(NumberOfQuestionsAnsweredCorrectly));
        intent.putExtra("TOTAL_QUESTIONS", String.valueOf(totalNumberOfQuestions));
        intent.putExtra("PLAYERS_NAME", playersName);
        startActivity(intent);
    }

}