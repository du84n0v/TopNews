package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.news.exception.AppBadRequestException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SmsSenderService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SmsHistoryService historyService;

    public void verificationCode(String phone) {
        runOut(phone);
        String providerUrl = "http://localhost:8082/sms-provider/send";
        String code = generateCode();
        Map<String, String> request = new HashMap<>();
        request.put("phone", phone);
        request.put("code", code);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(providerUrl, request, String.class);
            System.out.println("Provider javobi: " + response.getBody());

        } catch (RuntimeException e){
            System.out.println("SMS provayderga ulanishda xatolik yuz berdi: " + e.getMessage());
            throw new RuntimeException("SMS xizmatida nosozlik. Keyinroq urinib ko'ring.");
        }
    }

    private void runOut(String phone) {
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        int cnt = historyService.getCountAfter(phone, from);
        if(cnt >= 4){
            throw new AppBadRequestException("Too many request. Please try later");
        }
    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
