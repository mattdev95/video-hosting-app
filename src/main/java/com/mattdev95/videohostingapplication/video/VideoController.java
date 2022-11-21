package com.mattdev95.videohostingapplication.video;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {

    private final VideoBlobService videoBlobService;

    private final VideoCosmosService videoCosmosService;

    // this will need to be saved into teh cosmosdb
//    String url = null;
    String url = "random";
    /*
    1 - need to create a controller to catch the /videos post

     */
    @PostMapping
    public ResponseEntity<String> submitForm(@RequestBody VideoRequest video) {
        if(url != null) {
            Video newVideoData = new Video.VideoBuilder()
                    .id(video.getId())
                    .title(video.getTitle())
                    .producer(video.getProducer())
                    .publisher(video.getPublisher())
                    .genre(video.getGenre())
                    .fileName(url)
                    .ageRating(video.getAgeRating())
                    .dateOfUpload(LocalDateTime.now())
                    .build();
            videoCosmosService.saveVideoData(newVideoData);
            return ResponseEntity.created(URI.create("/videos")).body("Video data submitted");
        }


        return ResponseEntity.badRequest().body("Video data not submitted");
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


}
