package top.news.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import top.news.dto.category.CategoryShortDTO;
import top.news.dto.region.RegionShortDTO;
import top.news.dto.section.SectionShortDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFullInfoDTO {

    private String id;

    private String title;

    private String description;

    private String content;

    private Integer sharedCount;

    private RegionShortDTO region;

    private List<CategoryShortDTO> categoryList;

    private List<SectionShortDTO> sectionList;

    private LocalDateTime publishedDate;

    private Integer viewCount;

    private Integer likeCount;
}
