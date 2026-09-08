package top.news.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.article.ArticleRequestDTO;
import top.news.dto.article.ArticleShortInfoDTO;
import top.news.dto.article.ArticleStatusDTO;
import top.news.service.ArticleService;

@RestController
@RequestMapping("/api/v1/admin/article")
@PreAuthorize("hasRole('ADMIN')")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<ArticleShortInfoDTO> create(@Valid @RequestBody ArticleRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(dto));
    }

    @PutMapping("/update/{articleId}")
    public ResponseEntity<ArticleShortInfoDTO> update(@PathVariable String articleId,
                                                      @Valid @RequestBody ArticleRequestDTO dto){
        return ResponseEntity.ok(articleService.updateArticle(articleId, dto));
    }

    @PutMapping("/delete/{articleId}")
    public ResponseEntity<String> deleteById(@PathVariable String articleId){
        return ResponseEntity.ok(articleService.deleteArticleById(articleId));
    }

    @PutMapping("/change-status/{articleId}")
    public ResponseEntity<String> changeStatus(@PathVariable String articleId,
                                               @Valid @RequestBody ArticleStatusDTO dto){
        return ResponseEntity.ok(articleService.changeArticleStatus(articleId, dto));
    }


}
