package com.example.myfirstapp;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import java.util.ArrayList;

public class TabActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        int tagId = getIntent().getIntExtra("tabId", 0);

        ArrayList<ActionBar.Tab> tabs = new ArrayList<ActionBar.Tab>();


        ActionBar.Tab tabA = actionBar.newTab();
        tabA.setText("Info");
        tabA.setTag(1);
        tabA.setTabListener(new MainTabListener<Fragment_Info>(this, "a", Fragment_Info.class));
        tabs.add(tabA);

        ActionBar.Tab tabB = actionBar.newTab();
        tabB.setText("B");
        tabB.setTag(2);
        tabB.setTabListener(new MainTabListener<Fragment_Personal>(this, "b", Fragment_Personal.class));
        tabs.add(tabB);

        ActionBar.Tab tabC = actionBar.newTab();
        tabC.setText("C");
        tabC.setTag(3);
        tabC.setTabListener(new MainTabListener<Fragment_Progress>(this, "c", Fragment_Progress.class));
        tabs.add(tabC);

        ActionBar.Tab tabD = actionBar.newTab();
        tabD.setText("D");
        tabD.setTag(4);
        tabD.setTabListener(new MainTabListener<Fragment_Training>(this, "d", Fragment_Training.class));
        tabs.add(tabD);

        for(ActionBar.Tab tab: tabs) {
            if (tab.getTag() == (Integer)tagId) {
                actionBar.addTab(tab, (Integer)tab.getTag(), true);
            } else {
                actionBar.addTab(tab, (Integer)tab.getTag(), false);
            }
        }

    }

    public class MainTabListener<T extends Fragment> implements ActionBar.TabListener{
        private Fragment fragment;
        private final Activity activity;
        private final String tag;
        private final Class<T> cls;

        public MainTabListener(Activity activity, String tag, Class<T> cls){
            this.activity = activity;
            this.tag = tag;
            this.cls = cls;
        }

        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft){
            if (tab.getPosition() == 0) {
                finish();
            }
            if (fragment == null) {
                fragment = Fragment.instantiate(activity, cls.getName());
                ft.add(android.R.id.content, fragment, tag);
            } else {
                ft.attach(fragment);
            }
        }

        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){
            if(fragment != null){
                ft.detach(fragment);
            }
        }

        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){
        }
    }

}
