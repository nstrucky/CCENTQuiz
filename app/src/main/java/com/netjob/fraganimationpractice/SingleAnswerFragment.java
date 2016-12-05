package com.netjob.fraganimationpractice;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleAnswerFragment extends Fragment {

    private String questionTextFromBundle;
    private TextView questionTextView;

    private int questionNumberKey;
    private int correctAnswer;
    private ImageButton checkButton;

    private RadioGroup radioGroup;
    private RadioButton radioButtonA;
    private RadioButton radioButtonB;
    private RadioButton radioButtonC;
    private RadioButton radioButtonD;

    public SingleAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_single_answer, container, false);

        questionNumberKey = getArguments().getInt(MainActivity.BUNDLE_QUESTION_NUMBER_KEY);

        setCorrectAnswer(questionNumberKey);

        questionTextFromBundle = getArguments().getString(MainActivity.BUNDLE_STRING_KEY, "Error");
        questionTextView = (TextView) view.findViewById(R.id.textview_question_single);
        questionTextView.setText(questionTextFromBundle);

        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_single_answer);
        radioButtonA = (RadioButton) view.findViewById(R.id.radioButtonA);
        radioButtonB = (RadioButton) view.findViewById(R.id.radioButtonB);
        radioButtonC = (RadioButton) view.findViewById(R.id.radioButtonC);
        radioButtonD = (RadioButton) view.findViewById(R.id.radioButtonD);

        checkButton = (ImageButton) view.findViewById(R.id.imageButton_single_check);
        checkButton.setOnClickListener(new CheckButtonListener());

        return view;
    }


    private void setCorrectAnswer(int questionNumber) {

        switch (questionNumber) {

            case 0:
                correctAnswer = R.id.radioButtonC;
                break;

            case 1:
                correctAnswer = R.id.radioButtonD;
                break;

            case 2:
                correctAnswer = R.id.radioButtonB;
                break;

        }
    }

    private class CheckButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int checkedRadioButton = radioGroup.getCheckedRadioButtonId();

            if (checkedRadioButton == correctAnswer) {
                MainActivity.score += 1;
                Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
            }

            radioGroup.setAlpha(0.5f);
            radioButtonA.setClickable(false);
            radioButtonB.setClickable(false);
            radioButtonC.setClickable(false);
            radioButtonD.setClickable(false);

            checkButton.setClickable(false);
            checkButton.setAlpha(0.5f);

        }
    }



}
