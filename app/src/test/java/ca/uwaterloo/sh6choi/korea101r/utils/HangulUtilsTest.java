package ca.uwaterloo.sh6choi.korea101r.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Samson on 2015-10-08.
 */
public class HangulUtilsTest {
    private static final char CHAR_TYPE_1 = '오';
    private static final char CHAR_TYPE_2 = '안';
    private static final char CHAR_TYPE_3 = '가';
    private static final char CHAR_TYPE_4_SINGLE = '국';
    private static final char CHAR_TYPE_4_DOUBLE = '갔';

    private static final String VERB_1 = "가다";
    private static final String VERB_2 = "잡다";
    private static final String VERB_3 = "공부하다";
    private static final String VERB_4 = "사랑하다";


    @Test
    public void testUnicodeDecomposition() {
        char[] decomposed1 = HangulUtils.unicodeDecompose(CHAR_TYPE_1);
        char[] decomposed2 = HangulUtils.unicodeDecompose(CHAR_TYPE_2);
        char[] decomposed3 = HangulUtils.unicodeDecompose(CHAR_TYPE_3);
        char[] decomposed4single = HangulUtils.unicodeDecompose(CHAR_TYPE_4_SINGLE);
        char[] decomposed4double = HangulUtils.unicodeDecompose(CHAR_TYPE_4_DOUBLE);

        assertThat(decomposed1.length, equalTo(2));
        assertThat(decomposed1[0], equalTo('ㅇ'));
        assertThat(decomposed1[1], equalTo('ᅩ'));

        assertThat(decomposed2.length, equalTo(3));
        assertThat(decomposed2[0], equalTo('ㅇ'));
        assertThat(decomposed2[1], equalTo('ᅡ'));
        assertThat(decomposed2[2], equalTo('ᆫ'));

        assertThat(decomposed3.length, equalTo(2));
        assertThat(decomposed3[0], equalTo('ㄱ'));
        assertThat(decomposed3[1], equalTo('ᅡ'));

        assertThat(decomposed4single.length, equalTo(3));
        assertThat(decomposed4single[0], equalTo('ㄱ'));
        assertThat(decomposed4single[1], equalTo('ᅮ'));
        assertThat(decomposed4single[2], equalTo('ᆨ'));

        assertThat(decomposed4double.length, equalTo(3));
        assertThat(decomposed4double[0], equalTo('ㄱ'));
        assertThat(decomposed4double[1], equalTo('ᅡ'));
        assertThat(decomposed4double[2], equalTo('ᆻ'));
    }

    @Test
    public void testDecomposition() {
        char[] decomposed1 = HangulUtils.decompose(CHAR_TYPE_1);
        char[] decomposed2 = HangulUtils.decompose(CHAR_TYPE_2);
        char[] decomposed3 = HangulUtils.decompose(CHAR_TYPE_3);
        char[] decomposed4single = HangulUtils.decompose(CHAR_TYPE_4_SINGLE);
        char[] decomposed4double = HangulUtils.decompose(CHAR_TYPE_4_DOUBLE);

        assertThat(decomposed1.length, equalTo(2));
        assertThat(decomposed1[0], equalTo('ㅇ'));
        assertThat(decomposed1[1], equalTo('ㅗ'));

        assertThat(decomposed2.length, equalTo(3));
        assertThat(decomposed2[0], equalTo('ㅇ'));
        assertThat(decomposed2[1], equalTo('ㅏ'));
        assertThat(decomposed2[2], equalTo('ㄴ'));

        assertThat(decomposed3.length, equalTo(2));
        assertThat(decomposed3[0], equalTo('ㄱ'));
        assertThat(decomposed3[1], equalTo('ㅏ'));

        assertThat(decomposed4single.length, equalTo(3));
        assertThat(decomposed4single[0], equalTo('ㄱ'));
        assertThat(decomposed4single[1], equalTo('ㅜ'));
        assertThat(decomposed4single[2], equalTo('ㄱ'));

        assertThat(decomposed4double.length, equalTo(3));
        assertThat(decomposed4double[0], equalTo('ㄱ'));
        assertThat(decomposed4double[1], equalTo('ㅏ'));
        assertThat(decomposed4double[2], equalTo('ㅆ'));
    }

    @Test
    public void testComposition() {
        char[] decomposed1 = new char[] {'ㅇ', 'ㅗ'};
        char[] decomposed2 = new char[] {'ㅇ', 'ㅏ', 'ㄴ'};
        char[] decomposed3 = new char[] {'ㄱ', 'ㅏ'};
        char[] decomposed4single = new char[] {'ㄱ', 'ㅜ', 'ㄱ'};
        char[] decomposed4double = new char[] {'ㄱ', 'ㅏ', 'ㅆ'};

        assertThat(HangulUtils.compose(decomposed1), equalTo(CHAR_TYPE_1));
        assertThat(HangulUtils.compose(decomposed2), equalTo(CHAR_TYPE_2));
        assertThat(HangulUtils.compose(decomposed3), equalTo(CHAR_TYPE_3));
        assertThat(HangulUtils.compose(decomposed4single), equalTo(CHAR_TYPE_4_SINGLE));
        assertThat(HangulUtils.compose(decomposed4double), equalTo(CHAR_TYPE_4_DOUBLE));
    }

    @Test
    public void testCharRomanization() {
        String romanized1 = HangulUtils.romanize(CHAR_TYPE_1);
        String romanized2 = HangulUtils.romanize(CHAR_TYPE_2);
        String romanized3 = HangulUtils.romanize(CHAR_TYPE_3);
        String romanized4single = HangulUtils.romanize(CHAR_TYPE_4_SINGLE);
        String romanized4double = HangulUtils.romanize(CHAR_TYPE_4_DOUBLE);

        assertThat(romanized1, equalTo("o"));
        assertThat(romanized2, equalTo("an"));
        assertThat(romanized3, equalTo("ga"));
        assertThat(romanized4single, equalTo("guk"));
        assertThat(romanized4double, equalTo("gat"));
    }

    @Test
    public void testStringRomanization() {
        String romanized1 = HangulUtils.romanize(Character.toString(CHAR_TYPE_1));
        String romanized2 = HangulUtils.romanize(Character.toString(CHAR_TYPE_2));
        String romanized3 = HangulUtils.romanize(Character.toString(CHAR_TYPE_3));
        String romanized4single = HangulUtils.romanize(Character.toString(CHAR_TYPE_4_SINGLE));
        String romanized4double = HangulUtils.romanize(Character.toString(CHAR_TYPE_4_DOUBLE));

        assertThat(romanized1, equalTo("o"));
        assertThat(romanized2, equalTo("an"));
        assertThat(romanized3, equalTo("ga"));
        assertThat(romanized4single, equalTo("guk"));
        assertThat(romanized4double, equalTo("gat"));
    }

    @Test
    public void testPhraseRomanization() {
        String phrase1 = "안녕하세요";
        String phrase2 = "저는 캐나다 사람입니다.";
        String phrase3 = "아니요, 저는 미국 사람이 아닙니다.";
        String phrase4 = "중국 사람입니까?";


        String romanized1 = HangulUtils.romanize(phrase1);
        String romanized2 = HangulUtils.romanize(phrase2);
        String romanized3 = HangulUtils.romanize(phrase3);
        String romanized4 = HangulUtils.romanize(phrase4);

        assertThat(romanized1, equalTo("an-nyeong-ha-se-yo"));
        assertThat(romanized2, equalTo("jeo-neun kae-na-da sa-ram-ip-ni-da."));
        assertThat(romanized3, equalTo("a-ni-yo, jeo-neun mi-guk sa-ram-i a-nip-ni-da."));
        assertThat(romanized4, equalTo("jung-guk sa-ram-ip-ni-kka?"));
    }

    @Test
    public void testFormalPolitePresentStatementConjugation() {
        String conjugated1 = HangulUtils.conjugateFormalPolitePresent(VERB_1, ConjugationForm.STATEMENT);
        String conjugated2 = HangulUtils.conjugateFormalPolitePresent(VERB_2, ConjugationForm.STATEMENT);
        String conjugated3 = HangulUtils.conjugateFormalPolitePresent(VERB_3, ConjugationForm.STATEMENT);
        String conjugated4 = HangulUtils.conjugateFormalPolitePresent(VERB_4, ConjugationForm.STATEMENT);

        assertThat(conjugated1, equalTo("갑니다"));
        assertThat(conjugated2, equalTo("잡습니다"));
        assertThat(conjugated3, equalTo("공부합니다"));
        assertThat(conjugated4, equalTo("사랑합니다"));
    }

    @Test
    public void testFormalPolitePresentQuestionConjugation() {
        String conjugated1 = HangulUtils.conjugateFormalPolitePresent(VERB_1, ConjugationForm.QUESTION);
        String conjugated2 = HangulUtils.conjugateFormalPolitePresent(VERB_2, ConjugationForm.QUESTION);
        String conjugated3 = HangulUtils.conjugateFormalPolitePresent(VERB_3, ConjugationForm.QUESTION);
        String conjugated4 = HangulUtils.conjugateFormalPolitePresent(VERB_4, ConjugationForm.QUESTION);

        assertThat(conjugated1, equalTo("갑니까"));
        assertThat(conjugated2, equalTo("잡습니까"));
        assertThat(conjugated3, equalTo("공부합니까"));
        assertThat(conjugated4, equalTo("사랑합니까"));
    }

    @Test
    public void testFormalPolitePresentCommandConjugation() {
        String conjugated1 = HangulUtils.conjugateFormalPolitePresent(VERB_1, ConjugationForm.COMMAND);
        String conjugated2 = HangulUtils.conjugateFormalPolitePresent(VERB_2, ConjugationForm.COMMAND);
        String conjugated3 = HangulUtils.conjugateFormalPolitePresent(VERB_3, ConjugationForm.COMMAND);
        String conjugated4 = HangulUtils.conjugateFormalPolitePresent(VERB_4, ConjugationForm.COMMAND);

        assertThat(conjugated1, equalTo("가십시오"));
        assertThat(conjugated2, equalTo("잡으십시오"));
        assertThat(conjugated3, equalTo("공부하십시오"));
        assertThat(conjugated4, equalTo("사랑하십시오"));
    }

    @Test
    public void testFormalPolitePresentProposalConjugation() {
        String conjugated1 = HangulUtils.conjugateFormalPolitePresent(VERB_1, ConjugationForm.PROPOSAL);
        String conjugated2 = HangulUtils.conjugateFormalPolitePresent(VERB_2, ConjugationForm.PROPOSAL);
        String conjugated3 = HangulUtils.conjugateFormalPolitePresent(VERB_3, ConjugationForm.PROPOSAL);
        String conjugated4 = HangulUtils.conjugateFormalPolitePresent(VERB_4, ConjugationForm.PROPOSAL);

        assertThat(conjugated1, equalTo("갑시다"));
        assertThat(conjugated2, equalTo("잡읍시다"));
        assertThat(conjugated3, equalTo("공부합시다"));
        assertThat(conjugated4, equalTo("사랑합시다"));
    }

}
