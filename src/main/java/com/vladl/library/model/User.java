package com.vladl.library.model;

public class User {

    private static int counter = 1;

    private int id;
    private String name;
    private UserStatus status;

    public User(){
        //only for Jackson
    }

    public User(String name){
        this.id = counter++;
        this.name = name;
        this.status = UserStatus.ACTIVE;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public UserStatus getStatus(){
        return status;
    }

    public static void setCounter(int value){
        counter = value;
    }

    public void changeUserStatus(int userId, UserStatus status){
        if(status == getStatus()){
            throw new IllegalStateException("Cannot change user status, it is already in this status");
        }

        this.status = status;
    }

    @Override
    public String toString(){
        return String.format(
                "User{id:%d, name:'%s', status:'%s'}",
                id,
                name,
                status
        );
    }

    public static void resetCounter(){
        counter = 1;
    }
}
