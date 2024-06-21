package com.example.truyen_be.Dto;


import com.example.truyen_be.Model.Category;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class StoryDTO {

  private String title;

  private String image;


  private String description;


  private String author;



  private String status ;


  private Set<Category> categories = new HashSet<>();

}