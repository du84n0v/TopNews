package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.dto.article.ArticleRequestDTO;
import top.news.dto.article.ArticleShortInfoDTO;
import top.news.dto.article.ArticleStatusDTO;
import top.news.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<ArticleShortInfoDTO> create(@RequestBody ArticleRequestDTO dto){
        return ResponseEntity.ok(articleService.createArticle(dto));
    }

    @PutMapping("/update/{articleId}/{moderatorId}")
    public ResponseEntity<ArticleShortInfoDTO> update(@PathVariable String articleId,
                                                      @PathVariable Integer moderatorId,
                                                      @RequestBody ArticleRequestDTO dto){
        return ResponseEntity.ok(articleService.updateArticle(articleId, moderatorId, dto));
    }

    @PutMapping("/delete/{articleId}")
    public ResponseEntity<String> deleteById(@PathVariable String articleId){
        return ResponseEntity.ok(articleService.deleteArticleById(articleId));
    }

    @PutMapping("/change-status/{articleId}/{publisherId}")
    public ResponseEntity<String> changeStatus(@PathVariable String articleId,
                                               @PathVariable Integer publisherId,
                                               @RequestBody ArticleStatusDTO dto){
        return ResponseEntity.ok(articleService.changeArticleStatus(articleId, publisherId, dto));
    }

    @GetMapping("/last-n-by-section/{sectionId}")
    public ResponseEntity<Page<ArticleShortInfoDTO>> lastNBySection(@PathVariable Integer sectionId,
                                                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                  @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(articleService.getLastNArticleBySectionId(sectionId, page-1, size));
    }

    @GetMapping("/last-12")
    public ResponseEntity<Page<ArticleShortInfoDTO>> last12(@RequestBody List<String> ids,
                                                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "12") Integer size){
        return ResponseEntity.ok(articleService.getLast12(ids, page-1, size));
    }

    @GetMapping("/last-n-by-category/{categoryId}")
    public ResponseEntity<Page<ArticleShortInfoDTO>> lastNByCategory(@PathVariable Integer categoryId,
                                                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                     @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(articleService.getLastNArticleByCategoryId(categoryId, page-1, size));
    }

    @GetMapping("/last-n-by-region/{regionId}")
    public ResponseEntity<Page<ArticleShortInfoDTO>> lastNByRegion(@PathVariable Integer regionId,
                                                                   @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                   @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(articleService.getLastNArticleByRegionId(regionId, page-1, size));
    }
}
