package com.ohgiraffers.login;

import java.util.Scanner;

public class CommonMemberUI {
    Scanner sc = new Scanner(System.in);
    CommonMemberFT ft = new CommonMemberFT();
    public void userUI(){

        while(true){
        System.out.println("메뉴");
        System.out.println("1. 대여");
        System.out.println("2. 반납");
        System.out.println("3. 도서 검색");
        int num = sc.nextInt();
        sc.nextLine();
        switch (num) {
            case 1:
                ft.rental();
                break;
            case 2:
                ft.returnBook();
                break;
            case 3:
                searchBook();
                break;
            default:
                System.out.println("잘못입력");
                break;
        }
        }
    }
    public void searchBook(){

        System.out.println("1. 제목 검색");
        System.out.println("2. 저자 검색");
        System.out.println("3. 출판연도 검색");
        System.out.println("4. 장르 검색");
        System.out.println("5. 전체 조회 검색");
        System.out.println("6. 이전으로 돌아가기");

        int num = sc.nextInt();
        sc.nextLine();
        switch (num){
            case 1: ft.titleSearch(); break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: ft.allsearch(); break;
            case 6:  userUI(); break;
            default:
                System.out.println("잘못된 숫자 입력 이전으로 돌아갑니다"); break;
        }
    }
}
