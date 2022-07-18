package com.example.disasterpreventionmanagement.kids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disasterpreventionmanagement.R;
import com.example.disasterpreventionmanagement.sharelocation.ContactsDBHelper;
import com.example.disasterpreventionmanagement.sharelocation.ShareLocationForm;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizKids extends AppCompatActivity {
    ImageView backButton;
    TextView textQuestion, textSection;
    TextView textScore, textQuestionNr, textTimer;
    RadioGroup radioGroup;
    RadioButton ans1, ans2, ans3;
    Button next, skip;

    private int count_questions;
    private int total_questions; //total nr of questions in list
    private int score ;
    private boolean isAnswered;
    private QuestionModel current_question;

    public static final String finalScore = "finalScore";
    private static final long timer_millisec = 35000; //35 seconds to answer each question
    private CountDownTimer countDownTimer;
    private long timeLeft_millisec;

    int clickedAnswer = Color.parseColor("#4D001F71");
    int unclickedAnswer = Color.parseColor("#4DFFFFFF");
    private ColorStateList textColorAnswer;



    private List<QuestionModel> quesionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids_quiz);
        backButton = findViewById(R.id.backIcon);
        textSection = findViewById(R.id.textSection);
        textQuestion = findViewById(R.id.textQuestion);
        textScore = findViewById(R.id.score_quiz);
        textTimer = findViewById(R.id.timer_quiz);
        textQuestionNr = findViewById(R.id.question_number);
        radioGroup = findViewById(R.id.radioGroup1);

        ans1 = findViewById(R.id.answer1);
        ans2 = findViewById(R.id.answer2);
        ans3 = findViewById(R.id.answer3);
        next =  findViewById(R.id.next_quiz);
        skip = findViewById(R.id.skip_quiz);

        textColorAnswer = ans1.getTextColors();

        QuizDBHelper quizDBHelper = new QuizDBHelper(QuizKids.this);

        quesionsList = quizDBHelper.viewQuestions();
        total_questions = quesionsList.size();
        Collections.shuffle(quesionsList); //to randomize the questions

        displayNextQuestion();


        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(QuizKids.this, KidsActivity.class);
            startActivity(intent);
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(false);
                skip.setEnabled(false);
                if (!isAnswered) {
                    if (ans1.isChecked() || ans2.isChecked() || ans3.isChecked()) {
                        isCorrect();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                displayNextQuestion();
                            }
                        }, 5000);   //5 seconds
                    } else {
                        Toast.makeText(QuizKids.this, "Choose an answer !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    displayNextQuestion();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                next.setEnabled(false);
                skip.setEnabled(false);
                displayCorrectAns();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        displayNextQuestion();
                    }
                }, 5000);   //5 seconds
            }
        });

        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ans1.setBackgroundColor(clickedAnswer);
            }
        });

        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ans2.setBackgroundColor(clickedAnswer);
            }
        });

        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {ans3.setBackgroundColor(clickedAnswer);
            }
        });
    }

    private void isCorrect() {

        isAnswered = true;
        countDownTimer.cancel();
        RadioButton selected_answer = findViewById(radioGroup.getCheckedRadioButtonId());
        int answer_nr = radioGroup.indexOfChild(selected_answer) + 1;

        if(answer_nr == current_question.getAnswerNr()){
            score= score + 3; //for every correct answer 3 more points are added
            textScore.setText("Score: " + score);
            Toast.makeText(QuizKids.this, "You're right!", Toast.LENGTH_SHORT).show();
        }
        else if (ans1.isChecked() || ans2.isChecked() || ans3.isChecked()) {
            if(score > 0) {
                score = score - 1; // for every wrong answer user loses 1 point
                textScore.setText("Score: " + score);
            }
            Toast.makeText(QuizKids.this, "You're wrong!", Toast.LENGTH_SHORT).show();
        }
        else
        {//
             }
        displayCorrectAns();


    }

    private void displayCorrectAns() {
        ans1.setTextColor(Color.RED);
        ans2.setTextColor(Color.RED);
        ans3.setTextColor(Color.RED);

        switch (current_question.getAnswerNr()){
            case 1:
                ans1.setTextColor(Color.GREEN);
                break;
            case 2:
                ans2.setTextColor(Color.GREEN);
                break;
            case 3:
                ans3.setTextColor(Color.GREEN);
                break;
        }

        if(count_questions < total_questions){
            //
        }else
        {
            //there are no questions left

        }


    }

    private void displayNextQuestion() {
        next.setEnabled(true);
        skip.setEnabled(true);
        ans1.setTextColor(textColorAnswer); //reset text color of answers to default color
        ans2.setTextColor(textColorAnswer);
        ans3.setTextColor(textColorAnswer);

        radioGroup.clearCheck();//clear any selection from previous question
        ans1.setBackgroundColor(unclickedAnswer);
        ans2.setBackgroundColor(unclickedAnswer);
        ans3.setBackgroundColor(unclickedAnswer);


        if(count_questions < total_questions){
            if(count_questions == total_questions-1)
                next.setText("Finish quiz");
            current_question = quesionsList.get(count_questions);
            textSection.setText(current_question.getSection());
            textQuestion.setText(current_question.getQuestion());
            ans1.setText(current_question.getAnswer1());
            ans2.setText(current_question.getAnswer2());
            ans3.setText(current_question.getAnswer3());
            Log.d("ANSWER3", current_question.getAnswer3());

            count_questions++;
            textQuestionNr.setText("Question " + count_questions + "/" + total_questions);
            isAnswered = false;

            timeLeft_millisec = timer_millisec;
            startTimer();


        }else{
            stopQuiz();
        }

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft_millisec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft_millisec = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeft_millisec = 0;
                updateTimer();
                //Toast.makeText(QuizKids.this, "You did not answer!", Toast.LENGTH_SHORT).show();
                countDownTimer.cancel();
                displayNextQuestion();

            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeft_millisec / 1000) / 60;
        int seconds = (int) (timeLeft_millisec / 1000) % 60;

        String time_format = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textTimer.setText(time_format);

    }


    private void stopQuiz() {
        Intent i_result = new Intent();
        i_result.putExtra(finalScore, score);
        setResult(RESULT_OK, i_result);
        finish();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}