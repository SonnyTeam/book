package com.ohgiraffers.overdue;

import com.ohgiraffers.login.CommonMemberUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.getConnection;

public class OverdueController {
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement pstmt = null;
    static ResultSet rset = null;
    static Properties prop = new Properties();
    static CommonMemberUI ui = new CommonMemberUI();
    static LocalDate startTime;
    private OverdueDAO dao = new OverdueDAO("src/main/resources/mapper/book-query.xml");


    public void overduelist(){
        dao.overlist(getConnection());
    }
    public void overdueAutoInsert(){
        dao.mapinsert(getConnection());
        Map<Integer,String> map = dao.getIsbnDate();
        dao.insertauto(map, getConnection());
    }
}
