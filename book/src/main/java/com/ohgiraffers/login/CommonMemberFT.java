package com.ohgiraffers.login;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.getConnection;

public class CommonMemberFT {


    public CommonMemberFTDAO dao = new CommonMemberFTDAO("src/main/resources/mapper/book-query.xml");


    public String rental(int a){ //책 대여
        String returnRental = dao.rental(a, getConnection());

        return returnRental;
    }

    public String returnBook(int a){
        String returnBook = dao.returnBook(a, getConnection());

        return returnBook;
    }

    public void titleSearch() {
        dao.titleSearch(getConnection());
    }
    public void authorSearch() {
        dao.authorSearch(getConnection());
    }
    public void genreSearch() {
        dao.genreSearch(getConnection());
    }

    public void yearSearch() {
        dao.yearSearch(getConnection());
    }

    public void allSearch(){
        dao.allSearch(getConnection());
    }

    public void reserves(int a){
        int result = dao.reserves(a, getConnection());

        if(result == 1){
            System.out.println("예약 완료했습니다 ");
        }else {
            System.out.println("예약에 실패했습니다.");
        }
    }

}
