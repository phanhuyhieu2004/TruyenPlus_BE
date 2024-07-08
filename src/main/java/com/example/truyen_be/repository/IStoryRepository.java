package com.example.truyen_be.repository;


import com.example.truyen_be.model.Story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


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
@Query(value = "select s.* from storys s join story_category sc on s.story_id=sc.story_id\n" +
        "join categorys c on c.category_id=sc.category_id where c.category_name= :categoryName",nativeQuery = true)
public Iterable<Story> findByCategoryName(@RequestParam("categoryName") String categoryName);
public Iterable<Story>findStoriesByAuthor(@RequestParam  String author);
    @Query("SELECT s FROM Story s " +
            "WHERE s.title LIKE %:searchTerm% OR s.author LIKE %:searchTerm%")
    Iterable<Story> findByTitleOrAuthorContaining(@RequestParam("searchTerm") String searchTerm);
    @Query("SELECT DISTINCT s FROM Story s " +
            "LEFT JOIN s.categories c " +
            "WHERE (:title IS NULL OR s.title LIKE %:title%) " +
            "AND (:author IS NULL OR s.author LIKE %:author%) " +
            "AND (:categoryId IS NULL OR c.categoryId IN :categoryId)")
    Iterable<Story> findByCriteria(@RequestParam("title") String title,
                                   @RequestParam("author") String author,
                                   @RequestParam("categoryId") List<Long> categoryId);
}
