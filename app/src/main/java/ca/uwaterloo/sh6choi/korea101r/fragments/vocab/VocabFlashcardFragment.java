package ca.uwaterloo.sh6choi.korea101r.fragments.vocab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.adapters.FlashcardAdapter;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;
import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;
import ca.uwaterloo.sh6choi.korea101r.model.VocabWord;
import ca.uwaterloo.sh6choi.korea101r.presentation.HangulCharacterPresenter;
import ca.uwaterloo.sh6choi.korea101r.presentation.VocabSetPresenter;

/**
 * Created by Samson on 2015-09-22.
 */
public class VocabFlashcardFragment extends Fragment implements DrawerFragment, VocabSetPresenter.VocabSetView {

    private static final String TAG = VocabFlashcardFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hangul.flashcards";

    private RecyclerView mFlashcardRecyclerView;
    private FlashcardAdapter<VocabWord> mFlashcardAdapter;

    private VocabSetPresenter mPresenter;

    public static VocabFlashcardFragment getInstance(Bundle args) {
        VocabFlashcardFragment fragment = new VocabFlashcardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        View contentView = inflater.inflate(R.layout.fragment_hangul_flashcard, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFlashcardRecyclerView = (RecyclerView) view.findViewById(R.id.flashcard_recycler_view);
        mFlashcardAdapter = new FlashcardAdapter<>(R.layout.list_item_flashcard);

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

        mPresenter  = new VocabSetPresenter(getContext(), this);
        mPresenter.obtainAllVocabulary();
    }

    @Override
    public void refreshVocabSet(VocabSet vocabSet) {
        mFlashcardAdapter.setFlashcardList(Arrays.asList(vocabSet.getWords()));
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.vocab_flashcards;
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
        intent.setAction(MainActivity.ACTION_VOCAB);

        startActivity(intent);
        return true;
    }
}
