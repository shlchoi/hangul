package ca.uwaterloo.sh6choi.korea101r.fragments.hangul;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;
import ca.uwaterloo.sh6choi.korea101r.presentation.HangulCharacterPresenter;

/**
 * Created by Samson on 2015-09-22.
 */
public class HangulFlashcardFragment extends Fragment implements DrawerFragment, View.OnClickListener, View.OnTouchListener, HangulCharacterPresenter.HangulCharacterView {

    private static final String TAG = HangulFlashcardFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hangul.flashcards";

    private List<HangulCharacter> mHangulCharacterList;
    private HangulCharacterPresenter mPresenter;

    private int mCurIndex = -1;

    private TextView mCharacterTextView;
    private TextView mHintTextView;

    public static HangulFlashcardFragment getInstance(Bundle args) {
        HangulFlashcardFragment fragment = new HangulFlashcardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_hangul_flashcard, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHangulCharacterList = new ArrayList<>();

        mCharacterTextView = (TextView) view.findViewById(R.id.character_text_view);
        mCharacterTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mCharacterTextView.setOnTouchListener(this);

        mHintTextView = (TextView) view.findViewById(R.id.hint_text_view);

        view.findViewById(R.id.hangul_fragment_relative_layout).setOnClickListener(this);

        mPresenter  = new HangulCharacterPresenter(getContext(), this);
        mPresenter.obtainAllCharacters();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hangul_fragment_relative_layout:
                switchCharacter();
                break;
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

    @Override
    public void refreshHangulCharacterList(List<HangulCharacter> hangulCharacterList) {
        mHangulCharacterList = hangulCharacterList;

        switchCharacter();
    }

    private void switchCharacter() {
        Random random = new Random(new Date().getTime());

        if (mHangulCharacterList.size() > 0) {
            int nextInt;
            do {
                nextInt = random.nextInt(mHangulCharacterList.size());
            } while (nextInt == mCurIndex);


            mCurIndex = nextInt;
            mCharacterTextView.setText(mHangulCharacterList.get(mCurIndex).getCharacter());
            mHintTextView.setText(mHangulCharacterList.get(mCurIndex).getName());
        }
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
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_HANGUL);

        startActivity(intent);
        return true;
    }
}
