package com.getcodly.codly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class iframe2 extends AppCompatActivity {

    private codeFrament CodeFrament;
    private browserFragment BrowserFragment;
    public static boolean saved = false;
    public static String fileName;
    public static boolean p = false;
    private TabLayout tabs;
    public static String htmlCodeParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iframe2);
        saved = false;
        p = false;
        ViewPager viewPager = findViewById(R.id.view_pager6000);
        tabs = (TabLayout) findViewById(R.id.tabs6000);
        tabs.setupWithViewPager(viewPager);

        CodeFrament = new codeFrament();
        BrowserFragment = new browserFragment();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(CodeFrament, "תכנות");
        viewPagerAdapter.addFragment(BrowserFragment, "תצוגה");

        viewPager.setAdapter(viewPagerAdapter);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

    public void changeTab(int position){
        tabs.getTabAt(position).select();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String TAG = "iframe2";
        switch (item.getItemId()) {
            case R.id.saveProject:
                Toast.makeText(iframe2.this, "Hello", Toast.LENGTH_SHORT);
                Log.d("worked", codeFrament.htmlInp.getText().toString()); //codeFragment.htmlInp.getText().toString is the file which is supposed to be saved/shared
                htmlCodeParent = codeFrament.htmlInp.getText().toString();
                p = true;
                openDialog();

                return true;
            case R.id.shareProject:
                htmlCodeParent = codeFrament.htmlInp.getText().toString();
                p = true;
                openDialog();
                Toast.makeText(iframe2.this, "Shared", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog(){
        SaveDialog saveDialog = new SaveDialog();
        saveDialog.show(getSupportFragmentManager(), "save dialog");
    }

}