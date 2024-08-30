package com.ohgiraffers;

import com.ohgiraffers.login.Login;
import com.ohgiraffers.login.ManagerLogin;
import com.ohgiraffers.login.SignUp;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner scr = new Scanner(System.in);
        Login login = new Login();
        ManagerLogin managerLogin = new ManagerLogin();
        SignUp signUp = new SignUp();

        while(true){
            System.out.println("=== 도서관리 시스템 ===");
            System.out.println("1.일반회원 로그인");
            System.out.println("2.관리자 로그인");
            System.out.println("3.회원가입");
            int choice = scr.nextInt();
            scr.nextLine();

            switch (choice) {
                case 1:
                    login.login();
                    break;
                case 2:
                    managerLogin.managerLogin();
                    break;
                case 3:
                    signUp.signUp();
                    break;
                default:
                    System.out.println("잘못된 번호를 입력하셨습니다.");
            }
        }


    }
}
