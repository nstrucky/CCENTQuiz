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
    protected static final String BUNDLE_INT_KEY = "bundle_int_key";//will need this to send the Map Key for the selected question 
                                                                    //to the Fragment created so that we can dynamically change
                                                                    //the layout depending on which one is selected. 

    Map<Integer, Map<Integer, List<String>>> activeQuestionsMap; //needed to match questions to answers
    String[] singleAnswerQuestionsArray;
    String[] multipleAnswerQuestionsArray;
    String[] openAnswerQuestionsArray;
    
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
                          
        
        //Have tree here and bind each to either 1, 2, or 3;
        
        singleAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.one_questions_array);
        multipleAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.multi_questions_array);
        openAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.open_questions_array);
        


        //put each array into a map, give each value a key
        Map<Integer, String> singleAnswerQuestionMap = new HashMap<>();

        for (int i = 0; i < singleAnswerQuestionArray.length; i++) {
          singleAnswerQuestionMap.put(i, singleAnswerQuestionArray[i]);
        }

        Map<Integer, String> multiAnswerQuestionMap = new HashMap<>();

        for (int i = 0; i < multiAnswerQuestionArray.length; i++) {
          multiAnswerQuestionMap.put(i, multiAnswerQuestionArray[i]);
        }

        Map<Integer, String> openAnswerQuestionMap = new HashMap<>();

        for (int i = 0; i < openAnswerQuestionArray.length; i++) {
          openAnswerQuestionMap.put(i, openAnswerQuestionArray[i]);
        }
    
        //Map each of the HashMaps to a keyValue
        Map<Integer, Map<Integer, String>> outerMap = new HashMap<>();

        outerMap.put(0, singleAnswerQuestionMap);
        outerMap.put(0, multiAnswerQuestionMap);
        outerMap.put(0, openAnswerQuestionMap);
            
        

        



        
        
        //add each entry from tree to HashMap
        

        activeQuestionsMap = new HashMap<<Integer, Map.Entry>();
        
        //interate through the 

        }
    }
    
    private void add

    protected void buttonListener(View view) {

        int random;

        Object[] keySetArray = activeQuestionsMap.keySet().toArray();

        if (keySetArray.length > 1) {
            random = randomGenerator.nextInt(keySetArray.length - 1);

        } else {
            random = 0;
        }
        
        Integer questionNumberKey = (Integer) keySetArray[random];
        

        createFragment(questionNumberKey);
        

    }

    private void createFragment(Integer questionNumberKey) {

        Fragment aFragment;
        
            switch (typeOfQuestion) {
         
            case singleAnswer:
                aFragment = new singleQuestionFragment();
                break;
            case multiAnswer:
                aFragment = new multiQuestionFragment();
                break;
            case openAnswer:
                aFragment = new openQuestionFragment();
                break;
                        
        }
  
        Bundle args = new Bundle();
        args.putString(BUNDLE_STRING_KEY, activeQuestionsMap.get(questionNumberKey));
        args.putInt(BUNDLE_INT_KEY, questionNumberKey);//May need to unbox here?
        aFragment.setArguments(args);

        if (activeQuestionsMap.size() > 1) {
            activeQuestionsMap.remove(questionNumberKey);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, aFragment)
                .addToBackStack(null)
                .commit();

    }
}
