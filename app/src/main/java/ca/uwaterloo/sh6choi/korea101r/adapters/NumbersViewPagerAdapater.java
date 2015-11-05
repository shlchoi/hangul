package ca.uwaterloo.sh6choi.korea101r.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Samson on 2015-11-03.
 */
public class NumbersViewPagerAdapater extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public NumbersViewPagerAdapater(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);

        mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Korean";
        } else if (position == 1) {
            return "Sino-Korean";
        }

        return "";
    }
}
