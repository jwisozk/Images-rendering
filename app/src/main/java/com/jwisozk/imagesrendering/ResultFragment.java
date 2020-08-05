package com.jwisozk.imagesrendering;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment {

    private static final String IMG_NAME_LIST = "imgNameList";

    public static ResultFragment newInstance(List<String> imgNameList) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IMG_NAME_LIST, (ArrayList<String>) imgNameList);
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);
        return resultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setOptionsActionBar(true, R.string.result_fragment_label);
            Bundle bundle = getArguments();
            if (bundle != null) {
                List<String> imgNameList = bundle.getStringArrayList(IMG_NAME_LIST);
                activity.setImages(imgNameList, rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.action_group,false);
    }
}