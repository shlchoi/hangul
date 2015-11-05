package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

import android.os.Bundle;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.utils.NumberUtils;

/**
 * Created by Samson on 2015-11-02.
 */
public class SinoKoreanNumbersFragment extends NumbersFragment {
    private static final String TAG = KoreanNumbersFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.numbers.sino_korean";

    public static SinoKoreanNumbersFragment getInstance(Bundle args) {
        SinoKoreanNumbersFragment fragment = new SinoKoreanNumbersFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected String getAnswer(int number) {
        return NumberUtils.getSinoKoreanNumber(number);
    }

    @Override
    protected int getMaxInt() {
        return 20;
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.numbers_sino_korean;
    }
}
