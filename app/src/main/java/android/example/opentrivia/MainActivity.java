package android.example.opentrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String playersName;
    private ArrayList<Categories> mCategory;
    private CategoriesAdapter mAdapter;
    private String categoryName;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Greetings();

    }

    public void Greetings(){
//        System.out.print("Hello, Please give me your name: ");
////        name = in.next();
////        System.out.println("Ok "+ name+ " now choose a category that you want to play. " +
////                "You should write the number of the category.");
////        System.out.println("If you just press 0 you will play with random categories:");
////        System.out.println();
////        Categories.displayTheCategories();
////        int questionId = in.nextInt();
////        System.out.println("Please tell me how many questions do you want to play?");
////        int numberOfQuestions = in.nextInt();
////        checkTheResponse(questionId,numberOfQuestions);
        TextView greetings = findViewById(R.id.greetings_text);
        greetings.setText("Hello, Please give me your name: ");
        takeTheName();
    }

//    private void checkTheResponse(int questionId,int numberOfQuestions){
//        Quiz q;
//        if(questionId == 0){
//            q = new Quiz(numberOfQuestions);
//        }else{
//            q = new Quiz(questionId,numberOfQuestions);
//        }
//        q.displayTheQuestions();
//
//    }


    public void takeTheName(){
        Button okButton = findViewById(R.id.take_the_name);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.name_text_input);
                playersName = name.getText().toString();
                TextView greetingsContinue = findViewById(R.id.greetings_text_continue);
                greetingsContinue.setText("Ok "+ playersName+ " now choose a category that you want to play. \n" +
                        "You should choose one of the categories below: ");
                callTheApi();
            }
        });


    }

    public void callTheApi(){
        Context context = getApplicationContext();
        CharSequence txt = "Request Sent";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,txt,duration);
        toast.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://opentdb.com/api_category.php";

        final TextView errorText = findViewById(R.id.Error);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                for (Categories cat : Categories.getTheCategories(response)){
//                    theCategories.add(cat.getName());
//                    cats.add(cat);
//                }

                mCategory = Categories.getTheCategories(response);

//                ArrayAdapter<String> categoriesList = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,theCategories );

                Spinner theList = findViewById(R.id.spinner_with_category_questions);

                mAdapter = new CategoriesAdapter(MainActivity.this,mCategory);

//                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                theList.setAdapter(mAdapter);

                theList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                        String item = (String) parentView.getItemAtPosition(position);
                        Categories categorySelected = (Categories) parentView.getItemAtPosition(position);
                        categoryName = categorySelected.getName();
                        categoryId = categorySelected.getId();
                        if(categoryName.equals("Choose Category")){
                            onNothingSelected(parentView);

                        }else{
                            Log.i("MainActivity", categoryName + " " + categoryId);
                            Toast.makeText(getApplicationContext(), "Selected : " + categoryName, Toast.LENGTH_SHORT).show();
                            Button nextButton = findViewById(R.id.nextButton1);
                            nextButton.setEnabled(true);
                            sendTheIdOfCategory();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
                        Button nextButton = findViewById(R.id.nextButton1);
                        nextButton.setEnabled(false);
                    }

                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.toString().equals("com.android.volley.TimeoutError")){
                    errorText.setText("Connection time out");
                }
                Log.v("MainActivity",error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public void sendTheIdOfCategory(){
        Button nextButton = findViewById(R.id.nextButton1);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionsActivity = new Intent(MainActivity.this, QuestionsActivity.class);
                String catId = String.valueOf(categoryId);
                questionsActivity.putExtra("id",catId);
                questionsActivity.putExtra("categoryName",categoryName);
                questionsActivity.putExtra("PLAYERS_NAME", playersName);
                startActivity(questionsActivity);
            }
        });
    }
}
