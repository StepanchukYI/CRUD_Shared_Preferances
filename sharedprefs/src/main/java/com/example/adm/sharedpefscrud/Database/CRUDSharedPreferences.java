package com.example.adm.sharedpefscrud.Database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.adm.sharedpefscrud.Model.Person;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adm on 26.12.2016.
 */

public class CRUDSharedPreferences {
    public static final String PREFSS_NAME = "CRUD_APP";
    public static final String PERSON_CONSTANT = "Person constant";

    public CRUDSharedPreferences() {
        super();
    }

    public void savePerson(Context context, List<Person> persons) {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        preferences = context.getSharedPreferences(PREFSS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

        Gson gson = new Gson();
        String jsonPerson = gson.toJson(persons);

        editor.putString(PERSON_CONSTANT, jsonPerson);

        editor.commit();
    }

    public void addPerson(Context context, Person person){
        List<Person> personList = getPersons(context);

        if (personList == null){
            personList = new ArrayList<Person>();
        }
        personList.add(person);

        savePerson(context, personList);
    }

    public void updatePerson(Context context, Person person){
        List<Person> personList = getPersons(context);

        if (personList == null){
            personList = new ArrayList<Person>();
            personList.add(person);
        }
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId() == person.getId()){
                personList.get(i).setName(person.getName());
                personList.get(i).setSurname(person.getSurname());
                personList.get(i).setNumber(person.getNumber());
                personList.get(i).setMail(person.getMail());
                personList.get(i).setSkype(person.getSkype());
            }
        }

        savePerson(context, personList);
    }

    public void removePerson(Context context, Person person){
        ArrayList<Person> personList = getPersons(context);

        if (personList != null){
            personList.remove(person);
            savePerson(context, personList);
        }
    }

    public void deleteAllPersons(Context context){
        ArrayList<Person> personList = getPersons(context);

        for(int i = 0; i < personList.size(); i++){
            removePerson(context, personList.get(i));
        }
    }

    public ArrayList<Person> getPersons(Context context){
        SharedPreferences preferences;
        List<Person> personList;

        preferences = context.getSharedPreferences(PREFSS_NAME, Context.MODE_PRIVATE);

        if (preferences.contains(PERSON_CONSTANT)){
            String jsonPerson = preferences.getString(PERSON_CONSTANT, null);
            Gson gson = new Gson();
            Person[] personsItems = gson.fromJson(jsonPerson, Person[].class);

            personList = Arrays.asList(personsItems);
            personList = new ArrayList<Person>(personList);
        }
        else{
            return null;
        }

        return (ArrayList<Person>) personList;
    }

    public Person getPerson(Context context, int id){
        SharedPreferences preferences;
        List<Person> personList;

        preferences = context.getSharedPreferences(PREFSS_NAME, Context.MODE_PRIVATE);

        if (preferences.contains(PERSON_CONSTANT)){
            String jsonPerson = preferences.getString(PERSON_CONSTANT, null);
            Gson gson = new Gson();
            Person[] personsItems = gson.fromJson(jsonPerson, Person[].class);

            personList = Arrays.asList(personsItems);
            personList = new ArrayList<Person>(personList);
        }
        else{
            return null;
        }

        Person person = null;
        for (int i = 0; i < personList.size(); i++){
            if (personList.get(i).getId() == id){
                person = personList.get(i);
                break;
            }
        }

        return person;
    }
}
