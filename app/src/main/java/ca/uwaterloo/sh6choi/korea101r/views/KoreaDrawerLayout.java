package ca.uwaterloo.sh6choi.korea101r.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;

/**
 * Created by Samson on 2015-09-22.
 */
public class KoreaDrawerLayout extends NavigationDrawerLayout {

    public KoreaDrawerLayout(Context context) {
        super(context);
    }

    public KoreaDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KoreaDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void setupClickListener() {
        mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IDrawerMenuItem itemByPosition = mMenuAdapter.getItem(position);
                startNavigation(itemByPosition);
            }
        });
    }

    @Override
    protected void startNavigation(IDrawerMenuItem navigateToItem) {
        if (navigateToItem == null || !(navigateToItem instanceof KoreaMenuItem)) {
            return;
        }

        Intent intent;
        switch ((KoreaMenuItem) navigateToItem) {
            case HANGUL:
            default:
                intent = new Intent(getContext(), MainActivity.class);
                intent.setAction(MainActivity.ACTION_HANGUL);
                break;
            case DICTATION:
                intent = new Intent(getContext(), MainActivity.class);
                intent.setAction(MainActivity.ACTION_DICTATION);
                break;
            case PRONUNCIATION:
                intent = new Intent(getContext(), MainActivity.class);
                intent.setAction(MainActivity.ACTION_PRONUNCIATION);
                break;
        }
        getContext().startActivity(intent);
    }
}
