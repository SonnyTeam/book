package com.ohgiraffers.login;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.close;

public class CommonMemberFTDAO {
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement pstmt = null;
    static ResultSet rset = null;
    static Properties prop = new Properties();
    static CommonMemberUI ui = new CommonMemberUI();
    static LocalDate startTime;


    public CommonMemberFTDAO(String url) {
        try {
            prop.load(new FileReader(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String rental(int a, Connection con){ //책 대여

        int userCode = a;
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
            while(rset.next()){
                String currentStatus = rset.getString("status_rent");
                if("대여 중".equals(currentStatus)){
                    System.out.println("이미 대여중인 책입니다. 대여할 수 없습니다");
                    System.out.println("이전 메뉴로 돌아갑니다");
                    return ui.userUI(userCode);
                }else {

                    pstmt = con.prepareStatement(prop.getProperty("rentalable"));
                    pstmt.setInt(6, num);
                    pstmt.setString(1, "대여 중");
                    pstmt.setString(2, "예약가능");
                    pstmt.setString(3, startTime.toString());
                    pstmt.setString(4, startTime.plusDays(30).toString());
                    pstmt.setInt(5, userCode);


                    result = pstmt.executeUpdate();
                    if (result == 1) {
                        System.out.println("대여 완료했습니다");
                        System.out.println("대여 기간은 " + startTime + " ~ " + startTime.plusDays(30) + " 입니다");
                    }
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

    public String returnBook(int a, Connection con){
        int userCode = a;
        System.out.println("반납하실 책 번호 : ");
        int result = 0;
        int num = sc.nextInt();
        sc.nextLine();

        startTime = LocalDate.now();

        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));

            pstmt = con.prepareStatement(prop.getProperty("checkbookstatus"));
            pstmt.setInt(1, num);
            rset = pstmt.executeQuery();
            while(rset.next()){
                String currentStatus = rset.getString("STATUS_RENT");
                if("대여가능".equals(currentStatus)){
                    System.out.println("대여중인 책이 아닙니다. 이전으로 돌아갑니다");
                    return ui.userUI(userCode);
                } else {

                    pstmt = con.prepareStatement(prop.getProperty("returnBook"));
                    pstmt.setInt(3, num);
                    pstmt.setString(1, "대여가능");
                    pstmt.setString(2, startTime.toString());

                    result = pstmt.executeUpdate();
                    if (result == 1) {
                        System.out.println("정상적으로 반납 완료했습니다.");
                    } else {
                        System.out.println("반납 불가 이번 메뉴로 돌아갑니다");
                    }
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

    public void titleSearch(Connection con) {

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

    public void authorSearch(Connection con) {
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

    public void genreSearch(Connection con) {
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

    public void yearSearch(Connection con) {
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

    public void allSearch(Connection con){

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

    public String reserves(int a, Connection con){
        int userCode = a;
        //대여중만 예약하기
        System.out.println("예약하실 책 제목을 입력해주세요.");

        int reserveNum = sc.nextInt();
        int result = 0;
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("checkbookstatus"));
            pstmt.setInt(1, reserveNum);
            rset = pstmt.executeQuery();

            while(rset.next()) {
                String rentStataus = rset.getString("STATUS_RENT");
                if(rentStataus.equals("대여 중")){

                    System.out.println(rentStataus);

                    String currentStatus = rset.getString("STATUS_RESERVE");
                    int retnUserCode = rset.getInt("USER_CODE");

                    if(retnUserCode == userCode){
                        System.out.println("본인이 대여중인 책입니다. 다시 시도해주세요.");
                        return ui.userUI(userCode);
                    }

                    if ("예약 중".equals(currentStatus)) {
                        System.out.println("예약 중 ");

                        System.out.println("예약 중인 책입니다. 이전으로 돌아갑니다");
                        return ui.userUI(userCode);
                    }else{
                        System.out.println("대여 중 예약가능");

                        // 대여 중  예약가능
                        pstmt = con.prepareStatement(prop.getProperty("setreserve"));
                        pstmt.setInt(3, reserveNum);
                        pstmt.setString(1,"예약 중");
                        pstmt.setInt(2, userCode);
                        pstmt.executeUpdate();

                        pstmt = con.prepareStatement(prop.getProperty("checkbookstatus"));
                        pstmt.setInt(1, reserveNum);
                        rset = pstmt.executeQuery();
                        String endDay = "";
                        while(rset.next()) {
                            endDay = rset.getString("DATE_END");
                        }
                        System.out.println("예약 완료했습니다 "+ endDay+" 이후로 수령 가능합니다.");


                    }
                }else {
                    // 대여가능
                    System.out.println("예약불가인 책입니다. 이전으로 돌아갑니다");
                    return ui.userUI(userCode);
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
