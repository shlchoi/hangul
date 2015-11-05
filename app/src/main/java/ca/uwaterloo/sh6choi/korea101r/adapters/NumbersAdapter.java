package ca.uwaterloo.sh6choi.korea101r.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-11-03.
 */
public class NumbersAdapter extends RecyclerView.Adapter<ViewPagerListItemViewHolder> implements ViewPagerListItemViewHolder.OnItemClickListener {

    private List<String> mNumberList;
    private OnItemClickListener mOnItemClickListener;

    public NumbersAdapter(List<String> numberList) {
        mNumberList = numberList;
    }

    @Override
    public ViewPagerListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view_pager_list, parent, false);
        ViewPagerListItemViewHolder viewHolder = new ViewPagerListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewPagerListItemViewHolder holder, int position) {
        if (position % 2 == 1) {
            holder.getTextView().setText(mNumberList.get(position / 2));

            holder.itemView.setTag(mNumberList.get(position / 2));
            holder.setOnItemClickListener(this);
        } else {
            holder.getTextView().setText(Integer.toString((position / 2) + 1));
        }
    }

    @Override
    public int getItemCount() {
        return mNumberList.size() * 2;
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
