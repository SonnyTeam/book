package com.ohgiraffers.manager.controller;

import com.ohgiraffers.login.CommonMemberFT;
import com.ohgiraffers.overdue.OverdueController;

import java.util.Scanner;

public class Manager {

    public void manager(){

        while(true){
            Scanner scr = new Scanner(System.in);
            System.out.println("==========================================");
            System.out.println("메뉴를 선택해주세요.");
            System.out.println("1. 도서 정보 관리");
            System.out.println("2. 사용자 관리");
            System.out.println("3. 도서 상태 관리");
            System.out.println("4. 도서 통계");
            System.out.println("5. 연체 관리");
            System.out.println("9. 로그아웃");
            int choice = scr.nextInt();

            switch(choice){
                case 1:
                    book_manage();
                    break;
                case 2:
                    user_manage();
                    break;
                case 3:
                    book_status();
                    break;
                case 4: break;
                case 5:  overDue(); break;
                case 9:
                    System.out.println("로그아웃 성공 ! 👋");
                    return;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }

    }

    public void book_manage() {
        BookManageController bookManageController = new BookManageController();

        mloop: while(true){
            Scanner scr = new Scanner(System.in);
            System.out.println("1. 도서 정보 검색");
            System.out.println("2. 도서 추가");
            System.out.println("3. 도서 수정");
            System.out.println("4. 도서 삭제");
            int choice = scr.nextInt();
            scr.nextLine();
            switch(choice){
                case 1 :
                    bookSearch();
                    break mloop;
                case 2:
                    bookManageController.insertBook();
                    break mloop;
                case 3:
                    bookManageController.updateBook();
                    break mloop;
                case 4:
                    bookManageController.deleteBook();
                    break mloop;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

    public void bookSearch(){
        Scanner scr = new Scanner(System.in);

        System.out.println("1. 제목 검색");
        System.out.println("2. 저자 검색");
        System.out.println("3. 출판연도 검색");
        System.out.println("4. 장르 검색");
        System.out.println("5. 전체 조회 검색");
        System.out.println("6. 이전으로 돌아가기");

        int num = scr.nextInt();
        scr.nextLine();
        CommonMemberFT ft = new CommonMemberFT();

        switch (num){
            case 1: ft.titleSearch(); break;
            case 2: ft.authorSearch(); break;
            case 3: ft.yearSearch(); break;
            case 4: ft.genreSearch(); break;
            case 5: ft.allSearch(); break;
            case 6: manager(); break;
            default:
                System.out.println("잘못된 숫자 입력 이전으로 돌아갑니다"); break;
        }
    }


    public void book_status(){

        BookController bookController = new BookController();
        //while문 추가 = list 못받아옴
        loop:while(true){

            Scanner scr = new Scanner(System.in);
            System.out.println("1. 도서 상태 변경");
            System.out.println("2. 도서 상태 변경이력 조회");
            int choice = scr.nextInt();

            switch (choice){
                case 1:
                    bookController.updateStatus();
                    break loop;
                case 2:
                    bookController.selectStatus();
                    break loop;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }

    }

    public void user_manage(){
        UserController userController = new UserController();

        loop : while(true){
            Scanner scr = new Scanner(System.in);
            System.out.println("1. 회원 검색");
            System.out.println("2. 회원 정보 수정");
            System.out.println("3. 회원 삭제");
            System.out.println("9. 전체 회원 리스트 보기");

            int choice = scr.nextInt();

            switch (choice){
                case 1: userController.selectUser(); break loop;
                case 2: userController.updateUser(); break loop;
                case 3: userController.deleteUser(); break loop;
                case 9: userController.userList(); break loop;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }
    }
    public void overDue(){
        OverdueController overdueController = new OverdueController();
        overdueController.overdueAutoInsert();

        System.out.println("연체중인 회원 리스트 조회");
        overdueController.overduelist();



    }


}
