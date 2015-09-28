package ca.uwaterloo.sh6choi.korea101r.fragments.hangul;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;

/**
 * Created by Samson on 2015-09-25.
 */
public class HangulCharacterFragment extends Fragment implements DrawerFragment, View.OnClickListener, TextToSpeech.OnInitListener {

    private static final String TAG = HangulFlashcardFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hangul.flashcards";

    public static final String ARG_HANGUL = TAG + ".arg.hangul";
    public static final String ARG_ROMANIZATION = TAG + ".arg.romanization";
    public static final String ARG_PRONUNCIATION = TAG + ".arg.pronunciation";

    private String mHangulCharacter;
    private String mRomanization;
    private String mPronunciation;

    private TextView mCharacterTextView;
    private TextView mRomanizationTextView;
    private FloatingActionButton mPlayFab;


    private TextToSpeech mTextToSpeech;

    public static HangulFlashcardFragment getInstance(Bundle args) {
        HangulFlashcardFragment fragment = new HangulFlashcardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_hangul_flashcard, container, false);

        Bundle args = getArguments();

        if (args.containsKey(ARG_HANGUL)) {
            mHangulCharacter = args.getString(ARG_HANGUL);
        }

        if (args.containsKey(ARG_ROMANIZATION)) {
            mRomanization = args.getString(ARG_ROMANIZATION);
        }

        if (args.containsKey(ARG_PRONUNCIATION)) {
            mPronunciation = args.getString(ARG_PRONUNCIATION);
        }

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextToSpeech = new TextToSpeech(getActivity(), this);

        mCharacterTextView = (TextView) view.findViewById(R.id.character_text_view);
        mCharacterTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mCharacterTextView.setText(mHangulCharacter);

        mRomanizationTextView = (TextView) view.findViewById(R.id.romanization_text_view);
        mRomanizationTextView.setText(mRomanization);

        mPlayFab = (FloatingActionButton) view.findViewById(R.id.play_fab);
        mPlayFab.setOnClickListener(this);
    }

    @Override
    public void onInit(int status) {
        if (mTextToSpeech.isLanguageAvailable(Locale.KOREA) == TextToSpeech.LANG_AVAILABLE || mTextToSpeech.isLanguageAvailable(Locale.KOREA) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
            mTextToSpeech.setLanguage(Locale.KOREA);
            mTextToSpeech.setSpeechRate(0.5f);
        } else {
            Toast.makeText(getActivity(), "No Voice Files found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_fab:

                break;
        }
    }

    private void playSound() {
        mTextToSpeech.speak(mPronunciation, TextToSpeech.QUEUE_FLUSH, null);
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
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onDestroy() {
        mTextToSpeech.shutdown();
        super.onDestroy();
    }
}
