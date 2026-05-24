package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.news.dto.sms.LoginRequest;
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

    @Value("${company.email}")
    private String companyEmail;
    @Value("${company.password}")
    private String companyPassword;

    public void sendSms(String phone) {
        runOut(phone);
        String token = getToken();
        String providerUrl = "http://localhost:8082/sms/send";
        String code = generateCode();
        Map<String, String> request = new HashMap<>();
        String message = "Sizning Topnews portali uchun tastiqlash kodinggiz: " + code;
        request.put("userToken", token);
        request.put("phone", phone);
        request.put("message", message);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(providerUrl, request, String.class);
            System.out.println("Provider javobi: " + response.getBody());
            historyService.save(phone, message);

        } catch (RuntimeException e){
            System.out.println("SMS provayderga ulanishda xatolik yuz berdi: " + e.getMessage());
            throw new RuntimeException("SMS xizmatida nosozlik. Keyinroq urinib ko'ring.");
        }
    }

    private String getToken() {
        String providerUrl = "http://localhost:8082/auth/login";
        LoginRequest request = new LoginRequest(companyEmail, companyPassword);

        return restTemplate.postForObject(providerUrl, request, String.class);
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
