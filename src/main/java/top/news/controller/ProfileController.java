package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.dto.ProfileDTO;
import top.news.dto.ProfileFilterDTO;
import top.news.dto.ProfileInfoDTO;
import top.news.service.ProfileService;

@RestController
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileInfoDTO> create(@RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.save(dto));
    }

    @GetMapping("/by-id/{profileId}")
    public ResponseEntity<ProfileDTO> byId(@PathVariable Integer profileId){
        return ResponseEntity.ok(profileService.getById(profileId));
    }

    @GetMapping("/list")
    public ResponseEntity<PageImpl<ProfileInfoDTO>> list(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(profileService.getProfileList(page-1, size));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileInfoDTO>> filter(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                           @RequestBody ProfileFilterDTO dto){
        return ResponseEntity.ok(profileService.studentFilter(dto, page-1, size));
    }
}
