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
import java.util.Random;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.utils.CharacterType;
import ca.uwaterloo.sh6choi.korea101r.utils.ConjugationType;
import ca.uwaterloo.sh6choi.korea101r.utils.HangulUtils;
import ca.uwaterloo.sh6choi.korea101r.utils.SpeechForm;

/**
 * Created by Samson on 2015-10-01.
 */
public class ConjugationFragment extends Fragment implements DrawerFragment, View.OnClickListener, View.OnTouchListener {
    private static final String TAG = ConjugationFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.conjugation";

    private String[] mVerbSet;
    private SpeechForm mSpeechForm = SpeechForm.FORMAL_POLITE;
    private ConjugationType mConjugationType;

    private int mCurIndex = -1;

    private TextView mBasicVerbTextView;
    private TextView mSpeechFormConjugationTextView;
    private EditText mInputEditText;
    private TextView mHintTextView;
    private Button mCheckButton;

    public static ConjugationFragment getInstance(Bundle args) {
        ConjugationFragment fragment = new ConjugationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_conjugation, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVerbSet = getResources().getStringArray(R.array.verb_set);

        mBasicVerbTextView = (TextView) view.findViewById(R.id.basic_verb_text_view);
        mBasicVerbTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBasicVerbTextView.setOnTouchListener(this);

        mSpeechFormConjugationTextView = (TextView) view.findViewById(R.id.speech_form_conjugation_text_view);

        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);

        mInputEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mInputEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mCheckButton = (Button) view.findViewById(R.id.check_button);
        mCheckButton.setOnClickListener(this);

        switchVerb();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.check_button:
                String answer = HangulUtils.conjugatePresent(mVerbSet[mCurIndex], mSpeechForm, mConjugationType);
                if (TextUtils.equals(mInputEditText.getText(), answer)) {
                    switchVerb();
                } else {
                    mInputEditText.setError("Incorrect");
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.basic_verb_text_view:
                        mHintTextView.setVisibility(View.VISIBLE);
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.basic_verb_text_view:
                        mHintTextView.setVisibility(View.INVISIBLE);
                        return true;
                }
                break;
        }
        return false;
    }

    private void switchVerb() {
        Random random = new Random(new Date().getTime());

        mConjugationType = ConjugationType.values()[new Random(new Date().getTime()).nextInt(4)];

        int nextInt;
        do {
            nextInt = random.nextInt(mVerbSet.length);
        } while (nextInt == mCurIndex);

        mCurIndex = nextInt;

        mBasicVerbTextView.setText(mVerbSet[mCurIndex]);
        mSpeechFormConjugationTextView.setText(getString(mSpeechForm.getStringResId()) + " - " + getString(mConjugationType.getStringResId()));

        CharacterType characterType = HangulUtils.getCharacterType(mVerbSet[mCurIndex].charAt(mVerbSet[mCurIndex].length() - 2));
        if (characterType == CharacterType.TYPE_1 || characterType == CharacterType.TYPE_3) {
            mHintTextView.setText(mConjugationType.getVowelHintStringResId());
        } else {
            mHintTextView.setText(mConjugationType.getVowelHintStringResId());
        }

        mInputEditText.setText("");
        mInputEditText.setError(null);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_conjugation;
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
