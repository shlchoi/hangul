package ca.uwaterloo.sh6choi.korea101r.utils;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-10-01.
 */
public enum ConjugationForm {
    STATEMENT (R.string.conjugation_statement),
    QUESTION (R.string.conjugation_question),
    COMMAND (R.string.conjugation_command),
    PROPOSAL (R.string.conjugation_proposal);

    private int mStringResId;

    ConjugationForm(int stringResId) {
        mStringResId = stringResId;
    }

    public int getStringResId() {
        return mStringResId;
    }
}
