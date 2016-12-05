package com.netjob.fraganimationpractice;

//import android.app.Activity;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//delete this comment

public class MainActivity extends Activity {

    protected static final String BUNDLE_STRING_KEY = "bundle_string_key";
    protected static final String BUNDLE_QUESTION_NUMBER_KEY = "bundle_question_number_key";
    protected static int score;
    protected int numberOfQuestions;

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

        getFragmentManager()
                .beginTransaction()
                .add(R.id.main_frag_container, new LandingFragment())
                .commit();

        //Have tree here and bind each to either 1, 2, or 3;

        singleAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.one_questions_array);
        multiAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.multi_questions_array);
        openAnswerQuestionsArray = getResources()
                        .getStringArray(R.array.open_questions_array);

        numberOfQuestions =
                        singleAnswerQuestionsArray.length +
                        multiAnswerQuestionsArray.length +
                        openAnswerQuestionsArray.length;


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

        int randomTypeOfQuestion;
        int randomQuestionKey;
        Integer questionNumberKey;

        Object[] typeOfQuestionKeys;
        Object[] questionKeys = null;

        Random random = new Random();

        if (typeOfQuestionMap.isEmpty()) {
            displayScore();
//            Toast.makeText(this, "displayScore();", Toast.LENGTH_SHORT).show();
            return;
        }
        typeOfQuestionKeys = typeOfQuestionMap.keySet().toArray();

        if (typeOfQuestionKeys.length > 1) {
            randomTypeOfQuestion = random.nextInt(typeOfQuestionKeys.length);

        } else {
            randomTypeOfQuestion = 0;
        }

        switch ((Integer) typeOfQuestionKeys[randomTypeOfQuestion]) {

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

        if (questionKeys!= null && questionKeys.length > 1) {
            randomQuestionKey = randomGenerator.nextInt(questionKeys.length);
            questionNumberKey = (Integer) questionKeys[randomQuestionKey];
            createFragment(questionType, questionNumberKey);

        } else {
            randomQuestionKey = 0;
            questionNumberKey = (Integer) questionKeys[randomQuestionKey];
            createFragment(questionType, questionNumberKey);
            typeOfQuestionMap.remove(questionType);
        }
    }


    private void createFragment(Integer mTypeOfQuestion, Integer questionNumberKey) {

        Bundle args = new Bundle();
        Map<Integer, String> questionCategory = typeOfQuestionMap.get(mTypeOfQuestion);
        args.putString(BUNDLE_STRING_KEY, questionCategory.get(questionNumberKey));
        args.putInt(BUNDLE_QUESTION_NUMBER_KEY, questionNumberKey);

        Fragment toBuildFragment = null;

            switch (mTypeOfQuestion) {

            case 0:
                toBuildFragment = new SingleAnswerFragment();
                break;
            case 1:
                toBuildFragment = new MultiAnswerFragment();
                break;
            case 2:
                toBuildFragment = new OpenAnswerFragment();
                break;

        }

        if (toBuildFragment != null) {

            toBuildFragment.setArguments(args);
            questionCategory.remove(questionNumberKey);

            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(   R.animator.card_flip_enter,
                                            R.animator.card_flip_exit)
                    .replace(R.id.main_frag_container, toBuildFragment)
//                    .addToBackStack(null)
                    .commit();

        }

    }

    private void displayScore() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        builder.setMessage(String.format("You scored %d/9%n%nGood job?", score));
        builder.setTitle(String.format("You scored %d/%d. Good job?", score, numberOfQuestions));

        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetQuiz();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }


    private void resetQuiz() {

        score = 0;

//        numberOfQuestions =
//                        singleAnswerQuestionsArray.length +
//                        multiAnswerQuestionsArray.length +
//                        openAnswerQuestionsArray.length;

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

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frag_container, new LandingFragment())
                .commit();

    }
}
