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
        videoDataRepository.save(videoData);
        Video result = videoDataRepository.findVideoById(videoData.getId());
        System.out.println(result);

    }

    protected List<Video> getVideos() {
        List<Video> sortedVideos = new ArrayList<>();
        Iterable<Video> videos = videoDataRepository.findAll();
        List<Video> videoToList = new ArrayList<>();
        videos.forEach(videoToList::add);
        for(int i = videoToList.size()-1; i >=0; i--) {
            Video video = videoToList.get(i);
            sortedVideos.add(video);
        }

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
