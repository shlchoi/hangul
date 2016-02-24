package ca.uwaterloo.sh6choi.korea101r.fragments.hangul;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.adapters.HangulAdapter;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;
import ca.uwaterloo.sh6choi.korea101r.presentation.HangulCharacterPresenter;
import ca.uwaterloo.sh6choi.korea101r.services.HangulWebIntentService;

/**
 * Created by Samson on 2015-09-25.
 */
public class HangulLookupFragment extends Fragment implements DrawerFragment, HangulCharacterPresenter.HangulCharacterView,
        HangulAdapter.OnItemClickListener {

    private static final String TAG = HangulLookupFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hangul_lookup";

    public static final String EXTRA_CHARACTER = TAG + ".extra.character";

    private RecyclerView mHangulRecyclerView;
    private HangulAdapter mHangulAdapter;

    private HangulCharacterPresenter mPresenter;
    private BroadcastReceiver mSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mPresenter != null) {
                //mPinyinSwipeRefreshLayout.setRefreshing(false);
                mPresenter.obtainAllCharacters();
            }
        }
    };

    public static HangulLookupFragment getInstance(Bundle args) {
        HangulLookupFragment fragment = new HangulLookupFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_hangul_lookup, container, false);
        setHasOptionsMenu(true);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHangulRecyclerView = (RecyclerView) view.findViewById(R.id.hangul_recycler_view);
        mHangulRecyclerView.setHasFixedSize(true);
        mHangulRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        mHangulAdapter = new HangulAdapter();
        mHangulAdapter.setOnItemClickListener(this);
        mHangulRecyclerView.setAdapter(mHangulAdapter);

        mPresenter = new HangulCharacterPresenter(getContext(), this);
        mPresenter.obtainAllCharacters();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter successFilter = new IntentFilter();
        successFilter.addAction(HangulWebIntentService.ACTION_SUCCESS);
        getContext().registerReceiver(mSuccessReceiver, successFilter);
    }

    @Override
    public void onPause() {
        getContext().unregisterReceiver(mSuccessReceiver);
        super.onPause();
    }

    @Override
    public void refreshHangulCharacterList(List<HangulCharacter> hangulCharacterList) {
        mHangulAdapter.setHangulCharacterList(hangulCharacterList);
    }

    @Override
    public void onItemClick(View view) {
        HangulCharacter hangulCharacter = (HangulCharacter) view.getTag();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_HANGUL_CHARACTER);
        intent.putExtra(EXTRA_CHARACTER, hangulCharacter);

        startActivity(intent);
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
        Intent hangul = new Intent(getContext(), HangulWebIntentService.class);
        getContext().startService(hangul);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_hangul;
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
        intent.setAction(MainActivity.ACTION_HANGUL);

        startActivity(intent);
        return true;
    }
}
