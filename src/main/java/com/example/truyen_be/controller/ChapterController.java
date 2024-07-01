package com.example.truyen_be.controller;



import com.example.truyen_be.dto.ChapDTO;

import com.example.truyen_be.model.Chapter;
import com.example.truyen_be.model.ErrorResponse;
import com.example.truyen_be.model.Story;
import com.example.truyen_be.repository.IChapterRepository;
import com.example.truyen_be.repository.IStoryRepository;
import com.example.truyen_be.service.imp.ChapTerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api/chapters")
@CrossOrigin("*")
public class ChapterController {
    @Autowired
    private ChapTerService chapTerService;
    @Autowired
    private IChapterRepository iChapterRepository;
    @Autowired
    private IStoryRepository iStoryRepository;
    @GetMapping("/story/{storyId}")
    public ResponseEntity<Iterable<Chapter>> getAllChapByStoryId(@PathVariable Long storyId) {
        Iterable<Chapter> chapters = iChapterRepository.findByStoryIdOrderByCreatedAtDesc(storyId);
        if (chapters == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(chapters, HttpStatus.OK);
    }
    @GetMapping("/stories/{storyId}")
    public ResponseEntity<Iterable<Chapter>> getAllChap(@PathVariable Long storyId) {
        Iterable<Chapter> chapters = iChapterRepository.findAll(storyId);
        if (chapters == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(chapters, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Chapter> deleteChapter(@PathVariable Long id) {
        Optional<Chapter> chapterOptional = chapTerService.findById(id);
        Chapter chapter = chapterOptional.get();
        Story story = chapter.getStory();
        System.out.println(story.getTotalChapters());
        story.setTotalChapters(story.getTotalChapters() - 1);
        System.out.println(story.getTotalChapters());

        iStoryRepository.save(story);
        System.out.println(story);

        chapTerService.remove(id);




        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{storyId}")
    public ResponseEntity<?> createChap(@RequestBody ChapDTO chapDTO, @PathVariable Long storyId) throws IOException {
        try {
            Chapter createdChap = chapTerService.saveChap(chapDTO, storyId);
            return new ResponseEntity<>(createdChap, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Số chương đã tồn tại trong truyện này.")) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Số chương đã tồn tại trong truyện này."));
            } else if (errorMessage.contains("Tiêu đề chương đã tồn tại trong truyện này.")) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Tiêu đề chương đã tồn tại trong truyện này."));
            } else {
                return ResponseEntity.badRequest().body(new ErrorResponse("Lỗi không xác định."));
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chapter> updateChapter(
            @PathVariable Long id,
            @RequestBody ChapDTO chapDTO) throws IOException {

            Chapter updatedChapter = chapTerService.updateChap(chapDTO, id);
            return ResponseEntity.ok(updatedChapter);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Chapter> getChapById(@PathVariable Long id) {
        Optional<Chapter> chapter = chapTerService.findById(id);
        return new ResponseEntity<>(chapter.get(), HttpStatus.OK);

    }

}
