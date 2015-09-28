package ca.uwaterloo.sh6choi.korea101r.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-09-25.
 */
public class HangulAdapter extends RecyclerView.Adapter<HangulAdapter.ViewHolder> {
    private String[] mHangulCharacterSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.hangul_character_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HangulAdapter(String[] hangulCharacterSet) {
        mHangulCharacterSet = hangulCharacterSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HangulAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hangul, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mHangulCharacterSet[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mHangulCharacterSet.length;
    }
}
