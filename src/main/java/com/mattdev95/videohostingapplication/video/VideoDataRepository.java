package com.mattdev95.videohostingapplication.video;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Repository
public interface VideoDataRepository extends ReactiveCosmosRepository<Video, String> {
//    Flux<Video> findVideoById(String id);

    Flux<Video> findVideoByTitle(String title);

}
