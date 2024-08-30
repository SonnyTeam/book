package com.ohgiraffers.login;

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
    static Connection con = getConnection();
    static PreparedStatement pstmt = null;
    static ResultSet rset = null;
    static Properties prop = new Properties();
    public void rental(){ //책 대여

        LocalDate startTime;
        int result =0;
        System.out.println("대여하실 책 번호 : ");
        int num = sc.nextInt();
        sc.nextLine();

        try {
            startTime = LocalDate.now();
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("rentalable"));
            pstmt.setInt(4,num);
            pstmt.setString(1,"대여 중");
            pstmt.setString(2,"예약가능");
            pstmt.setString(3,startTime.toString());

            result = pstmt.executeUpdate();
            if(result == 1){
                System.out.println("대여 완료했습니다");
                startTime.plusDays(30);
                System.out.println("대여 기간은"+startTime+"~"+startTime.plusDays(30)+"입니다");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }
    }
    public void returnBook(){

        System.out.println("반납하실 책 번호 : ");
        int result = 0;
        int num = sc.nextInt();
        sc.nextLine();

        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("returnBook"));
            pstmt.setInt(2,num);
            pstmt.setString(1,"대여가능");

            result = pstmt.executeUpdate();
            if(result == 1){
                System.out.println("정상적으로 반납 완료했습니다.");
            }else {
                System.out.println("반납 불가");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }
    }
    public void titleSearch(){

        System.out.println("책 제목 : ");
        String title = sc.nextLine();
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("titleSearch"));
            pstmt.setString(1,title);
            rset = pstmt.executeQuery();
            while(rset.next()){
                System.out.println(rset.getString("SUBJECT")+
                        rset.getString("AUTHOR")+
                        rset.getString("PUBLISHER")+
                        rset.getInt("PUBLIC_YEAR")+
                        rset.getString("GENRE")+
                        rset.getInt("ISBN")+
                        rset.getString("STATUS_RENT")+
                        rset.getString("STATUS_RESERVE")
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
    }
    public void allsearch(){


        System.out.println("책 전체 리스트");
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("allSearch"));

            rset = pstmt.executeQuery();
            while(rset.next()){
                System.out.println(rset.getString("SUBJECT")+
                        rset.getString("AUTHOR")+
                        rset.getString("PUBLISHER")+
                        rset.getInt("PUBLIC_YEAR")+
                        rset.getString("GENRE")+
                        rset.getInt("ISBN")+
                        rset.getString("STATUS_RENT")+
                        rset.getString("STATUS_RESERVE"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
            close(pstmt);
            close(rset);
        }
    }






}
