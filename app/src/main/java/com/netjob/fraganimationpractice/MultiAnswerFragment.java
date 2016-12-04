package com.netjob.fraganimationpractice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultiAnswerFragment extends Fragment {

    private String questionTextFromBundle;
    private TextView questionTextView;
    private CheckBox[] checkBoxes = null;
    private int[] correctAnswers;
    private int questonNumberKey;
    private int numberOfCheckBoxes = 4;
    private int numberOfCorrectAnswers;
    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    public MultiAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_multi_answer, container, false);

        questonNumberKey = getArguments().getInt(MainActivity.BUNDLE_QUESTION_NUMBER_KEY);

        buildQuestionLogic(questonNumberKey);

        questionTextFromBundle = getArguments().getString(MainActivity.BUNDLE_STRING_KEY, "Error");
        questionTextView = (TextView) view.findViewById(R.id.textview_question);
        questionTextView.setText("Multi\n" + questionTextFromBundle);

        checkBoxes = new CheckBox[numberOfCheckBoxes];

        checkBoxA = (CheckBox) view.findViewById(R.id.checkBoxA);
        checkBoxB = (CheckBox) view.findViewById(R.id.checkBoxB);
        checkBoxC = (CheckBox) view.findViewById(R.id.checkBoxC);
        checkBoxD = (CheckBox) view.findViewById(R.id.checkBoxD);

        checkBoxes[0] = checkBoxA;
        checkBoxes[1] = checkBoxB;
        checkBoxes[2] = checkBoxC;
        checkBoxes[3] = checkBoxD;

        Button checkButton = (Button) view.findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (numberOfCorrectAnswers) {

                    case 1:
                        if (checkBoxes[correctAnswers[0]].isChecked()) {
                            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 2:
                        if (checkBoxes[correctAnswers[0]].isChecked() &&
                                checkBoxes[correctAnswers[1]].isChecked()) {
                            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 3:
                        if (checkBoxes[correctAnswers[0]].isChecked() &&
                                checkBoxes[correctAnswers[1]].isChecked() &&
                                checkBoxes[correctAnswers[2]].isChecked()) {
                            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 4:
                        if (checkBoxes[correctAnswers[0]].isChecked() &&
                                checkBoxes[correctAnswers[1]].isChecked() &&
                                checkBoxes[correctAnswers[2]].isChecked() &&
                                checkBoxes[correctAnswers[3]].isChecked()) {
                            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        Toast.makeText(getContext(), "No Correct Answers?", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        return view;
    }

    private void buildQuestionLogic(int questionNumber) {

        switch (questionNumber) {

            case 0:
                numberOfCorrectAnswers = 2;
                correctAnswers = new int[numberOfCorrectAnswers];
                correctAnswers[0] = 1;
                correctAnswers[1] = 3;
                break;

            case 1:
                numberOfCorrectAnswers = 1;
                correctAnswers = new int[numberOfCorrectAnswers];
                correctAnswers[0] = 1;
                break;

            case 2:
                numberOfCorrectAnswers = 1;
                correctAnswers = new int[numberOfCorrectAnswers];
                correctAnswers[0] = 0;
                break;



        }

    }

}
