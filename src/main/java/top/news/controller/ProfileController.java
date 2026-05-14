package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.dto.profile.ProfileRequestDTO;
import top.news.dto.profile.ProfileFilterDTO;
import top.news.dto.profile.ProfileResponseDTO;
import top.news.service.ProfileService;

@RestController
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileResponseDTO> create(@RequestBody ProfileRequestDTO dto){
        return ResponseEntity.ok(profileService.save(dto));
    }

    @PutMapping("/admin-update/{profileId}")
    public ResponseEntity<ProfileResponseDTO> update(@PathVariable Integer profileId,
                                                     @RequestBody ProfileRequestDTO dto){
        return ResponseEntity.ok(profileService.updateProfile(profileId, dto));
    }

    @GetMapping("/by-id/{profileId}")
    public ResponseEntity<ProfileResponseDTO> byId(@PathVariable Integer profileId){
        return ResponseEntity.ok(profileService.getById(profileId));
    }

    @PutMapping("/by-id/{profileId}")
    public ResponseEntity<String> delete(@PathVariable Integer profileId){
        return ResponseEntity.ok(profileService.deleteByProfileId(profileId));
    }

    @GetMapping("/list")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> list(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(profileService.getProfileList(page-1, size));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> filter(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                               @RequestBody ProfileFilterDTO dto){
        return ResponseEntity.ok(profileService.studentFilter(dto, page-1, size));
    }
}
