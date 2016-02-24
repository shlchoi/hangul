package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.utils.NumberUtils;

/**
 * Created by Samson on 2015-11-03.
 */
public class KoreanNumbersListFragment extends NumbersListFragment {
    private static final String TAG = KoreanNumbersListFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.korean_numbers_list";

    public static KoreanNumbersListFragment getInstance(Bundle args) {
        KoreanNumbersListFragment fragment = new KoreanNumbersListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected List<String> getNumbers(int maxNum) {
        List<String> numbers = new ArrayList<>();
        for (int i = 1; i <= maxNum; i++) {
            numbers.add(NumberUtils.getKoreanNumber(i));
        }
        return numbers;
    }
}
