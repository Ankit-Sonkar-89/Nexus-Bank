// package com.bharat.nexus_bank.service;

// public class AuthService {
    
// }

// (Ye naya file hai jo OTP bhejega aur verify karega)

package com.bharat.nexus_bank.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final JavaMailSender mailSender;
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public AuthService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendOtp(String email, String purpose) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("sonkarankit624@gmail.com", "NexusBank Security");
            helper.setTo(email);
            helper.setSubject("NexusBank " + purpose + " OTP 🔒");
            helper.setText("<h3>Welcome to NexusBank!</h3><p>Your OTP is: <b style='font-size:24px; color:#6366f1;'>" + otp + "</b></p><p>Do not share this with anyone.</p>", true);
            
            mailSender.send(message);
            return "OTP sent successfully to " + email;
        } catch (Exception e) {
            return "Failed to send OTP.";
        }
    }

    public boolean verifyOtp(String email, String otp) {
        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(otp.trim())) {
            otpStorage.remove(email); // Secure cleanup
            return true;
        }
        return false;
    }
}
