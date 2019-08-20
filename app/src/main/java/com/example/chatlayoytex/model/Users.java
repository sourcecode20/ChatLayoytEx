package com.example.chatlayoytex.model;

public class Users {
    private String name;
    private String age;
    private String mobile;
    private String address;
    private String chat;
    private String message;
    private String message_type;

    public Users() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public void getChat() {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public Users(String name, String age, String mobile, String address, String message, String message_type) {
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
        this.message = message;
        this.message_type = message_type;
    }


    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", chat='" + chat + '\'' +
                ", message='" + message + '\'' +
                ", message_type='" + message_type + '\'' +
                '}';
    }
}
