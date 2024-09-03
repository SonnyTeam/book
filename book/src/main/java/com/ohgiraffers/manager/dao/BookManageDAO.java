package com.ohgiraffers.manager.dao;

import com.ohgiraffers.book.dto.BookDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.*;

public class BookManageDAO {
    private Properties prop = new Properties();

    public BookManageDAO(String url) {

        try {
            prop.loadFromXML(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertBook(Connection con, BookDTO bookDTO) {
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            pstmt = con.prepareStatement(prop.getProperty("insertBook"));
            pstmt.setString(1, bookDTO.getSubject());
            pstmt.setString(2, bookDTO.getAuthor());
            pstmt.setString(3, bookDTO.getPublisher());
            pstmt.setInt(4, bookDTO.getPublic_year());
            pstmt.setString(5, bookDTO.getGenre());
            pstmt.setInt(6, bookDTO.getPages());
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }
        return result;
    }

    public int deleteBook(Connection con, String subject) {
        PreparedStatement pstmt = null;
        int result = 0;

        try {

            con.setAutoCommit(false);

            pstmt = con.prepareStatement(prop.getProperty("deleteBook"));
            pstmt.setString(1, subject);
            result = pstmt.executeUpdate();

            con.rollback();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int updateBook(Connection con, int isbn, BookDTO bookDTO) {
        PreparedStatement pstmt = null;
        if(bookDTO.getSubject() == null){

        }
        String query = "UPDATE tbl_book SET ";
        /*subject = ?,
        author = ?,
        publisher = ?,
        public_year = ?,
        genre = ?,
        pages = ?
        WHERE isbn = ?;*/
        if (bookDTO.getSubject() != null) {
            query = query + "subject = " + bookDTO.getSubject() + ", ";
        }
        if (bookDTO.getAuthor() != null) {
            query = query + "author = " + bookDTO.getAuthor() + ", ";
        }
        if (bookDTO.getPublisher() != null) {
            query = query + "publisher = " + bookDTO.getPublisher() + ", ";
        }
        if (bookDTO.getPublic_year() != 0) {
            query = query + "public_year = " + bookDTO.getPublic_year() + ", ";
        }
        if (bookDTO.getGenre() != null) {
            query = query + "genre = " + bookDTO.getGenre() + ", ";
        }
        if (bookDTO.getPages() != 0) {
            query = query + "pages = " + bookDTO.getPages() + ", ";
        }
        query =query.substring(0,query.length()-1);
        query = query + "WHERE ISBN = " + isbn;

        System.out.println(query);

        //query 문에서 마지막 , 제거후 where isbn = ? 추가
       /* query.split(,)

        pstmt = con.prepareStatement(query);*/


        //
        return 0;

    }
}
