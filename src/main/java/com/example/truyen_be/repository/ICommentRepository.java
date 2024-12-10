package com.example.truyen_be.repository;

import com.example.truyen_be.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

    public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.chapter.chapterId = :chapterId ORDER BY c.createdAt DESC")
    List<Comment> findByChapter_Id(Long chapterId);
}
