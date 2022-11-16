package com.mattdev95.videohostingapplication.video;

import com.azure.spring.data.cosmos.core.mapping.Container;
import lombok.Builder;

import javax.persistence.ElementCollection;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
@Container(containerName = "videoDataCollection")
@Builder
public class Video {

    @Id
    private String title;
    private String publisher;
    private String producer;
    private String genre;
    private String ageRating;
    private String fileName;
    @ElementCollection
    private List<String> comments;
    private Long likes;
    private Date dateOfUpload;

    public Video(String title, String publisher, String producer, String genre, String ageRating, String fileName, List<String> comments, Long likes, Date dateOfUpload) {
        this.title = title;
        this.publisher = publisher;
        this.producer = producer;
        this.genre = genre;
        this.ageRating = ageRating;
        this.fileName = fileName;
        this.comments = comments;
        this.likes = likes;
        this.dateOfUpload = dateOfUpload;
    }
    public Video() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Date getDateOfUpload() {
        return dateOfUpload;
    }

    public void setDateOfUpload(Date dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }
}
