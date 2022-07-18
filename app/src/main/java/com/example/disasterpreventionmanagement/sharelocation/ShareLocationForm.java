package com.example.disasterpreventionmanagement.sharelocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.disasterpreventionmanagement.MainActivity;
import com.example.disasterpreventionmanagement.R;
import com.google.android.material.textfield.TextInputEditText;


public class ShareLocationForm extends AppCompatActivity {
    private static final String FILE = "file.txt";
    ImageView backButton;
    TextInputEditText contactNameEditText;//contact name
    TextInputEditText contactPhoneEditText;//phone number
    ListView contactList;
    ArrayAdapter contactArrayAdapter;
    ContactsDBHelper contactsDbHelper;
    String useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location_form);
        contactNameEditText = findViewById(R.id.inputName);
        contactPhoneEditText = findViewById(R.id.inputTel);
        contactList = findViewById(R.id.viewcontacts);
        backButton = findViewById(R.id.backIcon);
        Button buttonSave = findViewById(R.id.saveTeltoDB);
        Button buttonSend = findViewById(R.id.saveTel);
        Button buttonView = findViewById(R.id.viewcontactsButton);
        Button emergencyCall = findViewById(R.id.emergencyCall);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            useremail = null;
        } else {
            useremail= extras.getString("user email");
        }


        this.contactsDbHelper = new ContactsDBHelper(ShareLocationForm.this);
        //ContactsListView(contactsDbHelper);
        ContactsDBHelper contactsDbHelper = new ContactsDBHelper(ShareLocationForm.this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShareLocationForm.this, MainActivity.class);
                startActivity(intent);
            }
        });
        emergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +112));
                startActivity(intent);
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsDBHelper contactsDbHelper = new ContactsDBHelper(ShareLocationForm.this);
                ContactsListView(contactsDbHelper);
                if(contactList.getVisibility()==View.VISIBLE){

                    contactList.setVisibility(View.GONE);

                }
                else
                    contactList.setVisibility(View.VISIBLE);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactModel contactModel;
                try {
                    contactModel = new ContactModel(-1, contactNameEditText.getText().toString(), contactPhoneEditText.getText().toString(), useremail);

                }
                catch (Exception e){
                    Toast.makeText(ShareLocationForm.this, "Contact could not be added", Toast.LENGTH_SHORT).show();
                    contactModel =  new ContactModel(-1, "error", "error", "error");
                }

                ContactsDBHelper contactsDbHelper = new ContactsDBHelper(ShareLocationForm.this);
               boolean success = contactsDbHelper.addOne(contactModel);
               if(success == true) {
                   contactNameEditText.getText().clear();
                   contactPhoneEditText.getText().clear();
                   Toast.makeText(ShareLocationForm.this, "Contact added to the emergency list !", Toast.LENGTH_SHORT).show();
               }
               else
                   Toast.makeText(ShareLocationForm.this, "Contact could not be added to the emergency list !", Toast.LENGTH_SHORT).show();

            }
        });


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShareLocationForm.this,ShareLocation.class);
                String allNumbers = contactsDbHelper.getPhoneNrs();
                intent.putExtra("Phone Number", allNumbers);
                startActivity(intent);

            }

        });

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactModel selectedContact = (ContactModel) adapterView.getItemAtPosition(i);
                contactsDbHelper.delete(selectedContact);
                ContactsListView(contactsDbHelper);
                Toast.makeText(ShareLocationForm.this,"Emergency contact deleted",Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void ContactsListView(ContactsDBHelper contactsDbHelper2) {
        contactArrayAdapter = new ArrayAdapter<ContactModel>(ShareLocationForm.this, R.layout.list_item, contactsDbHelper2.selectAll());
        contactList.setAdapter(contactArrayAdapter);
    }



}