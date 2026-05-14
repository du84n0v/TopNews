package top.news.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDTO {

    private Integer id;

    private Integer orderNumber;

    private String nameUz;

    private String nameRu;

    private String nameEn;

    private String categoryKey;

    private LocalDateTime createdDate;

    private String name;
}
