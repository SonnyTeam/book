package com.ohgiraffers;

import com.ohgiraffers.login.Login;
import com.ohgiraffers.login.ManagerLogin;
import com.ohgiraffers.login.SignUp;
import com.ohgiraffers.overdue.OverdueController;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        OverdueController overdueController = new OverdueController();
        Scanner scr = new Scanner(System.in);
        Login login = new Login();
        ManagerLogin managerLogin = new ManagerLogin();
        SignUp signUp = new SignUp();
        overdueController.overdueAutoInsert();

        while(true){
            System.out.println("=== 도서관리 시스템 ===");
            System.out.println("1.일반회원 로그인");
            System.out.println("2.관리자 로그인");
            System.out.println("3.회원가입");
            System.out.println("9.프로그램 종료");

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
                case 9:
                    System.out.println("프로그램이 종료됩니다.");
                    return;
                default:
                    System.out.println("잘못된 번호를 입력하셨습니다.");
            }
        }


    }
}
