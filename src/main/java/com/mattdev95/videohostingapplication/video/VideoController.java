package com.mattdev95.videohostingapplication.video;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@Controller
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {

    private final VideoBlobService videoBlobService;
    /*
    1 - need to create a controller to catch the /videos post

     */
    @PostMapping
    public void submitForm(@RequestBody VideoRequest pub) {
        System.out.println(pub);
    }
    // https://mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
    // you need to upload a file

    /**
     * // we need to first upload the video file and then send the video data to the cosmos db
     * To add to the blob storage, we need then another field to be able to add the file name to
     * @param file
     */
    @PostMapping("/upload")
    public ResponseEntity<String> submitVideo(@RequestParam("videoFile") MultipartFile file) throws MalformedURLException {
        URI url = videoBlobService.uploadVideo(file);
        String toUrl = url.toURL().toString();
        return ResponseEntity.created(URI.create(toUrl)).body("Video submitted");


    }


}
