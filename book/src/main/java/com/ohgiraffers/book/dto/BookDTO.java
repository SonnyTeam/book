package com.ohgiraffers.book.dto;

import java.util.Calendar;

public class BookDTO {
    private String subject;
    private String author;
    private String publisher;
    private int public_year;
    private String genre;
    private int pages;

    public BookDTO() {
    }

    public BookDTO(String subject, String author, String publisher, int public_year, String genre) {
        this.subject = subject;
        this.author = author;
        this.publisher = publisher;
        this.public_year = public_year;
        this.genre = genre;
    }

    public BookDTO(String subject, String author, String publisher, int public_year, String genre, int pages) {
        this.subject = subject;
        this.author = author;
        this.publisher = publisher;
        this.public_year = public_year;
        this.genre = genre;
        this.pages = pages;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPublic_year() {
        return public_year;
    }

    public String getGenre() {
        return genre;
    }

    public int getPages() {  return pages; }

    public BookDTO setSubject(String subject) {
        if (subject.equals("")) {
            this.subject = null;
        }
        this.subject = subject;
        return this;
    }

    public BookDTO setAuthor(String author) {
        if(author.equals("")) {
            this.author = null;
        }
        this.author = author;
        return this;
    }

    public BookDTO setPublisher(String publisher) {
        if (publisher.equals("")) {
            this.publisher = null;
        }
        this.publisher = publisher;
        return this;
    }

    public BookDTO setPublic_year(int public_year) throws Exception {
        Calendar calendar = Calendar.getInstance();

        if(public_year < 0){
            System.out.println("음수입니다.");
            throw new Exception();
        }else if(public_year > calendar.get(Calendar.YEAR)) {
            System.out.println("미래의 책입니다.");
            throw new Exception();
        }else if(public_year == 0){
            throw new Exception();
        }
        else {
            this.public_year = public_year;
        }

        return this;
    }

    public BookDTO setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public BookDTO setPages(int pages) {
        if(pages <= 0){
            System.out.println("페이지수는 양수여야 합니다~~~");
        } else {
            this.pages = pages;
        }
        return this;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "subject='" + subject + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", public_year=" + public_year +
                ", genre='" + genre + '\'' +
                ", pages=" + pages +
                '}';
    }
}
