package ca.uwaterloo.sh6choi.korea101r.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;
import ca.uwaterloo.sh6choi.korea101r.model.VocabWord;

/**
 * Created by Samson on 2015-10-23.
 */
public class VocabDataSource {

    private SQLiteDatabase mDatabase;
    private KoreanSQLiteOpenHelper mHelper;
    private String[] mColumnsVocab = {
            KoreanSQLiteOpenHelper.COLUMN_WORD_ID_VOCAB,
            KoreanSQLiteOpenHelper.COLUMN_HANGUL_VOCAB,
            KoreanSQLiteOpenHelper.COLUMN_WORD_TYPE,
            KoreanSQLiteOpenHelper.COLUMN_LESSON_ID,
    };

    private String[] mColumnsDefinitions = {
            KoreanSQLiteOpenHelper.COLUMN_WORD_ID,
            KoreanSQLiteOpenHelper.COLUMN_DEFINITION,
    };

    public VocabDataSource(Context context) {
        mHelper = new KoreanSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public void queryVocab(final int lessonId, final DatabaseRequestCallback<VocabSet> callback) {
        new AsyncTask<Void, Void, VocabSet>() {
            @Override
            protected VocabSet doInBackground(Void... params) {
                HashMap<Integer, VocabWord> words = new HashMap<>();

                Cursor vocabCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_VOCABULARY, mColumnsVocab,
                        KoreanSQLiteOpenHelper.COLUMN_LESSON_ID + "=?", new String[]{Integer.toString(lessonId)}, null, null, null);

                vocabCursor.moveToFirst();
                while (!vocabCursor.isAfterLast()) {
                    VocabWord vocab = cursorToWord(vocabCursor);
                    words.put(vocab.getWordId(), vocab);

                    vocabCursor.moveToNext();
                }
                vocabCursor.close();

                Cursor definitionCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_DEFINITIONS, mColumnsDefinitions,
                        KoreanSQLiteOpenHelper.COLUMN_WORD_ID + " IN (" + generateQuestionMarkString(words.keySet()) + ")", null, null, null, null);

                definitionCursor.moveToFirst();
                while (!definitionCursor.isAfterLast()) {
                    int wordId = definitionCursor.getInt(0);
                    String definition = definitionCursor.getString(1);

                    if (words.containsKey(wordId)) {
                        words.get(wordId).addDefinition(definition);
                    }
                    definitionCursor.moveToNext();
                }
                definitionCursor.close();

                VocabWord[] vocabWords = words.values().toArray(new VocabWord[words.size()]);
                Arrays.sort(vocabWords, new VocabWord.WordComparator());
                return new VocabSet(lessonId, vocabWords);
            }

            @Override
            protected void onPostExecute(VocabSet vocabSet) {
                callback.processResults(vocabSet);
            }

        }.execute();
    }

    public void queryVocab(final DatabaseRequestCallback<VocabSet> callback) {
        new AsyncTask<Void, Void, VocabSet>() {
            @Override
            protected VocabSet doInBackground(Void... params) {
                HashMap<Integer, VocabWord> words = new HashMap<>();

                Cursor vocabCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_VOCABULARY, mColumnsVocab,
                        null, null, null, null, null);

                vocabCursor.moveToFirst();
                while (!vocabCursor.isAfterLast()) {
                    VocabWord vocab = cursorToWord(vocabCursor);
                    words.put(vocab.getWordId(), vocab);

                    vocabCursor.moveToNext();
                }
                vocabCursor.close();

                Cursor definitionCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_DEFINITIONS, mColumnsDefinitions,
                        KoreanSQLiteOpenHelper.COLUMN_WORD_ID + " IN (" + generateQuestionMarkString(words.keySet()) + ")", null, null, null, null);

                definitionCursor.moveToFirst();
                while (!definitionCursor.isAfterLast()) {
                    int wordId = definitionCursor.getInt(0);
                    String definition = definitionCursor.getString(1);

                    if (words.containsKey(wordId)) {
                        words.get(wordId).addDefinition(definition);
                    }
                    definitionCursor.moveToNext();
                }
                definitionCursor.close();

                VocabWord[] vocabWords = words.values().toArray(new VocabWord[words.size()]);
                Arrays.sort(vocabWords, new VocabWord.WordComparator());
                return new VocabSet(0, vocabWords);
            }

            @Override
            protected void onPostExecute(VocabSet vocabSet) {
                callback.processResults(vocabSet);
            }

        }.execute();
    }

    public void queryVocab(final String vocabType, final DatabaseRequestCallback<VocabSet> callback) {
        new AsyncTask<Void, Void, VocabSet>() {
            @Override
            protected VocabSet doInBackground(Void... params) {
                HashMap<Integer, VocabWord> words = new HashMap<>();

                Cursor vocabCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_VOCABULARY, mColumnsVocab,
                        KoreanSQLiteOpenHelper.COLUMN_WORD_TYPE + "=?", new String[]{vocabType}, null, null, null);

                vocabCursor.moveToFirst();
                while (!vocabCursor.isAfterLast()) {
                    VocabWord vocab = cursorToWord(vocabCursor);
                    words.put(vocab.getWordId(), vocab);

                    vocabCursor.moveToNext();
                }
                vocabCursor.close();

                Cursor definitionCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_DEFINITIONS, mColumnsDefinitions,
                        KoreanSQLiteOpenHelper.COLUMN_WORD_ID + " IN (" + generateQuestionMarkString(words.keySet()) + ")", null, null, null, null);

                definitionCursor.moveToFirst();
                while (!definitionCursor.isAfterLast()) {
                    int wordId = definitionCursor.getInt(0);
                    String definition = definitionCursor.getString(1);

                    if (words.containsKey(wordId)) {
                        words.get(wordId).addDefinition(definition);
                    }
                    definitionCursor.moveToNext();
                }
                definitionCursor.close();

                VocabWord[] vocabWords = words.values().toArray(new VocabWord[words.size()]);
                Arrays.sort(vocabWords, new VocabWord.WordComparator());
                return new VocabSet(0, vocabWords);
            }

            @Override
            protected void onPostExecute(VocabSet vocabSet) {
                callback.processResults(vocabSet);
            }

        }.execute();
    }

    public void queryVocab(final DatabaseRequestCallback<VocabSet> callback, final String... vocabTypes) {
        new AsyncTask<Void, Void, VocabSet>() {
            @Override
            protected VocabSet doInBackground(Void... params) {
                HashMap<Integer, VocabWord> words = new HashMap<>();

                Cursor vocabCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_VOCABULARY, mColumnsVocab,
                        KoreanSQLiteOpenHelper.COLUMN_WORD_TYPE + " IN (" + generateQuestionMarkString(vocabTypes) + ")", null, null, null, null);

                vocabCursor.moveToFirst();
                while (!vocabCursor.isAfterLast()) {
                    VocabWord vocab = cursorToWord(vocabCursor);
                    words.put(vocab.getWordId(), vocab);

                    vocabCursor.moveToNext();
                }
                vocabCursor.close();

                Cursor definitionCursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_DEFINITIONS, mColumnsDefinitions,
                        KoreanSQLiteOpenHelper.COLUMN_WORD_ID + " IN (" + generateQuestionMarkString(words.keySet()) + ")", null, null, null, null);

                definitionCursor.moveToFirst();
                while (!definitionCursor.isAfterLast()) {
                    int wordId = definitionCursor.getInt(0);
                    String definition = definitionCursor.getString(1);

                    if (words.containsKey(wordId)) {
                        words.get(wordId).addDefinition(definition);
                    }
                    definitionCursor.moveToNext();
                }
                definitionCursor.close();

                VocabWord[] vocabWords = words.values().toArray(new VocabWord[words.size()]);
                Arrays.sort(vocabWords, new VocabWord.WordComparator());
                return new VocabSet(0, vocabWords);
            }

            @Override
            protected void onPostExecute(VocabSet vocabSet) {
                callback.processResults(vocabSet);
            }

        }.execute();
    }

    public void update(final VocabSet vocabSet, final DatabaseRequestCallback<Void> callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i < vocabSet.getWords().length; i++) {
                    ContentValues vocabValues = new ContentValues();
                    vocabValues.put(KoreanSQLiteOpenHelper.COLUMN_WORD_ID_VOCAB, vocabSet.getWords()[i].getWordId());
                    vocabValues.put(KoreanSQLiteOpenHelper.COLUMN_LESSON_ID, vocabSet.getId());
                    vocabValues.put(KoreanSQLiteOpenHelper.COLUMN_HANGUL_VOCAB, vocabSet.getWords()[i].getHangul());
                    vocabValues.put(KoreanSQLiteOpenHelper.COLUMN_WORD_TYPE, vocabSet.getWords()[i].getType());

                    mDatabase.insertWithOnConflict(KoreanSQLiteOpenHelper.TABLE_VOCABULARY, null, vocabValues,
                            SQLiteDatabase.CONFLICT_REPLACE);

                    for (int j = 0; j < vocabSet.getWords()[i].getDefinitions().length; j++) {
                        ContentValues definitionValues = new ContentValues();
                        definitionValues.put(KoreanSQLiteOpenHelper.COLUMN_WORD_ID, vocabSet.getWords()[i].getWordId());
                        definitionValues.put(KoreanSQLiteOpenHelper.COLUMN_DEFINITION, vocabSet.getWords()[i].getDefinitions()[j]);

                        mDatabase.insertWithOnConflict(KoreanSQLiteOpenHelper.TABLE_DEFINITIONS, null, definitionValues,
                                SQLiteDatabase.CONFLICT_REPLACE);
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (callback != null) {
                    callback.processResults(aVoid);
                }
            }
        }.execute();
    }

    public void clearDefinitions() {
        mHelper.clearDefinitionsTable(mDatabase);
    }

    private VocabWord cursorToWord(Cursor cursor) {
        VocabWord word = new VocabWord(cursor.getInt(0), cursor.getString(1), new String[0], cursor.getString(2));
        return word;
    }

    private String generateQuestionMarkString(Set<Integer> args) {
        StringBuilder builder = new StringBuilder();
        Integer[] intArray = args.toArray(new Integer[args.size()]);
        for (int i = 0; i < args.size(); i++) {
            builder.append(intArray[i]);
            if (i < args.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private String generateQuestionMarkString(String... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append("\"" + args[i] + "\"");
            if (i < args.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
