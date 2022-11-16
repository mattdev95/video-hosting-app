package com.mattdev95.videohostingapplication.video;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoBlobService {

    private final CloudBlobContainer cloudBlobContainer;

    public URI uploadVideo(MultipartFile multipartFile) {
        URI uri = null;
//        String multipartName = multipartFile.getName().replaceAll("[\n|\r|\t]", "_");
       // String fileName = multipartFile.getOriginalFilename();
        Optional<String> fileName = Optional.ofNullable(multipartFile.getOriginalFilename());

        try {
            CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(String.valueOf(fileName));
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException | StorageException | IOException e) {
            System.out.println("Something has gone wrong");
        }
        return Optional.ofNullable(uri).orElseThrow(NoSuchElementException::new);
    }
    }



