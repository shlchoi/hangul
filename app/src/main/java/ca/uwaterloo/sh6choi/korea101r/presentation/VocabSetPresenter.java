package ca.uwaterloo.sh6choi.korea101r.presentation;

import android.content.Context;

import ca.uwaterloo.sh6choi.korea101r.database.DatabaseRequestCallback;
import ca.uwaterloo.sh6choi.korea101r.database.VocabDataSource;
import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;

/**
 * Created by Samson on 2015-10-27.
 */
public class VocabSetPresenter {

    private final VocabSetView mView;

    private VocabDataSource mDataSource;

    public VocabSetPresenter(Context context, VocabSetView view) {
        mDataSource = new VocabDataSource(context);
        mView = view;
    }

    public void obtainAllVocabulary() {
        mDataSource.open();
        mDataSource.queryVocab(new DatabaseRequestCallback<VocabSet>() {
            @Override
            public void processResults(VocabSet results) {
                mView.refreshVocabSet(results);
                mDataSource.close();
            }
        });
    }

    public void obtainVocabulary(int lessonId) {
        mDataSource.open();
        mDataSource.queryVocab(lessonId, new DatabaseRequestCallback<VocabSet>() {
            @Override
            public void processResults(VocabSet results) {
                mView.refreshVocabSet(results);
                mDataSource.close();
            }
        });
    }

    public void obtainVocab(String type) {
        mDataSource.open();
        mDataSource.queryVocab(type, new DatabaseRequestCallback<VocabSet>() {
            @Override
            public void processResults(VocabSet results) {
                mView.refreshVocabSet(results);
                mDataSource.close();
            }
        });
    }

    public interface VocabSetView {
        void refreshVocabSet(VocabSet vocabSet);
    }
}
