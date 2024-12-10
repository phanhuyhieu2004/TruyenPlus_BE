package com.example.truyen_be.repository;

import com.example.truyen_be.model.Account;
import com.example.truyen_be.model.AccountLikeStory;
import com.example.truyen_be.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountLikeStoryRepository extends JpaRepository<AccountLikeStory,Long> {
    @Query(value = "SELECT * FROM account_like_story WHERE account_id = ? AND story_id = ?",nativeQuery = true)
    Optional<AccountLikeStory> findByAccountAndStory(Long accountId, Long storyId);
}
