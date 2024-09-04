package com.ohgiraffers.login;

import com.ohgiraffers.Application;

import java.util.Scanner;

public class CommonMemberUI {
    Scanner sc = new Scanner(System.in);
    CommonMemberFT ft = new CommonMemberFT();

    public String userUI(int a){
        int userCode = a;
        loop:while(true){
            System.out.println("일반 회원 메뉴");
            System.out.println("1. 도서 검색");
            System.out.println("2. 대여");
            System.out.println("3. 반납");
            System.out.println("4. 예약하기");
            System.out.println("5. 마이페이지");
            System.out.println("9. 로그아웃");
            int num = sc.nextInt();
            sc.nextLine();
            switch (num) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    ft.rental(userCode);
                    break;
                case 3:
                    ft.returnBook(userCode);
                    break;
                case 4:
                    ft.reserves(userCode);
                    break;
                case 5:
                    mypage(userCode);
                case 9:
                    System.out.println("로그아웃 성공 ! 👋");
                    break loop;
                default:
                    System.out.println("잘못입력");
                    break;
            }
        }
        return null;
    }

    private void mypage(int userCode) {
        myloop:while(true) {
            System.out.println("===마이페이지===");
            System.out.println("1. 내 정보 수정");
            System.out.println("2. 대여 중인 책 목록");
            System.out.println("3. 예약 중인 책 목록");
            System.out.println("9. 이전으로 돌아가기");
            int num = sc.nextInt();
            sc.nextLine();
            switch (num) {
                case 1:
                    ft.updateUser(userCode);
                    break myloop;
                case 2:

                    break myloop;
                case 3:

                    break myloop;
                case 9:
                    break myloop;
            }
        }
    }

    public void searchBook(){

        loop:while (true) {

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
                case 5: ft.allSearch(); break;
                //case 6: userUI(userCode); break;
                case 6:
                     break loop;
                default:
                    System.out.println("잘못된 숫자 입력 이전으로 돌아갑니다"); break;
            }
        }

    }
}
