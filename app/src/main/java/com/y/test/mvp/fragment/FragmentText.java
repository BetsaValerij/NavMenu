package com.y.test.mvp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.y.test.R;

import androidx.fragment.app.Fragment;

public class FragmentText extends Fragment {
    private static String ARG_TEXT_VALUE = "value";
    private TextView txtView;
    private static String txtValue;
    public static Fragment newInstance(String value){
        FragmentText fragment = new FragmentText();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TEXT_VALUE, value);
        fragment.setArguments(arguments);
        fragment.txtValue = value;
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_TEXT_VALUE, txtValue);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            txtValue = savedInstanceState.getString(ARG_TEXT_VALUE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState){
        View v = inflater.inflate(R.layout.fragment_text, parent, false);
        txtView = (TextView)v.findViewById(R.id.txtValue);
        if (savedInstanseState != null) {
            txtValue = savedInstanseState.getString(ARG_TEXT_VALUE);
        }
        txtView.setText(txtValue);
        return v;
    }


}
