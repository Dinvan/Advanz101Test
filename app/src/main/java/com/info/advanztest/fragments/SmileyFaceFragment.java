package com.info.advanztest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dbagv_000.advanz101test.R;
import com.google.android.gms.maps.GoogleMap;
import com.info.advanztest.utility.ui.SmileDrawingView;

public class SmileyFaceFragment extends Fragment implements View.OnClickListener {

    Button btnSad, btnHappy, btnClear;
    SmileDrawingView smileDrawingView;

    public SmileyFaceFragment() {
        // Required empty public constructor
    }

    public static SmileyFaceFragment newInstance() {
        SmileyFaceFragment fragment = new SmileyFaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_smiley_face, container, false);
        initializeView(rootView);
        return rootView;
    }

    public void initializeView(View rootView) {
        smileDrawingView = (SmileDrawingView) rootView.findViewById(R.id.smileDrawingView);
        btnSad = (Button) rootView.findViewById(R.id.btnSad);
        btnHappy = (Button) rootView.findViewById(R.id.btnHappy);
        btnClear = (Button) rootView.findViewById(R.id.btnClear);

        btnSad.setOnClickListener(this);
        btnHappy.setOnClickListener(this);
        btnClear.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHappy:
                smileDrawingView.movePath();
                break;
            case R.id.btnSad:
                smileDrawingView.movePath();
                break;
            case R.id.btnClear:
                smileDrawingView.clear();
                break;
        }
    }
}
