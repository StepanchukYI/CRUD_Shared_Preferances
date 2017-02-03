package com.example.sqllitecrud.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqllitecrud.Config;
import com.example.sqllitecrud.Model.Person;

import java.util.ArrayList;

/**
 * Created by adm on 06.01.2017.
 */

public class CRUDSQLite {
    public static final String PREFS_NAME = "CRUD_APP";
    public static final String PERSON_CONSTANT = "Person Constant";

    private SQLiteDBHelper sqLiteDBHelper;

    public CRUDSQLite(Context context){

        this.sqLiteDBHelper = new SQLiteDBHelper(context);
    }

    public ArrayList<Person> getAllPersons(){
        ArrayList<Person> persons = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDBHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Config.COMMAND_SELECT, null);
        Person person = null;
        if (cursor.moveToFirst()){
            do{
                person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setsName(cursor.getString(2));
                person.setPhone(cursor.getString(3));
                person.setMail(cursor.getString(4));
                person.setSkype(cursor.getString(5));
                persons.add(person);
            }while (cursor.moveToNext());
        }
        return  persons;
    }

    public void addPerson(Person person){

        SQLiteDatabase db = sqLiteDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.KEY_NAME, person.getName());
        values.put(Config.KEY_SURNAME, person.getsName());
        values.put(Config.KEY_PHONE, person.getPhone());
        values.put(Config.KEY_MAIL, person.getMail());
        values.put(Config.KEY_SKYPE, person.getSkype());
        db.insert(Config.TABLE_PERSON, null, values);
        db.close();
    }

    public void deletePerson(int id){

        SQLiteDatabase sqLiteDatabase = sqLiteDBHelper.getWritableDatabase();
        sqLiteDatabase.delete(Config.TABLE_PERSON, Config.KEY_ID + " = " + id, null);
        sqLiteDBHelper.close();
    }

    public void deleteAllPersons(){
        SQLiteDatabase db = sqLiteDBHelper.getWritableDatabase();
        db.delete(Config.TABLE_PERSON, null, null);
        db.close();
    }

    public void updatePerson(Person person){
        updatePerson(person.getId(), person.getName(), person.getsName(), person.getPhone(), person.getMail(), person.getSkype());
    }

    public void updatePerson(int id, String fName, String lName,
                             String phone, String mail, String skype){

        SQLiteDatabase sqLiteDatabase = sqLiteDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", fName);
        values.put("Surname", lName);
        values.put("Phone", phone);
        values.put("Mail", mail);
        values.put("Skype", skype);
        sqLiteDatabase.update(Config.TABLE_PERSON, values, Config.KEY_ID + " = " + id, null);
        sqLiteDatabase.close();
    }

    public ArrayList<Person> getPerson(int id){

        ArrayList<Person> persons = new ArrayList<Person>();
        SQLiteDatabase sqLiteDatabase = sqLiteDBHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(Config.TABLE_PERSON,
                null,
                Config.KEY_ID + " = " + id,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        Person person  = new Person();
        person.setId(Integer.parseInt(cursor.getString(0)));
        person.setName(cursor.getString(1));
        person.setsName(cursor.getString(2));
        person.setPhone(cursor.getString(3));
        person.setMail(cursor.getString(4));
        person.setSkype(cursor.getString(5));
        persons.add(person);
        sqLiteDBHelper.close();
        return persons;
    }
}
