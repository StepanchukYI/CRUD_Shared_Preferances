package com.example.sqllitecrud.UI.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sqllitecrud.R;
import com.example.sqllitecrud.UI.Adapter.FilterAdapter;
import com.example.sqllitecrud.Database.CRUDSQLite;
import com.example.sqllitecrud.Model.Person;

import java.util.ArrayList;

/**
 * Created by adm on 06.01.2017.
 */

public class SecondActivity extends AppCompatActivity {
    private ListView lvPersons;
    private ArrayList<Person> personList;
    private FilterAdapter adapter;
    private CRUDSQLite crudsqLite;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_get_person){
            adapter.updateListPersons();
            openPersonDetailsDialog();
        }
        else if (id == R.id.action_delete_persons){
            adapter.dropListPersons();
        }

        return true;
    }

    private void openPersonDetailsDialog(){
        final EditText etPersonId = new EditText(this);
        etPersonId.setInputType(InputType.TYPE_CLASS_NUMBER);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find Contact");
        builder.setMessage("Enter contact id:");
        builder.setView(etPersonId);

        builder.setPositiveButton("Find", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = Integer.parseInt(etPersonId.getText().toString());

                personList = new ArrayList<Person>();
                crudsqLite = new CRUDSQLite(SecondActivity.this);
                personList = crudsqLite.getPerson(id);

                adapter = new FilterAdapter(SecondActivity.this, personList);
                lvPersons.setAdapter(adapter);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lvPersons = (ListView)findViewById(R.id.list_view_person);
        EditText etSearch = (EditText) findViewById(R.id.et_second_search);

        personList = new ArrayList<Person>();
        crudsqLite = new CRUDSQLite(this);
        personList = crudsqLite.getAllPersons();

        adapter = new FilterAdapter(this, personList);
        lvPersons.setAdapter(adapter);
        registerForContextMenu(lvPersons);
        lvPersons.setTextFilterEnabled(true);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private Person getDataFromIntent(){
        Intent getMyIntent = getIntent();
        Person person;

        int extrasId = getMyIntent.getIntExtra("id", 0);
        String extrasName = getMyIntent.getStringExtra("name");
        String extrasSurname = getMyIntent.getStringExtra("surname");
        String extrasPhone = getMyIntent.getStringExtra("phone");
        String extrasMail = getMyIntent.getStringExtra("mail");
        String extrasSkype = getMyIntent.getStringExtra("skype");

        person = new Person(
                extrasId,
                extrasName,
                extrasSurname,
                extrasPhone,
                extrasMail,
                extrasSkype
        );

        return person;
    }
}
