package top.news.controller.moderator;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.article.ArticleFilterDTO;
import top.news.dto.article.ArticleFullInfoDTO;
import top.news.dto.article.ArticleRequestDTO;
import top.news.dto.article.ArticleShortInfoDTO;
import top.news.service.ArticleService;

@RestController
@RequestMapping("/api/v1/moderator/article")
@PreAuthorize("hasRole('MODERATOR')")
public class ModeratorArticleController {

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

    @GetMapping("/own-articles")
    public ResponseEntity<Page<ArticleShortInfoDTO>> getOwnArticles(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(articleService.getOwnArticles(page, size));
    }

    @PostMapping("/moderator/filter")
    public ResponseEntity<Page<ArticleShortInfoDTO>> filterForModerator(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                        @RequestBody ArticleFilterDTO dto){
        return ResponseEntity.ok(articleService.filterForModerator(dto, page-1, size));
    }
}
