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

    private BookDAO menuDAO = new BookDAO("src/main/resources/mapper/manager-query.xml");

    // 도서 상태 관리
    public void updateStatus(){

        // 관리자 기능 중 선택 후..
        Scanner scr = new Scanner(System.in);
        System.out.println("도서 상태 관리를 시작합니다....");
        System.out.println("도서의 제목을 입력해주세요.");
        String subject = scr.nextLine();

        int result = menuDAO.updateStatus(getConnection(), subject);

        if(result == 1){
            System.out.println("도서 상태를 변경했습니다.");
        }else{
            System.out.println("도서 상태 변경에 실패했습니다.");
        }
    }

    // 도서 상태 변경 기록 조회
    public void selectStatus(){

        List<StatusDTO> list = menuDAO.selectStatus(getConnection());
        for(StatusDTO statusDTO : list){
            System.out.println(statusDTO);
        }

    }



}
