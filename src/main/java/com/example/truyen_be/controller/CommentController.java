package com.example.truyen_be.controller;

import com.example.truyen_be.dto.CommentDTO;
import com.example.truyen_be.model.Account;
import com.example.truyen_be.model.Chapter;
import com.example.truyen_be.model.Comment;
import com.example.truyen_be.service.imp.AccountService;
import com.example.truyen_be.service.imp.ChapTerService;
import com.example.truyen_be.service.imp.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ChapTerService chapTerService;

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<Iterable<Comment>> getCommentsByChapter(@PathVariable Long chapterId) {
        Iterable<Comment> comments = commentService.getCommentsByChapter(chapterId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/chapter/{chapterId}/add")
    public ResponseEntity<?> addComment(@PathVariable Long chapterId,
                                        @RequestBody CommentDTO commentDTO) {
        Account account = accountService.findById(commentDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
        Chapter chapter = chapTerService.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chương không tồn tại"));
        Comment comment = commentService.addComment(account, chapter, commentDTO.getContent());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    // Sửa bình luận
    @PutMapping("/{commentId}/update")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(commentId, commentDTO.getContent());
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // Xóa bình luận
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}