package com.netjob.fraganimationpractice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleAnswerFragment extends Fragment {

    private int questionNumberKey;
    private int correctAnswer;
    private Button checkButton;


    String questionTextFromBundle;
    TextView questionTextView;

    public SingleAnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_single_answer, container, false);

        questionTextFromBundle = getArguments().getString(MainActivity.BUNDLE_STRING_KEY, "Error");
        questionTextView = (TextView) view.findViewById(R.id.textview_question);
        questionTextView.setText(questionTextFromBundle);

        checkButton = (Button) view.findViewById(R.id.checkButtonSingle);

        return view;
    }


    public void radioButtonListener(View view) {

        RadioButton radioButton = (RadioButton) view;

        switch (questionNumberKey) {




        }


    }

}
