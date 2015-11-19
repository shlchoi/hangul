package ca.uwaterloo.sh6choi.korea101r.fragments.vocab;

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
import ca.uwaterloo.sh6choi.korea101r.adapters.VocabViewPagerAdapter;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.services.VocabWebIntentService;

/**
 * Created by Samson on 2015-10-27.
 */
public class VocabLookupFragment extends Fragment implements DrawerFragment  {
    private static final String TAG = VocabLookupFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.kana_lookup";


    public static final String EXTRA_VOCAB_WORD = TAG + ".extras.vocab_word";

    private ViewPager mVocabViewPager;
    private PagerTabStrip mVocabPagerTabStrip;
    private VocabViewPagerAdapter mVocabViewPagerAdapter;

    public static VocabLookupFragment getInstance(Bundle args) {
        VocabLookupFragment fragment = new VocabLookupFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View contentView = inflater.inflate(R.layout.fragment_vocab_lookup, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVocabViewPager = (ViewPager) view.findViewById(R.id.vocab_view_pager);
        mVocabPagerTabStrip = (PagerTabStrip) view.findViewById(R.id.vocab_pager_tab_strip);
        mVocabPagerTabStrip.setTabIndicatorColorResource(R.color.colorAccent);
        mVocabPagerTabStrip.setDrawFullUnderline(true);

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 1; i < 6; i ++) {
            Bundle args = new Bundle();
            args.putInt(VocabListFragment.ARG_LESSON_ID, i);
            fragments.add(VocabListFragment.getInstance(args));
        }

        mVocabViewPagerAdapter = new VocabViewPagerAdapter(getChildFragmentManager(), fragments);
        mVocabViewPager.setAdapter(mVocabViewPagerAdapter);
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
        Intent intent = new Intent(getContext(), VocabWebIntentService.class);
        getContext().startService(intent);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_vocab;
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_VOCAB);

        startActivity(intent);
        return true;
    }
}
