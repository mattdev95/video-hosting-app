package com.mattdev95.videohostingapplication.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class VideoCosmosService {

    private final VideoDataRepository videoDataRepository;

    @Autowired
    public VideoCosmosService(VideoDataRepository videoDataRepository) {
        this.videoDataRepository = videoDataRepository;
    }

    protected void saveVideoData(Video videoData) {
        final Mono<Video> saveVideoMongo = videoDataRepository.save(videoData);
        final Flux<Video> titleOfVideoFlux = videoDataRepository.findVideoByTitle(videoData.getTitle());
        final Mono<Video> findByIdMono = videoDataRepository.findById(videoData.getId());
        findByIdMono.block();
        saveVideoMongo.block();
        titleOfVideoFlux.collectList().block();

        final Mono<Video> userResult = videoDataRepository.findById(videoData.getId());

//
//
//        System.out.println(userResult);

    }
}
