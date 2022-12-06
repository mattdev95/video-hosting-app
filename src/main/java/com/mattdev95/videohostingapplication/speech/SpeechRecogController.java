package com.mattdev95.videohostingapplication.speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RequestMapping(value = "/api/v1.0/speech")
@RestController
public class SpeechRecogController {

    SpeechRecogService speechRecogService;

    @Autowired
    public SpeechRecogController(SpeechRecogService speechRecogService) {
        this.speechRecogService = speechRecogService;
    }

    @GetMapping
    public String getSpeech() throws ExecutionException, InterruptedException {
        return speechRecogService.recogniseSpeech();
    }
}
