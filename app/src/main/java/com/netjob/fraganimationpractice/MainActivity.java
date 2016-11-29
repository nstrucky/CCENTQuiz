package com.netjob.fraganimationpractice;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    
    ArrayList<String> activeQuestionsArraylist;
    String[] qustionsArray;    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new OneFragment())
                .commit();
                    
        questionsArray = getResources().getStringArray(R.array.questions_array);
        activeQuestionsArrayList = new ArrayList<String>(Arrays.asList(questionsArray));

    }

    protected void buttonListener(View view) {

        TwoFragment aFragment = new TwoFragment();//add argument in constructor for question string?
        Random randomGenerate = new Random();
        int questionNumber = randomGenerate.nextInt(activeQuestionArrayList.size() - 1);
        textView.setText(activeQuestionArrayList.get(questionNumber));
        activeQuestionArrayList.remove(questionNumber);

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, aFragment)
                .addToBackStack(null)
                .commit();


    }


}
