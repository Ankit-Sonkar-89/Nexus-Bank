package com.bharat.nexus_bank.controller;

import com.bharat.nexus_bank.model.AccountInfo;
import com.bharat.nexus_bank.model.CustomerInfo;
import com.bharat.nexus_bank.model.TransactionLedger;
import com.bharat.nexus_bank.service.BankingService;
import com.bharat.nexus_bank.service.AuthService;
import com.bharat.nexus_bank.repository.AccountRepository;
import com.bharat.nexus_bank.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin("*")
public class BankingController {

    private final BankingService bankingService;
    private final AuthService authService;
    private final AccountRepository accRepo;
    private final CustomerRepository custRepo;
    private final JdbcTemplate jdbcTemplate;

    public BankingController(BankingService bankingService, AuthService authService, 
                             AccountRepository accRepo, CustomerRepository custRepo, 
                             JdbcTemplate jdbcTemplate) {
        this.bankingService = bankingService;
        this.authService = authService;
        this.accRepo = accRepo;
        this.custRepo = custRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/account/{accNo}")
    public AccountInfo getAccountDetails(@PathVariable Long accNo) {
        return bankingService.getAccountDetails(accNo);
    }

    // 🔥 NEW: Fetch Full Customer Profile for Dashboard
    @GetMapping("/customer-profile/{accNo}")
    public Map<String, Object> getCustomerProfile(@PathVariable Long accNo) {
        AccountInfo acc = accRepo.findById(accNo).orElse(null);
        if(acc != null) {
            CustomerInfo cust = custRepo.findById(acc.getCustomerId()).orElse(null);
            if(cust != null) {
                return Map.of("account", acc, "customer", cust);
            }
        }
        return Map.of("error", "Not found");
    }

    @GetMapping("/statement/{accNo}")
    public List<TransactionLedger> getStatement(@PathVariable Long accNo) {
        return bankingService.getMiniStatement(accNo);
    }

    @PostMapping("/transfer")
    public Map<String, Object> transferMoney(@RequestBody Map<String, Object> payload) {
        Long senderAcc = Long.parseLong(payload.get("senderAcc").toString());
        Long receiverAcc = Long.parseLong(payload.get("receiverAcc").toString());
        Double amount = Double.parseDouble(payload.get("amount").toString());
        String remarks = payload.getOrDefault("remarks", "Fund Transfer").toString();
        return bankingService.transferFunds(senderAcc, receiverAcc, amount, remarks);
    }

    @PostMapping("/admin/create-account")
    public Map<String, Object> createAccount(@RequestBody CustomerInfo customer) {
        return bankingService.openNewAccount(customer);
    }

    // 🔥 UPGRADED: Added Aadhar, DOB, KYC, and Account Type for Admin Modal
    @GetMapping("/admin/all-accounts")
    public List<Map<String, Object>> getAllAccounts() {
        String sql = "SELECT c.CUSTOMER_NAME as name, c.CONTACT_NO as phone, c.MAIL_ID as email, " +
                     "c.DOB as dob, c.AADHAR_NO as aadhar, c.KYC_STATUS as kyc, " +
                     "a.ACCOUNT_NO as accNo, a.CURRENT_BALANCE as balance, a.ACTIVATION_DATE as date, a.ACCOUNT_TYPE as type " +
                     "FROM CUSTOMER_INFO c JOIN ACCOUNT_INFO a ON c.CUSTOMER_ID = a.CUSTOMER_ID " +
                     "ORDER BY a.ACTIVATION_DATE DESC";
        return jdbcTemplate.queryForList(sql);
    }

    @PostMapping("/send-otp")
    public Map<String, String> sendOtp(@RequestBody Map<String, String> payload) {
        return Map.of("message", authService.sendOtp(payload.get("email"), payload.get("purpose")));
    }

    @PostMapping("/customer-login")
    public Map<String, Object> customerLogin(@RequestBody Map<String, String> payload) {
        Long accNo = Long.parseLong(payload.get("accountNo"));
        String otp = payload.get("otp");
        AccountInfo acc = accRepo.findById(accNo).orElse(null);
        if(acc == null) return Map.of("success", false, "message", "Account not found!");
        CustomerInfo cust = custRepo.findById(acc.getCustomerId()).orElse(null);
        if(cust == null) return Map.of("success", false, "message", "Customer details not found!");

        if (authService.verifyOtp(cust.getMailId(), otp)) {
            return Map.of("success", true, "customerName", cust.getCustomerName());
        }
        return Map.of("success", false, "message", "Invalid OTP!");
    }
}





