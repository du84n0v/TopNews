package top.news.controller.publisher;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.article.ArticleFilterDTO;
import top.news.dto.article.ArticleShortInfoDTO;
import top.news.dto.article.ArticleStatusDTO;
import top.news.service.ArticleService;

@RestController
@RequestMapping("/api/v1/publisher/article")
@PreAuthorize("hasRole('PUBLISHER')")
public class PublisherArticleController {

    @Autowired
    private ArticleService articleService;

    @PutMapping("/change-status/{articleId}")
    public ResponseEntity<String> changeStatus(@PathVariable String articleId,
                                               @Valid @RequestBody ArticleStatusDTO dto){
        return ResponseEntity.ok(articleService.changeArticleStatus(articleId, dto));
    }

    @PostMapping("/publisher/filter")
    public ResponseEntity<Page<ArticleShortInfoDTO>> filterForPublisher(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                        @RequestBody ArticleFilterDTO dto){
        return ResponseEntity.ok(articleService.filterForPublisher(dto, page-1, size));
    }
}
