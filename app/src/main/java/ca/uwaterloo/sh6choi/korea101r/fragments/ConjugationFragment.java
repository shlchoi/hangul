package ca.uwaterloo.sh6choi.korea101r.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;
import ca.uwaterloo.sh6choi.korea101r.model.VocabWord;
import ca.uwaterloo.sh6choi.korea101r.presentation.VocabSetPresenter;
import ca.uwaterloo.sh6choi.korea101r.utils.ConjugationForm;
import ca.uwaterloo.sh6choi.korea101r.utils.HangulUtils;
import ca.uwaterloo.sh6choi.korea101r.utils.SpeechForm;
import ca.uwaterloo.sh6choi.korea101r.utils.VerbTense;

/**
 * Created by Samson on 2015-10-01.
 */
public class ConjugationFragment extends Fragment implements DrawerFragment, View.OnClickListener, VocabSetPresenter.VocabSetView {
    private static final String TAG = ConjugationFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.conjugation";

    private VocabSet mVerbSet;
    private VocabSetPresenter mPresenter;

    private int mCurIndex = -1;
    private int mCurForm = -1;

    private TextView mBasicVerbTextView;
    private TextView mSpeechFormConjugationTextView;
    private EditText mInputEditText;
    private Button mCheckButton;

    public static ConjugationFragment getInstance(Bundle args) {
        ConjugationFragment fragment = new ConjugationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        View contentView = inflater.inflate(R.layout.fragment_conjugation, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVerbSet = new VocabSet(0, new VocabWord[0]);

        mBasicVerbTextView = (TextView) view.findViewById(R.id.basic_verb_text_view);
        mBasicVerbTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mSpeechFormConjugationTextView = (TextView) view.findViewById(R.id.speech_form_conjugation_text_view);

        mInputEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mInputEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mCheckButton = (Button) view.findViewById(R.id.check_button);
        mCheckButton.setOnClickListener(this);

        mPresenter = new VocabSetPresenter(getActivity(), this);
        mPresenter.obtainVocabulary("verb");
    }

    @Override
    public void refreshVocabSet(VocabSet vocabSet) {
        mVerbSet = vocabSet;
        if (mVerbSet.getWords().length > 0) {
            switchVerb();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.check_button:
                String answer = HangulUtils.conjugate(mVerbSet.getWords()[mCurIndex].getHangul(), isPositive(mCurForm),
                        getVerbTense(mCurForm), isHonorific(mCurForm), getConjugationForm(mCurForm),
                        SpeechForm.INFORMAL_POLITE);

                if (TextUtils.equals(mInputEditText.getText(), answer)) {
                    switchVerb();
                } else {
                    mInputEditText.setError("Incorrect");
                }
                break;
        }
    }

    private void switchVerb() {
        Random random = new Random(new Date().getTime());

        int nextForm;
        int nextInt;
        do {
            nextForm = random.nextInt(255);
            nextInt = random.nextInt(mVerbSet.getWords().length);
        } while (nextInt == mCurIndex && nextForm == mCurForm);

        mCurIndex = nextInt;
        mCurForm = nextForm;

        mBasicVerbTextView.setText(mVerbSet.getWords()[mCurIndex].getHangul());

        VerbTense verbTense = getVerbTense(mCurForm);

        String positiveString = "";
        if (isPositive(mCurForm)) {
            positiveString = getString(R.string.verb_positive);
        } else {
            positiveString = getString(R.string.verb_negative);
        }

        if (isHonorific(mCurForm)) {
            mSpeechFormConjugationTextView.setText(String.format(getString(R.string.conjugation_form_honorific),
                    positiveString, getString(verbTense.getStringResId()),
                    getString(getConjugationForm(mCurForm).getStringResId()),
                    getString(getSpeechForm(mCurForm).getStringResId())));
        } else {
            mSpeechFormConjugationTextView.setText(String.format(getString(R.string.conjugation_form),
                    positiveString, getString(verbTense.getStringResId()),
                    getString(getConjugationForm(mCurForm).getStringResId()),
                    getString(getSpeechForm(mCurForm).getStringResId())));
        }

        mInputEditText.setText("");
        mInputEditText.setError(null);
    }

    private SpeechForm getSpeechForm(int form) {
        return SpeechForm.values()[(form & 0b10000000) >> 6];
    }

    private boolean isPositive(int form) {
        return (form & 0b00100000) == 0;
    }

    private VerbTense getVerbTense(int form) {
        form = form & 0b11101111;

        return VerbTense.values()[(form & 0b00011000) >> 3];
    }

    private boolean isHonorific(int form) {
        return (form & 0b00000100) == 0;
    }

    private ConjugationForm getConjugationForm(int form) {
        if (getVerbTense(form) == VerbTense.PAST) {
            form = form & 0b11111101;
        }

        return ConjugationForm.values()[form & 0b00000011];
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
