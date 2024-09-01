package com.ohgiraffers.manager.controller;

import com.ohgiraffers.manager.dao.UserDAO;
import com.ohgiraffers.manager.dto.UserDTO;

import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.getConnection;

public class UserController {
    /*
    * - **사용자 관리**
    - 도서를 대여하는 사용자(회원) 정보를 관리할 수 있는 기능을 추가한다.
    - 사용자 정보에는 이름, 연락처, 대여 중인 도서 목록 등이 포함된다.
    * */

    UserDAO userDAO = new UserDAO("src/main/resources/mapper/manager-query.xml");

    public void selectUser(){

            Scanner scr = new Scanner(System.in);
            System.out.println("검색할 이름을 입력해주세요.");
            String name = scr.nextLine();

            UserDTO user = userDAO.selectUser(getConnection(), name);

            // System.out.println(user.getBorrowedList().isEmpty());
            System.out.print("이름 : " + user.getName() + " | 연락처 : " + user.getPhone() + " | 대여 중인 도서 목록 : ");
            if(user.getBorrowedList().isEmpty()){
                System.out.println("없음");
            }else {
                for(String subject : user.getBorrowedList()){
                    System.out.print(subject + " ");
                }
            }

            System.out.println();


    }

    public void updateUser(){


        int result = 0;

        Scanner scr = new Scanner(System.in);
        System.out.println("수정할 회원의 이름을 입력해주세요.");
        String name = scr.nextLine();
        System.out.println("수정할 연락처를 입력해주세요.");
        String phone = scr.nextLine();

        result = userDAO.updateUser(getConnection(), name, phone);

        if(result == 1){
            System.out.println("정보를 수정하였습니다.");
        }else {
            System.out.println("정보 수정을 실패했습니다.");
        }

    }

    public void deleteUser(){

            Scanner scr = new Scanner(System.in);
            System.out.println("삭제할 회원의 이름을 입력해주세요.");
            String name = scr.nextLine();

            int result = userDAO.deleteUser(getConnection(), name);
            if(result == 1){
                System.out.println("삭제를 성공하였습니다.");
            }else {
                System.out.println("삭제하지 못했습니다.");
            }


    }



    public void userList(){

        List<UserDTO> users = userDAO.selectAllUser(getConnection());
        for(UserDTO user : users){
            System.out.print("이름 : " + user.getName() + " | 연락처 : " + user.getPhone() + " | 대여 중인 도서 목록 : ");
            if(user.getBorrowedList().isEmpty()){
                System.out.println("없음");
            }else {
                for(String subject : user.getBorrowedList()){
                    System.out.print(subject + " ");

                }
                System.out.println();
            }
        }


    }

}
