package com.example.truyen_be.controller;


import com.example.truyen_be.dto.StoryDTO;
import com.example.truyen_be.model.Story;
import com.example.truyen_be.repository.IStoryRepository;
import com.example.truyen_be.service.imp.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/stories")
@CrossOrigin("*")
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private IStoryRepository iStoryRepository;
    @PostMapping("/{storyId}/like")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long storyId,
            @RequestParam Long accountId,
            @RequestParam(defaultValue = "false") boolean checkOnly) {
        String message = storyService.toggleLike(storyId, accountId, checkOnly);
        return ResponseEntity.ok(message);
    }
    @GetMapping("")
    public ResponseEntity<Iterable<Story>> getAllLikes() {
        Iterable<Story> stories = iStoryRepository.findByLikes();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
    @GetMapping("/new")
    public ResponseEntity<Iterable<Story>> getAllNew() {
        Iterable<Story> stories = iStoryRepository.findByCreatedAtOrderBy();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
    @GetMapping("/stories-chap")
    public ResponseEntity<Iterable<Story>> getAllStoriesChap() {
        Iterable<Story> stories = iStoryRepository.findAll();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<Iterable<Story>> getAllView() {
        Iterable<Story> stories = iStoryRepository.findByView();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
    @GetMapping("/category")
    public ResponseEntity<Iterable<Story>> getStoriesByCategory(@RequestParam String categoryName) {
        Iterable<Story> stories = iStoryRepository.findByCategoryName(categoryName);
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<Iterable<Story>> searchStories(   @RequestParam(required = false) String searchTerm
                                                  ) {
        Iterable<Story> stories = iStoryRepository.findByTitleOrAuthorContaining(searchTerm);
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
    @GetMapping("/searches")
    public Iterable<Story> searchStories(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String author,
                                     @RequestParam(required = false) List<Long> categories) {
        return iStoryRepository.findByCriteria(title, author, categories);
    }
    @GetMapping("/author")
    public ResponseEntity<Iterable<Story>> getStoriesByAuthor(@RequestParam String author) {
        Iterable<Story> stories = iStoryRepository.findStoriesByAuthor(author);
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }

    @GetMapping("/full")
    public ResponseEntity<Iterable<Story>> getAll() {
        Iterable<Story> stories = iStoryRepository.findByCreatedAtOrderByFull();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<Iterable<Story>> getStories() {
        Iterable<Story> stories = iStoryRepository.findByStatus();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long id) {
        Optional<Story> story = storyService.findById(id);
        if (!story.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Story storyView = story.get();

        storyView.setView(storyView.getView() + 1);
        storyService.save(storyView);
        return new ResponseEntity<>(storyView, HttpStatus.OK);

    }


    @PostMapping("")
    public ResponseEntity<Story> createStory(@ModelAttribute StoryDTO storyDTO) throws IOException {
        try {
            Story createdStory = storyService.saveStory(storyDTO);
            return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{storyId}")
    public ResponseEntity<Story> updateStory(
            @PathVariable Long storyId,
            @ModelAttribute StoryDTO storyDTO) throws IOException {
        try {
            Story updatedStory = storyService.updateStory(storyId, storyDTO);
            return ResponseEntity.ok(updatedStory);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Story> deleteStory(@PathVariable Long id) {
        Optional<Story> storyOptional = storyService.findById(id);
        if (!storyOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        storyService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/chap/{storyId}")
    public ResponseEntity<Integer> hasChapter(@PathVariable Long storyId) {
        int hasChapters = iStoryRepository.countStoriesWithChapters(storyId);
        return ResponseEntity.ok(hasChapters);
    }


}
