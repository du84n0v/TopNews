package top.news.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    @NotNull
    private Integer orderNumber;
    @NotBlank
    private String nameUz;
    @NotBlank
    private String nameRu;
    @NotBlank
    private String nameEn;
    @NotNull
    private String key;
}
