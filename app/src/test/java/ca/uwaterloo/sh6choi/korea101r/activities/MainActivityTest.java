package ca.uwaterloo.sh6choi.korea101r.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import ca.uwaterloo.sh6choi.korea101r.BuildConfig;
import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.fragments.ConjugationFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.DictationFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.PronunciationFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulCharacterFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulFlashcardFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulFragment;
import ca.uwaterloo.sh6choi.korea101r.fragments.hangul.HangulLookupFragment;
import ca.uwaterloo.sh6choi.korea101r.views.DrawerMenuAdapter;
import ca.uwaterloo.sh6choi.korea101r.views.NavigationDrawerLayout;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Samson on 2015-09-28.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class MainActivityTest {

    @Test
    public void shouldHaveNavigationDrawer() {
        ActivityController controller = Robolectric.buildActivity(MainActivity.class);
        controller.create();
        MainActivity activity = (MainActivity) controller.get();

        NavigationDrawerLayout drawerLayout = (NavigationDrawerLayout) activity.findViewById(R.id.drawer_layout);

        assertThat(drawerLayout, notNullValue());
    }

    @Test
    public void shouldHavePopulatedMenu() {
        ActivityController controller = Robolectric.buildActivity(MainActivity.class);
        controller.create();
        MainActivity activity = (MainActivity) controller.get();

        NavigationDrawerLayout drawerLayout = (NavigationDrawerLayout) activity.findViewById(R.id.drawer_layout);
        DrawerMenuAdapter adapter = drawerLayout.getMenuAdapter();

        assertThat(adapter.getCount(), equalTo(4));

        assertThat(adapter.getItem(0).getStringResId(), equalTo(R.string.nav_menu_hangul));
        assertThat(adapter.getItem(1).getStringResId(), equalTo(R.string.nav_menu_dictation));
        assertThat(adapter.getItem(2).getStringResId(), equalTo(R.string.nav_menu_pronunciation));
        assertThat(adapter.getItem(3).getStringResId(), equalTo(R.string.nav_menu_conjugation));
    }

    @Test
    public void shouldStartOnHangulFragmentByDefault() {
        ActivityController controller = Robolectric.buildActivity(MainActivity.class);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        Intent nextActivity = Shadows.shadowOf(activity).peekNextStartedActivity();

        assertThat(nextActivity.getAction(), equalTo(MainActivity.ACTION_HANGUL));

        activity = Robolectric.buildActivity(MainActivity.class).withIntent(nextActivity).create().resume().get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(HangulFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_HANGUL));
    }

    @Test
    public void shouldContainHangulFragmentOnIntent() {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_HANGUL);

        ActivityController controller = Robolectric.buildActivity(MainActivity.class).withIntent(intent);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(HangulFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_HANGUL));
    }

    @Test
    public void shouldContainHangulLookupFragmentOnIntent() {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_HANGUL_LOOKUP);

        ActivityController controller = Robolectric.buildActivity(MainActivity.class).withIntent(intent);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(HangulLookupFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_HANGUL_LOOKUP));
    }

    @Test
    public void shouldContainHangulCharacterFragmentOnIntent() {
        String hangul = "ㄱ";
        String romanization = "g";
        String pronunciation = "그";

        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_HANGUL_CHARACTER);
        intent.putExtra(HangulLookupFragment.EXTRA_HANGUL, hangul);
        intent.putExtra(HangulLookupFragment.EXTRA_ROMANIZATION, romanization);
        intent.putExtra(HangulLookupFragment.EXTRA_PRONUNCIATION, pronunciation);

        ActivityController controller = Robolectric.buildActivity(MainActivity.class).withIntent(intent);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(HangulCharacterFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_HANGUL_CHARACTER));

        assertThat(activity.getIntent().hasExtra(HangulLookupFragment.EXTRA_HANGUL), is(true));
        assertThat(activity.getIntent().hasExtra(HangulLookupFragment.EXTRA_ROMANIZATION), is(true));
        assertThat(activity.getIntent().hasExtra(HangulLookupFragment.EXTRA_PRONUNCIATION), is(true));

        assertThat(fragment.getArguments().containsKey(HangulCharacterFragment.ARG_HANGUL), is(true));
        assertThat(fragment.getArguments().containsKey(HangulCharacterFragment.ARG_ROMANIZATION), is(true));
        assertThat(fragment.getArguments().containsKey(HangulCharacterFragment.ARG_PRONUNCIATION), is(true));
    }


    @Test
    public void shouldContainHangulFlashcardFragmentOnIntent() {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_HANGUL_FLASHCARDS);

        ActivityController controller = Robolectric.buildActivity(MainActivity.class).withIntent(intent);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(HangulFlashcardFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_HANGUL_FLASHCARDS));
    }

    @Test
     public void shouldContainDictationFragmentOnIntent() {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_DICTATION);

        ActivityController controller = Robolectric.buildActivity(MainActivity.class).withIntent(intent);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(DictationFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_DICTATION));
    }

    @Test
    public void shouldContainPronunciationFragmentOnIntent() {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_PRONUNCIATION);

        ActivityController controller = Robolectric.buildActivity(MainActivity.class).withIntent(intent);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(PronunciationFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_PRONUNCIATION));
    }

    @Test
    public void shouldContainConjucationFragmentOnIntent() {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_CONJUGATION);

        ActivityController controller = Robolectric.buildActivity(MainActivity.class).withIntent(intent);
        controller.create().resume();
        MainActivity activity = (MainActivity) controller.get();

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        assertThat(fragment, instanceOf(ConjugationFragment.class));
        assertThat(activity.getIntent().getAction(), equalTo(MainActivity.ACTION_CONJUGATION));
    }
}
