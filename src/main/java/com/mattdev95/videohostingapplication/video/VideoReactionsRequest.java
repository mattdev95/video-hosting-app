package com.mattdev95.videohostingapplication.video;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class VideoReactionsRequest {

    private String title;
    private String comment;
    private String like;

}
