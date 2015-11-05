package ca.uwaterloo.sh6choi.korea101r.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.model.VocabSet;
import ca.uwaterloo.sh6choi.korea101r.model.VocabWord;


/**
 * Created by Samson on 2015-09-25.
 */
public class VocabAdapter extends RecyclerView.Adapter<ViewPagerListItemViewHolder> implements ViewPagerListItemViewHolder.OnItemClickListener {
    private VocabSet mVocabSet;

    private OnItemClickListener mOnItemClickListener;

    public VocabAdapter(int lessonId) {
        mVocabSet = new VocabSet(lessonId, new VocabWord[0]);
    }

    @Override
    public ViewPagerListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view_pager_list, parent, false);
        ViewPagerListItemViewHolder viewHolder = new ViewPagerListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewPagerListItemViewHolder holder, int position) {
        holder.getTextView().setText(mVocabSet.getWords()[position].getHangul());

        holder.itemView.setTag(mVocabSet.getWords()[position]);
        holder.setOnItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mVocabSet.getWords().length;
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

    public void setVocabSet(VocabSet vocabSet) {
        mVocabSet = vocabSet;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}
