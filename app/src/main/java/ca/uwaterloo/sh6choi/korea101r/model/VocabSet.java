package ca.uwaterloo.sh6choi.korea101r.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samson on 2015-09-23.
 */
public class VocabSet {

    @SerializedName("lesson_id")
    private int mId;

    @SerializedName("words")
    private VocabWord[] mWords;

    public VocabSet(int id, VocabWord[] words) {
        mId = id;
        mWords = words;
    }

    public int getId() {
        return mId;
    }

    public VocabWord[] getWords() {
        return mWords;
    }
}
