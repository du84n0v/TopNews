package top.news.dto.article;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.news.enums.ArticleStatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleStatusDTO {
    @NotNull
    private ArticleStatusEnum status;
}
