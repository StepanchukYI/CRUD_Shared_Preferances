package com.example.sqllitecrud.Model;

import java.io.Serializable;

/**
 * Created by adm on 06.01.2017.
 */

public class Person implements Serializable {

    private int id;
    private String Name;
    private String sName;
    private String mail;
    private String phone;
    private String skype;

    public static Person selectedPerson;

    public Person() { super();}

    public Person(String Name, String sName, String mail, String phone, String skype) {
        this.Name = Name;
        this.sName = sName;
        this.mail = mail;
        this.phone = phone;
        this.skype = skype;
    }

    public Person(int id, String Name, String sName, String mail, String phone, String skype) {
        this.id = id;
        this.Name = Name;
        this.sName = sName;
        this.mail = mail;
        this.phone = phone;
        this.skype = skype;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getsName() {
        return sName;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getSkype() {
        return skype;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (Name != null ? !Name.equals(person.Name) : person.Name != null) return false;
        if (sName != null ? !sName.equals(person.sName) : person.sName != null) return false;
        if (mail != null ? !mail.equals(person.mail) : person.mail != null) return false;
        if (phone != null ? !phone.equals(person.phone) : person.phone != null) return false;
        return skype != null ? skype.equals(person.skype) : person.skype == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        result = 31 * result + (sName != null ? sName.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (skype != null ? skype.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", mName='" + Name + '\'' +
                ", sName='" + sName + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", skype='" + skype + '\'' +
                '}';
    }
}