package com.mattdev95.videohostingapplication.video;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/api/v1.0/videos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {

    private final VideoBlobService videoBlobService;

    private final VideoCosmosService videoCosmosService;

    // this will need to be saved into teh cosmosdb
    String url = null;

    @PostMapping
    public ResponseEntity<String> submitForm(@RequestBody VideoRequest video) {
        if(url != null) {
            String dateToString = LocalDateTime.now().toString();
            Video newVideoData = new Video.VideoBuilder()
                    .id(video.getId())
                    .title(video.getTitle())
                    .producer(video.getProducer())
                    .publisher(video.getPublisher())
                    .genre(video.getGenre())
                    .fileName(url)
                    .ageRating(video.getAgeRating())
                    .dateOfUpload(dateToString)
                    .build();
            videoCosmosService.saveVideoData(newVideoData);
            return ResponseEntity.created(URI.create("/videos")).body("Video data submitted");
        }

        return ResponseEntity.badRequest().body("Video data not submitted");
    }

//    @GetMapping
//    public List<Video> findAllVideos() {
//        return videoCosmosService.getVideos();
//    }

    @GetMapping
    public List<Video> findAllVideos(@RequestParam Optional<String> title) {
        List<Video> listOfVideos = new ArrayList<>();
        try {
            if(title.isEmpty())  {
                return videoCosmosService.getVideos();
            }
            String titleOfVideo = title.get();
            Video findVideo = videoCosmosService.getVideoByTitle(titleOfVideo);
            if(findVideo != null) {
                listOfVideos.add(findVideo);
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No Video exists");
        }
        return listOfVideos;
    }

    @GetMapping("/{id}")
    public Video findVideo(@PathVariable String id) {
        return videoCosmosService.getVideo(id);
    }
    /**
     * // we need to first upload the video file and then send the video data to the cosmos db
     * To add to the blob storage, we need then another field to be able to add the file name to
     * @param file
     */
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('CREATOR')")
    public ResponseEntity<String> submitVideo(@RequestParam("videoFile") MultipartFile file) throws IOException {
        if(file != null) {
            url = videoBlobService.uploadVideo(file);
            return ResponseEntity.created(URI.create(url)).body("Video submitted");
        }
        return ResponseEntity.badRequest().body("The video was unable to be submitted");



    }

    /**
     * To post a comment on a video. The path should be /{id}/comments - however this was not possible in this JQuery solution.
     * @param commentRequest The request object
     * @return a response
     */
    @PostMapping("/comments")
    public ResponseEntity<String> submitComment(@RequestBody VideoReactionsRequest commentRequest) {
        Video video;
        if(commentRequest.getComment() != null || commentRequest.getLike() != null) {
            try {
                video = videoCosmosService.getVideoByTitle(commentRequest.getTitle());
                List<String> comments = new ArrayList<>();
                List<String> previousComments = video.getComments();
                Long previousLikes = video.getLikes();
                if(previousLikes == null) {
                    previousLikes = 0L;
                }
                Long newLikesValue = previousLikes + 1;
                if(previousComments != null) {
                    previousComments.add(commentRequest.getComment());
                    video.setComments(previousComments);
                } else {
                    comments.add(commentRequest.getComment());
                    video.setComments(comments);
                }
                video.setLikes(newLikesValue);
                videoCosmosService.saveVideoData(video);
                return ResponseEntity.ok("The comment of the video with ID " + video.getId() + " has been updated.");
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }

        }
        return ResponseEntity.badRequest().body("Something unexpectedly went wrong");
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<String> submitLikeCount(@RequestBody VideoReactionsRequest likeRequest, @PathVariable String id) {

        if(likeRequest.getLike() != null) {
            Long likes = Long.parseLong(likeRequest.getLike());
            Video video = videoCosmosService.getVideo(id);
            video.setLikes(likes);
            videoCosmosService.saveVideoData(video);
            return ResponseEntity.ok("The likes of the video with ID " + id + " has been updated.");
        }
        return ResponseEntity.notFound().build();

    }




}
