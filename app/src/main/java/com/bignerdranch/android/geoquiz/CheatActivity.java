package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

   // Constants
   private static final String EXTRA_ANSWER_IS_TRUE =
         "com.bignerdranch.android.geoquiz.answer_is_true";
   private static final String EXTRA_ANSWER_SHOWN =
         "com.bignerdranch.android.geoquiz.answer_shown";

   /**********************/
   // INSTANCE VARIABLES //
   /**********************/
   private boolean mAnswerIsTrue;
   private Button mShowAnswer;
   private TextView mAnswerTextView;

   /*****************/
   // LOCAL METHODS //
   /*****************/

   // Public method to create a new Intent for starting CheatActivity
   public static Intent newIntent(Context packageContext, boolean answerIsTrue) {

      Intent i = new Intent(packageContext, CheatActivity.class);
      i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
      return i;

   }

   //  Public method to decode the result Intent from CheatActivity
   public static boolean wasAnswerShown(Intent result) {
      return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
   }

   // Method to set result Intent to indicate whether or not answer was shown
   private void setAnswerShownResult(boolean isAnswerShown) {
      Intent data = new Intent();
      data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
      setResult(RESULT_OK, data);
   }

   /******************************/
   // ACTIVITY LIFECYCLE METHODS //
   /******************************/

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cheat);

      mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

      // Get references to view objects
      mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
      mShowAnswer = (Button) findViewById(R.id.showAnswerButton);

      // Show answer when button is pressed
      mShowAnswer.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            if (mAnswerIsTrue) {
               mAnswerTextView.setText(R.string.true_button);
            } else {
               mAnswerTextView.setText(R.string.false_button);
            }

            setAnswerShownResult(true);

         }

      });

   }
}
