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
    String[] activeQuestions;
    String activeQuestion;
    String[] questionsWithAnswers = 
        {"",
         "",
         "",
         "",
         ""};
    

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
        
        

        activeQuestions = Arrays.asList(questionsWithAnswers);
    }


    protected void buttonListener(View view) {
        
        int questionNumber = Math.Random(
    
        activeQuestion = activeQuestions[questionNumber];
            
//     String[] makeList = {"a ", "b ", "c ", "d "};
//     List<String> list = Arrays.asList(makeList);
//     ArrayList<String> arrayList = new ArrayList<String>(list);
        

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new TwoFragment())
                .addToBackStack(null)
                .commit();


    }


}
