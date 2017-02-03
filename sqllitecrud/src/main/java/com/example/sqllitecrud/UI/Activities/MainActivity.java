package com.example.sqllitecrud.UI.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqllitecrud.R;
import com.example.sqllitecrud.Database.CRUDSQLite;
import com.example.sqllitecrud.Model.Person;

public class MainActivity extends AppCompatActivity {

    private EditText editnName;
    private EditText editsName;
    private EditText editTelephone;
    private EditText editMail;
    private EditText editSkype;
    private Button buttonAddPerson;
    private CRUDSQLite crudsqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editnName = (EditText) findViewById(R.id.edit_text_nName);
        editsName = (EditText) findViewById(R.id.edit_text_sName);
        editTelephone = (EditText) findViewById(R.id.edit_text_Telephone);
        editMail = (EditText) findViewById(R.id.edit_text_Mail);
        editSkype = (EditText) findViewById(R.id.edit_text_Skype);
        buttonAddPerson = (Button)findViewById(R.id.button_add_person);

        buttonAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crudsqLite = new CRUDSQLite(v.getContext());
                switch (v.getId()) {

                    case R.id.button_add_person: {
                        Person person = getPerson();
                        crudsqLite.addPerson(person);
                        clearText();
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.list_main_person){
            followToListActivity();
        }
        return true;
    }

    private Person getPerson() {
        return new Person(editnName.getText().toString(), editsName.getText().toString(),
                editMail.getText().toString(), editTelephone.getText().toString(), editSkype.getText().toString());
    }

    private void followToListActivity(){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    private void clearText(){
        editnName.setText("");
        editsName.setText("");
        editTelephone.setText("");
        editMail.setText("");
        editSkype.setText("");
    }
}
