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
//        String constr = "AccountName=videostreamingservice;" +
//                "AccountKey=LS0losB4f73o4U6Xw47a3aQcAxUqf5eE40kOG/56VStVteneZxMmyfgsQxRW6CBwSujHSR3+VSgo+AStJ7R+tA==" +
//                "EndpointSuffix=blob.core.windows.net;" +
//                "DefaultEndpointProtocol=https;";
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
//        URI uri = null;
////        String multipartName = multipartFile.getName().replaceAll("[\n|\r|\t]", "_");
//       // String fileName = multipartFile.getOriginalFilename();
//        Optional<String> fileName = Optional.ofNullable(multipartFile.getOriginalFilename());
//
//        try {
//            CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(String.valueOf(fileName));
//            blob.upload(multipartFile.getInputStream(), -1);
//            uri = blob.getUri();
//        } catch (URISyntaxException | StorageException | IOException | StorageException e) {
//            System.out.println("Something has gone wrong");
//        }
//        return Optional.ofNullable(uri).orElseThrow(NoSuchElementException::new);
//    }
    }



