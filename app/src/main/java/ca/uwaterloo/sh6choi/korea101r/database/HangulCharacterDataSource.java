package ca.uwaterloo.sh6choi.korea101r.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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

    public void update(final List<HangulCharacter> hangulCharacters, final DatabaseRequestCallback<Void> callback ) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                String sql = "INSERT OR REPLACE INTO " + KoreanSQLiteOpenHelper.TABLE_CHARACTERS + "(" +
                        KoreanSQLiteOpenHelper.COLUMN_CHAR_ID + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_CHARACTER + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_NAME + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_PRONUNCIATION + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_ROMANIZATION_INITIAL + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_ROMANIZATION_FINAL + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_CHARACTER_TYPE + ") VALUES ";

                StringBuilder builder = new StringBuilder(sql);
                for (int i = 0; i < hangulCharacters.size(); i ++) {
                    HangulCharacter character = hangulCharacters.get(i);
                    String format = "(\"%1$s\", \"%2$s\", \"%3$s\", \"%4$s\", \"%5$s\", \"%6$s\", \"%7$s\")";

                    String romanizationFinal;
                    if (character.getRomanization().length > 1) {
                        romanizationFinal =  character.getRomanization()[1];
                    } else {
                        romanizationFinal =  character.getRomanization()[0];
                    }


                    builder.append(String.format(format, character.getCharId(), character.getCharacter(), character.getName(), character.getPronunciation(), character.getRomanization()[0], romanizationFinal, character.getType()));
                    if (i < hangulCharacters.size() - 1) {
                        builder.append(", ");
                    }
                }
                builder.append(";");
                SQLiteStatement statement = mDatabase.compileStatement(builder.toString());
                statement.executeInsert();
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
