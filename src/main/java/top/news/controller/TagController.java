package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.news.dto.tag.TagDTO;
import top.news.dto.tag.TagInfoDTO;
import top.news.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody TagDTO dto){
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/list")
    public ResponseEntity<List<TagInfoDTO>> list(){
        return ResponseEntity.ok(tagService.getList());
    }
}
