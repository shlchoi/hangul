package ca.uwaterloo.sh6choi.korea101r.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by Samson on 2015-11-01.
 */
public class KoreanNumber implements Parcelable {
    @SerializedName("number")
    private int mNumber;

    @SerializedName("korean")
    private String mKorean;

    @SerializedName("sino-korean")
    private String mSinoKorean;

    @SerializedName("count_form")
    private String mCount;

    public KoreanNumber(int number, String korean) {
        mNumber = number;
        mKorean = korean;
    }

    public KoreanNumber(int number, String korean, String sinoKorean) {
        mNumber = number;
        mKorean = korean;
        mSinoKorean = sinoKorean;
    }

    public KoreanNumber(int number, String korean, String sinoKorean, String count) {
        mNumber = number;
        mKorean = korean;
        mSinoKorean = sinoKorean;
        mCount = count;
    }

    protected KoreanNumber(Parcel in) {
        mNumber = in.readInt();
        mKorean = in.readString();
        mSinoKorean = in.readString();
        mCount = in.readString();
    }

    public static final Creator<KoreanNumber> CREATOR = new Creator<KoreanNumber>() {
        @Override
        public KoreanNumber createFromParcel(Parcel in) {
            return new KoreanNumber(in);
        }

        @Override
        public KoreanNumber[] newArray(int size) {
            return new KoreanNumber[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mNumber);
        dest.writeString(mKorean);
        dest.writeString(mSinoKorean);
        dest.writeString(mCount);
    }

    public int getNumber() {
        return mNumber;
    }

    public String getKorean() {
        return mKorean;
    }

    public String getSinoKorean() {
        return mSinoKorean;
    }

    public String getCount() {
        if (TextUtils.isEmpty(mCount) || TextUtils.equals(mCount, "null")) {
            return mKorean;
        }
        return mCount;
    }

    public static class NumberComparator implements Comparator<KoreanNumber>
    {
        public int compare(KoreanNumber c1, KoreanNumber c2)
        {
            return c1.getNumber() - c2.getNumber();
        }
    }
}
