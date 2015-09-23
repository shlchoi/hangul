package ca.uwaterloo.sh6choi.korea101r.views;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-09-22.
 */
public enum KoreaMenuItem implements IDrawerMenuItem {
    HANGUL(R.string.nav_menu_hangul),
    DICTATION(R.string.nav_menu_dictation);

    private int mStringResId;

    KoreaMenuItem(int stringResId) {
        mStringResId = stringResId;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.list_item_nav_menu;
    }

    @Override
    public int getStringResId() {
        return mStringResId;
    }

    @Override
    public int getDrawableResId() {
        return 0;
    }
}
