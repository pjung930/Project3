package geoquiz.android.bignerdranch.com.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Peter on 9/28/15.
 */
public class CheatActivity extends Activity{

    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private boolean mAnswerShown;

    private TextView mAnswerTextView;
    private TextView mAPITextView;
    private Button mShowAnswer;

    private static final String TAG = "CheatActivity";
    private static final String KEY_SHOWN = "shown";
    private static final String KEY_TRUE = "true";

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        mAPITextView = (TextView)findViewById(R.id.apiLevel);
        mAPITextView.setText("API level "+ String.valueOf(Build.VERSION.SDK_INT));

        setAnswerShownResult(false);
        mAnswerShown = false;

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
                mAnswerShown = true;
            }
        });

        if (savedInstanceState != null) {
            mAnswerShown = savedInstanceState.getBoolean(KEY_SHOWN);
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_TRUE);
            setAnswerShownResult(mAnswerShown);
            if (mAnswerShown && mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else if (mAnswerShown && !mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.false_button);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_SHOWN, mAnswerShown);
        savedInstanceState.putBoolean(KEY_TRUE, mAnswerIsTrue);
    }
}
