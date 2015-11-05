package ca.uwaterloo.sh6choi.korea101r.utils;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-10-28.
 */
public enum VerbTense {
    PAST(R.string.verb_tense_past),
    PRESENT(R.string.verb_tense_present);

    private int mStringResId;

    VerbTense(int stringResId) {
        mStringResId = stringResId;
    }

    public int getStringResId() {
        return mStringResId;
    }
}
