package com.example.truyen_be.repository;


import com.example.truyen_be.model.Story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;



@Repository
public interface IStoryRepository  extends JpaRepository<Story, Long> {
    @Query(value = "SELECT * FROM storys ORDER BY created_at DESC LIMIT 18", nativeQuery = true)
    public Iterable<Story>findByCreatedAtOrderBy();
    @Query(value = "SELECT * FROM storys ORDER BY created_at DESC ", nativeQuery = true)
    public Iterable<Story>findByCreatedAtOrderByFull();
    @Query(value = "SELECT * FROM storys WHERE status=\"Full\"", nativeQuery = true)
    public Iterable<Story>findByStatus();
    @Query(value = "SELECT COUNT(*) FROM storys WHERE story_id = :storyId AND total_chapters > 0", nativeQuery = true)
    int countStoriesWithChapters( Long storyId);
    boolean existsByTitle(String title);
    @Query(value = "SELECT * FROM storys ORDER BY view DESC LIMIT 10",nativeQuery = true)
    public Iterable<Story>findByView();
}
