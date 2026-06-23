package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.article.*;
import top.news.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ArticleShortInfoDTO> create(@RequestBody ArticleRequestDTO dto){
        return ResponseEntity.ok(articleService.createArticle(dto));
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PutMapping("/update/{articleId}")
    public ResponseEntity<ArticleShortInfoDTO> update(@PathVariable String articleId,
                                                      @RequestBody ArticleRequestDTO dto){
        return ResponseEntity.ok(articleService.updateArticle(articleId, dto));
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PutMapping("/delete/{articleId}")
    public ResponseEntity<String> deleteById(@PathVariable String articleId){
        return ResponseEntity.ok(articleService.deleteArticleById(articleId));
    }

    @PreAuthorize("hasAnyRole('PUBLISHER', 'ADMIN')")
    @PutMapping("/change-status/{articleId}")
    public ResponseEntity<String> changeStatus(@PathVariable String articleId,
                                               @RequestBody ArticleStatusDTO dto){
        return ResponseEntity.ok(articleService.changeArticleStatus(articleId, dto));
    }

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

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderator/filter")
    public ResponseEntity<Page<ArticleShortInfoDTO>> filterForModerator(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                        @RequestBody ArticleFilterDTO dto){
        return ResponseEntity.ok(articleService.filterForModerator(dto, page-1, size));
    }

    @PreAuthorize("hasRole('PUBLISHER')")
    @PostMapping("/publisher/filter")
    public ResponseEntity<Page<ArticleShortInfoDTO>> filterForPublisher(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                        @RequestBody ArticleFilterDTO dto){
        return ResponseEntity.ok(articleService.filterForPublisher(dto, page-1, size));
    }
}
