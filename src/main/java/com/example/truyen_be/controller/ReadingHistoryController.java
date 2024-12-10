package com.example.truyen_be.controller;

import com.example.truyen_be.dto.ReadingDTO;
import com.example.truyen_be.model.Account;
import com.example.truyen_be.model.Chapter;
import com.example.truyen_be.model.ReadingHistory;
import com.example.truyen_be.model.Story;

import com.example.truyen_be.service.imp.AccountService;
import com.example.truyen_be.service.imp.ChapTerService;
import com.example.truyen_be.service.imp.ReadingHistoryService;
import com.example.truyen_be.service.imp.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/history")
@CrossOrigin("*")

public class ReadingHistoryController {

    @Autowired
private ReadingHistoryService readingHistoryService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private ChapTerService chapTerService;

    @PostMapping("/read")
    public void recordReading(@RequestBody ReadingDTO readingDTO) {
        // Lấy tài khoản từ ID trong request
        Account account = accountService.findById(readingDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        // Lấy truyện và chương từ cơ sở dữ liệu bằng ID
        Story story = storyService.findById(readingDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm được truyện"));

        Chapter chapter = chapTerService.findById(readingDTO.getChapterId())
                .orElseThrow(() -> new RuntimeException("Không tìm được chương"));

        // Ghi nhận lịch sử đọc
        readingHistoryService.saveReadingHistory(account, story, chapter);
    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<ReadingHistory>> getReadingHistoryByAccountId(
            @PathVariable Long accountId,
            @RequestParam(required = false) String date) {

        List<ReadingHistory> history;
        if (date != null) {
            // Lấy lịch sử đọc theo ngày
            history = readingHistoryService.getReadingHistoryByDate(accountId, date);
        } else {
            // Lấy toàn bộ lịch sử đọc
            history = readingHistoryService.getReadingHistoryByAccountId(accountId);
        }
        return ResponseEntity.ok(history);
    }

}