package ca.uwaterloo.sh6choi.korea101r.utils;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-10-01.
 */
public enum ConjugationType {
    STATEMENT (R.string.conjugation_statement, R.string.conjugation_statement_vowel_hint, R.string.conjugation_statement_consonant_hint),
    QUESTION (R.string.conjugation_question, R.string.conjugation_question_vowel_hint, R.string.conjugation_question_consonant_hint),
    COMMAND (R.string.conjugation_command, R.string.conjugation_command_vowel_hint, R.string.conjugation_command_consonant_hint),
    PROPOSAL (R.string.conjugation_proposal, R.string.conjugation_proposal_vowel_hint, R.string.conjugation_proposal_consonant_hint);

    private int mStringResId;
    private int mVowelHintStringResId;
    private int mConsonantHintStringResId;

    ConjugationType(int stringResId, int vowelHintStringResId, int consonantHintStringResId) {
        mStringResId = stringResId;
        mVowelHintStringResId = vowelHintStringResId;
        mConsonantHintStringResId = consonantHintStringResId;
    }

    public int getStringResId() {
        return mStringResId;
    }

    public int getVowelHintStringResId() {
        return mVowelHintStringResId;
    }

    public int getConsonantHintStringResId() {
        return mConsonantHintStringResId;
    }
}
