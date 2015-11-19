package ca.uwaterloo.sh6choi.korea101r.fragments.vocab;

import android.content.Intent;
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
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;
import ca.uwaterloo.sh6choi.korea101r.model.VocabWord;
import ca.uwaterloo.sh6choi.korea101r.presentation.VocabSetPresenter;
import ca.uwaterloo.sh6choi.korea101r.utils.HangulUtils;

/**
 * Created by Samson on 2015-11-10.
 */
public class VocabQuizFragment extends Fragment implements DrawerFragment, View.OnClickListener,
        View.OnTouchListener, VocabSetPresenter.VocabSetView {
    private static final String TAG = VocabQuizFragment.class.getCanonicalName();
    private static final String FRAGMENT_TAG = MainActivity.TAG + ".quiz.hiragana_entry";

    private VocabSet mVocabSet;
    protected VocabSetPresenter mPresenter;

    protected VocabWord mCurWord;

    private TextView mDefinitionTextView;
    private EditText mAnswerEditText;
    private TextView mHintTextView;
    private Button mCheckButton;


    public static VocabQuizFragment getInstance(Bundle args) {
        VocabQuizFragment fragment = new VocabQuizFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.fragment_text_entry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDefinitionTextView = (TextView) view.findViewById(R.id.prompt_text_view);
        mAnswerEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);
        mCheckButton = (Button) view.findViewById(R.id.check_button);

        view.findViewById(R.id.text_entry_linear_layout).setOnTouchListener(this);
        mDefinitionTextView.setOnTouchListener(this);
        mCheckButton.setOnClickListener(this);

        mPresenter = new VocabSetPresenter(getContext(), this);
        mPresenter.obtainVocabulary("noun", "verb", "adverb", "additional", "noun modifier", "pronoun");
    }

    @Override
    public void refreshVocabSet(VocabSet vocabSet) {
        mVocabSet = vocabSet;
        switchWord();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_button:
                if (TextUtils.equals(mAnswerEditText.getText(), mCurWord.getHangul())) {
                    switchWord();
                } else {
                    mAnswerEditText.setError("Incorrect");
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.prompt_text_view:
                        mHintTextView.setVisibility(View.VISIBLE);
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.prompt_text_view:
                    case R.id.text_entry_linear_layout:
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
            nextInt = random.nextInt(mVocabSet.getWords().length);
        } while (mVocabSet.getWords()[nextInt] == mCurWord && !TextUtils.equals(mVocabSet.getWords()[nextInt].getDefinitions()[0], mCurWord.getDefinitions()[0]));

        mCurWord = mVocabSet.getWords()[nextInt];

        mDefinitionTextView.setText(mCurWord.getDefinitions()[0]);
        mHintTextView.setText(HangulUtils.romanize(mCurWord.getHangul()));

        mAnswerEditText.setText("");
        mAnswerEditText.setError(null);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.vocab_quiz;
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_VOCAB);

        startActivity(intent);
        return true;
    }

}
