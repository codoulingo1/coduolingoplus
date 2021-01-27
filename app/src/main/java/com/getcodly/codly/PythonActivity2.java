package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PythonActivity2 extends AppCompatActivity {

    private pythonCode CodeFrament;
    private pythonRun BrowserFragment;
    public static boolean p = false;
    public static String pyCodeParent;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python2);
        p = false;
        ViewPager viewPager = findViewById(R.id.view_pager6000);
        tabs = (TabLayout) findViewById(R.id.tabs6000);
        tabs.setupWithViewPager(viewPager);

        CodeFrament = new pythonCode();
        BrowserFragment = new pythonRun();

        PythonActivity2.ViewPagerAdapter viewPagerAdapter = new PythonActivity2.ViewPagerAdapter(getSupportFragmentManager(), 0);

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
        switch (item.getItemId()) {
            case R.id.saveProject:
                Toast.makeText(PythonActivity2.this, "Hello", Toast.LENGTH_SHORT);
                Log.d("worked", pythonCode.htmlInp.getText().toString()); //codeFragment.htmlInp.getText().toString is the file which is supposed to be saved/shared
                pyCodeParent = pythonCode.htmlInp.getText().toString();
                openDialog();

                return true;
            case R.id.shareProject:
                //Toast.makeText(PythonActivity2.this, "Shared", Toast.LENGTH_SHORT).show();
                pyCodeParent = pythonCode.htmlInp.getText().toString();
                p = true;
                openDialog();
                Toast.makeText(PythonActivity2.this, "Shared", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog(){
        pySaveDialog saveDialog = new pySaveDialog();
        saveDialog.show(getSupportFragmentManager(), "save dialog");
    }
}
