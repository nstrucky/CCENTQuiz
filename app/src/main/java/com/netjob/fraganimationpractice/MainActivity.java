package com.netjob.fraganimationpractice;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button button;
    protected static final String BUNDLE_STRING_KEY = "bundle_string_key";
    protected static final String BUNDLE_INT_KEY = "bundle_int_key";//will need this to send the Map Key for the selected question 
                                                                    //to the Fragment created so that we can dynamically change
                                                                    //the layout depending on which one is selected. 

    Map<Integer, Map<Integer, String>> questionsHierarchyMap;
    String[] singleAnswerQuestionsArray;
    String[] multiAnswerQuestionsArray;
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
        multiAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.multi_questions_array);
        openAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.open_questions_array);
        


        //put each array into a map, give each value a key
        Map<Integer, String> singleAnswerQuestionMap = new HashMap<>();

        for (int i = 0; i < singleAnswerQuestionsArray.length; i++) {
          singleAnswerQuestionMap.put(i, singleAnswerQuestionsArray[i]);
        }

        Map<Integer, String> multiAnswerQuestionMap = new HashMap<>();

        for (int i = 0; i < multiAnswerQuestionsArray.length; i++) {
          multiAnswerQuestionMap.put(i, multiAnswerQuestionsArray[i]);
        }

        Map<Integer, String> openAnswerQuestionMap = new HashMap<>();

        for (int i = 0; i < openAnswerQuestionsArray.length; i++) {
          openAnswerQuestionMap.put(i, openAnswerQuestionsArray[i]);
        }
    
        //Map each of the HashMaps to a keyValue
        /*Map<Integer, Map<Integer, String>>*/ questionsHierarchyMap = new HashMap<>();

        questionsHierarchyMap.put(0, singleAnswerQuestionMap);
        questionsHierarchyMap.put(1, multiAnswerQuestionMap);
        questionsHierarchyMap.put(2, openAnswerQuestionMap);
            

    }

    protected void buttonListener(View view) {

        Random random = new Random();

        Object[] hierarchyKeys = questionsHierarchyMap.keySet().toArray();
        ArrayList<Object> questionGroupKeys = new ArrayList<>();
        //TODO Once we are done with these, .clear() the group keys for the next quesiton

        int randomHierarchyKey = random.nextInt(3);
        int randomQuestionKey;

        switch (randomHierarchyKey) {

            case 0:
                //for single answer questions, get reference to Map<Integer, String> and get the keys
                questionGroupKeys.addAll(
                        Arrays.asList(
                                questionsHierarchyMap.get(0).keySet().toArray()
                        )
                );
                break;

            case 1:
                questionGroupKeys.addAll(
                        Arrays.asList(
                                questionsHierarchyMap.get(1).keySet().toArray()
                        )
                );
                break;

            case 2:
                questionGroupKeys.addAll(
                        Arrays.asList(
                                questionsHierarchyMap.get(2).keySet().toArray()
                        )
                );
                break;
        }
        

        if (questionGroupKeys.size() > 1) {
            randomQuestionKey = randomGenerator.nextInt(questionGroupKeys.size());

        } else {
            randomQuestionKey = 0;
        }
        
        Object questionNumberKey = questionGroupKeys.get(randomQuestionKey);
        
        createFragment(questionNumberKey, randomHierarchyKey);

    }

    private void createFragment(Object questionNumberKey, Integer typeOfQuestion) {

        Fragment toBuildFragment = null;
        
            switch (typeOfQuestion) {
         
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

        Map<Integer, String> currentQuestions = questionsHierarchyMap.get(typeOfQuestion);

        args.putString(BUNDLE_STRING_KEY,
                currentQuestions.get(questionNumberKey));

//        if (toBuildFragment != null) {
//            toBuildFragment.setArguments(args);

            if (currentQuestions.size() > 1) {
                currentQuestions.remove(questionNumberKey);
            }

//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                    .replace(R.id.container, toBuildFragment)
//                    .addToBackStack(null)
//                    .commit();


//        }




    }
}
