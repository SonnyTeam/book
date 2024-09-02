package com.ohgiraffers.login;

import java.util.Scanner;

public class CommonMemberUI {
    Scanner sc = new Scanner(System.in);
    CommonMemberFT ft = new CommonMemberFT();
    public String userUI(){

        while(true){
            System.out.println("일반 회원 메뉴");
            System.out.println("1. 도서 검색");
            System.out.println("2. 대여");
            System.out.println("3. 반납");
            System.out.println("4. 예약하기");
            int num = sc.nextInt();
            sc.nextLine();
            switch (num) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    ft.rental();
                    break;
                case 3:
                    ft.returnBook();
                    break;
                case 4:
                    ft.reseves();
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
            case 2: ft.authorSearch(); break;
            case 3: ft.yearSearch(); break;
            case 4: ft.genreSearch(); break;
            case 5: ft.allsearch(); break;
            case 6:  userUI(); break;
            default:
                System.out.println("잘못된 숫자 입력 이전으로 돌아갑니다"); break;
        }
    }
}
