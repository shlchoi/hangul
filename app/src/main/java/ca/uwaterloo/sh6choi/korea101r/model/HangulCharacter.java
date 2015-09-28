package ca.uwaterloo.sh6choi.korea101r.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samson on 2015-09-23.
 */
public class HangulCharacter {

    @SerializedName("character")
    private String mCharacter;

    @SerializedName("pronunciation")
    private String mPronunciation;

    public String getCharacter() {
        return mCharacter;
    }

    public String getPronunciation() {
        return mPronunciation;
    }
}
