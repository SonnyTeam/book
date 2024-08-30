package com.ohgiraffers.manager.dao;

import com.ohgiraffers.manager.dto.StatusDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookDAO {
    private Properties prop = new Properties();
    private StatusDTO statusDTO;
    private List<StatusDTO> statusList;

    public BookDAO(String url) {
        statusDTO = new StatusDTO();
        statusList = new ArrayList<StatusDTO>();

        try {
            prop.loadFromXML(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 도서 상태 관리 (도서이름으로 찾아서 도서 상태를 update)
    public int updateStatus(Connection con, String subject){

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result = 0;

        try {
            pstmt = con.prepareStatement(prop.getProperty("selectJoinStatus"));
            pstmt.setString(1, subject);
            rset = pstmt.executeQuery();

            String status_rent = "";
            String status_reserve = "";

            while (rset.next()) {
                status_rent = rset.getString(2);
                status_reserve = rset.getString(3);

            }

            pstmt = con.prepareStatement(prop.getProperty("updateStatus"));


            if(status_rent == "대여중"){

               pstmt.setString(3, "대여중");
               pstmt.setString(1, "대여가능");

               if(status_reserve == "예약가능"){
                  pstmt.setString(2, "예약중");
               }else if (status_reserve == "예약중"){
                  pstmt.setString(2, "예약가능");
               }

            }else {
               // 대여가능
                pstmt.setString(3, "대여가능");
                pstmt.setString(1, "대여중");

               if(status_reserve == "예약중"){
                   pstmt.setString(2, "예약가능");
               }else if(status_reserve == "예약불가"){
                   pstmt.setString(2, "예약가능");
               }
            }


            // 업데이트 결과 담음
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return result;
    }

    // 도서 상태 변경 이록 기록 / 조회
    public List<StatusDTO> selectStatus(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = con.prepareStatement(prop.getProperty("selectStatus"));

            rset = pstmt.executeQuery();


            String subject = "";
            String status_rent = "";
            String date_rent = "";
            String date_return = "";
            while (rset.next()) {
                subject = rset.getString(1);
                status_rent = rset.getString(2);
                date_rent = rset.getString(3);
                date_return = rset.getString(4);
            }

            statusDTO = new StatusDTO(subject, status_rent, date_rent, date_return);
            statusList.add(statusDTO);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return statusList;
    }



}
