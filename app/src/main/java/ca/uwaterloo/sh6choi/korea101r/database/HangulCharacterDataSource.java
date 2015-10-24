package ca.uwaterloo.sh6choi.korea101r.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;
import ca.uwaterloo.sh6choi.korea101r.presentation.HangulCharacterPresenter;

/**
 * Created by Samson on 2015-10-23.
 */
public class HangulCharacterDataSource {

    private SQLiteDatabase mDatabase;
    private KoreanSQLiteOpenHelper mHelper;
    private String[] mColumns = { KoreanSQLiteOpenHelper.COLUMN_CHAR_ID,
            KoreanSQLiteOpenHelper.COLUMN_CHARACTER,
            KoreanSQLiteOpenHelper.COLUMN_NAME,
            KoreanSQLiteOpenHelper.COLUMN_PRONUNCIATION,
            KoreanSQLiteOpenHelper.COLUMN_ROMANIZATION_INITIAL,
            KoreanSQLiteOpenHelper.COLUMN_ROMANIZATION_FINAL,
            KoreanSQLiteOpenHelper.COLUMN_CHARACTER_TYPE};

    public HangulCharacterDataSource(Context context) {
        mHelper = new KoreanSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public void queryItems(final DatabaseRequestCallback<List<HangulCharacter>> callback) {
        new AsyncTask<Void, Void, List<HangulCharacter>>() {
            @Override
            protected List<HangulCharacter> doInBackground(Void... params) {
                List<HangulCharacter> hangulCharacters = new ArrayList<>();

                Cursor cursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_CHARACTERS, mColumns, null, null, null, null, null);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    hangulCharacters.add(cursorToCharacter(cursor));
                    cursor.moveToNext();
                }

                cursor.close();

                Collections.sort(hangulCharacters, new HangulCharacter.CharacterComparator());
                return hangulCharacters;
            }

            @Override
            protected void onPostExecute(List<HangulCharacter> hangulCharacterList) {
                callback.processResults(hangulCharacterList);
            }

        }.execute();
    }

    public void update(final HangulCharacter hangulCharacter, final DatabaseRequestCallback<Void> callback ) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KoreanSQLiteOpenHelper.COLUMN_CHAR_ID, hangulCharacter.getCharId());
                contentValues.put(KoreanSQLiteOpenHelper.COLUMN_CHARACTER, hangulCharacter.getCharacter());
                contentValues.put(KoreanSQLiteOpenHelper.COLUMN_NAME, hangulCharacter.getName());
                contentValues.put(KoreanSQLiteOpenHelper.COLUMN_PRONUNCIATION, hangulCharacter.getPronunciation());
                contentValues.put(KoreanSQLiteOpenHelper.COLUMN_ROMANIZATION_INITIAL, hangulCharacter.getRomanization()[0]);

                if (hangulCharacter.getRomanization().length > 1) {
                    contentValues.put(KoreanSQLiteOpenHelper.COLUMN_ROMANIZATION_FINAL, hangulCharacter.getRomanization()[1]);
                } else {
                    contentValues.put(KoreanSQLiteOpenHelper.COLUMN_ROMANIZATION_FINAL, hangulCharacter.getRomanization()[0]);
                }
                contentValues.put(KoreanSQLiteOpenHelper.COLUMN_CHARACTER_TYPE, hangulCharacter.getType());

                mDatabase.insertWithOnConflict(KoreanSQLiteOpenHelper.TABLE_CHARACTERS, null, contentValues,
                        SQLiteDatabase.CONFLICT_REPLACE);

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

    private HangulCharacter cursorToCharacter(Cursor cursor) {
        HangulCharacter character = new HangulCharacter(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), new String[]{cursor.getString(4), cursor.getString(5)}, cursor.getString(6));
        return character;
    }
}
