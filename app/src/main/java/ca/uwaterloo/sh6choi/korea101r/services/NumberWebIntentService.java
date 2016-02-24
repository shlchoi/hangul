package ca.uwaterloo.sh6choi.korea101r.services;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import ca.uwaterloo.sh6choi.korea101r.database.DatabaseRequestCallback;
import ca.uwaterloo.sh6choi.korea101r.database.NumberDataSource;
import ca.uwaterloo.sh6choi.korea101r.model.KoreanNumber;
import ca.uwaterloo.sh6choi.korea101r.utils.NumberUtils;

/**
 * Created by Samson on 2015-09-24.
 */
public class NumberWebIntentService extends WebIntentService {

    private static final String TAG = NumberWebIntentService.class.getCanonicalName();
    public static final String ACTION_SUCCESS = TAG + ".action.success";

    public NumberWebIntentService() {
        super("NumberWebIntentService");
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL("https://raw.githubusercontent.com/shlchoi/hangul/master/numbers.json");
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "Numbers retrieved");
        JsonArray array = new JsonParser().parse(response).getAsJsonArray();
        KoreanNumber[] koreanNumbers = new Gson().fromJson(array, KoreanNumber[].class);

        final NumberDataSource dataSource = new NumberDataSource(this);
        dataSource.open();
        dataSource.update(Arrays.asList(koreanNumbers), new DatabaseRequestCallback<Void>() {
            @Override
            public void processResults(Void results) {
                dataSource.close();
                NumberUtils.refreshMap(NumberWebIntentService.this);
                sendBroadcast(new Intent(ACTION_SUCCESS));
            }
        });


    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, e.getMessage());
    }
}
