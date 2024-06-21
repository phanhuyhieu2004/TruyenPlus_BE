package com.example.truyen_be.Service.imp;



import com.example.truyen_be.Dto.StoryDTO;
import com.example.truyen_be.Model.Story;
import com.example.truyen_be.Repository.IStoryRepository;
import com.example.truyen_be.Service.IStoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
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
//        MultipartFile multipartFile = storyDTO.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//        FileCopyUtils.copy(storyDTO.getImage().getBytes(), new File(fileUpload + fileName));

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
//        MultipartFile multipartFile = storyDTO.getImage();
//        if (multipartFile != null && !multipartFile.isEmpty()) {
//            String fileName = multipartFile.getOriginalFilename();
//            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
            story.setImage(storyDTO.getImage());
//        }


        story.setDescription(storyDTO.getDescription());
        story.setAuthor(storyDTO.getAuthor());
        story.setUpdatedAt(LocalDateTime.now());
        story.setStatus(storyDTO.getStatus());
        story.setCategories(storyDTO.getCategories());

        // Tiếp tục cập nhật thông tin câu chuyện và lưu vào cơ sở dữ liệu
        // (phần này tương tự như trong ví dụ trước)

        return iStoryRepository.save(story);

    }

    @Override
    public void remove(Long id) {
        iStoryRepository.deleteById(id);
    }
}

