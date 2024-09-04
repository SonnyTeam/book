package com.ohgiraffers.user.dto;

public class UserDTO {
    private String name;
    private String phone;
    private String user_id;
    private String user_pwd;
    private int user_code;

    public UserDTO() {
    }

    public UserDTO(String name, String phone, String user_id, String user_pwd) {
        this.name = name;
        this.phone = phone;
        this.user_id = user_id;
        this.user_pwd = user_pwd;
    }

    public int getUser_code() {
        return user_code;
    }

    public void setUser_code(int user_code) {
        this.user_code = user_code;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                '}';
    }
}
