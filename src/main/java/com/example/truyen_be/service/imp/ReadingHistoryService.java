package com.example.truyen_be.service.imp;


import com.example.truyen_be.model.Account;
import com.example.truyen_be.model.Chapter;
import com.example.truyen_be.model.ReadingHistory;
import com.example.truyen_be.model.Story;
import com.example.truyen_be.repository.IReadingHistoryRepository;
import com.example.truyen_be.service.IReadingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReadingHistoryService implements IReadingHistoryService {

    @Autowired
    private IReadingHistoryRepository iReadingHistoryRepository;

    public void saveReadingHistory(Account account, Story story, Chapter chapter) {
        ReadingHistory history = new ReadingHistory(account, story, chapter, LocalDateTime.now());
        iReadingHistoryRepository.save(history);
    }

    @Override
    public Iterable<ReadingHistory> findAll() {
        return null;
    }
    public List<ReadingHistory> getReadingHistoryByAccountId(Long accountId) {
        return iReadingHistoryRepository.findByAccountAccountId(accountId);
    }
    public List<ReadingHistory> getReadingHistoryByDate(Long accountId, String date) {
        LocalDate parsedDate = LocalDate.parse(date); // Chuyển đổi chuỗi ngày thành LocalDate
        return iReadingHistoryRepository.findByAccountIdAndDate(accountId, parsedDate);
    }
    @Override
    public Optional<ReadingHistory> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ReadingHistory save(ReadingHistory readingHistory) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}

