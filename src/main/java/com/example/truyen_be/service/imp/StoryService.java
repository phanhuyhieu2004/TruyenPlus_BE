package com.example.truyen_be.service.imp;



import com.example.truyen_be.dto.StoryDTO;
import com.example.truyen_be.model.Story;
import com.example.truyen_be.repository.IStoryRepository;
import com.example.truyen_be.service.IStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StoryService implements IStoryService {


    @Autowired
    private IStoryRepository iStoryRepository;


    @Override
    public Iterable<Story> findAll() {
        return iStoryRepository.findAll();
    }

    @Override
    public Optional<Story> findById(Long id) {
        return iStoryRepository.findById(id);
    }

    @Override
    public Story save(Story story) {
        return iStoryRepository.save(story);
    }

    public Story saveStory(StoryDTO storyDTO) throws IOException {
        if (iStoryRepository.existsByTitle(storyDTO.getTitle())) {
            throw new DuplicateKeyException("Tiêu đề đã tồn tại.");
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        Story story = new Story(
                null,
                storyDTO.getTitle(),
                storyDTO.getImage(),

                storyDTO.getDescription(),
                storyDTO.getAuthor(),
                localDateTime,
                localDateTime,

                "New",

                storyDTO.getCategories(),
                0,
                0
        );
        return iStoryRepository.save(story);
    }
    public Story updateStory(Long storyId, StoryDTO storyDTO) throws IOException {
        Optional<Story> optionalStory = iStoryRepository.findById(storyId);


        Story story = optionalStory.get();

        // Kiểm tra xem tiêu đề mới có giống với tiêu đề cũ không
        String oldTitle = story.getTitle();
        String newTitle = storyDTO.getTitle();
        if (!oldTitle.equalsIgnoreCase(newTitle)) {
            // Tiêu đề mới đã thay đổi, kiểm tra xem có trùng với tiêu đề khác không
            if (iStoryRepository.existsByTitle(newTitle)) {
                throw new DuplicateKeyException("Tiêu đề đã tồn tại");
            }
            // Tiêu đề mới không trùng, cập nhật thông tin câu chuyện
            story.setTitle(newTitle);
        }
  story.setImage(storyDTO.getImage());



        story.setDescription(storyDTO.getDescription());
        story.setAuthor(storyDTO.getAuthor());
        story.setUpdatedAt(LocalDateTime.now());
        story.setStatus(storyDTO.getStatus());
        story.setCategories(storyDTO.getCategories());

        return iStoryRepository.save(story);

    }

    @Override
    public void remove(Long id) {
        iStoryRepository.deleteById(id);
    }
}

