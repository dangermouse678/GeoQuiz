package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

   // Constants
   private static final String TAG             = "QuizActivity";
   private static final String KEY_INDEX       = "index";
   private static final String KEY_CHEAT       = "cheated";
   private static final int REQUEST_CODE_CHEAT = 0;

   /**********************/
   // INSTANCE VARIABLES //
   /**********************/

   private Button mTrueButton;
   private Button mFalseButton;
   private Button mCheatButton;
   private ImageButton mNextButton;
   private ImageButton mPrevButton;
   private TextView mQuestionTextView;
   private int mCurrentIndex = 0;
   private boolean mIsCheater;

   // Question Bank
   private Question[] mQuestionBank = new Question[] {
         new Question(R.string.question_oceans, true),
         new Question(R.string.question_mideast, false),
         new Question(R.string.question_africa, false),
         new Question(R.string.question_americas, true),
         new Question(R.string.question_asia, true)
   };

   /***********************/
   // LOCAL CLASS METHODS //
   /***********************/

   // Update the currently displayed question
   private void updateQuestion() {
      int question = mQuestionBank[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);
   }

   // Increment the currently displayed question
   private void nextQuestion() {
      mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
      mIsCheater = false;
      updateQuestion();
   }

   // Decrement the currently displayed question
   private void prevQuestion() {
      if (mCurrentIndex > 0) {
         mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
      } else {
         mCurrentIndex = mQuestionBank.length - 1;
      }

      mIsCheater = false;
      updateQuestion();
   }

   // Check the answer (button press vs. correct answer)
   private void checkAnswer(boolean userPressedTrue) {

      boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
      int messageResId;

      if (mIsCheater) {
         messageResId = R.string.judgment_toast;
      } else {
         if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
         } else {
            messageResId = R.string.incorrect_toast;
         }
      }

      Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

   }

   // Handle returned result from CheatActivity
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      if (resultCode != Activity.RESULT_OK) {
         return;
      }

      if (requestCode == REQUEST_CODE_CHEAT) {
         if (data == null) {
            return;
         }
         mIsCheater = CheatActivity.wasAnswerShown(data);
      }

   }

   /******************************/
   // ACTIVITY LIFECYCLE METHODS //
   /******************************/

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_quiz);

      Log.d(TAG, "onCreate() called");

      // Add Listener for True button
      mTrueButton = (Button) findViewById(R.id.true_button);
      mTrueButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            checkAnswer(true);
         }
      });

      // Add Listener for False button
      mFalseButton = (Button) findViewById(R.id.false_button);
      mFalseButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            checkAnswer(false);
         }
      });

      // Add listener for Cheat button
      mCheatButton = (Button) findViewById(R.id.cheat_button);
      mCheatButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Start CheatActivity
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
            Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
            startActivityForResult(i, REQUEST_CODE_CHEAT);
         }
      });

      // Add listener for Prev button
      mPrevButton = (ImageButton) findViewById(R.id.prev_button);
      mPrevButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            prevQuestion();
         }
      });

      // Add listener for Next button
      mNextButton = (ImageButton) findViewById(R.id.next_button);
      mNextButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            nextQuestion();
         }
      });

      // Add listener for if user taps on question to move to the next question
      mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
      mQuestionTextView.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v) {
            nextQuestion();
         }
      });

      // Restore any saved data (due to runtime configuration change)
      if (savedInstanceState != null) {
         mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
         mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT, false);
      }

      // Initialize Question
      updateQuestion();

   }

   @Override
   protected void onSaveInstanceState(Bundle savedInstanceState) {
      super.onSaveInstanceState(savedInstanceState);
      Log.i(TAG, "onSaveInstanceState");
      savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
      savedInstanceState.putBoolean(KEY_CHEAT, mIsCheater);
   }

   @Override
   protected void onStart() {
      super.onStart();
      Log.d(TAG, "onStart() called");
   }

   @Override
   protected void onResume() {
      super.onResume();
      Log.d(TAG, "onResume() called");
   }

   @Override
   protected void onPause() {
      super.onPause();
      Log.d(TAG, "onPause() called");
   }

   @Override
   protected void onStop() {
      super.onStop();
      Log.d(TAG, "onStop() called");
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      Log.d(TAG, "onDestroy() called");
   }

}
