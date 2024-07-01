package com.example.truyen_be.dto;



import com.example.truyen_be.model.Category;

import lombok.Data;

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