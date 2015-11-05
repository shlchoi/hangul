package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.adapters.NumbersAdapter;
import ca.uwaterloo.sh6choi.korea101r.utils.NumberUtils;

/**
 * Created by Samson on 2015-11-03.
 */
public abstract class NumbersListFragment extends Fragment {
    private RecyclerView mListRecyclerView;
    private NumbersAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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

    protected abstract List<String> getNumbers(int maxNum);
}
