package com.ohgiraffers.manager.dto;

public class StatusDTO {

    private String subject;
    private String status_rent;
    private String date_rent;
    private String date_return;

    public StatusDTO() {
    }

    public StatusDTO(String subject, String status_rent, String date_rent, String date_return) {
        this.subject = subject;
        this.status_rent = status_rent;
        this.date_rent = date_rent;
        this.date_return = date_return;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus_rent() {
        return status_rent;
    }

    public void setStatus_rent(String status_rent) {
        this.status_rent = status_rent;
    }

    public String getDate_rent() {
        return date_rent;
    }

    public void setDate_rent(String date_rent) {
        this.date_rent = date_rent;
    }

    public String getDate_return() {
        return date_return;
    }

    public void setDate_return(String date_return) {
        this.date_return = date_return;
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
                "subject='" + subject + '\'' +
                ", status_rent='" + status_rent + '\'' +
                ", date_rent='" + date_rent + '\'' +
                ", date_return='" + date_return + '\'' +
                '}';
    }
}
