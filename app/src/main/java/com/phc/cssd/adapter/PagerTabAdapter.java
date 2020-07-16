package com.phc.cssd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.phc.cssd.ApproveStockTab1;

public class PagerTabAdapter  extends FragmentPagerAdapter {
    int mNumOfTabs;
    private AppCompatActivity context;

    public PagerTabAdapter(AppCompatActivity aActivity, FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = aActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ApproveStockTab1 tab1 = new ApproveStockTab1();
                return tab1;
            case 1:
                ApproveStockTab2 tab2 = new ApproveStockTab2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}