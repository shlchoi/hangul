package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;

/**
 * Created by Samson on 2015-11-02.
 */
public class NumberTimeFragment extends Fragment implements DrawerFragment, View.OnClickListener {

    private static final String TAG = NumberTimeFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.numbers.time";

    private Button mLookupButton;
    private Button mKoreanNumbersButton;
    private Button mSinoKoreanNumbersButton;
    private Button mTimeButton;

    public static NumberTimeFragment getInstance(Bundle args) {
        NumberTimeFragment fragment = new NumberTimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        View contentView = inflater.inflate(R.layout.fragment_numbers_time, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLookupButton = (Button) view.findViewById(R.id.lookup_button);
        mKoreanNumbersButton = (Button) view.findViewById(R.id.korean_numbers_button);
        mSinoKoreanNumbersButton = (Button) view.findViewById(R.id.sino_korean_numbers_button);
        mTimeButton = (Button) view.findViewById(R.id.time_button);

        mLookupButton.setOnClickListener(this);
        mKoreanNumbersButton.setOnClickListener(this);
        mSinoKoreanNumbersButton.setOnClickListener(this);
        mTimeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lookup_button:
                onLookupButtonClicked();
                break;
            case R.id.korean_numbers_button:
                onKoreanNumbersButton();
                break;
            case R.id.sino_korean_numbers_button:
                onSinoKoreanNumbersButton();
                break;
            case R.id.time_button:
                onTimeButton();
                break;
        }
    }

    private void onLookupButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_NUMBERS_LOOKUP);
        startActivity(intent);
    }

    private void onKoreanNumbersButton() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_KOREAN_NUMBERS);
        startActivity(intent);
    }

    private void onSinoKoreanNumbersButton() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_SINO_KOREAN_NUMBERS);
        startActivity(intent);
    }

    private void onTimeButton() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_TIME);
        startActivity(intent);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_number_time;
    }

    @Override
    public boolean shouldShowUp() {
        return false;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
