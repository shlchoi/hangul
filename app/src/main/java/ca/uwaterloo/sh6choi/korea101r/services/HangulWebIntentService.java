package ca.uwaterloo.sh6choi.korea101r.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.MalformedURLException;
import java.net.URL;

import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;

/**
 * Created by Samson on 2015-09-24.
 */
public class HangulWebIntentService extends WebIntentService {

    private static final String TAG = DictationWebIntentService.class.getCanonicalName();

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
        JsonObject o = new JsonParser().parse(response).getAsJsonObject();
        HangulCharacter[] hangulChars = new Gson().fromJson(o.getAsJsonArray("hangul"), HangulCharacter[].class);
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, e.getMessage());
    }
}
