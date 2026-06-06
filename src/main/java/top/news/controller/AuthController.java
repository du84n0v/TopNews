package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.dto.auth.LoginDTO;
import top.news.dto.auth.RegistrationDTO;
import top.news.dto.auth.VerificationDTO;
import top.news.dto.profile.ProfileDetailUpdateDTO;
import top.news.dto.profile.ProfileResponseDTO;
import top.news.service.AuthService;
import top.news.service.ProfileService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ProfileService profileService;

    @PostMapping("/registration")
    private ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerificationDTO dto){
        return ResponseEntity.ok(authService.verify(dto));
    }

    @PostMapping("/resend-code")
    public ResponseEntity<String> resend(@RequestParam String email){
        return ResponseEntity.ok(authService.resend(email));
    }

    @PutMapping("/update/{profileId}")
    public ResponseEntity<String> update(@PathVariable Integer profileId,
                                         @Valid @RequestBody ProfileDetailUpdateDTO dto){
        return ResponseEntity.ok(profileService.updateProfileById(profileId, dto));
    }

    @GetMapping("/login")
    public ResponseEntity<ProfileResponseDTO> login(@RequestBody LoginDTO login){
        return ResponseEntity.ok(authService.login(login));
    }


}
