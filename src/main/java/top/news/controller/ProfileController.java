package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.news.dto.ProfileDTO;
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
}
