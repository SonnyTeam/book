package com.ohgiraffers.login;

import java.io.FileInputStream;
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
    static CommonMemberUI ui = new CommonMemberUI();
    static LocalDate startTime;

    public String rental(){ //책 대여

        int result =0;
        System.out.println("대여하실 책 번호 : ");
        int num = sc.nextInt();
        sc.nextLine();

        try {
            startTime = LocalDate.now();
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));

            pstmt = con.prepareStatement(prop.getProperty("checkbookstatus"));
            pstmt.setInt(1, num);
            rset = pstmt.executeQuery();
            if(rset.next()){
                String currentStatus = rset.getString("status");
                if("대여 중".equals(currentStatus)){
                    System.out.println("이미 대여중인 책입니다. 대여할 수 없습니다");
                    System.out.println("이전 메뉴로 돌아갑니다");
                    return ui.userUI();
                }
            }else {

                pstmt = con.prepareStatement(prop.getProperty("rentalable"));
                pstmt.setInt(5, num);
                pstmt.setString(1, "대여 중");
                pstmt.setString(2, "예약가능");
                pstmt.setString(3, startTime.toString());
                pstmt.setString(4, startTime.plusDays(30).toString());

                result = pstmt.executeUpdate();
                if (result == 1) {
                    System.out.println("대여 완료했습니다");
                    System.out.println("대여 기간은 " + startTime + " ~ " + startTime.plusDays(30) + " 입니다");
                }
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
        return null;
    }
    public String returnBook(){

        System.out.println("반납하실 책 번호 : ");
        int result = 0;
        int num = sc.nextInt();
        sc.nextLine();

        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));

            pstmt = con.prepareStatement(prop.getProperty("checkbookstatus"));
            pstmt.setInt(1, num);
            rset = pstmt.executeQuery();
            if(rset.next()){
                String currentStatus = rset.getString("STATUS_RENT");
                if("대여가능".equals(currentStatus)){
                    System.out.println("대여중인 책이 아닙니다. 이전으로 돌아갑니다");
                    return ui.userUI();
                }
            }else {

                pstmt = con.prepareStatement(prop.getProperty("returnBook"));
                pstmt.setInt(2, num);
                pstmt.setString(1, "대여가능");

                result = pstmt.executeUpdate();
                if (result == 1) {
                    System.out.println("정상적으로 반납 완료했습니다.");
                } else {
                    System.out.println("반납 불가 이번 메뉴로 돌아갑니다");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }
        return null;
    }
    public void titleSearch() {

        System.out.println("책 제목 : ");
        String title = sc.nextLine();
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("titleSearch"));
            pstmt.setString(1, title);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getString("SUBJECT") +
                        rset.getString("AUTHOR") +
                        rset.getString("PUBLISHER") +
                        rset.getInt("PUBLIC_YEAR") +
                        rset.getString("GENRE") +
                        rset.getInt("ISBN") +
                        rset.getString("STATUS_RENT") +
                        rset.getString("STATUS_RESERVE")
                );
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
    public void authorSearch() {
        System.out.println("저자 이름 : ");
        String author = sc.nextLine();
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("authorsearch"));
            pstmt.setString(1, author);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getString("SUBJECT") +
                        rset.getString("AUTHOR") +
                        rset.getString("PUBLISHER") +
                        rset.getInt("PUBLIC_YEAR") +
                        rset.getString("GENRE") +
                        rset.getInt("ISBN") +
                        rset.getString("STATUS_RENT") +
                        rset.getString("STATUS_RESERVE")
                );
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
    public void genreSearch() {
        System.out.println("장르 검색 : ");
        String genre = sc.nextLine();
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("genresearch"));
            pstmt.setString(1, genre);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getString("SUBJECT") +
                        rset.getString("AUTHOR") +
                        rset.getString("PUBLISHER") +
                        rset.getInt("PUBLIC_YEAR") +
                        rset.getString("GENRE") +
                        rset.getInt("ISBN") +
                        rset.getString("STATUS_RENT") +
                        rset.getString("STATUS_RESERVE")
                );
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

    public void yearSearch() {
        System.out.println("출판 년도 : ");
        int year = sc.nextInt();
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("yearsearch"));
            pstmt.setInt(1, year);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getString("SUBJECT") +
                        rset.getString("AUTHOR") +
                        rset.getString("PUBLISHER") +
                        rset.getInt("PUBLIC_YEAR") +
                        rset.getString("GENRE") +
                        rset.getInt("ISBN") +
                        rset.getString("STATUS_RENT") +
                        rset.getString("STATUS_RESERVE")
                );
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

    public String reseves(){
        //대여중만 예약하기
        System.out.println("예약하실 책 번호 입력");

        int reserveNum = sc.nextInt();
        int result = 0;
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("checkbookstatus"));
            pstmt.setInt(1, reserveNum);
            rset = pstmt.executeQuery();

            if(rset.next()) {
                String currentStatus = rset.getString("STATUS_RESERVE");
                if ("예약 중".equals(currentStatus)) {
                    System.out.println("예약 중인 책입니다. 이전으로 돌아갑니다");
                    return ui.userUI();
                }else{
                    pstmt = con.prepareStatement(prop.getProperty("setreseve"));
                    pstmt.setInt(2, reserveNum);
                    pstmt.setString(1,"예약 중");
                    result = pstmt.executeUpdate();

                    if(result == 1) {
                        pstmt = con.prepareStatement(prop.getProperty("checkbookstatus"));
                        rset = pstmt.executeQuery();
                        String endDay = rset.getString("DATE_RETURN");
                        System.out.println("예약 완료했습니다 "+ endDay+" 이후로 수령 가능합니다.");
                    }
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
        return null;
    }
}
