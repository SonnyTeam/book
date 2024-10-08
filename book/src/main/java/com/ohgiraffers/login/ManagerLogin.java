package com.ohgiraffers.login;

import com.ohgiraffers.login.dao.LoginDAO;
import com.ohgiraffers.manager.controller.Manager;
import com.ohgiraffers.user.dto.UserDTO;

import java.util.Scanner;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.getConnection;

public class ManagerLogin {

    private LoginDAO loginDAO = new LoginDAO("src/main/resources/mapper/book-query.xml");

    public void managerLogin() {
        Scanner scr = new Scanner(System.in);
        UserDTO userDTO = new UserDTO();

        System.out.println("--------------관리자 로그인-----------------");
        System.out.println("관리자 ID 입력 : ");
        userDTO.setUser_id(scr.nextLine());
        System.out.println("관리자 Password 입력 : ");
        userDTO.setUser_pwd(scr.nextLine());
        int result = loginDAO.login(getConnection(), userDTO);

        if (result == 1) {
            System.out.println("관리자 로그인 성공");
            Manager manager = new Manager();
            manager.manager();
        } else {
            System.out.println("관리자 로그인 실패!!\nID 또는 Password가 맞지 않습니다.");
        }
    }
}
