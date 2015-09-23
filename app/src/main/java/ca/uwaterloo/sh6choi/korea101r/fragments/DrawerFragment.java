package ca.uwaterloo.sh6choi.korea101r.fragments;

import android.content.Context;

/**
 * Created by Samson on 2015-09-22.
 */
public interface DrawerFragment {

    String getFragmentTag();

    int getTitleStringResId();

    boolean onBackPressed();

}
