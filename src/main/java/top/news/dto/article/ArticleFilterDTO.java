package top.news.dto.article;

import lombok.Getter;
import lombok.Setter;
import top.news.enums.ArticleStatusEnum;

import java.time.LocalDate;

@Getter
@Setter
public class ArticleFilterDTO {
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private Integer sectionId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatusEnum status;
    LocalDate publishedDateFrom;
    LocalDate publishedDateTo;
    LocalDate createdDateFrom;
    LocalDate createdDateTo;


}
