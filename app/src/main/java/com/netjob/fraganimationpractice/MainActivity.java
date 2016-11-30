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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button button;
    protected static final String BUNDLE_STRING_KEY = "bundle_string_key";

    Map<Integer, String> activeQuestionsMap;
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

        activeQuestionsMap = new HashMap<>();
        for (int i = 0; i < questionsArray.length; i++) {
            activeQuestionsMap.put(i, questionsArray[i]);

        }
    }

    protected void buttonListener(View view) {

        int random;

        Object[] keySetArray = activeQuestionsMap.keySet().toArray();

        if (keySetArray.length > 1) {
            random = randomGenerator.nextInt(keySetArray.length - 1);

        } else {
            random = 0;
        }

        createFragmentWithQuestion(((Integer) keySetArray[random]));

    }

    private void createFragmentWithQuestion(Integer questionNumber) {

        TwoFragment aFragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_STRING_KEY, activeQuestionsMap.get(questionNumber));
        aFragment.setArguments(args);

        if (activeQuestionsMap.size() > 1) {
            activeQuestionsMap.remove(questionNumber);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, aFragment)
                .addToBackStack(null)
                .commit();

    }


}
