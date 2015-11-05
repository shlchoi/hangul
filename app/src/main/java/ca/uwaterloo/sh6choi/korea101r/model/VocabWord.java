package ca.uwaterloo.sh6choi.korea101r.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Samson on 2015-10-27.
 */
public class VocabWord implements Parcelable, FlashcardItem {

    @SerializedName("word_id")
    private int mWordId;

    @SerializedName("hangul")
    private String mHangul;

    @SerializedName("definitions")
    private String[] mDefinitions;

    @SerializedName("type")
    private String mType;

    public VocabWord(int wordId, String hangul, String[] definitions, String type) {
        mWordId = wordId;
        mHangul = hangul;
        mDefinitions = definitions;
        mType = type;
    }

    protected VocabWord(Parcel in) {
        mWordId = in.readInt();
        mHangul = in.readString();
        mDefinitions = in.createStringArray();
        mType = in.readString();
    }

    public int getWordId() {
        return mWordId;
    }

    public String getHangul() {
        return mHangul;
    }

    public String[] getDefinitions() {
        return mDefinitions;
    }

    public void addDefinition(String definition) {
        mDefinitions = Arrays.copyOf(mDefinitions, mDefinitions.length + 1);
        mDefinitions[mDefinitions.length - 1] = definition;
    }

    public String getType() {
        return mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String getFlashcardText() {
        return getHangul();
    }

    @Override
    public String getFlashcardHint() {
        return getDefinitions()[0];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mWordId);
        dest.writeString(mHangul);
        dest.writeStringArray(mDefinitions);
        dest.writeString(mType);
    }

    public static final Creator<VocabWord> CREATOR = new Creator<VocabWord>() {
        @Override
        public VocabWord createFromParcel(Parcel in) {
            return new VocabWord(in);
        }

        @Override
        public VocabWord[] newArray(int size) {
            return new VocabWord[size];
        }
    };

    public static class WordComparator implements Comparator<VocabWord>
    {
        public int compare(VocabWord c1, VocabWord c2)
        {
            return c1.getWordId() - c2.getWordId();
        }
    }
}
