package ca.uwaterloo.sh6choi.korea101r.utils;

import android.text.TextUtils;

/**
 * Created by Samson on 2015-09-29.
 */
public class HangulUtils {
    private static final int UNICODE_OFFSET = 44032;
    private static final int CHARS_PER_INITIAL = 588;
    private static final int CHARS_PER_VOWEL = 28;

    private static final char[] UNICODE_INITIALS = new char[]{'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
    private static final char[] UNICODE_VOWELS = new char[] {'ᅡ','ᅢ','ᅣ','ᅤ','ᅥ','ᅦ','ᅧ','ᅨ','ᅩ','ᅪ','ᅫ','ᅬ','ᅭ','ᅮ','ᅯ','ᅰ','ᅱ','ᅲ','ᅳ','ᅴ','ᅵ'};
    private static final char[] UNICODE_FINALS = new char[]{'ᆨ','ᆩ','ᆪ','ᆫ','ᆬ','ᆭ','ᆮ','ᆯ','ᆰ','ᆱ','ᆲ','ᆳ','ᆴ','ᆵ','ᆶ','ᆷ','ᆸ','ᆹ','ᆺ','ᆻ','ᆼ','ᆽ','ᆾ','ᆿ','ᇀ','ᇁ'};

    private static final char[] CHARACTER_INITIALS = new char[]{'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
    private static final char[] CHARACTER_VOWELS = new char[]{'ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ','ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ','ㅣ'};
    private static final char[] CHARACTER_FINALS = new char[]{'ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ','ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ','ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};

    private static final String[] ROMANIZED_INITIAL = new String[]{"g","kk","n","d","tt","r","m","b","pp","s","ss","","j","jj","ch","k","t","p","h"};
    private static final String[] ROMANIZED_VOWEL = new String[]{"a","ae","ya","yae","eo","e","yeo","ye","o","wa","wae","oe","yo","u","wo","we","wi","yu","eu","ui","i"};
    private static final String[] ROMANIZED_FINAL = new String[]{"k","k","k","n","n","n","t","l","k","m","p","l","l","l","l","m","p","p","t","t","ng","t","t","k","t","p","t"};

    public static char[] unicodeDecompose(char hangul) {
        int value = Character.toString(hangul).codePointAt(0) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        int initialValue = value / CHARS_PER_INITIAL;
        int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
        int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

        char initialChar = UNICODE_INITIALS[initialValue];
        char vowelChar = UNICODE_VOWELS[vowelValue];

        if (finalValue == 0) {
            return new char[]{initialChar, vowelChar};
        } else {
            char finalChar = UNICODE_FINALS[finalValue - 1];
            return new char[]{initialChar, vowelChar, finalChar};
        }
    }

    public static char[] decompose(char hangul) {
        int value = Character.toString(hangul).codePointAt(0) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        int initialValue = value / CHARS_PER_INITIAL;
        int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
        int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

        char initialChar = CHARACTER_INITIALS[initialValue];
        char vowelChar = CHARACTER_VOWELS[vowelValue];

        if (finalValue == 0) {
            return new char[]{initialChar, vowelChar};
        } else {
            char finalChar = CHARACTER_FINALS[finalValue - 1];
            return new char[]{initialChar, vowelChar, finalChar};
        }
    }

    public static char compose(char[] components) {
        if (components.length < 2) {
            throw new IllegalArgumentException("Hangul characters must have at least two components (use ㅇ for vowels)");
        } else if (components.length > 4) {
            throw new IllegalArgumentException("Hangul characters can only have three components (use the combined form of the finals");
        }

        int initialValue = -1;
        int vowelValue = -1;
        int finalValue = 0;

        for (int i = 0; i < CHARACTER_INITIALS.length; i ++) {
            if (CHARACTER_INITIALS[i] == components[0]) {
                initialValue = i;
                break;
            }
        }

        if (initialValue < 0) {
            throw new IllegalArgumentException("Not a valid hangul component");
        }

        for (int i = 0; i < CHARACTER_VOWELS.length; i ++) {
            if (CHARACTER_VOWELS[i] == components[1]) {
                vowelValue = i;
                break;
            }
        }

        if (vowelValue < 0) {
            throw new IllegalArgumentException("Not a valid hangul component");
        }

        if (components.length > 2) {
            for (int i = 0; i < CHARACTER_FINALS.length; i ++) {
                if (CHARACTER_FINALS[i] == components[2]) {
                    finalValue = i + 1;
                    break;
                }
            }
        }

        char[] unicodeArray = Character.toChars(initialValue * CHARS_PER_INITIAL + vowelValue * CHARS_PER_VOWEL + finalValue + UNICODE_OFFSET);

        return unicodeArray[0];
    }

    public static String romanize(char hangul) {
        int value = Character.toString(hangul).codePointAt(0) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        int initialValue = value / CHARS_PER_INITIAL;
        int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
        int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

        StringBuilder builder = new StringBuilder();
        String initialChar = ROMANIZED_INITIAL[initialValue];
        String vowelChar = ROMANIZED_VOWEL[vowelValue];

        builder.append(initialChar);
        builder.append(vowelChar);

        if (finalValue > 0) {
            String finalChar = ROMANIZED_FINAL[finalValue - 1];
            builder.append(finalChar);
        }

        return builder.toString();
    }

    public static String romanize(String hangul) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < hangul.length(); i ++) {
            if (hangul.charAt(i) == ' ') {
                builder.append(" ");
            } else if (hangul.charAt(i) == '.') {
                builder.append(".");
            } else if (hangul.charAt(i) == '?') {
                builder.append("?");
            } else if (hangul.charAt(i) == ',') {
                builder.append(",");
            } else {

                int value = hangul.codePointAt(i) - UNICODE_OFFSET;

                if (value < 0) {
                    throw new IllegalArgumentException("Provided string is not a hangul character");
                }

                int initialValue = value / CHARS_PER_INITIAL;
                int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
                int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);


                String initialChar = ROMANIZED_INITIAL[initialValue];
                String vowelChar = ROMANIZED_VOWEL[vowelValue];

                builder.append(initialChar);
                builder.append(vowelChar);

                if (finalValue > 0) {
                    String finalChar = ROMANIZED_FINAL[finalValue - 1];
                    builder.append(finalChar);
                }

                if (i < hangul.length() - 1) {
                    if (hangul.codePointAt(i + 1) - UNICODE_OFFSET > 0) {
                        builder.append("-");
                    }
                }
            }
        }

        return builder.toString();
    }

    public static String conjugatePresent(String verb, SpeechForm speechForm, ConjugationType conjugationType) {

        if (speechForm != SpeechForm.FORMAL_POLITE) {
            throw new IllegalArgumentException("Selected speech form is not implemented yet");
        }

        if (verb.substring(verb.length() - 1).compareTo("다") != 0) {
            throw new IllegalArgumentException("Provided string is not a verb or in basic verb form");
        }

        StringBuilder builder = new StringBuilder();
        String stem = verb.substring(0, verb.length() - 1);

        int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        int initialValue = value / CHARS_PER_INITIAL;
        int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
        int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

        if (conjugationType == ConjugationType.STATEMENT || conjugationType == ConjugationType.QUESTION) {
            if (stem.length() > 1) {
                builder.append(stem.substring(0, stem.length() - 1));
            }

            //ends with vowel
            if (finalValue == 0) {
                finalValue = 16;

                builder.append(compose(new char[]{CHARACTER_INITIALS[initialValue], CHARACTER_VOWELS[vowelValue], CHARACTER_FINALS[finalValue]}));
            } else {
                builder.append(stem.substring(stem.length() - 1));
                builder.append("습");
            }

            builder.append("니");

            if (conjugationType == ConjugationType.STATEMENT) {
                builder.append("다");
            } else {
                builder.append("까");
            }
        } else if (conjugationType == ConjugationType.COMMAND) {
            builder.append(stem);

            //ends with consonant
            if (finalValue != 0) {
                builder.append("으");
            }

            builder.append("십시오");
        } else if (conjugationType == ConjugationType.PROPOSAL) {
            if (stem.length() > 1) {
                builder.append(stem.substring(0, stem.length() - 1));
            }

            if (finalValue == 0) {
                finalValue = 16;

                builder.append(compose(new char[]{CHARACTER_INITIALS[initialValue], CHARACTER_VOWELS[vowelValue], CHARACTER_FINALS[finalValue]}));
            } else {
                builder.append(stem.substring(stem.length() - 1));
                builder.append("읍");
            }
            builder.append("시다");
        }

        return builder.toString();
    }

    public static String conjugatePresentWithHonorific(String verb, SpeechForm speechForm, ConjugationType conjugationType) {
        if (speechForm != SpeechForm.FORMAL_POLITE) {
            throw new IllegalArgumentException("Selected speech form is not implemented yet");
        }

        if (!TextUtils.equals(verb.substring(verb.length() - 1), "다")) {
            throw new IllegalArgumentException("Provided string is not a verb or in basic verb form");
        }

        StringBuilder builder = new StringBuilder();
        String stem = verb.substring(0, verb.length() - 1);

        int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        int initialValue = value / CHARS_PER_INITIAL;
        int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
        int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

        if (stem.length() > 1) {
            builder.append(stem.substring(0, stem.length() - 1));
        }

        if (finalValue > 0) {
            builder.append("으");
        }
        if (conjugationType == ConjugationType.STATEMENT || conjugationType == ConjugationType.QUESTION) {
            builder.append("십니");

            if (conjugationType == ConjugationType.STATEMENT) {
                builder.append("다");
            } else {
                builder.append("까");
            }
        } else if (conjugationType == ConjugationType.COMMAND) {
           builder.append("시십시오");
        } else if (conjugationType == ConjugationType.PROPOSAL) {
            builder.append("십시다");
        }

        return builder.toString();
    }

    public static String conjugatePresentNegativeShort(String verb, SpeechForm speechForm, ConjugationType conjugationType) {
        StringBuilder builder = new StringBuilder();
        builder.append("안");
        builder.append(conjugatePresent(verb, speechForm, conjugationType));

        return builder.toString();
    }

    public static CharacterType getCharacterType(char hangul) {
        char[] decomposed = HangulUtils.decompose(hangul);

        if (decomposed.length == 3) {
            if (decomposed[0] == 'ㅇ') {
                return CharacterType.TYPE_2;
            } else {
                return CharacterType.TYPE_4;
            }
        }

        if (decomposed[0] != 'ㅇ') {
            return CharacterType.TYPE_3;
        } else {
            return CharacterType.TYPE_1;
        }
    }
}
