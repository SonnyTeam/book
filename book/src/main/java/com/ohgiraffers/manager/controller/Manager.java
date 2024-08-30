package com.ohgiraffers.manager.controller;

import java.util.Scanner;

public class Manager {

    public void manager(){
        Scanner scr = new Scanner(System.in);
        System.out.println("메뉴");
        System.out.println("1. 도서 관리");
        System.out.println("2. 사용자 관리");
        System.out.println("3. 도서 상태 관리");
        System.out.println("4. 도서 통계");
        System.out.println("5. 연체 관리");
        int choice = scr.nextInt();

        switch(choice){
            case 1: break;
            case 2: break;
            case 3:
                book_status();
                break;
            case 4: break;
            case 5: break;
        }
    }

    public void book_status(){

        BookController bookController = new BookController();

        Scanner scr = new Scanner(System.in);
        System.out.println("1. 도서 상태 변경 및 조회");
        System.out.println("2. 도서 상태 변경 기록조회");
        int choice = scr.nextInt();

        switch (choice){
            case 1:
                bookController.updateStatus();
                break;
            case 2:
                bookController.selectStatus();
                break;

        }


    }
}
