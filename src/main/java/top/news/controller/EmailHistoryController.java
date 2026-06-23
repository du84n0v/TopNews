package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.entity.EmailHistoryEntity;
import top.news.service.EmailHistoryService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/email-history")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService historyService;

    @GetMapping("/by-email/{email}")
    public ResponseEntity<Page<EmailHistoryEntity>> byEmail(@PathVariable String email,
                                                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size){
        return ResponseEntity.ok(historyService.getHistoryByEmail(email, page-1, size));
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<Page<EmailHistoryEntity>> byDate(@PathVariable LocalDate date,
                                                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(name = "size", defaultValue = "10") Integer size){
        return ResponseEntity.ok(historyService.getHistoryByDate(date, page-1, size));
    }
}
