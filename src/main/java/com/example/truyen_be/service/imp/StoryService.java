package com.example.truyen_be.service.imp;


import com.example.truyen_be.dto.StoryDTO;
import com.example.truyen_be.model.Account;
import com.example.truyen_be.model.AccountLikeStory;
import com.example.truyen_be.model.Story;
import com.example.truyen_be.repository.IAccountLikeStoryRepository;
import com.example.truyen_be.repository.IAccountRepository;
import com.example.truyen_be.repository.ICategoryRepository;
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
    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private IAccountLikeStoryRepository iAccountLikeStoryRepository;


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
                0,
                0
        );
        return iStoryRepository.save(story);
    }

    public String toggleLike(Long storyId, Long accountId, boolean checkOnly) {
        Optional<Story> optionalStory = iStoryRepository.findById(storyId);
        Optional<Account> optionalAccount = iAccountRepository.findById(accountId);

        if (optionalAccount.isPresent() && optionalStory.isPresent()) {
            Story story = optionalStory.get();
            Account account = optionalAccount.get();

            Optional<AccountLikeStory> existingLike = iAccountLikeStoryRepository.findByAccountAndStory (accountId,storyId);

            // Nếu chỉ kiểm tra trạng thái
            if (checkOnly) {
                return existingLike.isPresent() ? "liked" : "";
            }

            // Nếu người dùng đã thích truyện thì bỏ thích
            if (existingLike.isPresent()) {
                iAccountLikeStoryRepository.delete(existingLike.get());
                if (story.getLikes() > 0) {
                    story.setLikes(story.getLikes() - 1);
                }
                iStoryRepository.save(story);
                return "Bạn đã bỏ thích truyện này";
            } else {
                // Nếu người dùng chưa thích truyện thì thêm thích
                AccountLikeStory accountLikeStory = new AccountLikeStory(account, story);
                iAccountLikeStoryRepository.save(accountLikeStory);
                story.setLikes(story.getLikes() + 1);
                iStoryRepository.save(story);
                return "Truyện đã được thích";
            }
        }

        throw new RuntimeException("Story hoặc Account không tồn tại");
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

