package com.example.disasterpreventionmanagement.kids;

import android.provider.BaseColumns;

public final class ContractQuestions {

    public static class Questions implements BaseColumns{ //db table for quiz questions
        public static final String TABLE_NAME = "quizQuestions";
        public static final String COLUMN_SECTION = "quiz_section";
        public static final String COLUMN_QUESTIONS = "question";
        public static final String COLUMN_ANSWER1 = "answer1";
        public static final String COLUMN_ANSWER2 = "answer2";
        public static final String COLUMN_ANSWER3 = "answer3";
        public static final String COLUMN_ANSWER_NUMBER = "answerNumber";




    }
}
