package ca.uwaterloo.sh6choi.korea101r.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.uwaterloo.sh6choi.korea101r.R;

/**
 * Created by Samson on 2015-10-02.
 */
public class HangulCharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTextView;

    private OnItemClickListener mOnItemClickListener;

    public HangulCharacterViewHolder(View v) {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.hangul_character_text_view);
        v.setOnClickListener(this);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hangul_character_linear_layout) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}
