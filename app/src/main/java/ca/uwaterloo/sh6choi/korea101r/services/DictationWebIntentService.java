package ca.uwaterloo.sh6choi.korea101r.services;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ca.uwaterloo.sh6choi.korea101r.model.DictationSet;

/**
 * Created by Samson on 2015-09-24.
 */
public class DictationWebIntentService extends WebIntentService {

    private static final String TAG = DictationWebIntentService.class.getCanonicalName();
    public static final String ACTION_SUCCESS = TAG + ".action.success";

    public DictationWebIntentService() {
        super("DictationWebIntentService");
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL("https://raw.githubusercontent.com/shlchoi/hangul/master/dictation.json");
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "Dictation set retrieved");
        JsonArray array = new JsonParser().parse(response).getAsJsonArray();
        DictationSet[] dictationSets = new Gson().fromJson(array, DictationSet[].class);

        sendBroadcast(new Intent(ACTION_SUCCESS));
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, e.getMessage());
    }
}
