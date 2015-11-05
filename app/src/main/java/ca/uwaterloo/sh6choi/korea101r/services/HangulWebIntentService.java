package ca.uwaterloo.sh6choi.korea101r.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import ca.uwaterloo.sh6choi.korea101r.database.DatabaseRequestCallback;
import ca.uwaterloo.sh6choi.korea101r.database.HangulCharacterDataSource;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;

/**
 * Created by Samson on 2015-09-24.
 */
public class HangulWebIntentService extends WebIntentService {

    private static final String TAG = HangulWebIntentService.class.getCanonicalName();

    public HangulWebIntentService() {
        super("HangulWebIntentService");
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL("https://raw.githubusercontent.com/shlchoi/korea101r/master/hangul.json");
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "Hangul retrieved");
        JsonArray array = new JsonParser().parse(response).getAsJsonArray();
        HangulCharacter[] hangulChars = new Gson().fromJson(array, HangulCharacter[].class);

        final HangulCharacterDataSource dataSource = new HangulCharacterDataSource(this);
        dataSource.open();

        dataSource.update(Arrays.asList(hangulChars), new DatabaseRequestCallback<Void>() {
            @Override
            public void processResults(Void results) {
                dataSource.close();
            }
        });
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, e.getMessage());
    }
}
