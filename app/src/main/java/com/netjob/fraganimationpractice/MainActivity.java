package com.netjob.fraganimationpractice;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {

    protected static final String BUNDLE_STRING_KEY = "bundle_string_key";
    protected static final String BUNDLE_QUESTION_NUMBER_KEY = "bundle_question_number_key";
    protected static final String PREF_CHECK_BUTTON_PUSHED = "bundle_check_button_pushed";
    protected static final String SHARED_PREF_KEY = "shared_pref_key";


    protected SharedPreferences checkButtonPushedPref;
    protected SharedPreferences.Editor editor;

    protected static int score;
    protected int numberOfQuestions;

    HashMap<Integer, HashMap<Integer, String>> typeOfQuestionMap;
    HashMap<Integer, String> singleAnswerQuestionMap;
    HashMap<Integer, String> multiAnswerQuestionMap;
    HashMap<Integer, String> openAnswerQuestionMap;

    String[] singleAnswerQuestionsArray;
    String[] multiAnswerQuestionsArray;
    String[] openAnswerQuestionsArray;
    int questionType;

    Random randomGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkButtonPushedPref = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        randomGenerator = new Random();
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

        } else {

            try {

                File file = new File(getDir("data", MODE_PRIVATE), "map");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);

                typeOfQuestionMap = (HashMap<Integer, HashMap<Integer, String>>) ois.readObject();

            } catch (FileNotFoundException e) {
                Log.e(e.getMessage(), "FileNotFoundException");
                e.printStackTrace();

            } catch (IOException e) {
                Log.e(e.getMessage(), "IOException Read Object");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                Log.e(e.getMessage(), "ClassNotFoundException");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        File file = new File(getDir("data", MODE_PRIVATE), "map");

        try {

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(typeOfQuestionMap);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(e.getMessage(), "IOException Write Object");

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


        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT ) {

            if (toBuildFragment != null) {

                editor = checkButtonPushedPref.edit();
                editor.putBoolean(PREF_CHECK_BUTTON_PUSHED, true);
                editor.commit();

                toBuildFragment.setArguments(args);
                questionCategory.remove(questionNumberKey);

                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_enter_portrait,
                                R.animator.card_flip_exit_portrait)
                        .replace(R.id.main_frag_container, toBuildFragment)
                        .commit();
            }
        } else {

            if (toBuildFragment != null) {

                editor = checkButtonPushedPref.edit();
                editor.putBoolean(PREF_CHECK_BUTTON_PUSHED, true);
                editor.commit();

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
    }

    private void displayScore() {

        String message;

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
