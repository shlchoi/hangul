package ca.uwaterloo.sh6choi.korea101r.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.fragments.ConjugationFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulCharacterFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulFlashcardFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulLookupFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.PronunciationFragment;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;
import ca.uwaterloo.sh6choi.korea101r.services.HangulWebIntentService;
import ca.uwaterloo.sh6choi.korea101r.utils.KeyboardUtils;
import ca.uwaterloo.sh6choi.korea101r.fragments.DictationFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.views.DrawerMenuAdapter;
import ca.uwaterloo.sh6choi.korea101r.views.IDrawerMenuItem;
import ca.uwaterloo.sh6choi.korea101r.views.ISlidingPane;
import ca.uwaterloo.sh6choi.korea101r.views.KoreaMenuItem;
import ca.uwaterloo.sh6choi.korea101r.views.NavigationDrawerLayout;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final String TAG = MainActivity.class.getCanonicalName();

    public static final String ACTION_HANGUL = TAG + ".action.hangul";
    public static final String ACTION_HANGUL_LOOKUP = TAG + ".action.hangul.lookup";
    public static final String ACTION_HANGUL_CHARACTER = TAG + ".action.hangul.character";
    public static final String ACTION_HANGUL_FLASHCARDS = TAG + ".action.hangul.flashcards";

    public static final String ACTION_DICTATION = TAG + ".action.dictation";
    public static final String ACTION_PRONUNCIATION = TAG + ".action.pronunciation";
    public static final String ACTION_CONJUGATION = TAG + ".action.conjugation";

    public static final String ACTION_SPEECH = TAG + ".action.speech";


    private NavigationDrawerLayout mDrawerLayout;
    private DrawerLayout mWrappedDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;
    private TextView mToolbarActionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, HangulWebIntentService.class);
        startService(intent);

        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mDrawerLayout = (NavigationDrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setMenuAdapter(new DrawerMenuAdapter(this, setupMenu()));


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarActionTextView = (TextView) mToolbar.findViewById(R.id.toolbar_action_tv);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        mWrappedDrawerLayout = new ISlidingPane.DrawerLayoutWrapper(this, mDrawerLayout) {
            @Override
            public void openDrawer(int gravity) {
                KeyboardUtils.hideKeyboard(MainActivity.this);
                super.openDrawer(gravity);
            }
        };

        mDrawerToggle = new ActionBarDrawerToggle(this, mWrappedDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mDrawerLayout.setDrawerEnabled(true);

        if (getIntent() == null || TextUtils.isEmpty(getIntent().getAction())) {
            Intent start = new Intent(ACTION_HANGUL);
            startActivity(start);
        } else {
            handleAction(getIntent());
        }
    }

    private List<IDrawerMenuItem> setupMenu() {
        List<IDrawerMenuItem> menuList = new ArrayList<>();
        menuList.add(KoreaMenuItem.HANGUL);
        menuList.add(KoreaMenuItem.DICTATION);
        menuList.add(KoreaMenuItem.PRONUNCIATION);
        menuList.add(KoreaMenuItem.CONJUGATION);
        return menuList;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!TextUtils.isEmpty(intent.getAction())) {
            handleAction(intent);
            setIntent(intent);
        }
    }

    private void handleAction(Intent intent) {
        String action = intent.getAction();

        if (TextUtils.equals(action, ACTION_HANGUL)) {
            onHangul();
        } else if (TextUtils.equals(action, ACTION_HANGUL_LOOKUP)) {
            onHangulLookup();
        } else if (TextUtils.equals(action, ACTION_HANGUL_CHARACTER)) {
            onHangulCharacter(intent);
        } else if (TextUtils.equals(action, ACTION_HANGUL_FLASHCARDS)) {
            onHangulFlashcards();
        } else if (TextUtils.equals(action, ACTION_DICTATION)) {
            onDictation();
        } else if (TextUtils.equals(action, ACTION_PRONUNCIATION)) {
            onPronunciation();
        } else if (TextUtils.equals(action, ACTION_CONJUGATION)) {
            onConjugation();
        } else {
            onHangul();
        }
    }

    private void onHangul() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HangulFragment) {
            mDrawerLayout.closePane();
            return;
        }
        HangulFragment fragment = HangulFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onHangulLookup() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HangulLookupFragment) {
            mDrawerLayout.closePane();
            return;
        }
        HangulLookupFragment fragment = HangulLookupFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onHangulCharacter(Intent intent) {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HangulCharacterFragment) {
            mDrawerLayout.closePane();
            return;
        }

        HangulCharacter character = intent.getParcelableExtra(HangulLookupFragment.EXTRA_CHARACTER);

        Bundle args = new Bundle();
        args.putParcelable(HangulCharacterFragment.ARG_HANGUL_CHARACTER, character);

        HangulCharacterFragment fragment = HangulCharacterFragment.getInstance(args);
        swapFragment(fragment);
    }

    private void onHangulFlashcards() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HangulFlashcardFragment) {
            mDrawerLayout.closePane();
            return;
        }
        HangulFlashcardFragment fragment = HangulFlashcardFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onDictation() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof DictationFragment) {
            mDrawerLayout.closePane();
            return;
        }
        DictationFragment fragment = DictationFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onPronunciation() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof PronunciationFragment) {
            mDrawerLayout.closePane();
            return;
        }
        PronunciationFragment fragment = PronunciationFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onConjugation() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof ConjugationFragment) {
            mDrawerLayout.closePane();
            return;
        }
        ConjugationFragment fragment = ConjugationFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private <T extends Fragment & DrawerFragment> void swapFragment(T fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

        if (fragment.shouldAddToBackstack()) {
            transaction.addToBackStack(fragment.getFragmentTag());
        }

        transaction.replace(R.id.fragment_container, fragment, fragment.getFragmentTag());
        transaction.commit();

        updateToolbar(fragment);

        final View fragmentContainer = findViewById(R.id.fragment_container);

        fragmentContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);

        refreshToolbar(fragment.shouldShowUp(), fragment.getTitleStringResId());
    }

    private <T extends Fragment & DrawerFragment> void updateToolbar(T fragment) {
        mToolbarActionTextView.setText(fragment.getTitleStringResId());
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof DrawerFragment) {
            if (!((DrawerFragment) currentFragment).onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //switch (requestCode) {
            //case PronunciationFragment.REQUEST_SPEECH:
                if (resultCode == RESULT_OK && data != null) {
                    if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof PronunciationFragment) {
                        Intent intent = new Intent();
                        intent.setAction(ACTION_SPEECH);
                        intent.putExtra(PronunciationFragment.EXTRA_RESULTS, data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));

                        sendBroadcast(intent);
                    }
                }
                //break;
        //}

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onGlobalLayout() {
        final View fragmentContainer = findViewById(R.id.fragment_container);
        mDrawerLayout.closePane();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            fragmentContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        else
            fragmentContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void refreshToolbar(boolean shouldShowUp, int titleResId) {
        if (getSupportActionBar() == null)
            return;

        if (shouldShowUp) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
            mDrawerLayout.setDrawerEnabled(false);
            setTitle(titleResId);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_SHOW_HOME);
            mDrawerLayout.setDrawerEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(this, mWrappedDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDrawerLayout.addDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();

            mDrawerToggle.setToolbarNavigationClickListener(null);
        }
    }
}
