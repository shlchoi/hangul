package ca.uwaterloo.sh6choi.korea101r.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by Samson on 2015-09-23.
 */
public class HangulCharacter implements Parcelable, FlashcardItem {

    @SerializedName("char_id")
    private String mCharId;

    @SerializedName("character")
    private String mCharacter;

    @SerializedName("name")
    private String mName;

    @SerializedName("pronunciation")
    private String mPronunciation;

    @SerializedName("romanization")
    private String[] mRomanization;

    @SerializedName("type")
    private String mType;

    public HangulCharacter(String charId, String character, String name, String pronunciation, String[] romanization, String type) {
        mCharId = charId;
        mCharacter = character;
        mName = name;
        mPronunciation = pronunciation;
        mRomanization = romanization;
        mType = type;
    }

    protected HangulCharacter(Parcel in) {
        mCharId = in.readString();
        mCharacter = in.readString();
        mName = in.readString();
        mPronunciation = in.readString();
        mRomanization = in.createStringArray();
        mType = in.readString();
    }

    public static final Creator<HangulCharacter> CREATOR = new Creator<HangulCharacter>() {
        @Override
        public HangulCharacter createFromParcel(Parcel in) {
            return new HangulCharacter(in);
        }

        @Override
        public HangulCharacter[] newArray(int size) {
            return new HangulCharacter[size];
        }
    };

    public String getCharId() {
        return mCharId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public String getName() {
        return mName;
    }

    public String getPronunciation() {
        return mPronunciation;
    }

    public String[] getRomanization() {
        return mRomanization;
    }

    public String getType() {
        return mType;
    }

    @Override
    public String getFlashcardText() {
        return getCharacter();
    }

    @Override
    public String getFlashcardHint() {
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCharId);
        dest.writeString(mCharacter);
        dest.writeString(mName);
        dest.writeString(mPronunciation);
        dest.writeStringArray(mRomanization);
        dest.writeString(mType);
    }


    public static class CharacterComparator implements Comparator<HangulCharacter>
    {
        public int compare(HangulCharacter c1, HangulCharacter c2)
        {
            return c1.getCharacter().compareTo(c2.getCharacter());
        }
    }
}
