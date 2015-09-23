package ca.uwaterloo.sh6choi.korea101r.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
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
import ca.uwaterloo.sh6choi.korea101r.Utils.KeyboardUtils;
import ca.uwaterloo.sh6choi.korea101r.fragments.DictationFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.HangulFragment;
import ca.uwaterloo.sh6choi.korea101r.views.DrawerMenuAdapter;
import ca.uwaterloo.sh6choi.korea101r.views.IDrawerMenuItem;
import ca.uwaterloo.sh6choi.korea101r.views.ISlidingPane;
import ca.uwaterloo.sh6choi.korea101r.views.KoreaMenuItem;
import ca.uwaterloo.sh6choi.korea101r.views.NavigationDrawerLayout;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final String ACTION_HANGUL = TAG + ".action.hangul";
    public static final String ACTION_DICTATION = TAG + ".action.dictation";


    private NavigationDrawerLayout mDrawerLayout;
    private DrawerLayout mWrappedDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;
    private TextView mToolbarActionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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

        mDrawerLayout.setDrawerEnabled(false);

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

    private void handleAction(Intent sentIntent) {
        String action = sentIntent.getAction();

        if (TextUtils.equals(action, ACTION_HANGUL)) {
            onHangulClicked();
        } else if (TextUtils.equals(action, ACTION_DICTATION)) {
            onDictationClicked();
        } else {
            onHangulClicked();
        }
    }

    private void onHangulClicked() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HangulFragment) {
            mDrawerLayout.closePane();
            return;
        }
        HangulFragment fragment = HangulFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private void onDictationClicked() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof DictationFragment) {
            mDrawerLayout.closePane();
            return;
        }
        DictationFragment fragment = DictationFragment.getInstance(new Bundle());
        swapFragment(fragment);
    }

    private <T extends Fragment & DrawerFragment> void swapFragment(T fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.fragment_container, fragment, fragment.getFragmentTag());
        transaction.commit();

        updateToolbar(fragment);

        final View fragmentContainer = findViewById(R.id.fragment_container);

        fragmentContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private <T extends Fragment & DrawerFragment> void updateToolbar(T fragment) {
        mToolbarActionTextView.setText(fragment.getTitleStringResId());
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

    private void refreshToolbar(boolean shouldShowUp, String title) {
        if (getSupportActionBar() == null)
            return;

        if (shouldShowUp) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
            mDrawerLayout.setDrawerEnabled(false);
            setTitle(title);

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
