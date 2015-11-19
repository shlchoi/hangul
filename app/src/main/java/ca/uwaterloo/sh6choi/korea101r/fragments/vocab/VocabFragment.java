package ca.uwaterloo.sh6choi.korea101r.fragments.vocab;

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
 * Created by Samson on 2015-10-27.
 */
public class VocabFragment extends Fragment implements DrawerFragment, View.OnClickListener {

    private static final String TAG = VocabFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.vocab";

    private Button mLookupButton;
    private Button mQuizButton;
    private Button mFlashcardButton;

    public static VocabFragment getInstance(Bundle args) {
        VocabFragment fragment = new VocabFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_vocab, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLookupButton = (Button) view.findViewById(R.id.lookup_button);
        mQuizButton = (Button) view.findViewById(R.id.vocab_quiz_button);
        mFlashcardButton = (Button) view.findViewById(R.id.flashcards_button);

        mLookupButton.setOnClickListener(this);
        mQuizButton.setOnClickListener(this);
        mFlashcardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lookup_button:
                onLookupButtonClicked();
                break;
            case R.id.vocab_quiz_button:
                onQuizButtonClicked();
                break;
            case R.id.flashcards_button:
                onFlashcardButtonClicked();
                break;
        }
    }

    private void onLookupButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_VOCAB_LOOKUP);
        startActivity(intent);
    }

    private void onQuizButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_VOCAB_QUIZ);
        startActivity(intent);
    }

    private void onFlashcardButtonClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_VOCAB_FLASHCARDS);
        startActivity(intent);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.nav_menu_vocab;
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
