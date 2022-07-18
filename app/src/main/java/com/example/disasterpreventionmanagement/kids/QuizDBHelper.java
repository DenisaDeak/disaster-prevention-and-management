package com.example.disasterpreventionmanagement.kids;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.disasterpreventionmanagement.kids.ContractQuestions.Questions;
import com.example.disasterpreventionmanagement.sharelocation.ContactModel;
import com.example.disasterpreventionmanagement.sharelocation.ContractContacts;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "KidsQuestions.db";

    private SQLiteDatabase db;
    private Context appContext;

    public QuizDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String create_table = "CREATE TABLE " +
                Questions.TABLE_NAME + " ( " +
                Questions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Questions.COLUMN_SECTION + " TEXT, " +
                Questions.COLUMN_QUESTIONS + " TEXT, " +
                Questions.COLUMN_ANSWER1 + " TEXT, " +
                Questions.COLUMN_ANSWER2 + " TEXT, " +
                Questions.COLUMN_ANSWER3 + " TEXT, " +
                Questions.COLUMN_ANSWER_NUMBER + " INTEGER" +
                ")";

        db.execSQL(create_table);
        //Floods questions
        QuestionModel q1 = new QuestionModel("Floods","What to do when water is flooding your house?", "A. Run outside." , "B. Go upstairs or on higher grounds.", "C. Nothing.", 2 );
        addQuestion(q1);
        QuestionModel q2 = new QuestionModel("Floods","What should you do when there is a flooded area in front of you?", "A. Turn around." , "B. Try to cross it if it doesn't seem too deep.", "C. Cross it while holding to nearly-located objects.", 1 );
        addQuestion(q2);
        QuestionModel q3 = new QuestionModel("Floods","What is a flood?", "A. A lot of rain." , "B. A hurricane.", "C. A rise of water with no place to go.", 3 );
        addQuestion(q3);
        QuestionModel q4 = new QuestionModel("Floods","What factors contribute to flooding?", "A. Rainfall duration" , "B. Rainfall intensity.", "C. Both.", 3 );
        addQuestion(q4);

        //Tornadoes questions
        QuestionModel t1 = new QuestionModel("Tornadoes","If you are outside and there is no building nearby, what should you do if a tornado is coming?", "A. Climb on a higher spot." , "B. Get low and cover your head.", "C. Get in a car.", 2 );
        addQuestion(t1);
        QuestionModel t2 = new QuestionModel("Tornadoes","If you are inside a building, what should you do if a tornado is coming?", "A. To a basement or in a room with no windows." , "B. Near a window.", "C. To the highest level in the building.", 1 );
        addQuestion(t2);
        QuestionModel t3 = new QuestionModel("Tornadoes","Wherever you are, if you hear or see a tornado coming, what should you do?", "A. Run as fast as possible." , "B. Stay where you are and do nothing.", "C. Take cover.", 3 );
        addQuestion(t3);
        QuestionModel t4 = new QuestionModel("Tornadoes","What is the main cause of tornadoes?", "A. Earthquakes." , "B. Thunderstorms.", "C. Rain.", 2 );
        addQuestion(t4);

        //Earthquakes questions
        QuestionModel e1 = new QuestionModel("Earthquakes","If you are at risk from earthquakes, what can you do to prepare for an earthquake?", "A.  Choose a safe place in every room in your home, such as under a sturdy table or desk." , "B. Develop a Family Disaster Plan.", "C. Both.", 3 );
        addQuestion(e1);
        QuestionModel e2 = new QuestionModel("Earthquakes","What should you do when there is an earthquake and you are outside?", "A. Move to an open area." , "B. Run to the nearest building.", "C. Grab the nearest tree or sturdy object.", 1 );
        addQuestion(e2);

        //Thunderstorms questions
        QuestionModel ts1 = new QuestionModel("Thunderstorms","If you cannot get inside a building during a thunderstorm, what should you do?", "A. Stand under a tree." , "B.Stand in an open area away from trees.", "C. Run toward a substantial building or vehicle with a metal top and sides.", 3 );
        addQuestion(ts1);
        QuestionModel ts2 = new QuestionModel("Thunderstorms","Which of the following occurs with every thunderstorm?", "A.Tornadoes.   " , "B.Lightning.   ", "C.Hail.      ", 2 );
        addQuestion(ts2);


        //Extreme heat questions
        QuestionModel eh1 = new QuestionModel("Extreme heat","What should you always wear if you are outside and the temperature is really high?", "A. A hat." , "B. A scarf.", "C. A swimsuit.", 1 );
        addQuestion(eh1);
        QuestionModel eh2 = new QuestionModel("Extreme heat","What should you do while there's a heat wave?", "A. Stay hydrated." , "B. Stay in the shade of a tree.", "C.Both.", 3 );
        addQuestion(eh2);


        //Blizzards questions
        QuestionModel b1 = new QuestionModel("Blizzards","Which part of the body loses the most heat in cold weather?", "A. Feet." , "B. Hands.", "C. Head.", 3 );
        addQuestion(b1);
        QuestionModel b2 = new QuestionModel("Blizzards","What is a blizzard?", "A. A dangerous winter storm." , "B. A tropical storm.", "C. Chilly weather.", 1 );
        addQuestion(b2);


        //Wildfires questions
        QuestionModel w1 = new QuestionModel("Wildfires","What should you do during a wildfire?", "A. Run outside." , "B. Stay inside if you are not told to evacuate.", "C. Stay in open area.", 2 );
        addQuestion(w1);
        QuestionModel w2 = new QuestionModel("Wildfires","What should you do if your clothes catch fire?", "A. Drop on the ground and roll." , "B. Try to find some water.", "C. Run fast.", 1 );
        addQuestion(w2);


        //Hurricanes questions
        QuestionModel h1 = new QuestionModel("Hurricanes","When is hurricane season?", "A. Mid January through mid May." , "B.  Early June through mid August.", "C.  Mid August through late October.", 3 );
        addQuestion(h1);
        QuestionModel h2 = new QuestionModel("Hurricanes","What is a hurricane?", "A. An intense tropical storm." , "B. A winter storm.", "C. Neither.", 1 );
        addQuestion(h2);



    }

    protected void addQuestion(QuestionModel questionModel) {
        ContentValues cv = new ContentValues();
        cv.put(ContractQuestions.Questions.COLUMN_SECTION, questionModel.getSection());
        cv.put(ContractQuestions.Questions.COLUMN_QUESTIONS, questionModel.getQuestion());
        cv.put(ContractQuestions.Questions.COLUMN_ANSWER1, questionModel.getAnswer1());
        cv.put(ContractQuestions.Questions.COLUMN_ANSWER2, questionModel.getAnswer2());
        cv.put(ContractQuestions.Questions.COLUMN_ANSWER3, questionModel.getAnswer3());
        cv.put(ContractQuestions.Questions.COLUMN_ANSWER_NUMBER, questionModel.getAnswerNr());
        db.insert(ContractQuestions.Questions.TABLE_NAME, null, cv);


    }


    public List<QuestionModel> viewQuestions(){


        List<QuestionModel> questionList = new ArrayList<>();
        //get data from db
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Questions.TABLE_NAME, null);

        SharedPreferences sp_section = appContext.getSharedPreferences("SectionQuiz", Context.MODE_PRIVATE);
        String section;
        section = sp_section.getString("SectionSelected", "");


        if(cursor.moveToFirst() ){
            //go through result set, create new contact obj, put them in return list
            do {
                QuestionModel questionModel = new QuestionModel();
                questionModel.setSection(cursor.getString(1));
                questionModel.setQuestion(cursor.getString(2));
                questionModel.setAnswer1(cursor.getString(3));
                questionModel.setAnswer2(cursor.getString(4));
                questionModel.setAnswer3(cursor.getString(5));
                questionModel.setAnswerNr(cursor.getInt(6));

                if(cursor.getString(1).equals(section))
                    questionList.add(questionModel);


            }while (cursor.moveToNext());
            //contactModel.getUser_email()
        }else{}

        cursor.close();
        db.close();
        return questionList;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
