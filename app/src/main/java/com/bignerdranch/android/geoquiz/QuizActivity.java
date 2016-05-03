package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

   private Button mTrueButton;
   private Button mFalseButton;
   private ImageButton mNextButton;
   private ImageButton mPrevButton;
   private TextView mQuestionTextView;
   private int mCurrentIndex = 0;

   // Question Bank
   private Question[] mQuestionBank = new Question[] {
         new Question(R.string.question_oceans, true),
         new Question(R.string.question_mideast, false),
         new Question(R.string.question_africa, false),
         new Question(R.string.question_americas, true),
         new Question(R.string.question_asia, true)
   };

   // Update the currently displayed question
   private void updateQuestion() {
      int question = mQuestionBank[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);
   }

   // Increment the currently displayed question
   private void nextQuestion() {
      mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
      updateQuestion();
   }

   // Decrement the currently displayed question
   private void prevQuestion() {
      if (mCurrentIndex > 0) {
         mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
      } else {
         mCurrentIndex = mQuestionBank.length - 1;
      }

      updateQuestion();
   }

   // Check the answer (button press vs. correct answer)
   private void checkAnswer(boolean userPressedTrue) {

      boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
      int messageResId;

      if (userPressedTrue == answerIsTrue) {
         messageResId = R.string.correct_toast;
      } else {
         messageResId = R.string.incorrect_toast;
      }

      Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_quiz);

      mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

      mTrueButton = (Button) findViewById(R.id.true_button);
      mFalseButton = (Button) findViewById(R.id.false_button);
      mNextButton = (ImageButton) findViewById(R.id.next_button);
      mPrevButton = (ImageButton) findViewById(R.id.prev_button);

      // Add Listener for True button
      mTrueButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            checkAnswer(true);
         }
      });

      // Add Listener for False button
      mFalseButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            checkAnswer(false);
         }
      });

      // Add listener for Prev button
      mPrevButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            prevQuestion();
         }
      });

      // Add listener for Next button
      mNextButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            nextQuestion();
         }
      });

      // Add listener for if user taps on question to move to the next question
      mQuestionTextView.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v) {
            nextQuestion();
         }
      });

      // Initialize Question
      updateQuestion();

   }
}
