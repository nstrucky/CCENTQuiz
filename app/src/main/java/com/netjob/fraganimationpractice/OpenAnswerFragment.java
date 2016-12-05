package com.netjob.fraganimationpractice;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenAnswerFragment extends Fragment {

    private String questionTextFromBundle;
    private TextView questionTextView;

    private EditText editTextOpenAnswer;

    private int questionNumberKey;
    private String correctAnswer;
    private String userInput;

    private ImageButton checkButton;

    public OpenAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_open_answer, container, false);

        editTextOpenAnswer = (EditText) view.findViewById(R.id.editText_open_answer);

        questionNumberKey = getArguments().getInt(MainActivity.BUNDLE_QUESTION_NUMBER_KEY);

        setCorrectAnswer(questionNumberKey);

        questionTextFromBundle = getArguments().getString(MainActivity.BUNDLE_STRING_KEY, "Error");
        questionTextView = (TextView) view.findViewById(R.id.textview_question_open);
        questionTextView.setText(questionTextFromBundle);

        checkButton = (ImageButton) view.findViewById(R.id.imageButton_open_check);
        checkButton.setOnClickListener(new OpenCheckButtonListener());


        return view;
    }

    private void setCorrectAnswer(int questionNumber) {

        switch (questionNumber) {

            case 0:
                correctAnswer = "open shortest path first";
                break;

            case 1:
                correctAnswer = "show ip ospf database";
                break;

            case 2:
                correctAnswer = "show ip ospf interface brief";
                break;

        }
    }

    class OpenCheckButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            userInput = editTextOpenAnswer.getText().toString().trim();

            if (userInput.equalsIgnoreCase(correctAnswer)) {
                MainActivity.score += 1;
                Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), "Incorrect :(", Toast.LENGTH_SHORT).show();
            }

            editTextOpenAnswer.setEnabled(false);
            editTextOpenAnswer.setAlpha(0.5f);

            checkButton.setClickable(false);
            checkButton.setAlpha(0.5f);
        }
    }

}
