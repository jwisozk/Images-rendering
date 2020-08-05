package com.jwisozk.imagesrendering;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        MainActivity activity = (MainActivity) getActivity();
        AssetManager assetManager = getResources().getAssets();
        String[] imgNameArray = getAllImagesFromAssets(assetManager);
        if (activity != null && imgNameArray != null) {
            List<String> imgNameList = new ArrayList<>(Arrays.asList(imgNameArray));
            activity.setImages(imgNameList, rootView);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null)
            getActivity().setTitle(R.string.select_fragment_label);
    }

    private String[] getAllImagesFromAssets(AssetManager assetManager) {
        try {
            return assetManager.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}