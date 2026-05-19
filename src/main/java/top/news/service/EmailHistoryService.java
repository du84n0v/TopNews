package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.news.dto.auth.MailMessageDTO;
import top.news.entity.EmailHistory;
import top.news.repository.EmailHistoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository historyRepository;

    public void create(MailMessageDTO dto, String code) {
        EmailHistory history = new EmailHistory();
        history.setToEmail(dto.getToAccount());
        history.setSubject(dto.getSubject());
        history.setCode(code);
        history.setCreatedDate(LocalDateTime.now());

        historyRepository.save(history);
    }

    public Page<EmailHistory> getHistoryByEmail(String email, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistory> pages = historyRepository.findAllByToEmailOrderByCreatedDateDesc(email, pageable);

        return new PageImpl<>(pages.getContent(), pageable, pages.getTotalElements());
    }

    public Page<EmailHistory> getHistoryByDate(LocalDate date, Integer page, Integer size) {
        LocalDateTime fromDate = date.atStartOfDay();
        LocalDateTime toDate = date.atTime(LocalTime.MAX);
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistory> pages = historyRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(fromDate, toDate, pageable);

        return new PageImpl<>(pages.getContent(), pageable, pages.getTotalElements());
    }

    public int getCountAfter(String toAccount, LocalDateTime from) {
        return historyRepository.countByToEmailAfter(toAccount, from);
    }
}
