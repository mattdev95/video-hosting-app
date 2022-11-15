package com.mattdev95.videohostingapplication.video;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
public class VideoController {
    /*
    1 - need to create a controller to catch the /videos post

     */
    @PostMapping
    public void submitForm() {
        System.out.println();
    }


}
