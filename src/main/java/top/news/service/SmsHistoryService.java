package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.repository.SmsHistoryRepository;

import java.time.LocalDateTime;

@Service
public class SmsHistoryService {

    @Autowired
    private SmsHistoryRepository historyRepository;

    public int getCountAfter(String phone, LocalDateTime from) {
        return historyRepository.countByPhoneAfter(phone, from);
    }
}
