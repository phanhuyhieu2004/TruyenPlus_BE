package com.example.truyen_be.service.imp;

import com.example.truyen_be.model.Comment;
import com.example.truyen_be.model.Account;
import com.example.truyen_be.model.Chapter;
import com.example.truyen_be.repository.ICommentRepository;
import com.example.truyen_be.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private ICommentRepository iCommentRepository;

    public Comment addComment(Account account, Chapter chapter, String content) {
        Comment comment = new Comment();
        comment.setAccount(account);
        comment.setChapter(chapter);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        return iCommentRepository.save(comment);
    }

    public List<Comment> getCommentsByChapter(Long chapterId) {
        return iCommentRepository.findByChapter_Id(chapterId);
    }

    @Override
    public Iterable<Comment> findAll() {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    public Comment updateComment(Long commentId, String content) {
        Comment comment = iCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Bình luận không tồn tại"));
        comment.setContent(content);
        return iCommentRepository.save(comment); // Lưu lại bình luận đã được cập nhật
    }

    public void deleteComment(Long commentId) {
        if (!iCommentRepository.existsById(commentId)) {
            throw new RuntimeException("Bình luận không tồn tại");
        }
        iCommentRepository.deleteById(commentId);
    }
}
