package ca.uwaterloo.sh6choi.korea101r.fragments.vocab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.adapters.VocabAdapter;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;
import ca.uwaterloo.sh6choi.korea101r.model.VocabWord;
import ca.uwaterloo.sh6choi.korea101r.presentation.VocabSetPresenter;

/**
 * Created by Samson on 2015-10-27.
 */
public class VocabListFragment extends Fragment implements DrawerFragment, VocabSetPresenter.VocabSetView,
        VocabAdapter.OnItemClickListener {

    private static final String TAG = VocabListFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.vocab_list";

    public static final String ARG_LESSON_ID = TAG + ".args.lesson_id";

    private RecyclerView mVocabRecyclerView;
    private VocabAdapter mVocabAdapter;

    private VocabSetPresenter mPresenter;

    private int mLessonId;

    public static VocabListFragment getInstance(Bundle args) {
        VocabListFragment fragment = new VocabListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_view_pager_list, container, false);

        mLessonId = getArguments().getInt(ARG_LESSON_ID);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVocabRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mVocabRecyclerView.setHasFixedSize(true);
        mVocabRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mVocabAdapter = new VocabAdapter(mLessonId);
        mVocabAdapter.setOnItemClickListener(this);
        mVocabRecyclerView.setAdapter(mVocabAdapter);

        mPresenter = new VocabSetPresenter(getContext(), this);
        mPresenter.obtainVocabulary(mLessonId);
    }

    @Override
    public void refreshVocabSet(VocabSet vocabSet) {
        mVocabAdapter.setVocabSet(vocabSet);
    }

    @Override
    public void onItemClick(View view) {
        VocabWord word = (VocabWord) view.getTag();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_VOCAB_WORD);
        intent.putExtra(VocabLookupFragment.EXTRA_VOCAB_WORD, word);

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
