package ca.uwaterloo.sh6choi.korea101r.fragments;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulFlashcardFragment;

/**
 * Created by Samson on 2015-09-24.
 */
public class PronunciationFragment extends Fragment implements DrawerFragment, View.OnClickListener, View.OnTouchListener {

    private static final String TAG = HangulFlashcardFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.pronunciation";

    public static final int REQUEST_SPEECH = 1012;

    public static final String EXTRA_RESULTS = TAG + ".extra.results";

    private String[] mDictationSet;
    private String[] mDictationAnswerSet;

    private int mCurIndex = -1;

    private TextView mWordTextView;
    private EditText mInputEditText;
    private FloatingActionButton mMicInputButton;
    private TextView mHintTextView;

    private BroadcastReceiver mSpeechReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(EXTRA_RESULTS)) {
                List<String> results = intent.getStringArrayListExtra(EXTRA_RESULTS);

                if (results.contains(mDictationSet[mCurIndex])) {
                    mInputEditText.setText(mDictationSet[mCurIndex]);
                } else {
                    mInputEditText.setText(results.get(0));
                }
            }
        }
    };

    public static PronunciationFragment getInstance(Bundle args) {
        PronunciationFragment fragment = new PronunciationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_pronuncation, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IntentFilter intentFilter = new IntentFilter(MainActivity.ACTION_SPEECH);
        getContext().registerReceiver(mSpeechReceiver, intentFilter);

        mDictationSet = getResources().getStringArray(R.array.dictation_set_1_answers);
        mDictationAnswerSet = getResources().getStringArray(R.array.dictation_set_1);

        mWordTextView = (TextView) view.findViewById(R.id.word_text_view);
        mWordTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWordTextView.setOnTouchListener(this);

        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);

        mInputEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mInputEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mMicInputButton = (FloatingActionButton) view.findViewById(R.id.mic_input_fab);
        mMicInputButton.setOnClickListener(this);

        switchWord();
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mSpeechReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.mic_input_fab:
                promptSpeechInput();
                break;
        }
    }

    private void promptSpeechInput() {

        mInputEditText.setText("");
        mInputEditText.setError(null);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREA);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, mDictationSet[mCurIndex]);
        try {
            startActivityForResult(intent, REQUEST_SPEECH);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(), "Not supported", Toast.LENGTH_SHORT).show();
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
        mWordTextView.setText(mDictationSet[mCurIndex]);
        mHintTextView.setText(mDictationAnswerSet[mCurIndex]);

        mInputEditText.setText("");
        mInputEditText.setError(null);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_pronunciation;
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
