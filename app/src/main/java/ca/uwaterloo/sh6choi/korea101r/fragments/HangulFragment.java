package ca.uwaterloo.sh6choi.korea101r.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-09-22.
 */
public class HangulFragment extends Fragment implements DrawerFragment, View.OnClickListener, View.OnTouchListener {

    private static final String TAG = HangulFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hangul";

    private String[] mCharacterSet;
    private String[] mPronounciationSet;
    private int mCurIndex = -1;

    private TextView mCharacterTextView;
    private TextView mHintTextView;

    public static HangulFragment getInstance(Bundle args) {
        HangulFragment fragment = new HangulFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_hangul, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCharacterSet = getResources().getStringArray(R.array.hangulChars);
        mPronounciationSet = getResources().getStringArray(R.array.hangulPronunciations);

        mCharacterTextView = (TextView) view.findViewById(R.id.character_text_view);
        mCharacterTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mCharacterTextView.setOnTouchListener(this);

        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);

        view.findViewById(R.id.hangul_fragment_relative_layout).setOnClickListener(this);

        switchCharacter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hangul_fragment_relative_layout:
                switchCharacter();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.character_text_view:
                        mHintTextView.setVisibility(View.VISIBLE);
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.character_text_view:
                        mHintTextView.setVisibility(View.GONE);
                        return true;
                }
                break;
        }
        return false;
    }

    private void switchCharacter() {
        Random random = new Random(new Date().getTime());

        int nextInt;
        do {
            nextInt = random.nextInt(mCharacterSet.length);
        } while (nextInt == mCurIndex);

        mCurIndex = nextInt;
        mCharacterTextView.setText(mCharacterSet[mCurIndex]);
        mHintTextView.setText(mPronounciationSet[mCurIndex]);
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
    public boolean onBackPressed() {
        return false;
    }
}
