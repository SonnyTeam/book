package com.ohgiraffers.manager.dao;

import com.ohgiraffers.manager.dto.StatusDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookDAO {
    private Properties prop = new Properties();
    private static List<StatusDTO> statusList = new ArrayList<>();


    public BookDAO(String url) {

        try {
            prop.loadFromXML(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StatusDTO getStatusDTO(Connection con, String subject) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        StatusDTO statusDTO = new StatusDTO();

        try {
            pstmt = con.prepareStatement(prop.getProperty("selectJoinStatus"));
            pstmt.setString(1, subject);
            rset = pstmt.executeQuery();
            String status_rent = "";
            String status_reserve = "";
            String date_rent = "";
            String date_return = "";
            int isbn = 0;

            while (rset.next()) {
                status_rent = rset.getString(2);
                status_reserve = rset.getString(3);
                date_rent = rset.getString(4);
                date_return = rset.getString(5);
                isbn = rset.getInt(6);
            }


            statusDTO = new StatusDTO(subject, status_rent, status_reserve, date_rent, date_return, isbn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statusDTO;
    }


    // 도서 상태 관리 (도서이름으로 찾아서 도서 상태를 update)
    public int updateStatus(Connection con, String subject, String name){

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result = 0;

        // 임시 리스트 생성
        List<StatusDTO> tempStatusList = new ArrayList<>();

        try {
            // 첫번째 상태 저장
            StatusDTO currentStatus = getStatusDTO(con, subject);

            if(currentStatus != null){
                tempStatusList.add(currentStatus);
            }


            //con.setAutoCommit(false);
            pstmt = con.prepareStatement(prop.getProperty("findUserCode"));
            pstmt.setString(1, name);
            rset = pstmt.executeQuery();
            String code = "";

            while(rset.next()){
                code = rset.getString(1);
            }


            pstmt = con.prepareStatement(prop.getProperty("updateStatus"));

            String statusRent = currentStatus.getStatus_rent().equals("대여 중") ? "대여가능" : "대여 중";
            // 예약은 수정해야함.
            String statusReserve = currentStatus.getStatus_rent().equals("대여 중") ? "예약불가" : "예약가능";
            String dateRent = statusRent.equals("대여 중") ? LocalDate.now().toString() : currentStatus.getDate_rent();
            String dateReturn = statusRent.equals("대여 중") ? null : LocalDate.now().toString();
            int isbn = currentStatus.getIsbn();

            pstmt.setString(1, statusRent);
            pstmt.setString(2, statusReserve);
            pstmt.setString(3, code);
            pstmt.setString(4, dateRent);
            pstmt.setString(5, dateReturn);
            pstmt.setString(6, currentStatus.getStatus_rent());
            pstmt.setInt(7, isbn);

            // 변경된 이력 저장
            StatusDTO updateStatus = new StatusDTO(subject, statusRent, statusReserve, dateRent, dateReturn, isbn);
            tempStatusList.add(updateStatus);

            statusList.addAll(tempStatusList);


            // 업데이트 결과 담음
            result = pstmt.executeUpdate();

            //con.rollback();

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("잘못 입력하셨습니다.");
            // throw new RuntimeException(e);
        }


        // 디버깅: 리스트 상태 확인
        System.out.println("updateStatus() - statusList size: " + statusList.size());

        return result;
    }





    public List<StatusDTO> selectStatus() {

        // 디버깅: 리스트 상태 확인
        System.out.println("selectStatus() - statusList size: " + statusList.size());


        return new ArrayList<>(statusList);
    }



}
