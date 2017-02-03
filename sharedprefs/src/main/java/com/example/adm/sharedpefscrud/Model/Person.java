package com.example.adm.sharedpefscrud.Model;

import java.io.Serializable;

/**
 * Created by adm on 26.12.2016.
 */

public class Person implements Serializable {
    private int id;
    private String name;
    private String surname;
    private String number;
    private String mail;
    private String skype;

    public static Person selectedPerson;

    public Person(int id, String name, String surname, String number, String mail, String skype) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.mail = mail;
        this.skype = skype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getId() != person.getId()) return false;
        if (!getName().equals(person.getName())) return false;
        if (!getSurname().equals(person.getSurname())) return false;
        if (!getNumber().equals(person.getNumber())) return false;
        if (getMail() != null ? !getMail().equals(person.getMail()) : person.getMail() != null)
            return false;
        return getSkype() != null ? getSkype().equals(person.getSkype()) : person.getSkype() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getNumber().hashCode();
        result = 31 * result + (getMail() != null ? getMail().hashCode() : 0);
        result = 31 * result + (getSkype() != null ? getSkype().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", number='" + number + '\'' +
                ", mail='" + mail + '\'' +
                ", skype='" + skype + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getSurname() {

        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
