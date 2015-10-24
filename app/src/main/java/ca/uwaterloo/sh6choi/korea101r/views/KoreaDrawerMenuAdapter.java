package ca.uwaterloo.sh6choi.korea101r.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-10-02.
 */
public class KoreaDrawerMenuAdapter extends DrawerMenuAdapter {

    public KoreaDrawerMenuAdapter(Context context, List<IDrawerMenuItem> menuLabels) {
        super(context, menuLabels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if((position % 2) == 0) {
            view.setBackgroundResource(R.drawable.selector_nav_menu_light);
        } else {
            view.setBackgroundResource(R.drawable.selector_nav_menu_dark);
        }

        return view;
    }
}
