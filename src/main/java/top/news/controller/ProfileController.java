package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.profile.*;
import top.news.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<ProfileResponseDTO> create(@RequestBody ProfileRequestDTO dto){
        return ResponseEntity.ok(profileService.save(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update/{profileId}")
    public ResponseEntity<ProfileResponseDTO> update(@PathVariable Integer profileId,
                                                     @RequestBody ProfileRequestDTO dto){
        return ResponseEntity.ok(profileService.updateProfile(profileId, dto));
    }

    @PutMapping("/update/detail/{profileId}")
    public ResponseEntity<String> updateDetail(@PathVariable Integer profileId,
                                                           @RequestBody ProfileDetailUpdateDTO dto){
        return ResponseEntity.ok(profileService.updateDetail(profileId, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-id/{profileId}")
    public ResponseEntity<ProfileResponseDTO> byId(@PathVariable Integer profileId){
        return ResponseEntity.ok(profileService.getById(profileId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/delete/{profileId}")
    public ResponseEntity<String> delete(@PathVariable Integer profileId){
        return ResponseEntity.ok(profileService.deleteByProfileId(profileId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> list(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(profileService.getProfileList(page-1, size));
    }

    @PutMapping("/update/photo")
    public ResponseEntity<String> updatePhoto(@RequestBody ProfileUpdatePhotoDTO dto){
        return ResponseEntity.ok(profileService.updateProfilePhoto(dto));
    }

    @PutMapping("/update/password/{profileId}")
    public ResponseEntity<String> updatePassword(@PathVariable Integer profileId,
                                                 @RequestBody ProfileUpdatePasswordDTO pDto){
        return ResponseEntity.ok(profileService.updatePassword(profileId, pDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> filter(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                               @RequestBody ProfileFilterDTO dto){
        return ResponseEntity.ok(profileService.filter(dto, page-1, size));
    }
}
