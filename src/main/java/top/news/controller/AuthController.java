package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.news.dto.AuthDTO;
import top.news.service.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    private ResponseEntity<String> register(@Valid @RequestBody AuthDTO dto){
        return ResponseEntity.ok(authService.register(dto));
    }
}
