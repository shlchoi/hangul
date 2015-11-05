package ca.uwaterloo.sh6choi.korea101r.fragments.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

import ca.uwaterloo.sh6choi.korea101r.R;
import ca.uwaterloo.sh6choi.korea101r.activities.MainActivity;
import ca.uwaterloo.sh6choi.korea101r.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.korea101r.utils.NumberUtils;

/**
 * Created by Samson on 2015-11-02.
 */
public abstract class NumbersFragment extends Fragment implements DrawerFragment, View.OnClickListener {

    protected int mCurNumber = -1;

    protected TextView mNumberTextView;
    protected EditText mInputEditText;
    protected Button mCheckButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_numbers, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNumberTextView = (TextView) view.findViewById(R.id.number_text_view);
        mNumberTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mInputEditText = (EditText) view.findViewById(R.id.input_edit_text);
        mInputEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mCheckButton = (Button) view.findViewById(R.id.check_button);
        mCheckButton.setOnClickListener(this);

        switchNumber();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.check_button:
                String answer = getAnswer(mCurNumber);

                if (TextUtils.equals(mInputEditText.getText(), answer)) {
                    switchNumber();
                } else {
                    mInputEditText.setError("Incorrect");
                }
                break;
        }
    }

    protected abstract int getMaxInt();

    protected abstract String getAnswer(int number);

    private void switchNumber() {
        Random random = new Random(new Date().getTime());

        int nextInt;
        do {
            nextInt = random.nextInt(getMaxInt()) + 1;
        } while (nextInt == mCurNumber);

        mCurNumber = nextInt;

        mNumberTextView.setText(Integer.toString(mCurNumber));

        mInputEditText.setText("");
        mInputEditText.setError(null);
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_NUMBERS_TIME);

        startActivity(intent);
        return true;
    }
}
