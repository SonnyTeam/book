package com.ohgiraffers.overdue;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.close;
import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.getConnection;

public class OverdueDAO {
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement pstmt = null;
    static ResultSet rset = null;
    static Properties prop = new Properties();
    static LocalDate startTime;
    Map<Integer, String> isbnDate = new HashMap<>();

    public OverdueDAO(String url){
        try {
            prop.load(new FileReader(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void overlist(Connection con){

        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("overduelist"));
            rset = pstmt.executeQuery();

            while(rset.next()){
                System.out.print(rset.getInt(1));
                System.out.print(rset.getInt(2));
                System.out.print(rset.getInt(3));
                System.out.print(rset.getString(4));
                System.out.print(rset.getString(5));
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);

        }
    }
    public void mapinsert(Connection con){


        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("keyAndval"));
            rset = pstmt.executeQuery();
            while(rset.next()) {
                if (!rset.getString("DATE_END").equals("NULL")) {
                    isbnDate.put(rset.getInt("ISBN"), rset.getString("DATE_END"));
                }
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
    }
    public Map<Integer, String> getIsbnDate() {
        return isbnDate;
    }
    public void insertauto(Map<Integer,String> isbnDate, Connection con){

        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("autoinsert"));

            for(Map.Entry<Integer,String> entry : isbnDate.entrySet()){
                int isbn = entry.getKey();
                String endday = entry.getValue();
                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //이 형식으로 초기화하고
                LocalDate dueday = LocalDate.parse(endday); // ,formatter
                LocalDate currentDate = LocalDate.now();

                long dateover = ChronoUnit.DAYS.between(dueday,currentDate); //일수빼기

                int fee = calculateFee(String.valueOf(dateover));
                int userCode = getUserCode(isbn,getConnection());

                pstmt.setInt(1, isbn);
                pstmt.setInt(2, userCode);
                pstmt.setInt(3, fee);
                pstmt.setString(4, String.valueOf(dateover));

                pstmt.executeUpdate();

            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int calculateFee(String dateover){
        //연체일을 통해 계산하는 과정
        return 100*Integer.parseInt(dateover);
    }


    public int getUserCode(int isbn, Connection con) {
        int userCode=0;
        try {
            prop.loadFromXML(new FileInputStream("src/main/resources/mapper/book-query.xml"));
            pstmt = con.prepareStatement(prop.getProperty("finduserbyisbn"));

            rset = pstmt.executeQuery();
            while (rset.next()) {
                userCode = rset.getInt("user_code");
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        return userCode;
    }
}

