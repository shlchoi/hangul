package ca.uwaterloo.sh6choi.korea101r.utils;

import android.text.TextUtils;

/**
 * Created by Samson on 2015-09-29.
 */
public class HangulUtils {
    private static final int UNICODE_OFFSET = 44032;
    private static final int CHARS_PER_INITIAL = 588;
    private static final int CHARS_PER_VOWEL = 28;

    private static final char[] UNICODE_INITIALS = new char[]{'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
    private static final char[] UNICODE_VOWELS = new char[] {'ᅡ', 'ᅢ', 'ᅣ', 'ᅤ', 'ᅥ', 'ᅦ', 'ᅧ', 'ᅨ', 'ᅩ', 'ᅪ', 'ᅫ', 'ᅬ', 'ᅭ', 'ᅮ', 'ᅯ', 'ᅰ', 'ᅱ', 'ᅲ', 'ᅳ', 'ᅴ', 'ᅵ'};
    private static final char[] UNICODE_FINALS = new char[]{'ᆨ', 'ᆩ', 'ᆪ', 'ᆫ', 'ᆬ', 'ᆭ', 'ᆮ', 'ᆯ', 'ᆰ', 'ᆱ', 'ᆲ', 'ᆳ', 'ᆴ', 'ᆵ', 'ᆶ', 'ᆷ', 'ᆸ', 'ᆹ', 'ᆺ', 'ᆻ', 'ᆼ', 'ᆽ', 'ᆾ', 'ᆿ', 'ᇀ', 'ᇁ'};

    private static final char[] CHARACTER_INITIALS = new char[]{'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
    private static final char[] CHARACTER_VOWELS = new char[]{'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};
    private static final char[] CHARACTER_FINALS = new char[]{'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    private static final String[] ROMANIZED_INITIAL = new String[]{"g", "kk", "n", "d", "tt", "r", "m", "b", "pp", "s", "ss", "", "j", "jj", "ch", "k", "t", "p", "h"};
    private static final String[] ROMANIZED_VOWEL = new String[]{"a", "ae", "ya", "yae", "eo", "e", "yeo", "ye", "o", "wa", "wae", "oe", "yo", "u", "wo", "we", "wi", "yu", "eu", "ui", "i"};
    private static final String[] ROMANIZED_FINAL = new String[]{"k", "k", "k", "n", "n", "n", "t", "l", "k", "m", "p", "l", "l", "l", "l", "m", "p", "p", "t", "t", "ng", "t", "t", "k", "t", "p", "t"};

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

        for (int i = 0; i < CHARACTER_INITIALS.length; i++) {
            if (CHARACTER_INITIALS[i] == components[0]) {
                initialValue = i;
                break;
            }
        }

        if (initialValue < 0) {
            throw new IllegalArgumentException("Not a valid hangul component");
        }

        for (int i = 0; i < CHARACTER_VOWELS.length; i++) {
            if (CHARACTER_VOWELS[i] == components[1]) {
                vowelValue = i;
                break;
            }
        }

        if (vowelValue < 0) {
            throw new IllegalArgumentException("Not a valid hangul component");
        }

        if (components.length > 2) {
            for (int i = 0; i < CHARACTER_FINALS.length; i++) {
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

        for (int i = 0; i < hangul.length(); i++) {
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

    public static String conjugate(String verb, boolean positive, VerbTense tense, boolean honorific, ConjugationForm conjugationForm, SpeechForm speechForm) {
        if (speechForm == SpeechForm.FORMAL_PLAIN) {
            throw new IllegalArgumentException("Selected speech form is not implemented yet");
        }

        if (verb.substring(verb.length() - 1).compareTo("다") != 0) {
            throw new IllegalArgumentException("Provided string is not a verb or in basic verb form");
        }

        switch (speechForm) {
            case FORMAL_POLITE:
            default:
                return conjugateFormalPolite(verb, positive, tense, honorific, conjugationForm);
            case INFORMAL_POLITE:
                return conjugateInformalPolite(verb, positive, tense, honorific, conjugationForm);
            case INFORMAL_PLAIN:
                return conjugateInformalPlain(verb, positive, tense, honorific, conjugationForm);
        }
    }

    public static String conjugateFormalPolite(String verb, boolean positive, VerbTense tense, boolean honorific, ConjugationForm conjugationForm) {
        String stem = verb.substring(0, verb.length() - 1);

        if (honorific) {
            StringBuilder honorBuilder = new StringBuilder(stem);

            int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

            if (value < 0) {
                throw new IllegalArgumentException("Provided string is not a hangul character");
            }

            int initialValue = value / CHARS_PER_INITIAL;
            int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
            int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

            if (finalValue != 0) {
                honorBuilder.append("으");
            }
            honorBuilder.append("시");
            stem = honorBuilder.toString();
        }

        if (!positive) {
            StringBuilder negativeBuilder = new StringBuilder(stem);

            negativeBuilder.append("지 않");
            stem = negativeBuilder.toString();
        }

        if (tense == VerbTense.PAST) {

            StringBuilder tenseBuilder = new StringBuilder();

            if (stem.length() > 1) {
                tenseBuilder.append(stem.substring(0, stem.length() - 1));
            }

            if (stem.endsWith("하")) {
                tenseBuilder.append("했");
            } else {
                int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

                if (value < 0) {
                    throw new IllegalArgumentException("Provided string is not a hangul character");
                }

                int initialValue = value / CHARS_PER_INITIAL;
                int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
                int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

                if (finalValue == 0) {
                    finalValue = 19;

                    if (vowelValue == 8) {
                        //ㅗ -> ㅘ
                        vowelValue = 9;
                    } else if (vowelValue == 13) {
                        //ㅜ -> ㅝ
                        vowelValue = 14;
                    } else if (vowelValue == 20) {
                        //ㅣ -> ㅕ
                        vowelValue = 6;
                    } else if (vowelValue == 18) {
                        //ㅡ -> ㅓ
                        vowelValue = 4;
                    }

                    tenseBuilder.append(compose(new char[]{CHARACTER_INITIALS[initialValue], CHARACTER_VOWELS[vowelValue], CHARACTER_FINALS[finalValue]}));
                } else {
                    if (vowelValue == 0 || vowelValue == 8) {
                        tenseBuilder.append(stem.substring(stem.length() - 1));
                        tenseBuilder.append("았");
                    } else {
                        tenseBuilder.append(stem.substring(stem.length() - 1));
                        tenseBuilder.append("었");
                    }
                }
            }

            stem = tenseBuilder.toString();
        }

        StringBuilder builder = new StringBuilder();
        int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        int initialValue = value / CHARS_PER_INITIAL;
        int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
        int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

        if (conjugationForm == ConjugationForm.STATEMENT || conjugationForm == ConjugationForm.QUESTION) {
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

            if (conjugationForm == ConjugationForm.STATEMENT) {
                builder.append("다");
            } else {
                builder.append("까");
            }
        } else if (conjugationForm == ConjugationForm.COMMAND) {
            builder.append(stem);

            //ends with consonant
            if (finalValue != 0) {
                builder.append("으");
            }

            builder.append("십시오");
        } else if (conjugationForm == ConjugationForm.PROPOSAL) {
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

    public static String conjugateInformalPolite(String verb, boolean positive, VerbTense tense, boolean honorific, ConjugationForm conjugationForm) {
        StringBuilder builder = new StringBuilder(conjugateInformalPlain(verb, positive, tense, honorific, conjugationForm));
        builder.append("요");
        return builder.toString();
    }

    public static String conjugateInformalPlain(String verb, boolean positive, VerbTense tense, boolean honorific, ConjugationForm conjugationForm) {
        String stem = verb.substring(0, verb.length() - 1);

        if (honorific) {
            StringBuilder honorBuilder = new StringBuilder(stem);

            int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

            if (value < 0) {
                throw new IllegalArgumentException("Provided string is not a hangul character");
            }

            int initialValue = value / CHARS_PER_INITIAL;
            int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
            int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

            if (finalValue != 0) {
                honorBuilder.append("으");
            }
            honorBuilder.append("시");
            stem = honorBuilder.toString();
        }

        if (!positive) {
            StringBuilder negativeBuilder = new StringBuilder(stem);

            negativeBuilder.append("지 않");
            stem = negativeBuilder.toString();
        }

        if (tense == VerbTense.PAST) {

            StringBuilder tenseBuilder = new StringBuilder();

            if (stem.length() > 1) {
                tenseBuilder.append(stem.substring(0, stem.length() - 1));
            }

            if (stem.endsWith("하")) {
                tenseBuilder.append("했");
            } else {
                int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

                if (value < 0) {
                    throw new IllegalArgumentException("Provided string is not a hangul character");
                }

                int initialValue = value / CHARS_PER_INITIAL;
                int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
                int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

                if (finalValue == 0) {
                    finalValue = 19;

                    if (vowelValue == 8) {
                        //ㅗ -> ㅘ
                        vowelValue = 9;
                    } else if (vowelValue == 13) {
                        //ㅜ -> ㅝ
                        vowelValue = 14;
                    } else if (vowelValue == 20) {
                        //ㅣ -> ㅕ
                        vowelValue = 6;
                    } else if (vowelValue == 18) {
                        //ㅡ -> ㅓ
                        vowelValue = 4;
                    }

                    tenseBuilder.append(compose(new char[]{CHARACTER_INITIALS[initialValue], CHARACTER_VOWELS[vowelValue], CHARACTER_FINALS[finalValue]}));
                } else {
                    if (vowelValue == 0 || vowelValue == 8) {
                        tenseBuilder.append(stem.substring(stem.length() - 1));
                        tenseBuilder.append("았");
                    } else {
                        tenseBuilder.append(stem.substring(stem.length() - 1));
                        tenseBuilder.append("었");
                    }
                }
            }

            stem = tenseBuilder.toString();
        }

        StringBuilder builder = new StringBuilder();

        if (stem.length() > 1) {
            builder.append(stem.substring(0, stem.length() - 1));
        }

        if (stem.endsWith("하")) {
            builder.append("해");
        } else {
            int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

            if (value < 0) {
                throw new IllegalArgumentException("Provided string is not a hangul character");
            }

            int initialValue = value / CHARS_PER_INITIAL;
            int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
            int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

            if (finalValue == 0) {
                if (vowelValue == 8) {
                    //ㅗ -> ㅘ
                    vowelValue = 9;
                } else if (vowelValue == 13) {
                    //ㅜ -> ㅝ
                    vowelValue = 14;
                } else if (vowelValue == 20) {
                    //ㅣ -> ㅕ
                    vowelValue = 6;
                } else if (vowelValue == 18) {
                    //ㅡ -> ㅓ
                    vowelValue = 4;
                }

                builder.append(compose(new char[]{CHARACTER_INITIALS[initialValue], CHARACTER_VOWELS[vowelValue], CHARACTER_FINALS[finalValue]}));
            } else {
                if (vowelValue == 0 || vowelValue == 8) {
                    builder.append(stem.substring(stem.length() - 1));
                    builder.append("아");
                } else {
                    builder.append(stem.substring(stem.length() - 1));
                    builder.append("");
                }
            }
        }

        return builder.toString();
    }

    public static String conjugateFormalPolitePresent(String verb, ConjugationForm conjugationForm) {

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

        if (conjugationForm == ConjugationForm.STATEMENT || conjugationForm == ConjugationForm.QUESTION) {
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

            if (conjugationForm == ConjugationForm.STATEMENT) {
                builder.append("다");
            } else {
                builder.append("까");
            }
        } else if (conjugationForm == ConjugationForm.COMMAND) {
            builder.append(stem);

            //ends with consonant
            if (finalValue != 0) {
                builder.append("으");
            }

            builder.append("십시오");
        } else if (conjugationForm == ConjugationForm.PROPOSAL) {
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

    public static String conjugatePresentWithHonorific(String verb, SpeechForm speechForm, ConjugationForm conjugationForm) {
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

        builder.append(stem);

        if (finalValue > 0) {
            builder.append("으");
        }

        if (conjugationForm == ConjugationForm.STATEMENT || conjugationForm == ConjugationForm.QUESTION) {
            builder.append("십니");

            if (conjugationForm == ConjugationForm.STATEMENT) {
                builder.append("다");
            } else {
                builder.append("까");
            }
        } else if (conjugationForm == ConjugationForm.COMMAND) {
            builder.append("시십시오");
        } else if (conjugationForm == ConjugationForm.PROPOSAL) {
            builder.append("십시다");
        }

        return builder.toString();
    }

    public static String congugatePast(String verb, SpeechForm speechForm, ConjugationForm conjugationForm) {

        if (speechForm != SpeechForm.FORMAL_POLITE) {
            throw new IllegalArgumentException("Selected speech form is not implemented yet");
        }

        if (conjugationForm == ConjugationForm.COMMAND || conjugationForm == ConjugationForm.PROPOSAL) {
            throw new IllegalArgumentException("Selected conjugation type is not possible");
        }

        if (verb.substring(verb.length() - 1).compareTo("다") != 0) {
            throw new IllegalArgumentException("Provided string is not a verb or in basic verb form");
        }

        StringBuilder builder = new StringBuilder();
        String stem = verb.substring(0, verb.length() - 1);

        if (stem.length() > 1) {
            builder.append(stem.substring(0, stem.length() - 1));
        }

        if (stem.endsWith("하")) {
            builder.append("했");
        } else {
            int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

            if (value < 0) {
                throw new IllegalArgumentException("Provided string is not a hangul character");
            }

            int initialValue = value / CHARS_PER_INITIAL;
            int vowelValue = (value - (initialValue * CHARS_PER_INITIAL)) / CHARS_PER_VOWEL;
            int finalValue = (value - (initialValue * CHARS_PER_INITIAL)) - (vowelValue * CHARS_PER_VOWEL);

            //a or o
            if (vowelValue == 0 || vowelValue == 8) {
                if (finalValue == 0) {
                    finalValue = 19;

                    //ㅗ -> ㅘ
                    if (vowelValue == 8) {
                        vowelValue = 9;
                    }
                    builder.append(compose(new char[]{CHARACTER_INITIALS[initialValue], CHARACTER_VOWELS[vowelValue], CHARACTER_FINALS[finalValue]}));
                } else {
                    builder.append(stem.substring(stem.length() - 1));
                    builder.append("았");
                }
            } else {
                if (finalValue == 0 && vowelValue != 18) {
                    finalValue = 19;

                    //ㅜ -> ㅝ
                    if (vowelValue == 13) {
                        vowelValue = 14;

                    //ㅣ -> ㅕ
                    } else if (vowelValue == 20) {
                        vowelValue = 6;
                    }
                    builder.append(compose(new char[]{CHARACTER_INITIALS[initialValue], CHARACTER_VOWELS[vowelValue], CHARACTER_FINALS[finalValue]}));
                } else {
                    builder.append(stem.substring(stem.length() - 1));
                    builder.append("었");
                }
            }
        }

        builder.append("습니");

        if (conjugationForm == ConjugationForm.STATEMENT) {
            builder.append("다");
        } else {
            builder.append("까");
        }

        return builder.toString();
    }

    public static String conjugatePresentNegative(String verb, SpeechForm speechForm, ConjugationForm conjugationForm) {
        if (speechForm != SpeechForm.FORMAL_POLITE) {
            throw new IllegalArgumentException("Selected speech form is not implemented yet");
        }

        StringBuilder builder = new StringBuilder();
        String stem = verb.substring(0, verb.length() - 1);

        int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        builder.append(stem);
        builder.append("지 않");

        if (conjugationForm == ConjugationForm.STATEMENT || conjugationForm == ConjugationForm.QUESTION) {
            builder.append("습니");

            if (conjugationForm == ConjugationForm.STATEMENT) {
                builder.append("다");
            } else {
                builder.append("까");
            }
        } else if (conjugationForm == ConjugationForm.COMMAND) {
            builder.append("십시오");
        } else if (conjugationForm == ConjugationForm.PROPOSAL) {
            builder.append("읍시다");
        }

        return builder.toString();
    }

    public static String conjugatePastNegative(String verb, SpeechForm speechForm, ConjugationForm conjugationForm) {
        if (speechForm != SpeechForm.FORMAL_POLITE) {
            throw new IllegalArgumentException("Selected speech form is not implemented yet");
        }

        if (conjugationForm == ConjugationForm.COMMAND || conjugationForm == ConjugationForm.PROPOSAL) {
            throw new IllegalArgumentException("Selected conjugation type is not possible");
        }

        StringBuilder builder = new StringBuilder();
        String stem = verb.substring(0, verb.length() - 1);

        int value = stem.codePointAt(stem.length() - 1) - UNICODE_OFFSET;

        if (value < 0) {
            throw new IllegalArgumentException("Provided string is not a hangul character");
        }

        builder.append(stem);
        builder.append("지 않았습니");

        if (conjugationForm == ConjugationForm.STATEMENT) {
            builder.append("다");
        } else {
            builder.append("까");
        }

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
