package com.example.disasterpreventionmanagement.sharelocation;

public class ContactModel {
    private int id;
    private String name;
    private String phone;
    protected String user_email; //associate the contacts with the user account in db

    public ContactModel() {
    }

    public ContactModel(int id, String name, String phone, String user_email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.user_email = user_email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return name +
                ", " + phone + "\n";
    }
}
