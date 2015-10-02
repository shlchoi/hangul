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
public class HangulAdapter extends RecyclerView.Adapter<HangulCharacterViewHolder> implements HangulCharacterViewHolder.OnItemClickListener {
    private String[] mHangulCharacterSet;

    private OnItemClickListener mOnItemClickListener;

    public HangulAdapter(String[] hangulCharacterSet) {
        mHangulCharacterSet = hangulCharacterSet;
    }

    @Override
    public HangulCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hangul, parent, false);
        HangulCharacterViewHolder viewHolder = new HangulCharacterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HangulCharacterViewHolder holder, int position) {
        holder.getTextView().setText(mHangulCharacterSet[position]);

        holder.itemView.setTag(position);
        holder.setOnItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mHangulCharacterSet.length;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}
