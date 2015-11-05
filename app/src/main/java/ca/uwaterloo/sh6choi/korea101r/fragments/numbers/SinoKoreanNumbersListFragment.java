package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
public class SinoKoreanNumbersListFragment extends NumbersListFragment {
    private static final String TAG = SinoKoreanNumbersListFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.sino_korean_numbers_list";

    public static SinoKoreanNumbersListFragment getInstance(Bundle args) {
        SinoKoreanNumbersListFragment fragment = new SinoKoreanNumbersListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected List<String> getNumbers(int maxNum) {
        List<String> numbers = new ArrayList<>();
        for (int i = 1; i <= maxNum; i ++) {
            numbers.add(NumberUtils.getSinoKoreanNumber(i));
        }
        return numbers;
    }
}
