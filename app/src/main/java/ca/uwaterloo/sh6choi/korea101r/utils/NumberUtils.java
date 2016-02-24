package ca.uwaterloo.sh6choi.korea101r.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.database.DatabaseRequestCallback;
import ca.uwaterloo.sh6choi.korea101r.database.NumberDataSource;
import ca.uwaterloo.sh6choi.korea101r.model.KoreanNumber;

/**
 * Created by Samson on 2015-11-02.
 */
public class NumberUtils {
    private static HashMap<Integer, KoreanNumber> sKoreanNumberMap;

    public static void refreshMap(Context context) {
        sKoreanNumberMap = new HashMap<>();
        NumberDataSource dataSource = new NumberDataSource(context);
        dataSource.open();
        dataSource.queryNumbers(new DatabaseRequestCallback<List<KoreanNumber>>() {
            @Override
            public void processResults(List<KoreanNumber> results) {
                for (int i = 0; i < results.size(); i++) {
                    sKoreanNumberMap.put(results.get(i).getNumber(), results.get(i));
                }
            }
        });
    }

    public static String getKoreanNumber(int number) {
        StringBuilder builder = new StringBuilder();

        if (number >= 100) {
            throw new IllegalArgumentException("Number is too high for Native Korean numerals. Use Sino-Korean numerals instead.");
        }

        if (number / 10 > 0) {
            int multiple = number / 10;
            builder.append(sKoreanNumberMap.get(multiple * 10).getKorean());
            if (number % 10 > 0) {
                builder.append(getKoreanNumber(number % 10));
            }
            return builder.toString();
        }

        return sKoreanNumberMap.get(number).getKorean();
    }

    public static String getCountForm(int number) {
        StringBuilder builder = new StringBuilder();

        if (number >= 100) {
            throw new IllegalArgumentException("Number is too high for Native Korean numerals. Use Sino-Korean numerals instead.");
        }

        if (number / 10 > 0) {
            int multiple = number / 10;
            builder.append(sKoreanNumberMap.get(multiple * 10).getCount());
            if (number % 10 > 0) {
                builder.append(getCountForm(number % 10));
            }
            return builder.toString();
        }

        if (TextUtils.isEmpty(sKoreanNumberMap.get(number).getCount()) || TextUtils.equals(sKoreanNumberMap.get(number).getCount(), "null")) {
            return sKoreanNumberMap.get(number).getKorean();
        } else {
            return sKoreanNumberMap.get(number).getCount();
        }
    }

    public static String getSinoKoreanNumber(int number) {
        StringBuilder builder = new StringBuilder();

        if (number / 100000000 > 0) {
            int multiple = number / 100000000;
            if (multiple > 1) {
                builder.append(getSinoKoreanNumber(multiple));
            }
            builder.append(sKoreanNumberMap.get(100000000).getSinoKorean());
            if (number % 100000000 > 0) {
                builder.append(getSinoKoreanNumber(number % 100000000));
            }
            return builder.toString();
        }

        if (number / 10000 > 0) {
            int multiple = number / 10000;
            if (multiple > 1) {
                builder.append(getSinoKoreanNumber(multiple));
            }
            builder.append(sKoreanNumberMap.get(10000).getSinoKorean());
            if (number % 10000 > 0) {
                builder.append(getSinoKoreanNumber(number % 10000));
            }
            return builder.toString();
        }

        if (number / 1000 > 0) {
            int multiple = number / 1000;
            if (multiple > 1) {
                builder.append(getSinoKoreanNumber(multiple));
            }
            builder.append(sKoreanNumberMap.get(1000).getSinoKorean());
            if (number % 1000 > 0) {
                builder.append(getSinoKoreanNumber(number % 1000));
            }
            return builder.toString();
        }

        if (number / 100 > 0) {
            int multiple = number / 100;
            if (multiple > 1) {
                builder.append(getSinoKoreanNumber(multiple));
            }
            builder.append(sKoreanNumberMap.get(100).getSinoKorean());
            if (number % 100 > 0) {
                builder.append(getSinoKoreanNumber(number % 100));
            }
            return builder.toString();
        }

        if (number / 10 > 0) {
            int multiple = number / 10;
            if (multiple > 1) {
                builder.append(getSinoKoreanNumber(multiple));
            }
            builder.append(sKoreanNumberMap.get(10).getSinoKorean());
            if (number % 10 > 0) {
                builder.append(getSinoKoreanNumber(number % 10));
            }
            return builder.toString();
        }

        return sKoreanNumberMap.get(number).getSinoKorean();
    }
}
