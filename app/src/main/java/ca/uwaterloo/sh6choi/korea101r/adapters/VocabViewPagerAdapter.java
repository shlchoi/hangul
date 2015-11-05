package ca.uwaterloo.sh6choi.korea101r.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Samson on 2015-10-26.
 */
public class VocabViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public VocabViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
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
        return "Lesson " + Integer.toString(position + 1);
    }
}
