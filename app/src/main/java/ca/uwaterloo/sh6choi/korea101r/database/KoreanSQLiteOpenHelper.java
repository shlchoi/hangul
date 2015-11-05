package ca.uwaterloo.sh6choi.korea101r.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Samson on 2015-10-23.
 */
public class KoreanSQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String TAG = KoreanSQLiteOpenHelper.class.getCanonicalName();

    public static final String TABLE_CHARACTERS = "characters";
    public static final String COLUMN_CHAR_ID = "char_id";
    public static final String COLUMN_CHARACTER = "character";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRONUNCIATION = "pronunciation";
    public static final String COLUMN_ROMANIZATION_INITIAL = "romanization_initial";
    public static final String COLUMN_ROMANIZATION_FINAL = "romanization_final";
    public static final String COLUMN_CHARACTER_TYPE = "character_type";

    public static final String TABLE_DICTATION = "dictation";
    public static final String COLUMN_WORD_ID_DICT = "word_id";
    public static final String COLUMN_SET_ID = "set_id";
    public static final String COLUMN_DEFINITION_DICT = "definition";
    public static final String COLUMN_HANGUL_DICT = "hangul";

    public static final String TABLE_VOCABULARY = "vocabulary";
    public static final String COLUMN_WORD_ID_VOCAB = "word_id";
    public static final String COLUMN_LESSON_ID = "lesson_id";
    public static final String COLUMN_HANGUL_VOCAB = "hangul";
    public static final String COLUMN_WORD_TYPE = "type";

    public static final String TABLE_DEFINITIONS = "definitions";
    public static final String COLUMN_DEFINITION_ID = "definition_id";
    public static final String COLUMN_WORD_ID = "word_id";
    public static final String COLUMN_DEFINITION = "definition";

    public static final String TABLE_NUMBERS = "numbers";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_KOREAN = "korean";
    public static final String COLUMN_SINO_KOREAN = "sino_korean";
    public static final String COLUMN_COUNT = "count";

    private static final String DATABASE_NAME = "Hangul.db";
    private static final int DATABASE_VERSION = 5;

    private static final String TABLE_CHARACTERS_CREATE = "CREATE TABLE " + TABLE_CHARACTERS + "(" +
            COLUMN_CHAR_ID + " TEXT NOT NULL PRIMARY KEY, " +
            COLUMN_CHARACTER + " TEXT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_PRONUNCIATION + " TEXT NOT NULL, " +
            COLUMN_ROMANIZATION_INITIAL + " TEXT NOT NULL, " +
            COLUMN_ROMANIZATION_FINAL + " TEXT NOT NULL," +
            COLUMN_CHARACTER_TYPE + " TEXT NOT NULL);";

    private static final String TABLE_DICTATION_CREATE = "CREATE TABLE " + TABLE_DICTATION + " (" +
            COLUMN_WORD_ID_DICT + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SET_ID + " INTEGER NOT NULL, " +
            COLUMN_DEFINITION_DICT + " TEXT NOT NULL, " +
            COLUMN_HANGUL_DICT + " TEXT NOT NULL);";

    private static final String TABLE_VOCABULARY_CREATE = "CREATE TABLE " + TABLE_VOCABULARY + " (" +
            COLUMN_WORD_ID_VOCAB + " INTEGER NOT NULL PRIMARY KEY, " +
            COLUMN_LESSON_ID + " INTEGER NOT NULL, " +
            COLUMN_HANGUL_VOCAB + " TEXT NOT NULL, " +
            COLUMN_WORD_TYPE + " TEXT NOT NULL);";

    private static final String TABLE_DEFINITIONS_CREATE = "CREATE TABLE " + TABLE_DEFINITIONS + " (" +
            COLUMN_DEFINITION_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_WORD_ID + " INTEGER NOT NULL, " +
            COLUMN_DEFINITION + " TEXT NOT NULL);";

    private static final String TABLE_NUMBERS_CREATE = "CREATE TABLE " + TABLE_NUMBERS + " (" +
            COLUMN_NUMBER + " INTEGER NOT NULL PRIMARY KEY, " +
            COLUMN_KOREAN + " TEXT, " +
            COLUMN_SINO_KOREAN + " TEXT, " +
            COLUMN_COUNT + " TEXT);";

    public KoreanSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CHARACTERS_CREATE);
        db.execSQL(TABLE_DICTATION_CREATE);
        db.execSQL(TABLE_VOCABULARY_CREATE);
        db.execSQL(TABLE_DEFINITIONS_CREATE);
        db.execSQL(TABLE_NUMBERS_CREATE);
        Log.d(TAG, "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARACTERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOCABULARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFINITIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUMBERS);
        onCreate(db);
    }

    public void clearDefinitionsTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFINITIONS);
        db.execSQL(TABLE_DEFINITIONS_CREATE);
    }
}
