package com.example.truyen_be.dto;



import com.example.truyen_be.model.Story;

import lombok.Data;

@Data
public class ChapDTO {

    private Story story;


    private String title;


    private String content;


    private int chapterNumber;

}
