package ca.uwaterloo.sh6choi.korea101r.utils;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-10-01.
 */
public enum SpeechForm {
    FORMAL_POLITE (R.string.speech_form_formal_polite),
    FORMAL_PLAIN (R.string.speech_form_formal_plain),
    INFORMAL_POLITE (R.string.speech_form_informal_polite),
    INFORMAL_PLAIN (R.string.speech_form_informal_plain);

    private int mStringResId;

    SpeechForm(int stringResId) {
        mStringResId = stringResId;
    }

    public int getStringResId() {
        return mStringResId;
    }
}
