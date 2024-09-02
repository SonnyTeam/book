package com.ohgiraffers.login;

import com.ohgiraffers.JDBCTemplate.JDBCTemplate;
import com.ohgiraffers.manager.dao.BookDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.close;
import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.getConnection;

public class CommonMemberFT {
    static Scanner sc = new Scanner(System.in);
    // static Connection con = getConnection();
    static PreparedStatement pstmt = null;
    static ResultSet rset = null;
    static Properties prop = new Properties();
    static CommonMemberUI ui = new CommonMemberUI();
    static LocalDate startTime;

    private CommonMemberFTDAO dao = new CommonMemberFTDAO("src/main/resources/mapper/book-query.xml");


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
