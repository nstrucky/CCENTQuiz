package com.netjob.fraganimationpractice;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
    private ImageButton checkButton;

    public MultiAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_multi_answer, container, false);

        questonNumberKey = getArguments().getInt(MainActivity.BUNDLE_QUESTION_NUMBER_KEY);

        setCorrectAnswers(questonNumberKey);

        questionTextFromBundle = getArguments().getString(MainActivity.BUNDLE_STRING_KEY, "Error");
        questionTextView = (TextView) view.findViewById(R.id.textview_question_multi);
        questionTextView.setText(questionTextFromBundle);

        checkBoxes = new CheckBox[numberOfCheckBoxes];

        checkBoxA = (CheckBox) view.findViewById(R.id.checkBoxA);
        checkBoxB = (CheckBox) view.findViewById(R.id.checkBoxB);
        checkBoxC = (CheckBox) view.findViewById(R.id.checkBoxC);
        checkBoxD = (CheckBox) view.findViewById(R.id.checkBoxD);

        checkBoxes[0] = checkBoxA;
        checkBoxes[1] = checkBoxB;
        checkBoxes[2] = checkBoxC;
        checkBoxes[3] = checkBoxD;

        checkButton = (ImageButton) view.findViewById(R.id.imageButton_multi_check);
        checkButton.setOnClickListener(new CheckButtonListener());

        return view;
    }

    private void setCorrectAnswers(int questionNumber) {

        switch (questionNumber) {

            case 0:
                numberOfCorrectAnswers = 3;
                correctAnswers = new int[numberOfCorrectAnswers];
                correctAnswers[0] = 1;
                correctAnswers[1] = 2;
                correctAnswers[2] = 3;
                break;

            case 1:
                numberOfCorrectAnswers = 2;
                correctAnswers = new int[numberOfCorrectAnswers];
                correctAnswers[0] = 1;
                correctAnswers[1] = 2;
                break;

            case 2:
                numberOfCorrectAnswers = 2;
                correctAnswers = new int[numberOfCorrectAnswers];
                correctAnswers[0] = 1;
                correctAnswers[1] = 3;
                break;
        }
    }

    class CheckButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (numberOfCorrectAnswers) {

                case 1:
                    if (checkBoxes[correctAnswers[0]].isChecked()) {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                        MainActivity.score += 1;

                    } else {
                        Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 2:
                    if (checkBoxes[correctAnswers[0]].isChecked() &&
                            checkBoxes[correctAnswers[1]].isChecked()) {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                        MainActivity.score += 1;

                    } else {
                        Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 3:
                    if (checkBoxes[correctAnswers[0]].isChecked() &&
                            checkBoxes[correctAnswers[1]].isChecked() &&
                            checkBoxes[correctAnswers[2]].isChecked()) {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                        MainActivity.score += 1;

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
                        MainActivity.score += 1;

                    } else {
                        Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    Toast.makeText(getContext(), "No Correct Answers?", Toast.LENGTH_SHORT).show();
                    break;

            }

            for (CheckBox checkbox : checkBoxes) {
                checkbox.setClickable(false);
                checkbox.setAlpha(0.5f);
            }

            checkButton.setClickable(false);
            checkButton.setAlpha(0.5f);

        }
    }
}
