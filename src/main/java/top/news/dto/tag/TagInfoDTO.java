package top.news.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.news.enums.TagStatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagInfoDTO {
    private Integer id;
    private String name;
    private TagStatusEnum status;
}
