package com.netjob.fraganimationpractice;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static android.support.v7.appcompat.R.styleable.AlertDialog;

public class MainActivity extends Activity {

    final String SHARED_PREF_KEY = "com.netjob.ccentquiz.sharedpref";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    protected static final String BUNDLE_STRING_KEY = "bundle_string_key";
    protected static final String BUNDLE_QUESTION_NUMBER_KEY = "bundle_question_number_key";
    final String SINGLE_ANSWER_SET_KEY = "singleAnswerSet";
    final String MULTI_ANSWER_SET_KEY = "multiAnswerSet";
    final String OPEN_ANSWER_SET_KEY = "openAnswerSet";



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

        sharedPreferences = getSharedPreferences(SHARED_PREF_KEY, 0);
        typeOfQuestionMap = new HashMap<>();
        singleAnswerQuestionMap = new HashMap<>();
        multiAnswerQuestionMap = new HashMap<>();
        openAnswerQuestionMap = new HashMap<>();

        singleAnswerQuestionsArray = getResources()
                .getStringArray(R.array.one_questions_array);
        multiAnswerQuestionsArray = getResources()
                .getStringArray(R.array.multi_questions_array);
        openAnswerQuestionsArray = getResources()
                .getStringArray(R.array.open_questions_array);

        numberOfQuestions = singleAnswerQuestionsArray.length +
                            multiAnswerQuestionsArray.length +
                            openAnswerQuestionsArray.length;

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_frag_container, new LandingFragment())
                    .commit();

            for (int i = 0; i < singleAnswerQuestionsArray.length; i++) {
                singleAnswerQuestionMap.put(i, singleAnswerQuestionsArray[i]);
            }


            for (int i = 0; i < multiAnswerQuestionsArray.length; i++) {
                multiAnswerQuestionMap.put(i, multiAnswerQuestionsArray[i]);
            }


            for (int i = 0; i < openAnswerQuestionsArray.length; i++) {
                openAnswerQuestionMap.put(i, openAnswerQuestionsArray[i]);
            }

            typeOfQuestionMap.put(0, singleAnswerQuestionMap);
            typeOfQuestionMap.put(1, multiAnswerQuestionMap);
            typeOfQuestionMap.put(2, openAnswerQuestionMap);

        }




        /* else {

            Set<String> singleStringSet = sharedPreferences.getStringSet(SINGLE_ANSWER_SET_KEY, null);
            Set<String> multiStringSet = sharedPreferences.getStringSet(MULTI_ANSWER_SET_KEY, null);
            Set<String> openStringSet = sharedPreferences.getStringSet(OPEN_ANSWER_SET_KEY, null);

            if (singleStringSet != null) {
                Object[] singleObjectArray = singleStringSet.toArray();
                for (int i = 0; i < singleObjectArray.length; i++) {
                    singleAnswerQuestionMap.put(i, (String) singleObjectArray[i]);

                }
                typeOfQuestionMap.put(0, singleAnswerQuestionMap);
            } else {
                typeOfQuestionMap.put(0, new HashMap<Integer, String>(1));

            }


            if (multiStringSet != null) {
                Object[] multiObjectArray = multiStringSet.toArray();
                for (int i = 0; i < multiObjectArray.length; i++) {
                    multiAnswerQuestionMap.put(i, (String) multiObjectArray[i]);

                }
                typeOfQuestionMap.put(1, multiAnswerQuestionMap);

            } else {
            typeOfQuestionMap.put(1, new HashMap<Integer, String>(1));

            }


            if (openStringSet != null) {
                Object[] openObjectArray = openStringSet.toArray();
                for (int i = 0; i < openObjectArray.length; i++) {
                    openAnswerQuestionMap.put(i, (String) openObjectArray[i]);

                }
                typeOfQuestionMap.put(2, openAnswerQuestionMap);

            } else {
                typeOfQuestionMap.put(2, new HashMap<Integer, String>(1));
            }

        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();

//        editor = sharedPreferences.edit();
//        editor.clear();
//
//        Set<String> singleStrings = new HashSet<>();
//        Set<String> multiStrings = new HashSet<>();
//        Set<String> openStrings = new HashSet<>();
//
//        if (typeOfQuestionMap.get(0) != null) {
//
//            for (int i = 0; i < typeOfQuestionMap.get(0).size(); i++) {
//                singleStrings.add(typeOfQuestionMap.get(0).get(i));
//            }
//            editor.putStringSet(SINGLE_ANSWER_SET_KEY, singleStrings);
//        }
//
//
//        if (typeOfQuestionMap.get(1) != null) {
//
//            for (int i = 0; i < typeOfQuestionMap.get(1).size(); i++) {
//                multiStrings.add(typeOfQuestionMap.get(1).get(i));
//            }
//            editor.putStringSet(MULTI_ANSWER_SET_KEY, multiStrings);
//        }
//
//
//        if (typeOfQuestionMap.get(2) != null) {
//            for (int i = 0; i < typeOfQuestionMap.get(2).size(); i++) {
//                openStrings.add(typeOfQuestionMap.get(2).get(i));
//            }
//            editor.putStringSet(OPEN_ANSWER_SET_KEY, openStrings);
//
//        }
//        editor.commit();


        File file = new File(getDir("data", MODE_PRIVATE), "map");

        try {

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(typeOfQuestionMap);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {

        }




    }

    public void buttonListener(View view) {

        int randomTypeOfQuestion;
        int randomQuestionKey;
        Integer questionNumberKey;

        Object[] typeOfQuestionKeys;
        Object[] questionKeys = null;

        Random random = new Random();

        if (typeOfQuestionMap.isEmpty()) {
            displayScore();
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
                questionKeys = typeOfQuestionMap.get(0).keySet().toArray();
                questionType = 0;
                break;

            case 1:
                questionKeys = typeOfQuestionMap.get(1).keySet().toArray();
                questionType = 1;
                break;

            case 2:
                questionKeys = typeOfQuestionMap.get(2).keySet().toArray();
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
                    .setCustomAnimations(
                            R.animator.card_flip_enter,
                            R.animator.card_flip_exit)
                    .replace(R.id.main_frag_container, toBuildFragment)
                    .commit();
        }
    }

    private void displayScore() {

        String message;

        Toast.makeText(getApplicationContext(),
                "You scored " + score + " out of 9!",
                Toast.LENGTH_SHORT)
                .show();

        if (score < 7) {
            message = "You need to study more, woh woh.";
        } else {
            message = "Good job!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.format("You scored %d/%d. %s", score, numberOfQuestions, message));
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

        typeOfQuestionMap = new HashMap<>();

        typeOfQuestionMap.put(0, singleAnswerQuestionMap);
        typeOfQuestionMap.put(1, multiAnswerQuestionMap);
        typeOfQuestionMap.put(2, openAnswerQuestionMap);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frag_container, new LandingFragment())
                .commit();

    }
}
