package ca.uwaterloo.sh6choi.korea101r.fragments.hangul;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import ca.uwaterloo.sh6choi.korea101r.adapters.FlashcardAdapter;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.model.FlashcardItem;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;
import ca.uwaterloo.sh6choi.korea101r.presentation.HangulCharacterPresenter;

/**
 * Created by Samson on 2015-09-22.
 */
public class HangulFlashcardFragment extends Fragment implements DrawerFragment, HangulCharacterPresenter.HangulCharacterView {

    private static final String TAG = HangulFlashcardFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hangul.flashcards";

    private RecyclerView mFlashcardRecyclerView;
    private FlashcardAdapter<HangulCharacter> mFlashcardAdapter;

    private HangulCharacterPresenter mPresenter;

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

        mFlashcardRecyclerView = (RecyclerView) view.findViewById(R.id.flashcard_recycler_view);
        mFlashcardAdapter = new FlashcardAdapter<>(R.layout.list_item_flashcard_character);

        ItemTouchHelper.Callback listItemTouchHelper = new ItemTouchHelper.Callback() {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    mFlashcardAdapter.nextCard();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(listItemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mFlashcardRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mFlashcardRecyclerView.setLayoutManager(manager);
        mFlashcardRecyclerView.setAdapter(mFlashcardAdapter);

        mPresenter  = new HangulCharacterPresenter(getContext(), this);
        mPresenter.obtainAllCharacters();
    }

    @Override
    public void refreshHangulCharacterList(List<HangulCharacter> hangulCharacterList) {
        mFlashcardAdapter.setFlashcardList(hangulCharacterList);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.hangul_flashcards;
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
