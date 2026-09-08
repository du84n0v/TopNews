package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.article.*;
import top.news.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@CrossOrigin(origins = "*")
public class PublicArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/get-by-id/{articleId}")
    public ResponseEntity<ArticleFullInfoDTO> getById(@PathVariable String articleId) {
        return ResponseEntity.ok(articleService.getArticleById(articleId));
    }

    @GetMapping("/last-n-by-section/{sectionId}")
    public ResponseEntity<Page<ArticleShortInfoDTO>> lastNBySection(@PathVariable Integer sectionId,
                                                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                  @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(articleService.getLastNArticleBySectionId(sectionId, page-1, size));
    }

    @PostMapping("/last-12")
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

    @GetMapping("/most-read-except/{articleId}")
    public ResponseEntity<Page<ArticleShortInfoDTO>> mostReadExcept(@PathVariable String articleId,
                                                                    @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                    @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(articleService.getMostReadExcept(articleId, page-1, size));
    }

    @PostMapping("/increase-view-count-by-id/{articleId}")
    public ResponseEntity<Integer> increaseViewCountByArticleId(@PathVariable String articleId){
        return ResponseEntity.ok(articleService.increaseViewCountByArticleId(articleId));
    }

    @PostMapping("/increase-share-count-by-id/{articleId}")
    public ResponseEntity<Integer> increaseShareCountByArticleId(@PathVariable String articleId){
        return ResponseEntity.ok(articleService.increaseShareCountByArticleId(articleId));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ArticleShortInfoDTO>> filterForEveryone(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                       @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                       @RequestBody ArticleFilterDTO filterDto){
        return ResponseEntity.ok(articleService.filterForEveryOne(filterDto, page-1, size));
    }
}
