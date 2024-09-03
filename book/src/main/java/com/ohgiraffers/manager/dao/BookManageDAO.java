package com.ohgiraffers.manager.dao;

import com.ohgiraffers.book.dto.BookDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        int result = 0;

        String query = "UPDATE tbl_book SET ";

        if (bookDTO.getSubject() != null) {
            query = query + "subject = '" + bookDTO.getSubject() + "', ";
        }
        if (bookDTO.getAuthor() != null) {
            query = query + "author = '" + bookDTO.getAuthor() + "', ";
        }
        if (bookDTO.getPublisher() != null) {
            query = query + "publisher = '" + bookDTO.getPublisher() + "', ";
        }
        if (bookDTO.getPublic_year() != 0) {
            query = query + "public_year = " + bookDTO.getPublic_year() + ", ";
        }
        if (bookDTO.getGenre() != null) {
            query = query + "genre = '" + bookDTO.getGenre() + "', ";
        }
        if (bookDTO.getPages() != 0) {
            query = query + "pages = " + bookDTO.getPages() + ", ";
        }
        query = query.substring(0,query.length()-2);
        query = query + " WHERE ISBN = " + isbn;

        System.out.println(query);

        try {
//            con.setAutoCommit(false);

            pstmt = con.prepareStatement(query);
            result = pstmt.executeUpdate();

//            con.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }
        return result;
    }

    public int selectByISBN(Connection con, int isbn) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = con.prepareStatement(prop.getProperty("selectByISBN"));
            pstmt.setInt(1, isbn);
            rset = pstmt.executeQuery();
            if (!rset.next()) {
                System.out.println("ISBN " + isbn + " 에 해당하는 도서가 없습니다.");
                return 0;
            }

            do {
                System.out.println("제목 : " + rset.getString("subject") + ", 저자 : " + rset.getString("author") + ", 출판사 : " + rset.getString("publisher") + ", 출판연도 : " + rset.getInt("public_year") + ", 장르 : " + rset.getString("genre") + ", 페이지수 : " + rset.getInt("pages"));
            } while (rset.next());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
        return 1;
    }
}
