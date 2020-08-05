package com.jwisozk.imagesrendering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String CHECKBOX = "checkbox_";
    private final static String IMAGE_VIEW = "imageView_";
    private final static String IMG_FORMAT = ".png";
    private final static String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new SelectFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            setOptionsActionBar(false, R.string.select_fragment_label);
            return true;
        }

        if (id == R.id.action_send) {
            List<String> imgNameList = getImgNameCheckedList();
            ResultFragment resultFragment = ResultFragment.newInstance(imgNameList);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, resultFragment);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }

        if (id == R.id.action_reset) {
            resetCheckedImg();
            return true;
        }

        if (id == R.id.action_random) {
            setCheckImg();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<String> getImgNameCheckedList() {
        List<String> imgNameList = new ArrayList<>();
        ViewGroup wrapper = findViewById(R.id.wrapper);
        for (int i = 1; i <= wrapper.getChildCount(); i++) {
            int resID = getResources().getIdentifier(CHECKBOX + i, ID, getPackageName());
            CheckBox checkBox = findViewById(resID);
            if (checkBox.isChecked()) {
                imgNameList.add(i + IMG_FORMAT);
            }
        }
        return imgNameList;
    }

    private void resetCheckedImg() {
        ViewGroup wrapper = findViewById(R.id.wrapper);
        for (int i = 1; i <= wrapper.getChildCount(); i++) {
            int resID = getResources().getIdentifier(CHECKBOX + i, ID, getPackageName());
            CheckBox checkBox = findViewById(resID);
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
            }
        }
    }

    private void setCheckImg() {
        ViewGroup wrapper = findViewById(R.id.wrapper);
        for (int i = 1; i <= wrapper.getChildCount(); i++) {
            int resID = getResources().getIdentifier(CHECKBOX + i, ID, getPackageName());
            CheckBox checkBox = findViewById(resID);
            checkBox.setChecked(Math.random() < 0.5);
        }
    }

    public void setImages(List<String> imgNameList, View rootView) {
        if (imgNameList != null) {
            AssetManager assetManager = getAssets();
            for (int i = 0; i < imgNameList.size(); i++) {
                Bitmap bm = getBitmapFromAsset(assetManager, imgNameList.get(i));
                if (bm != null) {
                    int resID = getResources().getIdentifier(IMAGE_VIEW + (i + 1), MainActivity.ID, getPackageName());
                    ImageView img = rootView.findViewById(resID);
                    img.setImageBitmap(bm);
                }
            }
        }
    }

    public void setOptionsActionBar(boolean isBackButton, int title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isBackButton);
            actionBar.setHomeButtonEnabled(isBackButton);
            actionBar.setTitle(title);
        }
    }

    private Bitmap getBitmapFromAsset(AssetManager assetManager, String strName) {
        try {
            InputStream inputStream = assetManager.open(strName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}