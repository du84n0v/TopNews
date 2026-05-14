package top.news.dto.section;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionResponseDTO {

    private Integer id;

    private Integer orderNumber;

    private String nameUz;

    private String nameRu;

    private String nameEn;

    private String key;

    private LocalDateTime createdDate;

    private String name;
}
