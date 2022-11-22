package com.mattdev95.videohostingapplication.video;

import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Builder;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@Builder
public class VideoResponse {

    private String id;
    private String title;
    private String publisher;
    private String producer;
    private String genre;
    private String ageRating;

    private String fileName;

    private List<String> comments = new ArrayList<>();
    private Long likes;
    private String dateOfUpload;

    public VideoResponse(String id, String title, String publisher, String producer, String genre, String ageRating, String fileName, List<String> comments, Long likes, String dateOfUpload) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDateOfUpload() {
        return dateOfUpload;
    }

    public void setDateOfUpload(String dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }
}
