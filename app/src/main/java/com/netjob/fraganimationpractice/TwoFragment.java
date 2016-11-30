package com.netjob.fraganimationpractice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {


    TextView textView2;
    String fromBundle;

    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        textView2 = (TextView) view.findViewById(R.id.text2);
        fromBundle = getArguments().getString(MainActivity.BUNDLE_STRING_KEY, "nope");
        textView2.setText(fromBundle);


        return view;
    }


}
