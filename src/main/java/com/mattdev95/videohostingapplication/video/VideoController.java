package com.mattdev95.videohostingapplication.video;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/videos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {

    private final VideoBlobService videoBlobService;

    private final VideoCosmosService videoCosmosService;

    // this will need to be saved into teh cosmosdb
//    String url = null;
    String url = "https://videostreamingservice.blob.core.windows.net/videodata/WIN_20221121_22_10_01_Pro.mp4";
    /*
    1 - need to create a controller to catch the /videos post

     */
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

    @GetMapping
    public List<Video> findAllVideos() {
        return videoCosmosService.getVideos();
    }

    @GetMapping("/{id}")
    public Video findVideo(@PathVariable String id) {
        return videoCosmosService.getVideo(id);
    }

    // https://mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
    // you need to upload a file

    /**
     * // we need to first upload the video file and then send the video data to the cosmos db
     * To add to the blob storage, we need then another field to be able to add the file name to
     * @param file
     */
    @PostMapping("/upload")
    public ResponseEntity<String> submitVideo(@RequestParam("videoFile") MultipartFile file) throws IOException {
        url = videoBlobService.uploadVideo(file);
        return ResponseEntity.created(URI.create(url)).body("Video submitted");


    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<String> submitComment(@RequestBody String comment, @PathVariable String id) {
        List<String> comments = new ArrayList<>();
        comments.add(comment);
        Video video = videoCosmosService.getVideo(id);
        video.setComments(comments);
        videoCosmosService.saveVideoData(video);
        return ResponseEntity.ok("The comment of the video with ID " + id + " has been updated.");


    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<String> submitLikeCount(@RequestBody String likes, @PathVariable String id) {
        Long idToLong = Long.parseLong(likes);
        Video video = videoCosmosService.getVideo(id);
        video.setLikes(idToLong);
        videoCosmosService.saveVideoData(video);
        return ResponseEntity.ok("The likes of the video with ID " + id + " has been updated.");


    }




}
