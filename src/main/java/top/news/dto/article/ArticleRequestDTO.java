package top.news.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String content;

    private String imageId;

    private Integer regionId;

    @NotEmpty
    private List<Integer> categoryIdList;

    @NotEmpty
    private List<Integer> sectionIdList;
}
