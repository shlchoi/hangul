package ca.uwaterloo.sh6choi.korea101r.fragments.hangul;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.adapters.HangulAdapter;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;

/**
 * Created by Samson on 2015-09-25.
 */
public class HangulLookupFragment extends Fragment implements DrawerFragment, HangulAdapter.OnItemClickListener {

    private static final String TAG = HangulLookupFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.hangul_lookup";

    public static final String EXTRA_HANGUL = TAG +".extra.character";
    public static final String EXTRA_ROMANIZATION = TAG +".extra.romanization";
    public static final String EXTRA_PRONUNCIATION = TAG +".extra.pronunciation";

    private String[] mCharacterSet;
    private String[] mRomanizationSet;
    private String[] mPronunciationSet;

    private RecyclerView mHangulRecyclerView;
    private HangulAdapter mHangulAdapter;

    public static HangulLookupFragment getInstance(Bundle args) {
        HangulLookupFragment fragment = new HangulLookupFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_hangul_lookup, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCharacterSet = getResources().getStringArray(R.array.hangul_characters);
        mRomanizationSet = getResources().getStringArray(R.array.hangul_romanizations);
        mPronunciationSet = getResources().getStringArray(R.array.hangul_pronunciations);

        mHangulRecyclerView = (RecyclerView) view.findViewById(R.id.hangul_recycler_view);

        mHangulRecyclerView.setHasFixedSize(true);

        mHangulRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));



        // specify an adapter (see also next example)
        mHangulAdapter = new HangulAdapter(mCharacterSet);
        mHangulAdapter.setOnItemClickListener(this);
        mHangulRecyclerView.setAdapter(mHangulAdapter);

    }

    @Override
    public void onItemClick(View view) {
        int index = (int) view.getTag();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_HANGUL_CHARACTER);
        intent.putExtra(EXTRA_HANGUL, mCharacterSet[index]);
        intent.putExtra(EXTRA_ROMANIZATION, mRomanizationSet[index]);
        intent.putExtra(EXTRA_PRONUNCIATION, mPronunciationSet[index]);

        startActivity(intent);
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
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_HANGUL);

        startActivity(intent);
        return true;
    }
}
