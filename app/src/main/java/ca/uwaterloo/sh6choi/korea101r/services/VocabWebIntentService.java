package ca.uwaterloo.sh6choi.korea101r.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.net.MalformedURLException;
import java.net.URL;

import ca.uwaterloo.sh6choi.korea101r.database.VocabDataSource;
import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;

/**
 * Created by Samson on 2015-09-24.
 */
public class VocabWebIntentService extends WebIntentService {

    private static final String TAG = VocabWebIntentService.class.getCanonicalName();
    public static final String ACTION_SUCCESS = TAG + ".action.success";

    public VocabWebIntentService() {
        super("VocabWebIntentService");
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL("https://raw.githubusercontent.com/shlchoi/hangul/master/vocabulary.json");
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "Vocabulary set retrieved");
        JsonArray array = new JsonParser().parse(response).getAsJsonArray();
        VocabSet[] vocabSets = new Gson().fromJson(array, VocabSet[].class);

        final VocabDataSource dataSource = new VocabDataSource(this);
        dataSource.open();
        dataSource.clearDefinitions();

        for (VocabSet vocabSet : vocabSets) {
            dataSource.update(vocabSet, null);
        }
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, e.getMessage());
    }
}
