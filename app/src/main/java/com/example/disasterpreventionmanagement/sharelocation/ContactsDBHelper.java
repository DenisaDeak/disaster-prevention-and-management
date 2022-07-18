package com.example.disasterpreventionmanagement.sharelocation;
import com.example.disasterpreventionmanagement.sharelocation.ContactModel;
import  com.example.disasterpreventionmanagement.sharelocation.ContractContacts.Contacts;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class ContactsDBHelper extends SQLiteOpenHelper {

    private Context appContext;

    public ContactsDBHelper(Context context) {
        super(context,"contacts.db", null , 1);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStatement = "CREATE TABLE " + Contacts.CONTACTS_TABLE + " (" + Contacts.COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Contacts.COLUMN_CONTACT_NAME + " TEXT, " + Contacts.COLUMN_CONTACT_PHONE
                + " TEXT, " + Contacts.COLUMN_USER_EMAIL + " TEXT)";
        sqLiteDatabase.execSQL(createStatement);


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(ContactModel contactModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Contacts.COLUMN_CONTACT_NAME, contactModel.getName());
        cv.put(Contacts.COLUMN_CONTACT_PHONE, contactModel.getPhone());
        cv.put(Contacts.COLUMN_USER_EMAIL, contactModel.getUser_email());
        long insert = db.insert(Contacts.CONTACTS_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
             }
        }


        public boolean delete(ContactModel contactModel) //delete contact from emergency contact list
        {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryStr = "DELETE FROM " + Contacts.CONTACTS_TABLE + " WHERE " + Contacts.COLUMN_ID + " = " + contactModel.getId();
        Cursor cursor = db.rawQuery(queryStr, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }

        }


        public String getPhoneNrs() //function to return phone numbers for sending location
        {
            String queryStr = "SELECT * FROM " + Contacts.CONTACTS_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryStr, null); //cursor is the result set from sql statement
            SharedPreferences sp = appContext.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE);
            String sp_user;
            String allPhoneNr= "";
            sp_user = sp.getString("UserEmail", "");
            if(cursor.moveToFirst() ){
                //go through result set, create string containing all phone numbers in contact list separated by ;
                do {

                    String contactPhone = cursor.getString(2);
                    if(cursor.getString(3).equals(sp_user))
                        allPhoneNr = allPhoneNr + contactPhone + ";";

                }while (cursor.moveToNext());
            }
            else{
                //if there are no results from the db, nothing will be added
            }
            cursor.close();
            db.close();
            return allPhoneNr;

        }




    public List<ContactModel> selectAll(){

        List<ContactModel> returnList = new ArrayList<>();
        //get data from db
        String queryStr = "SELECT * FROM " + Contacts.CONTACTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, null); //cursor is the result set from sql statement
        SharedPreferences sp = appContext.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE);
        String sp_user;
        sp_user = sp.getString("UserEmail", "");
        if(cursor.moveToFirst() ){
            //go through result set, create new contact obj, put them in return list
            do {
                int contactID = cursor.getInt(0);
                String contactName = cursor.getString(1);
                String contactPhone = cursor.getString(2);
                String userEmail = cursor.getString(3);
                ContactModel newContact = new ContactModel(contactID, contactName, contactPhone, userEmail);
                  if(cursor.getString(3).equals(sp_user))
                        returnList.add(newContact);

            }while (cursor.moveToNext());
        }
        else{
        //if there are no results from the db, nothing will be added to the list
        }
        cursor.close();
        db.close();
        return returnList;
    }
}

