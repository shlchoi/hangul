package ca.uwaterloo.sh6choi.korea101r.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samson on 2015-09-23.
 */
public class DictationSet {

    @SerializedName("id")
    private String mId;

    @SerializedName("words")
    private DictationWord[] mWords;

    public String getId() {
        return mId;
    }

    public DictationWord[] getWords() {
        return mWords;
    }
}
