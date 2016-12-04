package com.netjob.fraganimationpractice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    private Button checkButton;

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
        questionTextView = (TextView) view.findViewById(R.id.textview_question);
        questionTextView.setText(questionTextFromBundle);

        checkButton = (Button) view.findViewById(R.id.checkButtonOpen);
        checkButton.setOnClickListener(new OpenCheckButtonListener());


        return view;
    }

    private void setCorrectAnswer(int questionNumber) {

        switch (questionNumber) {

            case 0:
                correctAnswer = "answer!";
                break;

            case 1:
                correctAnswer = "answer!";
                break;

            case 2:
                correctAnswer = "answer!";
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
        }
    }

}
