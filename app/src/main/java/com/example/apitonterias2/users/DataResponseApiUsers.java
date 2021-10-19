package com.example.apitonterias2.users;

import java.util.List;

public class DataResponseApiUsers {

    //@SerializedName("name")
    //String name;
    //@SerializedName("pass")
    //String pass;

    public List<String> user;

    public DataResponseApiUsers(List<String> user){//, String pass) {
        this.user = user;
        //this.pass = pass;
    }
}
