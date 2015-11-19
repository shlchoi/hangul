package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.adapters.NumbersViewPagerAdapater;
import ca.uwaterloo.sh6choi.korea101r.adapters.VocabViewPagerAdapter;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.vocab.VocabListFragment;
import ca.uwaterloo.sh6choi.korea101r.services.NumberWebIntentService;

/**
 * Created by Samson on 2015-11-03.
 */
public class NumbersLookupFragment extends Fragment implements DrawerFragment {
    private String TAG = NumbersLookupFragment.class.getCanonicalName();
    private String FRAGMENT_TAG = MainActivity.TAG + ".fragment.numbers.lookup";

    private ViewPager mNumbersViewPager;
    private PagerTabStrip mNumbersPagerTabString;

    private NumbersViewPagerAdapater mNumbersViewPagerAdapater;

    public static NumbersLookupFragment getInstance(Bundle args) {
        NumbersLookupFragment fragment = new NumbersLookupFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View contentView = inflater.inflate(R.layout.fragment_numbers_lookup, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNumbersViewPager = (ViewPager) view.findViewById(R.id.numbers_view_pager);
        mNumbersPagerTabString = (PagerTabStrip) view.findViewById(R.id.numbers_pager_tab_strip);
        mNumbersPagerTabString.setTabIndicatorColorResource(R.color.colorAccent);
        mNumbersPagerTabString.setDrawFullUnderline(true);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(KoreanNumbersListFragment.getInstance(new Bundle()));
        fragments.add(SinoKoreanNumbersListFragment.getInstance(new Bundle()));

        mNumbersViewPagerAdapater = new NumbersViewPagerAdapater(getChildFragmentManager(), fragments);
        mNumbersViewPager.setAdapter(mNumbersViewPagerAdapater);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_refresh_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
//                mPinyinSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //    @Override
    public void onRefresh() {
        Intent intent = new Intent(getContext(), NumberWebIntentService.class);
        getContext().startService(intent);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.numbers_lookup;
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_NUMBERS_TIME);

        startActivity(intent);
        return true;
    }
}
