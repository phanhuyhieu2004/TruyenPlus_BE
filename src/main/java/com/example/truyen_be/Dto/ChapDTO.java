package com.example.truyen_be.Dto;


import com.example.truyen_be.Model.Story;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ChapDTO {

    private Story story;


    private String title;


    private String content;


    private int chapterNumber;

}
