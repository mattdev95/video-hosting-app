package com.mattdev95.videohostingapplication.video;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface VideoDataRepository extends CosmosRepository<Video, String> {
//    Flux<Video> findVideoById(String id);

    Video findVideoById(String id);

}
