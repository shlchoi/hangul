package ca.uwaterloo.sh6choi.korea101r.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.model.KoreanNumber;

/**
 * Created by Samson on 2015-11-01.
 */
public class NumberDataSource {

    private SQLiteDatabase mDatabase;
    private KoreanSQLiteOpenHelper mHelper;
    private String[] mColumns = {
            KoreanSQLiteOpenHelper.COLUMN_NUMBER,
            KoreanSQLiteOpenHelper.COLUMN_KOREAN,
            KoreanSQLiteOpenHelper.COLUMN_SINO_KOREAN,
            KoreanSQLiteOpenHelper.COLUMN_COUNT,
    };

    public NumberDataSource(Context context) {
        mHelper = new KoreanSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public void queryNumbers(final DatabaseRequestCallback<List<KoreanNumber>> callback) {
        new AsyncTask<Void, Void, List<KoreanNumber>>() {
            @Override
            protected List<KoreanNumber> doInBackground(Void... params) {
                List<KoreanNumber> koreanNumbers = new ArrayList<>();

                Cursor cursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_NUMBERS, mColumns, null, null, null, null, null);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    koreanNumbers.add(cursorToNumber(cursor));
                    cursor.moveToNext();
                }

                cursor.close();

                Collections.sort(koreanNumbers, new KoreanNumber.NumberComparator());
                return koreanNumbers;
            }

            @Override
            protected void onPostExecute(List<KoreanNumber> hangulCharacterList) {
                callback.processResults(hangulCharacterList);
            }
        }.execute();
    }

    public void queryNumber(final int number, final DatabaseRequestCallback<List<KoreanNumber>> callback) {
        new AsyncTask<Void, Void, List<KoreanNumber>>() {
            @Override
            protected List<KoreanNumber> doInBackground(Void... params) {
                List<KoreanNumber> koreanNumbers = new ArrayList<>();

                Cursor cursor = mDatabase.query(KoreanSQLiteOpenHelper.TABLE_NUMBERS, mColumns, "WHERE " + KoreanSQLiteOpenHelper.TABLE_NUMBERS + "." + KoreanSQLiteOpenHelper.COLUMN_NUMBER + " =?", new String[]{Integer.toString(number)}, null, null, null);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    koreanNumbers.add(cursorToNumber(cursor));
                    cursor.moveToNext();
                }

                cursor.close();

                Collections.sort(koreanNumbers, new KoreanNumber.NumberComparator());
                return koreanNumbers;
            }

            @Override
            protected void onPostExecute(List<KoreanNumber> hangulCharacterList) {
                callback.processResults(hangulCharacterList);
            }
        }.execute();
    }

    public void update(final List<KoreanNumber> koreanNumbers, final DatabaseRequestCallback<Void> callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                String sql = "INSERT OR REPLACE INTO " + KoreanSQLiteOpenHelper.TABLE_NUMBERS + "(" +
                        KoreanSQLiteOpenHelper.COLUMN_NUMBER + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_KOREAN + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_SINO_KOREAN + ", " +
                        KoreanSQLiteOpenHelper.COLUMN_COUNT + ") VALUES ";

                StringBuilder builder = new StringBuilder(sql);
                for (int i = 0; i < koreanNumbers.size(); i++) {
                    KoreanNumber koreanNumber = koreanNumbers.get(i);
                    String format = "(\"%1$s\", \"%2$s\", \"%3$s\", \"%4$s\")";

                    builder.append(String.format(format, koreanNumber.getNumber(), koreanNumber.getKorean(), koreanNumber.getSinoKorean(), koreanNumber.getCount()));
                    if (i < koreanNumbers.size() - 1) {
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

    private KoreanNumber cursorToNumber(Cursor cursor) {
        KoreanNumber koreanNumber = new KoreanNumber(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return koreanNumber;
    }
}
