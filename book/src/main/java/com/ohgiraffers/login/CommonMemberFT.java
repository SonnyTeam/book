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

    public String reserves(int a){
        String returnReserve = dao.reserves(a, getConnection());

        return returnReserve;
    }

}
