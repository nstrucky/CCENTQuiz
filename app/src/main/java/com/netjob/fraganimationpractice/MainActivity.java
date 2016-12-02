package com.netjob.fraganimationpractice;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    protected static final String BUNDLE_STRING_KEY = "bundle_string_key";

    Map<Integer, Map<Integer, String>> typeOfQuestionMap;
    Map<Integer, String> singleAnswerQuestionMap;
    Map<Integer, String> multiAnswerQuestionMap;
    Map<Integer, String> openAnswerQuestionMap;

    String[] singleAnswerQuestionsArray;
    String[] multiAnswerQuestionsArray;
    String[] openAnswerQuestionsArray;
    int questionType;
    
    Random randomGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomGenerator = new Random();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new OneFragment())
                .commit();

        //Have tree here and bind each to either 1, 2, or 3;
        
        singleAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.one_questions_array);
        multiAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.multi_questions_array);
        openAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.open_questions_array);
        


        //put each array into a map, give each value a key
        singleAnswerQuestionMap = new HashMap<>();
        for (int i = 0; i < singleAnswerQuestionsArray.length; i++) {
          singleAnswerQuestionMap.put(i, singleAnswerQuestionsArray[i]);
        }

        multiAnswerQuestionMap = new HashMap<>();
        for (int i = 0; i < multiAnswerQuestionsArray.length; i++) {
          multiAnswerQuestionMap.put(i, multiAnswerQuestionsArray[i]);
        }

        openAnswerQuestionMap = new HashMap<>();
        for (int i = 0; i < openAnswerQuestionsArray.length; i++) {
          openAnswerQuestionMap.put(i, openAnswerQuestionsArray[i]);
        }
    
        //Map each of the HashMaps to a keyValue (create Map of Maps)
        /*Map<Integer, Map<Integer, String>>*/ typeOfQuestionMap = new HashMap<>();

        typeOfQuestionMap.put(0, singleAnswerQuestionMap);
        typeOfQuestionMap.put(1, multiAnswerQuestionMap);
        typeOfQuestionMap.put(2, openAnswerQuestionMap);

            

    }

    protected void buttonListener(View view) {

        Random random = new Random();

        Object[] hierarchyKeys = typeOfQuestionMap.keySet().toArray();
        Object[] questionKeys = null;

//        int questionType = 4; //initialized to nonsensical question type number


        int randomTypeOfQuestion;

        if (hierarchyKeys.length > 1) {
            randomTypeOfQuestion = random.nextInt(hierarchyKeys.length);
        } else {
            randomTypeOfQuestion = 0;
        }



        switch ((Integer) hierarchyKeys[randomTypeOfQuestion]) {

            case 0:
                //for single answer questions, get reference to Map<Integer, String> and get the keys
                questionKeys = singleAnswerQuestionMap.keySet().toArray();
                questionType = 0;
                break;

            case 1:
                questionKeys = multiAnswerQuestionMap.keySet().toArray();
                questionType = 1;
                break;

            case 2:
                questionKeys = openAnswerQuestionMap.keySet().toArray();
                questionType = 2;
                break;
        }

        int randomQuestionKey;

        if (questionKeys!= null && questionKeys.length > 1) {
            randomQuestionKey = randomGenerator.nextInt(questionKeys.length);
            Integer questionNumberKey = (Integer) questionKeys[randomQuestionKey];
            createFragment(questionType, questionNumberKey);

        } else {
            randomQuestionKey = 0;
            Integer questionNumberKey = (Integer) questionKeys[randomQuestionKey];
            createFragment(questionType, questionNumberKey);
            typeOfQuestionMap.remove(questionType);
        }
        
//        questionKeys.clear();


    }


    private void createFragment(Integer mTypeOfQuestion, Integer questionNumberKey) {

        Fragment toBuildFragment = null;
        
            switch (mTypeOfQuestion) {
         
            case 0:
                //TODO toBuildFragment = new singleQuestionFragment();
                Toast.makeText(getApplicationContext(), "Single", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //TODO toBuildFragment = new multiQuestionFragment();
                Toast.makeText(getApplicationContext(), "Multi", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //TODO toBuildFragment = new openQuestionFragment();
                Toast.makeText(getApplicationContext(), "Open", Toast.LENGTH_SHORT).show();
                break;
                        
        }
  
        Bundle args = new Bundle();

        Map<Integer, String> questionCategory = typeOfQuestionMap.get(mTypeOfQuestion);

        args.putString(BUNDLE_STRING_KEY, questionCategory.get(questionNumberKey));

//        if (toBuildFragment != null) {
//            toBuildFragment.setArguments(args);


                questionCategory.remove(questionNumberKey);


//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                    .replace(R.id.container, toBuildFragment)
//                    .addToBackStack(null)
//                    .commit();


//        }




    }
}
