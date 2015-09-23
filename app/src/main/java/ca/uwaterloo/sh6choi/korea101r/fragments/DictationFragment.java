package ca.uwaterloo.sh6choi.korea101r.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-09-22.
 */
public class DictationFragment extends Fragment implements DrawerFragment, View.OnClickListener, View.OnTouchListener {

    private static final String TAG = HangulFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.dictation";

    private String[] mDictationSet;
    private String[] mDictationAnswerSet;

    private int mCurIndex = -1;

    private TextView mDictationWordTextView;
    private EditText mDictationInputEditText;
    private Button mDictationCheckButton;
    private TextView mHintTextView;

    public static DictationFragment getInstance(Bundle args) {
        DictationFragment fragment = new DictationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_dictation, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDictationSet = getResources().getStringArray(R.array.dictation_set_1);
        mDictationAnswerSet = getResources().getStringArray(R.array.dictation_set_1_answers);

        mDictationWordTextView = (TextView) view.findViewById(R.id.word_text_view);
        mDictationWordTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mDictationWordTextView.setOnTouchListener(this);

        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);

        mDictationInputEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mDictationInputEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mDictationCheckButton = (Button) view.findViewById(R.id.dictation_check_button);
        mDictationCheckButton.setOnClickListener(this);

        switchWord();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.dictation_check_button:
                if (TextUtils.equals(mDictationInputEditText.getText(), mDictationAnswerSet[mCurIndex])) {
                    switchWord();
                } else {
                    mDictationInputEditText.setError("Incorrect");
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.word_text_view:
                        mHintTextView.setVisibility(View.VISIBLE);
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.word_text_view:
                        mHintTextView.setVisibility(View.INVISIBLE);
                        return true;
                }
                break;
        }
        return false;
    }

    private void switchWord() {
        Random random = new Random(new Date().getTime());

        int nextInt;
        do {
            nextInt = random.nextInt(mDictationSet.length);
        } while (nextInt == mCurIndex);

        mCurIndex = nextInt;
        mDictationWordTextView.setText(mDictationSet[mCurIndex]);
        mHintTextView.setText(mDictationAnswerSet[mCurIndex]);

        mDictationInputEditText.setText("");
        mDictationInputEditText.setError(null);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_dictation;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
