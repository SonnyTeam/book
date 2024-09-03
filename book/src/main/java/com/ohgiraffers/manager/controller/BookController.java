package com.ohgiraffers.manager.controller;

import com.ohgiraffers.manager.dao.BookDAO;
import com.ohgiraffers.manager.dto.StatusDTO;

import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.getConnection;

public class BookController {
    /*
    *
    * **도서 상태 관리**
    - 도서의 상태(대여 중, 대여 가능, 예약 중 등)를 관리할 수 있어야 한다.
    - 도서 상태 변경 이력을 기록하고 조회할 수 있는 기능을 추가한다.
    * */

    private BookDAO bookDAO = new BookDAO("src/main/resources/mapper/manager-query.xml");


    // 도서 상태 관리
    public void updateStatus(){

        // 관리자 기능 중 선택 후..
        Scanner scr = new Scanner(System.in);
        System.out.println("도서 상태 변경을 시작합니다....");
        loop:while(true){
            System.out.println("상태를 변경할 도서의 제목을 입력해주세요.");
            // System.out.println("대여 중 <-> 대여가능 / 예약불가,예약 중 <-> 예약가능");
            String subject = scr.nextLine();

            StatusDTO statusDTO = bookDAO.getStatusDTO(getConnection(), subject);
            String currentStatus = statusDTO.getStatus_rent();
            int currentUserCode = statusDTO.getUser_code();

            if(currentStatus==null || currentStatus.equals("")){
                System.out.println("없는 책입니다. 다시 입력해주세요.");
                continue;
            }



            while(true){
                int answer = 0;
                if(currentStatus.equals("대여 중")){

                        System.out.println("해당 도서는 " + currentStatus + " 상태 입니다.");
                        //System.out.println("해당 도서는 " + currentStatus + " 상태 입니다. 반납하시겠습니까?");

                    System.out.println("1.반납 2.대여");
                    int choice = scr.nextInt();
                    switch(choice){
                        case 1: answer=1; break;
                        case 2: answer=3; break;
                    }
                }else {
                    System.out.println("해당 도서는 " + currentStatus + " 상태 입니다. 대여하시겠습니까?");
                }

                System.out.println("1. 예");
                System.out.println("2. 아니오");
                answer = scr.nextInt();

                if(answer == 1){
                    scr.nextLine();
                    if(currentStatus.equals("대여 중")){
                        System.out.println("반납할 회원의 이름을 입력해주세요.");
                    }else {
                        System.out.println("대여할 회원의 이름을 입력해주세요.");
                    }
                    //System.out.println("'" + differentStatus + "'으로 변경할 회원명을 입력해주세요.");
                    String name = scr.nextLine();
                    // System.out.println(name);
                    int result = bookDAO.updateStatus(getConnection(), subject, name);

                    if(result == 1){
                        System.out.println("도서 상태를 변경했습니다.");
                        break loop;
                    }else{
                        System.out.println("도서 상태 변경에 실패했습니다.");
                        break loop;
                    }
                }else if(answer == 2) {
                    System.out.println("상태 관리를 종료합니다.");
                    break loop;
                } else {
                    System.out.println("다시 입력해주세요.");
                }
            }

        }


    }

    // 도서 상태 변경 기록 조회
    public void selectStatus(){

        // List<StatusDTO> list = bookDAO.selectStatus(getConnection());
        List<StatusDTO> list = bookDAO.selectStatus(getConnection());


        if(list.isEmpty()){
            System.out.println("변경된 이력이 없습니다.");
        }else {
            for(StatusDTO statusDTO : list){
                // System.out.println(statusDTO);
                System.out.println(statusDTO.getSubject() + " | " + statusDTO.getStatus_rent() + " | " + statusDTO.getStatus_reserve() + " | " + statusDTO.getDate_rent() + " | " + statusDTO.getDate_return());
            }
        }


    }



}
