package com.mattdev95.videohostingapplication.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VideoCosmosService {

    private final VideoDataRepository videoDataRepository;

    @Autowired
    public VideoCosmosService(VideoDataRepository videoDataRepository) {
        this.videoDataRepository = videoDataRepository;
    }

    protected void saveVideoData(Video videoData) {
//        final Mono<Video> saveVideoMongo = videoDataRepository.save(videoData);
//        final Flux<Video> titleOfVideoFlux = videoDataRepository.findVideoByTitle(videoData.getTitle());
//        final Mono<Video> findByIdMono = videoDataRepository.findById(videoData.getId());
//        findByIdMono.block();
//        saveVideoMongo.block();
//        titleOfVideoFlux.collectList().block();
//
//        final Mono<Video> userResult = videoDataRepository.findById(videoData.getId());

        videoDataRepository.save(videoData);
        Video result = videoDataRepository.findVideoById(videoData.getId());
        System.out.println(result);



//
//
//        System.out.println(userResult);

    }

    protected List<Video> getVideos() {

        List<Video> sortedVideos = new ArrayList<>();
        Iterable<Video> videos = videoDataRepository.findAll();
//        for(Video oneVideo : videos) {
//            videoResponses.add(oneVideo);
//        }
        // want to

        List<Video> videoToList = new ArrayList<>();
        videos.forEach(videoToList::add);
        for(int i = videoToList.size()-1; i >=0; i--) {
            Video video = videoToList.get(i);
            sortedVideos.add(video);
        }
//        for(Video video : videoToList) {
//
//            VideoResponse videoBuild = new VideoResponse.VideoResponseBuilder()
//                    .fileName(video.getFileName())
//                    .ageRating(video.getAgeRating())
//                    .dateOfUpload(video.getDateOfUpload())
//                    .producer(video.getProducer())
//                    .genre(video.getGenre())
//                    .publisher(video.getPublisher())
//                    .title(video.getTitle())
//                    .build();
//            videoResponses.add(videoBuild);
//
//        }
        return sortedVideos;

    }

    protected Video getVideo(String id) {
        return videoDataRepository.findVideoById(id);
    }

    protected Video getVideoByTitle(String title) {
        Optional<Video> video = Optional.ofNullable(videoDataRepository.findVideoByTitle(title));
        if(video.isPresent()) {
            return videoDataRepository.findVideoByTitle(title);
        }
        throw new NoSuchElementException("No Video exists");

    }
}
