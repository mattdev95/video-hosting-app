package com.mattdev95.videohostingapplication.video;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VideoBlobService {

    public String uploadVideo(MultipartFile multipartFile) throws IOException {

        String ur = "https://videostreamingservice.blob.core.windows.net";
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .endpoint(ur)
                .sasToken("sp=racwdl&st=2022-11-22T09:40:25Z&se=2023-02-23T17:40:25Z&sv=2021-06-08&sr=c&sig=KBav7%2FC7WvU6WaL34yj57JJ0C82Ap3lPjzLqY4Xi818%3D")
                .containerName("videodata")
                .buildClient();
        BlobClient blobClient = containerClient.getBlobClient(multipartFile.getOriginalFilename());
        blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);
        return blobClient.getBlobUrl();
    }

    }



