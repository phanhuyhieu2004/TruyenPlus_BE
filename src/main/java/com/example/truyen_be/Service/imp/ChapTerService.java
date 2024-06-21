package com.example.truyen_be.Service.imp;




import com.example.truyen_be.Dto.ChapDTO;
import com.example.truyen_be.Model.Chapter;
import com.example.truyen_be.Model.Story;
import com.example.truyen_be.Repository.IChapterRepository;
import com.example.truyen_be.Repository.IStoryRepository;
import com.example.truyen_be.Service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class ChapTerService implements IChapterService {
@Autowired
private IChapterRepository iChapterRepository;

    @Autowired
    private IStoryRepository storyRepository;

    @Override
    public Iterable<Chapter> findAll() {
        return iChapterRepository.findAll();
    }

    @Override
    public Optional<Chapter> findById(Long id) {
        return iChapterRepository.findById(id);
    }

    @Override
    public Chapter save(Chapter chapter) {
        return iChapterRepository.save(chapter);
    }

    @Override
    public void remove(Long id) {
iChapterRepository.deleteById(id);
    }
    public Chapter saveChap(ChapDTO chapDTO, Long storyId) throws IOException {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (!optionalStory.isPresent()) {
            throw new RuntimeException("Không tìm thấy truyện với ID " + storyId);
        }
        Story story = optionalStory.get();

        // Kiểm tra xem số chương đã tồn tại trong cùng một câu truyện chưa
        if (iChapterRepository.existsByChapterNumberAndStory_StoryId(chapDTO.getChapterNumber(), storyId)) {
            throw new DuplicateKeyException("Số chương đã tồn tại trong truyện này.");
        }
// Kiểm tra tiêu đề chương có trùng nhau trong cùng một câu truyện chưa
        if (iChapterRepository.existsByTitleAndStory_StoryId(chapDTO.getTitle(), storyId)) {
            throw new DuplicateKeyException("Tiêu đề chương đã tồn tại trong truyện này.");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        Chapter chapter = new Chapter(
                null,
                story,
                chapDTO.getTitle(),
                chapDTO.getContent(),
                chapDTO.getChapterNumber(),
                localDateTime,
                localDateTime
        );
        Chapter savedChapter = iChapterRepository.save(chapter);

        // Cập nhật tổng số chương của câu truyện
        story.setTotalChapters(story.getTotalChapters() + 1);
        storyRepository.save(story);

        return savedChapter;
    }
    public Chapter updateChap(ChapDTO chapDTO,  Long chapterId) throws IOException {
        Optional<Chapter> optionalChapter = iChapterRepository.findById(chapterId);
        if (!optionalChapter.isPresent()) {
            throw new RuntimeException("Không tìm thấy chương với ID " + chapterId);
        }
        Chapter chapter = optionalChapter.get();



        LocalDateTime localDateTime = LocalDateTime.now();
        chapter.setTitle(chapDTO.getTitle());
        chapter.setContent(chapDTO.getContent());

        chapter.setUpdatedAt(localDateTime);

        return iChapterRepository.save(chapter);
    }

}
