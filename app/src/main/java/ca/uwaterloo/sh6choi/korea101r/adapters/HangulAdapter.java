package ca.uwaterloo.sh6choi.korea101r.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.model.HangulCharacter;

/**
 * Created by Samson on 2015-09-25.
 */
public class HangulAdapter extends RecyclerView.Adapter<HangulCharacterViewHolder> implements HangulCharacterViewHolder.OnItemClickListener {
    private List<HangulCharacter> mHangulCharacterList;

    private OnItemClickListener mOnItemClickListener;

    public HangulAdapter() {
        mHangulCharacterList = new ArrayList<>();
    }

    @Override
    public HangulCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hangul, parent, false);
        HangulCharacterViewHolder viewHolder = new HangulCharacterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HangulCharacterViewHolder holder, int position) {
        holder.getTextView().setText(mHangulCharacterList.get(position).getCharacter());

        holder.itemView.setTag(mHangulCharacterList.get(position));
        holder.setOnItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mHangulCharacterList.size();
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

    public void setHangulCharacterList(List<HangulCharacter> hangulCharacterList) {
        mHangulCharacterList = hangulCharacterList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}
