package com.example.truyen_be.repository;

import com.example.truyen_be.model.ReadingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IReadingHistoryRepository extends JpaRepository<ReadingHistory, Long>{
    @Query("SELECT r FROM ReadingHistory r WHERE r.account.accountId = :accountId AND DATE(r.readAt) = :date")
    List<ReadingHistory> findByAccountIdAndDate(@Param("accountId") Long accountId, @Param("date") LocalDate date);
    List<ReadingHistory> findByAccountAccountId(Long accountId);
}
