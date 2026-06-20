package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.comment.*;
import top.news.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CommentDTO dto){
        return ResponseEntity.ok(commentService.create(dto));
    }

    @PutMapping("/update/{commentId}")
    private ResponseEntity<String> update(@PathVariable Integer commentId,
                                          @RequestBody CommentUpdateDTO dto){
        return ResponseEntity.ok(commentService.update(commentId, dto));
    }

    @PutMapping("/delete/{commentId}")
    public ResponseEntity<String> delete(@PathVariable Integer commentId){
        return ResponseEntity.ok(commentService.delete(commentId));
    }

    @GetMapping("/replies/{commentId}")
    public ResponseEntity<List<CommentReplyResponseDTO>> replies(@PathVariable Integer commentId){
        return ResponseEntity.ok(commentService.getReplies(commentId));
    }

    @GetMapping("/article/{articleId}")
    ResponseEntity<List<CommentArticleDTO>> articleComment(@PathVariable String articleId){
        return ResponseEntity.ok(commentService.getArticleComments(articleId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<Page<CommentFilterFullDTO>> filter(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                             @RequestBody CommentFilterDTO dto){
        return ResponseEntity.ok(commentService.filter(dto, page-1, size));
    }
}
