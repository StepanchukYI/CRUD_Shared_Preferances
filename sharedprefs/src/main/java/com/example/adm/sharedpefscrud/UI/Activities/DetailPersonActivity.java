package com.example.adm.sharedpefscrud.UI.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.adm.sharedpefscrud.Model.Person;
import com.example.adm.sharedpefscrud.R;

public class DetailPersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_person);

        TextView tvName = (TextView)findViewById(R.id.tv_details_name);
        TextView tvSurname = (TextView)findViewById(R.id.tv_details_surname);
        TextView tvPhone = (TextView)findViewById(R.id.tv_details_number);
        TextView tvMail = (TextView)findViewById(R.id.tv_details_mail);
        TextView tvSkype = (TextView)findViewById(R.id.tv_details_skype);

        if (Person.selectedPerson != null){
            Person person = Person.selectedPerson;
            tvName.setText(person.getName());
            tvSurname.setText(person.getSurname());
            tvPhone.setText(person.getNumber());
            tvMail.setText(person.getMail());
            tvSkype.setText(person.getSkype());
        }
    }
}
