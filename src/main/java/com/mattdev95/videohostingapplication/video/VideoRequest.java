package com.mattdev95.videohostingapplication.video;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class VideoRequest {
    private String title;
    private String publisher;
}
