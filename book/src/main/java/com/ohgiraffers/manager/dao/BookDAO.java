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

import static com.ohgiraffers.JDBCTemplate.JDBCTemplate.*;

public class BookDAO {
    private Properties prop = new Properties();
    public static List<StatusDTO> statusList = new ArrayList<>();


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
            String date_end = "";
            int isbn = 0;
            int user_code = 0;

            while (rset.next()) {
                status_rent = rset.getString(2);
                //status_reserve = rset.getString(3);
                date_rent = rset.getString(3);
                date_return = rset.getString(4);
                isbn = rset.getInt(5);
                user_code = rset.getInt(6);
                date_end = rset.getString(7);
            }

            pstmt = con.prepareStatement(prop.getProperty("selectReserve"));
            pstmt.setInt(1, isbn);
            rset = pstmt.executeQuery();
            while(rset.next()){
                status_reserve = rset.getString(1);
            }

            statusDTO = new StatusDTO(subject, status_rent, status_reserve, date_rent, date_return, isbn, user_code, date_end);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } /*finally {
            //close(con);
            //close(pstmt);
            // close(rset);
        }*/

        return statusDTO;
    }


    // 도서 상태 관리 (도서이름으로 찾아서 도서 상태를 update)
    public int updateStatus(Connection con, String subject, String name){

        PreparedStatement pstmt = null;
        // PreparedStatement pstmt2 = null;
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

            // System.out.println(pstmt);

            while(rset.next()){
                code = rset.getString(1);
            }
            // System.out.println(code);

            if(code == null){
                System.out.println("없는 회원입니다.");
                return result;
            }

            String currentCode = String.valueOf(currentStatus.getUser_code());
            /*if(currentCode == null && !currentCode.equals(code)){
                // 현재유저코드가 null이면서 현재 사용자의 코드와같지않을때 같을 때 다 대여가능.
                // 대여가능
            }else */if(!code.equals(currentCode) && currentStatus.getStatus_rent().equals("대여 중")){
                // 현재유저코드랑 사용자의 코드랑 같지않을 때 -> 반납 시만 문제..
                // 대여 중
                System.out.println("회원이 일치하지 않습니다.");
                return result;
            }

            // 상태 테이블 수정
            pstmt.close();
            pstmt = con.prepareStatement(prop.getProperty("updateStatus"));

            String statusRent = currentStatus.getStatus_rent().equals("대여 중") ? "대여가능" : "대여 중";
            String statusReserve = currentStatus.getStatus_rent().equals("대여 중") ? "예약불가" : "예약가능";
            String dateRent = statusRent.equals("대여 중") ? LocalDate.now().toString() : currentStatus.getDate_rent();
            String dateReturn = statusRent.equals("대여 중") ? null : LocalDate.now().toString();
            String dateEnd = statusRent.equals("대여 중") ? LocalDate.now().plusDays(30).toString() : currentStatus.getDate_end();
            int isbn = currentStatus.getIsbn();


            pstmt.setString(1, statusRent);
            //pstmt.setString(2, statusReserve);
            pstmt.setString(2, code);
            pstmt.setString(3, dateRent);
            pstmt.setString(4, dateReturn);
            pstmt.setString(5, dateEnd);
            pstmt.setString(6, currentStatus.getStatus_rent());
            pstmt.setInt(7, isbn);

            result = pstmt.executeUpdate();

            // 예약상태테이블도 수정
            pstmt.close();
            pstmt = con.prepareStatement(prop.getProperty("updateReserveStatus"));


            pstmt.setString(1, statusReserve);
            pstmt.setString(2, null);
            pstmt.setInt(3, isbn);

            int codeI = Integer.parseInt(code);
            // 변경된 이력 저장
            StatusDTO updateStatus = new StatusDTO(subject, statusRent, statusReserve, dateRent, dateReturn, isbn, codeI, dateEnd);
            tempStatusList.add(updateStatus);

            statusList.addAll(tempStatusList);

            storeHistory(con);

            // 업데이트 결과 담음
            result = pstmt.executeUpdate();

            //con.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("잘못 입력하셨습니다.");
            // throw new RuntimeException(e);
        } finally {
            close(con);
            close(pstmt);
            close(rset);

        }


        // 디버깅: 리스트 상태 확인
        // System.out.println("updateStatus() - statusList size: " + statusList.size());

        return result;
    }

    public int reserveStatus(Connection con, String subject, String name){

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


            pstmt = con.prepareStatement(prop.getProperty("findUserCode"));

            pstmt.setString(1, name);
            rset = pstmt.executeQuery();
            String code = ""; // 입력한 사람의 사용자 코드


            while(rset.next()){
                code = rset.getString(1);
            }

            pstmt = con.prepareStatement(prop.getProperty("findUserCode"));

            pstmt.setString(1, subject);
            rset = pstmt.executeQuery();
            String compareCode = "";

            if(compareCode == code){
                System.out.println("본인이 대여중인 책입니다. 다시 시도해주세요.");
                return 0;
            }



            if(code == null){
                System.out.println("없는 회원입니다.");
                return result;
            }


            pstmt.close();
            pstmt = con.prepareStatement(prop.getProperty("updateReserveStatus"));


            String statusRent = "대여 중";
            // 예약은 수정해야함.
            String statusReserve = "예약 중";
            String dateRent = currentStatus.getDate_rent();
            String dateReturn = currentStatus.getDate_return();
            String dateEnd = currentStatus.getDate_end();
            int isbn = currentStatus.getIsbn();


            pstmt.setString(1, statusReserve);
            pstmt.setString(2, code);
            pstmt.setInt(3, isbn);

            int codeI = Integer.parseInt(code);

            // 변경된 이력 저장
            StatusDTO updateStatus = new StatusDTO(subject, statusRent, statusReserve, dateRent, dateReturn, isbn, codeI, dateEnd);
            tempStatusList.add(updateStatus);

            statusList.addAll(tempStatusList);

            storeHistory(con);

            // 업데이트 결과 담음
            result = pstmt.executeUpdate();

            //con.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("잘못 입력하셨습니다.");
            // throw new RuntimeException(e);
        } finally {
            close(con);
            close(pstmt);
            close(rset);

        }


        return result;
    }



    //public List<StatusDTO> selectStatus(Connection con) {
    public void selectStatus(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = con.prepareStatement(prop.getProperty("selectHistory"));
            rset = pstmt.executeQuery();

            System.out.println("====== 도서 상태변경 히스토리 ======");
            while(rset.next()){
                System.out.println("책 제목 : " + rset.getString(1)
                        + " | 사용자(대여/반납) : " + rset.getString(2)
                        + " | 도서상태(대여/반납) : " + rset.getString(3)
                        + " | 대여일 : " + rset.getString(4)
                        + " | 반납일 : " + rset.getString(5)
                        + " | 반납기한 : " + rset.getString(6)
                        + " | 사용자(예약) : " + rset.getString(7)
                        + " | 도서상태(예약) : " + rset.getString(8));
            }
            System.out.println();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // return new ArrayList<>(statusList);
    }

    public void storeHistory(Connection con) {

        PreparedStatement pstmt = null;
        int result = 0;

        try {

            if(!statusList.isEmpty()){
                // System.out.println(statusList);
                for(StatusDTO statusDTO : statusList){
                    pstmt = con.prepareStatement(prop.getProperty("insertHistory"));
                    String userCode = String.valueOf(Integer.valueOf(statusDTO.getUser_code()));
                    if(statusDTO.getUser_code() == 0){
                        pstmt.setString(2, null);
                    }else {
                        pstmt.setInt(2, statusDTO.getUser_code());
                    }

                    // System.out.println(statusDTO.getIsbn() + statusDTO.getStatus_rent() + statusDTO.getStatus_reserve() );

                    pstmt.setInt(1, statusDTO.getIsbn());
                    pstmt.setString(3, statusDTO.getStatus_rent());
                    pstmt.setString(4, statusDTO.getStatus_reserve());
                    pstmt.setString(5, statusDTO.getDate_rent());
                    pstmt.setString(6, statusDTO.getDate_return());
                    pstmt.setString(7, statusDTO.getDate_end());

                    result = pstmt.executeUpdate();

                }


                if(result == 1){
                    System.out.println("데이터베이스에 저장되었습니다.");
                }else {
                    System.out.println("데이터베이스 저장 실패");
                }

                statusList.clear();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
