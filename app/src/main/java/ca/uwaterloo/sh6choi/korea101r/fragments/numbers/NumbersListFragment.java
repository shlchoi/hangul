package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

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

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.adapters.NumbersAdapter;
import ca.uwaterloo.sh6choi.korea101r.services.NumberWebIntentService;
import ca.uwaterloo.sh6choi.korea101r.utils.NumberUtils;

/**
 * Created by Samson on 2015-11-03.
 */
public abstract class NumbersListFragment extends Fragment {
    private RecyclerView mListRecyclerView;
    private NumbersAdapter mAdapter;
    private BroadcastReceiver mSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                //mPinyinSwipeRefreshLayout.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.fragment_view_pager_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mListRecyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanCount(3);
        GridLayoutManager.SpanSizeLookup lookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 2 == 1) {
                    return 2;
                }
                return 1;
            }
        };
        manager.setSpanSizeLookup(lookup);
        mListRecyclerView.setLayoutManager(manager);

        List<String> numbers = getNumbers(20);

        mAdapter = new NumbersAdapter(numbers);
        mListRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter successFilter = new IntentFilter();
        successFilter.addAction(NumberWebIntentService.ACTION_SUCCESS);
        getContext().registerReceiver(mSuccessReceiver, successFilter);
    }

    @Override
    public void onPause() {
        getContext().unregisterReceiver(mSuccessReceiver);
        super.onPause();
    }

    protected abstract List<String> getNumbers(int maxNum);
}
