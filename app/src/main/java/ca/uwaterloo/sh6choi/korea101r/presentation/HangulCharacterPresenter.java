package ca.uwaterloo.sh6choi.korea101r.presentation;

import android.content.Context;

import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.database.DatabaseRequestCallback;
import ca.uwaterloo.sh6choi.korea101r.database.HangulCharacterDataSource;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;

/**
 * Created by Samson on 2015-10-23.
 */
public class HangulCharacterPresenter {
    private final HangulCharacterView mView;

    private HangulCharacterDataSource mDataSource;

    public HangulCharacterPresenter(Context context, HangulCharacterView view) {
        mDataSource = new HangulCharacterDataSource(context);
        mView = view;
    }

    public void obtainAllCharacters() {
        mDataSource.open();
        mDataSource.queryItems(new DatabaseRequestCallback<List<HangulCharacter>>() {
            @Override
            public void processResults(List<HangulCharacter> results) {
                mView.refreshHangulCharacterList(results);
                mDataSource.close();
            }
        });
    }

    public interface HangulCharacterView {
        void refreshHangulCharacterList(List<HangulCharacter> hangulCharacterList);
    }
}
