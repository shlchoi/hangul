package ca.uwaterloo.sh6choi.korea101r.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-09-23.
 */
public class LaunchActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                intent.setAction(MainActivity.ACTION_HANGUL);

                startActivity(intent);
            }
        }, 1500);
    }
}
