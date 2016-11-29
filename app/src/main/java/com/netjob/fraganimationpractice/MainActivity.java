package com.netjob.fraganimationpractice;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    protected static final String BUNDLE_STRING_KEY = "bundle_string_key";
    protected static final String BUNDLE_INT_KEY = "bundle_int_key";
    
    ArrayList<String> activeQuestionsArrayList;
    String[] questionsArray;
    Random randomGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        randomGenerator = new Random();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new OneFragment())
                .commit();
                    
        questionsArray = getResources()
                        .getStringArray(R.array.questions_array);
        activeQuestionsArrayList = new ArrayList<String>(Arrays.asList(questionsArray));

    }

    protected void buttonListener(View view) {

            if (activeQuestionsArrayList.size() > 1) {

                int questionNumber = randomGenerator.nextInt(activeQuestionsArrayList.size() - 1);

                TwoFragment aFragment = new TwoFragment();
                Bundle args = new Bundle();
                args.putString(BUNDLE_STRING_KEY, "example String");
                args.putInt(BUNDLE_INT_KEY, questionNumber);
                aFragment.setArguments(args);

                activeQuestionsArrayList.remove(questionNumber);

                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.container, aFragment)
                        .addToBackStack(null)
                        .commit();

            } else {
                Toast.makeText(getApplicationContext(), "Ya done", Toast.LENGTH_SHORT).show();
                return;
            }










    }


}
